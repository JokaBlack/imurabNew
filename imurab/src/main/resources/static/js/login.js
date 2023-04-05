const pwShowHide = document.querySelector(".showHidePw")
const pwField = document.querySelector("input[type=password]");

pwShowHide.addEventListener("click", () => {
    if (pwField.type === "password") {
        pwField.type = "text"
        pwShowHide.classList.replace("bi-eye-slash", "bi-eye")
    } else {
        pwField.type = "password"
        pwShowHide.classList.replace("bi-eye", "bi-eye-slash")
    }
})