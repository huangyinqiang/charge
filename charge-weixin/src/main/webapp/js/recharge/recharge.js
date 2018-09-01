var activityId;
// 优惠ID
var activityInfo;
// 优惠信息
var walletAccount;
// 微信钱包
var userInfo;
// 用户信息
var device;
// 设备信息
var deviceId;
// 设备编号
var area;
// 设备地址
var type = null;
// 充值类型 CH电卡，WA钱包
var money = null;
// 本次充值金额
var chargeNum = null;
// 活动充值金额（分）或者次数
var coupon = null;
// 活动赠送金额（分）或者次
var selectedHour;
// 传值用
var flag;
// 重复提交标记
$(function () {
    // 获取URL中的设备编号，地址
    var localUrl = window.location.href;
    deviceId = GetQueryString('deviceId');
    activityId = GetQueryString('activityId');
    type = GetQueryString('type');
    //充值类型
    // 	if(type == "CH" ){
    // //		$('#radioCH')
    // 		$("input[value='CH']").get(0).checked=true;
    // 	}
    // 查询设备信息
    getDeviceInfo(deviceId);
    $('.icon-ditu1').text(decodeURI(device.area));
    // 查询用户信息
    getUserInfo();
    // 设置钱包余额
    $('#walletAccount').text(walletAccount / 100);
    // "充值选项"事件
    $(":radio").click(function () {
        $("#explain").text("充值成为会员，享受更多优惠！");
        // 选择事件变化，重新赋值
        selectedHour = '请选择充值金额！'// 选择事件变化，重新赋值
        money = null;
        // 选择事件变化，重新赋值
    });
    getActivityInfo();
    // 查询活动信息
    rechargeAmount();
    //判断充值选项
    // 选择充值金额
    $('.select-type').on('click', function () {
        rechargeAmount();
    });
    // 选择金额
    $('.elecard-wallte').on('click', '.weui-flex__item', function () {
        $('.elecard-wallte').on('click', '.weui-flex__item', function () {
            $('.weui-flex__item').children().removeClass('active');
            var actNum = $(this).children().addClass('active').attr('data-date');
            // 循环优惠活动信息
            for (var key in activityInfo) {
                var act = activityInfo[key];
                if (actNum == act.actNum) {
                    // 活动编号=选中的金额编号
                    money = act.money;
                    // 金额(分)
                    chargeNum = act.chargeNum;
                    // 活动充值金额（分）/次
                    coupon = act.coupon;
                    // 活动赠送金额（分）/次
                    $("#explain").text(act.remark);
                    // 活动说明
                    break;
                }
            }
            selectedHour = $(this).children().html();
            // 充值金额
            $('#selectedMoney').val($(this).children().attr('data-date'));
        })
    });
})
    // 充值金额
    function rechargeAmount()
    {
        var selectedtype = type;
        if (selectedtype == 'WA')
        {
            type = "WA";
            // 充值类型
            $('#elecardType').css('display', 'none');
            $('#walletType').css('display', 'block');
            // 循环优惠活动信息 给页面标签赋值
            for ( var key in activityInfo)
            {
                var act = activityInfo[key];
                if ('2001' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='2001']").text( act.money  / 100 + "元")
                }
                if ('2002' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='2002']").text( act.money  / 100 + "元")
                }
                if ('2003' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='2003']").text( act.money  / 100 + "元")
                }
                if ('2005' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='2005']").text( act.money  / 100 + "元")
                }
                if ('2006' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='2006']").text( act.money  / 100 + "元")
                }
            }
        }
        else
        {
            type = "CH";
            // 充值类型
            // 如果选择电卡，绑定后才可以充值
            if (userInfo.band == 'N' || userInfo.cardNumber == null || userInfo.cardNumber == '') {
                alert("亲，绑定您的充电卡后才能充值哦...");
                location.href = "../user/userInfo";
                return;
            }
            $('#walletType').css('display', 'none');
            $('#elecardType').css('display', 'block');
            // 循环优惠活动信息 给页面标签赋值
            for ( var key in activityInfo)
            {
                var act = activityInfo[key];
                if ('1001' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='1001']").text( act.money  / 100 + "元")
                }
                if ('1002' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='1002']").text( act.money  / 100 + "元")
                }
                if ('1003' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='1003']").text( act.money  / 100 + "元")
                }
                if ('1005' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='1005']").text( act.money  / 100 + "元")
                }
                if ('1006' == act.actNum) {
                    // 活动编号=选中的金额编号
                    $(".weui-flex__item a[data-date='1006']").text( act.money  / 100 + "元")
                }
            }
        }
    }
    // 开始充值
    function startCharging()
    {
        if (money == null || chargeNum == null || coupon == null) {
            //		alert("请选择充值金额!");
            layer.msg("请选择充值金额！", {
                shift : 5
            });
            return;
        }
        wxpay(money);
    }
    /* 微信支付 */
    function wxpay(money)
    {
        $.post("../pay", {
                total_fee : LeadBase.encrypts(money.toString()),
                openId : openId
            },
            function (res)
            {
                if (res.code == 0)
                {
                    var data = $.parseJSON(res.data);
                    if (typeof WeixinJSBridge == "undefined")
                    {
                        if (document.addEventListener) {
                            document.addEventListener('WeixinJSBridgeReady', onBridgeReady(data), false);
                        }
                        else if (document.attachEvent)
                        {
                            document.attachEvent('WeixinJSBridgeReady', onBridgeReady(data));
                            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(data));
                        }
                    }
                    else {
                        onBridgeReady(data);
                    }
                }
                else
                {
                    if (res.code == 2) {
                        layer.alert(res.message);
                    }
                    else {
                        layer.msg("参数错误：" + res.message, {
                            shift : 6
                        });
                    }
                }
            });
    }
    function onBridgeReady(json)
    {
        WeixinJSBridge.invoke('getBrandWCPayRequest', json, function (res)
        {
            // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 ok，但并不保证它绝对可靠。
            if (res.err_msg == "get_brand_wcpay_request:ok") {
                recharge();
            }
            else {
                //			alert("支付失败，请联系客服...");
                layer.msg("支付失败，请联系客服...", {
                    shift : 5
                });
            }
        });
    }
    // 查询设备价格信息
    function getDeviceInfo(deviceId)
    {
        $.ajax(
            {
                type : 'POST', url : "../device/queryDeviceInfoByDeviceId", dataType : "json", data : {
                    deviceId : deviceId
                },
                cache : false, // *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时
                // 从缓存里读取,为false只有ＧＥＴ请求生效
                async : false, // 设置同步方式，false为同步
                success : function (data)
                {
                    if (data.respObj != null && data.respCode == "000000") {
                        device = data.respObj;
                    }
                    else {
                        layer.msg("设备不存在，请稍等片刻或更换其他设备...", {
                            shift : 5
                        });
                    }
                }
            });
    }
    // 查询用户信息
    function getUserInfo()
    {
        $.ajax(
            {
                type : "POST", url : "../user/findUserInfo", data : {
                    openId : localStorage.getItem("openId")
                },
                cache : false, // *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时
                // 从缓存里读取,为false只有ＧＥＴ请求生效
                async : false, // 设置同步方式，false为同步
                dataType : "json",
                success : function (data)
                {
                    if (data.respObj != null && data.respCode == "000000") {
                        userInfo = data.respObj;
                        walletAccount = userInfo.walletAccount;
                    }
                    else {
                        // 用户不存在 跳转到主页
                        location.href = "..";
                    }
                }
            });
    }
    // 查询优惠信息
    function getActivityInfo()
    {
        $.ajax(
            {
                type : 'POST', url : "../recharge/queryActivityInfo", dataType : "json", data : {
                    activityId : device.activity_id
                },
                cache : false, // *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时 从缓存里读取
                async : false, // 设置同步方式，false为同步
                success : function (data)
                {
                    if (data.respObj != null && data.respCode == "000000") {
                        activityInfo = data.respObj;
                    }
                    else {
                        layer.msg("活动信息不存在，请联系客服...", {
                            shift : 5
                        });
                    }
                }
            });
    }
    // 充值
    function recharge()
    {
        $.ajax(
            {
                type : 'POST', url : "../recharge/addChargeMoney", dataType : "json", data :
                    {
                        openId : localStorage.getItem("openId"), type : type, deviceId : deviceId, money : money,
                        chargeNum : chargeNum, walletAccount : walletAccount, cardNumber : userInfo.cardNumber,
                        coupon : coupon
                    },
                cache : false, // *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时 从缓存里读取
                async : false, // 设置同步方式，false为同步
                success : function (data)
                {
                    if (data.respCode == "000000" && type == "WA") {
                        // alert("充值成功");
                        layer.msg("充值成功", {
                            shift : 5
                        });
                        location.href = "../index"
                    }
                    else if (data.respCode == "000000" && type == "CH") {
                        alert("充值电卡支付成功，请根据设备语音提示继续操作...");
                        location.href = "../index"
                    }
                    else {
                        //				alert("充值失败，请联系客服...");
                        layer.msg("充值失败，请联系客服...", {
                            shift : 5
                        });
                    }
                }
            });
    }
    // 获取URL中的参数值
    function GetQueryString(url)
    {
        var reg = new RegExp("(^|&)" + url + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    }
