# Quiz Application

This is a Spring Boot application for managing quizzes. It allows users to create, update, and view quizzes and their questions.

## Features

- User registration and login
- Create and update quizzes
- View existing quizzes and questions
- Submit quizzes

## Technologies Used

- Spring Boot
- Spring Web
- Spring DevTools
- Lombok
- JSON Web Token (JWT)
- Tomcat
- JUnit for testing

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/quizapp.git
    cd quizapp
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Accessing the Application

Once the application is running, you can access it at `http://localhost:8080`.

### API Endpoints

#### User Endpoints

- `POST /api/register`: Register a new user
- `POST /api/login`: Login a user
- `GET /api/users/{id}`: Get user by ID
- `GET /api/users`: Get all users

#### Quiz Endpoints

- `GET /api/quiz`: Get all quizzes
- `GET /api/quiz/{id}`: Get quiz by ID
- `POST /api/quiz/createQuiz`: Create a new quiz
- `POST /api/quiz/submitQuiz`: Submit a quiz

### Example Usage

To register a user, send a POST request to `/api/register` with the following JSON body:
```json
{
    "username": "john_doe",
    "password": "password123"
}
```
To create a quiz, send a POST request to /api/quiz/createQuiz with the following JSON body:
```json
{
    "title": "Sample Quiz",
    "createdBy": "john_doe",
    "questions": [
        {
            "text": "What is the capital of France?",
            "answers": [
                {"text": "Paris", "isCorrect": true},
                {"text": "London", "isCorrect": false},
                {"text": "Berlin", "isCorrect": false},
                {"text": "Madrid", "isCorrect": false}
            ]
        }
    ]
}
```
