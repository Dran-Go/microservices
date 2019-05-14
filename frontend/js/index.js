$(document).ready(function(){
    var sessionId = sessionStorage.getItem("Authorization");
    // var sessionId = "debug";
    if (sessionId === null) {
        alert("请先登录");
        window.location.href="login.html";
    } else {
        $(":hidden").removeAttr("hidden");
        var data;
        $.ajax({
            type: "GET",
            url: "http://139.159.132.32:8800/api/users",
            dataType: "json",
            headers: {
                "Authorization": sessionStorage.getItem("Authorization")
            },
            async: false,
            success: function (res) {
                if (res.code == 200) {
                    // alert(res.listData);
                    data = res.listData;
                } else {
                    alert(res.message);
                }
            },
            error: function () {
                alert("网络繁忙，请刷新页面");
            }
        });

        if (data === undefined) {
            return;
        }

        let table_data = '';
        for(var i=0;i<data.length;i++){ //循环json对象，拼接tr,td的html
            table_data = table_data +'<tr>';
            table_data = table_data + '<td>' + data[i].userId + '</td>';
            table_data = table_data + '<td>' + data[i].username + '</td>';
            table_data = table_data +'</tr>';
        }
        $('#table-body').html(table_data); //通过jquery方式获取table，并把tr,td的html输出到table中

        $("tbody tr").click(function () { //获取选中行数据
            var username = $(this).children('td').eq(1).text();
            window.location.href = "mail.html?to=" + username;
        });

        $('#bootstrap-table').bdt({ //表格样式
            pageRowCount: 5,
            showEntriesPerPageField: 0,
            searchFieldText: "搜索",
        });
    }
});


