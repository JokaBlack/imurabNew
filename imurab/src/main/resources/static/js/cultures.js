let url = new URL(window.location.href)

const pagination = $("ul.pagination")

$(document).ready(function () {
    getCultures(1)
})

function getCultures(page) {
    let url = new URL(window.location.origin + `/api/field-crops?page=${page - 1}`);
    $.get(url, function (data) {
        renderCultures(data, page)
    })
}

function renderCultures(data, page) {
    let totalPages = data.totalPages;
    let elements = ''
    $.each(data.content, function (index, element) {
        elements += `
            <tr>
                <td>${element.name}</td>
                <td><img class="w-25" src="/projectImages/fieldCrops/${element.imgLink}" alt=""></td>
                <td class="text-center" style="min-width: 400px">
                    <a href="/field-crops/edit/${element.id}" class="btn btn-success culture edit">${avpEdit}</a>
                    <a data-id="${element.id}" class="btn btn-danger culture delete">${avpDelete}</a>
                </td>
            </tr>    
        `
    })
    $("#cultures tbody").html(elements)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function deleteCulture(id) {
    $.ajax({
        url: window.location.origin + `/api/field-crops/delete/${id}`,
        type: "DELETE",
        success: function () {
            hideDeleteModal()
            getCultures(page)
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

$("#cultures").on("click", ".culture.delete", function () {
    showDeleteModal($(this).attr("data-id"))
})

$("#deleteModal .btn-close").on("click", function () {
    hideDeleteModal()
})

$("#close").on("click", function () {
    hideDeleteModal()
})

$("#delete").on("click", function () {
    deleteCulture($(this).attr("data-id"))
})