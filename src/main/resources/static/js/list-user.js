document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/users')
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector('tbody');
            data.forEach(user => {
                const row = document.createElement('tr');
                const usernameCell = document.createElement('td');
                const emailCell = document.createElement('td');
                const actionsCell = document.createElement('td');
                const deleteButton = document.createElement('button');

                usernameCell.textContent = user.username;
                emailCell.textContent = user.email;

                deleteButton.className = 'btn btn-danger btn-sm';
                deleteButton.innerHTML = '<i class="fas fa-trash-alt"></i>';
                deleteButton.onclick = () => deleteUser(user.id);
                actionsCell.appendChild(deleteButton);

                row.appendChild(usernameCell);
                row.appendChild(emailCell);
                row.appendChild(actionsCell);
                tbody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching users:', error));
});

function deleteUser(userId) {
    if (confirm('Are you sure you want to delete this user?')) {
        fetch(`/api/users/${userId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete user.');
                }
                return response.text();
            })
            .then(data => {
                alert('User deleted successfully!');
                location.reload(); // Reload the page to refresh the list of users
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to delete user.');
            });
    }
}