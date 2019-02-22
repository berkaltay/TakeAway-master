# TesterAssignment
Backend and Frontend check

## Getting Started

User may put all the available source code to an IDE for testing purposes, Maven usage is supported.

### Prerequisites

```
JAVA JDK 1.8
MAVEN
an IDE (IntelliJ is preferred)

```

### Installing

JDK should be installed, JAVA_HOME and MAVEN_HOME should be configured properly.

## Running the tests
Before running the test make sure you set the localhost port number to your choice in config.properties
file which is under src/main/resources folder

Use MAVEN parameters to run all the tests available and run from console

```
mvn clean test -Dremote=false -DreTry=0 -Dbrowser -DgridUrl -Denv
```
## Tech Stack
* Written in JAVA
* Used MAVEN for build management
* TestNG for test orchestration and assertion
* Hamcrest for assertion
* Extent Reports 3.15 for reporting
* Jersey for webservices
* Mockito for unit tests

## Functionalities available
* Different browser can be selected with -Dbrowser(chrome and firefox available)
* ReTry mechanism implemented, you may choose the number to retry the failed or ignored tests with -DreTry
* Switching between local and remote runs available with -Dremote
* Running on remote is supported with -DgridUrl for Selenium Grid

## Reporting

Reports can be found under reports folder with the date at which test is run. In the same report appending is allowed which means you may see FrontEnd, BackEnd and UnitTest at the same time. Every new run will create another report appended to the same report. If any failures it will automatically log failure in the report by using Listeners.

## Unit tests documentation

Documentation is handled as comments in WaesheroesServiceClientTest class

## Suggestions for improvements

TestNG XML could be used to handle parameters in a file. And parallel runs could also be triggered by the use of TestNG XML



