package mx.com.sharkit.excel.objectbinding.handler;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FileUploadTemplateHandler {

	FileTemplate userfileTemplate;
	FileTemplate productoCargaTemplate;

	public FileUploadTemplateHandler() {
		initUserFileTemplate();
		initProductoCargaTemplate();
	}

	private void initProductoCargaTemplate() {
		ObjectMapper mapper = new ObjectMapper();

		try {

			ClassLoader cl = this.getClass().getClassLoader();
			InputStream inputStream = cl.getResourceAsStream("templates/file/productoCargaTemplate.json");

			// read from file, convert it to class
			productoCargaTemplate = mapper.readValue(inputStream, FileTemplate.class);

			// display to console
			System.out.println(productoCargaTemplate);

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private void initUserFileTemplate() {
		ObjectMapper mapper = new ObjectMapper();

		try {

			ClassLoader cl = this.getClass().getClassLoader();
			InputStream inputStream = cl.getResourceAsStream("templates/file/fileTemplate.json");

			// read from file, convert it to class
			userfileTemplate = mapper.readValue(inputStream, FileTemplate.class);

			// display to console
			System.out.println(userfileTemplate);

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public FileTemplate getUserfileTemplate() {
		return userfileTemplate;
	}

	public void setUserfileTemplate(FileTemplate userfileTemplate) {
		this.userfileTemplate = userfileTemplate;
	}

	public FileTemplate getProductoCargaTemplate() {
		return productoCargaTemplate;
	}

	public void setProductoCargaTemplate(FileTemplate productoCargaTemplate) {
		this.productoCargaTemplate = productoCargaTemplate;
	}

}
