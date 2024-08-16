document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/users')
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector('tbody');
            data.forEach(user => {
                const row = document.createElement('tr');
                const usernameCell = document.createElement('td');
                const emailCell = document.createElement('td');

                usernameCell.textContent = user.username;
                emailCell.textContent = user.email;

                row.appendChild(usernameCell);
                row.appendChild(emailCell);
                tbody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching users:', error));
});