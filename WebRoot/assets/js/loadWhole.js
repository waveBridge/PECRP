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
                               if(i > 6) return false;
                               $("#historyVideo").append("<li><a href=\""+video.link+"\">"+video.videoName+"</a></li>");
                           } )
                       }
                   }
                });

            }
        }
    })
});