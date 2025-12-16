// restcontroller 에서 만들어 놓은 주소랑 연동된다
const url = `http://localhost:8080/memo`;
const form = document.querySelector("#insert-form");
// fetch() : window 함수 기본 사용 가능
// post(put)
form.addEventListener("submit", (e) => {
  e.preventDefault();

  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json", //text/html;charset=UTF-8
    },
    body: JSON.stringify({ text: e.target.text.value }),
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
          title: "데이터 추가 완료",
          icon: "success",
          draggable: true,
        });
      }
    })
    .catch((err) => console.log(err));
});
