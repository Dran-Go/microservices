$(document).ready(function(){
    var sessionId = sessionStorage.getItem("Authorization");
    // var sessionId = "debug";
    if (sessionId === null) {
        alert("请先登录");
        window.location.href = "login.html";
        return;
    }
    $(":hidden").removeAttr("hidden");
    var to_username = decodeURIComponent(getQueryVariable("to"));
    $('#box-head').append(to_username);
});

function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

$('#box-button').click(function () {
    // alert($('#box-head').text());
    // alert($('#box-text-form').val());
    $.ajax({
        type: "POST",
        url: "http://139.159.132.32:8800/api/mail/letter",
        data: {"toUsername":$('#box-head').text(), "text":$('#box-text-form').val()},
        headers: {
            "Authorization": sessionStorage.getItem("Authorization")
        },
        dataType: "json",//预期服务器返回的数据类型
        async: false,
        success: function(result) {
            if(result.code == 200){
                confirm("发送成功");
                window.location.href="index.html"
            }else {
                alert(result.message);
            }
        },
        error: function () {
            alert("网络繁忙，请稍后再试");
        }}
    );
});

