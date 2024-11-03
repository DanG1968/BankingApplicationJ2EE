package com.banking.web;

import com.banking.ejb.AccountService;

import javax.naming.InitialContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "TransferServlet",
        urlPatterns = {"/transferFunds"},
        loadOnStartup = 3
)
public class TransferServlet extends HttpServlet {
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
        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        double amount = Double.parseDouble(request.getParameter("amount"));

        boolean success = accountService.transferFunds(fromAccount, toAccount, amount);
        response.getWriter().write(success ? "Funds transferred successfully" : "Failed to transfer funds");
    }
}
