$(document).ready(function(){
    var code = getQueryVariable("code");
    if (code) {
        $.ajax({
            type: "GET",
            url: "http://139.159.132.32:8800/api/user/email/verify?code=" + code,
            dataType: "json",
            async: false,
            success: function (result) {
                if(result.code === 200 && result.message === "ok"){
                    alert("邮箱地址验证成功！");
                    window.location.href="login.html"
                }else {
                    alert(result.message);
                }
            },
            error: function () {
                alert("网络繁忙，请稍后重试");
            }
        });
    }
    $(":hidden").removeAttr("hidden");
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

$(function() {
    var root = "http://139.159.132.32:8800";
    $(".register-form").submit(function(event) {
        var form = $(this);
        event.preventDefault();
        if (form[0].checkValidity() === true) {
            $.ajax({
                type: "POST",
                url: root + "/api/user",
                data: form.serialize(),
                dataType: "json",//预期服务器返回的数据类型
                async: false,
                success: function(result) {
                    if(result.code == 200){
                        confirm("请按接收到的邮件进行操作，完成注册")
                        window.location.href="login.html"
                    }else {
                        alert(result.message);
                    }
                },
                error: function () {
                    alert("网络繁忙，请稍后再试");
                }}
            );
        }else {
            form.addClass('was-validated');
        }
    });
});
