package com.example.florence.user.service.service.aspect.configuration;

import com.example.florence.user.service.service.model.ValidationMethodEnum;
import com.example.florence.user.service.service.aspect.validation.IValidationFieldStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserConfiguration {

    @Bean
    public Map<ValidationMethodEnum, IValidationFieldStrategy> validationStrategy(IValidationFieldStrategy changeUserFieldStrategy,
                                                                                    IValidationFieldStrategy saveUserFieldStrategy,
                                                                                    IValidationFieldStrategy findUserByFieldStrategy,
                                                                                    IValidationFieldStrategy deleteUserFieldStrategy) {
        Map<ValidationMethodEnum, IValidationFieldStrategy> map = new HashMap<>();
        map.put(ValidationMethodEnum.CHANGE, changeUserFieldStrategy);
        map.put(ValidationMethodEnum.SAVE, saveUserFieldStrategy);
        map.put(ValidationMethodEnum.FIND_BY, findUserByFieldStrategy);
        map.put(ValidationMethodEnum.DELETE, deleteUserFieldStrategy);
        return map;
    }
}
