let searchCurrentPage = 0; // 현재 페이지 번호
const searchPageSize = 6; // 페이지당 항목 수

document.addEventListener("DOMContentLoaded", function () {
    const integratedSearchInput = document.getElementById("integrated-search-input");

    // 엔터 키 감지 이벤트 추가
    integratedSearchInput.addEventListener("keypress", function (event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // 기본 폼 제출 방지
            const keyword = event.target.value.trim(); // 검색어 가져오기
            if (validateSearchInput(keyword)) {
                searchCurrentPage = 0; // 페이지를 초기화
                searchIntegratedResults(keyword, searchCurrentPage, searchPageSize);
            }
        }
    });
});

function showToast(message) {
    const toast = document.getElementById("integrated-search-toast");
    toast.textContent = message;
    toast.style.display = "block";

    // 3초 후에 자동으로 숨김
    setTimeout(() => {
        toast.style.display = "none";
    }, 3000);
}

// 예시: 검색어가 두 글자 미만일 때 호출
function validateSearchInput(keyword) {
    if (keyword.length < 2) {
        showToast("두 글자 이상 입력해주세요.");
        return false;
    }
    return true;
}


// 검색 요청을 처리하는 함수 추가
function searchIntegratedResults(keyword, page, size) {
    fetch(`/api/integrated-search/community?keyword=${keyword}&page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                if (page === 0) {
                    clearPreviousResults(); // 첫 페이지일 경우 기존 결과를 지움
                }
                renderSearchResults(data);
            }
        })
        .catch(error => console.error('Error fetching search results:', error));
}

// 검색 결과를 렌더링하는 함수
function renderSearchResults(data) {
    const mainContainer = document.querySelector('main'); // 첫 번째 main 태그 선택

    // 섹션 태그가 없으면 처음 호출된 상황이므로 생성
    let section = mainContainer.querySelector('#integrated-search-result');
    if (!section) {
        section = document.createElement('section');
        section.id = 'integrated-search-result';

        // resultContainer와 community-wrap, 더보기 버튼도 처음 섹션 생성 시 함께 생성
        const resultContainer = document.createElement('div');
        resultContainer.className = 'result-container';

        const articlesWrap = document.createElement('div');
        articlesWrap.id = 'community-wrap';
        articlesWrap.className = 'community-articles-wrap';

        const articleKind = document.createElement('p');
        articleKind.className = 'article-kind';
        articleKind.textContent = '동네생활';
        articlesWrap.appendChild(articleKind);

        // resultContainer에 community-wrap 추가
        resultContainer.appendChild(articlesWrap);
        section.appendChild(resultContainer);

        // 섹션을 메인 컨테이너에 추가
        mainContainer.appendChild(section);
    }

    // community-wrap 요소 선택
    const articlesWrap = section.querySelector('#community-wrap');
    articlesWrap.appendChild(createCommunityArticles(data.content,  document.getElementById("integrated-search-input").value.trim()));

    // 더보기 버튼 관리
    let moreButton = section.querySelector('.more-btn');
    if (!data.last) { // 다음 페이지가 있는 경우
        if (!moreButton) {
            moreButton = document.createElement('div');
            moreButton.className = 'more-btn';
            moreButton.innerHTML = `
                <span class="more-text">더보기</span>
                <div class="more-loading">
                    <div class="loader"></div>
                </div>`;

            // 클릭 이벤트 추가
            moreButton.addEventListener('click', () => loadMoreResults());
            section.querySelector('.result-container').appendChild(moreButton);
        }
    } else if (moreButton) { // 마지막 페이지면 더보기 버튼 제거
        moreButton.remove();
    }
}

// 더보기 버튼 클릭 시 호출되는 함수
function loadMoreResults() {
    searchCurrentPage++; // 페이지 번호 증가
    const keyword = document.getElementById("integrated-search-input").value.trim(); // 현재 검색어 가져오기
    searchIntegratedResults(keyword, searchCurrentPage, searchPageSize); // 다음 페이지 데이터 요청
}

// 기존 검색 결과를 지우는 함수
function clearPreviousResults() {
    const mainContainer = document.querySelector('main');
    mainContainer.innerHTML = ''; // 모든 내용을 지우기
}

// 커뮤니티 게시글 생성 함수
function createCommunityArticles(communityResults, keyword) {
    const fragment = document.createDocumentFragment();
    const regex = new RegExp(`(${keyword})`, 'gi');

    communityResults.forEach(result => {
        const article = document.createElement('div');
        article.className = 'community-article';

        // 이미지가 있는 경우에만 이미지 태그 추가
        let imageHtml = '';
        if (result.postImageUrl) {
            imageHtml = `
                <div class="community-article-image">
                    <img alt="더미데이터" src="${result.postImageUrl}">
                </div>`;
        }

        // HTML에서 순수 텍스트만 추출하는 함수
        const extractPlainText = (html) => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            return doc.body.textContent || "";
        };

        // 순수 텍스트 추출
        const plainTextContent = extractPlainText(result.content);

        // 내용 길이 제한 (예: 100자)
        const MAX_LENGTH = 100;
        const truncatedContent = plainTextContent.length > MAX_LENGTH ?
            plainTextContent.substring(0, MAX_LENGTH) + '...' :
            plainTextContent;

        // 검색어 강조 처리
        const highlightedContent = truncatedContent.replace(regex, '<mark>$1</mark>');

        article.innerHTML = `
            <a href="${result.postUrl}" class="article-link">
                ${imageHtml} <!-- 이미지 HTML 추가 -->
                <div class="community-article-text">
                    <p class="community-article-content">${highlightedContent}</p>
                    <p class="community-region-name">${result.addressName}</p>
                </div>
            </a>`;
        fragment.appendChild(article);
    });

    return fragment;
}




