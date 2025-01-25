package com.woc.emailscheduler.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_DETAILS")
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
    private String EmailId;
    private String Password;
    private String Designation;

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getName() {
  return Name;
 }

 public void setName(String name) {
  Name = name;
 }

 public String getPhoneNumber() {
  return PhoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
  PhoneNumber = phoneNumber;
 }

 public String getEmailId() {
  return EmailId;
 }

 public void setEmailId(String emailId) {
  EmailId = emailId;
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

 @Override
 public String toString() {
  return "RegistrationDetails{" +
          "Id=" + id +
          ", Name='" + Name + '\'' +
          ", PhoneNumber='" + PhoneNumber + '\'' +
          ", EmailId='" + EmailId + '\'' +
          ", Password='" + Password + '\'' +
          ", Designation='" + Designation + '\'' +
          '}';
 }
}

