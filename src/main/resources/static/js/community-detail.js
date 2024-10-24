const communityPostId = window.location.pathname.split('/').pop();

const currentUserId = (user && user.userProfile) ? user.userProfile.userId : null;
let postUserId;

document.addEventListener("DOMContentLoaded", function () {
  const commentInput = document.getElementById("commentInput");
  if(user) {
    commentInput.placeholder = "댓글을 남겨보세요.";
  } else {
    commentInput.placeholder = "로그인 후 이용 가능합니다.";
  }


  const nickname = document.getElementById("nickname");
  const userProfileImage = document.getElementById("userProfileImage")
  const region2DepthName = document.getElementById("region2DepthName");
  const region3DepthName = document.getElementById("region3DepthName");
  const title = document.getElementById("title");
  const communityCategory = document.getElementById("communityCategory");
  const content = document.getElementById("content");
  const createdAt = document.getElementById("createdAt");
  fetch(`/api/community/posts/${communityPostId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error("해당 게시글을 불러올 수 없습니다.")
        } else {
          return response.json();
        }
      })
      .then(data => {
        console.log(data);
        postUserId = data.userId;
        nickname.innerText = data.nickname;
        userProfileImage.src = data.picUrl;
        region2DepthName.innerText = data.region2DepthName;
        region3DepthName.innerText = data.region3DepthName;
        title.innerHTML = data.title;
        content.innerHTML = data.content;
        createdAt.innerHTML = data.createdAt;
        communityCategory.innerHTML = data.communityCategoryName;
        communityCategory.href = `/community/subCategories/${data.communityCategoryId}`;

      })
      .catch(error => {
        console.log("에러 발생: ", error);
      })
})

function createCommentHtml(data) {
  return `
        <div class="detail-comment-wrap">
            <div class="detail-comment-header">
                <div class="detail-comment-profile">
                    <img src="/images/community/cat.jpg" alt="유저 프로필 사진" id="commentUserProfile">
                </div>
                <div class="comment-user-info">
                    <div class="detail-comment-nickname">
                        <a href="/" id="commentUserNickname">구염둥이</a>
                    </div>
                    <div class="detail-comment-region-name" id="commentUserRegion">노원구 상계1동</div>
                </div>
                <div class="detail-comment-time-wrap">
                    <time class="detail-comment-time" id="commentCreatedAt"> · ${data.createdAt}</time>
                </div>
                <button class="moreOptionsButton">
                    <img src="/images/community/more-options.svg" alt="더보기 옵션">
                </button>
                <div class="overlay comment-overlay"></div>
                <div id="dropdownMenuComment" class="dropdown-content">
                    <button id="reportComment">댓글 신고</button>
                    <button id="editComment">댓글 수정</button>
                    <button id="deleteComment">댓글 삭제</button>
                </div>
            </div>

            <div class="detail-comment-content">
                <p id="commentContent">${data.content}</p>
            </div>

            <div>
                <button class="comment-like-button" id="commentLikeButton">
                    <img src="/images/community/unlike.svg" alt="댓글 좋아요" id="commentLikeIcon"/>
                    <span id="commentLikeCount">좋아요</span>
                </button>
                <button class="reply-button" id="replyButton">
                    <img src="/images/community/detail-comment.svg" alt="답글" id="replyIcon"/>
                    <span>답글쓰기</span>
                </button>
            </div>
        </div>
    `;
}

function updateCommentCount(count) {
  document.getElementById("commentCount").innerText = `댓글 ${count}`;
}


// community-detail.js

document.addEventListener("DOMContentLoaded", function () {
  // 드롭다운 메뉴와 오버레이를 관리
  document.addEventListener("click", function (event) {
    const target = event.target;

    // 게시글 더보기 버튼 클릭 시 드롭다운 메뉴 토글
    if (target.id === "moreOptionsButton" || target.closest("#moreOptionsButton")) {
      const dropdownMenu = document.getElementById("dropdownMenu");
      const overlay = document.getElementById("postOverlay");
      console.log("현재 접속한 아이디: ",currentUserId);
      console.log("게시글 작성한 사람의 아아디: ", postUserId);
      if (dropdownMenu.style.display === "block") {
        dropdownMenu.style.display = "none";
        overlay.style.display = "none"; // 오버레이 숨기기
      } else {
        dropdownMenu.style.display = "block";
        overlay.style.display = "block"; // 오버레이 보이기
      }
      const reportButton = document.getElementById("reportPost");
      const editButton = document.getElementById("editPost");
      const deleteButton = document.getElementById("deletePost");

      if (currentUserId === postUserId) {
        // 아이디가 같으면 수정, 삭제 버튼만 보이게
        editButton.style.display = "block";
        deleteButton.style.display = "block";
        reportButton.style.display = "none"; // 신고 버튼 숨기기
      } else {
        // 아이디가 다르면 신고 버튼만 보이게
        editButton.style.display = "none";
        deleteButton.style.display = "none";
        reportButton.style.display = "block";
      }



    }
    // 댓글 더보기 버튼 클릭 시 해당 댓글의 드롭다운 메뉴 토글
    else if (target.classList.contains("moreOptionsButton") || target.closest(".moreOptionsButton")) {
      const dropdownMenuComment = target.closest(".detail-comment-header").querySelector(".dropdown-content");
      const overlayComment = target.closest(".detail-comment-header").querySelector(".comment-overlay");

      if (dropdownMenuComment.style.display === "block") {
        dropdownMenuComment.style.display = "none";
        overlayComment.style.display = "none"; // 오버레이 숨기기
      } else {
        dropdownMenuComment.style.display = "block";
        overlayComment.style.display = "block"; // 오버레이 보이기
      }
    }
    // 메뉴 외부 클릭 시 모든 드롭다운 메뉴 닫기
    else {
      const allDropdowns = document.querySelectorAll(".dropdown-content");
      const allOverlays = document.querySelectorAll(".overlay");

      allDropdowns.forEach(menu => menu.style.display = "none");
      allOverlays.forEach(overlay => overlay.style.display = "none");
    }
  });
});

document.getElementById("submitCommentBtn").addEventListener("click", function (e) {
  e.preventDefault();
  const content = document.getElementById("commentInput").value;
  const data = {
    content: content,
    userId: currentUserId
  };
  fetch(`/api/community/posts/${communityPostId}/comments`, {
    method: "post",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  })
      .then(response => {
        if (!response.ok) {
          throw new Error("댓글 등록 실패");
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
        const newCommentHtml = createCommentHtml(data);
        // 댓글 목록에 새 댓글 추가
        document.getElementById("commentsList").insertAdjacentHTML('beforeend', newCommentHtml);
        // 댓글 입력창 초기화
        document.getElementById("commentInput").value = "";
        //     ToDO : 댓글 개수 실시간 반영하는 로직 추가해야함.
        updateCommentCount(data.commentCount);
      })
      .catch(error => {
        console.error("에러 발생:", error);
      });
})

document.addEventListener("DOMContentLoaded", function () {
  fetch(`/api/community/posts/${communityPostId}/comments`, {
    method: "get",
  })
      .then(response => {
        if (!response.ok) {
          throw new Error("댓글 리스트를 불러올 수 없습니다.")
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
        const commentCount = data.commentCount;
        const comments = data.commentList;
        comments.forEach(comment => {
          const commentHtml = createCommentHtml(comment);
          document.getElementById("commentsList").insertAdjacentHTML('beforeend', commentHtml);
        });
        updateCommentCount(commentCount);
      })
      .catch(error => {
        console.log("에러 발생: ", error);
      })
})

// 삭제 버튼을 클릭하면 모달이 보이도록 설정
document.getElementById("deletePost").addEventListener("click", function () {
  document.getElementById("deleteModalOverlay").style.display = "flex";
});

// 모달의 취소 버튼을 클릭하면 모달이 닫히도록 설정
document.getElementById("cancelDeleteButton").addEventListener("click", function () {
  document.getElementById("deleteModalOverlay").style.display = "none";
});

// 모달 오버레이를 클릭하면 모달이 닫히도록 설정
document.getElementById("deleteModalOverlay").addEventListener("click", function (event) {
  // 이벤트가 모달 내부에서 발생하지 않은 경우에만 모달을 닫음
  if (event.target === this) {
    document.getElementById("deleteModalOverlay").style.display = "none";
  }
});


document.getElementById("confirmDeleteButton").addEventListener("click", function () {
  fetch(`/api/community/posts/${communityPostId}`, {
    method: "delete"
  })
      .then(response => {
        if (!response.ok) {
          throw new Error("유효하지 않은 게시글번호 입니다.")
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
        window.location.href = "/community";
      })
})

document.getElementById("editPost").addEventListener("click", function () {
  window.location.href = `/community/write/${communityPostId}`;

})
