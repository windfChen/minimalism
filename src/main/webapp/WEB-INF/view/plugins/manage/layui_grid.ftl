<div class="content">
	<!-- 右侧内容框架，更改从这里开始 -->
	<form class="layui-form xbs" id="${RequestParameters["tabId"]}-grid-search-form" >
		<div class="layui-form-pane" style="text-align: center;">  
		  <div class="layui-form-item" id="${RequestParameters["tabId"]}-grid-search" style="display: inline-block;">
		  </div>
		</div> 
	</form>
	<xblock>
		<span id="${RequestParameters["tabId"]}-grid-menus"></span>
		<span class="x-right" style="line-height:40px">共有数据：<span id="${RequestParameters["tabId"]}-grid-page-count"></span>条</span>
	</xblock>
	<table class="layui-table" id="${RequestParameters["tabId"]}-grid-table">
		<thead id="${RequestParameters["tabId"]}-grid-thead">
		</thead>
		<tbody id="${RequestParameters["tabId"]}-grid-data">
		</tbody>
	</table>
	<div id="${RequestParameters["tabId"]}-grid-page" ></div>
	<!-- 右侧内容框架，更改从这里结束 -->
</div>
  <script>
	$(function(){
		var queryString = '${data.queryString}';
		queryString = queryString == ''? '': '?' + queryString;
		var controlerPath = '${rc.contextPath}${data.controlerPath}';
		controlerPath = controlerPath[controlerPath.length] == '/'? controlerPath: controlerPath + '/';
		window.grid${RequestParameters["tabId"]} = new Grid({
			gridPath: controlerPath,
			queryString: queryString
		},'${RequestParameters["tabId"]}');
		grid${RequestParameters["tabId"]}.init();
	})
  </script>