// 날짜 시간
const formatDate = (data) => {
  const date = new Date(data);
  // 2025/12/16 12:20
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};
// 전체 리뷰 가져오기
const url = `http://localhost:8080/reviews`;
const reviewArea = document.querySelector(".reviewList");
const reviewCnt = document.querySelector(".review-cnt");
const reviewForm = document.querySelector("#reviewForm");

const reviewList = () => {
  fetch(`${url}/${mno}/all`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      console.log(data);
      // 화면 작업
      let result = "";
      data.forEach((review) => {
        result += `<div class="d-flex justify-content-between py-2 border-bottom review-row" data-rno="${review.rno}">`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<div><span class="font-semibold">${review.text}</span></div>`;
        result += `<div class="small text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
        result += `평점 : <span class="grade">${review.grade}</span><div class="starrr"></div></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(review.createDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });
      reviewArea.innerHTML = result;
      // reviewCnt.innerHTML = data.length;
    })
    .catch((err) => console.log(err));
};
reviewList();
// 특정 리뷰 삭제
const reviewDelete = (rno) => {
  fetch(`${url}/${mno}/${rno}`, {
    method: "DELETE",
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.text();
    })
    .then((data) => {
      console.log(data);
      console.log("delete");
      // 화면 작업
      if (data) {
        Swal.fire({
          // 비동기 함수이므로 location이 먼저 실행되버린다
          title: "댓글 삭제 완료",
          icon: "success",
          draggable: true,
        }).then(() => {
          // then 을 추가해서 alert를 먼저 출력한다
          // 이동
          // location.href = "/board/list";

          // 댓글 다시 가져오기
          reviewList();
        });
      }
    })
    .catch((e) => console.error(e));
};

// 특정 리뷰 가져오기
const reviewGet = (rno) => {
  fetch(`${url}/${mno}/${rno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      console.log(data);
      console.log("get");
      // 화면 작업
      reviewForm.nickname.value = data.nickname;
      reviewForm.text.value = data.text;
      reviewForm.rno.value = data.rno;
      reviewForm.mid.value = data.mid;
      reviewForm.mno.value = data.mno;
      reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
    })
    .catch((e) => console.error(e));
};
// 수정 / 삭제 클릭 시
reviewArea.addEventListener("click", (e) => {
  console.log(e.target); // => 어느 버튼의 이벤트인가? 수정 / 삭제
  const btn = e.target;
  // 부모쪽으로만 검색
  // data- 에 접근하려면 dataset 을 쓴다
  const rno = btn.closest(".review-row").dataset.rno;
  console.log("rno", rno);
  if (btn.classList.contains("btn-outline-danger")) {
    // 삭제
    reviewDelete(rno);
  } else {
    // 수정
    reviewGet(rno);
  }
});

// 리뷰 수정 + PUT
// 리뷰 폼 등록 클릭 시 새로운 리뷰 등록 or 기존 리뷰 수정
const reviewPut = (form, rno) => {
  const review = {
    rno: rno,
    grade: grade,
    text: form.text.value,
  };
  fetch(`${url}/${mno}/${rno}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(review),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.text();
    })
    .then((data) => {
      console.log(data);
      console.log("modify");
      // 화면 작업
      form.nickname.value = "";
      form.text.value = "";
      form.rno.value = "";
      form.mid.value = "";
      form.querySelector(".starrr a:nth-child(" + grade + ")").click();

      reviewList();
    })
    .catch((e) => console.error(e));
};
const reviewPost = (form) => {
  const review = {
    mid: 1,
    mno: mno,
    grade: grade,
    text: form.text.value,
  };
  fetch(`${url}/${mno}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(review),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.text();
    })
    .then((data) => {
      console.log(data);
      console.log("new");
      // 화면 작업
      form.text.value = "";
      form.rno.value = "";
      form.querySelector(".starrr a:nth-child(" + grade + ")").click();

      reviewList();
    })
    .catch((e) => console.error(e));
};

reviewForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const rno = form.rno.value;
  // 리뷰 등록과 리뷰 수정을 같은 버튼으로 수행한
  // rno 값 여부로 리뷰 등록인지 수정인지 구분한다
  if (rno) {
    // 수정
    reviewPut(form, rno);
  } else {
    // 신규 등록
    reviewPost(form);
  }
});
// 큰 이미지 보기
const imgModal = document.getElementById("imgModal");

if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (event) => {
    // modal을 뜨게 하는 li 요소 찾기
    const posterLi = event.relatedTarget;
    // li의 data-* 요소 값 가져오기
    const filePath = posterLi.getAttribute("data-file");
    // If necessary, you could initiate an Ajax request here
    // and then do the updating in a callback.

    // Update the modal's content.
    const modalTitle = imgModal.querySelector(".modal-title");
    const modalBody = imgModal.querySelector(".modal-body");

    modalTitle.textContent = `${title}`;
    modalBody.innerHTML = `<img src="/upload/display?fileName=${filePath}" style="width=100%">`;
  });
}
$("#imgModal").on("hide.bs.modal", function (e) {
  if (this.contains(document.activeElement)) {
    document.body.focus();
  }
});
