package com.globant.tc.scooter.accounts.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.tc.scooter.accounts.exception.AccountsErrorCode;
import com.globant.tc.scooter.accounts.exception.InternalErrorException;
import com.globant.tc.scooter.accounts.exception.InvalidInputException;
import com.globant.tc.scooter.accounts.exception.NotFoundException;
import com.globant.tc.scooter.accounts.model.*;
import com.globant.tc.scooter.accounts.service.AccountsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest
public class AccountControllerTest {

    @InjectMocks
    private AccountsController accountsController;

    @MockBean
    private AccountsService accountsService;

    @Autowired
    private MockMvc mockMvc;

    private Integer accountId = 1;
    private Integer userId = 1;
    private Integer mercadoPagoId = 1;
    private Double credits = 100.0;
    private CreateAccount createAccount;
    private AssociateUser associateUser;
    private AddCredits addCredits;
    private LinkAccountToMercadoPago linkAccountToMercadoPago;
    private BuyCreditsMercadoPago buyCreditsMercadoPago;

    @Before
    public void init() {
        createAccount = new CreateAccount().account(new Account().active(true).balance(0.0)).userId(2);
        associateUser = new AssociateUser().userId(userId).accountId(accountId);
        addCredits = new AddCredits().credits(credits).accountId(accountId);
        linkAccountToMercadoPago = new LinkAccountToMercadoPago().mercadoPagoId(mercadoPagoId).accountId(accountId);
        buyCreditsMercadoPago = new BuyCreditsMercadoPago().credits(credits).accountId(accountId);
    }

    @Test
    public void createAccountTest() throws Exception {
        AccountResponse accountResponse = new AccountResponse().accountId(accountId);
        Mockito.when(accountsService.createAccount(Mockito.any(CreateAccount.class)))
                .thenReturn(accountResponse);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/createAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(createAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
        String content = response.getContentAsString();
        Assert.assertEquals(getJsonString(accountResponse), content);
    }

    @Test
    public void addCreditsToAccountTest() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/addCreditsToAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(addCredits))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void associateUserToAccountTest() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/associateUserToAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(associateUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void linkAccountToMercadoPagoTest() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/linkAccountToMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(linkAccountToMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest_NotFound() throws Exception {
        Mockito.doThrow(new NotFoundException(AccountsErrorCode.ACCOUNT_NOT_FOUND)).when(accountsService)
                .buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest_InvalidInput() throws Exception {
        Mockito.doThrow(new InvalidInputException(AccountsErrorCode.INVALID_INPUT)).when(accountsService)
                .buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest_InternalError() throws Exception {
        Mockito.doThrow(new InternalErrorException(AccountsErrorCode.INTERNAL_SERVER_ERROR)).when(accountsService)
                .buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(500, response.getStatus());
    }


    @Test
    public void buyCreditsMercadoPagoTest_NotFound2() throws Exception {
        Mockito.doThrow(new NotFoundException(AccountsErrorCode.ACCOUNT_NOT_FOUND, "Account not found")).when(accountsService)
                .buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest_InvalidInput2() throws Exception {
        Mockito.doThrow(new InvalidInputException(AccountsErrorCode.INVALID_INPUT, "Invalid credits")).when(accountsService)
                .buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest_InternalError2() throws Exception {
        Mockito.doThrow(new InternalErrorException(AccountsErrorCode.INTERNAL_SERVER_ERROR, "Error while buying credits"))
                .when(accountsService).buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest_RuntimeExceptionHandler() throws Exception {
        Mockito.doThrow(new RuntimeException("Error while buying credits"))
                .when(accountsService).buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCreditsMercadoPago))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void buyCreditsMercadoPagoTest_InvalidMethodArgument() throws Exception {
        BuyCreditsMercadoPago buyCredits = new BuyCreditsMercadoPago();
        buyCredits.credits(0.0);
        buyCredits.accountId(accountId);
        Mockito.doThrow(new InternalErrorException(AccountsErrorCode.INTERNAL_SERVER_ERROR, "Error while buying credits"))
                .when(accountsService).buyCreditsMercadoPago(Mockito.any(BuyCreditsMercadoPago.class));
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/buyCreditsMercadoPago")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getJsonString(buyCredits))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.toString());
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Assert.assertEquals(400, response.getStatus());
    }

    private String getJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
