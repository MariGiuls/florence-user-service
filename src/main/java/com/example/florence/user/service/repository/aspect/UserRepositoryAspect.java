package com.example.florence.user.service.repository.aspect;

import com.example.florence.user.service.exception.DBUserException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

@Slf4j
@Aspect
@Component
public class UserRepositoryAspect {

    private final StringBuilder errorMessage = new StringBuilder("Method throw exception: ");

    private HttpStatus statusError = HttpStatus.BAD_REQUEST;

    @Pointcut("execution(* com.example.florence.user.service.repository.service.UserRepositoryServiceImpl.*(..))")
    public void userServiceMethods() {}

    @Before("userServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("method: " + joinPoint.getSignature().getName() + " with arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "userServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Succeded execution of method: " + joinPoint.getSignature().getName());
        Optional.ofNullable(result)
            .ifPresentOrElse(data -> log.info("User DB response: " + data), () -> log.info("Not user founded"));
    }

    @AfterThrowing(pointcut = "userServiceMethods()", throwing = "ex")
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
            } else {
                statusError = HttpStatus.INTERNAL_SERVER_ERROR;
                errorMessage.append("Connection DB error with message: ").append(sqlEx.getMessage());
            }
        } else {
            errorMessage.append("Generic message: ").append(ex.getMessage());
        }
    }
}
