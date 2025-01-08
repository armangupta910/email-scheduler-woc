package com.woc.emailscheduler.controller;

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
public class ExelEmailsController{

    @Autowired
    private EmailSender emailSender; 

    /**
     * Endpoint to upload an Excel file and send emails
     *
     * @param file Multipart file containing Excel data
     * @return Response indicating success or failure
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendEmailsViaExcel(@RequestParam("file") MultipartFile file){
        try {
            if(!file.getOriginalFilename().endsWith(".xlsx")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to process the Excel file.");
            }

            InputStream inputStream =file.getInputStream();
            Workbook workbook= new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet("Sheet_Name");
            Iterator<Row> rows=sheet.iterator();
            rows.next();

            while(rows.hasNext()){
                Row row = rows.next();
                String to =row.getCell(0).getStringCellValue();
                String subject =row.getCell(1).getStringCellValue();
                String text =row.getCell(2).getStringCellValue();
                emailSender.sendEmail(to,subject,text);
            }
          
            workbook.close();
            return ResponseEntity.ok("Bulk Emails sent successfully.");

        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending email " + e.getMessage());
        }
    }
}

