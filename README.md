# JobFinder QA Automation Suite

## Overview

This project is a QA automation suite for a sample job search application. It includes automated UI tests and API tests to verify core application functionality, validate responses, and identify potential defects.

The project uses Java, Selenium WebDriver, JUnit 5, and REST Assured. Maven is used to manage dependencies and run tests, and GitHub Actions is configured to run the test suite automatically.

## Technologies Used

- **Java 17** – Programming language
- **Selenium WebDriver** – UI test automation
- **JUnit 5** – Test execution and assertions
- **REST Assured** – API testing
- **Maven** – Build and dependency management
- **GitHub Actions** – CI test execution

## Features Tested

### UI Testing
- Verify that the application loads successfully.
- Test job search functionality.
- Validate displayed job results.
- Check search behavior for different inputs.
- Verify basic page interactions.

### API Testing
- Validate API response status codes.
- Verify response data and structure.
- Test valid and invalid search inputs.
- Check behavior when no matching jobs are found.
- Validate error handling for unsuccessful requests.

## Project Structure

```text
jobfinder-qa-automation/
├── src/
│   ├── main/
│   │   └── java/          # Application source code
│   └── test/
│       └── java/          # UI and API automation tests
├── .github/
│   └── workflows/         # GitHub Actions workflow
├── pom.xml                # Maven configuration
└── README.md
```

## Prerequisites

Install the following before running the project:

- Java Development Kit (JDK) 17
- Apache Maven 3.8 or later
- Google Chrome browser

Check your Java and Maven installations:

```bash
java -version
mvn -version
```

## How to Run the Project

### 1. Clone the repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

### 2. Open the project directory

```bash
cd jobfinder-qa-automation
```

### 3. Start the application

```bash
mvn spring-boot:run
```

Keep this terminal open while the application is running.

### 4. Run the automated tests

Open a second terminal, navigate to the project directory, and run:

```bash
mvn test
```

Maven will compile the project and execute the UI and API test suites.

## Test Results

The initial test run completed successfully:

- **UI tests:** 5 passed
- **API tests:** 5 passed
- **Total:** 10 passed
- **Failures:** 0

Results may vary if the application, browser, or test environment changes.

## Continuous Integration

A GitHub Actions workflow is included to run the automated tests when configured workflow events occur. This helps identify test failures during development and provides a repeatable way to validate changes.

## Purpose

This project demonstrates practical QA automation skills, including UI testing, API validation, regression testing, test execution with Maven, and continuous integration.

## Disclaimer

This is a personal portfolio project built for learning and demonstration purposes. It is not affiliated with Jobright or any other job search company.