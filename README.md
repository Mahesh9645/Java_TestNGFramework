# FitPeo Automation Project

## Overview
This project automates the functional testing of the FitPeo website using Selenium WebDriver. It supports various browsers and includes tests for verifying titles, adjusting sliders, interacting with checkboxes, and validating recurring reimbursement amounts.

---

## Features
- **Cross-Browser Support**: Works with Chrome, Edge, and Firefox.
- **Dynamic Element Handling**: Uses resilient XPath selectors for web element interactions.
- **Functional Tests**:
  - Title and URL verification.
  - Slider adjustments.
  - Checkbox interactions.
  - Reimbursement validation.
- **Screenshots**: Captures screenshots of key actions.
- **Modular Design**: Centralized configurations and reusable methods.

---

## Prerequisites
1. **Java**: Install Java JDK 8 or higher. Ensure `JAVA_HOME` is set.
2. **Maven**: Install Apache Maven 3.x or higher.
3. **Browser**: Chrome, Edge, or Firefox.
4. **Internet Connection**: Required for dependency downloads and test execution.

---
SetUp Instructions:

1.Update maven Project-->mavan clean -->maven install
2.Change config.properties as requirment (browser and Url)
3.All files located in \src\test\java\Launch package & its based on TestNG framework
4.
