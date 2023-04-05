let url = new URL(window.location.href)

$(document).ready(function () {
    getGlobalNews(page);
    if (avpIdUser !== "anonymousUser") {
        getNewsAvp(page, avpIdUser)
    }
})

function getGlobalNews(page) {
    let url = new URL(window.location.origin + `/api/news?page=${page - 1}&size=4&sort=id,desc`)
    $.get(url, function (data) {
        renderNews(data, page, "/news", "globalNews", "paginationGlobalNews")
    })
}

function getNewsAvp(page, avpId) {
    let url = new URL(window.location.origin + `/api/news/avp/${avpId}?page=${page - 1}&size=4&sort=id,desc`)
    $.get(url, function (data) {
        renderNews(data, page, "/news/avp", "newsAvpMain", "paginationAvpNews")
    })
}

function renderNews(data, page, url, id, pagination) {
    let totalPages = data.totalPages;
    let news = ''
    $.each(data.content, function (index, element) {
        news += `
            <div class="col">
                <div class="card h-100">
                    <h2 class="text-center my-2">${element.title}</h2>
                    <div class="h-100">
                        <img src="/projectImages/news/${element.image}" class="card-img-top">
                    </div>
                    <div class="card-body text-center block-3-card">
                        <a href="${url}/${element.id}" class="btn btn-primary w-50 mb-3 block-3-button">${more}</a>
                        <p class="block-3-card-text">${element.createdAt}</p>
                    </div>
                </div>
            </div>  
        `
    })
    $(`#${id}`).html(news)
    $(`#${pagination}`).html(createPagination(totalPages, page, pagination))
}