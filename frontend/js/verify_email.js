$(document).ready(function(){
    var code = getQueryVariable("code");
    $.ajax({
        type: "GET",
        url: "http://139.159.132.32:8800/api/user/email/verify?code=" + code,
        dataType: "json",
        async: false,
        success: function (result) {
            if(result.code === 200 && result.message === "ok"){
                alert("邮箱账号验证成功！");
                window.location.href="login.html"
            }else {
                alert(result.message);
            }
        },
        error: function () {
            alert("网络繁忙，请稍后重试");
        }
    });

    window.location.href="login.html"
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