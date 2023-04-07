const API_BASE_URL = 'http://localhost:8080/api/boards';

document.getElementById('post-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, content })
    });

    if (response.status === 201) {
        alert('글이 작성되었습니다.');
        window.location.href = './index.html';
    } else {
        alert('글 작성에 실패했습니다.');
    }
});
