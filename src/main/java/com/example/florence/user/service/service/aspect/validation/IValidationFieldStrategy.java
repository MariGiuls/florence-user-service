package com.example.florence.user.service.service.aspect.validation;

import com.example.florence.user.service.exception.UserServiceException;
import it.florence.generate.model.User;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface IValidationFieldStrategy {

    void validateField(User user);

    default List<String> validateRequiredField(Object object, String objectName) throws UserServiceException {
        List<String> errorMessageValidation = new ArrayList<>();

        if (object == null) {
            errorMessageValidation.add(objectName);
            return errorMessageValidation;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                String fieldName = field.getName();
                if (!fieldName.equalsIgnoreCase("id")) {
                    if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
                        errorMessageValidation.add(objectName + "_" + fieldName);
                    }
                    if (value != null && !(value.getClass().isPrimitive() || value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof Character)) {
                        errorMessageValidation.addAll(validateRequiredField(value, objectName + "_" + fieldName));
                    }
                }
            } catch (IllegalAccessException e) {
                throw new UserServiceException("Error accessing field: " + field.getName(), HttpStatus.BAD_REQUEST);
            }
        }

        return errorMessageValidation;
    }

}
