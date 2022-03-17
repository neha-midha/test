package com.globant.tc.scooter.accounts.service.external.impl;

import com.globant.tc.scooter.accounts.model.mercadoPago.BuyCredits;
import com.globant.tc.scooter.accounts.service.external.MercadoPagoService;
import org.springframework.stereotype.Service;

@Service
public class MockMercadoPagoServiceImpl implements MercadoPagoService {

    @Override
    public boolean isValidAccount(Integer mercadoPagoId) {
        return true;
    }

    @Override
    public void buyCredits(BuyCredits buyCredits) {
        // doNothing
    }
}
