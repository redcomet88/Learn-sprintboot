var backendService = new BackendService();
var lastedPage = "";

var userInfo = null;
var state = backendService.getState();
var otherCollects = [];
var otherFormname = [];
var labels = ["新", "年", "一", "罐", "好", "运"];

//设置及加载百分比
function setLoadProecss(process) {
    var $loadText = $(".loadPage .progress").eq(0);
    var $loadWater = $(".loadPage .full").eq(0);
    var $wave = $(".loadPage .wave").eq(0);
    $loadText.text(process + "%");
    var clipTop = 144 - Math.round(process * 144 / 100);
    $loadWater.css("clip", 'rect(' + clipTop + 'px 83px 144px 0px)');

    if (process > 75) {
        $wave.css("bottom", Math.round(process * 144 / 100) - 12 - 0.1 * (process - 75));
    } else {
        $wave.css("bottom", Math.round(process * 144 / 100) - 12);
    }

    var scale;
    if (process <= 50) {
        scale = ((1.03 * process + 110.5) / 162);
    } else {
        scale = (1.9 * (100 - process) + 67) / 162;
    }
    // if (process > 70 && $wave.attr("src") == "img/wave.png") {
    //
    //     $wave.attr("src", "img/wave2.png");
    // }
    if (process > 90) {
        $wave.css("display", "none");
    }
    $wave.css("transform", "scale(" + scale + ")");
    if (process <= 50) {
        $wave.css("right", (1 - (162 - 162 * scale) / 5) + "px");
    } else {
        $wave.css("right", "inherit");
        $wave.css("left", (1 - (162 - 162 * scale) / 5) + "px");
    }

}

//加载资源
var loadProcess = 0;

//开始加载
function loadResource() {
    loadProcess = loadProcess + 1;
    if (loadProcess > 100) {
        loadProcess = 100;
    }
    setLoadProecss(loadProcess);
    if (loadProcess < 100) {
        setTimeout(loadResource, 25);
    } else {
        showMainPage();
    }
}

//显示主页面
function showMainPage() {
    $(".loadPage").animate({
        opacity: "0"
    }, "100", "linear", function () {
        $(".loadPage").css({
            display: "none"
        })
    });

}


//切换界面
function switchTab(cur, next) {
    cur.css("transform", "translate3d(-100%, 0px, 0px)");
    cur.css("-webkit-transform", "translate3d(-100%, 0px, 0px)");
    next.css("transform", "translate3d(0, 0px, 0px)");
    next.css("-webkit-transform", "translate3d(0, 0px, 0px)");

    cur.find(".cloud").css("display", "none");
    next.find(".cloud").css("display", "");
}

//切换窗体内容
function switchFrame(cur, next) {
    cur.animate({
        opacity: "0"
    }, "50", "linear", function () {
        cur.css("display", "none");
        next.css("opacity", 1);
        next.css("display", "");
    });
}

//切换窗体内容
function switchFramefast(cur, next) {
    next.css("opacity", 1);
    cur.css("display", "none");
    next.css("display", "");
}

var windowWidth;

//开始好礼
function beginClick() {
    switchTab($(".indexPage"), $(".drawMain"));
}

//改变bottles
function refeshBottles() {

    if (state != "") {
        $(".myBottleDialog .swiper-wrapper .swiper-slide span").eq(0).html(otherFormname);
        $.each(otherCollects, function (idx, val) {
            if (idx >= 6) {
                return false;
            }
            if (val.isCollect == 0) {
                $(".myBottleDialog .swiper-wrapper .swiper-slide span").eq(1 + idx).html("");
            } else {
                allCollects++;
                $(".myBottleDialog .swiper-wrapper .swiper-slide span").eq(1 + idx).html(val.message);
                $(".myBottleDialog .swiper-wrapper .swiper-slide .author").eq(1 + idx).html(val.helpNickName);
            }
        });
    } else {
        $(".myBottleDialog .swiper-wrapper .swiper-slide span").eq(0).html(userInfo.formname);
        var allCollects = 0;
        $.each(userInfo.collects, function (idx, val) {
            if (idx >= 6) {
                return false;
            }
            if (val.isCollect == 0) {
                $(".myBottleDialog .swiper-wrapper .swiper-slide span").eq(1 + idx).html("");
            } else {
                allCollects++;
                $(".myBottleDialog .swiper-wrapper .swiper-slide span").eq(1 + idx).html(val.message);
                $(".myBottleDialog .swiper-wrapper .swiper-slide .author").eq(1 + idx).html(val.helpNickName);
            }
        });
    }
    if (state == null || state == "" || state == "1") {
        if (userInfo.isCollected == 1) {
            $(".myBottleDialog .drawBtn").css("display", "");
        } else {
            $(".myBottleDialog .sharBtn").css("display", "");
        }
    } else {
        $(".myBottleDialog .helpOther").css("display", "");
        $(".myBottleDialog .thumbsupBtn").css("display", "");
    }


}

function addListener() {
    //播放视频
    $(".music-logo").off('click').on('click', function () {
        if ($(".music-logo").hasClass("playing")) {
            $("#bgMusic")[0].pause();
        } else {
            $("#bgMusic")[0].play();
        }
        $(".music-logo").toggleClass("playing");
    });

    //点击开启好礼
    $(".screen .indexPage .beginBtn").off('click').on('click', beginClick);

    //关闭活动介绍
    $(".screen .drawMain .introduceDialog .closeBtn").off('click').on('click', function () {
        $(".drawMain .introduceDialog").animate({
            opacity: "0"
        }, "100", "linear", function () {
            $(".drawMain .introduceDialog").css("display", "none");
        });
    });
    //打开活动介绍
    $(".screen .drawMain .introduceBtn").off('click').on('click', function () {
        $(".drawMain .introduceDialog").css("opacity", 1);
        $(".drawMain .introduceDialog").css("display", "");
    });

    //填写名字-确认按钮
    $(".drawMain .nameDialog .btnDiv img").off('click').on('click', function () {
        // if ($("#nameForm .formError").length > 0) {
        //     return;
        // }

        var valid = $("#nameForm").validationEngine('validate');
        if (!valid) {
            return;
        }

        var val = $(".nameDialog  input[type='text']").val().trim();
        backendService.updateformname(val, function (data) {
            if (data.result == 1) {
                userInfo.formname = val;
                refeshBottles();
                switchFrame($(".screen .drawMain .nameDialog"), $(".screen .drawMain .myBottleDialog"));
            } else {

                $(".nameDialog .errorMsg").text(data.msg);
            }
        });

    });

    //抽奖按钮-进入抽奖界面
    $(".drawMain .myBottleDialog .drawBtn span").off('click').on('click', function () {
        $(".introduceBtn").css("display", "none");
        // $(".drawMain").css("background-image", 'url("../static/img/draw-bg.jpg")');
        switchTab($(".drawMain"), $(".prizeLayer"));
        $(".prizeLayer .drawPrizeDialog").css("display", "");

    });


    //送出祝福按钮
    $(".drawMain .myBottleDialog .helpBtn span").off('click').on('click', function () {

        var valid = $("#helpOtherForm").validationEngine('validate');
        console.log(valid);
        if (!valid) {
            return;
        }

        var val = $(".drawMain .myBottleDialog .helpOther input").val();
        val = val.replace(/\s+/g, "");

        if (val.length > 0) {
            var begin = val.substring(0, 1);
            if (labels.indexOf(begin) >= 0) {
                var idx = labels.indexOf(begin);
                if (otherCollects[idx].isCollect == 0) {
                    backendService.thumbup(idx, state, val, function (data) {
                        if (data.result != null && data.result != undefined && data.result == 1) {
                            otherCollects[idx].isCollect = 1;
                            otherCollects[idx].helpNickName = userInfo.nickname;
                            otherCollects[idx].message = val;
                            refeshBottles();
                            $(".myBottleDialog .errorMsg").html("已成功为好友送上祝福");
                        } else {
                            $(".myBottleDialog .errorMsg").html(data.msg);
                        }
                    });
                } else {
                    //已经用验证框架进行验证
                }
            }
        }
    });

    //抽奖按钮-进行抽奖
    $(" .drawPrizeDialog .bottle .cover").off('click').on('click', function () {
        $(" .drawPrizeDialog .bottle .cover").off('click');
        var $item = $(this).parent();
        var top = $item.position().top + 10;
        var left = $item.position().left + 10;
        console.log(left);
        $item.css("top", top);
        $item.css("left", left);
        $item.css("position", "absolute");
        $item.css("margin", "0");
        $(".drawPrizeDialog .bottle .cover").not($(this)).parent().css("display", "none");

        var prizeType = 0;
        backendService.drawPrize(function (data) {
            if (data != null && data != undefined) {
                console.log(document.body.clientWidth);
                prizeType = data.prizeType;
                if (prizeType == undefined) {
                    prizeType = 0;
                }
                $item.animate({
                    left: (document.body.clientWidth - 86) / 2 + "px", top: "83px"
                }, "100", "linear", function () {
                    if (prizeType == 3) {
                        $item.find("> span").html(userInfo.formname);
                        //$(" .prizeResult1Dialog .bottle span").html(userInfo.formname);
                    } else if (prizeType == 2) {
                        $item.find("> span").html("好运罐");
                        // $(" .prizeResult1Dialog .bottle span").html("好运罐");
                    } else {
                        $item.find("> span").html("优惠卷");
                        //  $(".prizeResult1Dialog .bottle span").html("优惠卷");
                    }
                    $item.find(".cover").animate({
                        height: 0
                    }, "100", "linear", function () {
                        $item.find(".cover").css("display", "none");
                        refreshPrizeDialog(prizeType);

                        if (prizeType < 2) {
                            setTimeout(function () {
                                console.log("do");
                                switchFramefast($(".prizeLayer .drawPrizeDialog"), $(".prizeLayer .prizeResult1Dialog"))
                            }, 200);
                        } else {
                            switchFramefast($(".prizeLayer .drawPrizeDialog"), $(".prizeLayer .prizeResult1Dialog"));
                        }

                    });
                });
            }
        });

    });

    //进行填写按钮
    $(".prizeLayer .prizeResult1Dialog .btn span").off('click').on('click', function () {
        //将信息填充到表单
        $(".addressDialog input").eq(0).val(userInfo.contacts);
        $(".addressDialog input").eq(1).val(userInfo.phone);
        $(".addressDialog input").eq(2).val(userInfo.address);
        $(".addressDialog input").eq(3).val(userInfo.email);


        history.pushState({page: 2}, "一罐好运", "?page=address");
        lastedPage = "address";

        switchFrame($(".prizeLayer .prizeResult1Dialog"), $(".prizeLayer .addressDialog"));
    });


    //填写信息-确认按钮
    $(".prizeLayer .addressDialog .btnDiv img").off('click').on('click', function () {

        var valid = $("#addressform").validationEngine('validate');
        if (!valid) {
            return;
        }

        var name = $(".prizeLayer .addressDialog input").eq(0).val();
        var phone = $(".prizeLayer .addressDialog input").eq(1).val();
        var address = $(".prizeLayer .addressDialog input").eq(2).val();
        var message = $(".prizeLayer .addressDialog input").eq(3).val();


        backendService.updateContact(name, phone, address, message, function (data) {
            if (data != null && data != undefined && data.result == 1) {
                $("#addressform .errorMsg").html("已经成功更新您的联系地址");
                userInfo.contacts = name;
                userInfo.phone = phone;
                userInfo.address = address;
                userInfo.email = message;
            } else {
                $("#addressform .errorMsg").html(data.msg);
            }
        });
    });

    //进入分享到朋友圈界面
    $(".drawMain .myBottleDialog .sharBtn span").off('click').on('click', function () {
        $(".shareLayer .bottle span").html(userInfo.formname);
        backendService.getQRCode(function (data) {
            if (typeof (data) == "string" && data.indexOf("data:image") >= 0) {
                $(".shareLayer .QRcode img").attr("src", data);
            }
        });

        history.pushState({page: 1}, "一罐好运", "?page=share");
        lastedPage = "shared";
        switchTab($(".drawMain"), $(".shareLayer"));

    });


    //我也要一罐好运按钮
    $(".drawMain .myBottleDialog .thumbsupBtn span").off('click').on('click', function () {
        window.location.href = "index";
    });

    //点击分享到朋友圈按钮
    $(".shareLayer .btnDiv img").off('click').on('click', function () {
        $(".shareLayer .btnDiv").css("display", "none");
        $(".shareLayer .QRcode").css("display", "");
    });

    //绑定事件
    window.onpopstate = function (event) {
        if (lastedPage == "shared") {
            lastedPage = "";
            $(".shareLayer").css("transform", "translate3d(100%, 0px, 0px)");
            $(".shareLayer").css("-webkit-transform", "translate3d(100%, 0px, 0px)");
            $(".drawMain").css("transform", "translate3d(0, 0px, 0px)");
            $(".drawMain").css("-webkit-transform", "translate3d(0, 0px, 0px)");
            $(".shareLayer").find(".cloud").css("display", "none");
            $(".drawMain").find(".cloud").css("display", "");

        }
        if (lastedPage == "address") {
            switchFrame($(".prizeLayer .addressDialog"), $(".prizeLayer .prizeResult1Dialog"));
        }

    };


    $("#nameForm").validationEngine({
        "promptPosition": "bottomLeft",
        "binded": false,
        "fadeDuration": false,
        "isOverflown": true
    });

    $("#addressform").validationEngine({
        "promptPosition": "bottomLeft",
        "binded": false,
        "fadeDuration": false,
        "isOverflown": true
    });

    //表单验证
    // $(".nameDialog input[type='text']").off().on("blur", function () {
    //     console.log(this);
    //     $(".nameDialog input[type='text']")[0].setCustomValidity("用户名为空.");
    // });
}

//刷新中奖界面
function refreshPrizeDialog(type) {
    if (type == undefined) {
        type = 0;
    }

    if (type == 3) {

        $(" .prizeResult1Dialog .bottle span").html(userInfo.formname);
    } else if (type == 2) {

        $(" .prizeResult1Dialog .bottle span").html("好运罐");
    } else {

        $(".prizeResult1Dialog .bottle span").html("优惠卷");
    }


    if (type >= 2) {
        $(".prizeResult1Dialog .firework").css("display", "");
        $(".prizeResult1Dialog .ticketlogo").css("display", "none");
        $(".prizeResult1Dialog .bottles").css("display", "");

        $(".prizeResult1Dialog .tip").css("display", "");
        $(".prizeResult1Dialog .btn").css("display", "");

    } else {
        $(".prizeResult1Dialog .firework").css("display", "none");
        $(".prizeResult1Dialog .ticketlogo").css("display", "");
        $(".prizeResult1Dialog .bottles").css("display", "none");

        $(".prizeResult1Dialog .tip").css("display", "none");
        $(".prizeResult1Dialog .btn").css("display", "none");
    }

    if (type == 2) {
        $(".prizeResult1Dialog .resultInfo").html("健力宝送您一罐好运<br/>恭喜您获得一罐好运2瓶！");
    }
    if (type <= 1) {
        $(".prizeResult1Dialog .resultInfo").html("健力宝送您一罐好运<br/>不要伤心<br/><span onclick='getTicket()' class='ticket'>小宝送您年货卷<br/>祝您新年好运</span>");
    }

    //增加留言
    $(".prizeResult1Dialog .msgbox").html("");
    $.each(userInfo.collects, function (idx, val) {
        if (idx >= 6) {
            return false;
        }
        if (val.isCollect == 0) {

        } else {
            $(".prizeResult1Dialog .msgbox").append('<div class="row"><div class="col1"><div class="rect"></div></div><div class="col2">' + val.helpNickName + '祝您' + val.message + '</div></div>');
        }
    });

}

//获得卡劵
function getTicket() {
    $(".prizeResult1Dialog .ticketInfo").css("display", "");
    $(".prizeResult1Dialog .ticket").css("display", "none");
    $(".prizeResult1Dialog .ticketInfo textarea").val("【健力宝开启新年一罐好运，小宝送您年货卷】￥DBSPbH3sGS9￥ http://tb.cn/utejLGw 点击链接，选择浏览器咑閞；或復·制这段描述后到👉淘♂寳♀👈");
}


//送祝福验证方法
function thumnupvail(field, rules, i, options) {
    var canChars = "";
    $.each(otherCollects, function (idx, val) {
        if (idx >= 6) {

        } else {
            if (val.isCollect == 0) {
                canChars += labels[idx];
            } else {

            }
        }
    });

    var value = $(field).val();
    value = value.replace(/\s+/g, "");
    console.log(value.length);
    if (value.length != 4) {
        if (canChars.length > 0) {
            return '请输入以"' + canChars + '"为首的4字祝福语';
        } else {
            return '请输入4字祝福语';
        }

    }
    console.log("dodo");
    var begin = value.substring(0, 1);
    if (canChars.indexOf(begin) < 0) {

        if (labels.indexOf(begin) < 0) {
            return '请输入以"' + canChars + '"为首的4字祝福语';
        } else {
            return '好友已经收到以"' + begin + '"为首的4字祝福语';
        }
    }
    return true;
}

$(function () {


    $(".drawMain .introduceDialog").css("display", "none");
    loadResource();


    addListener();

    var swiper = new Swiper('.swiper-container', {
        effect: 'coverflow',
        grabCursor: true,
        centeredSlides: true,
        spaceBetween: -32,
        slidesPerView: 2,
        coverflowEffect: {
            rotate: 0,
            stretch: 0,
            depth: 330,
            modifier: 1,
            slideShadows: false
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    });


    $(".prizeResult1Dialog .bottle").css("left", (document.body.clientWidth - 86) / 2 + "px");

    //奖品界面调整样式
    var clentHieght = document.body.clientHeight;
    var justMarginBottom = Math.round(158.0 * clentHieght / 220 - 359.9636);
    if (justMarginBottom < 0) {
        justMarginBottom = 0;
    }
    $(".prizeResult1Dialog .msgs").css("margin-bottom", justMarginBottom + "px");


    $(".drawMain .nameDialog").css("display", "none");
    $(".drawMain .myBottleDialog").css("display", "none");
    $(".drawPrizeDialog").css("display", "none");
    $(".prizeResult1Dialog").css("display", "none");
    $(".addressDialog").css("display", "none");

    backendService.getUserInfo(function (data) {
        if (data.collects == undefined) {
            console.log(data);
            backendService.login();
        } else {
            userInfo = data;
            //如果用户表单的姓名没有填写，那么跳转到欢迎界面
            if (state !== "") {
                $(".drawMain").css("transform", "translate3d(0, 0px, 0px)").css("-webkit-transform", "translate3d(0, 0px, 0px)");
                $(".drawMain .cloud").css("display", "");
                backendService.getOtherInfo(state, function (data) {
                    if (data.collects != undefined) {
                        console.log("do");
                        otherFormname = data.msg;
                        otherCollects = data.collects;
                        //设置表单验证规则
                        $("#helpOtherForm").validationEngine({
                            "promptPosition": "topLeft",
                            "binded": false,
                            "fadeDuration": false,
                            "isOverflown": true
                        });
                    }
                    refeshBottles();
                });
                $(".drawMain .myBottleDialog").css("display", "");
            } else {
                if (userInfo.formname == null || userInfo.formname == "") {
                    $(".indexPage").css("transform", "translate3d(0, 0px, 0px)").css("-webkit-transform", "translate3d(0, 0px, 0px)");
                    $(".indexPage .cloud").css("display", "");


                    $(".nameDialog  input[type='text']").val(userInfo.nickname);
                    $(".drawMain .nameDialog").css("display", "");
                } else {
                    //todo

                    //没有抽奖，进入我的瓶子界面
                    if (userInfo.isDraw == 0) {
                        $(".drawMain").css("transform", "translate3d(0, 0px, 0px)").css("-webkit-transform", "translate3d(0, 0px, 0px)");
                        refeshBottles();
                        $(".drawMain .myBottleDialog").css("display", "");
                        $(".drawMain .cloud").css("display", "");
                    } else {
                        refreshPrizeDialog(userInfo.prizeType);
                        $(".prizeLayer").css("transform", "translate3d(0, 0px, 0px)").css("-webkit-transform", "translate3d(0, 0px, 0px)");
                        $(".prizeLayer .prizeResult1Dialog").css("display", "");
                        $(".prizeLayer .cloud").css("display", "");
                    }
                }
            }
            // //测试
            // backendService.thumbup(1, 2, "年年有鱼", function (data) {
            //     console.log(data);
            // });
            //信息

        }
    });

});


