import React, { useState } from "react";
import "../styles/ExcelUpload.css";

const ExcelUpload = () => {
  const [file, setFile] = useState(null);

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setFile(selectedFile);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!file) {
      alert("Please upload an Excel file.");
      return;
    }
    console.log("File uploaded:", file);
    alert("Excel file uploaded successfully!");
    
  };

  return (
    <div className="upload-container">
      <h3>Schedule Bulk Emails via Excel</h3>
      <input type="file" accept=".xls, .xlsx" onChange={handleFileChange} />
      <button onClick={handleSubmit}>Upload</button>
    </div>
  );
};

export default ExcelUpload;
