let url = new URL(window.location.href)
const pagination = $("ul.pagination")

$(document).ready(function () {
    if (url.searchParams.get("search")) {
        return searchTopics(page, orderBy, order, url.searchParams.get("search"))
    }
    getTopics(1, "id", "ASC")
})

$("#filter-bar li").on("click", function () {
    $(".active").removeClass("active")
    $(this).addClass("active")
})

function getTopics(page, orderBy, order) {
    let url = new URL(window.location.origin + `/api/discussion-topics?page=${page - 1}&orderBy=${orderBy}&order=${order}`)
    $.get(url, function (data) {
        renderTopics(data, page)
    })
}

function searchTopics(page, orderBy, order, search) {
    let url = new URL(window.location.origin + `/api/discussion-topics?page=${page - 1}&orderBy=${orderBy}&order=${order}&search=${search}`)
    $.get(url, function (data) {
        renderTopics(data, page)
    })
}

function renderTopics(data, page) {
    let totalPages = data.totalPages;
    let users = ''
    $.each(data.content, function (index, topic) {
        users += `
                <tr>
                    <td class="bg-primary text-white" style="width: 30px; vertical-align: middle"><i
                        class="bi bi-circle-fill"></i></td>
                    <td>
                        <a href="/forum/discussion-topic/${topic.id}">
                        <div>
                            <p class="m-0 fw-bold">${topic.title}</p>
                            <p class="m-0" style="font-size: 12px">${author}: ${topic.fio},
                                <span>${createdAt}: ${topic.createdAt}</span></p>
                        </div>
                        </a>
                    </td>
                    <td>
                        <div>
                            <p class="m-0">${topic.messages} ${messages}</p>
                            <p class="m-0">${topic.views} ${views}</p>
                        </div>
                    </td>
                    <td>
                        ${principal === "ROLE_MODERATOR" || principal === "ROLE_ADMIN" ?
                            `<a data-id="${topic.id}" class="forum-delete btn btn-danger">Удалить</a>
                            <a href="/forum/discussion-topic/${topic.id}/edit" class="forum-edit btn btn-success">Изменить</a>` : ``}
                    </td>
                </tr>  
        `
    })
    $("#topics tbody").html(users)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function deleteTopic(id) {
    $.ajax({
        url: window.location.origin + `/api/discussion-topics/${id}`,
        type: "DELETE",
        success: function () {
            hideDeleteModal()
            getTopics(page, orderBy, order)
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

$("#topics").on("click", ".forum-delete", function () {
    showDeleteModal($(this).attr("data-id"))
})

$("#close").on("click", function () {
    hideDeleteModal()
})

$("#delete").on("click", function () {
    deleteTopic($(this).attr("data-id"))
})

$("#deleteModal .btn-close").on("click", function () {
    hideDeleteModal()
})

