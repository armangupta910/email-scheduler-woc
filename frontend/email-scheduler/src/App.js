import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate, useNavigate } from "react-router-dom";
import "./styles/App.css";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import Dashboard from "./components/Dashboard";
import SingleMailForm from "./components/SingleMailForm";
import ExcelUpload from "./components/ExcelUpload";
import LiveView from "./components/LiveView";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isRegistered, setIsRegistered] = useState(false);
  const [sessionChecked, setSessionChecked] = useState(false);

  const [formData, setFormData] = useState({
    companyName: "",
    recipientEmail: "",
    dateTime: "",
    salutation: "",
    yourName: "",
    yourDesignation: "",
    yourPhoneNumber: "",
  });

  const [isFollowUp, setIsFollowUp] = useState(false);

  useEffect(() => {
    const sessionToken = localStorage.getItem("sessionToken");
    const tokenExpiry = localStorage.getItem("tokenExpiry");
    const userRegistered = localStorage.getItem("userRegistered");

    if (userRegistered) {
      setIsRegistered(true);
    }

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
          .finally(() => setSessionChecked(true));
      } else {
        clearSession();
        setSessionChecked(true);
      }
    } else {
      setSessionChecked(true);
    }
  }, []);

  const handleRegister = () => {
    setIsRegistered(true);
    localStorage.setItem("userRegistered", "true");
  };

  const handleLogin = (loginState, sessionToken) => {
    setIsLoggedIn(loginState);
    if (loginState) {
      const tokenExpiry = new Date().getTime() + 60 * 60 * 1000;
      localStorage.setItem("sessionToken", sessionToken);
      localStorage.setItem("tokenExpiry", tokenExpiry);
    } else {
      clearSession();
    }
  };

  const validateSessionToken = async (token) => {
    return new Promise((resolve) => {
      setTimeout(() => resolve(token === "valid_token"), 500);
    });
  };

  const clearSession = () => {
    localStorage.removeItem("sessionToken");
    localStorage.removeItem("tokenExpiry");
    localStorage.removeItem("userRegistered");
    setIsLoggedIn(false);
    setIsRegistered(false);
  };

  if (!sessionChecked) {
    return null;
  }

  return (
    <Router>
      <div className="email-scheduler-container">
        <header className="header">
          <h1>Email Scheduler</h1>
        </header>

        {isLoggedIn && <DashboardLogoutButtons onLogout={clearSession} />}

        <Routes>
  {sessionChecked && !isRegistered && (
    <Route path="*" element={<RegisterPage onRegister={handleRegister} />} />
  )}
  {sessionChecked && isRegistered && !isLoggedIn && (
    <Route path="*" element={<LoginPage onLogin={(state) => handleLogin(state, "valid_token")} />} />
  )}
  {sessionChecked && isLoggedIn && (
    <>
      <Route path="/dashboard" element={<Dashboard />} />
      <Route
        path="/"
        element={
          <MainPage
            formData={formData}
            setFormData={setFormData}
            isFollowUp={isFollowUp}
            setIsFollowUp={setIsFollowUp}
          />
        }
      />
      <Route path="*" element={<Navigate to="/" />} />
    </>
  )}
</Routes>

      </div>
    </Router>
  );
};

const DashboardLogoutButtons = ({ onLogout }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    onLogout();
    navigate("/login");
  };

  return (
    <div className="dashboard-button-container">
      <button className="dashboard-button" onClick={() => navigate("/dashboard")}>Go to Dashboard</button>
      <button className="logout-button" onClick={handleLogout}>Logout</button>
    </div>
  );
};

const MainPage = ({ formData, setFormData, isFollowUp, setIsFollowUp }) => {
  return (
    <div className="content-section">
      <div className="left-column">
        <SingleMailForm
          formData={formData}
          setFormData={setFormData}
          isFollowUp={isFollowUp}
          setIsFollowUp={setIsFollowUp}
        />
        <ExcelUpload />
      </div>
      <div className="right-column">
        <LiveView formData={formData} isFollowUp={isFollowUp} />
      </div>
    </div>
  );
};

export default App;
