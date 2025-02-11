package com.example.florence.user.service.service.mapper;

import com.example.florence.user.service.repository.model.DBUser;
import it.florence.generate.model.User;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceMapper implements IDataMapper{

    private final AddressServiceMapper addressServiceMapper;

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

    public User mapperUserFromCDV(CSVRecord record) {
        User user = new User();
        //TODO implement the logic for the change operation from csv
        //user.setId(retrieveId(record.get("id")));
        user.setName(record.get("name"));
        user.setSurname(record.get("surname"));
        user.setEmail(record.get("email"));
        user.setFiscalCode(record.get("fiscalCode"));
        user.setAddress(addressServiceMapper.mapperAddressFromCSV(record));
        return user;
    }
}
