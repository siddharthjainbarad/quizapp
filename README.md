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

| Method | Endpoint         | Description                |
|--------|------------------|----------------------------|
| POST   | /api/login       | Authenticate a user        |

### User Management

| Method | Endpoint                | Description                                      |
|--------|-------------------------|--------------------------------------------------|
| POST   | /api/register           | Register a new user                              |
| GET    | /api/users              | Get all users                                    |
| GET    | /api/users/{id}         | Get user details by ID                           |
| DELETE | /api/users/{id}         | Delete a user by ID                              |
| GET    | /api/current-user       | Get the currently logged-in user                 |
| POST   | /api/update-profile     | Update the profile of the currently logged-in user|

### Quiz Management

| Method | Endpoint                | Description                |
|--------|-------------------------|----------------------------|
| GET    | /api/quiz               | Get all quizzes            |
| GET    | /api/quiz/{id}          | Get quiz details by ID     |
| POST   | /api/quiz/createQuiz    | Create a new quiz          |
| POST   | /api/quiz/submitQuiz    | Add questions to a quiz    |
| DELETE | /api/quiz/{id}          | Delete a quiz by ID        |

### Question Management

| Method | Endpoint                | Description                |
|--------|-------------------------|----------------------------|
| DELETE | /api/question/{questionId} | Delete a question by ID |

### Quiz Attempts

| Method | Endpoint                        | Description                                |
|--------|---------------------------------|--------------------------------------------|
| GET    | /api/userQuizAttempts           | Get all user quiz attempts                 |
| GET    | /api/userQuizAttempts/{quizId}  | Get top 3 user quiz attempts by quiz ID    |
| POST   | /api/userQuizAttempts           | Create a new user quiz attempt             |
| PUT    | /api/userQuizAttempts/{id}      | Update an existing user quiz attempt       |
| DELETE | /api/userQuizAttempts/{id}      | Delete a user quiz attempt by ID           |
| GET    | /api/userQuizAttempts/leaderboard | Get the leaderboard                      |

### Chat Management

| Method | Endpoint                        | Description                                |
|--------|---------------------------------|--------------------------------------------|
| POST   | /api/chat/addQuestions/{quizId} | Add questions to a quiz via chat prompt    |

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