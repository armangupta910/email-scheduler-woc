package com.woc.emailscheduler;
import com.woc.emailscheduler.entity.RegistrationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailViaReg {

    @Autowired
    private JavaMailSender emailSender;

    // Define the email template as a constant
    private static final String EMAIL_TEMPLATE =
            "Dear sir,\n\n" +
                    "Greetings from IIT Jodhpur!\n\n" +
                    "On behalf of the Placement Cell at IIT Jodhpur, I, %s, %s, take this opportunity to invite %s to participate in our campus placement and internship season for the 2025 and 2026 batches, respectively.\n\n" +
                    "Since its inception in 2008, IIT Jodhpur has achieved several milestones and has always strived to push its limits in all spheres. The institute has a large pool of talented students pursuing their interests through a wide range of academic programs. Notably, IIT Jodhpur secured the 29th rank in NIRF 2024.\n\n" +
                    "IIT Jodhpur stands out with its Industry 4.0 curriculum, interdisciplinary projects, and mandatory courses in Machine Learning and Data Structures for all branches. Our students are actively engaged in various tech and non-tech clubs ensuring they are well-rounded and industry-ready.\n\n" +
                    "For more details, please feel free to reach out to me directly:\n" +
                    "Phone: %s\n\n" +
                    "Looking forward to your response.\n\n" +
                    "Warm Regards,\n" +
                    "%s\n" +
                    "Career Development Cell, IIT Jodhpur\n";

    public void sendEmail(RegistrationDetails rd) {
        String emailMessage = createEmailMessage(rd);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(rd.getEmailId()); // Send to the user's email
        message.setSubject("Invitation to Participate in Campus Placement");
        message.setText(emailMessage);

        emailSender.send(message);
    }

    public String createEmailMessage(RegistrationDetails rd) {

        String name = rd.getName();
        String designation = rd.getDesignation();
       // String company = rd.getCompany();
        String phone = rd.getPhoneNumber();


        return String.format(EMAIL_TEMPLATE, name, designation, phone, name);
    }
}
