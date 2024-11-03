package com.banking.web;

import com.banking.ejb.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ejb.EJB;
import java.io.IOException;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Injecting the AccountService EJB
    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve login details from the request
        String accountNumber = request.getParameter("accountNumber");
        String password = request.getParameter("password");

        // Validate the input (ensure accountNumber and password are not empty)
        if (accountNumber == null || accountNumber.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Account number and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Authenticate the user
        boolean isAuthenticated = authenticateUser(accountNumber, password);

        if (isAuthenticated) {
            // Successful login: Redirect to the account overview page
            response.sendRedirect("accountOverview.jsp");
        } else {
            // Failed login: Set an error message and forward back to login.jsp
            request.setAttribute("errorMessage", "Invalid account number or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Authenticate the user using the AccountService.
     * This method should validate the account number and password against the database.
     *
     * @param accountNumber The account number provided by the user.
     * @param password      The password provided by the user.
     * @return true if the authentication is successful, false otherwise.
     */
    private boolean authenticateUser(String accountNumber, String password) {
        // Dummy authentication logic for demonstration purposes
        // Replace this with a real authentication mechanism using AccountService
        try {
            // Example logic: Check if the account exists and verify the password
            double accountBalance = accountService.getBalance(accountNumber); // Check if account exists
            // Here, you would typically hash the password and compare it to the hashed value stored in the database
            return accountBalance > 0 && "password123".equals(password); // Replace "password123" with real password validation
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
