document.getElementById('signup-form').addEventListener('submit', async (event) => {
    event.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const name = document.getElementById('nickname').value;

    try {
        const response = await fetch('/api/members/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password, nickname }),
        });

        if (response.ok) {
            alert('Account created successfully');
            window.location.href = '/login.html';
        } else {
            alert('Error: ' + response.statusText);
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
});
