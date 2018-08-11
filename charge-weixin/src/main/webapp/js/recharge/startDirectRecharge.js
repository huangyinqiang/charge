

function getActivity() {

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