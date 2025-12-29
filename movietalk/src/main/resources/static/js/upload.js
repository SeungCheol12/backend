const fileInput = document.querySelector("[name='file']");

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
const showUploadImages = (files) => {
  const output = document.querySelector(".uploadResult ul");
  let tags = "";

  files.forEach((file) => {
    tags += `<li data-name="${file.imgName}" data-path"${file.path}" data-uuid="${file.uuid}">`;
    tags += `<a href ="${file.imageURL}">`;
    tags += `<img src="/upload/display?fileName=${file.thumbnailURL}" class = "block">`;
    tags += "</a>";
    tags += `<span class="text-sm d-inline-block mx-1">${file.imgName}</span>`;
    tags += `<a href ="${file.imageURL}" data-file=""><i class="fa-solid fa-xmark"></i></a>`;
    tags += "</li>";
  });
  output.insertAdjacentHTML("beforeend", tags);
};
fileInput.addEventListener("change", (e) => {
  const files = fileInput.files;
  const formData = new FormData();
  for (let idx = 0; idx < files.length; idx++) {
    formData.append("uploadFiles", files[idx]); // "uploadFiles" => uploadContoller 와 이름을 맞춘다
  }
  fetch("/upload/upload", {
    method: "POST",
    body: formData,
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);
      showUploadImages(data);
    });
});
