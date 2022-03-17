package com.globant.tc.scooter.accounts.service.impl;

import com.globant.tc.scooter.accounts.entity.AccountEntity;
import com.globant.tc.scooter.accounts.entity.AccountUserMappingEntity;
import com.globant.tc.scooter.accounts.entity.AccountUserMappingId;
import com.globant.tc.scooter.accounts.exception.AccountsErrorCode;
import com.globant.tc.scooter.accounts.exception.InvalidInputException;
import com.globant.tc.scooter.accounts.exception.NotFoundException;
import com.globant.tc.scooter.accounts.model.*;
import com.globant.tc.scooter.accounts.model.mercadoPago.BuyCredits;
import com.globant.tc.scooter.accounts.repository.AccountRepository;
import com.globant.tc.scooter.accounts.repository.AccountUserMappingRepository;
import com.globant.tc.scooter.accounts.service.AccountsService;
import com.globant.tc.scooter.accounts.service.external.MercadoPagoService;
import com.globant.tc.scooter.accounts.service.external.UserService;
import com.globant.tc.scooter.accounts.service.external.impl.MercadoPagoServiceImpl;
import com.globant.tc.scooter.accounts.service.external.impl.MockMercadoPagoServiceImpl;
import com.globant.tc.scooter.accounts.service.external.impl.MockUserServiceImpl;
import com.globant.tc.scooter.accounts.service.external.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountsServiceImpl implements AccountsService {

    private UserService userService;
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountUserMappingRepository accountUserMappingRepository;

    @Autowired
    private ApplicationContext context;

    @Value("${isUserServiceEnabled}")
    private boolean isUserServiceEnabled;

    @Value("${isMercadoPagoServiceEnabled}")
    private boolean isMercadoPagoServiceEnabled;

    @PostConstruct
    public void init() {
        if (isUserServiceEnabled) {
            userService = context.getBean(UserServiceImpl.class);
        } else {
            userService = context.getBean(MockUserServiceImpl.class);
        }
        if (isMercadoPagoServiceEnabled) {
            mercadoPagoService = context.getBean(MercadoPagoServiceImpl.class);
        } else {
            mercadoPagoService = context.getBean(MockMercadoPagoServiceImpl.class);
        }
    }

    @Transactional
    @Override
    public AccountResponse createAccount(CreateAccount createAccount) {
        if (userService.isValidUser(createAccount.getUserId())) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setBalance(
                    Objects.isNull(createAccount.getAccount().getBalance()) ? 0.0 : createAccount.getAccount().getBalance());
            accountEntity.setActive(createAccount.getAccount().getActive());
            accountEntity = accountRepository.save(accountEntity);
            AccountUserMappingId accountUserMappingId = new AccountUserMappingId();
            accountUserMappingId.setAccountId(accountEntity.getId());
            accountUserMappingId.setUserId(createAccount.getUserId());
            AccountUserMappingEntity accountUserMappingEntity = new AccountUserMappingEntity(accountUserMappingId);
            accountUserMappingRepository.save(accountUserMappingEntity);
            return new AccountResponse().accountId(accountEntity.getId());
        } else {
            throw new NotFoundException(AccountsErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public void addCreditsToAccount(AddCredits addCredits) {
        Optional<AccountEntity> optionalAccountEntity =
                Optional.ofNullable(accountRepository.findByIdAndIsActive(addCredits.getAccountId(), true));
        if (optionalAccountEntity.isPresent()) {
            AccountEntity accountEntity = optionalAccountEntity.get();
            accountEntity.setBalance(accountEntity.getBalance() + addCredits.getCredits());
            accountRepository.save(accountEntity);
        } else {
            throw new NotFoundException(AccountsErrorCode.ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public void associateUserToAccount(AssociateUser associateUser) {
        if (userService.isValidUser(associateUser.getUserId())) {
            Optional<AccountEntity> optionalAccountEntity =
                    Optional.ofNullable(accountRepository.findByIdAndIsActive(associateUser.getAccountId(), true));
            if (optionalAccountEntity.isPresent()) {
                AccountUserMappingId mappingId = new AccountUserMappingId();
                mappingId.setUserId(associateUser.getUserId());
                mappingId.setAccountId(associateUser.getAccountId());
                Optional<AccountUserMappingEntity> optionalMappingEntity = accountUserMappingRepository.findById(mappingId);
                if (optionalMappingEntity.isPresent()) {
                    throw new InvalidInputException(AccountsErrorCode.INVALID_INPUT, "User already associated to account");
                } else {
                    AccountUserMappingEntity mappingEntity = new AccountUserMappingEntity(mappingId);
                    accountUserMappingRepository.save(mappingEntity);
                }
            } else {
                throw new NotFoundException(AccountsErrorCode.ACCOUNT_NOT_FOUND);
            }
        } else {
            throw new NotFoundException(AccountsErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public void linkAccountToMercadoPago(LinkAccountToMercadoPago linkAccountToMercadoPago) {
        Optional<AccountEntity> optionalAccountEntity = Optional.ofNullable(
                accountRepository.findByIdAndIsActive(linkAccountToMercadoPago.getAccountId(), true));
        if (optionalAccountEntity.isPresent()) {
            AccountEntity accountEntity = optionalAccountEntity.get();
            if (Objects.nonNull(accountEntity.getMercadoPagoAccount())) {
                throw new InvalidInputException(AccountsErrorCode.INVALID_INPUT, "Account already linked to MercadoPago");
            }
            if (mercadoPagoService.isValidAccount(linkAccountToMercadoPago.getMercadoPagoId())) {
                accountEntity.setMercadoPagoAccount(linkAccountToMercadoPago.getMercadoPagoId().toString());
                accountRepository.save(accountEntity);
            } else {
                throw new InvalidInputException(AccountsErrorCode.INVALID_INPUT, "Invalid MercadoPago account");
            }
        } else {
            throw new NotFoundException(AccountsErrorCode.ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public void buyCreditsMercadoPago(BuyCreditsMercadoPago buyCreditsMercadoPago) {
        Optional<AccountEntity> optionalAccountEntity = Optional.ofNullable(
                accountRepository.findByIdAndIsActive(buyCreditsMercadoPago.getAccountId(), true));
        if (optionalAccountEntity.isPresent()) {
            AccountEntity accountEntity = optionalAccountEntity.get();
            if (Objects.isNull(accountEntity.getMercadoPagoAccount())) {
                throw new InvalidInputException(AccountsErrorCode.INVALID_INPUT, "MercadoPago account not linked");
            }
            BuyCredits buyCredits = new BuyCredits();
            buyCredits.setCredits(buyCreditsMercadoPago.getCredits());
            buyCredits.setMercadoPagoAccountId(Integer.parseInt(accountEntity.getMercadoPagoAccount()));
            mercadoPagoService.buyCredits(buyCredits);
            accountEntity.setBalance(accountEntity.getBalance() + buyCredits.getCredits());
            accountRepository.save(accountEntity);
        } else {
            throw new NotFoundException(AccountsErrorCode.ACCOUNT_NOT_FOUND);
        }
    }
}
