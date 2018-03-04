$(function () {
    //加载弹出层
    layui.use(['form','element','laypage'],
    function() {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        element = layui.element;
    });

	// 选项卡
	var tab = {
        tabAdd: function(title,url,id){
          //新增一个Tab项
          element.tabAdd('xbs_tab', {
            title: title 
            ,content: '<div class="x-content" tab-id="' + id + '" id="tab_content_' + id + '"></div>'
            ,id: id
          })
		  
		  $.get(url, function(data) {
			  $('#tab_content_' + id).html(data);
		  });
        }
        ,tabDelete: function(othis){
          //删除指定Tab项
          element.tabDelete('xbs_tab', '44');
                    
          othis.addClass('layui-btn-disabled');
        }
        ,tabChange: function(id){
          //切换到指定Tab项
          element.tabChange('xbs_tab', id);
        }
      };
	
	
	//初如化背景
	function bgint () {
    	if(localStorage.bglist){
            var arr = JSON.parse(localStorage.bglist);// 
            // console.log(arr);
            //全局背景统一
            if(arr['bgSrc']){
                $('body').css('background-image', 'url('+arr['bgSrc']+')');
            }
    	}
    }
    bgint();
	//背景主题功能
	var changerlist = new Swiper('.changer-list', {
		initialSlide :5,
        effect: 'coverflow',
        grabCursor: true,
        centeredSlides: true,
        slidesPerView: 'auto',
        coverflow: {
            rotate: 50,
            stretch: -10,
            depth: 100,
            modifier: 1,
            slideShadows : false
        }
    });
	//背景主题设置展示
    is_show_change=true;
    $('#changer-set').click(function(event) {
    	if(is_show_change){
            $('.bg-out').show();
    		$('.bg-changer').animate({top: '0px'}, 500);
    		is_show_change=false;
    	}else{
    		$('.bg-changer').animate({top: '-110px'}, 500);
    		is_show_change=true;
    	}
    	
    });
    //背景主题切换
    $('.bg-changer img.item').click(function(event) {
    	if(!localStorage.bglist){
    		arr = {};
    	}else{
    		arr = JSON.parse(localStorage.bglist);
    	}
    	var src = $(this).attr('src');
    	$('body').css('background-image', 'url('+src+')');

    	url = location.href;
        
        // 全局背景统一
        arr['bgSrc'] = src;

    	localStorage.bglist = JSON.stringify(arr);

    });
    //背景初始化
    $('.reset').click(function  () {
        localStorage.clear();
        layer.msg('初如化成功',function(){});
    });
    //背景切换点击空白区域隐藏
	$(document).click(function(e){
	  var _con = $('.bg-changer-target');   // 设置目标区域
	  if(!_con.is(e.target) && _con.has(e.target).length === 0){
		   $('.bg-changer').animate({top: '-110px'}, 500);
           $('.bg-out').hide();
	  }
	});


    //判断是否显示左侧菜单
    $(window).resize(function(){
        width=$(this).width();
        if(width>1024){
            $('#left-nav').show();
        }
    });
    //窄屏下的左侧菜单隐藏效果
    $('.container .open-nav i').click(function(event) {
        $('.left-nav').animate({width: 'toggle'});
    });
	
    //左侧菜单效果，切换标签
	$('.left-nav #nav li').click(function (event) {

        if($(this).children('.sub-menu').length){
            if($(this).hasClass('open')){
                $(this).removeClass('open');
                $(this).find('.nav_right').html('&#xe697;');
                $(this).children('.sub-menu').stop().slideUp();
                $(this).siblings().children('.sub-menu').slideUp();
            }else{
                $(this).addClass('open');
                $(this).children('a').find('.nav_right').html('&#xe6a6;');
                $(this).children('.sub-menu').stop().slideDown();
                $(this).siblings().children('.sub-menu').stop().slideUp();
                $(this).siblings().find('.nav_right').html('&#xe697;');
                $(this).siblings().removeClass('open');
            }
        }else{

            var url = $(this).children('a').attr('_href');
            var title = $(this).find('cite').html();
            var index  = $('.left-nav #nav li').index($(this));

            for (var i = 0; i <$('.x-content').length; i++) {
                if($('.x-content').eq(i).attr('tab-id')==index+1){
                    tab.tabChange(index+1);
                    event.stopPropagation();
                    return;
                }
            };
            
            tab.tabAdd(title,url,index+1);
            tab.tabChange(index+1);
        }
        
        event.stopPropagation();
         
    })
    //初始化菜单展开样式
    $('.left-nav #nav li .opened').siblings('a').find('.nav_right').html('&#xe6a6;');

        
})

/*弹出层*/
/*
    参数解释：
    title   标题
    data     html代码
    id      需要操作的数据id
    w       弹出层宽度（缺省调默认值）
    h       弹出层高度（缺省调默认值）
*/
function x_admin_show(title,data,w,h){
    if (title == null || title == '') {
        title=false;
    };
    if (w == null || w == '') {
        w=800;
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };
    layer.open({
        type: 1,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: title,
        content: data
    });
}

/*关闭弹出框口*/
function x_admin_close(){
	layer.close(layer.index);
}
