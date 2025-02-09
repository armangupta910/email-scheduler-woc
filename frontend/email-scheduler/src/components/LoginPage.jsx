import React, { useState } from "react";
import "../styles/LoginPage.css";

const LoginPage = ({ onLogin }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = () => {
    if (!email || !password) {
      setError("Both fields are required.");
      return;
    }
    if (email === "yash" && password === "123") {
      setError(""); // Clear error
      onLogin(true); // Notify parent of successful login
    } else {
      setError("Invalid email or password.");
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h2 className="welcome-back">Welcome back</h2>
        <p className="enter-details">Please enter your details</p>

        <div className="form-container">
          <input
            type="email"
            placeholder="Email address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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
