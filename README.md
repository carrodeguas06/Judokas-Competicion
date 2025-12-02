# Judokas-Competicion

![Build Status](https://img.shields.io/badge/Status-In%20Development-orange)
![Main Technology](https://img.shields.io/badge/Language-Java-blue)
![Build Tool](https://img.shields.io/badge/Build-Maven-red)

---

## 🥋 Project Description

**Judokas-Competicion** is an application designed for the management, simulation, or tracking of **Judo tournaments and competitions**. The main goal is to provide a robust tool for organizing and registering the matches, rankings, and data of the participating judokas.

**(Note: If the application's actual purpose is different (e.g., it's a web system with Spring Boot, a desktop application with JavaFX, etc.), please edit this paragraph to reflect its main functionality).**

---

## ⚙️ Technologies Used

The project is primarily developed in **Java** and utilizes the **Maven** dependency management and build system.

* **Primary Language:** Java (98.3%)
* **Style/Frontend (if applicable):** CSS (1.7%)
* **Dependency Manager and Build Tool:** Apache Maven
* **Additional Dependencies:** *`JavaFX`, `Hibernate`, `MySQL Connector`.*

---

## 🚀 Installation and Setup

Follow these steps to get a functional copy of the project running on your local machine.

### Prerequisites

You need the following installed on your system:

1.  **Java Development Kit (JDK):** Version 11 or higher (recommended).
2.  **Apache Maven:** Version 3.x.x.
3.  **Git:** To clone the repository.

### Installation Steps

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/carrodeguas06/Judokas-Competicion.git](https://github.com/carrodeguas06/Judokas-Competicion.git)
    cd Judokas-Competicion
    ```

2.  **Compile the Project with Maven:**
    Run the following command to download dependencies and compile the source code:
    ```bash
    mvn clean install
    ```
    *(This command will generate the executable JAR or WAR file in the `target/` directory.)*

3.  **Database Configuration (if applicable):**
    * Open your database manager and **initialize the script** located in the `resources` folder.

---

## 💻 Usage

### Running the Application

Once the project has been compiled (`mvn clean install`), you can run it from the command line (assuming it's an executable JAR file):

```bash
java -jar target/judokas-competicion-1.0-SNAPSHOT.jar 
# (Adjust the JAR filename as necessary)
