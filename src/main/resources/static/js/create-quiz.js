$(document).ready(function () {
    $('#createQuizForm').on('submit', function (event) {
        event.preventDefault();
        const formData = {
            title: $('#title').val(),
            topic: $('#topic').val(),
            createdBy: $('#createdBy').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/quiz/createQuiz',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                $('#successMessage').text('Quiz created successfully!').show();
                $('#errorMessage').hide();
                loadQuizzes(); // Refresh the list of quizzes
            },
            error: function (xhr) {
                $('#errorMessage').text(xhr.responseText).show();
                $('#successMessage').hide();
            }
        });
    });

    function loadQuizzes() {
        fetch('/api/quiz')
            .then(response => response.json())
            .then(data => {
                const tbody = document.querySelector('tbody');
                tbody.innerHTML = ''; // Clear existing rows
                data.forEach(quiz => {
                    const row = document.createElement('tr');

                    const titleCell = document.createElement('td');
                    titleCell.textContent = quiz.title;
                    titleCell.onclick = () => openQuiz(quiz.id);
                    titleCell.style.cursor = 'pointer';

                    const topicCell = document.createElement('td');
                    topicCell.textContent = quiz.topic;

                    const createdByCell = document.createElement('td');
                    createdByCell.textContent = quiz.createdBy;

                    const createdAtCell = document.createElement('td');
                    createdAtCell.textContent = new Date(quiz.createdAt).toLocaleDateString();

                    row.appendChild(titleCell);
                    row.appendChild(topicCell);
                    row.appendChild(createdByCell);
                    row.appendChild(createdAtCell);
                    tbody.appendChild(row);
                });
            })
            .catch(error => {
                console.error('Error fetching quizzes:', error);
                alert('Failed to load quizzes.');
            });
    }

    function openQuiz(quizId) {
        window.location.href = `/update-quiz?quizId=${quizId}`;
    }

    loadQuizzes(); // Load quizzes on page load
});