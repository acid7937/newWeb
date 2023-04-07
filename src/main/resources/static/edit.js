const API_BASE_URL = 'http://localhost:8080/api/boards';
const boardId = new URLSearchParams(window.location.search).get('boardId');

document.getElementById('cancel-btn').addEventListener('click', () => {
    window.location.href = `./view.html?boardId=${boardId}`;
});

async function fetchBoard() {
    const response = await fetch(`${API_BASE_URL}/${boardId}`);
    const data = await response.json();
    fillForm(data);
}

function fillForm(board) {
    document.getElementById('title').value = board.title;
    document.getElementById('content').value = board.content;
}

document.getElementById('edit-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    const response = await fetch(`${API_BASE_URL}/${boardId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ title, content }),
    });

    if (response.ok) {
        alert('게시글이 수정되었습니다.');
        window.location.href = `./view.html?boardId=${boardId}`;
    } else {
        alert('게시글 수정에 실패했습니다. 다시 시도해주세요.');
    }
});

fetchBoard();
