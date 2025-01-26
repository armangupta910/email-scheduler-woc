import React, { useState } from "react";
import "../styles/LoginPage.css";

const LoginPage = ({ onLogin }) => {
  const [name, setName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [designation, setDesignation] = useState("");
  const [error, setError] = useState("");

  const handleLogin = () => {
    if (!email || !password || !name || !phoneNumber || !designation) {
      setError("All fields are required.");
      return;
    }
     try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
          name,
          phoneNumber,
          designation,
        }),
      });

    if (email === "yash" && password === "123") {
      setError(""); // Clear error
      onLogin(true); // Notify parent of successful login
    } else {
      setError("Invalid email or password.");
    }
  };

  const handlePhoneNumberChange = (e) => {
    const value = e.target.value;
    // Allow only numbers
    if (/^\d*$/.test(value)) {
      setPhoneNumber(value);
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
            placeholder="Full Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          
          <input
            type="tel"
            placeholder="Phone Number"
            value={phoneNumber}
            onChange={handlePhoneNumberChange}
          />

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
          
          <input
            type="text"
            placeholder="Designation"
            value={designation}
            onChange={(e) => setDesignation(e.target.value)}
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
