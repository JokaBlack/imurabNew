let url = new URL(window.location.href)
let pagination = $('ul.pagination')
const options = {year: '2-digit', month: '2-digit', day: '2-digit'};

$(document).ready(function () {
    url = new URL(window.location.origin + `/api/queue?page=${page - 1}`)
    getQueue(page);
})

function getQueue() {
    $.get(url, function (data) {
        renderQueue(data, page)
    })
}

function timeFormat(timestamp) {
    const date = new Date(timestamp)
    return (date.getDate() +
        "-" + (date.getMonth() + 1) +
        "-" + date.getFullYear() +
        " " + date.getHours() +
        ":0" + date.getMinutes());
}

function renderQueue(data, page) {
    let totalPages = data.totalPages;
    let queue = ''
    $.each(data.content, function (index, task) {
        queue += `<div class="row">
                  <div class="col-3">${task.startWatering}</div>
                  <div class="col-3">${task.field.department.name}</div>
                  <div class="col-3">${task.field.name}</div>
                  <div class="col-3">${task.field.farmer.lastName}</div></div>`
    })
    $("#queue").html(queue)
    pagination.html(createPagination(totalPages, page, "ul.pagination"))
}

const queueForm = document.getElementById('queue-form');

function queueHandler(e) {
    e.preventDefault();

    const a = document.getElementById('selectAVP');
    const d = document.getElementById('selectDepartment');
    let queue = document.getElementById('queue');
    queue.innerHTML = '';
    let vacant = document.getElementById('divVacantDays');
    vacant.innerHTML = '';

    if (d.value >= 0) {
        fetch(window.location.origin + '/api/queue/' + d.value)
            .then(response => response.json()).then(data => {
            for (let i = 0; i < data.length; i++) {
                queue.innerHTML += `<div class="row">
                  <div class="col-3">${data[i].startWatering}</div>
                  <div class="col-3">${data[i].field.department.name}</div>
                  <div class="col-3">${data[i].field.name}</div>
                  <div class="col-3">${data[i].field.farmer.lastName}</div></div>`
            }
        });
    }
    if (d.value >= 0) {
        let text = '';
        let background;
        fetch(window.location.origin + '/api/queue/vacant/' + d.value)
            .then(response => response.json()).then(data => {

            for (let i = 0; i < data.length; i++) {
                text += `<div class="row align-items-center text-center"> <div class="col w-50" style="border: #008CBF 1px solid; border-radius: 15%; background: #FFFFFF; max-width: 80px;">${new Date(data[i].date).toLocaleDateString('en-GB', options)}</div>`;
                for (let j = 0; j < (data[i].hours.length); j++) {
                    if (data[i].hours[j].free)
                        background = 'lightgreen';
                    else
                        background = '#FF3366';
                    text += `<div class="col text-center" style="border: #008CBF 1px solid; border-radius: 20%; background:${background};  max-width: 40px;">${data[i].hours[j].hour}</div>`;
                }
                text += `</div>`;
            }
            vacant.innerHTML = text;
        });
    }
}

function myFunction() {
    let avp = document.getElementById('selectAVP');
    let list = document.getElementById("selectDepartment");
    let queue = document.getElementById('queue');
    const mySet = new Map();
    queue.innerHTML = '';
    list.innerHTML = '<option selected>Выберите участок</option>';
    if (avp.value >= 0) {
        fetch(window.location.origin + '/api/queue?page=0')
            .then(response => response.json()).then(data => {
            for (let i = 0; i < data.content.length; i++) {
                if (avp.value == data.content[i].field.department.avpDto.id)
                    mySet.set(data.content[i].field.department.id, data.content[i].field.department.name);
            }
            mySet.forEach(function (value, key, map) {
                list.innerHTML += `<option value="${key}" id="departmentId${key}" name="${value}">${value}</option>`
            })
        });
    }
}

document.getElementById("selectAVP").addEventListener("select", myFunction);
queueForm.addEventListener('submit', queueHandler);

var Cal = function (divId) {

    //Сохраняем идентификатор div
    this.divId = divId;

    // Дни недели с понедельника
    this.DaysOfWeek = [
        'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб', 'Вс'
    ];

    // Месяцы начиная с января
    this.Months = ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'];

    //Устанавливаем текущий месяц, год
    var d = new Date();

    this.currMonth = d.getMonth('9');
    this.currYear = d.getFullYear('22');
    this.currDay = d.getDate('3');
};

// Переход к следующему месяцу
Cal.prototype.nextMonth = function () {
    if (this.currMonth == 11) {
        this.currMonth = 0;
        this.currYear = this.currYear + 1;
    } else {
        this.currMonth = this.currMonth + 1;
    }
    this.showcurr();
};

// Переход к предыдущему месяцу
Cal.prototype.previousMonth = function () {
    if (this.currMonth == 0) {
        this.currMonth = 11;
        this.currYear = this.currYear - 1;
    } else {
        this.currMonth = this.currMonth - 1;
    }
    this.showcurr();
};

// Показать текущий месяц
Cal.prototype.showcurr = function () {
    this.showMonth(this.currYear, this.currMonth);
};


// Показать месяц (год, месяц)
Cal.prototype.showMonth = function (y, m) {

    var d = new Date()
        // Первый день недели в выбранном месяце
        , firstDayOfMonth = new Date(y, m, 7).getDay()
        // Последний день выбранного месяца
        , lastDateOfMonth = new Date(y, m + 1, 0).getDate()
        // Последний день предыдущего месяца
        , lastDayOfLastMonth = m == 0 ? new Date(y - 1, 11, 0).getDate() : new Date(y, m, 0).getDate();


    var html = '<table>';

    // Запись выбранного месяца и года
    html += '<thead><tr>';
    html += '<td colspan="7">' + this.Months[m] + ' ' + y + '</td>';
    html += '</tr></thead>';


    // заголовок дней недели
    html += '<tr class="days">';
    for (var i = 0; i < this.DaysOfWeek.length; i++) {
        html += '<td>' + this.DaysOfWeek[i] + '</td>';
    }
    html += '</tr>';

    // Записываем дни
    var i = 1;
    do {

        var dow = new Date(y, m, i).getDay();

        // Начать новую строку в понедельник
        if (dow == 1) {
            html += '<tr>';
        }

        // Если первый день недели не понедельник показать последние дни предидущего месяца
        else if (i == 1) {
            html += '<tr>';
            var k = lastDayOfLastMonth - firstDayOfMonth + 1;
            for (var j = 0; j < firstDayOfMonth; j++) {
                html += '<td class="not-current">' + k + '</td>';
                k++;
            }
        }

        // Записываем текущий день в цикл
        var chk = new Date();
        var chkY = chk.getFullYear();
        var chkM = chk.getMonth();
        if (chkY == this.currYear && chkM == this.currMonth && i == this.currDay) {
            html += '<td class="today busy">' + i + '</td>';
        } else {
            html += '<td class="normal">' + i + '</td>';
        }
        // закрыть строку в воскресенье
        if (dow == 0) {
            html += '</tr>';
        }
        // Если последний день месяца не воскресенье, показать первые дни следующего месяца
        else if (i == lastDateOfMonth) {
            var k = 1;
            for (dow; dow < 7; dow++) {
                html += '<td class="not-current">' + k + '</td>';
                k++;
            }
        }

        i++;
    } while (i <= lastDateOfMonth);

    // Конец таблицы
    html += '</table>';

    // Записываем HTML в div
    document.getElementById(this.divId).innerHTML = html;
};

// Получить элемент по id
function getId(id) {
    return document.getElementById(id);
}

window.addEventListener('load', function () {
    console.log(loginUserId); // не трогать! это проверка юзера
    fetch(window.location.origin + '/api/queue/myActualQueue')
        .then(response => response.json())
        .then(data => {
            data.forEach(item => {
                let card = document.createElement('div');
                card.className = 'col-md-6 col-lg-4 mb-4';
                card.innerHTML = `
          <div class="card">
            <img src="${'/projectImages/fieldCrops/' + item.field.fieldCropsDto.imgLink}" class="card-img-top" alt="${item.field.fieldCropsDto.name}">
            </div>`;
                let cardBody = document.createElement('div');
                cardBody.className = 'card-body';
                const cardTitle = document.createElement("h5");
                cardTitle.classList.add("card-title");
                cardTitle.textContent = item.field.department.name;

                const p1 = document.createElement("p");
                p1.classList.add("card-text");
                p1.textContent = item.field.fieldCropsDto.name + ' : ' + item.field.name;
                const p2 = document.createElement("p");
                p2.classList.add("card-text");
                p2.textContent = 'Начало полива: ' + item.startWatering;
                const p3 = document.createElement("p");
                p3.classList.add("card-text");
                p3.textContent = 'Конец полива: ' + item.endWatering;

                cardBody.appendChild(cardTitle);
                cardBody.appendChild(p1);
                cardBody.appendChild(p2);
                cardBody.appendChild(p3);
                cardBody.appendChild(queueActionButton(item));
                card.appendChild(cardBody);

                document.getElementById('queue-list').appendChild(card);
            });
        })
        .catch(error => console.error(error));
});

function queueActionButton(queue) {
    const button = document.createElement("button");
    button.classList.add("btn", "btn-primary");
    const now = new Date();
    const pattern = /(\d{2})\.(\d{2})\.(\d{2}) (\d{2}):(\d{2})/;
    const [, day, month, year, hours, minutes] = pattern.exec(queue.startWatering);
    const date = new Date(`${month} ${day} 20${year} ${hours}:${minutes}`);

    if (date > now && queue.status === "CREATED" || queue.status === "CONFIRMED") {
        button.textContent = "Отменить";
        button.addEventListener("click", () => {
            $.ajax({
                url: window.location.origin + `/api/queue/skip/${queue.id}`,
                type: "PUT",
                contentType: "application/json",
                success: function (queue) {
                    const message = "Ваша заявка на полив Отменена!";
                    alert(message);
                    window.location.reload();
                },
                error: function (request) {
                    alert(" Error: " + request.responseText);
                },
                beforeSend: function (xhr) {
                    let token = $("meta[name='_csrf']").attr("content");
                    let header = $("meta[name='_csrf_header']").attr("content");
                    xhr.setRequestHeader(header, token);
                }
            });
        })
    }
    else {
        button.textContent = "Завершить полив";
        button.addEventListener("click", () => {
            $.ajax({
                url: window.location.origin + `/api/queue/finish/${queue.id}`,
                type: "PUT",
                contentType: "application/json",
                success: function (queue) {
                    const message = "Вы закончили полив";
                    alert(message);
                    window.location.reload();
                },
                error: function (request) {
                    alert(" Error: " + request.responseText);
                },
                beforeSend: function (xhr) {
                    let token = $("meta[name='_csrf']").attr("content");
                    let header = $("meta[name='_csrf_header']").attr("content");
                    xhr.setRequestHeader(header, token);
                }
            });
        })
    }
        return button
}