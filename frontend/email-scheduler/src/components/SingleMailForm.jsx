import React from "react";
import "../styles/SingleMailForm.css";

const SingleMailForm = ({ formData, setFormData, isFollowUp, setIsFollowUp }) => {
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "yourPhoneNumber") {
      if (!/^\d*$/.test(value)) {
        return; // Prevent invalid input
      }
    }
    setFormData({ ...formData, [name]: value });
  };

  const handleToggle = () => {
    setIsFollowUp(!isFollowUp); // Toggle the email type
  };

  const handleSubmit = async(e) => {
    e.preventDefault();

    const isFormIncomplete = Object.values(formData).some(
      (value) => value.trim() === ""
    );

    if (isFormIncomplete) {
      alert("The form is incomplete. Please fill in all fields.");
    } else {
      const emailType = isFollowUp ? "Follow-Up Email" : "Main Email";
      console.log("Form Data:", formData, "Email Type:", emailType);
      alert(`Your ${emailType} is scheduled.`);
      try {
      const response = await fetch("http://localhost:3000/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: formData.recipientEmail,
          salutation: formData.salutation,
          name: formData.yourName,
          designation: formData.yourDesignation,
          company: formData.companyName,
          phone: formData.yourPhoneNumber,
        }),
      });
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

        {/* Toggle Switch */}
        <div className="email-options">
          <label className="toggle-switch">
            <input
              type="checkbox"
              checked={isFollowUp} // Toggle state
              onChange={handleToggle} // Handle toggle change
            />
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
