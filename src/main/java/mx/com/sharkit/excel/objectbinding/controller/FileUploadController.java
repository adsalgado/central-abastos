package mx.com.sharkit.excel.objectbinding.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mx.com.sharkit.excel.objectbinding.domain.Base;
import mx.com.sharkit.excel.objectbinding.domain.ExcelFile;
import mx.com.sharkit.excel.objectbinding.domain.ProductoCargaDTO;
import mx.com.sharkit.excel.objectbinding.handler.FileUploadTemplateHandler;
import mx.com.sharkit.excel.objectbinding.utils.ExcelUtilityParallelProcessor;
import mx.com.sharkit.excel.objectbinding.validator.ProductoCargaValidator;
import mx.com.sharkit.excel.objectbinding.validator.UserValidator;

@RestController
@RequestMapping("/api")
public class FileUploadController {

	@Autowired
	FileUploadTemplateHandler fileUploadTemplateHandler;

	@Autowired
	ExcelUtilityParallelProcessor excelUtilityParallelProcessor;

	@Autowired
	UserValidator userValidator;
	
	@Autowired
	ProductoCargaValidator productoCargaValidator;

	private final static Logger log = LoggerFactory.getLogger(FileUploadController.class);

	public FileUploadTemplateHandler getFileUploadTemplateHandler() {
		return fileUploadTemplateHandler;
	}

	public void setFileUploadTemplateHandler(FileUploadTemplateHandler fileUploadTemplateHandler) {
		this.fileUploadTemplateHandler = fileUploadTemplateHandler;
	}

	@GetMapping(value = "/files/upload")
	public @ResponseBody String provideUploadInfo() {
		return "You can upload a file by posting to this same URL.";
	}

	// @RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				List<Base> userList;
				userList = convertXl(file);

				return "You successfully uploaded " + userList + " into " + name + "-uploaded !";

			} catch (Exception e) {
				e.printStackTrace();
				return "You failed to upload " + name + " => " + e.getMessage();

			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	@PostMapping(value = "/files/upload", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody <T extends Base> List<T> getJson(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		try {
			return convertXl(file);
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	@PostMapping(value = "/getXml", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody <T extends Base> List<T> getXml(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		try {
			return convertXl(file);
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	private <T extends Base> List<T> convertXl(MultipartFile file) throws Exception {
		List<T> list = null;
		if (!file.isEmpty()) {

			ExecutorService executorService = Executors
					.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 16);

			try {
				Workbook workbook;
				if (file.getOriginalFilename().endsWith("xls")) {
					workbook = new HSSFWorkbook(file.getInputStream());
				} else if (file.getOriginalFilename().endsWith("xlsx")) {
					workbook = new XSSFWorkbook(file.getInputStream());
				} else {
					return list;
				}

				ExcelFile excelFile = new ExcelFile(workbook.getSheetAt(0), true, 2000,
						fileUploadTemplateHandler.getProductoCargaTemplate(), ProductoCargaDTO.class, productoCargaValidator);

				log.info("Parallel Processing Start time ~~~~~~~~~~~~~~~");
				list = excelUtilityParallelProcessor.readExcelInParallel(excelFile);

				return list;

			} catch (Exception e) {
				throw e;
			} finally {
				try {
					executorService.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			throw new Exception("You failed to upload   because the file was empty.");
		}
	}

}
