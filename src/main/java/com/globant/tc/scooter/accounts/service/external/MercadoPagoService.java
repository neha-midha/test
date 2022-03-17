package com.globant.tc.scooter.accounts.service.external;

import com.globant.tc.scooter.accounts.model.mercadoPago.BuyCredits;

public interface MercadoPagoService {

    /**
     * calls mercadoPago service to check if account is valid
     * @param mercadoPagoId
     * @return
     */
    boolean isValidAccount(Integer mercadoPagoId);

    void buyCredits(BuyCredits buyCredits);
}
