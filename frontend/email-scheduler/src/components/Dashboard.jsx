import React from "react";
import "../styles/Dashboard.css";

const Dashboard = () => {
  return (
      <div className="dashboard">
      <h2>Dashboard</h2>
      <div className="stats-container">
        <div className="stat-box">
          <h3>Total Emails sent</h3>
          <p>0</p>
        </div>
        <div className="stat-box">
          <h3>Total Emails scheduled to be sent</h3>
          <p>0</p>
        </div>
        <div className="stat-box">
          <h3>Failed Emails</h3>
          <p>0</p>
        </div>
        <div className="stat-box">
          <h3>Graph of Emails sent</h3>
          <p></p>
        </div>
        <div className="stat-box">
          <h3>List and Graph of Emails sent per Company</h3>
          <p></p>
        </div>
        <div className="stat-box">
          <h3>Total Follow-up Emails sent</h3>
          <p>0</p>
        </div>
        <div className="stat-box">
          <h3>Total Follow-up Emails scheduled to be sent</h3>
          <p>0</p>
        </div>
        <div className="stat-box">
          <h3>Failed Follow-up Emails</h3>
          <p>0</p>
        </div>
        <div className="stat-box">
          <h3>Graph of Follow-up Emails sent</h3>
          <p></p>
        </div>
        <div className="stat-box">
          <h3>List and Graph of Follow-up Emails sent per Company</h3>
          <p></p>
        </div>
      </div>
    </div>
   
  );
};

export default Dashboard;
