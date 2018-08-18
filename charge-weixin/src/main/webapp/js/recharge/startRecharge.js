var localUrl = window.location.href;
var type;//充值類型
$(function() {
	var localUrl = window.location.href;
	type = GetQueryString('type');
	$.ajax({
		type : "GET",
		url : "./scan/getJsSdk",
		data : {
			url : localUrl
		},
		cache : false,// *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时 从缓存里读取,为false只有ＧＥＴ请求生效
		async : false,// 设置同步方式，false为同步
		dataType : "json",
		success : function(data) {
			wx.config({
				debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId : data.appId, // 必填，公众号的唯一标识
				timestamp : data.timestamp, // 必填，生成签名的时间戳
				nonceStr : data.nonceStr, // 必填，生成签名的随机串
				signature : data.signature,// 必填，签名，见附录1
				jsApiList : [ 'scanQRCode']
			// 必填，需要使用的JS接口列表
			});
		}
	});
});
// 扫码查询
// function scanQR() {
// 	wx.scanQRCode({
// 		needResult : 1,// 默认为0，扫描结果由微信处理，1则直接返回扫描结果
// // 		desc : 'scanQRCode desc',
// // 		success : function(res) {
// // 			// 扫码后获取结果参数赋值给Input
// // 			queryDevice(res.resultStr);
// // 		}
// // 	});
// // }

function scanQR() {

            queryDevice("290000001");

}
// 输入设备充值
function inputNum() {
	queryDevice($("#deviceNum").val()); // 设备编号
}
// 查询
function queryDevice(value) {
	$.ajax({
		type : 'GET',
		url : "./device/queryDeviceInfoByQr",
		dataType : "json",
		data : {
			qr : value
		},
		cache : false,// *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时 从缓存里读取,为false只有ＧＥＴ请求生效
		async : false,// 设置同步方式，false为同步
		success : function(data) {
			if (data.respCode != '000000' | null == data.respObj) {
				layer.msg("设备暂时离线，请稍后再试...", {
					shift : 5
				});
				return;
			}
			location.href = "./recharge/recharge?deviceId="
					+ data.respObj.match_num + "&area="
					+ encodeURI(encodeURI(data.respObj.area)) + "&activityId="
					+ data.respObj.activity_id + "&type=WA"
		},
		error : function() {

		}
	});
}

//获取URL中的参数值
function GetQueryString(url) {
	var reg = new RegExp("(^|&)" + url + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}