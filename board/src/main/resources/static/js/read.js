const replyList = document.querySelector(".replyList");
// 댓글 목록 가져오기
const url = `http://localhost:8080/replies`;
// 댓글 목록을 다시 호출하기 위해 함수 형태로 만들었다
const loadReply = () => {
  fetch(`${url}/board/${bno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      console.log(data);
      // 댓글 개수 보여주기
      // document.querySelector(".row .card:nth-child(2) .card-title span").innerHTML = data.length; // 두개가 같다
      replyList.previousElementSibling.firstElementChild.innerHTML = data.length;
      let result = "";
      // data 댓글 목록으로 보여주기
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${
          reply.rno
        }" data-email="${reply.replyerEmail}">
              <div class="p-3">
                <img
                  src="/img/user.png"
                  alt=""
                  class="rounded-cricle mx-auto d-block"
                  style="width: 60px; height: 60px"
                />
              </div>
              <div class="flex-grow-1 align-self-center">
                 <div>${reply.replyerName}</div>
                <div>
                  <sapn class="fs-5">${reply.text}</sapn>
                </div>
                <div class="text-muted">
                  <span class="samll">${formatDate(reply.createDateTime)}</span>
                </div>
              </div>
              <div class="d-flex flex-column align-self-center">
                <div class="mb-2">
                  <button class="btn btn-outline-danger btn-sm">삭제</button>
                </div>
                <div class="mb-2">
                  <button class="btn btn-outline-success btn-sm">수정</button>
                </div>
                </div>
                </div>`;
      });
      replyList.innerHTML = result;
    })
    .catch((err) => console.log(err));
};

loadReply();
// 댓글 추가
// 댓글 작성 클릭 시 == replyForm submit 이 발생 시
// submit 기능 중지
// post 요청

document.getElementById("replyForm").addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const rno = form.rno.value;
  const reply = {
    rno: form.rno.value,
    text: form.text.value,
    replyerEmail: form.replyerEmail.value,
    bno: bno,
  };
  // new or modify => rno value 존재 여부
  if (!rno) {
    // new
    fetch(`${url}/new`, {
      method: "POST",
      headers: {
        "X-CSRF-TOKEN": csrfVal,
        "Content-Type": "application/json", //text/html;charset=UTF-8
      },
      body: JSON.stringify(reply),
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        // json body 추출
        return res.json();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "댓글 작성 완료",
            icon: "success",
            draggable: true,
          });
        }
        form.replyer.value = "";
        form.text.value = "";
        loadReply();
      })
      .catch((err) => console.log(err));
  } else {
    // modify
    // 밑에서 준비한 댓글 수정 form을 불러와서 작성한 뒤 수정 확정
    fetch(`${url}/${rno}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json", //text/html;charset=UTF-8
      },
      body: JSON.stringify(reply),
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        // json body 추출
        return res.json();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "댓글 수정 완료",
            icon: "success",
            draggable: true,
          });
        }
        form.replyer.value = "";
        form.text.value = "";
        form.rno.value = "";
        form.rbtn.innerHTML = "댓글 작성";
        loadReply();
      })
      .catch((err) => console.log(err));
  }
});
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

// 댓글 삭제 버튼 클릭 시 => foreach 구문을 쓴다
// document.querySelectorAll(".btn-outline-danger").forEach((btn) => {
//   btn.addEventListener("click", (e) => {
//     const targetBtn = e.target();

//     const rno = targetBtn.closest(".reply-row").dataset.rno;
//   });
// });

// 이벤트 버블링 활용
replyList.addEventListener("click", (e) => {
  console.log(e.target); // => 어느 버튼의 이벤트인가?
  const btn = e.target;

  // 부모쪽으로만 검색
  // data- 에 접근하려면 dataset 을 쓴다
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log("rno", rno);
  // 삭제 or 수정
  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    // true 인 경우 삭제요청(fetch)
    fetch(`http://localhost:8080/replies/${rno}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        // json body 추출 => text 추출
        return res.text();
      })
      .then((data) => {
        console.log(data);
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
            loadReply();
          });
        }
      })
      .catch((err) => console.log(err));
  } else if (btn.classList.contains("btn-outline-success")) {
    // rno 를 이용해 reply 가져오기
    // 가져온 reply 를 이용해 replyForm에 보여주기
    const form = document.querySelector("#replyForm");
    // 댓글 작성 버튼 => 댓글 수정
    // 댓글 수정 준비
    fetch(`${url}/${rno}`)
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        // json body 추출
        return res.json();
      })
      .then((data) => {
        console.log(data);

        form.rno.value = data.rno;
        form.replyer.value = data.replyer;
        form.text.value = data.text;
        // 버튼 텍스트를 댓글 수정으로 변경
        form.rbtn.innerHTML = "댓글 수정";
      })
      .catch((err) => console.log(err));
  }
});
