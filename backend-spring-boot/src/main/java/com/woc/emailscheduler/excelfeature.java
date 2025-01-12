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
public class ExcelFeature{

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
                Cell salutationCell=row.getCell(1);
                Cell nameCell=row.getCell(2);
                Cell companyCell=row.getCell(3);
                Cell designationCell=row.getCell(4);
                Cell phoneCell=row.getCell(5);
               // Cell subjectCell = row.getCell(6);

                if (toCell !=null && salutationCell !=null && nameCell != null && companyCell!=null && designationCell!=null && phoneCell!=null &&
                        toCell.getCellType() == CellType.STRING &&
                        //subjectCell.getCellType() == CellType.STRING &&
                        salutationCell.getCellType() == CellType.STRING &&
                        nameCell.getCellType() == CellType.STRING &&
                        designationCell.getCellType() == CellType.STRING &&
                        companyCell.getCellType() == CellType.STRING &&
                        phoneCell.getCellType() == CellType.STRING) {
                    String to = toCell.getStringCellValue();
                    //String subject = subjectCell.getStringCellValue();
                    String salutation = salutationCell.getStringCellValue();
                    String name = nameCell.getStringCellValue();
                    String designation = designationCell.getStringCellValue();
                    String company = companyCell.getStringCellValue();
                    String phone = phoneCell.getStringCellValue();

                    String emailText=String.format("Dear %s,\n\n" +
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
