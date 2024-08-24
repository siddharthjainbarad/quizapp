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

                    const actionsCell = document.createElement('td');
                    const deleteButton = document.createElement('button');
                    deleteButton.className = 'btn btn-danger btn-sm';
                    deleteButton.innerHTML = '<i class="fas fa-trash-alt"></i>';
                    deleteButton.onclick = () => deleteQuiz(quiz.id);
                    actionsCell.appendChild(deleteButton);

                    row.appendChild(titleCell);
                    row.appendChild(topicCell);
                    row.appendChild(createdByCell);
                    row.appendChild(createdAtCell);
                    row.appendChild(actionsCell);
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

    function deleteQuiz(quizId) {
        if (confirm('Are you sure you want to delete this quiz?')) {
            fetch(`/api/quiz/${quizId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to delete quiz.');
                    }
                    return response.text();
                })
                .then(data => {
                    alert('Quiz deleted successfully!');
                    loadQuizzes(); // Reload quizzes after deletion
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Failed to delete quiz.');
                });
        }
    }

    loadQuizzes(); // Load quizzes on page load
});