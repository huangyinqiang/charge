$(function() {
	getCompanyInfo()
});

function getCompanyInfo() {
	$.ajax({
		type : 'POST',
		url : "../about/getCompanyInfo",
		dataType : "json",
		data : {},
		cache : false,// *ie下面只会建立一次 ajax 请求，将响应结果放在浏览器缓存里 下次调用该ajax请求时 从缓存里读取
		async : false,// 设置同步方式，false为同步
		success : function(data) {
			$(".shortName").html(data.shortName);
			$(".company").html(data.company);
			$(".wechat").html(data.wechat);
			$(".tel").html(data.tel);
		}
	});
}