$(document).ready(function () {
    $('#loginForm').on('submit', function (event) {
        event.preventDefault();
        const formData = {
            username: $('#username').val(),
            password: $('#password').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/login',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                localStorage.setItem('authToken', response);
                window.location.href = '/home'; // Redirect to home page on successful login
            },
            error: function (xhr) {
                $('#errorMessage').text(xhr.responseText).show();
            }
        });
    });
});