$(function() {
	var devicePort = GetQueryString('devicePort');
	$('#devicePort').text(devicePort);
	var time = GetQueryString('time');
	$('#time').text(time);

    window.addEventListener("beforeunload", function(e) {
    	if (socketIsOpen && !userIsConfirmed ) {
    		//插座打开，但是用户未确认，需要关闭插座
            socketIsOpen = false;
		}
    }, false);

    window.addEventListener("unload", function(e) {
        if (socketIsOpen && !userIsConfirmed ) {
            //插座打开，但是用户未确认，需要关闭插座
            socketIsOpen = false;
        }
    }, false);


	// 监听后退事件 ，点击返回关闭页面
	pushHistory();
	window.addEventListener("popstate", function(e) {
        WeixinJSBridge.call('closeWindow');//点击返回关闭窗口
		// location.href="../index";//或者根据自己的需求跳转
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
var i = 1;
function shownum() {
	document.getElementById("showTimes").innerHTML = i + "功率检测中...";
	setTimeout('shownum()', 1000);
	i=i+1;
}