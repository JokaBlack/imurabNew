const url = new URL(window.location.href)

const startedAtEl = $("#startedAt")
const endedAtEl = $("#endedAt")
const descriptionEl = $("#description")
const historyIdEl = $("#historyId")
const editStartedAtEl = $("#editStartedAt")
const editEndedAtEl = $("#editEndedAt")
const editDescriptionEl = $("#editDescription")

const fieldId = url.pathname.split("/")[2]

const pagination = $("ul.pagination")

$(document).ready(function () {
    getFieldHistories(page, fieldId)
})

function clearModalForm(startedAt, endedAt, description) {
    startedAt.val("")
    endedAt.val("")
    description.val("")

    startedAt.removeClass("is-valid")
    endedAt.removeClass("is-valid")
    description.removeClass("is-valid")

    startedAt.removeClass("is-invalid")
    endedAt.removeClass("is-invalid")
    description.removeClass("is-invalid")
}

function getFieldHistories(page, fieldId) {
    let url = new URL(window.location.origin + `/api/fields/${fieldId}/histories?page=${page - 1}&size=20&sort=startedAt,asc`)
    $.get(url, function (data) {
        renderFieldHistory(data, page)
    })
}

function deleteField(id) {
    $.ajax({
        url: window.location.origin + `/api/fields/${id}/delete/`,
        type: "DELETE",
        success: function () {
            window.location.replace(window.location.origin + "/fields")
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function renderFieldHistory(data, page) {
    let totalPages = data.totalPages;
    let histories = ''
    $.each(data.content, function (index, history) {
        histories += `
            <li class="mb-1">
                ${history.startedAt} - ${history.endedAt}: ${history.description}
                <button data-id="${history.id}" class="btn btn-danger field-history-delete p-0"><i class="bi bi-x-lg"></i></button>
                <button data-id="${history.id}" class="btn btn-success field-history-edit p-0"><i class="bi bi-pencil-square"></i></button>
            </li>
        `
    })
    $("#historyList").html(histories)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function createFieldHistory() {
    let formData = {
        startedAt: startedAtEl.val(),
        endedAt: endedAtEl.val(),
        fieldId: fieldId,
        description: descriptionEl.val().trim()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: window.location.origin + `/api/fields/histories`,
        data: JSON.stringify(formData),
        data_type: "json",
        success: function () {
            clearModalForm(startedAtEl, endedAtEl, descriptionEl)
            hideHistoryModal()
            getFieldHistories(page, fieldId)
        },
        error: function (data) {
            let errors = JSON.parse(data.responseText)
            $.map(errors, function (v, k) {
                showError($(`#${k}`), v)
            })
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function editFieldHistory(id) {
    let formData = {
        id: id,
        startedAt: editStartedAtEl.val(),
        endedAt: editEndedAtEl.val(),
        fieldId: fieldId,
        description: editDescriptionEl.val().trim()
    };
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: window.location.origin + `/api/fields/histories/${id}`,
        data: JSON.stringify(formData),
        data_type: "json",
        success: function () {
            hideEditHistoryModal()
            clearModalForm(editStartedAtEl, editEndedAtEl, editDescriptionEl)
            getFieldHistories(page, fieldId)
        },
        error: function (data) {
            let errors = JSON.parse(data.responseText)
            $.map(errors, function (v, k) {
                showError($(`#edit` + `${k}`.charAt(0).toUpperCase() + `${k}`.slice(1)), v)
            })
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function deleteFieldHistory(id) {
    $.ajax({
        url: window.location.origin + `/api/fields/histories/${id}`,
        type: "DELETE",
        success: function () {
            getFieldHistories(page, fieldId)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function showDeleteModal(id) {
    $("#deleteModal").css("display", "block")
    $("#delete").attr("data-id", id)
    setTimeout(function () {
        $("#deleteModal").addClass("show")
    }, 150)
}

function hideDeleteModal() {
    setTimeout(function () {
        $("#deleteModal").css("display", "none")
    }, 150)
    $("#deleteModal").removeClass("show")
}

function showHistoryModal() {
    $("#fieldHistory").css("display", "block")
    setTimeout(function () {
        $("#fieldHistory").addClass("show")
    }, 150)
}

function hideHistoryModal() {
    setTimeout(function () {
        $("#fieldHistory").css("display", "none")
    }, 150)
    $("#fieldHistory").removeClass("show")
}

function showEditHistoryModal(id) {
    $("#editFieldHistory").css("display", "block")
    setTimeout(function () {
        $("#editFieldHistory").addClass("show")
    }, 150)
    let url = new URL(window.location.origin + `/api/fields/histories/${id}`)
    $.get(url, function (data) {
        let startedAt = createDate(data.startedAt)
        let endedAt = createDate(data.endedAt)

        startedAt.setHours(startedAt.getHours() + 6)
        endedAt.setHours(endedAt.getHours() + 6)

        historyIdEl.val(data.id)
        editStartedAtEl.val(startedAt.toISOString().substring(0, 16))
        editEndedAtEl.val(endedAt.toISOString().substring(0, 16))
        editDescriptionEl.val(data.description)

        showSuccess(editStartedAtEl)
        showSuccess(editEndedAtEl)
        showSuccess(editDescriptionEl)
    })
}

function hideEditHistoryModal() {
    setTimeout(function () {
        $("#editFieldHistory").css("display", "none")
    }, 150)
    $("#editFieldHistory").removeClass("show")
}

$("#fieldHistory").submit((e) => {
    e.preventDefault()

    let isStartedAtValid = checkStartedAt(startedAtEl)
    let isEndedAtValid = checkEndedAt(endedAtEl)
    let isDescriptionValid = checkDescription(descriptionEl)

    let isFormValid = isStartedAtValid && isEndedAtValid && isDescriptionValid

    if (isFormValid) {
        createFieldHistory()
    }
})

$("#editFieldHistory").submit((e) => {
    e.preventDefault()

    let isStartedAtValid = checkStartedAt(editStartedAtEl)
    let isEndedAtValid = checkEndedAt(editEndedAtEl)
    let isDescriptionValid = checkDescription(editDescriptionEl)

    let isFormValid = isStartedAtValid && isEndedAtValid && isDescriptionValid

    if (isFormValid) {
        editFieldHistory(historyIdEl.val())
    }
})

const isRequired = value => value !== ""

const showError = (input, message) => {
    const element = input.parent().children().last()
    input.addClass("is-invalid")
    element.show()
    element.html(message)
}

const showSuccess = (input) => {
    input.removeClass("is-invalid")
    input.addClass("is-valid")
}

const checkStartedAt = (element) => {
    const startedAt = element.val()
    if (!isRequired(startedAt)) {
        return showError(element, errorStartedAt)
    }

    showSuccess(element)
    return true
}

const checkEndedAt = (element) => {
    const endedAt = element.val()

    if (!isRequired(endedAt)) {
        return showError(element, errorEndedAt)
    }

    showSuccess(element)
    return true
}

const checkDescription = (element) => {
    const description = element.val()

    if (!isRequired(description)) {
        return showError(element, errorDescription)
    }

    showSuccess(element)
    return true
}

function createDate(date) {
    let dateArr = date.split(" ")[0].split(".")
    let timeArr = date.split(" ")[1].split(":")
    return new Date(dateArr[2], dateArr[1], dateArr[0], timeArr[0], timeArr[1])
}

$("#historyList").on("click", ".field-history-delete", function () {
    deleteFieldHistory($(this).attr("data-id"))
})

$("#historyList").on("click", ".field-history-edit", function (e) {
    e.preventDefault()
    showEditHistoryModal($(this).attr("data-id"))
})

$(".field-history").on("click", ".create-history", () => {
    showHistoryModal()
})

$("#fieldHistory .btn-close").on("click", () => {
    hideHistoryModal()
    clearModalForm(startedAtEl, endedAtEl, descriptionEl)
})

$("#fieldHistory .close").on("click", () => {
    hideHistoryModal()
    clearModalForm(startedAtEl, endedAtEl, descriptionEl)
})

$("#editFieldHistory .btn-close").on("click", () => {
    hideEditHistoryModal()
    clearModalForm(editStartedAtEl, editEndedAtEl, editDescriptionEl)
})

$("#editFieldHistory .close").on("click", () => {
    hideEditHistoryModal()
    clearModalForm(editStartedAtEl, editEndedAtEl, editDescriptionEl)
})

$("#deleteField").on("click", function () {
    showDeleteModal($(this).attr("data-id"))
})

$("#deleteModal .btn-close").on("click", function () {
    hideDeleteModal()
})

$("#delete").on("click", function (event) {
    event.preventDefault()
    deleteField($(this).attr("data-id"))
})

$("#close").on("click", function () {
    hideDeleteModal()
})


