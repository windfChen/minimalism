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
			<!--
			<tr>
				<td>
					<input type="checkbox" value="1" name="">
				</td>
				<td>
					1
				</td>
				<td>
					<u style="cursor:pointer" onclick="member_show('张三','member-show.html','10001','360','400')">
						小明
					</u>
				</td>
				<td >
					男
				</td>
				<td >
					13000000000
				</td>
				<td >
					admin@mail.com
				</td>
				<td >
					北京市 海淀区
				</td>
				<td>
					2017-01-01 11:11:42
				</td>
				<td class="td-status">
					<span class="layui-btn layui-btn-normal layui-btn-mini">
						已启用
					</span>
				</td>
				<td class="td-manage">
					<a style="text-decoration:none" onclick="member_stop(this,'10001')" href="javascript:;" title="停用">
						<i class="layui-icon">&#xe601;</i>
					</a>
					<a title="编辑" href="javascript:;" onclick="member_edit('编辑','member-edit.html','4','','510')"
					class="ml-5" style="text-decoration:none">
						<i class="layui-icon">&#xe642;</i>
					</a>
					<a style="text-decoration:none"  onclick="member_password('修改密码','member-password.html','10001','600','400')"
					href="javascript:;" title="修改密码">
						<i class="layui-icon">&#xe631;</i>
					</a>
					<a title="删除" href="javascript:;" onclick="member_del(this,'1')" 
					style="text-decoration:none">
						<i class="layui-icon">&#xe640;</i>
					</a>
				</td>
			</tr>
			-->
		</tbody>
	</table>
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