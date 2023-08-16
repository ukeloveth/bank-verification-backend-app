package com.task.Bank.Verification.Number.Application.services;


import com.task.Bank.Verification.Number.Application.model.BlackListedToken;

public interface BlackListService {

    BlackListedToken blackListToken(String  token);

//    BlackListedToken getToken(String  token);

    boolean tokenExist(String token);
}
