package com.globant.tc.scooter.accounts.service.external.impl;

import com.globant.tc.scooter.accounts.exception.InternalErrorException;
import com.globant.tc.scooter.accounts.model.users.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isValidUserTest() {
        ResponseEntity<User> userResponseEntity = new ResponseEntity<>(new User(), HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(userResponseEntity);
        Assert.assertTrue(userService.isValidUser(1));
    }

    @Test
    public void isValidUserTest_False() {
        ResponseEntity<User> userResponseEntity = new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(userResponseEntity);
        Assert.assertFalse(userService.isValidUser(1));
    }

    @Test(expected = InternalErrorException.class)
    public void isValidUserTest_Exception() {
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenThrow(new RuntimeException());
        userService.isValidUser(1);
    }

}
