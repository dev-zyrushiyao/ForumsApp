package com.codingdojo.ForumsApp.auth;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
	
	//    1 specify instance of user model can be validated using custom validator
    @Override
    public boolean supports(Class<?> clazz) {
        return UserModel.class.equals(clazz);
    }
    
    // 2 - ERRORS Built-in class
    @Override
    public void validate(Object object, Errors errors) {
        UserModel user = (UserModel) object;
        
        //Custom Validations
        //create untitled text file (Directory: src/main/resources) named: "messages.properties" and paste the code below on properites file.
        //#UserValidator - AUTH
        //Match.user.passwordConfirmation=Password and Password Confirmation must match
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match");
        }         
    }
}
