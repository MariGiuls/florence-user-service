package com.example.florence.user.service.repository.aspect.validation;

import com.example.florence.user.service.exception.DBUserException;
import com.example.florence.user.service.repository.model.DBUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("saveUserFieldStrategy")
public class DBValidationSaveUserFieldStrategy implements IDBValidationFieldStrategy{

    @Override
    public void validateField(DBUser user) {
        List<String> errorsField = validateRequiredField(user, user.getClass().getName());
        if (errorsField != null && !errorsField.isEmpty()) throw new DBUserException("Cannot save new user without the required fields: " + errorsField, HttpStatus.BAD_REQUEST);
    }
}
