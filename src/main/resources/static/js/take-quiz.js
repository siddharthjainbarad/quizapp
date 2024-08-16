$(document).ready(function () {
    fetchQuizzes();

    function fetchQuizzes() {
        $.ajax({
            url: '/api/quiz',
            method: 'GET',
            success: function (data) {
                const quizzesTableBody = $('#quizzesTable tbody');
                quizzesTableBody.empty();
                data.forEach(quiz => {
                    const row = `
                        <tr>
                            <td>${quiz.id}</td>
                            <td>${quiz.title}</td>
                            <td><button class="btn btn-primary" onclick="loadQuiz(${quiz.id})">Take Quiz</button></td>
                        </tr>
                    `;
                    quizzesTableBody.append(row);
                });
            },
            error: function (xhr) {
                console.error('Error fetching quizzes:', xhr);
            }
        });
    }
});

function loadQuiz(quizId) {
    $.ajax({
        url: `/api/quiz/${quizId}`,
        method: 'GET',
        success: function (data) {
            const quizDetails = $('#quizDetails');
            const questionsContainer = $('#questionsContainer');
            questionsContainer.empty();

            data.questions.forEach((question, index) => {
                const questionDiv = `
                    <div class="question">
                        <h3>Question ${index + 1}: ${question.text}</h3>
                        <div class="options">
                            ${question.answers.map((answer, answerIndex) => `
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="question${index}" id="question${index}answer${answerIndex}" value="${answerIndex}" data-correct="${answer.correct}">
                                    <label class="form-check-label" for="question${index}answer${answerIndex}">${answer.text}</label>
                                </div>
                            `).join('')}
                        </div>
                    </div>
                `;
                questionsContainer.append(questionDiv);
            });

            // Add an event listener to update selection immediately
            $('input[type="radio"]').change(function () {
                const $parentDiv = $(this).closest('.question');
                // Remove the selected style from other labels
                $parentDiv.find('label').removeClass('selected');
                // Add selected class to the clicked label
                $(this).next('label').addClass('selected');
            });

            $('#quizzesTable').hide();
            quizDetails.show();
        },
        error: function (xhr) {
            console.error('Error loading quiz:', xhr);
        }
    });
}

function submitQuiz() {
    let score = 0;

    // Iterate over each question
    $('.question').each(function () {
        const selectedOption = $(this).find('input[type="radio"]:checked');
        const correctOption = $(this).find('input[data-correct="true"]');

        // Highlight the correct answer
        correctOption.next('label').addClass('correct');

        // If the selected option is correct, increment the score
        if (selectedOption.length && selectedOption.data('correct')) {
            score++;
        } else if (selectedOption.length) {
            // If the selected option is wrong, highlight it
            selectedOption.next('label').addClass('wrong');
        }
    });

    // Assuming you have userId and quizId available
    const userId = 17;  // Example userId, replace with actual value
    const quizId = 5;   // Example quizId, replace with actual value

    // Construct the payload
    const payload = {
        userId: userId,
        quizId: quizId,
        score: score,
        attemptDate: new Date().toISOString()
    };

    // Send the score to the API
    $.ajax({
        url: '/api/userQuizAttempts',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function () {
            alert(`Your score is: ${score}`);
            // You can also display this score on the page instead of alerting it
        },
        error: function (xhr) {
            console.error('Error submitting quiz score:', xhr);
            alert('Failed to submit the quiz score. Please try again.');
        }
    });
}

$('.btn-submit').click(submitQuiz);