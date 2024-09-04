# Java-Based Web Server Project

This project is a Java-based web server that serves HTML pages and static files and provides an Inversion of Control (IoC) framework for building web applications from Plain Old Java Objects (POJOs). It includes custom annotations similar to those in the Spring framework, and it handles both static file requests and RESTful API requests.


![Demo GIF](https://github.com/alexandrac1420/Servidores_Aplicaciones/blob/master/Pictures/gif%20(1).gif)


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to install the following tools and configure their dependencies:

1. **Java** (versions 7 or 8)
    ```sh
    java -version
    ```
    Should return something like:
    ```sh
    java version "1.8.0"
    Java(TM) SE Runtime Environment (build 1.8.0-b132)
    Java HotSpot(TM) 64-Bit Server VM (build 25.0-b70, mixed mode)
    ```

2. **Maven**
    - Download Maven from [here](http://maven.apache.org/download.html)
    - Follow the installation instructions [here](http://maven.apache.org/download.html#Installation)

    Verify the installation:
    ```sh
    mvn -version
    ```
    Should return something like:
    ```sh
    Apache Maven 3.2.5 (12a6b3acb947671f09b81f49094c53f426d8cea1; 2014-12-14T12:29:23-05:00)
    Maven home: /Users/dnielben/Applications/apache-maven-3.2.5
    Java version: 1.8.0, vendor: Oracle Corporation
    Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0.jdk/Contents/Home/jre
    Default locale: es_ES, platform encoding: UTF-8
    OS name: "mac os x", version: "10.10.1", arch: "x86_64", family: "mac"
    ```

3. **Git**
    - Install Git by following the instructions [here](http://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

    Verify the installation:
    ```sh
    git --version
    ```
    Should return something like:
    ```sh
    git version 2.2.1
    ```

### Installing

1. Clone the repository and navigate into the project directory:
    ```sh
    git clone https://github.com/alexandrac1420/Servidores_Aplicaciones.git

    cd Servidores_Aplicaciones
    ```

2. Build the project:
    ```sh
    mvn package
    ```

    Should display output similar to:
    ```sh
      [INFO] --- jar:3.3.0:jar (default-jar) @ Spark ---
      [INFO] Building jar: C:\Users\alexa\OneDrive\Escritorio\Servidores_Aplicaciones\target\SpringEci-1.0-SNAPSHOT.jar
      [INFO] BUILD SUCCESS
    ```

3. Run the application:
    ```sh
    java -cp target/SpringEci-1.0-SNAPSHOT.jar edu.escuelaing.arep.SimpleWebServer 

    ```
    When running the application, the following message should appear

    ```
    Ready to receive on port 8080...
    ```

    And now you can access `index.html` and other static files in http://localhost:8080/index.html

### Usage

When you access [http://localhost:8080/index.html](http://localhost:8080/index.html), you will be greeted by a webpage that allows you to interact with various services provided by the server. The page is designed with a clean and organized layout, featuring sections for different services, each with their own functionality. Here’s a detailed explanation of what you’ll find and how to use each service:

#### 1. Current Time Service
![image](https://github.com/user-attachments/assets/a71f3f5a-3b45-4381-bb7d-bb830483ea91)

- **Description:** This section provides the current time.
- **How to Use:** Click the "Get Current Time" button.
- **Expected Behavior:** The server will respond with the current time.
- **Notes:** No additional input is required for this service.

#### 2. Hello Service
![image](https://github.com/user-attachments/assets/e32d1fba-1825-4521-a52b-c2b6d44e7e69)
![image](https://github.com/user-attachments/assets/c302c266-edcf-48f6-acdd-0594356b2065)

- **Description:** This service returns a personalized greeting based on the name provided.
- **How to Use:**
  - Enter your name in the text input field labeled "Type your name here...".
  - Click the "Get Greeting" button.
- **Expected Behavior:** The server will respond with a greeting that includes the name you entered (e.g., "Hello Maria").
- **Default Behavior:** If no name is provided, the server will default to "World" (e.g., "Hello World").

#### 3. Pi Service

![image](https://github.com/user-attachments/assets/0912aa50-48bb-4bc8-9021-921ef73fbde1)
![image](https://github.com/user-attachments/assets/cded0e19-2462-475a-ac2e-139a573f8a58)

- **Description:** This service returns the value of π (pi) with a specified number of decimal places.
- **How to Use:**
  - Enter the number of decimal places you want in the input field labeled "Decimals...".
  - Click the "Get Pi Value" button.
- **Expected Behavior:** The server will respond with the value of π rounded to the specified number of decimal places (e.g., "3.14159" for 5 decimals).
- **Default Behavior:** If no number of decimals is provided, the server will default to 2 decimal places (e.g., "3.14").

#### 4. Random Number Service
![image](https://github.com/user-attachments/assets/2a7ca542-3c37-400f-9b13-8f1240d57de4)
![image](https://github.com/user-attachments/assets/b17eabd2-d0fb-468e-a121-b4ad4fc00aa4)

- **Description:** This service returns a random number within a specified range.
- **How to Use:**
  - Enter the minimum value in the input field labeled "Min value...".
  - Enter the maximum value in the input field labeled "Max value...".
  - Click the "Get Random Number" button.
- **Expected Behavior:** The server will respond with a random number between the provided minimum and maximum values.
- **Default Behavior:** If no values are provided, the server will default to a minimum value of 0 and a maximum value of 1.

Each service's result will be displayed directly on the page in the corresponding result area below the button used to make the request. If the server encounters any issues or the request parameters are invalid, appropriate error messages will be displayed.

    
## Architecture

![Architecture Diagram](https://github.com/alexandrac1420/Servidores_Aplicaciones/blob/master/Pictures/Arquitectura.png)


### Overview

The web server architecture consists of several key components: a browser interface for static file requests, a web server to handle HTTP requests, and various services that provide RESTful API functionality. The server handles both static file requests (e.g., HTML, CSS, JS, images) and dynamic RESTful API requests.

### Components

- **Browser:** The client interface where users interact with static files such as HTML, CSS, and JavaScript. The browser also sends requests to the server for RESTful services.
- **Web Server:** Manages incoming HTTP requests, serves static files, and handles RESTful API requests. The main class `SimpleWebServer` oversees server operations and service management.
- **Services:** Implemented to provide various functionalities:
  - `CurrentTimeService`: Returns the current time.
  - `HelloService`: Provides personalized greetings.
  - `PiService`: Returns the value of π with a specified number of decimal places.
  - `RandomService`: Generates random numbers within a specified range.

### Interaction Flow

1. The **Client** (user's browser) accesses static files (HTML, CSS, JS, images) from the **Web Server** via port 8080.
2. The **Web Server** handles requests for static files through `ClientHandler`.
3. For RESTful API requests, the `ClientHandler` parses the request and routes it to the appropriate service.
4. Each service processes the request and returns a response to the **Web Server**.
5. The **Web Server** prepares the HTTP response and sends it back to the **Client**.


## Class Diagram

![Class Diagram](https://github.com/alexandrac1420/Servidores_Aplicaciones/blob/master/Pictures/diagramaClases.png)

### Overview

The class diagram depicts the main classes and their relationships within the web server project. It includes custom annotations, service classes, and the server's core components.

### Class Descriptions

- **Annotations** (package `edu.escuelaing.arep.Annotations`):
  - `GetMapping`: Maps HTTP GET requests to specific methods.
  - `RequestMapping`: Maps HTTP requests to specific methods.
  - `RequestParam`: Defines parameters for request methods with optional default values.
  - `RestController`: Marks a class as a REST controller.

- **Services** (package `edu.escuelaing.arep.Services`):
  - `CurrentTimeService`: Provides the current time.
  - `HelloService`: Returns a personalized greeting.
  - `PiService`: Returns the value of π with specified decimal places.
  - `RandomService`: Generates random numbers within a given range.

- **Server Components** (package `edu.escuelaing.arep`):
  - `SimpleWebServer`: Manages server operations, including static file serving and service handling.
  - `ClientHandler`: Handles HTTP requests, processes GET and POST requests, and interacts with services.


## Test Report - Web Framework for REST Services and Static File Management

### Author
- **Name**: Alexandra Cortes Tovar

### Date
- **Date**: 04/09/2024

### Summary

This report summarizes the unit tests conducted on the web server project, covering various functionalities including service responses and static file handling.

### Tests Conducted

1. **Test Hello Service Response**
   - **Description:** Verifies that the `HelloService` returns a personalized greeting.
   - **Objective:** Ensure the server responds with the correct greeting based on the input name.
   - **Testing Scenario:** Request `/app/hello?name=Maria`.
   - **Expected Behavior:** Response contains "Hello Maria".
   - **Outcome:** Passed
   - **Verification:** Verified that the response text contains the correct greeting.

2. **Test Current Time Service Response**
   - **Description:** Verifies that the `CurrentTimeService` returns the current time.
   - **Objective:** Ensure the server responds with the current time.
   - **Testing Scenario:** Request `/app/current-time`.
   - **Expected Behavior:** Response contains "Current time is:".
   - **Outcome:** Passed
   - **Verification:** Verified that the response text contains the current time message.

3. **Test Pi Service Response**
   - **Description:** Verifies that the `PiService` returns the value of π with specified decimals.
   - **Objective:** Ensure the server responds with the correct value of π.
   - **Testing Scenario:** Request `/app/pi?decimals=5`.
   - **Expected Behavior:** Response contains "3.14159".
   - **Outcome:** Passed
   - **Verification:** Verified that the response text contains the correct value of π.

4. **Test Random Service Response**
   - **Description:** Verifies that the `RandomService` returns a random number within a specified range.
   - **Objective:** Ensure the server responds with a random number between min and max values.
   - **Testing Scenario:** Request `/app/random?min=1&max=10`.
   - **Expected Behavior:** Response contains "Random value between".
   - **Outcome:** Passed
   - **Verification:** Verified that the response text contains a random number within the specified range.

5. **Test Load Static File**
   - **Description:** Verifies that static files such as `index.html` are served correctly.
   - **Objective:** Ensure the server serves static files with a 200 OK status.
   - **Testing Scenario:** Request `/index.html`.
   - **Expected Behavior:** Response status is 200.
   - **Outcome:** Passed
   - **Verification:** Verified that the response status code is 200.

6. **Test Invalid Request**
   - **Description:** Verifies that invalid requests return a 404 error.
   - **Objective:** Ensure the server returns a 404 status for non-existent files.
   - **Testing Scenario:** Request `/nonexistentfile.html`.
   - **Expected Behavior:** Response status is 404.
   - **Outcome:** Passed
   - **Verification:** Verified that the response status code is 404.

7. **Test Multiple Connections**
   - **Description:** Verifies that the server can handle multiple simultaneous connections.
   - **Objective:** Ensure the server handles multiple requests without issues.
   - **Testing Scenario:** Concurrently request `/index.html` from multiple clients.
   - **Expected Behavior:** Server responds to all requests with correct content.
   - **Outcome:** Passed
   - **Verification:** Verified that all requests are handled correctly.
  
   
 ![image](https://github.com/user-attachments/assets/1184e116-fcb4-484e-9770-0d829d42d8ca)



## Built With


* [Maven](https://maven.apache.org/) - Dependency Management
* [Git](http://git-scm.com/) - Version Control System

## Versioning

I use [GitHub](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/alexandrac1420/Servidores_Aplicaciones.git).

## Authors

* **Alexandra Cortes Tovar** - [alexandrac1420](https://github.com/alexandrac1420)

## License

This project is licensed under the GNU
