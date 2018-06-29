package net.engining.sccc.init.param.sccc;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;

import net.engining.pcx.cc.param.model.Subject;
import net.engining.pcx.cc.param.model.enums.AmtDbCrFlag;
import net.engining.pcx.cc.param.model.enums.BalDbCrFlag;
import net.engining.pcx.cc.param.model.enums.SubjectType;
import net.engining.pg.parameter.ParameterFacility;
import net.engining.pg.support.init.ParameterInitializer;

@Service
@Transactional
public class ApSubjectInit implements ParameterInitializer{
	
	@Autowired
	private ParameterFacility facility;
	
	@Value("classpath:/data/subject.csv")
	private Resource data;

	@Override
	@Transactional
	public void init() throws Exception {
		for(String s : facility.getParameterMap(Subject.class).keySet()){
			facility.removeParameter(Subject.class, s);
		}
		
		List<String> lines = IOUtils.readLines(data.getInputStream(),Charsets.UTF_8);
		
		for(String line : lines){
			Subject subject = new Subject();
			
			String fields[] = StringUtils.splitPreserveAllTokens(line,",");
			int i = 0;
			subject.subjectCd = fields[i++];
			subject.name = fields[i++];
			subject.description = fields[i++];
			subject.balDbCrFlag = BalDbCrFlag.valueOf(fields[i++]);
			subject.amtDbCrFlag = AmtDbCrFlag.valueOf(fields[i++]);
			subject.type = SubjectType.valueOf(fields[i++]);
			subject.currCd = fields[i++];
			subject.parentSubjectCd = fields[i++];
			subject.enable = Boolean.valueOf(fields[i++]);
			subject.subjectCode = fields[i++];
			
			subject.isOverdraft = Boolean.valueOf(fields[i++]);
			subject.subjectHierarchy = fields[i++];
			subject.isLast = Boolean.valueOf(fields[i++]);
			
			String[] str = fields[i++].split("/",0);
			List<String> list = new ArrayList<String>();
			for(String s: str){
				list.add(s);
			}
			
			subject.auxiliaryAssist = list;
			subject.isAccount = Boolean.valueOf(fields[i++]);
			
			facility.addParameter(subject.subjectCd,subject);
			
		}
		
	}
}
