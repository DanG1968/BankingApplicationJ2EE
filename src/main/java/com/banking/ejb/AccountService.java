package com.banking.ejb;

import jakarta.ejb.Remote;

@Remote
public interface AccountService {
    boolean createAccount(String accountNumber, String accountHolder, double initialBalance);
    boolean transferFunds(String fromAccount, String toAccount, double amount);
    double getBalance(String accountNumber);

    // New methods to fetch account details
    String getAccountHolder(String accountNumber);
    double getAccountBalance(String accountNumber);
    String getAccountType(String accountNumber);

    boolean authenticate(String username, String hashedPassword);
}
