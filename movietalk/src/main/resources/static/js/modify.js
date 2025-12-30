document.querySelectorAll(".uploadResult .fa-xmark").forEach((item) => {
  item.addEventListener("click", (e) => {
    e.preventDefault();
    // 이미지를 삭제하고 수정 버튼을 누르지 않을 수도 있기 때문에
    // 폴더에서 직접 삭제하지 않는다
    const li = e.target.closest("li");
    if (confirm("정말로 삭제하시겠습니까?")) {
      // 화면에서만 이미지 제거
      li.remove();
    }
  });
});
// removeForm에 hidden 으로 mno를 집어 넣었다
document.querySelector(".delete").addEventListener("click", () => {
  const remove = document.querySelector("#removeForm");
  remove.submit();
});
