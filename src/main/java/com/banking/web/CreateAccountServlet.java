package com.banking.web;

import com.banking.ejb.AccountService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/createAccount")
public class CreateAccountServlet extends HttpServlet {

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String accountHolder = request.getParameter("accountHolder");
        String accountType = request.getParameter("accountType");
        double initialBalance;

        try {
            initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid initial balance. Please enter a valid number.");
            request.getRequestDispatcher("createAccount.jsp").forward(request, response);
            return;
        }

        boolean success = accountService.createAccount(username, password, accountHolder, accountType, initialBalance);

        if (success) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("errorMessage", "Account creation failed. Please try again.");
            request.getRequestDispatcher("createAccount.jsp").forward(request, response);
        }
    }
}
