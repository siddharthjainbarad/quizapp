# QuizApp

QuizApp is a web application that allows users to create, take, and manage quizzes. It includes features such as user authentication, quiz creation, quiz attempts, and a leaderboard.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Frontend](#frontend)
- [Contributing](#contributing)
- [License](#license)

## Features

- User Authentication
- Create and manage quizzes
- Take quizzes
- View leaderboard
- Delete users and quizzes

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Thymeleaf
- Bootstrap
- JPA/Hibernate
- H2 Database (for development)
- RESTful APIs

## Setup and Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/quizapp.git
    cd quizapp
    ```

2. **Build the project:**

    ```bash
    ./mvnw clean install
    ```

3. **Run the application:**

    ```bash
    ./mvnw spring-boot:run
    ```

4. **Access the application:**

    Open your browser and navigate to `http://localhost:8080`.

## Usage

### User Authentication

- Users can log in using their credentials.
- Authentication is handled by Spring Security.

### Creating a Quiz

- Navigate to `/create-quiz`.
- Fill in the quiz details and submit.

### Taking a Quiz

- Navigate to `/take-quiz`.
- Select a quiz and answer the questions.

### Viewing the Leaderboard

- Navigate to `/api/userQuizAttempts/leaderboard` to view the leaderboard.

### Deleting a User

- Navigate to the user management section and delete a user by clicking the delete button.

### Deleting a Quiz

- Navigate to the quiz management section and delete a quiz by clicking the delete button.

## API Endpoints

### User Authentication

- **POST** `/api/login`: Authenticate a user.

### User Management

- **GET** `/api/users`: Get all users.
- **GET** `/api/users/{id}`: Get a user by ID.
- **DELETE** `/api/users/{id}`: Delete a user by ID.

### Quiz Management

- **POST** `/api/quiz/createQuiz`: Create a new quiz.
- **POST** `/api/quiz/submitQuiz`: Add questions to a quiz.
- **DELETE** `/api/quiz/{id}`: Delete a quiz by ID.

### Quiz Attempts

- **GET** `/api/userQuizAttempts/leaderboard`: Get the leaderboard.

## Frontend

### HTML Templates

- **Home Page**: `/private/home`
- **Create Quiz Page**: `/private/create-quiz`
- **Take Quiz Page**: `/private/take-quiz`

### JavaScript

- **list-user.js**: Handles fetching and deleting users.
- **create-quiz.js**: Handles creating quizzes.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.