package com.picpay.challenge.services;

import com.picpay.challenge.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizarionService {

    @Autowired
    private RestTemplate restTemplate;


    public boolean authorizeTransaction (User sender, BigDecimal value){

        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(
                "https://util.devi.tools/api/v2/authorize", Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK){

            return true;

        }

        return false;

    }

}
