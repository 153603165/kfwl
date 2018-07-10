function closes(){
	$("#Loading").fadeOut("normal",function(){
	$(this).remove();
	});
}
	var pc;
	$.parser.onComplete = function(){
	if(pc) clearTimeout(pc);
	pc = setTimeout(closes, 600);
}

$(function () {
	//自定义验证
	$.extend($.fn.validatebox.defaults.rules, {
		phoneRex: {
			validator: function(value){
			////手机
			////var rex=/^1[3-8]\d{9}$/;
			////固话
			var rex2=/^\(?0\d{2}\)?[- ]?\d{8}$|^0\d{2}[- ]?\d{8}$|^\(?0\d{3}\)?[- ]?\d{8}$|^0\d{3}[- ]?\d{8}$|^[1-9]\d{7}$/;
			///企业
			var rex3 = /^400[- ]?\d{4}[- ]?\d{3}$|^800[- ]?\d{4}[- ]?\d{3}$/;
			if(rex2.test(value)||rex3.test(value))
				{
				  // alert('t'+value);
				  return true;
				}else
				{
				 //alert('false '+value);
				   return false;
				}
			  
			},
			message: '请输入正确电话号码'
		},
		cellphoneRex: {
			validator: function(value){
				////手机
				var cellRex=/^1[3-8]\d{9}$/;
				if(cellRex.test(value)){
					return true;
				}else return false;
			},
			message: '请输入正确的11位手机号码'
		},
		zipcodeRex: {
			validator: function(value){
				////手机
				var zipcode=/^[1-9]\d{5}$/;
				if(zipcode.test(value)){
					return true;
				}else return false;
			},
			message: '请输入正确的邮编号码'
		}		
	});
	
    //在右边center区域打开菜单，新增tab
    function Open(identity,text, url) {
        if ($("#tabs").tabs('exists', text)) {
            $('#tabs').tabs('select', text);
            if (url) {
               //loadHtml(text,url);
            };
        } else {
        	closeallbox(identity);
            $('#tabs').tabs('add', {
                title : text,
                closable : true,
                // content: '<iframe src="' + 'https://www.baidu.com' + '" frameborder=0 height=100% width=100% scrolling=yes></iframe>'
                content:'<div id='+text+' style="padding:10px"></div>'
            });
            if (url) {
                loadHtml(text,url);
            };
        }
    }
    function loadHtml(text,url){
        $("#"+text).load(url);
    }
    //绑定tabs的右键菜单
    $("#tabs").tabs({
        onContextMenu : function (e, title) {
            e.preventDefault();
            $('#tabsMenu').menu('show', {
                left : e.pageX,
                top : e.pageY
            }).data("tabTitle", title);
        }
    });
    
    //实例化menu的onClick事件
    $("#tabsMenu").menu({
        onClick : function (item) {
            CloseTab(this, item.name);
        }
    });
    
    //几个关闭事件的实现
    function CloseTab(menu, type) {
        var curTabTitle = $(menu).data("tabTitle");
        var tabs = $("#tabs");
        
        if (type === "close") {
            tabs.tabs("close", curTabTitle);
            return;
        }
        
        var allTabs = tabs.tabs("tabs");
        var closeTabsTitle = [];
        
        $.each(allTabs, function () {
            var opt = $(this).panel("options");
            if (opt.closable && opt.title != curTabTitle && type === "Other") {
                closeTabsTitle.push(opt.title);
            } else if (opt.closable && type === "All") {
                closeTabsTitle.push(opt.title);
            }
        });
        
        for (var i = 0; i < closeTabsTitle.length; i++) {
            tabs.tabs("close", closeTabsTitle[i]);
        }
    }
    $(".my-menu li:not(.exclusive)").click(function(){
    	$(".li-menu-selected").removeClass("li-menu-selected");
    	$(this).addClass("li-menu-selected");
    	text = $(this).attr("name");//用li的name来保存text
    	identity = $(this).attr("id");//用li的name来保存text
    	locate = $(this).children('span').attr("name");//用li下的span的name来保存url
		Open(identity,text,locate);
    })
	////修改密码点击事件
	$('#changePassword').click(function(){
		$('#account_change_passwordWin').window({
			left:($(window).width()-250)/2,
			top:($(window).height()-350)/2,			
			width:250,
			height:350,
			modal:true
		});
		$('#account_change_passwordWin').show();
		$('#account_change_passwordWin').window('open');
	})

	//修改密码提交按钮
	$('#account_changePassord_updateBtn').unbind('click').click(function(){
		$.ajax({
			type: "GET",
			url: rootPath+"changePassword",
			data: $('#account_change_passworForm').serialize(),
			dataType: "json",
			beforeSend: function() {
				if($('#oldPassword').val()=="" || $('#newPassword').val()=="" || $('#comfirmPassword').val()==""){
					alert('请输入密码');
					$('#account_change_passworForm').form('clear');
					return false;
				}else if($('#newPassword').textbox('getValue')!=$('#comfirmPassword').textbox('getValue')){
					alert('两次输入的新密码不一致！请重新输入');
					return false;
				}
			},
			success: function(data){
				if(data.account_update_password_result=="wrongOldPwd"){
					alert('原密码不正确，请重新输入');
					$('#account_change_passworForm').form('clear');
					return false;
				} else if(data.account_update_password_result=="success"){
					$('#account_change_passwordWin').window('close');
					alert("修改密码成功，请重新登录!");
					window.location.reload();
				}else {
					alert("修改密码失败，请联系管理员，以便解决");
				}
	        },error: function(){
	        	alert('无法与主机通讯，请联系管理员');
	        }
		});
	});		
	//修改密码取消按钮
	$('#account_changePassord_cancelBtn').unbind('click').click(function(){
 		$('#account_change_passwordWin').window('close');
	})
//密码验证狂	
	$.extend($.fn.validatebox.defaults.rules, {    
		equals: {
        	validator: function(value,param){
				return value == $(param[0]).val();
        	},
			message: '两次输入的新密码不相符！'   
		}    
	});
	/**
	 * 修改个人信息
	 */
	$('#accountInfo').click(function(){
		$('#account_change_owninfo').window({
			left:($(window).width()-300)/2,
			top:($(window).height()-400)/2,			
			width:300,
			height:400,
			modal:true
		});
		$('#accountinfo_contacter').validatebox({
			validType:'cellphoneRex'
		});
		$('#accountinfo_companyPhone').validatebox({
			validType:'phoneRex'
		});
		$('#accountinfo_email').validatebox({
			validType:'email'
		});
		$('#accountinfo_companyWebsite').validatebox({
			validType:'url'
		});
		$('#accountinfo_zipCode').validatebox({
			validType:'zipcodeRex'
		});		
		$('#account_change_owninfo').show();
		$("#account_change_owninfoForm").form("clear");
		$('#account_change_owninfo').window('open');
		getaccount_change_Info();
	});
});
/**
 * 获取个人信息
 */
function getaccount_change_Info(){
	$.post(rootPath+"getOwnAccountInfo", {"para":Math.random()}, 
 		function(data) {
		$("#accountinfo_accountName").html(data.accountName);
		$("#accountinfo_email").textbox('setValue',data.email);
		$("#accountinfo_companyName").textbox('setValue',data.companyName);
		$("#accountinfo_companyWebsite").textbox('setValue',data.companyWebsite);
		$("#accountinfo_companyPhone").textbox('setValue',data.companyPhone);
		$("#accountinfo_address").textbox('setValue',data.address);
		$("#accountinfo_contacter").textbox('setValue',data.contacter);
		$("#accountinfo_contactPhone").textbox('setValue',data.contactPhone);
		$("#accountinfo_zipCode").textbox('setValue',data.zipCode);
	});
}
/**
 * 取消修改个人信息
 */
function account_changeowninfo_cancelBtn(){
	$('#account_change_owninfo').window('close');
}
/**
 * 确认修改个人信息
 */
function account_changeowninfo_updateBtn(){
	var accountName=$("#accountinfo_accountName").html();
	var email=$("#accountinfo_email").textbox('getValue');
	var companyName=$("#accountinfo_companyName").textbox('getValue');
	var companyWebsite=$("#accountinfo_companyWebsite").textbox('getValue');
	var companyPhone=$("#accountinfo_companyPhone").textbox('getValue');
	var address=$("#accountinfo_address").textbox('getValue');
	var contacter=$("#accountinfo_contacter").textbox('getValue');
	var contactPhone=$("#accountinfo_contactPhone").textbox('getValue');
	var zipCode=$("#accountinfo_zipCode").textbox('getValue');
	if(!$('#accountinfo_companyPhone').validatebox('isValid')){
		alert('请输入正确的电话号码');
		return false;
	}else if(!$('#accountinfo_contactPhone').validatebox('isValid')){
		alert('请输入正确的手机号码');
		return false;	
	}else if(!$('#accountinfo_email').validatebox('isValid')){
		alert('请输入正确的邮箱地址');
		return false;	
	}else if(!$('#accountinfo_companyWebsite').validatebox('isValid')){
		alert('请输入正确的网站地址');
		return false;	
	}else if(!$('#accountinfo_zipCode').validatebox('isValid')){
		alert('请输入正确的邮编号码');
		return false;	
	}	
	$.post(rootPath+"updateOwnAccountInfo", {"para":Math.random(),"accountName":accountName,"email":email,"companyName":companyName,
		"companyWebsite":companyWebsite,"companyPhone":companyPhone,"address":address,"contacter":contacter,"contactPhone":contactPhone,
		"zipCode":zipCode}, 
 		function(data) {
			if(data=="true"){
				$('#account_change_owninfo').window('close');
				alert("修改成功!");
			}else{
				alert("修改失败");
		}
	});
}
/**
 * 日期校验
 * @param datafrom
 * @param datato
 */
function dataRex(data_from,data_to){
	data_from=data_from.replace(/\-/gi,"/");
	data_to=data_to.replace(/\-/gi,"/");
	var d_from=new Date(data_from).getTime();
	var d_to=new Date(data_to).getTime();	
	if(d_from<1000650000000){
		alert("查询起始时间不能为空或早于20010917");
		return false;
	}
	if(d_from<1225468800000){
		alert("展现数据提供的起始日期不能早于20081031、包括当天");
		return false;
	}
	if(d_from>d_to){  
		alert("报告的终止时间早于起始时间");
		return false;
	}
	if((d_to-d_from)>31622400000){
		alert("查询时间不能超过367天");
		return false;
	}
	if(d_to>new Date().getTime()){
		alert("查询时间不能超过今天");
		return false;
	}
	return true;
}
/**
 * keyword日期校验
 * @param datafrom
 * @param datato
 */
function keyworddataRex(data_from,data_to){
	data_from=data_from.replace(/\-/gi,"/");
	data_to=data_to.replace(/\-/gi,"/");
	var d_from=new Date(data_from).getTime();
	var d_to=new Date(data_to).getTime();	
	if(d_from<1259596800000){
		alert("查询起始时间不能为空或早于20091201");
		return false;
	}
	if(d_from>d_to){
		alert("报告的终止时间早于起始时间");
		return false;
	}
	if((d_to-d_from)>31622400000){
		alert("查询时间不能超过 367");
		return false;
	}
	if(d_to>new Date().getTime()){
		alert("查询时间不能超过今天");
		return false;
	}
	return true;
}
/**
 * 关闭所有box
 */
function closeallbox(title){
	$("."+title).parent().html("");
}