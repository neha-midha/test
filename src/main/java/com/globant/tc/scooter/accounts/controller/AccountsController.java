package com.globant.tc.scooter.accounts.controller;

import com.globant.tc.scooter.accounts.common.AccountsConstants;
import com.globant.tc.scooter.accounts.model.*;
import com.globant.tc.scooter.accounts.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @RequestMapping(path = "/createAccount", method = RequestMethod.POST)
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid CreateAccount createAccount) {
        AccountResponse account = accountsService.createAccount(createAccount);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = "/addCreditsToAccount")
    public ResponseEntity<String> addCreditsToAccount(@RequestBody @Valid AddCredits addCredits) {
        accountsService.addCreditsToAccount(addCredits);
        return new ResponseEntity<>(AccountsConstants.SUCCESS, HttpStatus.OK);
    }

    @PostMapping(path = "/associateUserToAccount")
    public ResponseEntity<String> associateUserToAccount(@RequestBody @Valid AssociateUser associateUser) {
        accountsService.associateUserToAccount(associateUser);
        return new ResponseEntity<>(AccountsConstants.SUCCESS, HttpStatus.OK);
    }

    @PostMapping(path = "/linkAccountToMercadoPago")
    public ResponseEntity<String> linkAccountToMercadoPago(@RequestBody @Valid LinkAccountToMercadoPago linkAccountToMercadoPago) {
        accountsService.linkAccountToMercadoPago(linkAccountToMercadoPago);
        return new ResponseEntity<>(AccountsConstants.SUCCESS, HttpStatus.OK);
    }

    @PostMapping(path = "/buyCreditsMercadoPago")
    public ResponseEntity<String> buyCreditsMercadoPago(@RequestBody @Valid BuyCreditsMercadoPago buyCreditsMercadoPago) {
        accountsService.buyCreditsMercadoPago(buyCreditsMercadoPago);
        return new ResponseEntity<>(AccountsConstants.SUCCESS, HttpStatus.OK);
    }
}
