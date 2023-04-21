const boardPostForm = document.getElementById('boardPostForm');
const boardList = document.getElementById('boardList');

boardPostForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    const response = await fetch('/api/boards/post', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, content })
    });

    if (response.status === 201) {
        const boardId = await response.json();
        console.log(`Board created with id: ${boardId}`);
    } else {
        console.error('Failed to create board.');
    }

    getBoards();
});

async function getBoards() {
    const response = await fetch('/api/boards');
    const boards = await response.json();
    boardList.innerHTML = '';

    for (const board of boards.content) {
        const boardElement = document.createElement('div');
        boardElement.innerHTML = `
            <h2>${board.title}</h2>
            <p>${board.content}</p>
        `;
        boardList.appendChild(boardElement);
    }
}

getBoards();
