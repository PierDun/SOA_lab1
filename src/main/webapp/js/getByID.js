const aggregateFunctions = document.forms.namedItem("getDragonByID");
aggregateFunctions.addEventListener('submit',
    function (ev) {
        let url = "/Lab1-1.0-SNAPSHOT/dragons/" + document.getElementById("function").value;
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