$(document).ready(function () {
    $.ajax({
        url: "getUserInfoAction",
        type: "POST",
        dataType: "json",
        async: false,
        success: function (data) {
            var json = eval(data);
            data = "";
            console.log(json.msg);
            if(json.msg == "0"){
                $("#userImg").attr("src", "assets/img/user.png");
                $("#loginLi").show();
                $("#registerLi").show();
                $("#infoLi").hide();
                $("#tagLi").hide();
                $("#logoutLi").hide();
                $("#historyVideo").hide();
                $("#collectionVideo").hide();
                $(".unLogin").show();
                //for index & single
                //useless
                $("#toHistory").attr("href", "");
                $("#toCollection").attr("href", "");
            }
            else{
                //has Login
                $("#userImg").attr("src", json.msg.photo);
                $("#loginLi").hide();
                $("#registerLi").hide();
                $("#infoLi").show();
                $("#tagLi").show();
                $("#logoutLi").show();
                $("#historyVideo").show();
                $("#collectionVideo").show();
                $(".unLogin").hide();
                //for index & single
                //useless
                $("#toHistory").attr("href", "history.html");
                $("#toCollection").attr("href", "collection.html");
                $.ajax({
                   url: "getHistoryAction",
                   type: "POST",
                   dataType: "json",
                   async: false,
                   success: function (data) {
                       var json = eval(data);
                       data = "";
                       console.log(json.msg);
                       if(json.msg == "0"){
                           alert("error happend!");
                       }
                       else{
                           $.each(json.msg, function (i, video) {
                               console.log(i, video.videoName, video.link);
                               if(i > 6){
                                   return false;
                               }
                               $("#historyVideo").append("<li><a href=\""+video.link+"\">"+video.videoName+"</a></li>");
                           } );
                           $("#historyVideo").append("<li ><a style='color: #806fff; font-size: 89%;' href=\"history.html\">查看更多</a></li>");
                       }
                   }
                });
                $.ajax({
                    url: "getCollectAction",
                    type: "POST",
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        var json = eval(data);
                        data = "";
                        console.log(json.msg);
                        if(json.msg == "0"){
                            alert("error happend!");
                        }
                        else{
                            $.each(json.msg, function (i, video) {
                                console.log(i, video.videoName, video.link);
                                if(i > 6){
                                    return false;
                                }
                                $("#collectionVideo").append("<li><a href=\""+video.link+"\">"+video.videoName+"</a></li>");
                            } );
                            $("#collectionVideo").append("<li ><a style='color: #806fff; font-size: 89%;' href=\"collection.html\">查看更多</a></li>");
                        }
                    }
                });

            }
        }
    });
    $("#logoutLi").click(function () {
       $.ajax({
           url: "logoutAction",
           dataType: "json",
           async: false,
           success : function (data) {
               var json = eval(data);
               data = "";
               console.log("logout"+json.msg);
               if(json.msg == "0"){
                   alert("注销失败，请重试！");
               }
               else{
                   window.location.href="index.html";
               }
           }
       })
    });
});