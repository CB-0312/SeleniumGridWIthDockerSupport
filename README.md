# Selenium Grid with Docker Support

A comprehensive test automation framework that integrates **Selenium Grid** with **Docker** support, enabling distributed and scalable automated testing. This project combines the power of Selenium WebDriver, Cucumber BDD, and Docker containerization for robust test automation.

## 📋 Overview

This project provides a production-ready setup for:
- **Distributed Testing**: Run tests in parallel across multiple machines using Selenium Grid
- **Containerized Environment**: Deploy and manage Selenium Grid nodes using Docker
- **BDD Approach**: Write tests using Cucumber Gherkin syntax for better readability
- **Advanced Reporting**: Generate detailed reports with Allure and ExtentReports
- **Scalability**: Easily scale your test infrastructure with Docker containers

## 🛠️ Tech Stack

| Component | Version |
|-----------|---------|
| **Java** | 21 |
| **Selenium WebDriver** | 4.40.0 |
| **Cucumber** | 7.11.0 |
| **JUnit** | 4.13.2 |
| **Allure Reporting** | 2.29.1 |
| **ExtentReports** | 5.0.9 |
| **Maven** | 3.6.3+ |
| **Docker** | Latest |

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21** or higher
  - [Download Java](https://www.oracle.com/java/technologies/downloads/)
  
- **Maven 3.6.3** or higher
  - [Download Maven](https://maven.apache.org/download.cgi)
  
- **Docker** (for containerized Selenium Grid)
  - [Download Docker](https://www.docker.com/products/docker-desktop)
  
- **Git** (for cloning the repository)
  - [Download Git](https://git-scm.com/downloads)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/CB-0312/SeleniumGridWIthDockerSupport.git
cd SeleniumGridWIthDockerSupport
```

### 2. Install Dependencies

```bash
mvn clean install
```

### 3. Start Selenium Grid with Docker

```bash
# Using Docker Compose (if docker-compose.yml exists)
docker-compose up -d

# Or start Selenium Grid Hub manually
docker run -d -p 4444:4444 --name selenium-hub selenium/hub

# Start Chrome nodes
docker run -d -p 5900 --shm-size=2gb -e SE_EVENT_BUS_HOST=<hub-ip> -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-chrome

# Start Firefox nodes
docker run -d -p 5900 --shm-size=2gb -e SE_EVENT_BUS_HOST=<hub-ip> -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-firefox
```

### 4. Run Tests

```bash
# Run all tests
mvn test

# Run specific test suite
mvn test -Dtest=TestRunner

# Run with specific browser
mvn test -Dbrowser=chrome

# Run with specific tags
mvn test -Dcucumber.filter.tags="@smoke"
```

## 📁 Project Structure

```
SeleniumGridWIthDockerSupport/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── [Application Code]
│   │   └── resources/
│   │
│   └── test/
│       ├── java/
│       │   ├── stepdefinitions/        # Cucumber step definitions
│       │   ├── runners/                # Test runners
│       │   ├── pages/                  # Page Object Model classes
│       │   └── utils/                  # Utility classes
│       │
│       └── resources/
│           └── features/               # Cucumber feature files (.feature)
│
├── SeleniumGRID/                      # Docker and Grid configuration
│
├── pom.xml                             # Maven project configuration
├── docker-compose.yml                  # Docker Compose configuration
├── README.md                           # This file
└── .gitignore                          # Git ignore file
```

## 🎯 Key Features

- ✅ **Selenium Grid Integration** - Distributed test execution across multiple nodes
- ✅ **Docker Support** - Containerized Selenium Grid for easy deployment
- ✅ **BDD with Cucumber** - Write tests in Gherkin language for better readability
- ✅ **Page Object Model** - Maintainable and scalable test structure
- ✅ **Multiple Reporting Options** - Allure and ExtentReports integration
- ✅ **Parallel Execution** - Run tests in parallel for faster results
- ✅ **Cross-Browser Testing** - Support for Chrome, Firefox, and Edge browsers
- ✅ **CI/CD Ready** - Easy integration with Jenkins, GitHub Actions, GitLab CI, etc.

## 🔧 Configuration

### Selenium Grid Hub Connection

Update your configuration file with the Selenium Grid Hub URL:

```properties
selenium.grid.url=http://localhost:4444
browser=chrome
```

### Docker Compose Example

Create a `docker-compose.yml` file to orchestrate your test environment:

```yaml
version: '3.8'
services:
  selenium-hub:
    image: selenium/hub:latest
    ports:
      - "4444:4444"
      - "4442:4442"
      - "4443:4443"

  chrome-node:
    image: selenium/node-chrome:latest
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443

  firefox-node:
    image: selenium/node-firefox:latest
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443
```

## 📊 Generating Reports

### Allure Reports

```bash
# Run tests with Allure
mvn clean test

# Generate Allure report
mvn allure:report

# Open Allure report
mvn allure:serve
```

### ExtentReports

ExtentReports are automatically generated in the `target/` directory after test execution.

## 🐳 Docker Commands

```bash
# Build custom Docker image
docker build -t selenium-grid-custom .

# Run Docker Compose
docker-compose up -d
docker-compose down

# View logs
docker-compose logs -f

# Stop containers
docker-compose stop
```

## 🔍 Running Specific Tests

### By Cucumber Tags

```bash
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression"
mvn test -Dcucumber.filter.tags="@smoke and @high-priority"
```

### By Feature File

```bash
mvn test -Dcucumber.features="src/test/resources/features/login.feature"
```

## 🐛 Troubleshooting

### Issue: Cannot connect to Selenium Grid Hub

**Solution:**
```bash
# Verify Selenium Grid Hub is running
docker ps | grep selenium

# Check network connectivity
ping localhost:4444
```

### Issue: Tests timeout with Docker Selenium

**Solution:**
- Increase the `shm-size` in Docker run command: `--shm-size=2gb`
- Ensure sufficient system resources are allocated

### Issue: Port already in use

**Solution:**
```bash
# Find process using port 4444
lsof -i :4444

# Kill the process
kill -9 <PID>
```

## 📝 Writing Tests

### Sample Cucumber Feature File

```gherkin
Feature: Login Functionality

  @smoke
  Scenario: User successfully logs in
    Given User navigates to the application
    When User enters valid credentials
    And User clicks the login button
    Then User should see the dashboard
```

### Sample Step Definition

```java
@Given("User navigates to the application")
public void user_navigates_to_application() {
    driver.get("https://example.com");
}
```

## 🚦 CI/CD Integration

### GitHub Actions Example

Create `.github/workflows/test.yml`:

```yaml
name: Test Suite
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Java
        uses: actions/setup-java@v2
        with:
          java-version: '21'
      - name: Run tests
        run: mvn clean test
```

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [Docker Documentation](https://docs.docker.com/)
- [Allure Reports](https://docs.qameta.io/allure/)
- [ExtentReports](https://www.extentreports.com/)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit issues and enhancement requests.

## 📄 License

This project is open source and available under the MIT License.

## 📧 Contact

For questions or support, please contact the project maintainer at:
- **GitHub**: [@CB-0312](https://github.com/CB-0312)

## 🔄 Changelog

### Version 1.0.0
- Initial release
- Selenium Grid with Docker support
- Cucumber BDD integration
- Allure and ExtentReports setup
- Cross-browser testing support

---

**Happy Testing! 🎉**
