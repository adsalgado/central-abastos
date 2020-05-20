package mx.com.sharkit.excel.objectbinding.utils;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.sharkit.excel.objectbinding.domain.Base;
import mx.com.sharkit.excel.objectbinding.domain.ExcelFile;
import mx.com.sharkit.excel.objectbinding.domain.Limit;

public class ExcelUtilityParallelReader implements Callable<List<? extends Base>> {

	ExcelFile excelFile;

	Limit limit;

	boolean isHeaderThread;

	private final static Logger log = LoggerFactory.getLogger(ExcelUtilityParallelReader.class);

	public ExcelUtilityParallelReader(ExcelFile excelFile, Limit limit, boolean isHeaderThread) {
		super();
		this.excelFile = excelFile;
		this.limit = limit;
		this.isHeaderThread = isHeaderThread;
	}

	@Override
	public List<? extends Base> call() throws Exception {
		log.info("Running Thread for set starting : " + limit.getStartRow());
		return ExcelUtility.readXlFile(excelFile.getSheet(), excelFile.getFileTemplate(), excelFile.getClazz(),
				isHeaderThread, limit.getStartRow(), limit.getEndRow(), excelFile.getValidator());

	}

}
