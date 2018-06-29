package net.engining.sccc.batch.sccc5902;

import java.io.File;
import java.io.FileWriter;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import net.engining.pg.batch.entity.model.PgFileItem;

@Service
@StepScope
public class Sccc5902P02CreateXML implements ItemProcessor<PgFileItem, Object> {
	@Override
	public Object process(PgFileItem item) throws Exception {
		FileWriter writerXML = null;// 声明写XML的对象
		File file = new File(item.getFilename());// 获得文件
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		// 生成XML文件
		try {
			writerXML = new FileWriter(file);
			writerXML.write(item.getLine());
		}finally {
			writerXML.close();
		}

		return null;
	}

}
