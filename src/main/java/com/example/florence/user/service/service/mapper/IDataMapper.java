package com.example.florence.user.service.service.mapper;

import java.math.BigDecimal;
import java.util.Optional;

public interface IDataMapper {

    String REGEX = "^\\d+$";

    default BigDecimal retrieveId(Integer id) {
        return Optional.ofNullable(id)
                .map(BigDecimal::valueOf)
                .orElse(null);
    }

    default Integer retrieveId(BigDecimal id) {
        return Optional.ofNullable(id)
                .map(BigDecimal::intValue)
                .orElse(null);
    }

    default BigDecimal retrieveId(String id) {
        return Optional.ofNullable(id)
                .filter(data -> !data.isEmpty())
                .filter(data -> data.matches(REGEX))
                .map(BigDecimal::new)
                .orElse(null);
    }
}
