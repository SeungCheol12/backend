// x를 클릭 시 파일 삭제
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();

  const aTag = e.target.closest("a");
  const div = e.target.closest("li");
  // href 값 가져오기
  console.log("속성 값", aTag.href);
  console.log("속성 값", aTag.getAttribute("href"));
  const href = aTag.getAttribute("href");

  // 컨트롤러로 요청 보내기
  const formData = new FormData();
  formData.append("fileName", href); // controller remove와 이름을 맞춘다
  fetch("/upload/remove", {
    method: "POST",
    body: formData,
  })
    .then((res) => res.text())
    .then((data) => {
      console.log(data);
      // 화면에서 이미지 제거
      div.remove();
    });
});
