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
    @RequestMapping("/email/follow")
    public class FollowUpExcel {

        @Autowired
        private EmailSender emailSender;

        @PostMapping("/send")
        public ResponseEntity<String> sendEmailsViaExcel(@RequestParam("followfile") MultipartFile file) {
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
                        //String salutation =row.getCell(1).getStringCellValue();
                        String name =row.getCell(1).getStringCellValue();
                        String company =row.getCell(2).getStringCellValue();
                       // String designation =row.getCell(4).getStringCellValue();
                        String phone =row.getCell(3).getStringCellValue();


                        String emailText = String.format("Dear Team,\n" +
                                        "This is a follow-up email to our previous email regarding the invitation extended to %s for \"IIT Jodhpur's placement and internship season 2025-26\". Do let me know in case of any developments. We would like to empanel Verizon and establish a long-term association with you. In case of any query, do feel free to reach out to us.\n" +
                                        "\n" +
                                        "Look forward to hearing from you.\n" +
                                        "--\n" +
                                        "Warm Regards,\n" +
                                        "%s\n" +
                                        "Internship Coordinator\n" +
                                        "Career Development Cell | IIT Jodhpur\n" +
                                        "Contact : %s",
                                  company, name, phone);
                        emailSender.sendSimpleEmail(to, "Invitation to Participate in Campus Placement", emailText);
                    }
                    //emailSender.sendSimpleEmail(to, subject, emailText);



                    return ResponseEntity.ok("Bulk Follow Up Emails sent successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error sending follow up emails: " + e.getMessage());
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error sending follow up emails: " + e.getMessage());
            }
        }
    }
