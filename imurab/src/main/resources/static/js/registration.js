let pwShowHide = document.querySelector(".showHidePw")
let pwcShowHide = document.querySelector(".showHidePwc")

let pwField = document.getElementById("password")
let pwcField = document.getElementById("confirmPassword")

pwShowHide.addEventListener("click", () => {
    if (pwField.type === "password") {
        pwField.type = "text"
        pwShowHide.classList.replace("bi-eye-slash", "bi-eye")
    } else {
        pwField.type = "password"
        pwShowHide.classList.replace("bi-eye", "bi-eye-slash")
    }
})

pwcShowHide.addEventListener("click", () => {
    if (pwcField.type === "password") {
        pwcField.type = "text"
        pwcShowHide.classList.replace("bi-eye-slash", "bi-eye")
    } else {
        pwcField.type = "password"
        pwcShowHide.classList.replace("bi-eye", "bi-eye-slash")
    }
})