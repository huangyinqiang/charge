$(function() {
	var chargePort = GetQueryString('chargePort');
	$('#devicePort').text(chargePort);
	var time = GetQueryString('time');
	$('#time').text(time);

	// 监听后退事件 ，点击返回关闭页面
	pushHistory();
	window.addEventListener("popstate", function(e) {
		WeixinJSBridge.call('closeWindow');//点击返回关闭窗口
		location.href="../index";//或者根据自己的需求跳转
	}, false);
	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}
});
// 获取URL中的参数值
function GetQueryString(url) {
	var reg = new RegExp("(^|&)" + url + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

// 自动跳转
var i = 11;
function shownum() {
	i = i - 1;
	document.getElementById("showTimes").innerHTML = i + "秒后转入充电记录页面...";
	setTimeout('shownum()', 1000);
}