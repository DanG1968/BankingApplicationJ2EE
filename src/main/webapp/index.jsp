<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Welcome to the Banking Application</title>
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
        .nav-links {
            margin-top: 20px;
        }
        .nav-links a {
            display: block;
            margin: 10px 0;
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
        }
        .nav-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome to the Banking Application</h1>
    <p>Manage your accounts securely and efficiently.</p>
    <div class="nav-links">
        <a href="createAccount.jsp">Create an Account</a>
        <a href="viewAccount.jsp">View Account Details</a>
        <a href="login.jsp">Login to Your Account</a>
        <a href="transferFunds.jsp">Transfer Funds</a>
    </div>
</div>
</body>
</html>
