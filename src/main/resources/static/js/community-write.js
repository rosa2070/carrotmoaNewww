const communityPostId = window.location.pathname.split('/').pop(); // 게시글 ID 가져오기
console.log("게시글 아이디: ", communityPostId);

function isValidPostId(id) {
    return !isNaN(id) && id.trim() !== ""; // id가 숫자이고 빈 문자열이 아닐 경우
}


document.addEventListener("DOMContentLoaded", function () {
    if (isValidPostId(communityPostId)) {
        // 게시글 수정 모드
        document.getElementById("page-type").textContent = "동네생활 글 수정하기";
        document.getElementById("submit-btn").textContent = "수정완료";
        fetch(`/api/community/posts/${communityPostId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("해당 게시글을 불러올 수 없습니다.");
                } else {
                    return response.json();
                }
            })
            .then(data => {
                console.log(data);
                document.getElementById("community-post-title").value = data.title;
                editor.setData(data.content);
                document.getElementById("community-post-category").value = data.communityCategoryId;
            })
            .catch(error => console.error("에러 발생: ", error));
        document.getElementById("submit-btn").addEventListener('click', function (e) {
            e.preventDefault();

        });

    } else {
        // 신규 게시글 작성 모드
        document.getElementById("page-type").textContent = "동네생활 글쓰기";
        document.getElementById("submit-btn").textContent = "작성완료";
        document.getElementById("community-post-title").value = ""; // 제목 비우기
        editor.setData(""); // CKEditor 비우기

        document.getElementById("submit-btn").addEventListener('click', function (e) {
            e.preventDefault();
            const editorContent = editor.getData();
            const form = document.getElementById('community-post-form');

            // form 데이터를 JSON으로 변환
            const formData = {
                userId: form.userId.value,
                communityCategoryId: form.communityCategoryId.value,
                title: form.title.value,
                content: editorContent // CKEditor에서 가져온 내용
            };
            fetch('/api/community/posts', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json', // JSON 형식으로 보냄
                },
                body: JSON.stringify(formData) // JSON 문자열로 변환
            })
                .then(response => response.json())
                .then(postId => {
                    window.location.href = `/community/posts/${postId}`;

                })
                .catch(error => console.error('Error 발생: ', error));
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/community/categories")
        .then(response => response.json())
        .then(data => {
            const categorySelect = document.getElementById("community-post-category");

            // 상위 카테고리 찾기 (parentId가 없는 카테고리)
            const parentCategories = data.filter(category => !category.parentId);

            // 각 상위 카테고리마다 optgroup을 만들고, 해당하는 하위 카테고리를 넣기
            parentCategories.forEach(parentCategory => {
                // 상위 카테고리를 optgroup으로 만들기
                const optgroup = document.createElement("optgroup");
                optgroup.label = parentCategory.name;

                // 해당 상위 카테고리(parentId)와 연결된 하위 카테고리 추가
                const childCategories = data.filter(category => category.parentId === parentCategory.id);
                childCategories.forEach(childCategory => {
                    const option = document.createElement("option");
                    option.value = childCategory.id;
                    option.textContent = childCategory.name;
                    optgroup.appendChild(option);
                });

                // 상위 카테고리 (optgroup)를 select 요소에 추가
                categorySelect.appendChild(optgroup);
            });
        })
        .catch(error => console.error("카테고리 데이터를 불러오지 못했습니다 : ", error));
})
