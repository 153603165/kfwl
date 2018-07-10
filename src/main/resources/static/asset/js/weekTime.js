$(function(){
	////时间轴位置调整
	$('#hourDisplay').css('margin-left',$("#campaign_schedule_updateWin").parent().position().left+98);
//	$(window).resize(function(){
//		$('#hourDisplay').css('margin-left',$("#campaign_schedule_updateWin").parent().offset().left+100);
//	});
	$('#timeToolTip').css('border-radius',10).hide();
	var timeCell =$("td[class^='day'],td[class^='time']");//时间选择框
	var allRowCheckbox = $("input[id$='Checkbox']");//所有行的复选框
	var allColCheckbox = $("input[id^='timeCheckbox']");//所有列的复选框
//	var allRows = $("td[id^='day']");//所有行
//	var allCols = $("td[id^='time']");//所有行
	var positionX;//点中的X坐标
	var positionY;//点中的Y坐标
	var clickCell;//点中的时间框
	var unSelectedCell = "";

	/**
	 * 获取XY坐标eg:day*,time*
	 * @param {Object} cell
	 */
	function getXY(cell){
		var temp = cell.attr('class').split(" ");
		positionX = temp[0];
		positionY = temp[1];
	}
	/**
	 * 获取选中的时间框
	 */
	function getSelectedCell(){
		clickCell = $('.'+positionX+'.'+positionY);
	}
	/**
	 * 选择全部时间框
	 */
	function selectAll(){
		////将全选按钮的复选框选中
		setCheckboxStatus($('#selectAll'),true);
		////现将所有星期的复选框选中
		setCheckboxStatus($('#MondayCheckbox'),true);
		setCheckboxStatus($('#TuesdayCheckbox'),true);
		setCheckboxStatus($('#WednesdayCheckbox'),true);
		setCheckboxStatus($('#ThursdayCheckbox'),true);
		setCheckboxStatus($('#FridayCheckbox'),true);
		setCheckboxStatus($('#SaturdayCheckbox'),true);
		setCheckboxStatus($('#SundayCheckbox'),true);
		////将所有的时间列的复选框选中
		allColCheckbox.each(function(){
			setCheckboxStatus($(this),true);
		})
		////高亮显示所有时间框
		timeCell.each(function(){
			$(this).addClass('cell_selected');
		})
	}
	function unSelectAll(){
		////将全选按钮的复选框取消选中
		setCheckboxStatus($('#selectAll'),false);
		////现将所有星期的复选框选中
		setCheckboxStatus($('#MondayCheckbox'),false);
		setCheckboxStatus($('#TuesdayCheckbox'),false);
		setCheckboxStatus($('#WednesdayCheckbox'),false);
		setCheckboxStatus($('#ThursdayCheckbox'),false);
		setCheckboxStatus($('#FridayCheckbox'),false);
		setCheckboxStatus($('#SaturdayCheckbox'),false);
		setCheckboxStatus($('#SundayCheckbox'),false);
		////将所有的时间列的复选框取消选中
		allColCheckbox.each(function(){
			setCheckboxStatus($(this),false);
		})
		////清除所有时间框的高亮显示
		timeCell.each(function(){
			$(this).removeClass('cell_selected');
		})		
	}
	/**
	 * 修改复选框的状态
	 * @param {Object} id
	 * @param {Object} status
	 */
	function setCheckboxStatus(item,status){
		item.prop("checked",status);
		item.attr("checked",status);
	}
	/**
	 * 整行是否都被选中
	 * @param {Object} rowIndex
	 */
	function isRowSelected(rowIndex){
		var flag = true;//假设行内全部元素都选中
		$('.'+rowIndex).each(function(){
			if(!$(this).hasClass('cell_selected')){
				flag = false;//并不是全部都选中
				return false;//跳出循环
			}
		})
		return flag;
	}
	/**
	 * 整列是否都被选中
	 * @param {Object} ColIndeY
	 */
	function isColumnSelected(colIndeY){
		var flag = true;//假设列内全部元素都选中
		$('.'+colIndeY).each(function(){
			if(!$(this).hasClass('cell_selected')){
				flag = false;//并不是全部都选中
				return false;//跳出循环
			}
		})
		return flag;		
	}
	/**
	 * 是否全部单元格都选中了
	 */
	function isAllSelected(){
		var flag = true;//假设行，列全部复选框都选中
		timeCell.each(function(){
			if(!$(this).hasClass('cell_selected')){//一旦有一个没有选中
				flag = false;
				return false;//退出循环
			}
		})
		return flag;
	}
	/**
	 * 通过X坐标获取行复选框
	 * @param {Object} x
	 */
	function getCheckboxByPositionX(x){
		var item;
		switch(x){
		case 'day1':
			item = $('#MondayCheckbox');
			break;
		case 'day2':
			item = $('#TuesdayCheckbox');
			break;		
		case 'day3':
			item = $('#WednesdayCheckbox');
			break;
		case 'day4':
			item = $('#ThursdayCheckbox');
			break;
		case 'day5':
			item = $('#FridayCheckbox');
			break;
		case 'day6':
			item = $('#SaturdayCheckbox');
			break;
		case 'day7':
			item = $('#SundayCheckbox');
			break;
		default :
			break;
		}
		return item;
	}
	/**
	 * 通过Y坐标获取列复选框
	 * @param {Object} y
	 */
	function getCheckboxByPositionY(y){
		var item;
		item = $('#'+y+' input');
		return item;
	}
	/**
	 * 获取X轴星期
	 */
	function getWeekDay(){
		var num =positionX;
			weekDay=""
			for(i=0;i<num.length;i++){
				if("0123456789".indexOf(num.substr(i,1))>-1)
				weekDay+=num.substr(i,1)
			}
		return weekDay;		
	}
	/**
	 * 获取Y轴时间
	 */
	function getTime(){
		var num =positionY;
			time=""
			for(i=0;i<num.length;i++){
				if("0123456789".indexOf(num.substr(i,1))>-1)
				time+=num.substr(i,1)
			}
		return time-1;
	}
	/**
	 * 取消特定时间框的选中
	 * @param {Object} x
	 * @param {Object} y
	 */
	function unClickCell(x,y){
		$('.'+x+'.'+y).removeClass('cell_selected');
	}	
	/**
	 * 选择某一天
	 * @param {Object} today
	 */
	function clickDay(today){
		if(today.children().attr('checked') == 'checked'){//如果这一天是选中状态，则取消选中
			setCheckboxStatus(today.children(),false);
			today.siblings().each(function(){
				if($(this).hasClass('cell_selected')){//有高亮的时间框，全部取消
					$(this).removeClass('cell_selected');
				}
				allColCheckbox.each(function(){//所有列的复选框取消选中
					setCheckboxStatus($(this),false);
				})				
			})
			setCheckboxStatus($('#selectAll'),false);////取消全选按钮的选中状态
		}else {//如果这一天是未选中状态，则全部选中
			setCheckboxStatus(today.children(),true);
			today.siblings().each(function(){
				if(!$(this).hasClass('cell_selected')){//没有高亮的时间框，全部高亮
					$(this).addClass('cell_selected');
				}
				getXY($(this));//获取X,Y的值
				if(isColumnSelected(positionY)){//如果列元素全部选中，则选中列的复选框
					setCheckboxStatus($("th[id$='"+positionY+"']>input"),true);
				}				
			})
			if(isAllSelected()){//如果全部选中，则选中全选框
				setCheckboxStatus($('#selectAll'),true);
			}			
		}				
	}
	/**
	 * 获取所有未选中的时间框
	 */
	function getUnselected(){
		unSelectedCell = "";//重置内容
		timeCell.each(function(){
			if(!$(this).hasClass('cell_selected')){//没有高亮，即未选中
				getXY($(this))//获取星期和时间的坐标
				unSelectedCell +=(getWeekDay()+'@'+getTime()+':');
			}
		})
		////console.log(unSelectedCell);
	}
	/**
	 * 初始化
	 */
	function init(){
		selectAll();		
	}
	init();
	/**
	 * 时间框的hover事件
	 */
	timeCell.hover(function(e){
		e = e || window.event;
		getXY($(this));
		var now = getTime();
		var to = now+1;
		if(!$(this).hasClass('cell_selected')){//没有选中才有over效果
			$(this).addClass('over');
		}
		$('#timeToolTip').css('left',e.pageX-$("#campaign_schedule_updateWin").parent().position().left+10).css('top',e.pageY-$("#campaign_schedule_updateWin").parent().position().top+10).show().text(now+":00"+"--"+to+":00");
		//$('#timeToolTip').css('left',e.pageX-$("#campaign_schedule_updateWin").position().left+10).css('top',e.pageY-$("#campaign_schedule_updateWin").position().top+10).show().text($("#campaign_schedule_updateWin").parent().position().left+"--"+$("#campaign_schedule_updateWin").parent().position().top);
	},function(){
		$(this).removeClass('over');
		$('#timeToolTip').hide();
	})
	/**
	 * 时间框的点击事件
	 */
	timeCell.unbind('click').click(function(){
		$(this).removeClass('over');//移除over效果
		getXY($(this));
		////console.log('X->'+positionX+'  Y->'+positionY);
		//getSelectedCell();//获取选中的单元格,给clickCell赋值
		if($(this).hasClass('cell_selected')){
			////console.log('取消选中');
			$(this).removeClass('cell_selected');
		}else{
			////console.log('选中');
			$(this).addClass('cell_selected');			
		}
		if(isRowSelected(positionX)){//如果整行选中，则复选框选中
			setCheckboxStatus(getCheckboxByPositionX(positionX),true);
		}else{//如果整行并没有选中，则复选框取消选中
			setCheckboxStatus(getCheckboxByPositionX(positionX),false);
		}
		if(isColumnSelected(positionY)){//如果整列选中，则复选框选中
			setCheckboxStatus(getCheckboxByPositionY(positionY),true);
		}else{//如果整列并没有选中，则复选框取消选中
			setCheckboxStatus(getCheckboxByPositionY(positionY),false);
		}
		if(isAllSelected()){//如果全部选中，则选中全选框
			setCheckboxStatus($('#selectAll'),true);
		}else{
			setCheckboxStatus($('#selectAll'),false);
		}
		
	})
	/**
	 * 周一点击事件
	 */
	$('#MondayCheckbox').unbind('click').click(function(){
		clickDay($('#Monday'));
	})
	/**
	 * 周二点击事件
	 */
	$('#TuesdayCheckbox').unbind('click').click(function(){
		clickDay($('#Tuesday'));
	})
	/**
	 * 周三点击事件
	 */
	$('#WednesdayCheckbox').unbind('click').click(function(){
		////console.log('wednesday!!');
		clickDay($('#Wednesday'));
	})
	/**
	 * 周四点击事件
	 */
	$('#ThursdayCheckbox').unbind('click').click(function(){
		clickDay($('#Thursday'));
	})
	/**
	 * 周五点击事件
	 */
	$('#FridayCheckbox').unbind('click').click(function(){
		clickDay($('#Friday'));
	})
	/**
	 * 周六点击事件
	 */
	$('#SaturdayCheckbox').unbind('click').click(function(){
		clickDay($('#Saturday'));
	})
	/**
	 * 周日点击事件
	 */
	$('#SundayCheckbox').unbind('click').click(function(){
		clickDay($('#Sunday'));
	})
	/**
	 * 列复选框点击事件
	 */
	$("input[id^='timeCheckbox']").unbind('click').click(function(){
		var colIndex = $(this).parent().attr('id');
		if($(this).attr('checked')=='checked'){//如果列是选中状态，则取消选中
			setCheckboxStatus($(this),false);
			$('.'+colIndex).each(function(){
				if($(this).hasClass('cell_selected')){//有高亮的时间框，全部取消
					$(this).removeClass('cell_selected');
				}				
			})
			allRowCheckbox.each(function(){
				setCheckboxStatus($(this),false);//所有行的复选框选中
			})
			setCheckboxStatus($('#selectAll'),false);////取消全选按钮的选中状态
		}else {//如果列是未选中状态，则全部选中
			setCheckboxStatus($(this),true);
			$('.'+colIndex).each(function(){
				if(!$(this).hasClass('cell_selected')){//没有高亮的时间框，全部高亮
					$(this).addClass('cell_selected');
				}
				getXY($(this));//获取X,Y的值
				if(isRowSelected(positionX)){//如果行元素全部选中，则选中h行的复选框
					setCheckboxStatus($("tr[id$='"+positionX+"']>td>input"),true);
				}				
			})
			if(isAllSelected()){
				setCheckboxStatus($('#selectAll'),true);
			}
		}
	})
	/**
	 * 全选按钮点击事件
	 */
	$('#selectAll').unbind('click').click(function(){
		if($(this).attr('checked')=='checked'){//如果列是选中状态，则取消选中
			unSelectAll();	
		}else {//没有高亮的时间框，全部高亮
			selectAll();		
		}
	})
	$('#allTime').unbind('click').click(function(){
		selectAll();
	})
	$('#weekday').unbind('click').click(function(){
		unSelectAll();
		clickDay($('#Monday'));
		clickDay($('#Tuesday'));
		clickDay($('#Wednesday'));
		clickDay($('#Thursday'));
		clickDay($('#Friday'));
	})
	$('#weekend').unbind('click').click(function(){
		unSelectAll();
		clickDay($('#Saturday'));
		clickDay($('#Sunday'));
	})	
	$('#reset').unbind('click').click(function(){
		unSelectAll();
	})	
})
/**
 * 选择全部时间框
 */
function selectAllCells(){
	////将全选按钮的复选框选中
	setCheckboxStatus($('#selectAll'),true);
	////现将所有星期的复选框选中
	setCheckboxStatus($('#MondayCheckbox'),true);
	setCheckboxStatus($('#TuesdayCheckbox'),true);
	setCheckboxStatus($('#WednesdayCheckbox'),true);
	setCheckboxStatus($('#ThursdayCheckbox'),true);
	setCheckboxStatus($('#FridayCheckbox'),true);
	setCheckboxStatus($('#SaturdayCheckbox'),true);
	setCheckboxStatus($('#SundayCheckbox'),true);
	////将所有的时间列的复选框选中
	$("input[id^='timeCheckbox']").each(function(){
		setCheckboxStatus($(this),true);
	})
	////高亮显示所有时间框
	$("td[class^='day'],td[class^='time']").each(function(){
		$(this).addClass('cell_selected');
	})
}
var x;
var y;
/**
 * 修改复选框的状态
 * @param {Object} id
 * @param {Object} status
 */
function setCheckboxStatus(item,status){
	item.prop("checked",status);
	item.attr("checked",status);
}
/**
 * 获取XY坐标eg:day*,time*
 * @param {Object} cell
 */
function getXY(cell){
	var temp = cell.attr('class').split(" ");
	x = temp[0];
	y = temp[1];
}
/**
 * 获取X轴星期
 */
function getWeekDay(){
	var num =x;
		weekDay=""
		for(i=0;i<num.length;i++){
			if("0123456789".indexOf(num.substr(i,1))>-1)
			weekDay+=num.substr(i,1)
		}
	return weekDay;		
}
/**
 * 获取Y轴时间
 */
function getTime(){
	var num =y;
		time=""
		for(i=0;i<num.length;i++){
			if("0123456789".indexOf(num.substr(i,1))>-1)
			time+=num.substr(i,1)
		}
	return time-1;
}
/**
 * 获取所有未选中的时间框
 */
function getUnselected(){
	var unSelectedCell = "";//重置内容
	$("td[class^='day'],td[class^='time']").each(function(){
		if(!$(this).hasClass('cell_selected')){//没有高亮，即未选中
			getXY($(this))//获取星期和时间的坐标
			unSelectedCell +=(getWeekDay()+'@'+getTime()+':');
		}
	})
	return unSelectedCell;
}
/**
 * 选择全部时间框
 */
function selectAll(){
	////console.log('select All');
	////将全选按钮的复选框选中
	setCheckboxStatus($('#selectAll'),true);
	////现将所有星期的复选框选中
	setCheckboxStatus($('#MondayCheckbox'),true);
	setCheckboxStatus($('#TuesdayCheckbox'),true);
	setCheckboxStatus($('#WednesdayCheckbox'),true);
	setCheckboxStatus($('#ThursdayCheckbox'),true);
	setCheckboxStatus($('#FridayCheckbox'),true);
	setCheckboxStatus($('#SaturdayCheckbox'),true);
	setCheckboxStatus($('#SundayCheckbox'),true);
	////将所有的时间列的复选框选中
	$("input[id^='timeCheckbox']").each(function(){
		setCheckboxStatus($(this),true);
	})
	////高亮显示所有时间框
	$("td[class^='day'],td[class^='time']").each(function(){
		$(this).addClass('cell_selected');
	})
}
/**
 * 通过Y坐标获取列复选框
 * @param {Object} y
 */
function getCheckboxByPositionY(y){
	var item;
	item = $('#'+y+' input');
	return item;
}