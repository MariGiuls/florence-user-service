package com.example.florence_user_service.repository.aspect.validation;

import com.example.florence_user_service.exception.DBUserException;
import com.example.florence_user_service.repository.model.DBUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("deleteUserFieldStrategy")
public class DBValidationDeleteUserFieldStrategy implements IDBValidationFieldStrategy{

    private static final String ERROR_MESSAGE = "Cannot perform the delete operation without the ID user";

    @Override
    public void validateField(DBUser user) {
        Optional.ofNullable(user)
            .map(DBUser::getId)
            .orElseThrow(() -> new DBUserException(ERROR_MESSAGE, HttpStatus.BAD_REQUEST));
    }
}
