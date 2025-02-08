package com.example.florence_user_service.repository.aspect.validation;

import com.example.florence_user_service.exception.DBUserException;
import com.example.florence_user_service.repository.model.DBUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("findUserByFieldStrategy")
public class DBValidationFindByUserFieldStrategy implements IDBValidationFieldStrategy{

    @Override
    public void validateField(DBUser user) {
        List<String> errorsField = validateForFind(user);
        if (errorsField != null && !errorsField.isEmpty())
            throw new DBUserException("Cannot find user because of: " + errorsField + " should be populated in the user", HttpStatus.BAD_REQUEST);
    }

    public List<String> validateForFind(DBUser user) throws DBUserException {
        List<String> errorsField = new ArrayList<>();
        if (user.getId() == null && user.getName() == null && user.getSurname() == null && user.getEmail() == null && user.getFiscalCode() == null && user.getAddress() == null) {
            errorsField.add("At least one field ");
            return errorsField;
        }

        if (user.getAddress() != null) {
            List<String> addressErrors = validateRequiredField(user.getAddress(), user.getAddress().getClass().getName());
            if (addressErrors != null && !addressErrors.isEmpty()) {
                errorsField.addAll(addressErrors);
            }
        }

        return errorsField;
    }
}
