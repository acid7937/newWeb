// view.js
const API_BASE_URL = 'http://localhost:8080/api/boards';

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const boardId = urlParams.get('boardId');


async function fetchBoard() {
    const response = await fetch(`${API_BASE_URL}/${boardId}`);
    const data = await response.json();
    displayBoard(data);
}

function displayBoard(board) {
    const boardDiv = document.getElementById('board');
    boardDiv.innerHTML = `
        <h3>${board.title}</h3>
        <p>${board.content}</p>
    `;
}

document.getElementById('edit-btn').addEventListener('click', () => {
    window.location.href = `./edit.html?boardId=${boardId}`;
});

document.getElementById('back-btn').addEventListener('click', () => {
    window.location.href = './index.html';
});

fetchBoard();
