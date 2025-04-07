import React, { useState } from "react";
import "../styles/RegisterPage.css";

const RegisterPage = ({ onRegister }) => {
  const [username, setUsername] = useState("");
  const [phone, setPhone] = useState("");
  const [designation, setDesignation] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleRegister = async () => {
    if (!username || !phone || !designation || !email || !password) {
      setError("All fields are required.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username,
          email,
          phone,
          designation,
          password,
        }),
      });

      const data = await response.json();

      if (response.ok) {
        // Store token in localStorage
        localStorage.setItem("token", data.token);

        alert("Registration successful! Redirecting to login.");
        onRegister(); // Navigate to login page
      } else {
        setError(data.message || "Registration failed. Try again.");
      }
    } catch (err) {
      setError("Error connecting to the server. Please try again.");
    }
  };

  return (
    <div className="register-page">
      <div className="register-container">
        <h2 className="welcome">Registration</h2>
        <p className="enter-details">Please fill in your details</p>

        <div className="form-container">
          <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
          <input
            type="tel"
            placeholder="Phone Number"
            value={phone}
            onChange={(e) => setPhone(e.target.value.replace(/\D/g, ""))}
            pattern="[0-9]*"
            inputMode="numeric"
          />
          <input type="text" placeholder="Designation" value={designation} onChange={(e) => setDesignation(e.target.value)} />
          <input type="email" placeholder="Email address" value={email} onChange={(e) => setEmail(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          
          {error && <p className="error-message">{error}</p>}

          <button onClick={handleRegister} className="register-button">Register</button>
          <p className="login-link" onClick={onRegister}>Already have an account? Login</p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
