function getUriParam(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return "";
}


function BackendService() {
    this.login = function () {
        var state = getUriParam("state");
        window.location.href = "webauth?state=" + state;
    }

    this.getState = function () {
        return getUriParam("state");
    }

    //获得当前用户信息
    this.getUserInfo = function (callback) {
        var sendData = {};
        $.ajax({
            type: "GET",
            url: "getUserInfo",
            //url: "json/userinfo.json",
            dataType: 'json',
            async: true,
            data: sendData,//也可以是字符串链接"id=1001"，建议用对象
            success: function (data) {
                console.log(data);
                if (data == null || data == undefined || data.collects == undefined) {
                    callback({result: 0, msg: "服务器异常！"});
                } else {
                    callback(data);
                }
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                callback({result: 0, msg: "服务器异常！"});
            }
        });
    };
    //点赞
    this.thumbup = function (index, id, message, callback) {
        var sendData = {};
        sendData.index = index;
        sendData.id = id;
        sendData.message = message;
        console.log(sendData);
        $.ajax({
            type: "POST",
            url: "thumb-up",
            //url: "json/msg.json",
            dataType: 'json',
            async: true,
            data: sendData,//也可以是字符串链接"id=1001"，建议用对象
            success: function (data) {
                console.log(data);
                if (data == null || data == undefined || data.result == undefined) {
                    callback({result: 0, msg: "服务器异常！"});
                } else {
                    callback(data);
                }

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                callback({result: 0, msg: "服务器异常！"});
            }
        });
    }
    //更新表单名
    this.updateformname = function (formname, callback) {
        var sendData = {};
        sendData.formname = formname;
        $.ajax({
            type: "POST",
            url: "formname",
            //type: "GET",
            //url: "json/msg.json",
            dataType: 'json',
            async: true,
            data: sendData,//也可以是字符串链接"id=1001"，建议用对象
            success: function (data) {
                console.log(data);
                if (data == null || data == undefined || data.result == undefined) {
                    callback({result: 0, msg: "服务器异常！"});
                } else {
                    callback(data);
                }

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                callback({result: 0, msg: "服务器异常！"});
            }
        });
    }
    //6.生成奖品-抽奖
    this.drawPrize = function (callback) {
        var sendData = {};
        $.ajax({
            type: "POST",
            url: "drawPrize",
            dataType: 'json',
            async: true,
            data: sendData,
            success: function (data) {
                console.log(data);
                if (data == null || data == undefined || data.prizeType == undefined) {
                    callback({result: 0, msg: "服务器异常！"});
                } else {
                    callback(data);
                }

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                callback({result: 0, msg: "服务器异常！"});
            }
        });
    }
    //6.生成QRCODE
    this.getQRCode = function (callback) {
        var sendData = {};
        $.ajax({
            type: "POST",
            url: "getQRCode",
            dataType: 'text',
            async: true,
            data: sendData,
            success: function (data) {
                console.log(data);
                if (data == null || data == undefined || data.indexOf("data:image") < 0) {
                    callback({result: 0, msg: "服务器异常！"});
                } else {
                    callback(data);
                }

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                callback({result: 0, msg: "服务器异常！"});
            }
        });
    }

    //7.更新联系方式
    this.updateContact = function (contacts, phone, address, email, callback) {
        var sendData = {};
        sendData.address = address;
        sendData.phone = phone;
        sendData.email = email;
        sendData.contacts = contacts;

        console.log("senddata");
        console.log(sendData);
        $.ajax({
            type: "POST",
            url: "updateContact",
            dataType: 'json',
            async: true,
            data: sendData,
            success: function (data) {
                console.log(data);
                if (data == null || data == undefined || data.result == undefined) {
                    callback({result: 0, msg: "服务器异常！"});
                } else {
                    callback(data);
                }

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                callback({result: 0, msg: "服务器异常！"});
            }
        });
    }

    //8.获得他人赞的情况
    this.getOtherInfo = function (id, callback) {
        var sendData = {};
        sendData.id = id;
        console.log(id);
        $.ajax({
            type: "POST",
            url: "getOtherInfo",
            // url: "json/otherinfo.json",
            dataType: 'json',
            async: true,
            data: sendData,
            success: function (data) {
                console.log(data);
                if (data == null || data == undefined || data.collects == undefined) {
                    callback({result: 0, msg: "服务器异常！"});
                } else {
                    callback(data);
                }

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
                callback({result: 0, msg: "服务器异常！"});
            }
        });
    }


}
