let url = new URL(window.location.href)
const pagination = $("ul.pagination")

$(document).ready(function () {
    if (url.searchParams.get("search")) {
        return getNewsSearch(page, url.searchParams.get("search"))
    }
    getNews(page);
})

function getNews(page) {
    let url = new URL(window.location.origin + `/api/news?page=${page - 1}&size=4&sort=id,desc`)
    $.get(url, function (data) {
        renderNews(data, page)
    })
}

function getNewsSearch(page, search) {
    let url = new URL(window.location.origin + `/api/news?search=${search}&page=${page - 1}&size=4&sort=id,desc`)
    $.get(url, function (data) {
        renderNews(data, page)
    })
}

function deleteNews(id) {
    $.ajax({
        url: window.location.origin + `/api/news/${id}`,
        type: "DELETE",
        success: function () {
            hideDeleteModal()
            if (url.searchParams.get("search")) {
                return getNewsSearch(page, url.searchParams.get("search"))
            }
            getNews(page)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function renderNews(data, page) {
    let totalPages = data.totalPages;
    let news = ''
    $.each(data.content, function (index, task) {
        news += `
            <div class="card mb-2 mx-auto">
                <div class="row justify-content-between card-body">
                    <div class="title col-sm-12 col-md-6">
                        <a class="link-title" href="/news/${task.id}">${task.title}</a>
                    </div>
                    <div class="image col-sm-12 col-md-6">
                        ${task.image.trim() === '' || task.image == null ? `<img class="news image" src="/projectImages/news/default_image.jpg">` :
            `<img class="news image" src="/projectImages/news/${task.image}">`}
                    </div>
                </div>
                <div class="card-footer text-muted">
                    <p class="m-0">${createdAt}: ${task.createdAt}</p>
                </div>
                ${principal === "ROLE_MODERATOR" || principal === "ROLE_ADMIN" ?
            `<div class="card-footer">
                    <a href="/news/${task.id}/edit" class="btn btn-success">Изменить</a>
                    <a data-id="${task.id}" class="news-delete btn btn-danger">Удалить</a>
                </div>` : ``}
            </div>   
        `
    })
    $("#news").html(news)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
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

$("#news").on("click", ".news-delete", function () {
    showDeleteModal($(this).attr("data-id"))
})

$("#deleteModal .btn-close").on("click", function () {
    hideDeleteModal()
})

$("#close").on("click", function () {
    hideDeleteModal()
})

$("#delete").on("click", function () {
    deleteNews($(this).attr("data-id"))
})