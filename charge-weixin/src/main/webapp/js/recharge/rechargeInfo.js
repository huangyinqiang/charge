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

$(function () {
   
    var list = [] 
    var data = {
    		openId : localStorage.getItem("openId"),
    		pageNumber : 0,  // 页码
    		pageSize : 5    // 每页展示条数
    	}

    // dropload
    $('.content').dropload({
        scrollArea: window,
        domUp: {
            domClass: 'dropload-up',
            domRefresh: '<div class="dropload-refresh">↓下拉刷新-充值记录</div>',
            domUpdate: '<div class="dropload-update">↑释放更新-充值记录</div>',
            domLoad: '<div class="dropload-load"><span class="loading"></span>加载中-充值记录...</div>'
        },
        domDown: {
            domClass: 'dropload-down',
            domRefresh: '<div class="dropload-refresh">↑上拉加载更多-充值记录</div>',
            domLoad: '<div class="dropload-load"><span class="loading"></span>加载中-充值记录...</div>',
            domNoData: '<div class="dropload-noData">暂无更多数据</div>'
        },
        loadUpFn: function (me) {
        	// 重置页数，重新获取loadDownFn的数据
            data.pageNumber = 0;
            $.ajax({
            	type : 'POST',
    			url : "../recharge/queryRechargeInfo",
    			data : data,
    			dataType : "json",
                success: function (data) {
                	if (data.respObj && data.respCode == "000000") {
    					var info = data.respObj;
    					if(info.list.length>0){
        					$(".codePay-inner").template7($('#dataListTpl'), info);
    						 // 每次数据加载完，必须重置
                            me.resetload();
                            // 解锁loadDownFn里锁定的情况
                            me.unlock();
                            me.noData(false);
    					}
    				} 
                },
                error: function (xhr, type) {
//                    alert('Ajax error!');
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        },
        loadDownFn: function (me) {
            data.pageNumber++;
            $.ajax({
            	type : 'POST',
    			url : "../recharge/queryRechargeInfo",
    			dataType : "json",
    			data : data,
                success: function (data) {
                	if (data.respObj && data.respCode == "000000") {
    					var info = data.respObj;
    					if(info.list.length>0){
    						list=list.concat(info.list)
        					info.list = list
                                // 插入数据到页面，放到最后面
                            	$(".codePay-inner").template7($('#dataListTpl'), info);
                                // 每次数据插入，必须重置
                                me.resetload();
    					}else {  // 如果没有数据
       					  // 锁定
                          me.lock();
                          // 无数据
                          $('.dropload-down').children().remove()
                          $('.dropload-down').append('<div class="dropload-noData">暂无数据</div>')
                          
                          me.noData();
    					}
    				} 
                },
                error: function (xhr, type) {
                    console.log(xhr)
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        },
        threshold: 50
    });
});
