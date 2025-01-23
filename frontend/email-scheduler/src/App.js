import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import "./styles/App.css";
import LoginPage from "./components/LoginPage";
import Dashboard from "./components/Dashboard";
import SingleMailForm from "./components/SingleMailForm";
import ExcelUpload from "./components/ExcelUpload";
import LiveView from "./components/LiveView";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [sessionChecked, setSessionChecked] = useState(false); // Track if session check is complete

  // State to hold form data for live preview
  const [formData, setFormData] = useState({
    companyName: "",
    recipientEmail: "",
    dateTime: "",
    salutation: "",
    yourName: "",
    yourDesignation: "",
    yourPhoneNumber: "",
  });

  // State to track whether it's a follow-up email or not
  const [isFollowUp, setIsFollowUp] = useState(false);

  // Load login state from localStorage when the app loads
  useEffect(() => {
    const sessionToken = localStorage.getItem("sessionToken");
    const tokenExpiry = localStorage.getItem("tokenExpiry");

    if (sessionToken && tokenExpiry) {
      const now = new Date().getTime();
      if (now < parseInt(tokenExpiry)) {
        validateSessionToken(sessionToken)
          .then((isValid) => {
            if (isValid) {
              setIsLoggedIn(true);
            } else {
              clearSession();
            }
          })
          .catch(() => clearSession())
          .finally(() => setSessionChecked(true)); // Mark session check as complete
      } else {
        clearSession();
        setSessionChecked(true);
      }
    } else {
      setSessionChecked(true);
    }
  }, []);

  // Function to handle login
  const handleLogin = (loginState, sessionToken) => {
    setIsLoggedIn(loginState);
    if (loginState) {
      const tokenExpiry = new Date().getTime() + 15 * 60 * 1000; // Token valid for 15 min
      localStorage.setItem("sessionToken", sessionToken);
      localStorage.setItem("tokenExpiry", tokenExpiry);
    } else {
      clearSession();
    }
  };

  // Simulated function to validate session token
  const validateSessionToken = async (token) => {
    // Replace this with actual API validation logic
    return new Promise((resolve) => {
      setTimeout(() => resolve(token === "valid_token"), 500); // Simulate a valid token
    });
  };

  // Function to clear session
  const clearSession = () => {
    localStorage.removeItem("sessionToken");
    localStorage.removeItem("tokenExpiry");
    setIsLoggedIn(false);
  };

  // Render nothing until session validation is complete
  if (!sessionChecked) {
    return null; // Render nothing
  }

  return (
    <Router>
      <div className="email-scheduler-container">
        <header className="header">
          <h1>Email Scheduler</h1>
        </header>
        <Routes>
          {/* Login Page */}
          {!isLoggedIn && (
            <Route
              path="*"
              element={<LoginPage onLogin={(state) => handleLogin(state, "valid_token")} />}
            />
          )}

          {/* Protected Routes */}
          {isLoggedIn && (
            <>
              {/* Dashboard Page */}
              <Route path="/dashboard" element={<Dashboard />} />

              {/* Main Page */}
              <Route
                path="/"
                element={
                  <div className="content-section">
                    {/* Left Column */}
                    <div className="left-column">
                      <SingleMailForm
                        formData={formData}
                        setFormData={setFormData}
                        isFollowUp={isFollowUp}
                        setIsFollowUp={setIsFollowUp}
                      />
                      <ExcelUpload />
                    </div>

                    {/* Right Column */}
                    <div className="right-column">
                      <LiveView formData={formData} isFollowUp={isFollowUp} />
                    </div>
                  </div>
                }
              />

              {/* Redirect invalid routes for logged-in users */}
              <Route path="*" element={<Navigate to="/" />} />
            </>
          )}
        </Routes>
      </div>
    </Router>
  );
};

export default App;
