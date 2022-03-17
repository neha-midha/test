package com.globant.tc.scooter.accounts.service.external.impl;

import com.globant.tc.scooter.accounts.exception.AccountsErrorCode;
import com.globant.tc.scooter.accounts.exception.InternalErrorException;
import com.globant.tc.scooter.accounts.model.mercadoPago.BuyCredits;
import com.globant.tc.scooter.accounts.service.external.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.mercadoPago.url}")
    private String url;

    @Override
    public boolean isValidAccount(Integer mercadoPagoId) {
        try {
            ResponseEntity<Integer> responseEntity =
                    restTemplate.getForEntity(url + "/findUserByAccount/" + mercadoPagoId, Integer.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                if (Objects.nonNull(responseEntity.getBody())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new InternalErrorException(AccountsErrorCode.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

    @Override
    public void buyCredits(BuyCredits buyCredits) {
        try {
            HttpEntity<BuyCredits> request = new HttpEntity<>(buyCredits);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url + "/buyCredits", request, String.class);
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                throw new InternalErrorException(AccountsErrorCode.INTERNAL_SERVER_ERROR, "Error while buying MercadoPago credits");
            }
        } catch (Exception e) {
            throw new InternalErrorException(AccountsErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
