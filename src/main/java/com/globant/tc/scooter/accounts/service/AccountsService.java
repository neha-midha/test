package com.globant.tc.scooter.accounts.service;

import com.globant.tc.scooter.accounts.model.*;

public interface AccountsService {

    /**
     * create account for userId
     * @param createAccount
     * @return
     */
    AccountResponse createAccount(CreateAccount createAccount);

    /**
     * add credits for accountId
     * @param addCredits
     */
    void addCreditsToAccount(AddCredits addCredits);

    /**
     * associate user to account
     * @param associateUser
     */
    void associateUserToAccount(AssociateUser associateUser);

    /**
     * link accountId with mercadoPagoId
     * @param linkAccountToMercadoPago
     */
    void linkAccountToMercadoPago(LinkAccountToMercadoPago linkAccountToMercadoPago);

    /**
     * Buy MercadoPago credits for account
     * @param buyCreditsMercadoPago
     */
    void buyCreditsMercadoPago(BuyCreditsMercadoPago buyCreditsMercadoPago);
}
