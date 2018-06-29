package net.engining.sccc.batch.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import net.engining.sccc.config.props.BatchTaskProperties;

/**
 * Batch的远程分区通讯配置,基于Spring Integration+RabbitMQ
 * @author luxue
 *
 */
@Configuration
public class BatchIntegrationContextConfig {

	@Autowired
	JobExplorer jobExplorer;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	CachingConnectionFactory connectionFactory;
	
	@Autowired
	SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	BatchTaskProperties batchTaskProperties;
	
	@Autowired
	RabbitProperties rabbitProperties;
	
	/**
	 * Step9999 数据清理任务特定的消息队列
	 */
	protected static final String EodJob4Step9999Queue = "eodjob.step9999.queue";
	
	protected static final String BatchPartitionQueue = "batch.partition.queue";
	
	@Bean
	public Queue eodJob4Step9999Queue(){
		//定义为持久化队列
		return new Queue(EodJob4Step9999Queue, true);
	}

	/**
	 * Direct类型的通道,为每个发送的消息调用单个独立的订阅服务器,调用将发生在发送者的线程中;<br>
	 * 用于点对点场景,因此该通道会将消息直接分发给接收者，且接收者只有一个;这里的对应的场景是接收者(slave
	 * step)只返回处理结果给其对应的发送者(master step);<br>
	 * 除此之外,DirectChannel还有一个最重要的特点就是发送和接收双方处于一个线程当中,如下:发送者发送消息->接收者接收消息并触发处理操作->返回;
	 * 
	 * @return
	 */
	@Bean
	public DirectChannel outboundRequests() {
		return new DirectChannel();
	}

	/**
	 * 队列类型的通道,可以缓存消息;<br>
	 * 在缓存消息没有达到上限时,消息发送者将消息发送到该通道后立即返回.如果缓存消息数量达到设定的容量,则消息发送者发送消息后会被阻塞,直到消息队列中有空间为止或者超时;<br>
	 * 对于消息接收者正好相反，尝试获取消息时，如果队列中有消息会立即返回，如果队列中没有消息则会一直阻塞直到超时,需要设定超时时间，不设定的话一直阻塞;<br>
	 * 
	 * @return
	 */
	@Bean
	public QueueChannel inboundRequests() {
		return new QueueChannel();
	}

	/**
	 * Define the message gateway, which is responsible for sending messages to
	 * the requests queue and receive messages from the reply queue.
	 * 
	 * @return
	 */
	@Bean
	public MessagingTemplate messageTemplate() {
		// 设置消息的默认Channel: (outboundRequests-DirectChannel)
		MessagingTemplate messagingTemplate = new MessagingTemplate(outboundRequests());
		// 设置接收消息的超时时间, 这里应该是用于master step接受slave step的执行结果的超时配置: 60秒
		messagingTemplate.setReceiveTimeout(60000L);
		// 设置发送消息的超时时间, 这里应该是用于master step发送指令给slave step的超时配置: 60秒
//		messagingTemplate.setSendTimeout(60000l);
		return messagingTemplate;
	}

	/**
	 * 批量Step分区,专用的消息队列;这里定义的队列是非持久化的;
	 * 
	 * @return
	 */
	@Bean
	public Queue batchPartitionQueue() {
		return new Queue(BatchPartitionQueue, false);
	}

	/**
	 * 消息端点(Message EndPoint)是真正处理消息的(Message)组件,即是一个消息处理器(implement
	 * MessageHandler);等同于创建一个基于Direct方式的AMQP交换机(用于converts and sends Messages
	 * to an AMQP Exchange);<br>
	 * 消息端点可以包含如下组件: Channel Adapter,Gateway,Service
	 * Activator,Router,Filter,Splitter,Aggregator,Enricher,Transformer,Bridge;<br>
	 * 
	 * 对于@ServiceActivator来说，必须至少有一个inputChannel，这里定义为outboundRequests(DirectChannel);<br>
	 * 对应的场景是master
	 * step通过inputChannel发送分片消息给定义的AMQP交换机(AmqpOutboundEndpoint),由于这个inputChannel是一个DirectChannel类型，因此只有其自己可以获得返回消息;<br>
	 * 而该消息处理器的发送通道对应一个消息队列,因为Slave
	 * step可以是多个,因此就会有多个消费者;完成step执行后将结果返回给消息处理器的ReturnChannel(无配置时，原Channel返回),最后由于ServiceActivator的配置再将返回消息返回给其DirectChannel;
	 * 
	 * 另外AmqpOutboundEndpoint也是SPI默认的Outbound Channel Adapter;
	 * 
	 * @param template
	 * @return
	 */
	@Bean
	@ServiceActivator(inputChannel = "outboundRequests")
	public AmqpOutboundEndpoint amqpOutboundEndpoint() {
		AmqpOutboundEndpoint endpoint = new AmqpOutboundEndpoint(rabbitTemplate);
		// 设置必须有返回
		endpoint.setExpectReply(true);
		// 设置生成消息的消息通道:
		endpoint.setOutputChannel(inboundRequests());
		// 设置将无法交付的消息返回给适配器的通道
		// endpoint.setReturnChannel(returnChannel);
		// 设置发送回复消息的超时时间：60秒
//		endpoint.setSendTimeout(60000l);
		// 设置路由key
		endpoint.setRoutingKey(BatchPartitionQueue);
		return endpoint;
	}

	/**
	 * 通道适配器(Channel
	 * Adapter)是一种连接外部系统或传输协议的端点(EndPoint),可以分为入站(inbound)和出站(outbound);通道适配器是单向的，入站通道适配器只支持接收消息，出站通道适配器只支持输出消息;<br>
	 * 
	 * 这里定义了一个Inbound通道适配器,只作为slave step接收来自master
	 * step的分片处理请求消息,使用QueueChannel;通过定义的SimpleMessageListenerContainer监听是否有来自master
	 * step消息;<br>
	 * 
	 * @param listenerContainer
	 * @return
	 */
	@Bean
	@Profile({ "slave", "mixed" })
	public AmqpInboundChannelAdapter inbound(SimpleMessageListenerContainer listenerContainer) {
		AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(listenerContainer);
		adapter.setOutputChannel(inboundRequests());
		adapter.afterPropertiesSet();
		return adapter;
	}

	/**
	 * 这里相当于定义了一个消息端点(Message EndPoint),只用于slave step;<br>
	 * 该MessageEndPoint包含一个ServiceActivator,从inboundRequests(QueueChannel)获取信息，并执行处理相应的step，然后将处理结果返回到outboundStaging(NullChannel);<br>
	 * 
	 * 通用的从节点StepExecutionRequestHandler配置;从节点最关键的地方在于StepExecutionRequestHandler,会接收MQ消息中间件中的消息,并从分区信息中获取到需要处理的数据边界;<br>
	 * 
	 * @param jobExplorer
	 * @return
	 */
	@Bean
	@Profile({ "slave", "mixed" })
	@ServiceActivator(inputChannel = "inboundRequests", outputChannel = "outboundStaging")
	public StepExecutionRequestHandler stepExecutionRequestHandler() {
		StepExecutionRequestHandler stepExecutionRequestHandler = new StepExecutionRequestHandler();
		BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
		stepLocator.setBeanFactory(applicationContext);
		stepExecutionRequestHandler.setStepLocator(stepLocator);
		stepExecutionRequestHandler.setJobExplorer(jobExplorer);
		return stepExecutionRequestHandler;
	}

	/**
	 * 配置一个RabbitMq的amqp监听器,负责监听指定的队列"batch.partition.queue";
	 * 默认只有一个线程监听，可以配置线程池;
	 * 
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer listenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueueNames(BatchPartitionQueue);
		// 消息监听容器的线程池大小
//		container.setTaskExecutor(Executors.newFixedThreadPool(batchTaskProperties.getRabbitmqMsgListenerPoolSize()));
		// 并发的消息消费者数量
		container.setConcurrentConsumers(rabbitProperties.getListener().getConcurrency());
		// 每次从broker里面一次性获取的待消费的消息的个数;
		container.setPrefetchCount(rabbitProperties.getListener().getPrefetch());
		container.setAutoStartup(rabbitProperties.getListener().isAutoStartup());

		return container;
	}

	@Bean
	public PollableChannel outboundStaging() {
		return new NullChannel();
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata defaultPoller() {
		PollerMetadata pollerMetadata = new PollerMetadata();
		pollerMetadata.setTrigger(new PeriodicTrigger(10));
		return pollerMetadata;
	}

}
