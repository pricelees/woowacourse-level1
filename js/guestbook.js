document.addEventListener("DOMContentLoaded", function() {
    // 등록 버튼 클릭 시 정보 불러오기
    document.getElementById("apply").addEventListener("click", function() {
        const name = document.getElementById("guest_name").value;
        const checkboxes = document.querySelectorAll('input[type="checkbox"][name="movie"]:checked');
        let length = checkboxes.length;

        alert(name + "님, " + "저와 " + length + "개의 취향이 같으시네요!");
    });
});

