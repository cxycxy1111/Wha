function httpRequest(method,url,param,successArr,successDic,error) {
    $.ajax({
        type: method,
        contentType: 'application/json;charset=UTF-8',
        url: url + "?" + param,
        success: function (result) {
            var data = JSON.parse(result);
            if (result.startsWith("{", 0)) {
                successDic(data);
            } else if (result.startsWith("[", 0)) {
                successArr(data);
            } else {
                error(result);
            }

        },
        error: function (e) {
            console.log(e.status);
            console.log(e.responseText);
        }
    })
}
