package com.example.florence.user.service.service.mapper;

import com.example.florence.user.service.repository.model.DBAddress;
import it.florence.generate.model.Address;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressServiceMapper implements IDataMapper{

    public DBAddress mapperToDB(Address address) {
        return Optional.ofNullable(address)
                .map(data -> DBAddress.builder()
                        .withId(retrieveId(data.getId()))
                        .withCity(data.getCity())
                        .withStreet(data.getStreet())
                        .withStreetNumber(data.getStreetNumber())
                        .withCap(data.getCap())
                        .withProvince(data.getProvince())
                        .withCountry(data.getCountry())
                        .build())
                .orElse(null);
    }

    public Address mapperToWeb(DBAddress address) {
        return Optional.ofNullable(address)
                .map(data -> {
                    Address dataMapped = new Address();
                    dataMapped.setId(retrieveId(data.getId()));
                    dataMapped.setCity(data.getCity());
                    dataMapped.setStreet(data.getStreet());
                    dataMapped.setStreetNumber(data.getStreetNumber());
                    dataMapped.setCap(data.getCap());
                    dataMapped.setProvince(data.getProvince());
                    dataMapped.setCountry(data.getCountry());
                    return dataMapped;
                })
                .orElse(null);
    }

    public DBAddress mapperChangeAddressForDB(Address address, DBAddress dbAddress) {
        return Optional.ofNullable(address)
                .map(data -> DBAddress.builder()
                        .withId(retrieveId(data.getId()))
                        .withCity(data.getCity() != null ? data.getCity() : dbAddress.getCity())
                        .withStreet(data.getStreet() != null ? data.getStreet() : dbAddress.getStreet())
                        .withStreetNumber(data.getStreetNumber() != null ? data.getStreetNumber() : dbAddress.getStreetNumber())
                        .withCap(data.getCap() != null ? data.getCap() : dbAddress.getCap())
                        .withProvince(data.getProvince() != null ? data.getProvince() : dbAddress.getProvince())
                        .withCountry(data.getCountry() != null ? data.getCountry() : dbAddress.getCountry())
                        .build())
                .orElse(dbAddress);
    }

    public Address mapperAddressFromCSV(CSVRecord record) {
        Address address = new Address();
        //TODO implement the logic for the change operation from csv
        //address.setId(retrieveId(record.get("address_id")));
        address.setCity(record.get("address_city"));
        address.setStreet(record.get("address_street"));
        address.setStreetNumber(record.get("address_streetNumber"));
        address.setCap(record.get("address_fiscalCode"));
        address.setProvince(record.get("address_province"));
        address.setCountry(record.get("address_country"));
        return address;
    }
}
