let url = new URL(window.location.href)
const pagination = $("ul.pagination")

$(document).ready(function () {
    getFields(page);
})

function getFields(page) {
    let url = new URL(window.location.origin + `/api/fields?page=${page - 1}&size=8&sort=id,asc`)
    $.get(url, function (data) {
        renderFields(data, page)
    })
}

function renderFields(data, page) {
    let totalPages = data.totalPages;
    let fields = ''
    $.each(data.content, function (index, field) {
        fields += `
            <div class="col">
                <div class="card h-100 text-center">
                    <h5 class="card-title2">${field.fieldCropsDto.name} - ${field.name}</h5>
                    <p class="card-text2">${field.size} га</p>
                    <div class="h-100">
                        <img src="/projectImages/fieldCrops/${field.fieldCropsDto.imgLink}" class="card-img-top">
                    </div>
                    <div class="card-body block-3-card">
                        <a href="/fields/${field.id}" class="btn btn-primary w-50 mb-3 block-3-button">${fieldView}</a>
                    </div>
                </div>
            </div> 
        `
    })
    $("#fields .row").html(fields)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

