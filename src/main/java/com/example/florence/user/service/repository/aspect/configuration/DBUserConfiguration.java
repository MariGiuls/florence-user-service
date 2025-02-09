package com.example.florence.user.service.repository.aspect.configuration;

import com.example.florence.user.service.repository.aspect.validation.IDBValidationFieldStrategy;
import com.example.florence.user.service.repository.model.ValidationMethodEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DBUserConfiguration {

    @Bean
    public Map<ValidationMethodEnum, IDBValidationFieldStrategy> validationFieldStrategy(IDBValidationFieldStrategy changeUserFieldStrategy,
                                                IDBValidationFieldStrategy saveUserFieldStrategy,
                                                IDBValidationFieldStrategy findUserByFieldStrategy,
                                                IDBValidationFieldStrategy deleteUserFieldStrategy) {
        Map<ValidationMethodEnum, IDBValidationFieldStrategy> map = new HashMap<>();
        map.put(ValidationMethodEnum.CHANGE, changeUserFieldStrategy);
        map.put(ValidationMethodEnum.SAVE, saveUserFieldStrategy);
        map.put(ValidationMethodEnum.FIND_BY, findUserByFieldStrategy);
        map.put(ValidationMethodEnum.DELETE, deleteUserFieldStrategy);
        return map;
    }
}
