$(document).ready(function () {
    $.ajax({
        url: "getHotVideoAction",
        type: "POST",
        dataType: "json",
        async: true,
        success: function (data) {
            var json = eval(data);
            data = "";
            console.log(json.msg);
            if (json.msg == "0") {
                alert("error happend in getHot!");
            }
            else {
                console.log(json);
                videoSet = json.msg;
                cnt = json.cnt;
                $.each(videoSet, function (i, video) {
                    if (i == 0) {
                        $('#mostPop').append(' <div class="zoom-container">\n' +
                            '                            <div class="zoom-caption">\n' +
                            '                                <a href="single.html?vid=' + video.vid + '" id="toMostPop">\n' +
                            '                                    <i class="fa fa-play-circle-o fa-5x" style="color: #fff"></i>\n' +
                            '                                </a>\n' +
                            '                                <p id="mostPopName">' + video.videoName + '</p>\n' +
                            '                            </div>\n' +
                            '                            <img src="' + video.picture + '" id="mostPopImg"/>\n' +
                            '                        </div>')
                    }
                    else if (i <= 2) {
                        $('#otherPop1').append('<div class="zoom-container">\n' +
                            '                            <div class="zoom-caption">\n' +
                            '                                <a href="single.html?vid=' + video.vid + '">\n' +
                            '                                    <i class="fa fa-play-circle-o fa-5x" style="color: #fff"></i>\n' +
                            '                                </a>\n' +
                            '                                <p>' + video.videoName + '</p>\n' +
                            '                            </div>\n' +
                            '                            <img src="' + video.picture + '"/>\n' +
                            '                        </div>')

                    }
                    else if (i <= 4) {
                        $('#otherPop2').append('<div class="zoom-container">\n' +
                            '                            <div class="zoom-caption">\n' +
                            '                                <a href="single.html?vid=' + video.vid + '">\n' +
                            '                                    <i class="fa fa-play-circle-o fa-5x" style="color: #fff"></i>\n' +
                            '                                </a>\n' +
                            '                                <p>' + video.videoName + '</p>\n' +
                            '                            </div>\n' +
                            '                            <img src="' + video.picture + '"/>\n' +
                            '                        </div>')
                    }
                })
            }
        }
    });
    var className = ['首页', '篮球', '足球', '排球', '网球', '乒乓球', '羽毛球', '跑步', '瑜伽', '健身', '剑道', '其他'];
    $.each(className, function (i, classified) {
        if (i > 0 && i < 5) {
            // alert(classified);
            $.ajax({
                url: "getClassifyVideoAction",
                type: "POST",
                dataType: "json",
                data: {
                    classifyName: classified
                },
                async: true,
                success: function (data) {
                    var json = eval(data);
                    data = "";
                    console.log(json.msg);
                    if (json.msg == "0") {
                        alert("error happend in getClassified!");
                    }
                    else {
                        console.log(classified,json);
                        recVideo = json.recommendVideo;
                        hotVideo = json.hotVideo;
                        recLabel = json.recommendLabel;

                        $.each(recVideo, function (x, video) {
                            id = 'c' + i;
                            if (x < 6) {
                                id = id + 'v' +parseInt((x + 2) / 2);
                                console.log(id)
                                $('#'+id).append('<div class="wrap-vid">\n' +
                                    '                                    <div class="zoom-container">\n' +
                                    '                                        <div class="zoom-caption">\n' +
                                    '                                            <a href="single.html?vid='+video.vid+'">\n' +
                                    '                                                <i class="fa fa-play-circle-o fa-5x" style="color: #fff"></i>\n' +
                                    '                                            </a>\n' +

                                    '                                        </div>\n' +
                                    '                                        <img src="'+video.picture+'"/>\n' +
                                    '                                    </div>\n' +
                                    '                                    <p class="vid-name"><a href="#">'+video.videoName+'</a></p>\n' +
                                    '                                    <div class="info">\n' +
                                    '                                        <span><i class="fa fa-heart"></i>'+video.zanUserSet.length+'</span>\n' +
                                    '                                    </div>\n' +
                                    '                                </div>');
                            }

                        });

                        $.each(recLabel, function (x, label) {
							if(x <= 10){
								
								id = 'c' + i + 'l';
								$('#' + id).append('<li><a href="#">'+label.labelName+' ,</a></li>');
							}
                        });

                        $.each(hotVideo, function (x, video) {
                            id = 'c' + i + 'r';
                            if(x < 3){
                                $('#' + id).append('<div class="wrap-vid">\n' +
                                    '                            <div class="vid-name">\n' +
                                    '                                <div class="vid-rank">\n' +
                                    '                                    <div class="vid-rank-number">'+(x+1)+'</div>\n' +
                                    '                                </div>\n' +
                                    '\n' +
                                    '                                <a href="single.html?vid='+video.vid+'">'+video.videoName+'</a>\n' +
                                    '\n' +
                                    '                                <i class="fa fa-heart" style="margin-left: 6px"></i>'+video.zanUserSet.length+'\n' +
                                    '                            </div>\n' +
                                    '                        </div>');
                            }
                            else if(x < 5){
                                $('#' + id).append(' <div class="wrap-vid">\n' +
                                    '\n' +
                                    '                            <div class="vid-name">\n' +
                                    '                                <div class="vid-rank-out">\n' +
                                    '                                    <div class="vid-rank-number">'+(x+1)+'</div>\n' +
                                    '                                </div>\n' +
                                    '\n' +
                                    '                                <a href="single.html?vid='+video.vid+'">'+video.videoName+'</a>\n' +
                                    '\n' +
                                    '                                <i class="fa fa-heart" style="margin-left: 6px"></i>'+video.zanUserSet.length+'\n' +
                                    '                            </div>\n' +
                                    '                        </div>');
                            }

                        })


                    }
                }

            });
        }
    });


});