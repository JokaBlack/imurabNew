const select = document.querySelector('#field-select');
const form = document.querySelector('#queue-form');
const departmentIdInput = document.querySelector('#departmentId-input');
const FieldId = document.querySelector('#fieldId');
const startDateInput = document.querySelector('#startDate-input');
const startTimeInput = document.querySelector('#startTime-input');
const okInput = document.querySelector('#ok-input');
const hourInput = document.querySelector('#hour-input');

function getFields() {
    let url = new URL(window.location.origin + `/api/fields?page=0&size=10&sort=id,asc`)
    $.get(url, function (data) {
        renderFields(data)
    })
}

function renderFields(data) {
    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.text = 'Выберите поле';
    defaultOption.disabled = true;
    defaultOption.selected = true;
    select.appendChild(defaultOption);

    $.each(data.content, function (index, field) {
        const option = document.createElement('option');
        option.value = field.id;
        option.text = field.name;
        select.appendChild(option);
    })
}

select.addEventListener('change', event => {
    const fieldId = event.target.value;
    fetch(window.location.origin + `/api/queue/vacantForField/${fieldId}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    throw new Error(`${errorMessage}`);
                });
            }
            return response.json();
        })
        .then(data => {
            departmentIdInput.value = data.vacant.departmentId;
            startDateInput.value = data.vacant.startDate;
            startTimeInput.value = data.vacant.startTime;
            hourInput.value = data.vacant.hour;
            okInput.value = data.vacant.ok;
            FieldId.value = data.field.id;
        })
        .catch(error => {
            cleanQueueModal();
            alert(`${error.message}`);
        });
});

function cleanQueueModal() {
    departmentIdInput.value = '';
    startDateInput.value = '';
    startTimeInput.value = '';
    hourInput.value = '';
    okInput.value = '';
    FieldId.value = '';
}

var modal = document.getElementById("createQueueModal");
var btn = document.getElementById("createQueue");
var span = document.getElementsByClassName("close")[0];

btn.onclick = function () {
    select.innerHTML = "";
    modal.style.display = "block";
    getFields();
}

span.onclick = function () {
    cleanQueueModal()
    modal.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

form.addEventListener('submit', function (event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    var object = {};
    formData.forEach(function (value, key) {
        object[key] = value;
    });
    var json = JSON.stringify(object);
    createQueue(FieldId.value, json)
});

function createQueue(fieldId, json) {
    $.ajax({
        url: window.location.origin + `/api/queue/create/${fieldId}`,
        type: "POST",
        data: json,
        contentType: "application/json",
        success: function (response) {
            const department = response.field.department.name;
            const field = response.field;
            const fieldName = field.name;
            const cropName = field.fieldCropsDto.name;
            const farmerName = field.farmer.firstName + " " + field.farmer.lastName;
            const startTime = response.startWatering;
            const endTime = response.endWatering;

            const message = `Ваша заявка на полив принята!\nУчасток: ${department}\nФермер: ${farmerName}\nПоле:   ${fieldName}\nКультура: ${cropName}\nВремя начала полива: ${startTime}\nВремя конца полива: ${endTime}`;
            alert(message);
            cleanQueueModal()
            modal.style.display = "none";
        },
        error: function (request) {
            alert(" Error: " + request.responseText);
            cleanQueueModal()
        },
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        }
    });
}