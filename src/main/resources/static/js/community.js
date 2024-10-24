document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/community/sub-categories")
        .then(response => {
            if (!response.ok) {
                throw new Error("에러!!")
            }
            return response.json();
        })
        .then(data => {
            const categoryList = document.getElementById("community-category-list");
            console.log(data);
            data.forEach(data => {
                const li = document.createElement('li');
                li.innerHTML = `<a href="/api/community/sub-categories/${data.id}">${data.name}</a>`;
                categoryList.appendChild(li);
            });
        })
        .catch(error => console.error('에러가 있어요', error));
})

// 게시글 HTML을 생성하는 함수
function createPostHTML(post) {
    const tempDiv = document.createElement('div');
    tempDiv.innerHTML = post.content;
    const plainText = tempDiv.textContent || tempDiv.innerText || '';
    const previewText = plainText.length > 30 ? plainText.substring(0, 30) + "..." : plainText;
    return `
        <div class="community-post-body">
            <div class="community-post-content">
                <div class="community-post-category">${post.categoryName}</div>
                <div class="community-post-title">${post.title}</div>
                <div class="community-post-content">${previewText}</div>
            </div>
            <div class="community-post-thumbnail">
                ${post.imageUrl ? `<img src="${post.imageUrl}" alt="썸네일 이미지"/>` : ''}
            </div>
        </div>
        <div class="community-post-footer">
            <div class="community-post-info">
                ${post.region3DepthName} · ${post.createdAt}  <!-- 서버에서 시간 처리 예정 -->
            </div>
            <div class="community-post-stats">
                <div class="community-post-like">
                        <span>
                            <img src="/images/community/like.svg" alt="좋아요 아이콘">
                            <span class="community-post-like-count">10</span>
                        </span>
                </div>
                <div class="community-post-comment">
                    <span>
                        <img src="/images/community/comment.svg" alt="댓글 아이콘">
                        <span class="community-post-comment-count">${post.commentCount}</span>
                    </span>
                </div>
            </div>
        </div>
    `;
}

document.addEventListener("DOMContentLoaded", function () {
    const postListContainer = document.getElementById('community-post-list');

    // API로부터 게시글을 가져오는 함수
    function fetchPosts() {
        fetch('/api/community/posts')
            .then(response => response.json())
            .then(posts => {
                console.log(posts);

                postListContainer.innerHTML = ''; // 기존 내용을 비움

                posts.forEach(post => {
                    // 게시글 항목 생성
                    const postItem = document.createElement('a');
                    postItem.href = `/community/posts/${post.communityPostId}`;
                    postItem.classList.add('community-post-item');

                    // HTML 생성 함수 호출
                    postItem.innerHTML = createPostHTML(post);

                    // 생성된 게시글 항목을 목록에 추가
                    postListContainer.appendChild(postItem);
                });
            })
            .catch(error => {
                console.error('게시글을 불러오는 중 오류가 발생했습니다:', error);
            });
    }

    // 페이지 로드 시 게시글을 불러옴
    fetchPosts();
});