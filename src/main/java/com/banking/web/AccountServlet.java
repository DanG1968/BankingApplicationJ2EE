package com.banking.web;

import com.banking.ejb.AccountService;

import javax.naming.InitialContext;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AccountServlet",
        urlPatterns = {"/createAccount", "/viewAccount"},
        loadOnStartup = 2
)
public class AccountServlet extends HttpServlet {

    @EJB
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            accountService = (AccountService) ctx.lookup("java:global/BankingApp/AccountServiceBean");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the HTTP request
        String accountNumber = request.getParameter("accountNumber");
        String accountHolder = request.getParameter("accountHolder");
        String password = request.getParameter("password");
        String accountType = request.getParameter("accountType");
        double initialBalance;

        try {
            // Parse the initial balance and handle potential parsing errors
            initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid initial balance value");
            return;
        }

        // Call the createAccount method from the AccountService
        try {
            accountService.createAccount(accountNumber, password, accountHolder, accountType, initialBalance);
            response.getWriter().write("Account created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Failed to create account: " + e.getMessage());
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the account number from the request parameters
        String accountNumber = request.getParameter("accountNumber");

        // Use the accountService to get account details
        String accountHolder = accountService.getAccountHolder(accountNumber);
        double accountBalance = accountService.getAccountBalance(accountNumber);
        String accountType = accountService.getAccountType(accountNumber);

        // Set account details as request attributes
        request.setAttribute("accountNumber", accountNumber);
        request.setAttribute("accountHolder", accountHolder);
        request.setAttribute("accountBalance", accountBalance);
        request.setAttribute("accountType", accountType);

        // Forward the request to viewAccount.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewAccount.jsp");
        dispatcher.forward(request, response);
    }
}
