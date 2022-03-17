package com.globant.tc.scooter.accounts.service.external.impl;

import com.globant.tc.scooter.accounts.exception.AccountsErrorCode;
import com.globant.tc.scooter.accounts.exception.InternalErrorException;
import com.globant.tc.scooter.accounts.model.users.User;
import com.globant.tc.scooter.accounts.service.external.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.user.url}")
    private String url;

    @Override
    public boolean isValidUser(Integer userId) {
        try {
            ResponseEntity<User> responseEntity = restTemplate.getForEntity(url + "/findUserById/" + userId, User.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                User user = responseEntity.getBody();
                if (Objects.nonNull(user)) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new InternalErrorException(AccountsErrorCode.INTERNAL_SERVER_ERROR);
        }
        return false;
    }
}
