<div id="manage_dev_box">
	<form id="manage_dev_form" class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">*属性名称</label>
			<div class="layui-input-block">
				<input type="text" name="name" lay-verify="required"  placeholder="属性名称" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">*数据类型</label>
			<div class="layui-input-block">
				<select name="type" lay-verify="required">
					<option value=""></option>
					<option value="varchar">字符串</option>
					<option value="int">整数</option>
					<option value="text">文本</option>
					<option value="date">日期</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">*长度</label>
			<div class="layui-input-block">
				<input type="text" name="length" required lay-verify="number" placeholder="数据库字段长度" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">必填项</label>
			<div class="layui-input-block">
				<input type="checkbox" name="isNotNull" lay-skin="switch">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text" name="comment"  placeholder="代码的注释" autocomplete="off" class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
			</div>
		</div>
	</form>
</div>