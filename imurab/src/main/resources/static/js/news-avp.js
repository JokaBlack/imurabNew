let url = new URL(window.location.href)
const pagination = $("ul.pagination")

$(document).ready(function () {
    if (url.searchParams.get("search")) {
        return getNewsAvpSearch(page, url.searchParams.get("search"))
    }
    getNewsAvp(page);
})

function getNewsAvp(page) {
    let url = new URL(window.location.origin + `/api/news/avp/${avpId}?page=${page - 1}&size=4&sort=id,desc`)
    $.get(url, function (data) {
        renderNews(data, page)
    })
}

function getNewsAvpSearch(page, search) {
    let url = new URL(window.location.origin + `/api/news/avp/${avpId}?search=${search}&page=${page - 1}&size=4&sort=id,desc`)
    $.get(url, function (data) {
        renderNews(data, page)
    })
}

function deleteNews(id) {
    $.ajax({
        url: window.location.origin + `/api/news/avp/${id}`,
        type: "DELETE",
        success: function () {
            hideDeleteModal()
            if (url.searchParams.get("search")) {
                return getNewsAvpSearch(page, url.searchParams.get("search"))
            }
            getNewsAvp(page)
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
    let newsContent = ''
    $.each(data.content, function (index, news) {
        newsContent += `
            <div class="card mb-2 mx-auto">
                <div class="row justify-content-between card-body">
                    <div class="title col-sm-12 col-md-6">
                        <a class="link-title" href="/news/avp/${news.id}">${news.title}</a>
                    </div>
                    <div class="image col-sm-12 col-md-6">
                        ${news.image.trim() === '' || news.image == null ? `<img class="news image" src="/projectImages/news/default_image.jpg">` :
                        `<img class="news image" src="/projectImages/news/${news.image}">`}
                    </div>
                </div>
                <div class="card-footer text-muted">
                    <p class="m-0">${createdAt}: ${news.createdAt}</p>
                </div>
                ${principal === "ROLE_MODERATOR" || principal === "ROLE_ADMIN" ?
                `<div class="card-footer">
                    <a href="/news/avp/${news.id}/edit" class="btn btn-success">Изменить</a>
                    <a data-id="${news.id}" class="news-delete btn btn-danger">Удалить</a>
                </div>` : ``}
            </div>   
        `
    })
    $("#news").html(newsContent)
    pagination.html(createPagination(totalPages, page))
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