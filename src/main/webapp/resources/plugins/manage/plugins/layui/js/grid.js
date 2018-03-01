String.prototype.startsWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
}

function Grid(config) {	
	var defaultConfig = {
		gridPath:'',
		queryString:'',
		tableDiv : '#grid-table',
		titleDiv : '#title',
		tableTitleDiv : '#grid-thead',
		tableBodyDiv : '#grid-data',
		tableSelectAllInput : '#select_all_input',
		tableSelectInput : '.grid_id',
		pageDiv : '#page',
		tableForm : '#form',
		menusDiv : '#grid-menus',
		tableTitles : undefined,
		tableTrHtml : undefined,
		tableTdHtmls : [],
		listPage : '#listPage',
		savePage : '#savePage',
		userSavePage : false,
		saveTdHtmls : [],
		searchDiv : '#table_search',
		detailOnchange : false
	}
	
	this.config = $.extend(defaultConfig, config);
	
	this.gridConfig = {};
	
	this.tableTitleHTML = '';
	
	this.pageNum = 1;
	this.pageSize = 10;
	
	this.submiting = false;
	this.dataColumnWidth = [];
	this.idColunmWidth = 0;
	
	window.load = function (obj) {
		return function(pageNum,searchData){
			obj.load(pageNum,searchData);
		}
	}(this);
}
Grid.prototype = {
	
	init : function () {
		var obj = this;
		$.getJSON(obj.config.gridPath + 'grid.json' + obj.config.queryString, function(gridConfig){
			obj.gridConfig = gridConfig.data;
			obj.initGrid();
			obj.load();
		})
	},
	
	initGrid : function () {
		// 初始化标题 TODO 没有标题栏
		$(this.config.titleDiv).html(this.gridConfig.title);
		
		// 计算宽度 TODO 需要新的方案
				
		this.initTitle();
		this.initMenu();
		this.initSearch();
	},
	
	initSearch : function () {
		
	},

	searchBtn : function () {
	},

	initTitle : function () {

		// 初始化表头
		if (!this.config.tableTitles) {	// 如果是第一次加载，需要构建
			// 行开始
			var tableTitle = '<tr>';
			// 复选框
			tableTitle += '<th><input type="checkbox" name="" value=""></th>';
			// 数据项
			for (var i = 0; i < this.gridConfig.columns.length; i++) {
				var c = this.gridConfig.columns[i];
				
				if (c.canList) {
					tableTitle += '<th>' + c.name + '</th>';
				}
			}
			// 操作列
			tableTitle += '<th>操作</th>';
			// 行结束
			tableTitle += '</tr>';
			// 缓存头部
			this.config.tableTitles = tableTitle;
		}
		$(this.config.tableTitleDiv).html(this.config.tableTitles);
		// 点击事件 TODO 现在无效，需要修改checkbox样式
		var obj = this;
		$(this.config.tableSelectAllInput).click(function(){
			if ($(this).is(":checked")) {
				$(obj.config.tableSelectInput).each(function(){
					$(this).attr('checked', 'true');
					$(this).parent().addClass('c_on');
				});
			} else {
				$(obj.config.tableSelectInput).each(function(){
					$(obj.config.tableSelectInput).removeAttr("checked");
					$(this).parent().removeClass('c_on');
				});
			}
		});
	},
	
	initMenu : function () {
		var obj = this;
		
		// 添加删除按钮
		if (this.gridConfig.canAdd) {
			$(obj.config.menusDiv).append('<button class="layui-btn" onclick="member_add()"><i class="layui-icon">&#xe608;</i>添加</button>');
		}
		if (this.gridConfig.canDelete) {
			$(obj.config.menusDiv).append('<button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon">&#xe640;</i>批量删除</button>');
		}
		
		for (var i = 0; i < this.gridConfig.menus.length; i++) {
			var menu = this.gridConfig.menus[i];
			
			// 根据名称获取按钮样式 TODO
			var menuStyleClass = obj._btnStyle(menu.name);
			// 生成按钮
			var a = '<button class="layui-btn layui-btn-danger" id="menu_' + i + '"><i class="layui-icon">&#xe640;</i>' + menu.name + '</button>';
			$(obj.config.menusDiv).append(a);
			// 绑定按钮事件
			$('#menu_' + i).click(getMenuOption(menu));
			
			function getMenuOption(menu) {
				return function () {
					var ids = obj.getSelections();
					
					// 关于选择的提醒
					if (menu.canSelect) {
						if (ids.length == 0) {
							if (menu.isSingleSelect) {
								alert('请选择一条记录');
								return;
							} else {
								alert('请至少选择一条记录');
								return;
							}
						}
						
						if (ids.indexOf(',') > 0 && menu.isSingleSelect) {
							alert('只能选择一条记录');
							return;
						}
					}
					
					// TODO 弹窗字段
					
					var menuFunciotn = function(){
						if (menu.actionAddress) {
							var actionAddress = menu.actionAddress;
							if (actionAddress.indexOf('${queryString}') > 0) {
								actionAddress = actionAddress.replace('${queryString}', queryString);
							}
							if (actionAddress.startsWith("/")) {
								actionAddress = basePath + actionAddress;
							}
							if (menu.checkColumn) {
								var data = ids;
								
								if (menu.ajaxReturn) {
									// TODO 等待框
									$.ajax({
										url: actionAddress,
										data: {
											ids: data
										},
										method: 'post',
										dataType : 'json',
										waitMsg: '处理中，请稍候...',
										success: function (json) {
											
											// TODO 自定义ajax处理方法
											
											if (json.success == 'Y') {
												alert((json.tip && json.tip != 'success'? json.tip: '操作成功！'), function(){
													obj.load();
												});
											} else {
												alert(json.tip, function(){
													obj.load();
												});
											}
											
										}
									});
								} else {
									// TODO 非ajax提交数据
								}
							} else {
								if(menu.name == '自定义表单'){
									window.open(actionAddress,'_blank')
								}else{
									window.location = actionAddress;
								}
							}
						}
					};
					
					if(menu.confirm != "false") {
						var confirmMsg = menu.confirm == true || menu.confirm == '' ?'您确定要执行此操作吗？' : menu.config;
						confirm(confirmMsg,function(){
							menuFunciotn();
						});
					} else {
						menuFunciotn();
					}
				}
			}
		}
	},
	
	load : function (pageNum,searchData){
		pageNum = pageNum? pageNum: this.pageNum;	
		if (!searchData) {
			searchData = {};
		}
		searchData.page = pageNum;
		searchData.limit = this.pageSize;

		var obj = this;
		$.ajax({
			async:true,
			url: obj.config.gridPath + 'list.json' + obj.config.queryString,
			type: "GET",
			dataType: 'json',
			data: searchData,
			beforeSend: function(){
			},
			success: function (data) {
				// 初始化头部
				obj.initTitle();
				// 加载数据
				if (data.data && data.data.length > 0) {
					var searchDataTemp = [];
					for (var i = 0; i < data.data.length; i++) {
						var d = data.data[i];
						// 行开始
						var h = '<tr>';
						// 复选框
						h += '<td><input type="checkbox" value="1" name=""></td>';
						var listIndex = 0;
						for (var j = 0; j < obj.gridConfig.columns.length; j++) {
							var c = obj.gridConfig.columns[j];
							if (c.canList) {
								var display = obj._getColumnDisplay(d, c);
								var searchName = searchData[c.dataIndex];
								// 判断该列是否有搜索
								if(!(searchName == '' || searchName == undefined) && display.indexOf(searchName) >= 0){	// 如果有搜索，关键字变红
									searchDataTemp[j]=c.dataIndex + "_" + searchName;
									var str_before = obj._getColumnDisplay(d, c).split(searchName)[0];
									var str_after = obj._getColumnDisplay(d, c).split(searchName)[1];
									h += '<td>'+str_before+'<span style="color:#F00">' + searchName + '</span>'+str_after+'</td>';
								}else{	// 否则，正常显示
									h += '<td>' + obj._getColumnDisplay(d, c) + '</td>';
								}
							}
						}
						// 操作列
						h += '<td class="td-manage"></td>';
						// 行结束
						h += '</tr>';
						$(obj.config.tableBodyDiv).append(h);
					}
					
					// 分页 TODO
					if (true || data.totalCount > obj.pageSize) {
						// $(obj.config.pageDiv).html(getPageHtml(pageNum, data.totalCount, obj.pageSize,searchDataTemp));
					}
					
					// 加载表格事件 TODO
					// gridEvent();
				}else{
					$(obj.config.pageDiv).html(getPageHtml(1, data.totalCount, 1,[]));	
				}
				
			},
			error:function(XHR, textStatus, errorThrown){
				alert('error: ' + errorThrown);
			}
		});
	},
	
	_getColumnDisplay : function (d, c, dataIndex) {
		dataIndex = dataIndex? dataIndex: c.dataIndex;
		var obj = this;
		var result = this._getValue(d, dataIndex);
		if (c.display) {
			result = c.display;
			result = result.replace('$' + '{value}', this._getValue(d, dataIndex));
			
			result = this._replaceValue(result, d);
			
			if (result.indexOf('href="/') > 0 || result.indexOf('href=\'/') > 0) {
				result = result.replace(/href=\"\//g, 'href="' + basePath + '/').replace(/href=\'\//g, 'href=\'' + basePath + '/');
			}
			
			if (result.startsWith('function')) {
				eval('result = ' + result + '().toString();');
			}						
		}
		return (result == '' || result == undefined)? '&nbsp;': result;
	},
	
	_replaceValue : function (html, d) {
		var result = html;
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c1 = this.gridConfig.columns[i];
			var reg = new RegExp('\\${' + c1.dataIndex + '}',"g");   
			result = result.replace(reg, this._getValue(d, c1.dataIndex));
			reg = new RegExp('@{' + c1.dataIndex + '}',"g");   
			result = result.replace(reg, this._getValue(d, c1.dataIndex));
		}
		return result;
	},
	
	_getValue : function (d, dataIndex) {
		var result = '';
		try {
			eval('result = d.' + dataIndex + ';');
		} catch (e){
			
		}
		return result;
	},
	
	_btnStyle : function(name) {
	},
	
	del : function() {
	},
	
	initSavePage : function(isUpdate){
		
	},
	
	detail : function(id) {
		var obj = this;
		
	},
	
	showSavePage : function(isUpdate) {
	},
	
	save : function() {
		var obj = this;
	},
	
	getSelections : function () {
		var ids = '';
	},
	
	getCombox : function (c) {
	}
	
}

//ueditor
function initUEditor(dom){
		
}//end ueditor
	