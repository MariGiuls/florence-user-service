package com.example.florence.user.service.service;

import com.example.florence.user.service.exception.UserServiceException;
import com.example.florence.user.service.service.mapper.UserServiceMapper;
import it.florence.generate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service("userServiceCSVImpl")
public class UserServiceCSVImpl implements IUserService {

    private IUserService userServiceImpl;
    private UserServiceMapper mapper;

    @Autowired
    public UserServiceCSVImpl(@Qualifier("userServiceImpl")IUserService userServiceImpl, UserServiceMapper mapper) {
        this.userServiceImpl = userServiceImpl;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return userServiceImpl.save(user);
    }

    @Override
    public User change(User user) {
        return userServiceImpl.change(user);
    }

    @Override
    public void delete(User user) {
        userServiceImpl.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userServiceImpl.findAll();
    }

    @Override
    public User findById(BigDecimal id) {
        return userServiceImpl.findById(id);
    }

    @Override
    public List<User> findBy(User user) {
        return userServiceImpl.findBy(user);
    }

    @Override
    public void uploadUsersCsv(MultipartFile file) {
        Optional.ofNullable(file)
                .orElseThrow(() ->
                        new UserServiceException("csv file not present", HttpStatus.BAD_REQUEST));

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            List<User> users = StreamSupport.stream(csvParser.getRecords().spliterator(), false)
                    .map(data -> mapper.mapperUserFromCDV(data))
                    .toList();

            saveAllTheUser(users);

        } catch (IOException e) {
            String errMsg = MessageFormat.format("Invalid File. Message {0}:", e.getMessage());
            throw new UserServiceException(errMsg, HttpStatus.BAD_REQUEST);
        }
    }

    private void saveAllTheUser(List<User> users) {
        List<String> errorFiscalCode = new ArrayList<>();
        users.forEach(data -> {
            try {
                save(data);
            } catch (UserServiceException ex) {
                errorFiscalCode.add(data.getFiscalCode());
                log.warn("User refused " + data.getFiscalCode() + " because of " + ex.getMessage());
            }
        });
        if (users.size() == errorFiscalCode.size())
            throw new UserServiceException("No User insert insert on florence system", HttpStatus.BAD_REQUEST);
        log.info(errorFiscalCode.isEmpty() ? "All the users added into the florence system" : "Cannot insert this fiscalCodes " + errorFiscalCode);
    }
}
