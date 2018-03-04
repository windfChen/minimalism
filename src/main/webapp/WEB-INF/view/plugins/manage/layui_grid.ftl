<div class="content">
	<!-- 右侧内容框架，更改从这里开始 -->
	<form class="layui-form xbs" id="grid-search-form" >
		<div class="layui-form-pane" style="text-align: center;">  
		  <div class="layui-form-item" id="grid-search" style="display: inline-block;">
		  </div>
		</div> 
	</form>
	<xblock>
		<div id="grid-menus">
		</div>
		<!--
		<span class="x-right" style="line-height:40px">共有数据：<span id=""></span>条</span>
		-->
	</xblock>
	<table class="layui-table" id="grid-table">
		<thead id="grid-thead">
		</thead>
		<tbody id="grid-data">
		</tbody>
	</table>
	<div id="grid-page" ></div>
	<!-- 右侧内容框架，更改从这里结束 -->
</div>
  <script>
	$(function(){
			
		var queryString = '${data.queryString}';
		queryString = queryString == ''? '': '?' + queryString;
		var controlerPath = '${rc.contextPath}${data.controlerPath}';
		controlerPath = controlerPath[controlerPath.length] == '/'? controlerPath: controlerPath + '/';
		window.grid = new Grid({
			gridPath: controlerPath,
			queryString: queryString
		});
		grid.init();
	})
  </script>