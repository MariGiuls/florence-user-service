package com.example.florence.user.service.service.aspect.validation;

import com.example.florence.user.service.exception.UserServiceException;
import com.example.florence.user.service.repository.model.DBUser;
import com.example.florence.user.service.repository.service.IUserRepositoryService;
import it.florence.generate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("saveUserFieldStrategy")
public class ValidationSaveUserFieldStrategy implements IValidationFieldStrategy {

    private IUserRepositoryService userRepositoryService;

    @Autowired
    public ValidationSaveUserFieldStrategy(IUserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public void validateField(User user) {
        if (user.getId() != null)
            throw new UserServiceException("Cannot send the id information saving a new user", HttpStatus.BAD_REQUEST);

        List<String> errorsField = validateRequiredField(user, user.getClass().getName());
        if (errorsField != null && !errorsField.isEmpty())
            throw new UserServiceException("Cannot save new user without the required fields: " + errorsField, HttpStatus.BAD_REQUEST);

        DBUser userToFind = DBUser.builder().withFiscalCode(user.getFiscalCode()).build();
        List<DBUser> sameUser = userRepositoryService.findBy(Example.of(userToFind));
        if (sameUser != null && !sameUser.isEmpty())
            throw new UserServiceException("Fiscal code already present in system", HttpStatus.BAD_REQUEST);
    }
}
