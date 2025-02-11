package com.example.florence.user.service.service.aspect.validation;

import com.example.florence.user.service.exception.UserServiceException;
import com.example.florence.user.service.repository.service.IUserRepositoryService;
import it.florence.generate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("deleteUserFieldStrategy")
public class ValidationDeleteUserFieldStrategy implements IValidationFieldStrategy {

    private static final String ERROR_MESSAGE = "Cannot perform the delete operation because of ID";

    private IUserRepositoryService userRepositoryService;

    @Autowired
    public ValidationDeleteUserFieldStrategy(IUserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public void validateField(User user) {
        Optional.ofNullable(user)
            .map(User::getId)
            .map(data -> userRepositoryService.findById(data.intValue()))
            .orElseThrow(() -> new UserServiceException(ERROR_MESSAGE, HttpStatus.BAD_REQUEST));
    }
}
