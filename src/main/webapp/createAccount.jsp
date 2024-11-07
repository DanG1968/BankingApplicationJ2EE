<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Create Account</title>
</head>
<body>
<h2>Create a New Account</h2>

<form action="createAccount" method="post">
  <label for="username">Username:</label>
  <input type="text" id="username" name="username" required>
  <br><br>

  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required>
  <br><br>

  <label for="accountHolder">Account Holder Name:</label>
  <input type="text" id="accountHolder" name="accountHolder" required>
  <br><br>

  <label for="accountType">Account Type:</label>
  <select name="accountType" id="accountType" required>
    <option value="Savings">Savings</option>
    <option value="Checking">Checking</option>
    <option value="Business">Business</option>
  </select>
  <br><br>

  <label for="initialBalance">Initial Balance:</label>
  <input type="number" step="0.01" id="initialBalance" name="initialBalance" required>
  <br><br>

  <button type="submit">Create Account</button>
</form>

<% if (request.getAttribute("errorMessage") != null) { %>
<p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<% if (request.getAttribute("successMessage") != null) { %>
<p style="color:green;"><%= request.getAttribute("successMessage") %></p>
<% } %>

</body>
</html>
