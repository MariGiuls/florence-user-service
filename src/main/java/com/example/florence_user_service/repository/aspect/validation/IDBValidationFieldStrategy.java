package com.example.florence_user_service.repository.aspect.validation;

import com.example.florence_user_service.exception.DBUserException;
import com.example.florence_user_service.repository.model.DBUser;
import jakarta.persistence.Column;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.List;

public interface IDBValidationFieldStrategy {

    void validateField(DBUser user);

    default List<String> validateRequiredField(Object object, String objectName) throws DBUserException {
        List<String> errorMessageValidation = null;
        if (object == null) {
            //throw new DBUserException("Object cannot be null", HttpStatus.BAD_REQUEST);
            //errorMessageValidation = "Object" + objectName + "cannot be null";
            errorMessageValidation.add(objectName);
            return errorMessageValidation;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && !columnAnnotation.nullable()) {
                    if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                        //throw new DBUserException(field.getName() + " cannot be null or empty", HttpStatus.BAD_REQUEST);
                        //errorMessageValidation = "Field " + field.getName() + " inside the Object " + objectName + " cannot be null or empty";
                        errorMessageValidation.add(objectName + "_" + field.getName());
                    }
                }
                if (value != null && !value.getClass().isPrimitive() && !(value instanceof String)) {
                    errorMessageValidation = validateRequiredField(value, value.getClass().getName());
                }
            } catch (IllegalAccessException e) {
                throw new DBUserException("Error accessing field: " + field.getName(), HttpStatus.BAD_REQUEST);
            }
        }
        return errorMessageValidation;
    }
}
