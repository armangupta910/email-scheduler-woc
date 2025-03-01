package com.woc.emailscheduler;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ExcelFollowUpService{

    @Autowired
    private EmailSender emailSender;

    public void sendEmailsViaExcel(String filePath){

        try (FileInputStream fis = new FileInputStream(new File(filePath))){
            Workbook workbook =WorkbookFactory.create(fis);
            Sheet sheet =workbook.getSheetAt(0);
            Iterator<Row> rows =sheet.iterator();

            while(rows.hasNext()){
                Row row= rows.next();
                Cell toCell = row.getCell(0);
                //Cell salutationCell=row.getCell(1);
                Cell nameCell=row.getCell(1);
                Cell companyCell=row.getCell(2);
                //Cell designationCell=row.getCell(4);
                Cell phoneCell=row.getCell(3);
                // Cell subjectCell = row.getCell(6);

                if (toCell !=null &&  nameCell != null && companyCell!=null && phoneCell!=null &&
                        toCell.getCellType() == CellType.STRING &&
                        //subjectCell.getCellType() == CellType.STRING &&
                        nameCell.getCellType() == CellType.STRING &&
                        companyCell.getCellType() == CellType.STRING &&
                        phoneCell.getCellType() == CellType.STRING) {
                    String to = toCell.getStringCellValue();
                    //String subject = subjectCell.getStringCellValue();
                    //String salutation = salutationCell.getStringCellValue();
                    String name = nameCell.getStringCellValue();
                    //String designation = designationCell.getStringCellValue();
                    String company = companyCell.getStringCellValue();
                    String phone = phoneCell.getStringCellValue();

                    String emailText=String.format("Dear Team,\n" +
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
                    emailSender.sendSimpleEmail(to,"Invitation to Participate in Campus Placement",emailText);
                }
            }
        }  catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: "+e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: "+e.getMessage());
        }
    }
}
