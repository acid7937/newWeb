const API_BASE_URL = 'http://localhost:8080/api/boards';

async function fetchBoards(page = 1, size = 5) {
    page = parseInt(page); // 문자열을 정수로 변환
    const response = await fetch(`${API_BASE_URL}?page=${page}&size=${size}`);
    const data = await response.json();
    displayBoards(data.content);
    displayPagination(data.totalPages, page);
}

// main.js
function displayBoards(boards) {
    const boardList = document.getElementById('board-list');
    boardList.innerHTML = '';

    boards.forEach(board => {
        const boardDiv = document.createElement('div');
        const title = document.createElement('a');
        title.textContent = board.title;
        title.href = `./view.html?boardId=${board.boardId}`;
        boardDiv.appendChild(title);
        boardDiv.innerHTML += `
            <p>${board.content}</p>
            <hr>
        `;
        boardList.appendChild(boardDiv);
    });
}


function displayPagination(totalPages, currentPage) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';

    for (let i = 1; i <= totalPages; i++) {
        const pageButton = document.createElement('button');
        pageButton.textContent = i;
        pageButton.addEventListener('click', () => fetchBoards(i));
        if (i === currentPage) {
            pageButton.disabled = true;
        }
        pagination.appendChild(pageButton); // 이 부분을 수정했습니다.
    }
}

document.getElementById('write-btn').addEventListener('click', () => {
    window.location.href = './write.html';
});

fetchBoards();

