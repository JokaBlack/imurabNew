let url = new URL(window.location.href)
let fieldReviewAvp = ""
let fieldReviewDepartments = ""
const pagination = $("ul.pagination")

$(document).ready(function () {
    getFieldsReview(page, orderBy, order);
})

function getFieldsReview(page, orderBy, order) {
    let url = new URL(window.location.origin + `/api/fields/review?page=${page - 1}&size=8&sort=${orderBy},${order}`)
    $.get(url, function (data) {
        renderFields(data, page)
    })
}

function getFieldsAvpReview(page, id, orderBy, order) {
    let url = new URL(window.location.origin + `/api/fields/avp/${id}/review?page=${page - 1}&size=8&sort=${orderBy},${order}`)
    $.get(url, function (data) {
        renderFields(data, page)
    })
}

function getFieldsDepartmentReview(page, id, orderBy, order) {
    let url = new URL(window.location.origin + `/api/fields/departments/${id}/review?page=${page - 1}&size=8&sort=${orderBy},${order}`)
    $.get(url, function (data) {
        renderFields(data, page)
    })
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

function renderFields(data, page) {
    let totalPages = data.totalPages;
    let fields = ''
    $.each(data.content, function (index, field) {
        fields += `
            <tr>
                <td>${field.name}</td>
                <td>${field.size}</td>
                <td>${field.department.avpDto.name}
                </td>
                <td>${field.department.name}</td>
                <td>${field.fieldCropsDto.name}</td>
                <td>${field.farmer.lastName}</td>
                <td>${field.farmer.firstName}</td>
                <td>${field.farmer.patronymic}</td>
                <td class="text-end" style="min-width: 400px">
                    <a data-id="${field.id}" class="btn btn-success field confirm">${fieldConfirm}</a>
                    <a data-id="${field.id}" class="btn btn-danger field reject">${fieldReject}</a>
                    <a data-id="${field.id}" class="btn btn-warning field edit" href="/fields/${field.id}/edit">${fieldEdit}</a>
                </td>
            </tr>    
        `
    })
    $("#fields tbody").html(fields)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function confirmField(id) {
    $.ajax({
        url: window.location.origin + `/api/fields/${id}/confirm`,
        type: "PUT",
        success: function () {
            getFieldsReview(page, orderBy, order)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function rejectField(id) {
    $.ajax({
        url: window.location.origin + `/api/fields/${id}/reject`,
        type: "DELETE",
        success: function () {
            getFieldsReview(page, orderBy, order)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

$("#fields").on("click", ".confirm", function () {
    confirmField($(this).attr("data-id"))
})

$("#fields").on("click", ".reject", function () {
    rejectField($(this).attr("data-id"))
})

$("#avp").change(function () {
    fieldReviewAvp = $("#avp").val()
    fieldReviewDepartments = ""
    getFieldsAvpReview(1, fieldReviewAvp, orderBy, order)
    getDepartmentsAvp(fieldReviewDepartments)
})

$("#departments").change(function () {
    fieldReviewDepartments = $("#departments").val()
    getFieldsDepartmentReview(1, fieldReviewDepartments, orderBy, order)
})

