import React, { useState } from "react";
import "../styles/ExcelUpload.css";

const ExcelUpload = () => {
  const [file, setFile] = useState(null);
  const [isFollowUp, setIsFollowUp] = useState(false); // Toggle state (default: Main Email)

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setFile(selectedFile);
    }
  };

  const handleToggle = () => {
    setIsFollowUp(!isFollowUp); // Toggle between Main Email & Follow-Up Email
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!file) {
      alert("Please upload an Excel file.");
      return;
    }

    const token = localStorage.getItem("token");

    if (!token) {
      alert("You must be logged in to upload files.");
      return;
    }

    // Determine API endpoint based on toggle state
    const apiUrl = isFollowUp
      ? "http://localhost:8080/email/follow/send"
      : "http://localhost:8080/email/bulk/send";

    // Create FormData object to send the file
    const formData = new FormData();
    formData.append("file", file); // 'file' is the key required by the backend

    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        body: formData, // Send file data
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const contentType = response.headers.get("content-type");
      let responseText;

      if (contentType && contentType.includes("application/json")) {
        const data = await response.json();
        responseText = data.message || "Bulk emails scheduled successfully!";
      } else {
        responseText = await response.text(); // Read plain text response
      }

      alert(responseText);
      console.log("Server response:", responseText);
    } catch (error) {
      alert("Error: " + error.message);
      console.error("Network or Server Error:", error);
    }
  };

  return (
    <div className="upload-container">
      <h3>Schedule Bulk Emails via Excel</h3>

      {/* Toggle Switch */}
      <div className="bulk-options">
        <label className="toggle-switch">
          <input
            type="checkbox"
            checked={isFollowUp} // Toggle state
            onChange={handleToggle} // Handle toggle change
          />
          <span className="slider"></span>
        </label>
        <span className="text">{isFollowUp ? "Follow-Up Email" : "Main Email"}</span>
      </div>

      <input type="file" accept=".xls, .xlsx" onChange={handleFileChange} />
      <button onClick={handleSubmit}>Upload</button>
    </div>
  );
};

export default ExcelUpload;
