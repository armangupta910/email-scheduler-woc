import React, { useState } from "react";
import "../styles/LoginPage.css";

const LoginPage = ({ onLogin }) => {
  const [username, setUsername] = useState(""); 
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async () => {
    if (!username || !password) {
      setError("Both fields are required.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username, 
          password,
        }),
      });

      const data = await response.json();

      if (response.ok) {
        localStorage.setItem("token", data.token); // Store JWT token
        setError("");
        alert("Login Successful!");
        onLogin(true); // Notify parent that login was successful
      } else {
        setError(data.message || "Invalid username or password.");
      }
    } catch (err) {
      setError("Error connecting to the server. Please try again.");
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h2 className="welcome-back">Welcome back</h2>
        <p className="enter-details">Please enter your details</p>

        <div className="form-container">
          <input
            type="text"
            placeholder="Username"
            value={username} 
            onChange={(e) => setUsername(e.target.value)} // Updated state setter
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          {error && <p className="error-message">{error}</p>}

          <button onClick={handleLogin} className="login-button">
            Login
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
