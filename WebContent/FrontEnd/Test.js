function loadDoc() {
    var dataList = document.getElementById('json-datalist');
    
    var xhttp = new XMLHttpRequest();
    var val = document.getElementById("autocomplete").value;
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var jsonOptions = JSON.parse(xhttp.responseText);
            while (dataList.hasChildNodes()) {
                dataList.removeChild(dataList.lastChild);
            }
            jsonOptions.forEach(function(item) {
                // create a new option element within the drop down menu
                // as we loop through the jsonOptions object, set option element to the item
                var option = document.createElement('option');
                option.value = item;
                dataList.appendChild(option);

                //document.getElementById("demo").innerHTML = item;
                // add option to the datalist

            });
        }
    };

    // remove all nodes before adding new ones
    // to prevent the datalist from adding many redundant nodes
    xhttp.open("GET", "/Fabflix/AutoCompleteSearch?query=" + val, true);
    xhttp.send();
}
  
 