<div class="content">
	<!-- 右侧内容框架，更改从这里开始 -->
	<form class="layui-form xbs" action="" >
		<div class="layui-form-pane" style="text-align: center;">
		  <div class="layui-form-item" style="display: inline-block;">
			<label class="layui-form-label xbs768">日期范围</label>
			<div class="layui-input-inline xbs768">
			  <input class="layui-input" placeholder="开始日" id="LAY_demorange_s">
			</div>
			<div class="layui-input-inline xbs768">
			  <input class="layui-input" placeholder="截止日" id="LAY_demorange_e">
			</div>
			<div class="layui-input-inline">
			  <input type="text" name="username"  placeholder="请输入用户名" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-input-inline" style="width:80px">
				<button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
			</div>
		  </div>
		</div> 
	</form>
	<xblock><button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon">&#xe640;</i>批量删除</button><button class="layui-btn" onclick="member_add('添加用户','member-add.html','600','500')"><i class="layui-icon">&#xe608;</i>添加</button><span class="x-right" style="line-height:40px">共有数据：88 条</span></xblock>
	<table class="layui-table">
		<thead>
			<tr>
				<th>
					<input type="checkbox" name="" value="">
				</th>
				<th>
					ID
				</th>
				<th>
					用户名
				</th>
				<th>
					性别
				</th>
				<th>
					手机
				</th>
				<th>
					邮箱
				</th>
				<th>
					地址
				</th>
				<th>
					加入时间
				</th>
				<th>
					状态
				</th>
				<th>
					操作
				</th>
			</tr>
		</thead>
		<tbody>
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
		</tbody>
	</table>
	<!-- 右侧内容框架，更改从这里结束 -->
  </div>