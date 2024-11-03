package com.banking.bankingapplicationj2ee;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/account")
public class AccountResource {

    @GET
    @Path("/details")
    @Produces("application/json")
    public Response getAccountDetails(@QueryParam("accountNumber") String accountNumber) {
        // Simulate fetching account details (replace with actual logic)
        String accountHolder = "John Doe";
        double accountBalance = 1500.75;
        String accountType = "Savings";

        // Build a JSON-like response (replace with real JSON conversion if needed)
        String accountDetails = String.format(
            "{\"accountNumber\": \"%s\", \"accountHolder\": \"%s\", \"accountBalance\": %.2f, \"accountType\": \"%s\"}",
            accountNumber, accountHolder, accountBalance, accountType
        );

        return Response.ok(accountDetails).build();
    }
}
