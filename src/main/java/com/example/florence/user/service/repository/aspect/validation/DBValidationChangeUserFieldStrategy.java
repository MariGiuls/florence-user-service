package com.example.florence.user.service.repository.aspect.validation;

import com.example.florence.user.service.exception.DBUserException;
import com.example.florence.user.service.repository.model.DBUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("changeUserFieldStrategy")
public class DBValidationChangeUserFieldStrategy implements IDBValidationFieldStrategy{

    @Override
    public void validateField(DBUser user) {
        List<String> errorsField = validateRequiredField(user, user.getClass().getName());

        if (errorsField == null) {
            errorsField = new ArrayList<>();
        }
        if (user.getId() == null) {
            errorsField.add("id");
        }

        if (!errorsField.isEmpty()) {
            throw new DBUserException("Cannot change user without the required fields: " + String.join(", ", errorsField), HttpStatus.BAD_REQUEST);
        }
    }
}
