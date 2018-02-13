// JavaScript Document
$(function(){
	"use strict";
	
	// >>>>>>>>>> 小搜索框
	$(".search_ico").click(function(){ // 点击放大镜时触发动作
	//alert(0);
		$(".search_sm").addClass("search_open"); 
		$(".search_sm input").focus() // 让input输入框为焦点状态
							 .blur(function(){ // 在其失去焦点时
			// 删除样式`search_open`
			$(".search_sm").removeClass("search_open"); 
		});
	});
    
	// >>>>>>>>>> 顶部下拉菜单
	$('.login').mouseover(function(){
		$('.head_dropdown').show();
	}).mouseleave(function(){
		$('.head_dropdown').hide();
	});
	
	gridEvent();
	
	
	// >>>>>>>>>> 搜索筛选
	$(".filter_link").click(function(){
		$(this).parent(".filter_item").siblings().children(".filter_link").removeClass("filter_selected");
		$(this).addClass("filter_selected");
	});
	
	
	// >>>>>>>>> 统计区域筛选
	$(".filter_link1").click(function(){
		$(this).siblings().removeClass("filter_selected");
		$(this).addClass("filter_selected");
	});
	
	
	
	
	// >>>>>>>>>> 精选图片筛选
	$(".photo-menu-rigt a").click(function(){
		$(this).parent(".filter_item").siblings().children(".filter_link").removeClass("filter_selected");
		$(this).addClass("filter_selected");
	});
	
	// >>>>>>>>>> 搜索结果树目录
	$(".tree_sub_link").click(function(){
		$(".tree_sub_link").removeClass("tree_cur");
		$(this).addClass("tree_cur");
	});
	
	// >>>>>>>>>> 作者特供左右高度对齐
	$(".special_tit").height($(".special_con").height());
	
	// >>>>>>>>>> 订单页
	$('.order').each(function(index, element) {
		var h = $(this).height();
		$(this).children('.order_price').height(h); // 两条竖线高度撑满
		$(this).find('.order_detail:last').css('border-bottom','none'); // 列表最后一行去掉下划线
	});
	
	//>>>>>>>>>> 老师介绍显示隐藏
	
	
});

function gridEvent(){
	// >>>>>>>>>> 单选多选
	//多选
	$("input[type='checkbox']").click(function(){ 
		if($(this).is(':checked')){ 
			$(this).attr("checked","checked"); 
			$(this).parent().addClass("c_on"); 
		}else{ 
			$(this).removeAttr("checked"); 
			$(this).parent().removeClass("c_on"); 
		} 
	}); 
	//单选
    $("input[type='radio']").click(function(){ 
 		$("input[name='" + $(this).attr('name') + "']").removeAttr("checked"); 
 		$(this)[0].checked = true; 
 		$(this).parent().addClass("r_on").siblings().removeClass("r_on"); 
 	});
	
	// >>>>>>>>>> 表格排序
	$(".tabl_head_rank").click(function(){
		var classname = $(this).find("i").attr("class");
		if(classname == "presspc icon-arrDown"){
			$(this).find("i").removeClass("icon-arrDown");
			$(this).find("i").addClass("icon-arrUp");
		}
		if(classname == "presspc icon-arrUp"){
			$(this).find("i").removeClass("icon-arrUp");
			$(this).find("i").addClass("icon-arrDown");
		}
	});
}

	
// >>>>>>>>>> 页面滚动
$(window).scroll(function(){
	// 设置变量top,表示当前滚动条到顶部的值
	var top = $(window).scrollTop(); 
	if (top > 430)  // 当滚动条到顶部的值大430(`head` + `ban` + `assist`)时
	{
		// 搜索区出现
		//$(".search_sm").show(); 
		
		// `返回顶部`按钮出现
		$(".totop").fadeIn(1000);
	}
	else                             
	{
		// 隐藏搜索区
		//$(".search_sm").hide();
		
		// `返回顶部`按钮消失
		$(".totop").fadeOut(1000);
	}
	
	$(".totop").click(function(){ // 点击`返回顶部`按钮时
		// 页面上滚
		$('body,html').animate({scrollTop:0},1000);
		return false;
	});
	$(".totop").mouseover(function(){ // 滑过时
		// 箭头变文字
		$(this).html("返回<br>顶部").addClass("totop_txt");
	}).mouseout(function(){ // 滑出时
		// 还原
		$(this).html('<i class="presspc icon-arrUp"></i>').removeClass("totop_txt");
	});
});	

// -------------------------------------------------------------------------------------------------
// 选项卡
// -------------------------------------------------------------------------------------------------
function setTabs(name,cursel,n){
    for(i=1;i<=n;i++){
        var menu=document.getElementById(name+i);
        var con=document.getElementById(name+"-con"+"-"+i);
        var cur = name + "-" + "cur";
        menu.className=i==cursel?cur:"";
        con.style.display=i==cursel?"block":"none";
    }
}
//合作伙伴滚动
function DY_scroll(wraper,prev,next,img,speed,or)
	{	
		var wraper = $(wraper);
		var prev = $(prev);
		var next = $(next);
		var img = $(img).find('ul');
		var w = img.find('li').outerWidth(true);
		var s = speed;
		next.click(function(){
			img.animate({'margin-left':-w},function(){
				img.find('li').eq(0).appendTo(img);
				img.css({'margin-left':0});
				});
			});
		prev.click(function(){
			img.find('li:last').prependTo(img);
			img.css({'margin-left':-w});
			img.animate({'margin-left':0});
			});
		if (or == true)
		{
			ad = setInterval(function() { next.click();},s*1000);
			wraper.hover(function(){clearInterval(ad);},function(){ad = setInterval(function() { next.click();},s*1000);});
		}
	}
	

//筛选 显示隐藏
function show_hiddendiv(){
	document.getElementById("hidden_div").style.display='block';
	document.getElementById("_strHref").href='javascript:hidden_showdiv();';
	document.getElementById("_strSpan").innerHTML="隐藏部分 ↑";
}
 function hidden_showdiv(){
 	document.getElementById("hidden_div").style.display='none';
 	document.getElementById("_strHref").href='javascript:show_hiddendiv();';
 	document.getElementById("_strSpan").innerHTML="显示全部 ↓";
 }