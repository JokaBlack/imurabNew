let url = new URL(window.location.href)
const pagination = $("ul.pagination")

let messageEditEl = $("#messageEdit")
let messageEl = $("#message")
let messageIdEl = $("#messageId")

const topicId = url.pathname.split("/")[3]
const message = $("#message")

$(document).ready(function () {
    getMessages(1, topicId)
})

function getMessages(page, topic) {
    let url = new URL(window.location.origin + `/api/discussion-topics/${topic}/messages?page=${page - 1}`)
    $.get(url, function (data) {
        renderMessages(data, page)
    })
}

function deleteMessage(id) {
    $.ajax({
        url: window.location.origin + `/api/discussion-topics/messages/${id}`,
        type: "DELETE",
        success: function () {
            getMessages(page, topicId)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function renderMessages(data, page) {
    let totalPages = data.totalPages;
    let messages = ''
    $.each(data.content, function (index, message) {
        messages += `
            <div class="message mb-3">
                <div class="header d-flex p-1">
                    <p class="m-0 fw-bold">${message.fio}</p>
                    <p class="m-0">${message.createdAt}</p>
                </div>
                <div class="body">
                    <p>${message.message}</p>
                    ${principal !== "anonymousUser" && principal == message.userId ? `<div class="message-action">` +
            `<a data-id="${message.id}" class="message-edit btn btn-success">${messageEdit}</a>` +
            `<a data-id="${message.id}" class="message-delete btn btn-danger">${messageDelete}</a>` +
            `</div>` : ''}
                </div>
            </div>
        `
    })
    $("#messages").html(messages)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function createMessage() {
    let formData = {
        message: message.val().trim(),
        topicId: topicId
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: window.location.origin + "/api/discussion-topics/messages/create",
        data: JSON.stringify(formData),
        data_type: "json",
        success: function () {
            getMessages(page, topicId)
            messageEl.removeClass("is-invalid")
            messageEl.val("")
        },
        error: function (data) {
            let errors = JSON.parse(data.responseText)
            $.map(errors, function (v, k) {
                showError($(`#${k}`), v)
            })
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content")
            let header = $("meta[name='_csrf_header']").attr("content")
            xhr.setRequestHeader(header, token)
        }
    })
}

function editMessage(id) {
    let formData = {
        id: id,
        message: messageEditEl.val().trim()
    };
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: window.location.origin + `/api/discussion-topics/messages/${id}`,
        data: JSON.stringify(formData),
        data_type: "json",
        success: function () {
            hideEditMessageModal()
            messageEditEl.removeClass("is-invalid")
            messageEditEl.val("")
            getMessages(page, topicId)
        },
        error: function (data) {
            let errors = JSON.parse(data.responseText)
            $.map(errors, function (v, k) {
                showError($(`#${k}Edit`), v)
            })
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

$("#messages").on("click", ".message-delete", function () {
    deleteMessage($(this).attr("data-id"))
})

function showEditMessageModal(id) {
    $("#editMessage").css("display", "block")
    $("#messageId").val(id)
    setTimeout(function () {
        $("#editMessage").addClass("show")
    }, 150)
    let url = new URL(window.location.origin + `/api/discussion-topics/messages/${id}`)
    $.get(url, function (data) {
        messageEditEl.val(data.message)
    })
}

function hideEditMessageModal() {
    setTimeout(function () {
        $("#editMessage").css("display", "none")
    }, 150)
    $("#editMessage").removeClass("show")
}

const isRequired = value => value !== ""

const checkMessageEdit = (element) => {
    const messageEdit = messageEditEl.val()

    if (!isRequired(messageEdit)) {
        return showError(element, messageError)
    }

    return true
}

const checkMessage = (element) => {
    const message = messageEl.val()

    if (!isRequired(message)) {
        return showError(element, messageError)
    }

    return true
}

const showError = (input, message) => {
    const element = input.parent().children().last()
    input.addClass("is-invalid")
    element.show()
    element.html(message)
}

$("#messages").on("click", ".message-edit", function () {
    showEditMessageModal($(this).attr("data-id"))
})

$("#editMessage").on("click", ".close", function () {
    hideEditMessageModal()
})

$("#editMessage").on("click", ".btn-close", function () {
    hideEditMessageModal()
})

$("#sendMessage").on("click", function () {
    if (checkMessage(messageEl)) {
        createMessage()
    }
})

$("#editMessageForm").submit(function (e) {
    e.preventDefault()
    if (checkMessageEdit(messageEditEl)) {
        editMessage(messageIdEl.val())
    }
})