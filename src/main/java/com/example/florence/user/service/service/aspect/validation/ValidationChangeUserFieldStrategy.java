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
import java.util.Optional;

@Component("changeUserFieldStrategy")
public class ValidationChangeUserFieldStrategy implements IValidationFieldStrategy {

    private IUserRepositoryService userRepositoryService;

    @Autowired
    public ValidationChangeUserFieldStrategy(IUserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public void validateField(User user) {
        if (user.getName() == null && user.getSurname() == null && user.getEmail() == null && user.getFiscalCode() == null && user.getAddress() == null) {
            throw new UserServiceException("Cannot change user without an information to change", HttpStatus.BAD_REQUEST);
        }
        if (user.getId() == null) {
            throw new UserServiceException("Cannot change user without the ID information", HttpStatus.BAD_REQUEST);
        }
        DBUser userToChange = userRepositoryService.findById(user.getId().intValue())
                .orElseThrow(() -> new UserServiceException("Not user found to change", HttpStatus.BAD_REQUEST));
        if (user.getFiscalCode() != null && !user.getFiscalCode().equalsIgnoreCase(userToChange.getFiscalCode())) {
            DBUser userToFind = DBUser.builder().withFiscalCode(user.getFiscalCode()).build();
            List<DBUser> sameUser = userRepositoryService.findBy(Example.of(userToFind));
            if (sameUser != null && !sameUser.isEmpty())
                throw new UserServiceException("Fiscal code already present in system", HttpStatus.BAD_REQUEST);
        }
    }
}
