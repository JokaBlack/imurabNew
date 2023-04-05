let url = new URL(window.location.href)

const pagination = $("ul.pagination")

$(document).ready(function () {
    getDepartments(1)
})

function getDepartments(page) {
    let url = new URL(window.location.origin + `/api/department?page=${page - 1}`);
    $.get(url, function (data) {
        renderDepartment(data, page)
    })
}

function renderDepartment(data, page) {
    let totalPages = data.totalPages;
    let elements = ''
    $.each(data.content, function (index, element) {
        elements += `
            <tr>
                <td>${element.name}</td>
                <td>${element.avpDto.name}</td>
                <td class="text-center" style="min-width: 400px">
                    <a href="/department/edit/${element.id}" class="btn btn-success department edit">${departmentEdit}</a>
                    <a data-id="${element.id}" class="btn btn-danger department delete">${departmentDelete}</a>
                </td>
            </tr>    
        `
    })
    $("#departments tbody").html(elements)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function deleteDepartment(id) {
    $.ajax({
        url: window.location.origin + `/api/department/delete/${id}`,
        type: "DELETE",
        success: function () {
            hideDeleteModal()
            getDepartments(page)
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

$("#departments").on("click", ".department.delete", function () {
    showDeleteModal($(this).attr("data-id"))
})

$("#deleteModal .btn-close").on("click", function () {
    hideDeleteModal()
})

$("#close").on("click", function () {
    hideDeleteModal()
})

$("#delete").on("click", function () {
    deleteDepartment($(this).attr("data-id"))
})