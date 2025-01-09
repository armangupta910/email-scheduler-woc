package com.woc.emailscheduler;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Service
public class excelfeature{
    
    @Autowired
    private EmailSender emailSender;
    
    public void sendEmailsViaExcel(String filePath){
          try (FileInputStream fis = new FileInputStream(new File(filePath))){
                 Workbook workbook =WorkbookFactory.create(fis);
                 Sheet sheet =workbook.getSheetAt(0); 
                 Iterator<Row> rows = sheet.iterator();
              
                 while(rows.hasNext()){
                       Row row= rows.next();
                       Cell toCell = row.getCell(0); 
                       Cell subjectCell = row.getCell(1); 
                       Cell textCell = row.getCell(2); 
                     
                       String to= toCell.getStringCellValue();
                       String subject= subjectCell.getStringCellValue();
                       String text =textCell.getStringCellValue();
                       emailSender.sendSimpleEmail(to,subject,text);
                 }
           }  catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }
 }
