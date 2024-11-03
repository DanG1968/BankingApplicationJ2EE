package com.banking.ejb;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Stateless
@Local(AccountService.class) // Specify the local interface
public class AccountServiceBean implements AccountService {

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/banking";
    private static final String DB_USER = "daniel";
    private static final String DB_PASSWORD = "s3cret";

    // Helper method to establish a database connection
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public boolean createAccount(String accountNumber, String accountHolder, double initialBalance) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO accounts (account_number, account_holder, balance, account_type) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            stmt.setString(2, accountHolder);
            stmt.setDouble(3, initialBalance);
            stmt.setString(4, "Savings"); // Default account type, modify as needed
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean transferFunds(String fromAccount, String toAccount, double amount) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // Deduct from the sender's account
            String deductSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement deductStmt = conn.prepareStatement(deductSql);
            deductStmt.setDouble(1, amount);
            deductStmt.setString(2, fromAccount);
            int rowsUpdated = deductStmt.executeUpdate();

            if (rowsUpdated == 0) {
                conn.rollback();
                return false;
            }

            // Add to the receiver's account
            String addSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement addStmt = conn.prepareStatement(addSql);
            addStmt.setDouble(1, amount);
            addStmt.setString(2, toAccount);
            addStmt.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public double getBalance(String accountNumber) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // New method to get the account holder's name
    @Override
    public String getAccountHolder(String accountNumber) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT account_holder FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("account_holder");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Account Holder";
    }

    // New method to get the account balance
    @Override
    public double getAccountBalance(String accountNumber) {
        return getBalance(accountNumber); // Reuse the existing getBalance method
    }

    // New method to get the account type
    @Override
    public String getAccountType(String accountNumber) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT account_type FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("account_type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Account Type";
    }

    @Override
    public boolean authenticate(String username, String hashedPassword) {
        return false;
    }
}
