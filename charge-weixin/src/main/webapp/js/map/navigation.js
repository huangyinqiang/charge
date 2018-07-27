var localUrl = window.location.href;
$(function() {
	// 获取jssdk
	getJsSdk();
});

// 开始导航
function start(latitude, longitude) {
	// // 使用微信内置地图查看位置接口
	$.ajax({
		type : 'POST',
		url : "../device/queryAddress",
		dataType : "json",
		data : {
			longitude : longitude,
			latitude : latitude
		},
		cache : false,
		async : false,//
		dataType : "json",
		success : function(data) {
			if (data.respObj.length != 0 && data.respCode == "000000") {
				wx.openLocation({
					latitude : latitude, // 纬度，浮点数，范围为90 ~ -90,不能是字符串。
					longitude : longitude, // 经度，浮点数，范围为180 ~ -180,不能是字符串。
					name : data.respObj.title, // 位置名
					address : data.respObj.address, // 地址详情说明
					scale : 14, // 地图缩放级别,整形值,范围从1~28。默认为最大
					infoUrl : 'http://www.henankejue.com' // 在查看位置界面底部"更多信息"的超链接,可点击跳转
				});
			} else {
				layer.msg("导航失败，请联系客服...", {
					shift : 5
				});
//				alert("导航失败，请联系客服...");
			}
		}
	});
}

// 获取微信js-sdk
function getJsSdk() {
	$.ajax({
		type : "post",
		url : "../scan/getJsSdk",
		data : {
			url : localUrl
		},
		cache : false,
		async : false,//
		dataType : "json",
		success : function(data) {
			wx.config({
				debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId : data.appId, // 必填，公众号的唯一标识
				timestamp : data.timestamp, // 必填，生成签名的时间戳
				nonceStr : data.nonceStr, // 必填，生成签名的随机串
				signature : data.signature,// 必填，签名，见附录1
				jsApiList : [ 'openLocation', 'getLocation' ]
			// 必填，需要使用的JS接口列表
			});
			wx.ready(function() {
				wx.getLocation({
					success : function(res) {
						// 根据座标查询附近的设备信息 5000米
						queryDevice(res.longitude, res.latitude);
					},
					cancel : function(res) {
//						alert('false');
						layer.msg("失败", {
							shift : 5
						});
					}
				});
			});
		}
	});
}

// 查询
function queryDevice(longitude, latitude) {
	$.ajax({
		type : 'POST',
		url : "../device/queryNearDevice",
		dataType : "json",
		data : {
			longitude : longitude,
			latitude : latitude
		},
		async : true,
		success : function(data) {
			if (data.respObj.length != 0 && data.respCode == "000000") {
				$(".codePay-inner").template7($('#dataListTpl'), data);
			} else {
//				alert("附近没用充电设备...");
				layer.msg("附近没用充电设备...", {
					shift : 5
				});
			}

		},
		error : function() {

		}
	});
}

// 编译模板
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