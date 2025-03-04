package com.woc.emailscheduler.entity;
//reg
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class RegistrationDetails {
   /* Name
    Phone Number
    Email
    Password
    Designation*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Name;
    private String PhoneNumber;
    private String Designation;
    @Column(unique = true, nullable = false)
    private String emailId;
    private String Password;
    private Date createdAt;


 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getName() {return Name;}

 public void setName(String name) {Name = name;}

 public String getPhoneNumber() {
  return PhoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
  PhoneNumber = phoneNumber;
 }

 public String getEmailId() {
  return emailId;
 }

public void setEmailId(String mailId) { 
 emailId=mailId;
}

 public String getPassword() {
  return Password;
 }

 public void setPassword(String password) {
  Password = password;
 }

 public String getDesignation() {
  return Designation;
 }

 public void setDesignation(String designation) {
  Designation = designation;
 }

 /*@Override
 public String toString() {
  return "RegistrationDetails{" +
          "Id=" + id +
          ", Name='" + Name + '\'' +
          ", PhoneNumber='" + PhoneNumber + '\'' +
          ", EmailId='" + EmailId + '\'' +
          ", Password='" + Password + '\'' +
          ", Designation='" + Designation + '\'' +
          '}';
 }*/
}

