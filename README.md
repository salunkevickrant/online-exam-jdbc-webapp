# Online Exam JDBC Web Application

A Java web application for managing an online examination database using JDBC, Jakarta Servlets, JSP, HikariCP connection pooling, and H2 Database.

This project was built as an academic case study to demonstrate how core Java web technologies can be used to develop a structured database-driven application for question management, student response recording, and performance reporting.

## Table of Contents

- [Project Overview](#project-overview)
- [Problem Statement](#problem-statement)
- [Objectives](#objectives)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [System Architecture](#system-architecture)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [Application Modules](#application-modules)
- [Connection Pooling](#connection-pooling)
- [User Interface](#user-interface)
- [How It Works](#how-it-works)
- [Setup and Installation](#setup-and-installation)
- [How to Run](#how-to-run)
- [Build the Project](#build-the-project)
- [Expected Output](#expected-output)
- [Sample Workflow](#sample-workflow)
- [Limitations](#limitations)
- [Future Enhancements](#future-enhancements)
- [Learning Outcomes](#learning-outcomes)
- [References](#references)
- 
## Project Overview

Online examination systems are widely used in schools, colleges, universities, and training organizations to manage assessment activities digitally. This project focuses on the administrative side of such a system.

The application allows an administrator to:

- create and maintain exam questions
- record student answers
- automatically evaluate correctness
- generate summary and student-wise performance reports

The application uses a layered design so that the user interface, request processing, and database logic remain clearly separated. It also integrates HikariCP connection pooling so that database access is more efficient than basic JDBC connection creation for every request.

## Problem Statement

Many small academic projects and institutions manage exam data manually or with loosely connected files and spreadsheets. This creates several issues:

- questions are difficult to organize and update
- student responses are not stored consistently
- result processing becomes slow
- reporting is difficult and repetitive
- raw JDBC code can become inefficient if connections are created repeatedly

This project solves these issues by providing a centralized Java web application backed by a relational database.

## Objectives

The main objectives of this project are:

- to develop a Java web application using JDBC
- to manage online exam questions in a relational database
- to record student responses and evaluate correctness automatically
- to generate meaningful reports from stored exam data
- to implement connection pooling for efficient database access
- to demonstrate a clean layered architecture using servlets, JSP, DAO classes, and SQL

## Key Features

- Add new exam questions with subject, difficulty, options, and correct answer
- View the complete question bank
- Delete questions safely along with related student responses
- Record student responses through a web form
- Automatically mark answers as correct or incorrect
- View recent student responses
- Generate report summaries such as:
  - total questions
  - total responses
  - average score percentage
  - top performer
  - student-wise performance
- Use HikariCP for JDBC connection pooling
- Automatically initialize database schema and sample seed data at startup

## Technology Stack

The project uses the following technologies:

- `Java` for backend development
- `Jakarta Servlet` for request handling
- `JSP` for rendering the web interface
- `JDBC` for direct database interaction
- `HikariCP` for connection pooling
- `H2 Database` as the relational database
- `Maven` for dependency management and build automation
- `Jetty` for running the application locally
- `HTML/CSS` for the frontend

## System Architecture

The application follows a layered architecture:

1. Presentation Layer  
   JSP pages render forms, tables, and reports.

2. Controller Layer  
   Servlets process requests, collect form data, and call DAO methods.

3. Persistence Layer  
   DAO classes handle SQL operations such as insert, fetch, delete, and reporting queries.

4. Database Layer  
   H2 stores questions and student responses in relational tables.

5. Infrastructure Layer  
   HikariCP manages pooled database connections and a startup listener initializes the schema.

### Architecture Flow

```text
User -> JSP Page -> Servlet -> DAO -> HikariCP DataSource -> H2 Database
```

## Project Structure

```text
online-exam-jdbc/
├── pom.xml
├── README.md
├── IEEE_Case_Study_Report.html
└── src/
    └── main/
        ├── java/
        │   └── com/example/exam/
        │       ├── config/
        │       │   ├── AppConfig.java
        │       │   ├── DataSourceProvider.java
        │       │   └── DatabaseBootstrap.java
        │       ├── dao/
        │       │   ├── QuestionDao.java
        │       │   ├── ResponseDao.java
        │       │   └── ReportDao.java
        │       ├── listener/
        │       │   └── ApplicationLifecycleListener.java
        │       ├── model/
        │       │   ├── Question.java
        │       │   ├── StudentResponse.java
        │       │   ├── ReportSummary.java
        │       │   └── StudentPerformance.java
        │       └── servlet/
        │           ├── BaseServlet.java
        │           ├── HomeServlet.java
        │           ├── QuestionServlet.java
        │           ├── ResponseServlet.java
        │           └── ReportServlet.java
        ├── resources/
        │   ├── application.properties
        │   ├── schema.sql
        │   └── seed.sql
        └── webapp/
            ├── assets/
            │   └── styles.css
            └── WEB-INF/
                └── views/
                    ├── questions.jsp
                    ├── responses.jsp
                    └── reports.jsp
```

## Database Design

The project uses two main database tables.

### 1. `questions`

Stores the question bank.

| Column | Description |
|---|---|
| `id` | Primary key |
| `subject` | Subject or topic name |
| `difficulty` | Difficulty level |
| `question_text` | Question statement |
| `option_a` | First option |
| `option_b` | Second option |
| `option_c` | Third option |
| `option_d` | Fourth option |
| `correct_option` | Correct answer |
| `created_at` | Creation timestamp |

### 2. `student_responses`

Stores student submissions.

| Column | Description |
|---|---|
| `id` | Primary key |
| `student_name` | Name of the student |
| `question_id` | Foreign key to `questions.id` |
| `selected_option` | Submitted option |
| `is_correct` | Correctness flag |
| `submitted_at` | Submission timestamp |

### Relationship

One question can have many student responses.

## Application Modules

### Question Management Module

This module allows the admin to:

- add new questions
- view all existing questions
- delete questions when they are no longer needed

The module is implemented using:

- `QuestionServlet`
- `QuestionDao`
- `questions.jsp`

### Response Management Module

This module allows the admin to:

- select a question
- enter a student name
- submit a selected option
- store the response in the database
- calculate correctness automatically

The module is implemented using:

- `ResponseServlet`
- `ResponseDao`
- `responses.jsp`

### Reporting Module

This module generates:

- total question count
- total response count
- average score percentage
- top performer details
- student-wise performance table

The module is implemented using:

- `ReportServlet`
- `ReportDao`
- `reports.jsp`

## Connection Pooling

The project uses `HikariCP` for connection pooling.

### Why connection pooling was used

In plain JDBC, creating a new connection for every request can increase overhead and reduce efficiency. Connection pooling solves this by reusing a set of already-created database connections.

### Benefits in this project

- better runtime efficiency
- reduced connection creation overhead
- cleaner centralized database configuration
- more realistic industry-style implementation

### Key class

- `src/main/java/com/example/exam/config/DataSourceProvider.java`

## User Interface

The application includes three main pages:

- `Questions Page`  
  Used to add, list, and delete questions.

- `Responses Page`  
  Used to record and review student responses.

- `Reports Page`  
  Used to view summary metrics and student performance.

The interface was later improved to make it more polished and more suitable for demonstration and academic submission.

## How It Works

### Step 1: Application Startup

When the application starts:

- configuration is loaded from `application.properties`
- HikariCP initializes the connection pool
- schema creation runs from `schema.sql`
- sample data loads from `seed.sql`

### Step 2: Question Creation

The administrator fills in the question form. The servlet receives the request and passes the data to `QuestionDao`, which inserts the record into the database.

### Step 3: Response Recording

The administrator selects a question and submits a student's answer. The DAO stores the response and evaluates correctness by comparing the selected option with the correct answer.

### Step 4: Report Generation

The report servlet fetches aggregated data from the database and forwards it to the report JSP for display.

## Setup and Installation

### Prerequisites

Make sure the following are installed:

- Java 21 or later
- Maven 3.8 or later

### Clone the Repository

```bash
git clone https://github.com/your-username/online-exam-jdbc-webapp.git
cd online-exam-jdbc-webapp
```

## How to Run

Run the application locally using Jetty:

```bash
mvn "-Dmaven.repo.local=.m2" jetty:run
```

Then open:

```text
http://localhost:8080/questions
```

### If port 8080 is already in use

Use a different port:

```bash
mvn "-Dmaven.repo.local=.m2" "-Djetty.http.port=8081" jetty:run
```

Then open:

```text
http://localhost:8081/questions
```

## Build the Project

To compile and package the project:

```bash
mvn "-Dmaven.repo.local=.m2" clean package
```

Generated artifact:

```text
target/online-exam-jdbc.war
```

## Expected Output

After running the project:

- the `Questions` page should show seeded questions
- you should be able to add new questions
- you should be able to delete questions
- you should be able to record student answers
- the `Reports` page should show summary statistics and performance tables

## Sample Workflow

### Add a Question

1. Open `/questions`
2. Fill in the question form
3. Click `Add Question`
4. Confirm the question appears in the table

### Record a Student Response

1. Open `/responses`
2. Enter student name
3. Select a question
4. Select an option
5. Click `Submit Response`
6. Confirm the entry appears in recent responses

### View Reports

1. Open `/reports`
2. Check:
   - total questions
   - total responses
   - average score
   - top performer
   - student-wise performance


## Limitations

This project currently has the following limitations:

- no authentication or user roles
- no student login workflow
- no timed exam session
- no persistent production database configuration
- no advanced analytics charts
- no automated test suite yet

## Future Enhancements

Possible future improvements include:

- admin and student login system
- timed online examination mode
- result export to PDF or Excel
- chart-based reporting dashboard
- subject-wise and difficulty-wise analytics
- MySQL or PostgreSQL integration
- REST API support
- automated unit and integration testing

## Learning Outcomes

This project demonstrates practical understanding of:

- Java web development using servlets and JSP
- JDBC-based CRUD operations
- relational database design
- SQL-based correctness evaluation
- DAO pattern implementation
- connection pooling with HikariCP
- debugging deployment and routing issues
- report generation using aggregate SQL queries

## References

1. M. R. Hameed and F. A. Abdullatif, "Online Examination System," *International Advanced Research Journal in Science, Engineering and Technology*, 2017.  
2. A. E. Fluck, C. Pullen, and B. Harper, "Flexible and secure computer-based assessment using a single zip disk," *Computers & Education*, 2008.  
3. H. M. Alessio et al., "A systematic review of online examinations," *Computers & Education*, 2020.  
4. H. Nie, "Design and Development of the Online Examination System Based on B/S Structure," 2014.  
5. H. Hartatik and S. Wulandari, "Web-Based Online Exam Information System," 2022.  
6. B. Yi and Y. Sun, "Enhancing Educational Outcomes with a High-performance Online Exam System," 2022.  
7. Jakarta Servlet Specification, Version 6.0.  
8. Oracle JDBC and DataSource documentation.  
9. HikariCP official repository and documentation.  
10. H2 Database official documentation.  
