# Site Survey Tool

The Site Survey Tool is a web-based application developed to manage and perform site surveys for buildings and telecom infrastructure in an organized and efficient manner.

## Project Overview

This application allows users to manage properties, buildings, floors, and spaces, and perform survey checklists for selected spaces. The system supports draft and final submission of surveys along with PDF report generation.

## Features

- Property, Building, Floor, and Space management
- Hierarchical navigation:
  - Properties → Buildings → Floors → Spaces
- Floor plan upload and preview
- Space listing and selection
- Survey checklist workflow:
  - Save as Draft
  - Submit Survey
- Checklist history with status tracking
- PDF report generation for submitted surveys

## Tech Stack

- **Backend**: Spring Boot, Spring Data JPA, Hibernate
- **Frontend**: HTML, CSS, JavaScript
- **Database**: MySQL
- **Tools**: Spring Tool Suite (STS), MySQL Workbench


## Project Structure
site-survey-tool/
├── backend/ # Spring Boot backend application
├── frontend/ # HTML, CSS, JavaScript frontend
├── database/ # MySQL SQL dump (schema and data)
└── README.md

## How to Run the Project

## Prerequisites

- Java 17 or later
- MySQL Server
- Spring Tool Suite (STS) / Eclipse
- MySQL Workbench
- Web browser (Chrome / Edge)

### Backend Setup (Spring Boot)

1. Open **MySQL Workbench**
2. Create a database:
   ```sql
   CREATE DATABASE site_survey;


3. Import the SQL file:
   -Go to **Server → Data** Import
   -Select **Import from Self-Contained File**
   -Choose database/site_survey.sql
   -Select the site_survey database
   -Click Start Import

4. Open the backend folder in Spring Tool Suite / Eclipse

5. Update database credentials in:
     src/main/resources/application.properties
     Example:
     spring.datasource.url=jdbc:mysql://localhost:3306/site_survey
     spring.datasource.username=root
     spring.datasource.password=your_password


6. Run the Spring Boot application:
   -Right click on main class
   -Select Run As → Spring Boot App

7. Backend will start at:
   http://localhost:8082

**Frontend Setup**
1. Open the frontend/html folder
2. Open index.html or login.html in a web browser
3. Use the application UI to:
   -Login
   -View Properties → Buildings → Floors
   -View and select Spaces
   -Fill and submit survey checklists

## Usage Flow

1. Login to the application
2. Navigate through Properties → Buildings → Floors
3. Upload and view floor plans
4. View and select spaces
5. Fill survey checklist and save as draft
6. Submit checklist and download PDF report

## Author
**Sowjanya**

