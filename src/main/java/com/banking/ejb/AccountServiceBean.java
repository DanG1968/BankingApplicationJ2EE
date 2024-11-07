package com.banking.ejb;

import com.banking.entity.Account;
import com.banking.entity.User;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Stateless
@Remote(AccountService.class)
public class AccountServiceBean implements AccountService {

    @PersistenceContext(unitName = "BankingPU")
    private EntityManager entityManager;

    private static final int SALT_LENGTH = 16;
    private static final int HASH_ITERATIONS = 10000;
    private static final int HASH_KEY_LENGTH = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public boolean createAccount(String username, String password, String accountHolder, String accountType, double initialBalance) {
        // Check if the user already exists
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (user == null) {
            // If the user does not exist, create a new User entity
            user = new User();
            user.setUsername(username);

            byte[] salt = generateSalt();
            user.setSalt(Base64.getEncoder().encodeToString(salt).getBytes());
            user.setPassword(hashPassword(password, salt));

            entityManager.persist(user);
        }

        // Generate unique account number
        String accountNumber = generateUniqueAccountNumber();

        // Create and persist the Account entity, linking it to the User entity
        Account newAccount = new Account();
        newAccount.setAccountNumber(accountNumber);
        newAccount.setUser(user); // Associate the account with the User
        newAccount.setAccountHolder(accountHolder);
        newAccount.setAccountType(accountType);
        newAccount.setBalance(initialBalance);

        entityManager.persist(newAccount);

        return true;
    }


    private boolean isUsernameExists(String username) {
        Query query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username");
        query.setParameter("username", username);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = "AC" + (100000 + RANDOM.nextInt(900000)); // Generates a unique 6-digit random number prefixed with "AC"
        } while (isAccountNumberExists(accountNumber));
        return accountNumber;
    }

    private boolean isAccountNumberExists(String accountNumber) {
        Query query = entityManager.createQuery("SELECT COUNT(a) FROM Account a WHERE a.accountNumber = :accountNumber");
        query.setParameter("accountNumber", accountNumber);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return salt;
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, HASH_KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    @Override
    public boolean authenticate(String username, String password) {
        try {
            User user = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (user == null) {
                return false; // User not found
            }

            String storedHashedPassword = user.getPassword();
            byte[] salt = Base64.getDecoder().decode(user.getSalt());

            String hashedPassword = hashPassword(password, salt);

            return hashedPassword.equals(storedHashedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean transferFunds(String fromAccount, String toAccount, double amount) {
        Account sourceAccount = entityManager.find(Account.class, fromAccount);
        Account destinationAccount = entityManager.find(Account.class, toAccount);

        if (sourceAccount == null || destinationAccount == null || sourceAccount.getBalance() < amount) {
            return false; // Transfer cannot proceed
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        entityManager.merge(sourceAccount);
        entityManager.merge(destinationAccount);

        return true;
    }

    @Override
    public String getAccountHolder(String accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber);
        return account != null ? account.getAccountHolder() : null;
    }

    @Override
    public double getAccountBalance(String accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }

    @Override
    public String getAccountType(String accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber);
        return account != null ? account.getAccountType() : null;
    }
}
