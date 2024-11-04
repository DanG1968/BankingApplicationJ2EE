package com.banking.ejb;

import com.banking.entity.Account;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Stateless
public class AccountServiceBean implements AccountService {

    @PersistenceContext(unitName = "BankingPU")
    private EntityManager entityManager;

    /**
     * Authenticate a user by validating the provided password against the stored hashed password.
     *
     * @param accountNumber The account number of the user.
     * @param password The password provided by the user.
     * @return true if authentication is successful, false otherwise.
     */
    @Override
    public boolean authenticate(String accountNumber, String password) {
        try {
            // Retrieve the account from the database using the account number
            Account account = entityManager.find(Account.class, accountNumber);
            if (account == null) {
                return false; // Account not found
            }

            // Get the stored hashed password and salt from the account
            String storedHashedPassword = account.getHashedPassword();
            String salt = account.getSalt();

            // Hash the provided password with the stored salt
            String hashedPassword = hashPassword(password, salt);

            // Compare the hashed password with the stored hashed password
            return hashedPassword.equals(storedHashedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Authentication failed due to an error
        }
    }

    /**
     * Hashes a password using SHA-256 and a provided salt.
     *
     * @param password The password to be hashed.
     * @param salt The salt to use in hashing.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException If the hashing algorithm is not available.
     */
    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    /**
     * Generates a secure random salt for password hashing.
     *
     * @return A secure random salt.
     */
    public String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    @Override
    public void createAccount(String accountNumber, String password, String accountHolder, String accountType, double initialBalance) {
        String salt = generateSalt();
        String hashedPassword = null;
        try {
            hashedPassword = hashPassword(password, salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Error hashing password");
        }

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setHashedPassword(hashedPassword);
        account.setSalt(salt);
        account.setAccountHolder(accountHolder);
        account.setAccountType(accountType);
        account.setBalance(initialBalance);

        entityManager.persist(account);
    }

    /**
     * Updates the balance of an account.
     *
     * @param accountNumber The account number.
     * @param newBalance The new balance to set.
     */
    public void updateBalance(String accountNumber, double newBalance) {
        Account account = entityManager.find(Account.class, accountNumber);
        if (account != null) {
            account.setBalance(newBalance);
            entityManager.merge(account);
        }
    }

    @Override
    public boolean transferFunds(String fromAccount, String toAccount, double amount) {
        Account sourceAccount = entityManager.find(Account.class, fromAccount);
        Account destinationAccount = entityManager.find(Account.class, toAccount);

        if (sourceAccount == null || destinationAccount == null || sourceAccount.getBalance() < amount) {
            return false; // Transfer cannot proceed
        }

        // Deduct from source account
        double newSourceBalance = sourceAccount.getBalance() - amount;
        updateBalance(fromAccount, newSourceBalance);

        // Add to destination account
        double newDestinationBalance = destinationAccount.getBalance() + amount;
        updateBalance(toAccount, newDestinationBalance);

        return true; // Transfer successful
    }

    /**
     * Retrieves the account holder's name.
     *
     * @param accountNumber The account number.
     * @return The account holder's name, or null if the account does not exist.
     */
    public String getAccountHolder(String accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber);
        return account != null ? account.getAccountHolder() : null;
    }

    /**
     * Retrieves the account balance.
     *
     * @param accountNumber The account number.
     * @return The account balance, or 0.0 if the account does not exist.
     */
    public double getAccountBalance(String accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }

    /**
     * Retrieves the account type.
     *
     * @param accountNumber The account number.
     * @return The account type, or null if the account does not exist.
     */
    public String getAccountType(String accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber);
        return account != null ? account.getAccountType() : null;
    }
}
