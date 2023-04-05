let url = new URL(window.location.href)
const pagination = $("ul.pagination")

$(document).ready(function () {
    getAvp(1)
})

function getAvp(page) {
    let url = new URL(window.location.origin + `/api/avp?page=${page - 1}`);
    $.get(url, function (data) {
        renderAvp(data, page)
    })
}

function renderAvp(data, page) {
    let totalPages = data.totalPages;
    let elements = ''
    $.each(data.content, function (index, element) {
        elements += `
            <tr>
                <td>${element.name}</td>
                <td class="text-center" style="min-width: 400px">
                    <a href="/avp/edit/${element.id}" class="btn btn-success avp edit">${avpEdit}</a>
                    <a data-id="${element.id}" class="btn btn-danger avp delete">${avpDelete}</a>
                </td>
            </tr>    
        `
    })
    $("#avp tbody").html(elements)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function deleteAvp(id) {
    $.ajax({
        url: window.location.origin + `/api/avp/delete/${id}`,
        type: "DELETE",
        success: function () {
            hideDeleteModal()
            getAvp(page)
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

$("#avp").on("click", ".avp.delete", function () {
    showDeleteModal($(this).attr("data-id"))
})

$("#deleteModal .btn-close").on("click", function () {
    hideDeleteModal()
})

$("#close").on("click", function () {
    hideDeleteModal()
})

$("#delete").on("click", function () {
    deleteAvp($(this).attr("data-id"))
})