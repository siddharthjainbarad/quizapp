$(document).ready(function() {
    $('#signupForm').on('submit', function(event) {
        event.preventDefault();
        const formData = {
            email: $('#email').val(),
            username: $('#username').val(),
            password: $('#password').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/register',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                $('#successMessage').text(response).show();
                $('#errorMessage').hide();
            },
            error: function(xhr) {
                $('#errorMessage').text(xhr.responseText).show();
                $('#successMessage').hide();
            }
        });
    });
});