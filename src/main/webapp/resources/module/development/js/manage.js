$(function(){
	var md = new ManageDevWindow();
	md.init();
})

function ManageDevWindow(config) {
	var defaultConfig = {
		boxId: 'manage_dev_box',
		formId: 'manage_dev_form'
	}
	this.config = $.extend(defaultConfig, config);
  
}

ManageDevWindow.prototype = {
	html: '<div id="' + this.config.boxId + '">\
			<form id="' +  + '" class="layui-form">\
				\
			</form>\
		   </div>',
	init: function() {
		if ($('#' + this.config.boxId).length == 0) {
			$('body').append(this.html);
		}
	},
	createInput: function(obj){
		var h = '\
			<div class="layui-form-item">\
			    <label class="layui-form-label">obj.labelName</label>\
			    <div class="layui-input-block">\
			      <input type="text" name="title" required placeholder="请输入' + obj.labelName + '" autocomplete="off" class="layui-input">\
			    </div>\
			</div>';

		return h;
	},
}