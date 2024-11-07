package com.banking.ejb;

import jakarta.ejb.Remote;

@Remote
public interface AccountService {

    boolean createAccount(String username, String password, String accountHolder, String accountType, double initialBalance);

    boolean transferFunds(String fromAccount, String toAccount, double amount);

    String getAccountHolder(String accountNumber);

    double getAccountBalance(String accountNumber);

    String getAccountType(String accountNumber);

    boolean authenticate(String username, String password);
}
