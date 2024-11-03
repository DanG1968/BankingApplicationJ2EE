<%--
  Created by IntelliJ IDEA.
  User: daniel-grindstaff
  Date: 11/3/24
  Time: 6:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Account Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
        }
        h1 {
            color: #333;
        }
        .account-details {
            text-align: left;
            margin-top: 20px;
        }
        .account-details p {
            margin: 10px 0;
            font-size: 14px;
            color: #555;
        }
        .back-link {
            margin-top: 20px;
            display: inline-block;
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Account Details</h1>
    <div class="account-details">
        <p><strong>Account Number:</strong> <%= request.getAttribute("accountNumber") %></p>
        <p><strong>Account Holder:</strong> <%= request.getAttribute("accountHolder") %></p>
        <p><strong>Account Balance:</strong> $<%= request.getAttribute("accountBalance") %></p>
        <p><strong>Account Type:</strong> <%= request.getAttribute("accountType") %></p>
    </div>
    <a href="index.jsp" class="back-link">Back to Home</a>
</div>
</body>
</html>
