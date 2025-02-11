package com.example.florence.user.service.service.mapper;

import com.example.florence.user.service.repository.model.DBUser;
import it.florence.generate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class UserServiceMapper {

    private AddressServiceMapper addressServiceMapper;

    @Autowired
    public UserServiceMapper(AddressServiceMapper addressServiceMapper) {
        this.addressServiceMapper = addressServiceMapper;
    }

    public DBUser mapperToDB(User user) {
        return Optional.ofNullable(user)
                .map(data -> DBUser.builder()
                        .withId(retrieveId(data.getId()))
                        .withName(data.getName())
                        .withSurname(data.getSurname())
                        .withEmail(data.getEmail())
                        .withFiscalCode(data.getFiscalCode())
                        .withAddress(addressServiceMapper.mapperToDB(data.getAddress()))
                        .build())
                .orElse(null);
    }

    public User mapperToWeb(DBUser user) {
        return Optional.ofNullable(user)
                .map(data -> {
                    User dataMapped = new User();
                    dataMapped.setId(retrieveId(data.getId()));
                    dataMapped.setName(data.getName());
                    dataMapped.setSurname(data.getSurname());
                    dataMapped.setEmail(data.getEmail());
                    dataMapped.setFiscalCode(data.getFiscalCode());
                    dataMapped.setAddress(addressServiceMapper.mapperToWeb(data.getAddress()));
                    return dataMapped;
                })
                .orElse(null);
    }

    public DBUser mapperChangeUserForDB(User user, DBUser dbUser) {
        return Optional.ofNullable(user)
                .map(data -> DBUser.builder()
                        .withId(retrieveId(data.getId()))
                        .withName(data.getName() != null ? data.getName() : dbUser.getName())
                        .withSurname(data.getSurname() != null ? data.getSurname() : dbUser.getSurname())
                        .withEmail(data.getEmail() != null ? data.getEmail() : dbUser.getEmail())
                        .withFiscalCode(data.getFiscalCode() != null ? data.getFiscalCode() : dbUser.getFiscalCode())
                        .withAddress(addressServiceMapper.mapperChangeAddressForDB(data.getAddress(), dbUser.getAddress()))
                        .build())
                .orElse(null);
    }

    private BigDecimal retrieveId(Integer id) {
        return Optional.ofNullable(id)
                .map(BigDecimal::valueOf)
                .orElse(null);
    }

    private Integer retrieveId(BigDecimal id) {
        return Optional.ofNullable(id)
                .map(BigDecimal::intValue)
                .orElse(null);
    }
}
