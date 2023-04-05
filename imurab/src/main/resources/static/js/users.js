let url = new URL(window.location.href)
let usersAvp =  ""
const pagination = $("ul.pagination")

$(document).ready(function () {
    if (url.searchParams.get("search")) {
        return searchUsers(page, orderBy, order, url.searchParams.get("search"))
    }
    getUsers(page, orderBy, order);
})

function getUsers(page, orderBy, order) {
    let url = new URL(window.location.origin + `/api/users?page=${page - 1}&orderBy=${orderBy}&order=${order}`)
    $.get(url, function (data) {
        renderUsers(data, page)
    })
}

function getUsersAvp(page, orderBy, order, avp, search) {
    let url = new URL(window.location.origin + `/api/users/avp/${avp}?page=${page - 1}&size=10&sort=${orderBy},${order}` + (search != null ? `&search=${search}` : ``))
    $.get(url, function (data) {
        renderUsers(data, page)
    })
}

function searchUsers(page, orderBy, order, search) {
    let url = new URL(window.location.origin + `/api/users?search=${search}&page=${page - 1}&orderBy=${orderBy}&order=${order}`)
    $.get(url, function (data) {
        renderUsers(data, page);
    })
}

function renderUsers(data, page) {
    let totalPages = data.totalPages;
    let users = ''
    $.each(data.content, function (index, user) {
        users += `
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.patronymic}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.role}</td>
                <td class="text-center" style="min-width: 400px">
                    <a data-id="${user.id}" class="btn btn-success user edit" href="/users/edit/${user.id}">${userEdit}</a>
                    <a data-id="${user.id}" class="btn btn-danger user delete">${userDelete}</a>
                    ${user.locked ? `<a data-id="${user.id}" class='btn btn-warning user activation'>${userActivation}</a>` :
            `<a data-id="${user.id}" class='btn btn-danger user deactivation'>${userLocked}</a>`}
                </td>
            </tr>    
        `
    })
    $("#users tbody").html(users)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

function deleteUser(id) {
    $.ajax({
        url: window.location.origin + `/api/users/delete/${id}`,
        type: "DELETE",
        success: function () {
            hideDeleteModal()
            if (url.searchParams.get("search")) {
                return searchUsers(page, orderBy, order, url.searchParams.get("search"))
            }
            getUsers(page, orderBy, order)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token)
        }
    })
}

function activationUser(id) {
    $.ajax({
        url: window.location.origin + `/api/users/activation/${id}`,
        type: "PUT",
        success: function () {
            if (url.searchParams.get("search")) {
                return searchUsers(page, orderBy, order, url.searchParams.get("search"))
            }
            getUsers(page, orderBy, order)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content")
            let header = $("meta[name='_csrf_header']").attr("content")
            xhr.setRequestHeader(header, token)
        }
    })
}

function deactivationUser(id) {
    $.ajax({
        url: window.location.origin + `/api/users/deactivation/${id}`,
        type: "PUT",
        success: function () {
            if (url.searchParams.get("search")) {
                return searchUsers(page, orderBy, order, url.searchParams.get("search"))
            }
            getUsers(page, orderBy, order)
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content")
            let header = $("meta[name='_csrf_header']").attr("content")
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

$("#users").on("click", ".user.delete", function () {
    showDeleteModal($(this).attr("data-id"))
})

$("#deleteModal .btn-close").on("click", function () {
    hideDeleteModal()
})

$("#users").on("click", ".user.activation", function () {
    activationUser($(this).attr("data-id"))
})

$("#users").on("click", ".user.deactivation", function () {
    deactivationUser($(this).attr("data-id"))
})

$("#delete").on("click", function () {
    deleteUser($(this).attr("data-id"))
})

$("#close").on("click", function () {
    hideDeleteModal()
})

$("#avp").change(function () {
    usersAvp = $("#avp").val()
    getUsersAvp(page, orderBy, order, usersAvp, url.searchParams.get("search"))
})