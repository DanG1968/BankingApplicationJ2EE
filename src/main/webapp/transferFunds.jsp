<%--
  Created by IntelliJ IDEA.
  User: daniel-grindstaff
  Date: 11/2/24
  Time: 11:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Transfer Funds</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f7f7f7;
      padding: 20px;
    }
    .container {
      max-width: 400px;
      margin: 0 auto;
      background-color: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h2 {
      text-align: center;
    }
    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }
    input[type="text"], input[type="number"] {
      width: 100%;
      padding: 8px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    input[type="submit"] {
      width: 100%;
      padding: 10px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    input[type="submit"]:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Transfer Funds</h2>
  <form action="/transferFunds" method="post">
    <label for="fromAccount">From Account:</label>
    <input type="text" id="fromAccount" name="fromAccount" required>

    <label for="toAccount">To Account:</label>
    <input type="text" id="toAccount" name="toAccount" required>

    <label for="amount">Amount:</label>
    <input type="number" id="amount" name="amount" required min="0">

    <input type="submit" value="Transfer Funds">
  </form>
</div>
</body>
</html>

