# EMAIL SCHEDULER Project (WoC)

## API Endpoints

All API endpoints are prefixed with: `http://localhost:<portnumber>`.

## Methods for the Main Page

### 1). Schedule single Email

- **URL:** `/email/send`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "company":"<Company Name>",
    "dateTime":"<Date and Time in 24-hour format> 2024-12-19T10:06:00",
    "email":"<Recipient Email>",
    "salutation":"Mam",
    "name":"Sender's Name",
    "designation":"Sender's Designation",
    "phone":"Sender's Phone Number"
  }
  ```
- **Response:**
  - **Success (200):**
    ```json
    {
      "message":"Email Scheduled successfully!"
    }
    ```
  - **Error (400):**
    ```json
    {
      "message": "Invalid request data"
    }
    ```
    
### 2). Schedule Bulk Emails using an Excel Sheet
- **URL:** `/email/schedule/bulk`
- **Method:** `POST`
- **Headers:**
  - `Content-Type: multipart/form-data`
- **Request Body:**
  - **Key:** `file`
    - Upload an Excel file containing the following columns:
      - `to`: Recipient email address
      - `subject`: Email subject
      - `body`: Email body
- **Response:**
  - **Success (200):**
    ```json
    {
      "message": "Bulk emails scheduled successfully"
    }
    ```
  - **Error (400):**
    ```json
    {
      "message": "Failed to process the Excel file"
    }
    ```

### 3). Schedule a Single Follow-Up Email
- **URL:** `/email/schedule/followup`
- **Method:** `POST`
- **Headers:**
  - `Content-Type: application/json`
- **Request Body:**
  ```json
  {
    "company":"Google",
    "dateTime":"2024-12-19T10:06:00",
    "email":"guptaarman910@gmail.com",
    "salutation":"Mam",
    "name":"Arman Gupta",
    "designation":"Intern rep",
    "phone":"8273072067"
  }
  ```
- **Response:**
  - **Success (200):**
    ```json
    {
      "message": "Follow-up email scheduled successfully"
    }
    ```
  - **Error (400):**
    ```json
    {
      "message": "Invalid scheduling request"
    }
    ```

### 4). Schedule Bulk Follow-Up Emails using an Excel Sheet
- **URL:** `/email/schedule/followup/bulk`
- **Method:** `POST`
- **Headers:**
  - `Content-Type: multipart/form-data`
- **Request Body:**
  - **Key:** `file`
    - Upload an Excel file containing the following columns:
      - `to`: Recipient email address
      - `subject`: Email subject
      - `body`: Email body
      - `scheduledTime`: Time to send the follow-up email (in ISO format, e.g., `2024-12-31T10:00:00`)
- **Response:**
  - **Success (200):**
    ```json
    {
      "message": "Bulk follow-up emails scheduled successfully"
    }
    ```
  - **Error (400):**
    ```json
    {
      "message": "Failed to process the Excel file"
    }
    ```

## Methods for the Dashboard

### 1). Total Follow-up Emails sent
- **URL:** `/followup-emails/sent`
- **Method:** `GET`
- **Response:**
  - **Success (200):**
  ```json
   {
    "TotalSent": "No. of emails sent"
  }
  ```
### 2). Total Follow-up Emails scheduled to be sent
- **URL:** `/followup-emails/scheduled`
- **Method:** `GET`
- **Response:**
  - **Success (200):**
  ```json
   {
    "TotalScheduled": "No. of emails scheduled"
  }
  ```
### 3). Total Follow-up Emails failed
- **URL:** `/followup-emails/failed`
- **Method:** `GET`
- **Response:**
  - **Success (200):**
  ```json
   {
    "FailedEmails": "No. of emails failed"
  }
  ```
### 4). Total Emails sent
- **URL:** `/dashboard/sent`
- **Method:** `GET`
- **Response:**
  - **Success (200):**
  ```json
   {
    "TotalSent": "No. of total emails sent"
  }
  ```
### 5). Total Emails scheduled to be sent
- **URL:** `/dashboard/scheduled`
- **Method:** `GET`
- **Response:**
  - **Success (200):**
  ```json
   {
    "TotalScheduled": "No. of total emails scheduled to be sent"
  }
  ```
### 6). Total Failed Emails
- **URL:** `/dashboard/failed`
- **Method:** `GET`
- **Response:**
  - **Success (200):**
  ```json
   {
    "TotalFailed": "No. of total failed emails"
  }
  ```

### 7). List of emails sent to a particular Company
- **URL** `/emails/list`
- **Method** `POST`
- **Request Body:**
  ```json
  {
      "CompanyName":"Name of company"
  }
  ```
- **Response**
-  - **Success (200):**
    ```json
    {
      "emails":[
        {
         "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        },
        { "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        }
       ]
    }
    ```
  - **Error (400):**
    ```json
    {
      "message": "Invalid Comapny name"
    }
    ```
### 8). List of all Emails sent
- **URL** `/emails/all`
- **Method** `POST`
- **Request Body:**
  ```json
  {}
  ```
- **Response:**
-  -**Success (200):**
    ```json
    {
      "emails":[
        {
         "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        },
        { "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        }
       ]
    }
    ```
    -**Error (400):**
      ```json
     {
      "message": "Emails could not be fetched"
     }
     ```
### 7). List of Follow-up emails by Company
- **URL** `/followup/emails/list`
- **Method** `POST`
- **Request Body:**
  ```json
  {
      "CompanyName":"Name of company"
  }
  ```
  - **Response**
-  - **Success (200):**
    ```json
    {
     "emails":[
        {
         "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        },
        { "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        }
       ]
    }
    ```
   -**Error (400):**
      ```json
     {
      "message": "Invalid Company name"
     }
     ```  
   ### 8). List of all follow-up Emails
- **URL** `/followup/emails/all`
- **Method** `POST`
- **Request Body:**
  ```json
  {}
  ```
- **Response:**
-  -**Success (200):**
    ```json
    {
     "emails":[
        {
         "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        },
        { "emailObject": "Will return the emailObject which needs to be handled on the frontend",
         "status": "Delivered/Undelivered"
        }
       ]
    }
    ```
   -**Error (400):**
      ```json
     {
      "message": "Emails could not be fetched"
     }
     ```
