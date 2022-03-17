package com.globant.tc.scooter.accounts.service.impl;

import com.globant.tc.scooter.accounts.entity.AccountEntity;
import com.globant.tc.scooter.accounts.entity.AccountUserMappingEntity;
import com.globant.tc.scooter.accounts.entity.AccountUserMappingId;
import com.globant.tc.scooter.accounts.exception.InvalidInputException;
import com.globant.tc.scooter.accounts.exception.NotFoundException;
import com.globant.tc.scooter.accounts.model.*;
import com.globant.tc.scooter.accounts.model.mercadoPago.BuyCredits;
import com.globant.tc.scooter.accounts.repository.AccountRepository;
import com.globant.tc.scooter.accounts.repository.AccountUserMappingRepository;
import com.globant.tc.scooter.accounts.service.external.MercadoPagoService;
import com.globant.tc.scooter.accounts.service.external.UserService;
import com.globant.tc.scooter.accounts.service.external.impl.MercadoPagoServiceImpl;
import com.globant.tc.scooter.accounts.service.external.impl.MockMercadoPagoServiceImpl;
import com.globant.tc.scooter.accounts.service.external.impl.MockUserServiceImpl;
import com.globant.tc.scooter.accounts.service.external.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class AccountsServiceImplTest {

    @InjectMocks
    private AccountsServiceImpl accountsService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountUserMappingRepository accountUserMappingRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private MercadoPagoService mercadoPagoService;

    @MockBean
    private ApplicationContext context;

    private Integer accountId = 1;
    private Integer userId = 1;
    private Integer mercadoPagoId = 1;
    private Double credits = 100.0;
    private CreateAccount createAccount;
    private AssociateUser associateUser;
    private AddCredits addCredits;
    private LinkAccountToMercadoPago linkAccountToMercadoPago;
    private BuyCreditsMercadoPago buyCreditsMercadoPago;

    private AccountEntity accountEntity;
    private AccountUserMappingEntity accountUserMappingEntity;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        createAccount = new CreateAccount().account(new Account().active(true).balance(0.0)).userId(2);
        associateUser = new AssociateUser().userId(userId).accountId(accountId);
        addCredits = new AddCredits().credits(credits).accountId(accountId);
        linkAccountToMercadoPago = new LinkAccountToMercadoPago().mercadoPagoId(mercadoPagoId).accountId(accountId);
        buyCreditsMercadoPago = new BuyCreditsMercadoPago().credits(credits).accountId(accountId);
        ReflectionTestUtils.setField(accountsService, "userService", userService);
        ReflectionTestUtils.setField(accountsService, "mercadoPagoService", mercadoPagoService);

        accountEntity = new AccountEntity();
        accountEntity.setBalance(0.0);
        accountEntity.setMercadoPagoAccount(null);
        accountEntity.setActive(true);
        accountEntity.setId(accountId);

        AccountUserMappingId mappingId = new AccountUserMappingId();
        mappingId.setAccountId(accountId);
        mappingId.setUserId(userId);
        accountUserMappingEntity = new AccountUserMappingEntity();
        accountUserMappingEntity.setAccountUserMappingId(mappingId);
    }

    @Test
    public void initTest() {
        ReflectionTestUtils.setField(accountsService, "isUserServiceEnabled", false);
        ReflectionTestUtils.setField(accountsService, "isMercadoPagoServiceEnabled", false);
        Mockito.when(context.getBean(MockUserServiceImpl.class)).thenReturn(new MockUserServiceImpl());
        Mockito.when(context.getBean(MockMercadoPagoServiceImpl.class)).thenReturn(new MockMercadoPagoServiceImpl());
        accountsService.init();
        Assert.assertTrue(ReflectionTestUtils.getField(accountsService, "userService") instanceof MockUserServiceImpl);
        Assert.assertTrue(ReflectionTestUtils.getField(accountsService, "mercadoPagoService") instanceof MockMercadoPagoServiceImpl);
        ReflectionTestUtils.setField(accountsService, "isUserServiceEnabled", true);
        ReflectionTestUtils.setField(accountsService, "isMercadoPagoServiceEnabled", true);
        Mockito.when(context.getBean(UserServiceImpl.class)).thenReturn(new UserServiceImpl());
        Mockito.when(context.getBean(MercadoPagoServiceImpl.class)).thenReturn(new MercadoPagoServiceImpl());
        accountsService.init();
        Assert.assertTrue(ReflectionTestUtils.getField(accountsService, "userService") instanceof UserServiceImpl);
        Assert.assertTrue(ReflectionTestUtils.getField(accountsService, "mercadoPagoService") instanceof MercadoPagoServiceImpl);
        ReflectionTestUtils.setField(accountsService, "userService", userService);
        ReflectionTestUtils.setField(accountsService, "mercadoPagoService", mercadoPagoService);
    }

    @Test(expected = NotFoundException.class)
    public void createAccountTest_UserNotFound() {
        accountsService.createAccount(createAccount);
    }

    @Test
    public void createAccountTest() {
        Mockito.when(userService.isValidUser(createAccount.getUserId())).thenReturn(true);
        Mockito.when(accountRepository.save(Mockito.any(AccountEntity.class))).thenReturn(accountEntity);
        AccountResponse accountResponse = accountsService.createAccount(createAccount);
        Assert.assertEquals(accountId, accountResponse.getAccountId());
        Mockito.verify(accountRepository).save(Mockito.any(AccountEntity.class));
        Mockito.verify(accountUserMappingRepository).save(Mockito.any(AccountUserMappingEntity.class));
    }

    @Test(expected = NotFoundException.class)
    public void addCreditsToAccountTest_AccountNotFound() {
        accountsService.addCreditsToAccount(addCredits);
    }

    @Test
    public void addCreditsToAccountTest() {
        Mockito.when(accountRepository.findByIdAndIsActive(addCredits.getAccountId(), true))
                .thenReturn(accountEntity);
        accountsService.addCreditsToAccount(addCredits);
        Mockito.verify(accountRepository).save(Mockito.any(AccountEntity.class));
    }

    @Test(expected = NotFoundException.class)
    public void associateUserToAccountTest_UserNotFound() {
        accountsService.associateUserToAccount(associateUser);
    }

    @Test(expected = NotFoundException.class)
    public void associateUserToAccountTest_AccountNotFound() {
        Mockito.when(userService.isValidUser(userId)).thenReturn(true);
        accountsService.associateUserToAccount(associateUser);
    }

    @Test(expected = InvalidInputException.class)
    public void associateUserToAccountTest_UserAlreadyAssociated() {
        Mockito.when(userService.isValidUser(userId)).thenReturn(true);
        Mockito.when(accountRepository.findByIdAndIsActive(accountId, true)).thenReturn(accountEntity);
        Mockito.when(accountUserMappingRepository.findById(Mockito.any(AccountUserMappingId.class)))
                .thenReturn(Optional.ofNullable(accountUserMappingEntity));
        accountsService.associateUserToAccount(associateUser);
    }

    @Test
    public void associateUserToAccountTest() {
        Mockito.when(userService.isValidUser(userId)).thenReturn(true);
        Mockito.when(accountRepository.findByIdAndIsActive(accountId, true)).thenReturn(accountEntity);
        accountsService.associateUserToAccount(associateUser);
        Mockito.verify(accountUserMappingRepository).save(Mockito.any(AccountUserMappingEntity.class));
    }

    @Test(expected = NotFoundException.class)
    public void linkAccountToMercadoPagoTest_AccountNotFound() {
        accountsService.linkAccountToMercadoPago(linkAccountToMercadoPago);
    }

    @Test(expected = InvalidInputException.class)
    public void linkAccountToMercadoPagoTest_AccountAlreadyLinked() {
        AccountEntity entity = new AccountEntity();
        entity.setId(accountId);
        entity.setMercadoPagoAccount("1");
        Mockito.when(accountRepository.findByIdAndIsActive(accountId, true)).thenReturn(entity);
        accountsService.linkAccountToMercadoPago(linkAccountToMercadoPago);
    }

    @Test(expected = InvalidInputException.class)
    public void linkAccountToMercadoPagoTest_InvalidMercadoPagoAccount() {
        Mockito.when(accountRepository.findByIdAndIsActive(accountId, true)).thenReturn(accountEntity);
        accountsService.linkAccountToMercadoPago(linkAccountToMercadoPago);
    }

    @Test
    public void linkAccountToMercadoPagoTest() {
        Mockito.when(accountRepository.findByIdAndIsActive(accountId, true)).thenReturn(accountEntity);
        Mockito.when(mercadoPagoService.isValidAccount(mercadoPagoId)).thenReturn(true);
        accountsService.linkAccountToMercadoPago(linkAccountToMercadoPago);
        Mockito.verify(accountRepository).save(Mockito.any(AccountEntity.class));
    }

    @Test(expected = NotFoundException.class)
    public void buyCreditsMercadoPagoTest_AccountNotFound() {
        accountsService.buyCreditsMercadoPago(buyCreditsMercadoPago);
    }

    @Test(expected = InvalidInputException.class)
    public void buyCreditsMercadoPagoTest_MercadoPagoNotLinked() {
        Mockito.when(accountRepository.findByIdAndIsActive(accountId, true)).thenReturn(accountEntity);
        accountsService.buyCreditsMercadoPago(buyCreditsMercadoPago);
    }

    @Test
    public void buyCreditsMercadoPagoTest() {
        AccountEntity entity = new AccountEntity();
        entity.setId(accountId);
        entity.setMercadoPagoAccount("1");
        entity.setBalance(0.0);
        Mockito.when(accountRepository.findByIdAndIsActive(accountId, true)).thenReturn(entity);
        accountsService.buyCreditsMercadoPago(buyCreditsMercadoPago);
        Mockito.verify(mercadoPagoService).buyCredits(Mockito.any(BuyCredits.class));
        Mockito.verify(accountRepository).save(Mockito.any(AccountEntity.class));
    }

    @Test
    public void modelTest() {
        Assert.assertEquals(accountId, accountUserMappingEntity.getAccountUserMappingId().getAccountId());
        Assert.assertEquals(userId, accountUserMappingEntity.getAccountUserMappingId().getUserId());
        Assert.assertTrue(accountEntity.isActive());
    }
}
