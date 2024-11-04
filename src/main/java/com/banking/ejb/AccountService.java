package com.banking.ejb;

import jakarta.ejb.Remote;

@Remote
public interface AccountService {

    /**
     * Creates a new account with the specified details.
     *
     * @param accountNumber  The unique identifier for the account.
     * @param password       The plaintext password to be hashed and stored securely.
     * @param accountHolder  The name of the account holder.
     * @param accountType    The type of the account (e.g., savings, checking).
     * @param initialBalance The initial deposit amount for the account.
     */
    void createAccount(String accountNumber, String password, String accountHolder, String accountType, double initialBalance);

    /**
     * Transfers funds between two accounts.
     *
     * @param fromAccount The account number to transfer funds from.
     * @param toAccount   The account number to transfer funds to.
     * @param amount      The amount to be transferred.
     * @return true if the transfer is successful, false otherwise.
     */
    boolean transferFunds(String fromAccount, String toAccount, double amount);

    /**
     * Retrieves the account holder's name for a given account number.
     *
     * @param accountNumber The account number.
     * @return The name of the account holder, or null if the account does not exist.
     */
    String getAccountHolder(String accountNumber);

    /**
     * Retrieves the balance of a given account.
     *
     * @param accountNumber The account number.
     * @return The balance of the account, or 0.0 if the account does not exist.
     */
    double getAccountBalance(String accountNumber);

    /**
     * Retrieves the type of a given account.
     *
     * @param accountNumber The account number.
     * @return The type of the account, or null if the account does not exist.
     */
    String getAccountType(String accountNumber);

    /**
     * Authenticates a user by validating the provided credentials.
     *
     * @param username      The username of the account.
     * @param hashedPassword The hashed version of the password.
     * @return true if authentication is successful, false otherwise.
     */
    boolean authenticate(String username, String hashedPassword);
}
