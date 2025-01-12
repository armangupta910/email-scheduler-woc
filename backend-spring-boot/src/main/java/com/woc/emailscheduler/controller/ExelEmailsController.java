package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.EmailSender;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.Iterator;

@RestController
@RequestMapping("/email/bulk")
public class ExelEmailsController {

    @Autowired
    private EmailSender emailSender;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmailsViaExcel(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid file format. Please upload an .xlsx file.");
            }

            try (InputStream inputStream = file.getInputStream();
                 Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();

                // Skip header row if present
                if (rows.hasNext()) rows.next();

                while (rows.hasNext()) {
                    Row row = rows.next();

                    String to =row.getCell(0).getStringCellValue();
                    String salutation =row.getCell(1).getStringCellValue();
                    String name =row.getCell(2).getStringCellValue();
                    String company =row.getCell(3).getStringCellValue();
                    String designation =row.getCell(4).getStringCellValue();
                    String phone =row.getCell(5).getStringCellValue();


                        String emailText = String.format("Dear %s,\n\n" +
                                        "Greetings from IIT Jodhpur!\n\n" +
                                        "On behalf of the Placement Cell at IIT Jodhpur, I, %s, %s, take this opportunity to invite %s to participate in our campus placement and internship season for the 2025 and 2026 batches, respectively.\n\n" +
                                        "Since its inception in 2008, IIT Jodhpur has achieved several milestones and has always strived to push its limits in all spheres. The institute has a large pool of talented students pursuing their interests through a wide range of academic programs. Notably, IIT Jodhpur secured the 29th rank in NIRF 2024.\n\n" +
                                        "IIT Jodhpur stands out with its Industry 4.0 curriculum, interdisciplinary projects, and mandatory courses in Machine Learning and Data Structures for all branches. Our students are actively engaged in various tech and non-tech clubs ensuring they are well-rounded and industry-ready.\n\n" +
                                        "For more details, please feel free to reach out to me directly:\n" +
                                        "Phone: %s\n\n" +
                                        "Looking forward to your response.\n\n" +
                                        "Warm Regards,\n" +
                                        "%s\n" +
                                        "Career Development Cell, IIT Jodhpur\n",
                                salutation, name, designation, company, phone, name);
                        emailSender.sendSimpleEmail(to, "Invitation to Participate in Campus Placement", emailText);
                    }
                    //emailSender.sendSimpleEmail(to, subject, emailText);



                return ResponseEntity.ok("Bulk Emails sent successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error sending emails: " + e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending emails: " + e.getMessage());
        }
    }
}
