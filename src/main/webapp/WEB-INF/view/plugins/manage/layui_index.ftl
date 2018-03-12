<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>后台管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/plugins/manage/plugins/layui/css/font.css" />
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/plugins/manage/plugins/layui/css/xadmin.css" />
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/common/plugins/swiper/swiper.min.css" />
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/common/plugins/swiper/swiper.jquery.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/lib/layui/layui.js"></script>
	<script type="text/javascript">
		var basePath = '${rc.contextPath}';
		var moduleCode = '${module.code}';
		var mBasePath = '${module.basePath}';
		var resourceBasePath = '${rc.contextPath}/resources/';
	</script>
	<script type="text/javascript" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/js/xadmin.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/js/grid.js"></script>


    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/module/development/css/manage.css" />
    <script type="text/javascript" src="${rc.contextPath}/resources/module/development/js/manage.js"></script>
</head>
<body>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo"><a href="./index.html">X-ADMIN V1.1</a></div>
        <div class="open-nav"><i class="iconfont">&#xe699;</i></div>
        <ul class="layui-nav right" lay-filter="">
          <li class="layui-nav-item">
            <a href="javascript:;">admin</a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
              <dd><a href="javascript:void(0)">个人信息</a></dd>
              <dd><a href="javascript:void(0)">切换帐号</a></dd>
              <dd><a href="javascript:void(0)" class="bg-changer-target" id="changer-set">切换主题</a></dd>
              <dd><a href="./login.html">退出</a></dd>
            </dl>
          </li>
          <li class="layui-nav-item"><a href="/">前台首页</a></li>
        </ul>
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
    <div class="wrapper">
        <!-- 左侧菜单开始 -->
        <div class="left-nav">
          <div id="side-nav">
            <ul id="nav">
                <li class="list" current>
                    <a _href="./index.html">
                        <i class="iconfont">&#xe761;</i>
                        欢迎页面
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                </li>
				<li class="list">
                    <a href="javascript:void(0);">
                        <i class="iconfont">&#xe70b;</i>
                        开发者管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a _href="${rc.contextPath}/test/" class="first" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                <cite>添加模型</cite>
                            </a>
                        </li>
                        <li>
                            <a _href="${rc.contextPath}/dev/module/" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                <cite>模块管理</cite>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list">
                    <a href="javascript:void(0);>
                        <i class="iconfont">&#xe70b;</i>
                        会员管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a _href="member-list.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                会员列表
                            </a>
                        </li>
                        <li>
                            <a _href="member-del.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                会员删除
                            </a>
                        </li>
                        <li>
                            <a _href="member-level.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                等级管理
                            </a>
                        </li>
                        <li>
                            <a _href="member-kiss.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                积分管理
                            </a>
                        </li>
                        <li>
                            <a _href="member-view.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                浏览记录
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:void(0);>
                        <i class="iconfont">&#xe6a3;</i>
                        分类管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a _href="./category.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                分类列表
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:void(0);>
                        <i class="iconfont">&#xe6a3;</i>
                        轮播管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu" style="display:none">
                        <li>
                            <a _href="./banner-list.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                轮播列表
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:void(0);>
                        <i class="iconfont">&#xe6a3;</i>
                        管理员管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu" style="display:none">
                        <li>
                            <a _href="./banner-list.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                轮播列表
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:void(0);>
                        <i class="iconfont">&#xe6a3;</i>
                        系统统计
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu" style="display:none">
                        <li>
                            <a _href="./echarts1.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                拆线图
                            </a>
                        </li>
                        <li>
                            <a _href="./echarts2.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                柱状图
                            </a>
                        </li>
                        <li>
                            <a _href="./echarts3.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                地图
                            </a>
                        </li>
                        <li>
                            <a _href="./echarts4.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                饼图
                            </a>
                        </li>
                        <li>
                            <a _href="./echarts5.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                k线图
                            </a>
                        </li>
                        <li>
                            <a _href="./echarts6.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                仪表图
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:void(0);>
                        <i class="iconfont">&#xe6a3;</i>
                        系统设置
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu" style="display:none">
                        <li>
                            <a _href="./banner-list.html" href="javascript:void(0);">
                                <i class="iconfont">&#xe6a7;</i>
                                轮播列表
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
          </div>
        </div>
        <!-- 左侧菜单结束 -->
        
		<!-- 右侧主体开始 -->
		<div class="page-content">
			<div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
			  <ul class="layui-tab-title">
				<li>我的桌面</li>
			  </ul>
			  <div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
				</div>
			  </div>
			</div>
		</div>
		<!-- 右侧主体结束 -->
    </div>
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright">Copyright ©2017 陈亚峰 All Rights Reserved.</div>  
    </div>
    <!-- 底部结束 -->
    <!-- 背景切换开始 -->
	<div class="bg-changer">
        <div class="swiper-container changer-list bg-changer-target">
            <div class="swiper-wrapper">
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/a.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/b.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/c.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/d.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/e.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/f.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/g.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/h.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/i.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/j.jpg" alt=""></div>
                <div class="swiper-slide"><img class="item" src="${rc.contextPath}/resources/plugins/manage/plugins/layui/images/k.jpg" alt=""></div>
                <div class="swiper-slide"><span class="reset">初始化</span></div>
            </div>
        </div>
        <div class="bg-out"></div>
    </div>
    <!-- 背景切换结束 -->
	<script>
		$(function(){
			setTimeout('$(".first").trigger("click")',100);
		})
	</script>
</body>
</html>