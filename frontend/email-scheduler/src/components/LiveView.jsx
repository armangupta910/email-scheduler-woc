import React from "react";
import "../styles/LiveView.css";

const LiveView = ({ formData, isFollowUp }) => {
  const {
    companyName,
    salutation,
    yourName,
    yourDesignation,
    yourPhoneNumber,
  } = formData;

  return (
    <div className="live-view">
      <h2>Preview Email</h2>
      {isFollowUp ? (
        // Follow-Up Email Preview
        <div className="follow-up-email-preview">
          <p>Dear Team,</p>
          <p>
            This is a follow-up email to our previous email regarding the
            invitation extended to {companyName || "[your company]"} for "IIT Jodhpur's placement and
            internship season 2025-26". Do let me know in case of any
            developments. We would like to empanel {companyName || "[your company]"} and establish a
            long-term association with you. In case of any query, do feel free
            to reach out to us.
          </p>
          <p>Look forward to hearing from you.</p>
          <p>--</p>
          <p>
            Warm Regards, <br />
            {yourName || "[your Name]"} <br />
            {yourDesignation || "[your Designation]"} <br />
            Career Development Cell | IIT Jodhpur <br />
            Contact: {yourPhoneNumber || "N/A"}
          </p>
        </div>
      ) : (
        // Main Email Preview
        <div className="email-preview">
          <p>Dear {salutation || "[salutation]"},</p>
          <p>Greetings from IIT Jodhpur!</p>
          <p>
            On behalf of the Placement Cell at IIT Jodhpur, I,{" "}
            {yourName || "[your Name]"}, {yourDesignation || "[your Designation]"}, take
            this opportunity to invite {companyName || "[your company]"} to
            participate in our campus placement and internship season for the
            2025 and 2026 batches, respectively.
          </p>
          <p>
            Since its inception in 2008, IIT Jodhpur has achieved several
            milestones and has always strived to push its limits in all spheres.
            The institute has a large pool of talented students pursuing their
            interests through a wide range of academic programs. Notably, IIT
            Jodhpur secured the 29th rank in NIRF 2024.
          </p>
          <p>
            IIT Jodhpur stands out with its Industry 4.0 curriculum,
            interdisciplinary projects, and mandatory courses in Machine
            Learning and Data Structures for all branches. Our students are
            actively engaged in various tech and non-tech clubs ensuring they
            are well-rounded and industry-ready.
          </p>
          <p>
            <strong>Why Collaborate with IIT Jodhpur?</strong>
          </p>
          <ul>
            <li>
              <strong>Qualified Talent Pool:</strong> Our students undergo
              rigorous training and excel both academically and practically.
            </li>
            <li>
              <strong>Diverse Skill Sets:</strong> Programs offered include
              B.Tech, BS, M.Tech, M.Sc, Ph.D., and dual degrees across various
              departments.
            </li>
            <li>
              <strong>Innovative Learning:</strong> Our curriculum is updated
              with the latest industry trends and technologies, focusing on
              emerging domains like Artificial Intelligence, IoT, and
              Computational Sciences.
            </li>
            <li>
              <strong>Interdisciplinary Projects & Research:</strong> Students
              engage in projects that integrate multiple disciplines, preparing
              them for complex industry challenges.
            </li>
            <li>
              <strong>Active Clubs:</strong> Tech and non-tech clubs, such as
              Product, DevLup Labs, RAID, Robotics Society, and E-Cell,
              contribute to the holistic development of our students.
            </li>
          </ul>
          <p>
            <strong>PLACEMENTS</strong>
          </p>
          <table>
            <tr>
              <th>Programs Offered</th>
              <th>Available Batch Strength</th>
            </tr>
            <tr>
              <td>B.Tech</td>
              <td>440</td>
            </tr>
            <tr>
              <td>M.Tech</td>
              <td>210</td>
            </tr>
            <tr>
              <td>M.Sc</td>
              <td>80</td>
            </tr>
            <tr>
              <td>Tech MBA</td>
              <td>80</td>
            </tr>
          </table>
          <p>
            <strong>INTERNSHIPS</strong>
          </p>
          <table>
            <tr>
              <th>Programs Offered</th>
              <th>Available Batch Strength</th>
            </tr>
            <tr>
              <td>B.Tech</td>
              <td>500</td>
            </tr>
            <tr>
              <td>Tech MBA</td>
              <td>120</td>
            </tr>
          </table>
          <p>
            For more details, please find the brochure attached. We invite you
            to consider our students for both technical and non-technical roles.
            Kindly fill out and return the attached Job (JAF) / Internship (IAF)
            Announcement Form to expedite the process.
          </p>
          <p>
            We look forward to a long-term relationship with your organization.
            For any queries, feel free to contact me or our team.
          </p>
          <p>Warm Regards,</p>
          <p>
            {yourName || "[your Name]"} <br />
            {yourDesignation || "[your Designation]"} <br />
            Career Development Cell | IIT Jodhpur <br />
            Contact: {yourPhoneNumber || "N/A"}
          </p>
          <p>
            Alternate Contact - <br />
            Puneet Garg <br />
            Training & Placement Officer <br />
            Career Development Cell | IIT Jodhpur <br />
            Contact: +91 9815964823 , 0291-2801155
          </p>
        </div>
      )}
    </div>
  );
};

export default LiveView;
