package com.globant.tc.scooter.accounts.service.external.impl;

import com.globant.tc.scooter.accounts.exception.InternalErrorException;
import com.globant.tc.scooter.accounts.model.mercadoPago.BuyCredits;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
public class MercadoPagoServiceImplTest {

    @InjectMocks
    private MercadoPagoServiceImpl mercadoPagoService;

    @MockBean
    private RestTemplate restTemplate;

    private BuyCredits buyCredits;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        buyCredits = new BuyCredits().credits(1000.0).mercadoPagoAccountId(1);
    }

    @Test
    public void isValidAccountTest() {
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(1, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(responseEntity);
        Assert.assertTrue(mercadoPagoService.isValidAccount(1));
    }

    @Test
    public void isValidUserTest_False() {
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(1, HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(responseEntity);
        Assert.assertFalse(mercadoPagoService.isValidAccount(1));
    }

    @Test(expected = InternalErrorException.class)
    public void isValidUserTest_Exception() {
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenThrow(new RuntimeException());
        mercadoPagoService.isValidAccount(1);
    }

    @Test(expected = InternalErrorException.class)
    public void buyCreditsTest_InternalServerError() {
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(1, HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(HttpEntity.class),
                Mockito.any(Class.class))).thenReturn(responseEntity);
        mercadoPagoService.buyCredits(buyCredits);
    }

    @Test(expected = InternalErrorException.class)
    public void buyCreditsTest_InternalServerError2() {
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(HttpEntity.class),
                Mockito.any(Class.class))).thenThrow(new RuntimeException());
        mercadoPagoService.buyCredits(buyCredits);
    }

    @Test
    public void buyCreditsTest() {
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(1, HttpStatus.OK);
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(HttpEntity.class),
                Mockito.any(Class.class))).thenReturn(responseEntity);
        mercadoPagoService.buyCredits(buyCredits);
    }
}
