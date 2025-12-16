// restcontroller 에서 만들어 놓은 주소랑 연동된다
const url = `http://localhost:8080/memo`;
const form = document.querySelector("#modify-form");
// fetch() : window 함수 기본 사용 가능

// 삭제 클릭시
document.querySelector(".btn-outline-danger").addEventListener("click", () => {
  const id = form.id.value;
  fetch(`http://localhost:8080/memo/${id}`, {
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
          title: "데이터 삭제 완료",
          icon: "success",
          draggable: true,
        }).then(() => {
          // then 을 추가해서 alert를 먼저 출력한다

          // 이동
          location.href = "/memo/list2";
        });
      }
    })
    .catch((err) => console.log(err));
});

// post(put)
form.addEventListener("submit", (e) => {
  e.preventDefault();
  // 스크립트 객체
  const send = {
    id: form.id.value,
    text: form.text.value,
  };
  console.log(send);

  fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json", //text/html;charset=UTF-8
    },
    body: JSON.stringify(send),
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
          title: "데이터 수정 완료",
          icon: "success",
          draggable: true,
        });
      }
    })
    .catch((err) => console.log(err));
});
