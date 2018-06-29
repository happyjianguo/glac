package net.engining.sccc.batch.sccc4900;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.gm.facility.SystemStatusFacility;

/**
 * 更新系统状态到非批量状态
 *
 */
@Service
@StepScope
public class Sccc4900T10SystemStatus implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(Sccc4900T10SystemStatus.class);
	
	@Autowired
	SystemStatusFacility systemStatusFacility;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info("##更新系统状态到非批量状态");
		
		systemStatusFacility.changeSystemStatus();
		
		return RepeatStatus.FINISHED;
	}
}
