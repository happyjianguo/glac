package net.engining.sccc.init.param.gm;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.engining.gm.infrastructure.enums.SystemStatusType;
import net.engining.gm.param.model.SystemStatus;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;
import net.engining.sccc.config.props.CommonProperties;

@Service
public class SystemStatusInit implements ParameterInitializer {

	@Autowired
	private ParameterFacility facility;
	
	@Autowired
	private CommonProperties commonProperties;

	@Override
	public void init() throws Exception {
		
		for (String s : facility.getParameterMap(SystemStatus.class).keySet()){
			facility.removeParameter(SystemStatus.class, s);
		}
		
		for (String s : facility.getParameterMap(SystemStatus.class).keySet()){
			facility.removeParameter(SystemStatus.class, s);
		}
		
		facility.addParameter(ParameterFacility.UNIQUE_PARAM_KEY, genSystemStatus());
	}

	private SystemStatus genSystemStatus() throws Exception{
		SystemStatus s = new SystemStatus();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		s.businessDate = format.parse(commonProperties.getBusinessDate());
		s.lastProcessDate = format.parse(commonProperties.getLastProcessDate());
		s.processDate = format.parse(commonProperties.getProcessDate());
		s.batchSeq = 0;
	    s.systemStatus = SystemStatusType.N;
		return s;
	}
	
}
