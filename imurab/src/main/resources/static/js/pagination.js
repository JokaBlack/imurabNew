let page = 1;

let orderBy = "id"
let order = "ASC"

let page2 = 1;


$(".pagination").on("click", ".page-item", function () {
    let tag = $(this).attr("class").trim()

    let mainPaginationPage = $(this).parent().attr("id")

    if (mainPaginationPage === "paginationGlobalNews") {
        if (tag == "page-item prev") {
            page -= 1
        } else if (tag == "page-item next") {
            page += 1
        } else {
            page = parseInt($(this).children(0).text())
        }

        return getGlobalNews(page)
    } else if (mainPaginationPage === "paginationAvpNews") {
        if (tag == "page-item prev") {
            page2 -= 1
        } else if (tag == "page-item next") {
            page2 += 1
        } else {
            page2 = parseInt($(this).children(0).text())
        }
        return getNewsAvp(page2, avpIdUser)
    }

    if (tag == "page-item prev") {
        page -= 1
    } else if (tag == "page-item next") {
        page += 1
    } else {
        page = parseInt($(this).children(0).text())
    }

    if (url.pathname.split("/")[2] === "discussion-topic") {
        getMessages(page, topicId)
    } else if (url.pathname === "/forum") {
        getTopics(page, orderBy, order)
    } else if (url.pathname === "/users") {
        if (usersAvp !== "") {
            return getUsersAvp(page, orderBy, order, usersAvp, url.searchParams.get("search"))
        } else if (url.searchParams.get("search")) {
            return searchUsers(page, orderBy, order, url.searchParams.get("search"))
        }
        getUsers(page, orderBy, order)
    } else if (url.pathname === "/news") {
        getNewsAvp(page)
    } else if (url.pathname === "/fields") {
        getFields(page)
    } else if (url.pathname.match(/^\/fields\/(\d)/)) {
        getFieldHistories(page, fieldId)
    } else if (url.pathname == "/queue") {
        getQueue(page)
    } else if (url.pathname == "/queue/1") {
        getQueue(page)
    } else if (url.pathname === "/fields/review") {
        if (fieldReviewAvp !== "" && fieldReviewDepartments === "") {
            return getFieldsAvpReview(page, fieldReviewAvp, orderBy, order)
        } else if (fieldReviewAvp !== "" && fieldReviewDepartments !== "") {
            return getFieldsDepartmentReview(page, queueReviewDepartments, orderBy, order)
        }
        getFieldsReview(page, orderBy, order)
    } else if (url.pathname === "/news/avp") {
        if (url.searchParams.get("search")) {
            getNewsAvpSearch(page, url.searchParams.get("search"))
        }
        getNewsAvp(page)
    } else if (url === "/queue/review") {
        if (queueReviewAvp !== "" && queueReviewDepartments === "") {
            return getQueueCreatedAvp(page, queueReviewAvp)
        } else if (avp !== "" && departments !== "") {
            return getQueueCreatedDepartment(page, queueReviewDepartments)
        }
        getQueueCreated(page)
    } else if (url === "/avp") {
        getAvp(page)
    } else if (url === "/department") {
        getDepartments(page)
    } else if (url === "/field-crops") {
        getCultures(page)
    }
})

$("#users [data-column]").on("click", function () {
    orderBy = $(this).attr("data-column")
    order = $(this).attr("data-order")

    if (order == "DESC") {
        $(this).attr("data-order", "ASC")
    } else {
        $(this).attr("data-order", "DESC")
    }

    if (url.searchParams.get("search")) {
        return searchUsers(page, orderBy, order, url.searchParams.get("search"))
    }

    getUsers(page, orderBy, order)
})

$("#fields [data-column]").on("click", function () {
    orderBy = $(this).attr("data-column")
    order = $(this).attr("data-order")

    if (order == "DESC") {
        $(this).attr("data-order", "ASC")
    } else {
        $(this).attr("data-order", "DESC")
    }

    if (avp !== "" && departments === "") {
        return getFieldsAvpReview(page, avp, orderBy, order)
    } else if (avp !== "" && departments !== "") {
        return getFieldsDepartmentReview(page, departments, orderBy, order)
    }

    getFieldsReview(page, orderBy, order)
})

$("#filter-bar [data-column]").on("click", function () {
    orderBy = $(this).attr("data-column")
    order = $(this).attr("data-order")

    if (url.searchParams.get("search")) {
        return searchTopics(page, orderBy, order, url.searchParams.get("search"))
    }

    getTopics(page, orderBy, order)
})

function createPagination(totalPages, page, pagination) {
    let liTag = ''
    let active
    let beforePage = page - 1
    let afterPage = page + 1
    if (page > 1) { //show the next button if the page value is greater than 1
        liTag += `<li class="page-item prev"><a class="page-link"><i class="bi bi-arrow-left"></i></a></li>`
    }

    if (page > 2 && totalPages > 4) { //if page value is less than 2 then add 1 after the previous button
        liTag += `<li class="page-item"><a class="page-link">1</a></li>`
        if (page > 3 && totalPages > 4) { //if page value is greater than 3 then add this (...) after the first li or page
            liTag += `<li class="page-item disabled"><a class="page-link">...</a></li>`
        }
    }

    // how many pages or li show before the current li
    if (page == totalPages && beforePage > 1) {
        beforePage = beforePage - 2
    } else if (page == totalPages - 1 && beforePage > 0) {
        beforePage = beforePage - 1
    }
    // how many pages or li show after the current li
    if (page == 1 && totalPages > 2) {
        afterPage = afterPage + 2
    } else if (page == 2 && totalPages > 1) {
        afterPage = afterPage + 1
    }

    for (let plength = beforePage; plength <= afterPage; plength++) {
        if (plength > totalPages) { //if plength is greater than totalPage length then continue
            continue
        }
        if (plength == 0) { //if plength is 0 than add +1 in plength value
            plength = plength + 1
        }
        if (page == plength) { //if page is equal to plength than assign active string in the active variable
            active = "active"
        } else { //else leave empty to the active variable
            active = ""
        }

        if (totalPages > 1) {
            liTag += `<li class="page-item ${active}"><a class="page-link">${plength}</a></li>`
        }
    }

    if (page < totalPages - 1 && totalPages > 4) { //if page value is less than totalPage value by -1 then show the last li or page
        if (page < totalPages - 2 && totalPages > 4) { //if page value is less than totalPage value by -2 then add this (...) before the last li or page
            liTag += `<li class="page-item disabled"><a class="page-link">...</a></li>`
        }
        liTag += `<li class="page-item"><a class="page-link">${totalPages}</a></li>`
    }

    if (page < totalPages) { //show the next button if the page value is less than totalPage
        liTag += `<li class="page-item next"><a class="page-link"><i class="bi bi-arrow-right"></i></a></li>`
    }
    $(`${pagination}`).html(liTag); //add li tag inside ul tag
    return liTag; //return the li tag
}