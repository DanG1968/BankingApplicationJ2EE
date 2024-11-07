package com.banking.web;

import com.banking.ejb.AccountService;
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
        urlPatterns = {"/account", "/viewAccount"},
        loadOnStartup = 2
)
public class AccountServlet extends HttpServlet {

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String accountHolder = request.getParameter("accountHolder");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String accountType = request.getParameter("accountType");
        double initialBalance;

        try {
            initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid initial balance value");
            return;
        }

        try {
            boolean success = accountService.createAccount(username, password, accountHolder, accountType, initialBalance);
            response.getWriter().write(success ? "Account created successfully" : "Failed to create account");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Failed to create account: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        String accountHolder = accountService.getAccountHolder(accountNumber);
        double accountBalance = accountService.getAccountBalance(accountNumber);
        String accountType = accountService.getAccountType(accountNumber);

        request.setAttribute("accountNumber", accountNumber);
        request.setAttribute("accountHolder", accountHolder);
        request.setAttribute("accountBalance", accountBalance);
        request.setAttribute("accountType", accountType);

        RequestDispatcher dispatcher = request.getRequestDispatcher("viewAccount.jsp");
        dispatcher.forward(request, response);
    }
}
