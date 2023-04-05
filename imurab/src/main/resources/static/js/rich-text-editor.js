let optionsButtons = document.querySelectorAll(".option-button")
let advancedOptionButton = document.querySelectorAll(".adv-option-button")
let fontNameButton = document.getElementById("fontName")
let fontSizeButton = document.getElementById("fontSize")
let linkButton = document.getElementById("createLink")
let alignsButtons = document.querySelectorAll(".align")
let spacingButtons = document.querySelectorAll(".spacing")
let formatButtons = document.querySelectorAll(".format")
let scriptButtons = document.querySelectorAll(".script")
let inputText = document.getElementById("text-input")
let textArea = document.getElementById("textArea")

let fonts = [
    "Arial",
    "Verdana",
    "Times New Roman",
    "Garamond",
    "Georgia",
    "Courier New"
];

const initializer = () => {
    highlighter(alignsButtons, true)
    highlighter(spacingButtons, true)
    highlighter(formatButtons, false)
    highlighter(scriptButtons, true)

    fonts.map((value => {
        let option = document.createElement("option")
        option.value = value
        option.innerHTML = value
        fontNameButton.appendChild(option)
    }))

    for (let i = 1; i <= 7; i++) {
        let option = document.createElement("option")
        option.value = i;
        option.innerHTML = i;
        fontSizeButton.appendChild(option)
    }

    fontSizeButton.value = 3;
}

const modifyText = (command, defaultUi, value) => {
    document.execCommand(command, defaultUi, value)
}

optionsButtons.forEach(button => {
    button.addEventListener("click", () => {
        modifyText(button.id, false, null)
    })
})

advancedOptionButton.forEach((button) => {
    button.addEventListener("change", () => {
        modifyText(button.id, false, button.value)
    })
})

linkButton.addEventListener("click", () => {
    let userLink = prompt("Enter a URL");
    //if link has http then pass directly else add https
    if (/http/i.test(userLink)) {
        modifyText(linkButton.id, false, userLink);
    } else {
        userLink = "http://" + userLink;
        modifyText(linkButton.id, false, userLink);
    }
});

const highlighter = (className, needsRemoval) => {
    className.forEach((button) => {
        button.addEventListener("click", () => {
            if (needsRemoval) {
                let alreadyActive = false

                if (button.classList.contains("active")) {
                    alreadyActive = true
                }

                highlighterRemover(className)
                if (!alreadyActive) {
                    button.classList.add("active")
                }
            } else {
                button.classList.toggle("active")
            }
        })
    })
}

const highlighterRemover = (className) => {
    className.forEach((button) => {
        button.classList.remove("active")
    })
}

inputText.oninput = (e) => {
    textArea.value = inputText.innerHTML
}

window.onload = function () {
    initializer();
    textArea.value = inputText.innerHTML
}

