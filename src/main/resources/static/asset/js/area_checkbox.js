$(function(){	
	$('.all-sort-list > .item').hover(
			function() {
				var eq = $('.all-sort-list > .item').index(this), //获取当前滑过是第几个元素
				h = $('.all-sort-list').offset().top, //获取当前下拉菜单距离窗口多少像素
				s = $(window).scrollTop(), //获取游览器滚动了多少高度
				i = $(this).offset().top, //当前元素滑过距离窗口多少像素
				item = $(this).children('.item-list').height(), //下拉菜单子类内容容器的高度
				sort = $('.all-sort-list').height(); //父类分类列表容器的高度
				if (item < sort) { //如果子类的高度小于父类的高度
					if (eq == 0) {
						$(this).children('.item-list').css('top', (i - h));
					} else {
						$(this).children('.item-list').css('top',
								(i - h) + 1);
					}
				} else {
					if (s > h) { //判断子类的显示位置，如果滚动的高度大于所有分类列表容器的高度
						if (i - s > 0) { //则 继续判断当前滑过容器的位置 是否有一半超出窗口一半在窗口内显示的Bug,
							$(this).children('.item-list').css('top',
									(s - h) + 2);
						} else {
							$(this).children('.item-list').css('top',
									(s - h) - (-(i - s)) + 2);
						}
					} else {
						$(this).children('.item-list').css('top', 3);
					}
				}

				$(this).addClass('hover');
				$(this).children('.item-list').css('display', 'block');
			}, function() {
				$(this).removeClass('hover');
				$(this).children('.item-list').css('display', 'none');
			});

	$('.item > .item-list > .close').click(function() {
		$(this).parent().parent().removeClass('hover');
		$(this).parent().hide();
	});
	/*全部区域*/
	$("#ctrlradioboxallRegion").click(function (){
		if($("#ctrlradioboxallRegion").is(":checked")){
			$(".item-list").show();
			$(".all_area:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".all_area:visible").each(function(){
				$(".item-list").show();
				$(".all_area").attr("checked",false); 
				$(".item-list").hide();
			});
		}
	});
	/*中国区域*/
	$("#ctrlregionregionBodyChina").click(function (){
		if($("#ctrlregionregionBodyChina").is(":checked")){
			$(".item-list").show();
			$(".area_china:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_china:visible").each(function(){		
				$(".item-list").show();
				$(".item-list table input").attr("checked",false);   
				$(this).attr("checked",false);  
				$(".item-list").hide();
			});
		}
	});
	/*华北地区*/	
	$("#ctrlregionregionBodyNorth").click(function(){
		if($("#ctrlregionregionBodyNorth").is(":checked")){
			$(".item-list").show();
			$(".area_BN:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_BN:visible").each(function(){		
				$(".item-list").show();
				$(".area_BN:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*东北地区*/
	$("#ctrlregionregionBodyNorthEast").click(function(){
		if($("#ctrlregionregionBodyNorthEast").is(":checked")){
			$(".item-list").show();
			$(".area_NE:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_NE:visible").each(function(){		
				$(".item-list").show();
				$(".area_NE:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*华东地区*/
	$("#ctrlregionregionBodyEast").click(function(){
		if($("#ctrlregionregionBodyEast").is(":checked")){
			$(".item-list").show();
			$(".area_EC:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_EC:visible").each(function(){		
				$(".item-list").show();
				$(".area_EC:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*华中地区*/
	$("#ctrlregionregionBodyMiddle").click(function(){
		if($("#ctrlregionregionBodyMiddle").is(":checked")){
			$(".item-list").show();
			$(".area_CC:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_CC:visible").each(function(){		
				$(".item-list").show();
				$(".area_CC:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*华南地区*/
	$("#ctrlregionregionBodySouth").click(function(){
		if($("#ctrlregionregionBodySouth").is(":checked")){
			$(".item-list").show();
			$(".area_SC:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_SC:visible").each(function(){		
				$(".item-list").show();
				$(".area_SC:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*西南地区*/
	$("#ctrlregionregionBodySouthWest").click(function(){
		if($("#ctrlregionregionBodySouthWest").is(":checked")){
			$(".item-list").show();
			$(".area_SW:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_SW:visible").each(function(){		
				$(".item-list").show();
				$(".area_SW:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*西北地区*/
	$("#ctrlregionregionBodyNorthWest").click(function(){
		if($("#ctrlregionregionBodyNorthWest").is(":checked")){
			$(".item-list").show();
			$(".area_NW:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_NW:visible").each(function(){		
				$(".item-list").show();
				$(".area_NW:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*其他地区*/
	$("#ctrlregionregionBodyOther").click(function(){
		if($("#ctrlregionregionBodyOther").is(":checked")){
			$(".item-list").show();
			$(".area_OT:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_OT:visible").each(function(){		
				$(".item-list").show();
				$(".area_OT:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*其他国家*/
	$("#ctrlregionregionBodyAbroad").click(function(){
		if($("#ctrlregionregionBodyAbroad").is(":checked")){
			$(".item-list").show();
			$(".area_AB:visible").prop("checked",true);
			$(".item-list").hide();
		}
		else{
			$(".area_AB:visible").each(function(){		
				$(".item-list").show();
				$(".area_AB:visible").prop("checked",false);
				$(".item-list").hide();
			});
		}
	});
	/*-------------------------------城市----------------------------*/
	/*北京*/
	$("#cn_1000").click(function(){
		if($("#cn_1000").is(":checked")){
			$(this).parent().parent().css("background","#DEDEDE");
		}else{
			$(this).parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*天津*/
	$("#cn_3000").click(function(){
		if($("#cn_3000").is(":checked")){
			$(this).parent().parent().css("background","#DEDEDE");
		}else{
			$(this).parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*上海*/
	$("#cn_2000").click(function(){
		if($("#cn_2000").is(":checked")){
			$(this).parent().parent().css("background","#DEDEDE");
		}else{
			$(this).parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*重庆*/
	$("#cn_33000").click(function(){
		if($("#cn_33000").is(":checked")){
			$(this).parent().parent().css("background","#DEDEDE");
		}else{
			$(this).parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*香港*/
	$("#cn_34000").click(function(){
		if($("#cn_34000").is(":checked")){
			$(this).parent().parent().css("background","#DEDEDE");
		}else{
			$(this).parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*台湾*/
	$("#cn_35000").click(function(){
		if($("#cn_35000").is(":checked")){
			$(this).parent().parent().css("background","#DEDEDE");
		}else{
			$(this).parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*澳门*/
	$("#cn_36000").click(function(){
		if($("#cn_36000").is(":checked")){
			$(this).parent().parent().css("background","#DEDEDE");
		}else{
			$(this).parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*河北*/
	$("#cn_13000").click(function(){
		if($("#cn_13000").is(":checked")){
			$(".area_13000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_13000:visible").each(function(){		
				$(".area_13000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_13000").click(function(){
		var length=$(".area_13000:checked").length;
		if(length==$(".area_13000").length){
			$("#cn_13000").prop("checked",true);
		}else{
			$("#cn_13000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	
	/*内蒙古*/
	$("#cn_22000").click(function(){
		if($("#cn_22000").is(":checked")){
			$(".area_22000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_22000:visible").each(function(){		
				$(".area_22000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_22000").click(function(){
		var length=$(".area_22000:checked").length;
		if(length==$(".area_22000").length){
			$("#cn_22000").prop("checked",true);
		}else{
			$("#cn_22000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
		
	});
	/*山西*/
	$("#cn_26000").click(function(){
		if($("#cn_26000").is(":checked")){
			$(".area_26000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_26000:visible").each(function(){		
				$(".area_26000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_26000").click(function(){
		var length=$(".area_26000:checked").length;
		if(length==$(".area_26000").length){
			$("#cn_26000").prop("checked",true);
		}else{
			$("#cn_26000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*黑龙江*/
	$("#cn_15000").click(function(){
		if($("#cn_15000").is(":checked")){
			$(".area_15000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_15000:visible").each(function(){		
				$(".area_15000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_15000").click(function(){
		var length=$(".area_15000:checked").length;
		if(length==$(".area_15000").length){
			$("#cn_15000").prop("checked",true);
		}else{
			$("#cn_15000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}		
	});
	/*吉林*/
	$("#cn_18000").click(function(){
		if($("#cn_18000").is(":checked")){
			$(".area_18000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_18000:visible").each(function(){		
				$(".area_18000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_18000").click(function(){
		var length=$(".area_18000:checked").length;
		if(length==$(".area_18000").length){
			$("#cn_18000").prop("checked",true);
		}else{
			$("#cn_18000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}	
	});
	/*辽宁*/
	$("#cn_21000").click(function(){
		if($("#cn_21000").is(":checked")){
			$(".area_21000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_21000:visible").each(function(){		
				$(".area_21000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_21000").click(function(){
		var length=$(".area_21000:checked").length;
		if(length==$(".area_21000").length){
			$("#cn_21000").prop("checked",true);
		}else{
			$("#cn_21000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});

	/*福建*/
	$("#cn_5000").click(function(){
		if($("#cn_5000").is(":checked")){
			$(".area_5000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_5000:visible").each(function(){		
				$(".area_5000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_5000").click(function(){
		var length=$(".area_5000:checked").length;
		if(length==$(".area_5000").length){
			$("#cn_5000").prop("checked",true);
		}else{
			$("#cn_5000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*安徽*/
	$("#cn_9000").click(function(){
		if($("#cn_9000").is(":checked")){
			$(".area_9000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_9000:visible").each(function(){		
				$(".area_9000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_9000").click(function(){
		var length=$(".area_9000:checked").length;
		if(length==$(".area_9000").length){
			$("#cn_9000").prop("checked",true);
		}else{
			$("#cn_9000").prop("checked",false);
		}	
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*江苏*/
	$("#cn_19000").click(function(){
		if($("#cn_19000").is(":checked")){
			$(".area_19000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_19000:visible").each(function(){		
				$(".area_19000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_19000").click(function(){
		var length=$(".area_19000:checked").length;
		if(length==$(".area_19000").length){
			$("#cn_19000").prop("checked",true);
		}else{
			$("#cn_19000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*江西*/
	$("#cn_20000").click(function(){
		if($("#cn_20000").is(":checked")){
			$(".area_20000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_20000:visible").each(function(){		
				$(".area_20000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_20000").click(function(){
		var length=$(".area_20000:checked").length;
		if(length==$(".area_20000").length){
			$("#cn_20000").prop("checked",true);
		}else{
			$("#cn_20000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}	
	});
	/*山东*/
	$("#cn_25000").click(function(){
		if($("#cn_25000").is(":checked")){
			$(".area_25000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_25000:visible").each(function(){		
				$(".area_25000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_25000").click(function(){
		var length=$(".area_25000:checked").length;
		if(length==$(".area_25000").length){
			$("#cn_25000").prop("checked",true);
		}else{
			$("#cn_25000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*浙江*/
	$("#cn_32000").click(function(){
		if($("#cn_32000").is(":checked")){
			$(".area_32000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_32000:visible").each(function(){		
				$(".area_32000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_32000").click(function(){
		var length=$(".area_32000:checked").length;
		if(length==$(".area_32000").length){
			$("#cn_32000").prop("checked",true);
		}else{
			$("#cn_32000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*河南*/
	$("#cn_14000").click(function(){
		if($("#cn_14000").is(":checked")){
			$(".area_14000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_14000:visible").each(function(){		
				$(".area_14000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_14000").click(function(){
		var length=$(".area_14000:checked").length;
		if(length==$(".area_14000").length){
			$("#cn_14000").prop("checked",true);
		}else{
			$("#cn_14000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*湖北*/
	$("#cn_16000").click(function(){
		if($("#cn_16000").is(":checked")){
			$(".area_16000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_16000:visible").each(function(){		
				$(".area_16000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_16000").click(function(){
		var length=$(".area_16000:checked").length;
		if(length==$(".area_16000").length){
			$("#cn_16000").prop("checked",true);
		}else{
			$("#cn_16000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*湖南*/
	$("#cn_17000").click(function(){
		if($("#cn_17000").is(":checked")){
			$(".area_17000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_17000:visible").each(function(){		
				$(".area_17000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_17000").click(function(){
		var length=$(".area_17000:checked").length;
		if(length==$(".area_17000").length){
			$("#cn_17000").prop("checked",true);
		}else{
			$("#cn_17000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*广东*/
	$("#cn_4000").click(function(){
		if($("#cn_4000").is(":checked")){
			$(".area_4000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_4000:visible").each(function(){		
				$(".area_4000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_4000").click(function(){
		var length=$(".area_4000:checked").length;
		if(length==$(".area_4000").length){
			$("#cn_4000").prop("checked",true);
		}else{
			$("#cn_4000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*海南*/
	$("#cn_8000").click(function(){
		if($("#cn_8000").is(":checked")){
			$(".area_8000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_8000:visible").each(function(){		
				$(".area_8000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_8000").click(function(){
		var length=$(".area_8000:checked").length;
		if(length==$(".area_8000").length){
			$("#cn_8000").prop("checked",true);
		}else{
			$("#cn_8000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*广西*/
	$("#cn_12000").click(function(){
		if($("#cn_12000").is(":checked")){
			$(".area_12000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_12000:visible").each(function(){		
				$(".area_12000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_12000").click(function(){
		var length=$(".area_12000:checked").length;
		if(length==$(".area_12000").length){
			$("#cn_12000").prop("checked",true);
		}else{
			$("#cn_12000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*贵州*/
	$("#cn_10000").click(function(){
		if($("#cn_10000").is(":checked")){
			$(".area_10000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_10000:visible").each(function(){		
				$(".area_10000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_10000").click(function(){
		var length=$(".area_10000:checked").length;
		if(length==$(".area_10000").length){
			$("#cn_10000").prop("checked",true);
		}else{
			$("#cn_10000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*四川*/
	$("#cn_28000").click(function(){
		if($("#cn_28000").is(":checked")){
			$(".area_28000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_28000:visible").each(function(){		
				$(".area_28000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_28000").click(function(){
		var length=$(".area_28000:checked").length;
		if(length==$(".area_28000").length){
			$("#cn_28000").prop("checked",true);
		}else{
			$("#cn_28000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*西藏*/
	$("#cn_29000").click(function(){
		if($("#cn_29000").is(":checked")){
			$(".area_29000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_29000:visible").each(function(){		
				$(".area_29000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_29000").click(function(){
		var length=$(".area_29000:checked").length;
		if(length==$(".area_29000").length){
			$("#cn_29000").prop("checked",true);
		}else{
			$("#cn_29000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*云南*/
	$("#cn_31000").click(function(){
		if($("#cn_31000").is(":checked")){
			$(".area_31000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_31000:visible").each(function(){		
				$(".area_31000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});	
	$(".area_31000").click(function(){
		var length=$(".area_31000:checked").length;
		if(length==$(".area_31000").length){
			$("#cn_31000").prop("checked",true);
		}else{
			$("#cn_31000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});

	/*甘肃*/
	$("#cn_11000").click(function(){
		if($("#cn_11000").is(":checked")){
			$(".area_11000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_11000:visible").each(function(){		
				$(".area_11000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_11000").click(function(){
		var length=$(".area_11000:checked").length;
		if(length==$(".area_11000").length){
			$("#cn_11000").prop("checked",true);
		}else{
			$("#cn_11000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*宁夏*/
	$("#cn_23000").click(function(){
		if($("#cn_23000").is(":checked")){
			$(".area_23000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_23000:visible").each(function(){		
				$(".area_23000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_23000").click(function(){
		var length=$(".area_23000:checked").length;
		if(length==$(".area_23000").length){
			$("#cn_23000").prop("checked",true);
		}else{
			$("#cn_23000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*青海*/
	$("#cn_24000").click(function(){
		if($("#cn_24000").is(":checked")){
			$(".area_24000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_24000:visible").each(function(){		
				$(".area_24000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_24000").click(function(){
		var length=$(".area_24000:checked").length;
		if(length==$(".area_24000").length){
			$("#cn_24000").prop("checked",true);
		}else{
			$("#cn_24000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*陕西*/
	$("#cn_27000").click(function(){
		if($("#cn_27000").is(":checked")){
			$(".area_27000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_27000:visible").each(function(){		
				$(".area_27000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_27000").click(function(){
		var length=$(".area_27000:checked").length;
		if(length==$(".area_27000").length){
			$("#cn_27000").prop("checked",true);
		}else{
			$("#cn_27000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	/*新疆*/
	$("#cn_30000").click(function(){
		if($("#cn_30000").is(":checked")){
			$(".area_30000:visible").prop("checked",true);
			$(this).parent().parent().css("background","#DEDEDE");
		}
		else{
			$(".area_30000:visible").each(function(){		
				$(".area_30000:visible").prop("checked",false);
				$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
			});
		}
	});
	$(".area_30000").click(function(){
		var length=$(".area_30000:checked").length;
		if(length==$(".area_30000").length){
			$("#cn_30000").prop("checked",true);
		}else{
			$("#cn_30000").prop("checked",false);
		}
		if(length>0){
			$(this).closest("table").parent().parent().parent().css("background","#DEDEDE");
		}else{
			$(this).closest("table").parent().parent().parent().css("background","#fafafa none repeat scroll 0 0");
		}
	});
	$(".all_area").click(function(){
		setareacheck();
	});
});
/*选择省份默认勾选市*/
function getPtoScheck(){
	$(".item-list").show();
	if($("#cn_13000").is(":checked")){
		$(".area_13000:visible").prop("checked",true);
	}
	if($("#cn_22000").is(":checked")){
		$(".area_22000:visible").prop("checked",true);
	}
	if($("#cn_26000").is(":checked")){
		$(".area_26000:visible").prop("checked",true);
	}
	if($("#cn_15000").is(":checked")){
		$(".area_15000:visible").prop("checked",true);
	}
	if($("#cn_18000").is(":checked")){
		$(".area_18000:visible").prop("checked",true);
	}
	if($("#cn_21000").is(":checked")){
		$(".area_21000:visible").prop("checked",true);
	}
	if($("#cn_5000").is(":checked")){
		$(".area_5000:visible").prop("checked",true);
	}
	if($("#cn_9000").is(":checked")){
		$(".area_9000:visible").prop("checked",true);
	}
	if($("#cn_19000").is(":checked")){
		$(".area_19000:visible").prop("checked",true);
	}
	if($("#cn_20000").is(":checked")){
		$(".area_20000:visible").prop("checked",true);
	}
	if($("#cn_25000").is(":checked")){
		$(".area_25000:visible").prop("checked",true);
	}
	if($("#cn_32000").is(":checked")){
		$(".area_32000:visible").prop("checked",true);
	}
	if($("#cn_14000").is(":checked")){
		$(".area_14000:visible").prop("checked",true);
	}
	if($("#cn_16000").is(":checked")){
		$(".area_16000:visible").prop("checked",true);
	}
	if($("#cn_17000").is(":checked")){
		$(".area_17000:visible").prop("checked",true);
	}
	if($("#cn_4000").is(":checked")){
		$(".area_4000:visible").prop("checked",true);
	}
	if($("#cn_8000").is(":checked")){
		$(".area_8000:visible").prop("checked",true);
	}
	if($("#cn_12000").is(":checked")){
		$(".area_12000:visible").prop("checked",true);
	}
	if($("#cn_10000").is(":checked")){
		$(".area_10000:visible").prop("checked",true);
	}
	if($("#cn_28000").is(":checked")){
		$(".area_28000:visible").prop("checked",true);
	}
	if($("#cn_29000").is(":checked")){
		$(".area_29000:visible").prop("checked",true);
	}
	if($("#cn_31000").is(":checked")){
		$(".area_31000:visible").prop("checked",true);
	}
	if($("#cn_33000").is(":checked")){
		$(".area_33000:visible").prop("checked",true);
	}
	if($("#cn_11000").is(":checked")){
		$(".area_11000:visible").prop("checked",true);
	}
	if($("#cn_23000").is(":checked")){
		$(".area_23000:visible").prop("checked",true);
	}
	if($("#cn_24000").is(":checked")){
		$(".area_24000:visible").prop("checked",true);
	}
	if($("#cn_27000").is(":checked")){
		$(".area_27000:visible").prop("checked",true);
	}
	if($("#cn_30000").is(":checked")){
		$(".area_30000:visible").prop("checked",true);
	}
	$(".item-list").hide();
}
/*获取华东地区选中个数*/
function getBNlength(){
	var length=$(".area_BN:checked").length;
	if(length==$(".area_BN").length){
		$("#ctrlregionregionBodyNorth").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyNorth").prop("checked",false);
	}	
}
/*获取东北地区选中个数*/
function getNElength(){
	var length=$(".area_NE:checked").length;
	if(length==$(".area_NE").length){
		$("#ctrlregionregionBodyNorthEast").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyNorthEast").prop("checked",false);
	}	
}
/*获取华东地区选中个数*/
function getEClength(){
	var length=$(".area_EC:checked").length;
	if(length==$(".area_EC").length){
		$("#ctrlregionregionBodyEast").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyEast").prop("checked",false);
	}	
}
/*获取华中地区选中个数*/
function getCClength(){
	var length=$(".area_CC:checked").length;
	if(length==$(".area_CC").length){
		$("#ctrlregionregionBodyMiddle").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyMiddle").prop("checked",false);
	}	
}
/*获取华南地区选中个数*/
function getSClength(){
	var length=$(".area_SC:checked").length;
	if(length==$(".area_SC").length){
		$("#ctrlregionregionBodySouth").prop("checked",true);
	}else{
		$("#ctrlregionregionBodySouth").prop("checked",false);
	}	
}
/*获取西南地区选中个数*/
function getSWlength(){
	var length=$(".area_SW:checked").length;
	if(length==$(".area_SW").length){
		$("#ctrlregionregionBodySouthWest").prop("checked",true);
	}else{
		$("#ctrlregionregionBodySouthWest").prop("checked",false);
	}	
}
/*获取西北地区选中个数*/
function getNWlength(){
	var length=$(".area_NW:checked").length;
	if(length==$(".area_NW").length){
		$("#ctrlregionregionBodyNorthWest").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyNorthWest").prop("checked",false);
	}	
}
/*获取其他地区选中个数*/
function getOTlength(){
	var length=$(".area_OT:checked").length;
	if(length==$(".area_OT").length){
		$("#ctrlregionregionBodyOther").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyOther").prop("checked",false);
	}	
}
/*获取其他国家选中个数*/
function getABlength(){
	var length=$(".area_AB:checked").length;
	if(length==$(".area_AB").length){
		$("#ctrlregionregionBodyAbroad").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyAbroad").prop("checked",false);
	}	
}
/*获取中国地区选中个数*/
function getChinalength(){
	var length=$(".area_china:checked").length;
	if(length==$(".area_china").length){
		$("#ctrlregionregionBodyChina").prop("checked",true);
	}else{
		$("#ctrlregionregionBodyChina").prop("checked",false);
	}	
}
/*获取全部地区选中个数*/
function getAllarealength(){
	var length=$(".all_area:checked").length;
	if(length==$(".all_area").length){
		$("#ctrlradioboxallRegion").prop("checked",true);
	}else{
		$("#ctrlradioboxallRegion").prop("checked",false);
	}	
}
function loadSBefore(){
	
	if($("#cn_200000:checked").length>0){
		$("#second-area-200000").children().css("background","#F4FED2");
	}
	if($("#cn_300000:checked").length>0){
		$("#second-area-300000").children().css("background","#F4FED2");
	}
	if($("#cn_34000:checked").length>0){
		$("#second-area-34000").children().css("background","#F4FED2");
	}
	if($("#cn_35000:checked").length>0){
		$("#second-area-35000").children().css("background","#F4FED2");
	}
	if($("#cn_36000:checked").length>0){
		$("#second-area-36000").children().css("background","#F4FED2");
	}
	if($("#cn_2000:checked").length>0){
		$("#second-area-2000").children().css("background","#F4FED2");
	}
	if($("#cn_33000:checked").length>0){
		$("#second-area-33000").children().css("background","#F4FED2");
	}
	if($("#cn_1000:checked").length>0){
		$("#second-area-1000").children().css("background","#F4FED2");
	}
	if($("#cn_3000:checked").length>0){
		$("#second-area-3000").children().css("background","#F4FED2");
	}
	if($(".area_13000:checked").length>0){
		$("#second-area-13000").children().css("background","#F4FED2");
	}
	if($(".area_22000:checked").length>0){
		$("#second-area-22000").children().css("background","#F4FED2");
	}
	if($(".area_26000:checked").length>0){
		$("#second-area-26000").children().css("background","#F4FED2");
	}
	if($(".area_15000:checked").length>0){
		$("#second-area-15000").children().css("background","#F4FED2");
	}
	if($(".area_18000:checked").length>0){
		$("#second-area-18000").children().css("background","#F4FED2");
	}
	if($(".area_21000:checked").length>0){
		$("#second-area-21000").children().css("background","#F4FED2");
	}
	if($(".area_5000:checked").length>0){
		$("#second-area-5000").children().css("background","#F4FED2");
	}
	if($(".area_9000:checked").length>0){
		$("#second-area-9000").children().css("background","#F4FED2");
	}
	if($(".area_19000:checked").length>0){
		$("#second-area-19000").children().css("background","#F4FED2");
	}
	if($(".area_20000:checked").length>0){
		$("#second-area-20000").children().css("background","#F4FED2");
	}
	if($(".area_25000:checked").length>0){
		$("#second-area-25000").children().css("background","#F4FED2");
	}
	if($(".area_32000:checked").length>0){
		$("#second-area-32000").children().css("background","#F4FED2");
	}
	if($(".area_14000:checked").length>0){
		$("#second-area-14000").children().css("background","#F4FED2");
	}
	if($(".area_16000:checked").length>0){
		$("#second-area-16000").children().css("background","#F4FED2");
	}
	if($(".area_17000:checked").length>0){
		$("#second-area-17000").children().css("background","#F4FED2");
	}
	if($(".area_4000:checked").length>0){
		$("#second-area-4000").children().css("background","#F4FED2");
	}
	if($(".area_8000:checked").length>0){
		$("#second-area-8000").children().css("background","#F4FED2");
	}
	if($(".area_12000:checked").length>0){
		$("#second-area-12000").children().css("background","#F4FED2");
	}
	if($(".area_10000:checked").length>0){
		$("#second-area-10000").children().css("background","#F4FED2");
	}
	if($(".area_28000:checked").length>0){
		$("#second-area-28000").children().css("background","#F4FED2");
	}
	if($(".area_29000:checked").length>0){
		$("#second-area-29000").children().css("background","#F4FED2");
	}
	if($(".area_31000:checked").length>0){
		$("#second-area-31000").children().css("background","#F4FED2");
	}
	if($(".area_33000:checked").length>0){
		$("#second-area-33000").children().css("background","#F4FED2");
	}
	if($(".area_11000:checked").length>0){
		$("#second-area-11000").children().css("background","#F4FED2");
	}
	if($(".area_23000:checked").length>0){
		$("#second-area-23000").children().css("background","#F4FED2");
	}
	if($(".area_24000:checked").length>0){
		$("#second-area-24000").children().css("background","#F4FED2");
	}
	if($(".area_27000:checked").length>0){
		$("#second-area-27000").children().css("background","#F4FED2");
	}
	if($(".area_30000:checked").length>0){
		$("#second-area-30000").children().css("background","#F4FED2");
	}
}
/*更改多选框状态*/
function setareacheck(){
//	getPtoScheck();
	getBNlength();
	getNElength();
	getEClength();
	getCClength();
	getSClength();
	getSWlength();
	getNWlength();
	getOTlength();
	getABlength();
	getChinalength();
	getAllarealength();
}