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
    const loggedIn = localStorage.getItem("isLoggedIn") === "true";
    setIsLoggedIn(loggedIn);
  }, []);

  // Function to handle login (sets both state and localStorage)
  const handleLogin = (loginState) => {
    setIsLoggedIn(loginState);
    localStorage.setItem("isLoggedIn", loginState);
  };

  return (
    <Router>
      <div className="email-scheduler-container">
        {isLoggedIn ? (
          <>
            {/* Header */}
            <header className="header">
              <h1>Email Scheduler</h1>
            </header>

            {/* Routes for Logged-In Users */}
            <Routes>
              {/* Dashboard Page */}
              <Route path="/dashboard" element={<Dashboard />} />

              {/* Main Page with SingleMailForm, ExcelUpload, and LiveView */}
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

              {/* Redirect any other path to the main page */}
              <Route path="*" element={<Navigate to="/" />} />
            </Routes>
          </>
        ) : (
          // Show Login Page if not logged in
          <Routes>
            <Route path="*" element={<LoginPage onLogin={handleLogin} />} />
          </Routes>
        )}
      </div>
    </Router>
  );
};

export default App;
