package com.globant.tc.scooter.accounts.service.external;

public interface UserService {

    /**
     * calls user management service to validate userid
     * @param userId
     * @return
     */
    boolean isValidUser(Integer userId);
}
