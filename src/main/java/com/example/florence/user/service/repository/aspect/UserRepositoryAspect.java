package com.example.florence.user.service.repository.aspect;

import com.example.florence.user.service.exception.DBUserException;
import com.example.florence.user.service.repository.aspect.validation.IDBValidationFieldStrategy;
import com.example.florence.user.service.repository.model.DBUser;
import com.example.florence.user.service.repository.model.ValidationMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

@Slf4j
@Aspect
@Component
public class UserRepositoryAspect {

    private final StringBuilder errorMessage = new StringBuilder("Method throw exception: ");

    private final Map<ValidationMethodEnum, IDBValidationFieldStrategy> validationFieldStrategy;

    @Autowired
    public UserRepositoryAspect(Map<ValidationMethodEnum, IDBValidationFieldStrategy> validationFieldStrategy) {
        this.validationFieldStrategy = validationFieldStrategy;
    }

    private HttpStatus statusError = HttpStatus.INTERNAL_SERVER_ERROR;

    @Pointcut("execution(* com.example.florence_user_service.repository.service.UserRepositoryServiceImpl.*(com.example.florence_user_service.repository.model.DBUser, ..))")
    public void userServiceMethods() {}

    @Pointcut("execution(* com.example.florence_user_service.repository.service.UserRepositoryServiceImpl.*(..))")
    public void userServiceMethodsWithString() {}

    @Before("userServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("method: " + joinPoint.getSignature().getName() + " with arguments: " + Arrays.toString(joinPoint.getArgs()));

        Object firstArg = joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null;

        if (firstArg instanceof String) {
            validateString(joinPoint);
        } else if (firstArg instanceof DBUser) {
            ValidationMethodEnum methodEnum = ValidationMethodEnum.valueOf(joinPoint.getSignature().getName());
            validationFieldStrategy.get(methodEnum).validateField((DBUser) firstArg);
        }
    }

    @AfterReturning(pointcut = "userServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Succeded execution of method: " + joinPoint.getSignature().getName());
        Optional.ofNullable(result)
            .ifPresent(data -> log.info("User DB response: " + data));
    }

    @AfterThrowing(pointcut = "execution(* it.intessera.app.service.user.service.repoconnector.UserServiceConnectorImpl.*(..))", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex) throws DBUserException {
        errorMessage.append(joinPoint.getSignature().getName());
        populateSpecificExceptionData(ex);
        throw new DBUserException(errorMessage.toString(), statusError);
    }

    private void populateSpecificExceptionData(Exception ex) {
        if (ex instanceof SQLException sqlEx) {
            errorMessage.append(", SQLException - ");

            if (sqlEx.getMessage().contains("Duplicate entry") || sqlEx.getMessage().contains("foreign key constraint")) {
                statusError = HttpStatus.BAD_REQUEST;
                errorMessage.append("Constraint violated with message: ").append(sqlEx.getMessage());
                //throw new DBUserException("Errore di vincolo nel metodo " + joinPoint.getSignature().getName() + ": " + sqlEx.getMessage(), HttpStatus.BAD_REQUEST);
            } else {
                statusError = HttpStatus.INTERNAL_SERVER_ERROR;
                errorMessage.append("Connection DB error with message: ").append(sqlEx.getMessage());
                //throw new DBUserException("Errore di connessione al DB nel metodo " + joinPoint.getSignature().getName() + ": " + sqlEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            errorMessage.append("Generic message: ").append(ex.getMessage());
            //throw new DBUserException("Errore nel metodo " + joinPoint.getSignature().getName() + ": " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateString(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(Objects::nonNull)
                .filter(data -> data instanceof String)
                .map(data -> (String) data)
                .filter(data -> !data.isEmpty())
                .findFirst()
                .orElseThrow(() -> new DBUserException("Required parameter String found", HttpStatus.BAD_REQUEST));
    }
}
