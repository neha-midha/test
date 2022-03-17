package com.globant.tc.scooter.accounts.service.external.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MockUserServiceImplTest {

    @InjectMocks
    private MockUserServiceImpl mockUserService;


    @Test
    public void isValidUserTest() {
        Assert.assertTrue(mockUserService.isValidUser(1));
    }
}
