let questions = [];

function addQuestion() {
    const questionText = document.getElementById('questionText').value;
    const answerElements = document.querySelectorAll('#answers .form-check');
    const answers = [];
    let correctAnswerIndex = -1;

    answerElements.forEach((element, index) => {
        const answerText = element.querySelector('input[type="text"]').value;
        const isCorrect = element.querySelector('input[type="radio"]').checked;
        answers.push({ text: answerText, isCorrect: isCorrect });
        if (isCorrect) {
            correctAnswerIndex = index;
        }
    });

    if (questionText && correctAnswerIndex !== -1) {
        questions.push({ text: questionText, answers: answers });
        updateNewQuestionList();
        clearForm();
    } else {
        alert('Please enter a question and select the correct answer.');
    }
}

function updateNewQuestionList() {
    const newQuestionList = document.getElementById('newQuestionList');
    newQuestionList.innerHTML = '';
    questions.forEach((question, index) => {
        const listItem = document.createElement('li');
        listItem.className = 'list-group-item question-item';
        listItem.textContent = `${index + 1}. ${question.text}`;
        newQuestionList.appendChild(listItem);
    });
}

function clearForm() {
    document.getElementById('questionText').value = '';
    document.querySelectorAll('#answers .form-check input[type="text"]').forEach(input => input.value = '');
    document.querySelectorAll('#answers .form-check input[type="radio"]').forEach(radio => radio.checked = false);
}

function submitQuiz() {
    const quizId = document.getElementById('quizId').value;
    const payload = {
        quizId: quizId.toString(),
        questions: questions
    };

    fetch('/api/quiz/submitQuiz', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update quiz.');
            }
            return response.json();
        })
        .then(data => {
            alert('Quiz updated successfully!');
            questions = [];
            updateNewQuestionList();
            loadExistingQuestions(quizId);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to update quiz.');
        });
}

function loadExistingQuestions(quizId) {
    fetch(`/api/quiz/${quizId}`)
        .then(response => response.json())
        .then(data => {
            const existingQuestions = data.questions;
            const existingQuestionList = document.getElementById('existingQuestionList');
            existingQuestionList.innerHTML = '';
            existingQuestions.forEach((question, index) => {
                const listItem = document.createElement('li');
                listItem.className = 'list-group-item question-item';
                listItem.textContent = `${index + 1}. ${question.text}`;
                existingQuestionList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error:', error));
}

function generateAIQuestions() {
    const quizId = document.getElementById('quizId').value;

    fetch(`/api/chat/addQuestions/${quizId}`, {
        method: 'POST',
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            updateNewQuestionList();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to generate AI questions.');
        });
}

window.onload = function () {
    const urlParams = new URLSearchParams(window.location.search);
    const quizId = urlParams.get('quizId');
    document.getElementById('quizId').value = quizId;
    loadExistingQuestions(quizId);
};