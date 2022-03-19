function addDragon() {
    const addDragonForm = document.forms.namedItem("addDragonForm");
    let formData = new FormData(addDragonForm);
    let request = new XMLHttpRequest();
    const newDragon = '<dragon>' +
        '         <id>0</id>' +
        '         <name>' + formData.get('name') + '</name>' +
        '         <coordinates>' +
        '            <x>' + formData.get('x') + '</x>' +
        '            <y>' + formData.get('y') + '</y>' +
        '         </coordinates>' +
        '         <age>' + formData.get('age') + '</age>' +
        '         <type>' + formData.get('type') + '</type>' +
        '         <color>' + formData.get('color') + '</color>' +
        '         <description>' + formData.get('description') + '</description>' +
        '         <cave>' +
        '            <depth>' + formData.get('depth') + '</depth>' +
        '         </cave>' +
        '      </dragon>'
    request.open("POST", "/Lab1-1.0-SNAPSHOT/dragons");
    request.send(newDragon);
    request.onreadystatechange = function() {
        window.open('/Lab1-1.0-SNAPSHOT/jsp/main-page.jsp');
    };
}


function updateDragon() {
    const updateDragonForm = document.forms.namedItem("updateDragonForm");
    let formData = new FormData(updateDragonForm);
    let request = new XMLHttpRequest();
    const newDragon = '<dragon>' +
        '         <id>' + formData.get('id') + '</id>' +
        '         <name>' + formData.get('name') + '</name>' +
        '         <coordinates>' +
        '            <x>' + formData.get('x') + '</x>' +
        '            <y>' + formData.get('y') + '</y>' +
        '         </coordinates>' +
        '         <age>' + formData.get('age') + '</age>' +
        '         <type>' + formData.get('type') + '</type>' +
        '         <color>' + formData.get('color') + '</color>' +
        '         <description>' + formData.get('description') + '</description>' +
        '         <cave>' +
        '            <depth>' + formData.get('depth') + '</depth>' +
        '         </cave>' +
        '      </dragon>'
    request.open("PUT", "/Lab1-1.0-SNAPSHOT/dragons");
    request.send(newDragon);
    request.onreadystatechange = function() {
        window.location = '/Lab1-1.0-SNAPSHOT/dragons';
    };
}

function getErrorMsg(request) {
    let errorMsg = "";
    if (request.status === 200) {
        window.location = '/Lab1-1.0-SNAPSHOT/dragons';
    } else {
        console.log(request.response);
        let rawData = $.parseXML(request.response).getElementsByTagName("errors")[0].getElementsByTagName("errors")[0];
        let k, i, j, oneRecord, oneObject, innerObject;
        for (i = 0; i < rawData.children.length; i++) {
            errorMsg += rawData.children[i].children[2].textContent + "<br>"
        }
    }

    $('.error-msg__text').remove();
    $('.error-msg').append("<p class='error-msg__text alert alert-danger'>" + errorMsg + "</p>");
}