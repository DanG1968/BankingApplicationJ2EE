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
        loadOnStartup = 1
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
        String accountNumber = request.getParameter("accountNumber");
        String accountHolder = request.getParameter("accountHolder");
        double initialBalance = Double.parseDouble(request.getParameter("initialBalance"));

        boolean success = accountService.createAccount(accountNumber, accountHolder, initialBalance);
        response.getWriter().write(success ? "Account created successfully" : "Failed to create account");
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
