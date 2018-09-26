
//编译模板
(function($) {
    var compiled = {};
    $.fn.template7 = function(template, data) {
        if (template instanceof jQuery) {
            var templateId = $(template).attr('id');
        } else {
            return false;
        }
        if (typeof (compiled[templateId]) == 'undefined') {
            template = $(template).html();
            compiled[templateId] = Template7.compile(template);
        }
        var appendFlag = arguments[2] ? arguments[2] : 0;
        if (appendFlag) {
            this.append(compiled[templateId](data));
        } else {
            this.html(compiled[templateId](data));
        }
    };

})(jQuery);

$(function() {
    // 监听后退事件 ，点击返回关闭页面
    pushHistory();
    window.addEventListener("popstate", function(e) {
        window.location="../"
        // WeixinJSBridge.call('closeWindow');//点击返回关闭窗口
        // location.href="../index";//或者根据自己的需求跳转
    }, false);
    function pushHistory() {
        var state = {
            title : "title",
            url : "#"
        };
        window.history.pushState(state, "title", "#");
    }
    getChargingInfo();
    shownum();
});




// 自动跳转
var i = 1;
function shownum() {

    if(i%3 == 1){
        document.getElementById("showTimes").innerHTML =  "加载中..";
    }
    if(i%3 == 2){
        document.getElementById("showTimes").innerHTML =  "加载中...";
    }
    if(i%3 == 0){
        document.getElementById("showTimes").innerHTML =  "加载中.";
    }

    setTimeout('shownum()', 1000);
    i=i+1;
}
//查询可远程断电的设备
function getChargingInfo() {
    $.ajax({
        type : 'POST',
        data : {openId : localStorage.getItem("openId")},
        url : "./poweroff/queryPowerOffByOpenId",
        dataType : "json",
        async : false,
        success : function(data) {
            console.log(data.respObj)
            if (data.respObj.length != 0 && data.respCode == "000000") {
                $("#showTimes").hide();
                $(".codePay-inner").template7($('#dataListTpl'), data);
                remainingTime(timeAddMin(data.operStartTime,data.chargeTime));
                userTime(data.operStartTime)
            } else {
                // layer.msg("未查询到可远程断电的设备...", {
                // 	shift : 15
                // });
                setTimeout(getChargingInfo(), 1000);
            }
        }
    });
}

function confirm() {
    layer.confirm('您确定要断开本次充电吗？', {
        btn : [ '取消', '断电' ]
        //按钮
    }, function() {
        layer.msg('已取消');
    }, function() {

        var deviceId = $("#deviceId").val();
        if (deviceId.length == 11 | deviceId.length == 12) {
            poweroffNewDevice();
        }else {
            poweroff();
        }
        getChargingInfo()
    });
}

function poweroffNewDevice() {
    var id = $("#id").val();
    var deviceId = $("#deviceId").val();
    var devicePort = $("#devicePort").val();
    $.ajax({
        type : 'POST',
        url : "./newDevice/powerOff",
        dataType : "json",
        data : {
            openId : localStorage.getItem("openId"),
            id : id,
            deviceId : deviceId,
            channeNum : devicePort
        },
        async : false,
        success : function(data) {
            if (data == "1") {
                layer.msg("远程断电成功...", {
                    shift : 5
                });

                // window.location.href = "./index"
            } else {
                layer.msg("远程断电失败...", {
                    shift : 5
                });
            }
        }
    });
}

function poweroff() {
    var id = $("#id").val();
    var deviceId = $("#deviceId").val();
    var devicePort = $("#devicePort").text();
    $.ajax({
        type : 'POST',
        url : "./poweroff/poweroff",
        dataType : "json",
        data : {
            openId : localStorage.getItem("openId"),
            id : id,
            deviceId : deviceId,
            channeNum : devicePort
        },
        async : false,
        success : function(data) {
            if (data.respCode == "000000") {
                layer.msg("远程断电成功...", {
                    shift : 5
                });
            } else {
                layer.msg("远程断电失败...", {
                    shift : 5
                });
            }
        }
    });
}




function timeAddMin(timeStr,chargeTime){
    var nowDate = new Date(timeStr);
    var endDate=nowDate.getTime()+chargeTime*1000*60;
    return endDate;
}

function userTime(timeStr){
    setInterval(function(){

        var nowDate = new Date();
        nowDate = nowDate.getTime();
        var startDate = new Date(timeStr);
        startDate = startDate.getTime();
        var mss = nowDate-startDate;
        var hours = parseInt(mss / 1000 / 60 / 60 % 24, 10);
        var minutes = parseInt(mss / 1000 / 60 % 60, 10);
        var seconds =parseInt(mss / 1000 % 60, 10);
        document.getElementById("userTime").innerHTML=checkTime(hours) + "小时" + minutes + "分" + seconds + "秒";
    },1000)

}



function remainingTime(timeStr,item){
    setInterval(function(){
        var nowTime = new Date(timeStr) - new Date;
        var minutes = parseInt(nowTime / 1000 / 60 % 60, 10);//计算剩余的分钟
        var seconds = parseInt(nowTime / 1000 % 60, 10);//计算剩余的秒数


        minutes = checkTime(minutes);
        seconds = checkTime(seconds);
        if(item==='day'){
            // let days = parseInt(nowTime / 1000 / 60 / 60 / 24, 10); //计算剩余的天数
            var hours = parseInt(nowTime / 1000 / 60 / 60 % 24, 10); //计算剩余的小时
            // days = checkTime(days);
            hours = checkTime(hours);
            document.getElementById('remainingTime').innerHTML=hours + "小时" + minutes + "分" + seconds + "秒";
        }else{
            var hours = parseInt(nowTime / ( 1000 * 60 * 60), 10); //计算剩余的小时
            hours = checkTime(hours);
            document.getElementById("remainingTime").innerHTML = hours + "小时" + minutes + "分" + seconds + "秒";
        }
    },1000);
}

function checkTime(i) { //将0-9的数字前面加上0，例1变为01
    if(i <= 0){
        return "0"
    }
    if ( i < 10) {
        i = "0" + i;
    }

    return i;
}