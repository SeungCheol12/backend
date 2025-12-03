// 삭제버튼 클릭 시
// remove-form 가져온 후 sunmit 시키기
document.querySelector(".btn-outline-danger").addEventListener("click", () => {
  const remove = document.querySelector("[name='remove-form']");

  remove.submit();
});
