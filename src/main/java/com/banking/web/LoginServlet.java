package com.banking.web;

import com.banking.ejb.AccountService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/login"},
        loadOnStartup = 1
)
public class LoginServlet extends HttpServlet {

    // Injecting the AccountService EJB
    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve login details from the request
        String accountNumber = request.getParameter("accountNumber");
        String password = request.getParameter("password");

        // Input validation: Check if accountNumber and password are provided
        if (accountNumber == null || accountNumber.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Account number and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            // Use AccountServiceBean to authenticate the user
            boolean isAuthenticated = accountService.authenticate(accountNumber, password);

            if (isAuthenticated) {
                // Successful authentication: Store user details in session and redirect to account overview
                request.getSession().setAttribute("accountNumber", accountNumber);
                response.sendRedirect("accountOverview.jsp");
            } else {
                // Authentication failed: Set an error message and forward to login page
                request.setAttribute("errorMessage", "Invalid account number or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log the error and provide a generic error message to the user
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your request. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
