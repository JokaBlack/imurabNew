let url = new URL(window.location.href)
let queueReviewAvp = ""
let queueReviewDepartments = ""
const pagination = $("ul.pagination")

$(document).ready(function () {
    getQueueCreated(1)
})

function getQueueCreated(page) {
    let url = new URL(window.location.origin + `/api/queue/review?page=${page - 1}&sort=id,desc`)
    $.get(url, function (data) {
        renderQueueCreated(data, page)
    })
}

function getQueueCreatedAvp(page, id) {
    let url = new URL(window.location.origin + `/api/queue/avp/${id}/review?page=${page - 1}&sort=id,desc`)
    $.get(url, function (data) {
        renderQueueCreated(data, page)
    })
}

function getQueueCreatedDepartment(page, id) {
    let url = new URL(window.location.origin + `/api/queue/departments/${id}/review?page=${page - 1}&sort=id,desc`)
    $.get(url, function (data) {
        renderQueueCreated(data, page)
    })
}

function renderQueueCreated(data, page) {
    let totalPages = data.totalPages;
    let elements = ''
    $.each(data.content, function (index, element) {
        elements += `
            <tr>
                <td>${element.field.name}</td>
                <td>${element.field.size}</td>
                <td>${element.field.fieldCropsDto.name}</td>
                <td>${element.field.department.name}</td>
                <td>${element.field.department.avpDto.name}</td>
                <td>${element.field.farmer.firstName}</td>
                <td>${element.field.farmer.lastName}</td>
                <td>${element.field.farmer.lastName}</td>
                <td>${element.startWatering}</td>
                <td>${element.endWatering}</td>
                <td class="text-center" style="min-width: 400px">
                    <a data-id="${element.id}" class="btn btn-success queue confirm">${queueReviewConfirm}</a>
                    <a data-id="${element.id}" class="btn btn-danger queue reject">${queueReviewReject}</a>
                </td>
            </tr>    
        `
    })
    $("#queueReviewTable tbody").html(elements)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function getDepartmentsAvp(id) {
    let url = new URL(window.location.origin + `/api/department/avp/${id}`)
    $.get(url, function (data) {
        renderDepartmentsAvp(data)
    })
}

function renderDepartmentsAvp(data) {
    let departments = `<option selected disabled value="">${department}</option>`
    $.each(data, function (index, department) {
        departments += `
            <option value="${department.id}">${department.name}</option>
        `
    })
    $("#departments").html(departments)
}

function confirmQueueReview(id) {
    $.ajax({
        url: window.location.origin + `/api/queue/confirm/${id}`,
        type: "PUT",
        success: function () {
            if (queueReviewAvp !== "" && queueReviewDepartments === "") {
                return getQueueCreatedAvp(page, queueReviewAvp)
            } else if (queueReviewAvp !== "" && queueReviewDepartments !== "") {
                return getQueueCreatedDepartment(page, queueReviewDepartments)
            }
            getQueueCreated(page)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content")
            let header = $("meta[name='_csrf_header']").attr("content")
            xhr.setRequestHeader(header, token)
        }
    })
}

function rejectQueueReview(id) {
    $.ajax({
        url: window.location.origin + `/api/queue/reject/${id}`,
        type: "PUT",
        success: function () {
            if (queueReviewAvp !== "" && queueReviewDepartments === "") {
                return getQueueCreatedAvp(page, queueReviewAvp)
            } else if (queueReviewAvp !== "" && queueReviewAvp !== "") {
                return getQueueCreatedDepartment(page, queueReviewDepartments)
            }
            getQueueCreated(page)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

$("#avp").change(function () {
    queueReviewAvp = $("#avp").val()
    queueReviewDepartments = ""
    getQueueCreatedAvp(1, queueReviewAvp)
    getDepartmentsAvp(queueReviewAvp)
})

$("#departments").change(function () {
    queueReviewDepartments = $("#departments").val()
    getQueueCreatedDepartment(1, queueReviewDepartments)
})

$("#queueReviewTable").on("click", ".confirm", function () {
    confirmQueueReview($(this).attr("data-id"))
})

$("#queueReviewTable").on("click", ".reject", function () {
    rejectQueueReview($(this).attr("data-id"))
})

