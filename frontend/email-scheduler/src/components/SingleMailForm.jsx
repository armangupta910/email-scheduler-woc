import React from "react";
import "../styles/SingleMailForm.css";

const SingleMailForm = ({ formData, setFormData, isFollowUp, setIsFollowUp }) => {
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "yourPhoneNumber" && !/^\d*$/.test(value)) {
      return; // Prevent invalid input for phone number
    }
    setFormData({ ...formData, [name]: value });
  };

  const handleToggle = () => {
    setIsFollowUp(!isFollowUp); // Toggle between main and follow-up email
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate form fields (ensure no empty fields)
    const isFormIncomplete = Object.values(formData).some((value) => value.trim() === "");
    if (isFormIncomplete) {
      alert("The form is incomplete. Please fill in all fields.");
      return;
    }

    // Retrieve token from localStorage
    const token = localStorage.getItem("token");
    if (!token) {
      alert("Authentication error: Please log in again.");
      return;
    }

    // Determine API endpoint
    const apiUrl = isFollowUp
      ? "http://localhost:8080/email/followup"
      : "http://localhost:8080/email/schedule";

    // Prepare request body
    const requestData = {
      company: formData.companyName,
      scheduledTime: formData.dateTime,
      email: formData.recipientEmail,
      salutation: formData.salutation,
      name: formData.yourName,
      designation: formData.yourDesignation,
      phone: formData.yourPhoneNumber,
    };

    try {
      console.log("Sending request to:", apiUrl, "with data:", requestData);
      
      const response = await fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`, // Include token in request headers
        },
        body: JSON.stringify(requestData),
      });

      let data;
      try {
        data = await response.json(); // Try parsing JSON response
      } catch (error) {
        console.error("Invalid JSON response from backend:", error);
        data = { error: "Invalid response from server. Please try again later." };
      }

      if (response.ok) {
        alert(data.message || "Email scheduled successfully!");
        console.log("Email scheduled successfully:", data);
      } else {
        alert(`Error: ${data.error || "Unknown error occurred"}`);
        console.error("Error scheduling email:", data);
      }
    } catch (error) {
      alert("A network error occurred. Please check your connection.");
      console.error("Network error:", error);
    }
  };

  return (
    <div className="single-mail-form-container">
      <h2>Schedule a Single Email</h2>
      <form onSubmit={handleSubmit} className="single-mail-form">
        <label>Company Name</label>
        <input
          type="text"
          name="companyName"
          placeholder="Enter company name"
          value={formData.companyName}
          onChange={handleChange}
        />

        <label>Recipient Email</label>
        <input
          type="email"
          name="recipientEmail"
          placeholder="Enter recipient email"
          value={formData.recipientEmail}
          onChange={handleChange}
        />

        <label>Date and Time</label>
        <input
          type="datetime-local"
          name="dateTime"
          value={formData.dateTime}
          onChange={handleChange}
        />

        <label>Salutation</label>
        <input
          type="text"
          name="salutation"
          placeholder="Enter salutation"
          value={formData.salutation}
          onChange={handleChange}
        />

        <label>Your Name</label>
        <input
          type="text"
          name="yourName"
          placeholder="Enter your name"
          value={formData.yourName}
          onChange={handleChange}
        />

        <label>Your Designation</label>
        <input
          type="text"
          name="yourDesignation"
          placeholder="Enter your designation"
          value={formData.yourDesignation}
          onChange={handleChange}
        />

        <label>Your Phone Number</label>
        <input
          type="tel"
          name="yourPhoneNumber"
          placeholder="Enter your phone number"
          value={formData.yourPhoneNumber}
          onChange={handleChange}
        />

        {/* Toggle Switch for Main/Follow-Up Email */}
        <div className="email-options">
          <label className="toggle-switch">
            <input type="checkbox" checked={isFollowUp} onChange={handleToggle} />
            <span className="slider"></span>
          </label>
          <span>{isFollowUp ? "Follow-Up Email" : "Main Email"}</span>
        </div>

        <button type="submit">Schedule Email</button>
      </form>
    </div>
  );
};

export default SingleMailForm;
