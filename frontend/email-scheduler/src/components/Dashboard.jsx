import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Dashboard.css";

const Dashboard = () => {
  const navigate = useNavigate();
  const [emailStats, setEmailStats] = useState({
    totalEmailsSent: 0,
    totalEmailsScheduled: 0,
    failedEmails: 0,
    totalFollowupEmailsSent: 0,
    totalFollowupScheduled: 0,
    failedFollowupEmails: 0,
  });

  const [followupEmailDetails, setFollowupEmailDetails] = useState([]);
  const [emailDetails, setEmailDetails] = useState([]);
  const [expandedView, setExpandedView] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    companyName: "",
    email: "",
    dateTime: "",
    salutation: "",
    name: "",
    designation: "",
    phone: ""
  });

  useEffect(() => {
    fetch("http://localhost:8080/stats/counts")
      .then((response) => response.json())
      .then((data) => setEmailStats(data))
      .catch((error) => console.error("Error fetching email stats:", error));

    fetch("http://localhost:8080/stats/companies", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`, 
      },
    })
      .then((response) => response.json())
      .then((data) => setEmailDetails(data))
      .catch((error) => console.error("Error fetching company info:", error));

      fetch("http://localhost:8080/stats/companies/followup", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
        .then((response) => response.json())
        .then((data) => setFollowupEmailDetails(data))
        .catch((error) => console.error("Error fetching follow-up email info:", error));
  }, []);
  
  
  const [rescheduleType, setRescheduleType] = useState("");
  const handleRescheduleClick = (company, email, type) => {
    setFormData({
      companyName: company,
      email: email,
      dateTime: "",
      salutation: "",
      name: "",
      designation: "",
      phone: ""
    });
    setRescheduleType(type);
    setShowModal(true);
  };

  const handleSubmitReschedule = async () => {
  
    if (
      !formData.dateTime ||
      !formData.salutation.trim() ||
      !formData.name.trim() ||
      !formData.designation.trim() ||
      !formData.phone.trim()
    ) {
      alert("Please fill in all required fields.");
      return;
    }
  
    const apiUrl = rescheduleType === "followup"
  ? "http://localhost:8080/email/followup"
  : "http://localhost:8080/email/schedule";

  
    const payload = {
      company: formData.companyName,
      email: formData.email,
      scheduledTime: formData.dateTime,
      salutation: formData.salutation,
      name: formData.name,
      designation: formData.designation,
      phone: formData.phone
    };
  
    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(payload)
      });
  
      if (response.ok) {
        alert("Email successfully rescheduled!");
        setShowModal(false);
        setFormData({
          companyName: "",
          email: "",
          dateTime: "",
          salutation: "",
          name: "",
          designation: "",
          phone: ""
        });
      } else {
        const errorText = await response.text(); // fallback if not JSON
        alert("Error: " + errorText);
      }
    } catch (error) {
      console.error("Error submitting reschedule request:", error);
      alert("An error occurred while rescheduling.");
    }
  };
  

  const renderEmailTable = (title, emails, type) => {
    const hasData = emails.length > 0;
    const visibleEmails = expandedView ? emails : emails.slice(0, 5);

    return (
      <div className="other-stats">
        <div className="stats-box">
          <h3>{title}</h3>
          <div className={`table-container ${expandedView ? "scroll-enabled" : ""}`}>
            <table className="email-table">
              <thead>
                <tr>
                  <th>Company Name</th>
                  <th>Email ID</th>
                  <th>Last Scheduled Time</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {hasData ? (
                  visibleEmails.map((email) => (
                    <tr key={`${email.name}-${email.emailId}-${email.lastScheduledTime}`}>
                      <td>{email.name || "N/A"}</td>
                      <td>{email.emailId || "N/A"}</td>
                      <td>{email.lastScheduledTime || "N/A"}</td>
                      <td>
                        <button
                          className="reschedule-btn"
                          onClick={() => handleRescheduleClick(email.name, email.emailId, type)}
                        >
                          Reschedule
                        </button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4" style={{ textAlign: "center" }}>No data available</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
          {emails.length > 5 && (
            <div className="view-more-wrapper">
              <button className="view-more-btn" onClick={() => setExpandedView(!expandedView)}>
                {expandedView ? "View Less" : "View More"}
              </button>
            </div>
          )}
        </div>
      </div>
    );
  };

  return (
    <div className="dashboard">
      <button className="back-button" onClick={() => navigate("/")}>
        Back
      </button>

      <h2>Dashboard</h2>
      <div className="stats-container">
        <div className="stat-box"><h3>Total Emails Sent</h3><p>{emailStats.totalEmailsSent}</p></div>
        <div className="stat-box"><h3>Total Emails Scheduled</h3><p>{emailStats.totalEmailsScheduled}</p></div>
        <div className="stat-box"><h3>Failed Emails</h3><p>{emailStats.failedEmails}</p></div>
        <div className="stat-box"><h3>Total Follow-up Emails Sent</h3><p>{emailStats.totalFollowupEmailsSent}</p></div>
        <div className="stat-box"><h3>Total Follow-up Emails Scheduled</h3><p>{emailStats.totalFollowupScheduled}</p></div>
        <div className="stat-box"><h3>Failed Follow-up Emails</h3><p>{emailStats.failedFollowupEmails}</p></div>
      </div>

      {renderEmailTable("List of all emails sent", emailDetails, "normal")}
      {renderEmailTable("List of all follow-up emails sent", followupEmailDetails, "followup")}

      {/* Modal for Reschedule Form */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Reschedule Email</h3>
            <label>Company Name:
              <input type="text" value={formData.companyName} disabled />
            </label>
            <label>Email:
              <input type="email" value={formData.email} disabled />
            </label>
            <label>Date & Time:
              <input type="datetime-local" value={formData.dateTime} onChange={(e) => setFormData({...formData, dateTime: e.target.value})} />
            </label>
            <label>Salutation:
              <input type="text" value={formData.salutation} onChange={(e) => setFormData({...formData, salutation: e.target.value})} />
            </label>
            <label>Name:
              <input type="text" value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} />
            </label>
            <label>Designation:
              <input type="text" value={formData.designation} onChange={(e) => setFormData({...formData, designation: e.target.value})} />
            </label>
            <label>Phone:
              <input type="tel" value={formData.phone} onChange={(e) => setFormData({...formData, phone: e.target.value})} />
            </label>
            <div className="modal-actions">
              <button onClick={() => setShowModal(false)}>Cancel</button>
              <button onClick={handleSubmitReschedule}>Submit</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
