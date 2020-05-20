package mx.com.sharkit.excel.objectbinding.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import mx.com.sharkit.excel.objectbinding.domain.User;

@Component
public class UserValidator extends LocalValidatorFactoryBean implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		// Name is Required value
		if (!StringUtils.hasText(user.getName())) {
			errors.rejectValue("name", "user.name.required", "User Name is Required");
			// to check multiple Errors for same field
			errors.rejectValue("name", "user.name.required", "User Name is Required1");
		}

	}

}
