package com.globant.tc.scooter.accounts.service.external.impl;

import com.globant.tc.scooter.accounts.service.external.UserService;
import org.springframework.stereotype.Service;

@Service
public class MockUserServiceImpl implements UserService {

    @Override
    public boolean isValidUser(Integer userId) {
        return true;
    }
}
