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
public class ExelEmailsController{

    @Autowired
    private EmailSender emailSender; 

    @PostMapping("/send")
    public ResponseEntity<String> sendEmailsViaExcel(@RequestParam("file") MultipartFile file){
        try{
            if(!file.getOriginalFilename().endsWith(".xlsx")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to process the Excel file.");
            }

            try (InputStream inputStream =file.getInputStream();
                   Workbook workbook= new XSSFWorkbook(inputStream)){
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows=sheet.iterator();
            //rows.next();
            if (rows.hasNext()) rows.next();

            while(rows.hasNext()){
                Row row = rows.next();
                String to =row.getCell(0).getStringCellValue();
                String subject =row.getCell(1).getStringCellValue();
                String text =row.getCell(2).getStringCellValue();
                emailSender.sendSimpleEmail(to,subject,text);
            }
          
           // workbook.close();
            return ResponseEntity.ok("Bulk Emails sent successfully.");

        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending emails: " + e.getMessage());
        }
    }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending emails: " + e.getMessage());
        }
    }
}

