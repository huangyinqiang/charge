var walletAccount;// 微信钱包
var autoPrice;// 智能充电预扣费价格
var device;// 设备信息
var deviceId;// 设备编号
var activityId;// 活动编号
var devicePort = 0;// 设备插座
var area;// 设备地址
var userInfo;// 设备插座
var time;// 充电时间
var money;// 本次充电金额
var type;// 充电类型
var operType;// 支付类型
var flag;// 重复提交标记
var selectedHour;// 传值用
var openId;
$(function() {
	var $iosDialog2 = $('#iosDialog2');
	$('.weui-dialog').on('click', '.weui-flex__item', function() {
		$('.weui-flex__item').children().removeClass('active');
		$(this).children().addClass('active').attr('data-date');
		selectedHour = $(this).children().html();// 充电几小时
		$('#selectedTime').val($(this).children().attr('data-date'));

	})
	$('#dialogs').on('click', '.weui-dialog__btn', function() {
		$(this).parents('.js_dialog').fadeOut(200);
	});
	// 弹出层关闭按钮
	$('.close-icon').click(function() {
		$('.js_dialog').css('display', 'none')
	})
	// 选择充电时间
	$('#showIOSDialog2').on('click', function() {
		$iosDialog2.fadeIn(200);
	});
	// 选择充电插座
	$('#showportDialog').on('click', function() {
		// if($(".grid").css('display') =="none"){
		// $('.grid').css('display', 'block');
		// } else if ($(".grid").css('display') =="block"){
		// $('.grid').css('display', 'none');
		// }
	});
	openId = localStorage.getItem("openId");// 获取名称为“key”的值
	// 获取URL中的设备编号，地址
	var localUrl = window.location.href;
	deviceId = GetQueryString('deviceId');
	activityId = GetQueryString('activityId');
	getDeviceStatus(deviceId);
	getDeviceInfo(deviceId);
	getUserInfo();
	$('.icon-ditu1').text(decodeURI(device.area));
});
// 设置非会员加个
function selectxftime2() {
	money = parseInt(device.tow_hours_price);// 非会员价格
}
function selectxftime4() {
	money = parseInt(device.four_hours_price);// 非会员价格
}
function selectxftime8() {
	money = parseInt(device.eight_hours_price);// 非会员价格
}
function selectxftime12() {
	money = parseInt(device.four_hours_price);// 非会员价格
}
function selectxftimeauto() {
	money = parseInt(device.twelve_hours_price);// 非会员价格
}

// 确定充电时间和充电端口按钮
function affirm() {
	$("#showIOSDialog2").html(selectedHour);
	time = $('#selectedTime').val();
	if (time == "" || time == null) {
		layer.msg("请选择充电时间!", {
			shift : 5
		});
		return;
	}
	// 会员(选择智能充电)
	if (walletAccount >= autoPrice && time == 800) {
		$("#explain").text('会员专享智能充电，充满后自动断电计时扣费。');// 智能充电说明
		return;
	}
	// 钱包小于价格,认定为非会员(选择智能充电)
	if (walletAccount < money && time == 800) {
		reCharge();// 直接提示充值钱包
		return;
	}
	// 会员,但钱包小于5块(选择智能充电)
	if (autoPrice > walletAccount && time == 800) {
		reCharge();// 直接提示充值钱吧
		return;
	}
	// 会员(未选择智能充电)
	if (walletAccount >= autoPrice) {
		$("#explain").text('会员充电' + time + '分钟，根据功率自动计算费用。');// 充电说明
		return;
	}
	// 认定为非会员 (未选择智能充电)
	if (money > walletAccount) {
		$("#explain").text('余额不足，请"点击开始充电"充值钱包或微信支付。');// 充电说明
		return;
	}
	// 会员,但钱包小于5块(未选择智能充电)
	if (autoPrice > walletAccount) {
		$("#explain").text('亲，钱包余额少于五元，只能充电二小时按功率自动计费');// 充电说明
		return;
	}

}
// 开始充电按钮
function startCharging() {
	if (devicePort == "0") {
		layer.msg("请选择充电插座!", {
			shift : 5
		});
		return;
	}
	if (time == "0" || time == null || time == "") {
		layer.msg("请选择充电时间!", {
			shift : 5
		});
		return;
	}
	// 会员(选择智能充电)
	if (walletAccount >= autoPrice && time == 800) {
		type = "auto";// 智能充电
		operType = "W";// 会员扣费
		money = autoPrice;
		memCharge();
		return;
	}
	// 会员,但钱包小于5块(选择智能充电)
	if (autoPrice > walletAccount && time == 800) {
		reCharge();// 充值钱包
		return;
	}
	// 会员(未选择智能充电)
	if (walletAccount >= autoPrice) {
		type = "charge";// 按功率扣费
		operType = "W";// 会员扣费
		money = autoPrice;// 预扣费5元
		memCharge();
		return;
	}
	// 小于1元,认定为非会员 (未选择智能充电)
	if (walletAccount < money) {
		chargeOrPay();// 充值钱包或者单次支付
		return;
	}
	// 会员,但钱包小于5块，只能充电2小时(未选择智能充电)
	if (autoPrice > walletAccount) {
		chargeTwoHoursOrPay();
		return;
	}

}
// 会员充电提示
function memCharge() {
	layer.confirm('请确认' + devicePort + '插座，充电' + time + '分钟；点击"确认" 后开始充电', {
		btn : [ '确定', '取消' ]
	}, function() {
		invokeServerCharging();
	}, function() {

	});
}
// 充值钱包或者单次支付
function chargeOrPay() {
	layer.confirm('请确认' + devicePort + '插座，充电' + time
			+ '分钟；点击"临时支付" 支付完成后开始充电，点击"充值" 支付完成后享受会员价格！', {
		btn : [ '临时支付', '充值' ]
	}, function() {
		// 单次支付
		wxpay(money);
	}, function() {
		// 跳转到充值页面
		location.href = "../recharge/recharge?deviceId=" + deviceId
				+ "&activityId=" + activityId + "&area=" + encodeURI(area)
	});
}
// 充值钱包或者扣1块充电两小时
function chargeTwoHoursOrPay() {
	layer.confirm('余额不足，只能充电两小时。请确认' + devicePort + '插座充电' + time
			+ '分钟；点击"充值" 支付完成后享受会员价格，点击"充电" 充电两小时！', {
		btn : [ '充电', '充值' ]
	}, function() {
		type = "charge";// 按功率扣费
		operType = "W";// 会员扣费
		money = 100;
		time = 120;
		invokeServerCharging();
	}, function() {
		location.href = "../recharge/recharge?deviceId=" + deviceId
				+ "&activityId=" + activityId + "&area=" + encodeURI(area)
	});
}
// 充值钱包
function reCharge() {
	layer.confirm('亲，余额不足不能使用此功能哦，点击"确定"去充值成为会员后使用此功能...', {
		btn : [ '取消', '确认' ]
	}, function() {
	}, function() {
		// 跳转到充值页面
		location.href = "../recharge/recharge?deviceId=" + deviceId
				+ "&activityId=" + activityId + "&area=" + encodeURI(area)
	});
}
/* 微信支付 */
function wxpay(money) {
	$.post("../pay", {
		total_fee : LeadBase.encrypts(money.toString()),
	}, function(res) {
		if (res.code == 0) {
			var data = $.parseJSON(res.data);
			if (typeof WeixinJSBridge == "undefined") {
				if (document.addEventListener) {
					document.addEventListener('WeixinJSBridgeReady',
							onBridgeReady(data), false);
				} else if (document.attachEvent) {
					document.attachEvent('WeixinJSBridgeReady',
							onBridgeReady(data));
					document.attachEvent('onWeixinJSBridgeReady',
							onBridgeReady(data));
				}
			} else {
				onBridgeReady(data);
			}
		} else {
			if (res.code == 2) {
				layer.alert(res.message);
			} else {
				layer.msg("参数错误：" + res.message, {
					shift : 6
				});
			}
		}
	});
}
function onBridgeReady(json) {
	WeixinJSBridge.invoke('getBrandWCPayRequest', json, function(res) {
		// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 ok，但并不保证它绝对可靠。
		if (res.err_msg == "get_brand_wcpay_request:ok") {
			type = "temp";// 按功率扣费
			operType = "M";// 微信单次支付
			invokeServerCharging();
		} else {
			layer.msg("支付失败", {
				shift : 5
			});
		}
	});
}

// 调用服务器充电
function invokeServerCharging() {
	$.ajax({
		type : 'POST',
		url : "../charging/startHttpCharging",
		dataType : "json",
		data : {
			openId : openId,
			type : type,
			deviceId : deviceId,
			devicePort : devicePort,
			time : time,
			money : money,
			walletAccount : walletAccount,
			operType : operType
		},
		cache : false,// *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时
		// 从缓存里读取,为false只有ＧＥＴ请求时生效
		async : false,// 设置同步方式，false为同步
		success : function(data) {
			if (data.respCode == "000000") {
				window.location = "../charging/charging?deviceId=" + deviceId
						+ "&chargePort=" + devicePort + "&time=" + time;
			} else {
				layer.msg("充电失败", {
					shift : 5
				});
			}
		}
	});
}
// 查询设备价格信息
function getDeviceInfo(deviceId) {
	$.ajax({
		type : 'GET',
		url : "../device/queryDeviceInfoByDeviceId",
		dataType : "json",
		data : {
			deviceId : deviceId
		},
		cache : false,// *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时
		// 从缓存里读取,为false只有ＧＥＴ请求时生效
		async : false,// 设置同步方式，false为同步
		success : function(data) {
			if (data.respObj != null && data.respCode == "000000") {
				device = data.respObj;
				autoPrice = device.auto_price;
			} else {
				layer.msg("设备不存在，请联系客服...", {
					shift : 5
				});
			}
		}
	});
}
// 查询用户信息
function getUserInfo() {
	$.ajax({
		type : "GET",
		url : "../user/findUserInfo",
		cache : false,// *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时
						// 从缓存里读取,为false只有ＧＥＴ请求生效
		async : false,// 设置同步方式，false为同步
		data : {
			"openId" : openId
		},
		dataType : "json",
		success : function(data) {
			if (data.respObj != null && data.respCode == "000000") {
				userInfo = data.respObj;
				walletAccount = userInfo.walletAccount;
			} else {
				// layer.msg("用户信息不存在", {
				// shift : 5
				// });
				location.href = "..";
			}
		}
	});
}
// 查询设备状态
function getDeviceStatus(deviceId) {
	// 查询设备状态
	$.ajax({
		type : "GET",
		url : "../device/getDeviceStatus",
		data : {
			deviceId : deviceId
		},
		cache : false,// *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时
		// 从缓存里读取,为false只有ＧＥＴ请求生效
		async : false,// 设置同步方式，false为同步
		dataType : "json",
		success : function(data) {
			portStatus(data);
		}
	});
}
// 获取URL中的参数值
function GetQueryString(url) {
	var reg = new RegExp("(^|&)" + url + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

// 设置端口状态
function portStatus(data) {
	if (data.device != null && data.device.onLine == true) {
		$(".normal").html("正常");
		var portStatus1 = data.device.portStatus['01'];
		if (portStatus1) {
			$("#1").text("插座1充电中");
		}
		var portStatus2 = data.device.portStatus['02'];
		if (portStatus2) {
			$("#2").text("插座2充电中");
		}
		var portStatus3 = data.device.portStatus['03'];
		if (portStatus3) {
			$("#3").text("插座3充电中");
		}
		var portStatus4 = data.device.portStatus['04'];
		if (portStatus4) {
			$("#4").text("插座4充电中");
		}
		var portStatus5 = data.device.portStatus['05'];
		if (portStatus5) {
			$("#5").text("插座5充电中");
		}
		var portStatus6 = data.device.portStatus['06'];
		if (portStatus6) {
			$("#6").text("插座6充电中");
		}
		var portStatus7 = data.device.portStatus['07'];
		if (portStatus7) {
			$("#7").text("插座7充电中");
		}
		var portStatus8 = data.device.portStatus['08'];
		if (portStatus8) {
			$("#8").text("插座8充电中");
		}
		var portStatus9 = data.device.portStatus['09'];
		if (portStatus9) {
			$("#9").text("插座9充电中");
		}
		var portStatus10 = data.device.portStatus['10'];
		if (portStatus10) {
			$("#10").text("插座10充电中");
		}
		var portStatus11 = data.device.portStatus['11'];
		if (portStatus11) {
			$("#11").text("插座11充电中");
		}
		var portStatus12 = data.device.portStatus['12'];
		if (portStatus12) {
			$("#12").text("插座12充电中");
		}
	}
}

function selectPort(value, ele) {
	devicePort = value;// 获取选择的插座
	$('.rui-btn').removeClass('rui-btn-outlined');
	$(ele).addClass('rui-btn-outlined')
	$("#showportDialog").html('插座' + value);
}