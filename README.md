# QuizApp

QuizApp is a web application that allows users to take quizzes and interact with a chat service for quiz-related queries.

## Prerequisites

- Java 11 or higher
- Maven
- Spring Boot

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/quizapp.git
    ```
2. Navigate to the project directory:
    ```sh
    cd quizapp
    ```
3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Usage

1. Run the application:
    ```sh
    mvn spring-boot:run
    ```
2. Access the application at `http://localhost:8080`.

## API Endpoints

### ChatController

- **POST /api/chat/{quizId}**: Initiates a chat for the specified quiz.

### QuizController

- **GET /api/quiz**: Retrieves a list of all quizzes.
- **GET /api/quiz/{id}**: Retrieves a specific quiz by ID.
- **POST /api/quiz**: Creates a new quiz.
- **PUT /api/quiz/{id}**: Updates an existing quiz by ID.
- **DELETE /api/quiz/{id}**: Deletes a quiz by ID.

### UserController

- **GET /api/user**: Retrieves a list of all users.
- **GET /api/user/{id}**: Retrieves a specific user by ID.
- **POST /api/user**: Creates a new user.
- **PUT /api/user/{id}**: Updates an existing user by ID.
- **DELETE /api/user/{id}**: Deletes a user by ID.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.