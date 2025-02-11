package com.example.florence.user.service.service.aspect;

import com.example.florence.user.service.exception.UserServiceException;
import com.example.florence.user.service.service.model.ValidationMethodEnum;
import com.example.florence.user.service.service.aspect.validation.IValidationFieldStrategy;
import it.florence.generate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class UserServiceAspect {

    private final StringBuilder errorMessage = new StringBuilder("Method throw exception: ");

    private final Map<ValidationMethodEnum, IValidationFieldStrategy> validationStrategy;

    @Autowired
    public UserServiceAspect(Map<ValidationMethodEnum, IValidationFieldStrategy> validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    private HttpStatus statusError = HttpStatus.INTERNAL_SERVER_ERROR;

    @Pointcut("execution(* com.example.florence.user.service.service.UserServiceImpl.*(..))")
    public void userServiceMethods() {}

    @Before("userServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        ValidationMethodEnum methodEnum = ValidationMethodEnum.fromValue(joinPoint.getSignature().getName());
        log.info("SERIVCE - method: " + methodEnum + " with arguments: " + Arrays.toString(joinPoint.getArgs()));
        Object firstArg = joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null;

        if (firstArg instanceof String) {
            validateString(firstArg);
        } else if (firstArg instanceof User) {
            validationStrategy.get(methodEnum).validateField((User) firstArg);
        }
    }

    @AfterReturning(pointcut = "userServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Successed execution of method: " + joinPoint.getSignature().getName());
        Optional.ofNullable(result)
            .ifPresent(data -> log.info("User DB response: " + data));
    }

    @AfterThrowing(pointcut = "userServiceMethods()", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex) throws UserServiceException {
        errorMessage.append(joinPoint.getSignature().getName())
                .append(" - message: ")
                .append(ex.getMessage());
        throw new UserServiceException(errorMessage.toString(), statusError);
    }

    private void validateString(Object arg) {
        Optional.ofNullable(arg)
            .map(data -> (String) data)
            .filter(data -> !data.isEmpty())
            .orElseThrow(() -> new UserServiceException("Required parameter String found", HttpStatus.BAD_REQUEST));
    }

    private void validate(Object arg) {
        Optional.ofNullable(arg)
                .orElseThrow(() -> new UserServiceException("Required parameter not found", HttpStatus.BAD_REQUEST));
    }
}
