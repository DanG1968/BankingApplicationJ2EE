<%--
  Created by IntelliJ IDEA.
  User: daniel-grindstaff
  Date: 11/3/24
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Account Overview - Banking Application</title>
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
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      width: 500px;
      text-align: center;
    }
    h1 {
      color: #333;
      margin-bottom: 20px;
    }
    .account-summary {
      text-align: left;
      margin-bottom: 20px;
    }
    .account-summary p {
      font-size: 14px;
      color: #555;
      margin: 10px 0;
    }
    .action-buttons a {
      display: inline-block;
      margin: 10px;
      padding: 10px 20px;
      background-color: #007BFF;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      font-weight: bold;
    }
    .action-buttons a:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Account Overview</h1>
  <div class="account-summary">
    <p><strong>Account Number:</strong> <%= request.getAttribute("accountNumber") %></p>
    <p><strong>Account Holder:</strong> <%= request.getAttribute("accountHolder") %></p>
    <p><strong>Account Balance:</strong> $<%= request.getAttribute("accountBalance") %></p>
    <p><strong>Account Type:</strong> <%= request.getAttribute("accountType") %></p>
  </div>
  <div class="action-buttons">
    <a href="viewAccount.jsp?accountNumber=<%= request.getAttribute("accountNumber") %>">View Account Details</a>
    <a href="transferFunds.jsp">Transfer Funds</a>
    <a href="logout.jsp">Logout</a>
  </div>
</div>
</body>
</html>
