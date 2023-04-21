document.getElementById('login-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
const response = await fetch("/members/login", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({ email: email, password }),
});

if (response.ok) {
    const jwtToken = response.headers.get('Authorization').split(' ')[1]; // 토큰을 헤더에서 가져옵니다.
    alert('Logged in successfully');
    localStorage.setItem('jwt', jwtToken); // 헤더에서 가져온 토큰을 저장합니다.
    window.location.href = '/boards.html';
} else {
    alert('Error: ' + response.statusText);
}

    } catch (error) {
        alert('Error: ' + error.message);
    }
});
