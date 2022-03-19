const fields = ["Name", "X", "Y", "Date", "Age", "Color", "Type", "Depth"];

fields.forEach((element) => {
    $('.is' + element + 'Disabled').change(function () {
        if ($('.is' + element + 'Disabled').is(':checked')) {
            $('.' + element).removeAttr("disabled");
        } else {
            $('.' + element).attr("disabled", "true");
        }
    });
})

function parseDate(date, time) {
    let year = date.children[0].textContent;
    let month = date.children[1].textContent;
    let day = date.children[2].textContent;
    let hours = time.children[0].textContent;
    let min = time.children[1].textContent;
    // yyyy-MM-dd HH:mm
    return year + "-" + month + "-" + day + " " + hours + ":" + min;
}

function filterListener(form, url, ev) {
    let formData = new FormData(form);
    let request = new XMLHttpRequest();
    request.responseType = "text";
    let getStr = "?selectedPage=" + document.getElementById("selectedPage").value + "&numberOfRecordsPerPage="+  document.getElementById("numberOfRecordsPerPage").value+ "&";
    for (let pair of formData.entries()) {
        getStr += pair[0] + '=' + pair[1] + '&';
    }
    getStr = url + getStr.substring(0, getStr.length - 1);
    request.open("GET", getStr);

    request.onload = function (oEvent) {
        createDragonTable(request);
    };
    request.send(formData);
    ev.preventDefault();
}

const filterForm = document.forms.namedItem("filterForm");
filterForm.addEventListener('submit', function (ev) {
    filterListener(filterForm, '/Lab1-1.0-SNAPSHOT/dragons/filter', ev);
}, false);

const aggregateFunctions = document.forms.namedItem("aggregateFunctions");
aggregateFunctions.addEventListener('submit',
    function (ev) {
        let url = "/Lab1-1.0-SNAPSHOT/age/" + document.getElementById("function").value;
        console.log(url);
        let request = new XMLHttpRequest();
        request.open("GET", url);
        request.responseType = 'text';
        request.onload = function () {
            if (request.status === 200) {
                document.getElementById("result").textContent = request.responseText;
            }
        }
    }, false);

const getDragonsWithLesserColor = document.forms.namedItem("findDragonsWithLesserColor");
getDragonsWithLesserColor.addEventListener('submit',
    function (ev) {
        let formData = new FormData(getDragonsWithLesserColor);
        const radioButtons = document.querySelectorAll('input[name="color"]');

        let selectedColor;
        for (const radioButton of radioButtons) {
            if (radioButton.checked) {
                selectedColor = radioButton.value;
                break;
            }
        }

        let getStr = "?selectedPage=" + document.getElementById("selectedPage").value + "&numberOfRecordsPerPage=" +
            document.getElementById("numberOfRecordsPerPage").value+ "&color=" + selectedColor;
        let url = "/Lab1-1.0-SNAPSHOT/color" + getStr;

        let request = new XMLHttpRequest();
        request.responseType = 'text';
        request.open("GET", url);

        request.onload = function (oEvent) {
            if (request.status === 200) {
                createDragonTable(request);
            } else {
                console.log("Error : " + request.status);
            }
        };
        request.send(formData);
        ev.preventDefault();
    }, false);

const sortForm = document.forms.namedItem("sortForm");
sortForm.addEventListener('submit',
    function (ev) {
        let url = "/Lab1-1.0-SNAPSHOT/dragons/sort?selectedPage=" + document.getElementById("selectedPage").value + "&numberOfRecordsPerPage="
            +  document.getElementById("numberOfRecordsPerPage").value+ "&";
        $('input[type=radio]').filter(':checked').each(function () {
            const inputField = $(this);
            url += inputField.attr('name') + "=" + inputField.val() + "&";
        });
        url = url.substring(0, url.length - 1);
        let request = new XMLHttpRequest();
        request.responseType = "text";
        request.open("GET", url);

        request.onload = function (oEvent) {
            createDragonTable(request);
        };
        request.send();
        ev.preventDefault();
    }, false);

function changePagesQuantity(dragonsQuantity) {
    const numberOfRecordsPerPage = document.getElementById("numberOfRecordsPerPage").value;
    const pagesQuality = Math.ceil(dragonsQuantity / numberOfRecordsPerPage);
    $('#selectedPage').remove();
    let html = "<select id='selectedPage' name='selectedPage'>";
    for (let i = 1; i < pagesQuality+1; i++) {
        html+='<option value='+ i + '>'+ i + '</option>'
    }
    html+= "</select>"
    $('.selectedPage').append(html);
}

function deleteDragon(id) {
    let request = new XMLHttpRequest();
    request.open("DELETE", "/Lab1-1.0-SNAPSHOT/dragons?id=" + id);
    request.onload = function (oEvent) {
        window.location = '/Lab1-1.0-SNAPSHOT/dragons';
    };
    request.send();
}

function createDragonTable(request) {
    if (request.status === 200) {
        let parsedResponse = $.parseXML(request.responseText);
        let filteredDragons = [];
        let rawData = parsedResponse.getElementsByTagName("dragons")[0].getElementsByTagName("dragons")[0];
        let k, i, j, oneRecord, oneObject;
        for (i = 0; i < rawData.children.length; i++) {
            oneRecord = rawData.children[i];
            oneObject = filteredDragons[filteredDragons.length] = {};
            for (j = 0; j < oneRecord.children.length; j++) {
                if (oneRecord.children[j].tagName.includes('creationDate')) {
                    let dateTime = oneRecord.children[j].children[0];
                    let date = dateTime.children[0];
                    let time = dateTime.children[1];
                    oneObject[oneRecord.children[j].tagName] = parseDate(date, time);
                } else if (oneRecord.children[j].tagName.includes('coordinates')) {
                    let x = oneRecord.children[j].children[1].textContent;
                    let y = oneRecord.children[j].children[2].textContent;
                    oneObject[oneRecord.children[j].children[1].tagName] = x;
                    oneObject[oneRecord.children[j].children[2].tagName] = y;
                } else if (oneRecord.children[j].tagName.includes('cave')) {
                    oneObject[oneRecord.children[j].children[1].tagName] = oneRecord.children[j].children[1].textContent;
                }
                else {
                    oneObject[oneRecord.children[j].tagName] = oneRecord.children[j].textContent;
                }
            }
        }
        $('.table-rows').remove();
        let html = "";
        for (i = 0; i < filteredDragons.length; i++) {
            html += "<tr class='table-rows'><td>" + filteredDragons[i].id + "</td><td>" + filteredDragons[i].name + "</td>"
                + "<td>" + filteredDragons[i].x + "</td><td>" + filteredDragons[i].y + "</td>"
                + "<td>" + filteredDragons[i].creationDate + "</td>"
                + "<td>" + filteredDragons[i].age + "</td>"
                + "<td>" + filteredDragons[i].dragonType  + "</td><td>" + filteredDragons[i].dragonColor + "</td>"
                + "<td>" + filteredDragons[i].description + "</td>"
                + "<td>" + filteredDragons[i].depth + "</td>"
                + "<td><a href=pages/edit-form?id=" + filteredDragons[i].id + ">Edit </a>"
                + "<button class='btn btn-primary mx-auto mt-2' onclick='deleteDragon(${dragon.id});'>Delete</button></td></tr>";
        }
        $('table').append(html);
        console.log(filteredDragons);
    } else {
        console.log("Error " + request.status);
    }
}