// 게시글 목록을 불러오는 API를 호출하는 함수를 작성하세요.
async function getPosts() {
    // 백엔드 API를 호출하여 게시글 목록을 가져옵니다.
    // 예: const response = await fetch('https://example.com/api/posts');
    // const posts = await response.json();

    // 게시글 목록을 화면에 표시합니다.
    displayPosts(posts);
}

// 게시글 목록을 화면에 표시하는 함수입니다.
function displayPosts(posts) {
    const postList = document.getElementById('post-list');
    posts.forEach(post => {
        const postElement = document.createElement('div');
        postElement.className = 'post';
                postElement.innerHTML = `
                    <h3>${post.title}</h3>
                    <p>${post.content}</p>
                `;
                postList.appendChild(postElement);
            });
        }

        // 페이지네이션 처리를 하는 함수를 작성하세요.
        async function handlePagination() {
            // 백엔드 API를 호출하여 페이지네이션 정보를 가져옵니다.
            // 예: const response = await fetch('https://example.com/api/posts?page=1');
            // const pageInfo = await response.json();

            // 페이지네이션 정보를 사용하여 페이지네이션 컨트롤을 생성합니다.
            createPaginationControls(pageInfo);
        }

        // 페이지네이션 컨트롤을 생성하는 함수입니다.
        function createPaginationControls(pageInfo) {
            const pagination = document.querySelector('.pagination');
            for (let i = 1; i <= pageInfo.totalPages; i++) {
                const pageButton = document.createElement('button');
                pageButton.innerText = i;
                pageButton.onclick = () => {
                    getPosts(i); // 페이지 번호를 인수로 전달합니다.
                };
                pagination.appendChild(pageButton);
            }
        }

        // 로그인 버튼과 회원가입 버튼의 이벤트 리스너를 설정합니다.
        document.getElementById('login-button').onclick = () => {
            window.location.href = '/login.html';
        };
        document.getElementById('signup-button').onclick = () => {
            window.location.href = '/signup.html';
        };

        // 페이지 로드 시 게시글 목록과 페이지네이션을 불러옵니다.
        getPosts();
        handlePagination();

