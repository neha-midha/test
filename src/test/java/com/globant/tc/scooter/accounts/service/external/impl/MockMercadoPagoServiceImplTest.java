package com.globant.tc.scooter.accounts.service.external.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MockMercadoPagoServiceImplTest {

    @InjectMocks
    private MockMercadoPagoServiceImpl mockMercadoPagoService;

    @Test
    public void isValidAccountTest() {
        Assert.assertTrue(mockMercadoPagoService.isValidAccount(1));
    }

    @Test
    public void  buyCreditsTest() {
        mockMercadoPagoService.buyCredits(null);
    }
}
