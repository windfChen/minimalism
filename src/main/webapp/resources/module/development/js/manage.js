$(function(){
	//var md = new ManageDevelopmentControler();
	//md.init();
})

function ManageDevelopmentControler(config) {
	var defaultConfig = {
		boxId: 'manage_dev_box',
		formId: 'manage_dev_form',
		queryString:'',
		parentDiv:'.x-content',
		basePath: basePath,
	}
	this.config = $.extend(defaultConfig, config);
  
}

ManageDevelopmentControler.prototype = {
	init: function() {
		var obj = this;
		 $.get(obj.config.basePath + '/dev/manage/' +'' , function(data) {
			  $(obj.config.parentDiv).append(data);
			   // 重新渲染表单
			   form.render();
		  });
	},
}