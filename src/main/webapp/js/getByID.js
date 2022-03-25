function getById () {
        let id = document.getElementById("id").value;
        document.forms.namedItem("getDragonByID").action = "/Lab1-1.0-SNAPSHOT/dragons/" + id;
}