String.prototype.startsWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
}

function Grid(config, index) {	
	var defaultConfig = {
		index: index? index: '',
		tabId: index? '#tab_content_' + index: 'body',
		gridPath:'',
		queryString:'',
		tableDiv : '#grid-table',
		titleDiv : '#title',
		tableTitleDiv : '#grid-thead',
		tableBodyDiv : '#grid-data',
		tableSelectInput : '.grid_id',
		tableSelectAllId: 'table_select_all',
		pageDiv : '#grid-page-count',
		menusDiv : '#grid-menus',
		tableTitles : undefined,
		gridPage : '#grid-page',
		gridAddId:'grid_data_add',
		gridDelId:'grid_data_del',
		savePage : '#savePage',
		userSavePage : false,
		saveTdHtmls : [],
		searchDiv : '#grid-search',
		searchFormDiv : '#grid-search-form',
		saveFormId : 'grid-save-form',
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
		if(!this.gridConfig.canSearch){
			return;
		}
		var listIndex = 0;
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			if (c.canSearch) {
				if (c.type == 'ComboBox') {
						// 生成Id
						var id = this.config.index + 'combox_' + c.dataIndex.replace('.', '');
						// 生成input
						var h = '<label class="layui-form-label">' + c.name + '</label>\
								<div class="layui-input-inline">\
									<select name="search_' + c.dataIndex + '" id="' + id + '" lay-search>\
										<option value="">请选择</option>\
									</select>\
								</div>';
						$(this.config.searchDiv).append(h);
						// 绑定combox控件事件
						initCombox(id, c.comboUrl?c.comboUrl :c.comboDataArray);
				} else if (c.type == 'Date' || c.type == 'DateTime') {
					// 生成Id
					var dateInputId = this.config.index + 'date_input_' + c.dataIndex;
					// 生成input
					var h = '<label class="layui-form-label xbs768">' + c.name + '</label>\
							<div class="layui-input-inline xbs768">\
							  <input class="layui-input" placeholder="' + c.name + '" id="' + dateInputId + '">\
							</div>';
					$(this.config.searchDiv).append(h);
					// 绑定日历控件事件
					initDate(dateInputId, c.type,false);
				} else if (c.type == 'TextField') {
					var h = '<label class="layui-form-label">' + c.name + '</label>\
							 <div class="layui-input-inline">\
							   <input type="text" name="search_' + c.dataIndex + '" placeholder="请输入' + c.name + '" autocomplete="off" class="layui-input">\
							 </div>';
					$(this.config.searchDiv).append(h);
				}
			}
		}
		// 搜索按钮
		var a = '<div class="layui-input-inline" style="width:80px">\
					<button class="layui-btn searchBtn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>\
				</div>';
		$(this.config.searchDiv).append(a);
		// 搜索事件设置
		var obj = this;
		$(this.config.searchFormDiv).submit(function(){
			obj.search();
			return false;
		});
	},

	search : function (pageNum) {
		pageNum = pageNum? pageNum: this.pageNum;
		var listIndex = 0;
		var searchData = {};
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			if (c.canSearch) {
				var value = $(this.config.tabId).find('[name="search_'+c.dataIndex+'"]').val();
				searchData['condition.' + c.dataIndex] = value;
			}
		}
		//请求
		this.load(pageNum,searchData);
	},

	initTitle : function () {

		// 初始化表头
		if (!this.config.tableTitles) {	// 如果是第一次加载，需要构建
			// 行开始
			var tableTitle = '<tr>';
			// 复选框
			tableTitle += '<th><input type="checkbox" id="' + this.config.tableSelectAllId + '"  name="" value=""></th>';
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
		// 全选选中事件
		$('#' + this.config.tableSelectAllId).click(function(){
			if ($(this.config.tabId).find('[name=ids]').not(':checked').length != 0) {
				$(this.config.tabId).find('[name=ids]').prop('checked', true);
			} else {
				$(this.config.tabId).find('[name=ids]').each(function(){
					$(this.config.tabId).find('[name=ids]').removeAttr("checked");
				});
			}
		});
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
			$(obj.config.menusDiv).append('<button class="layui-btn" id="' + obj.config.gridAddId + '"><i class="layui-icon">&#xe608;</i>添加</button>');
			$('#' + obj.config.gridAddId).click(function(){
				obj.showSavePage();
			});
		}
		if (this.gridConfig.canDelete) {
			$(obj.config.menusDiv).append('<button class="layui-btn layui-btn-danger" id="' + obj.config.gridDelId + '"><i class="layui-icon">&#xe640;</i>批量删除</button>');
			$('#' + obj.config.gridDelId).click(function(){
				obj.del();
			});
		}
		// TODO 修改按钮
		
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
	
	locationPage : function (url) {
		var obj = this;
		alert(obj.config.tabId)
		$.get(url, function(data) {
			alert(data);
			$(obj.config.tabId).html(data);
		});
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
				// 清除数据
				$(obj.config.tableBodyDiv).html('');
				// 加载数据
				if (data.data && data.data.length > 0) {
					var searchDataTemp = [];
					for (var i = 0; i < data.data.length; i++) {
						var d = data.data[i];
						// 行开始
						var h = '<tr>';
						// 复选框
						h += '<td><input type="checkbox" value="' + d.id + '" name="ids"></td>';
						var listIndex = 0;
						for (var j = 0; j < obj.gridConfig.columns.length; j++) {
							var c = obj.gridConfig.columns[j];
							if (c.canList) {
								var display = obj._getColumnDisplay(d, c);
								var searchName = searchData['condition.' + c.dataIndex];
								// 判断该列是否有搜索
								if(!(searchName == '' || searchName == undefined) && display.indexOf(searchName) >= 0){	// 如果有搜索，关键字变红
									searchDataTemp[j]=c.dataIndex + "_" + searchName;
									var str_before = display.split(searchName)[0];
									var str_after = display.split(searchName)[1];
									h += '<td>'+str_before+'<span style="color:#F00">' + searchName + '</span>'+str_after+'</td>';
								}else{	// 否则，正常显示
									h += '<td>' + display + '</td>';
								}
							}
						}
						// 修改操作
						var opts = '';
						if (obj.gridConfig.canUpdate) {
							opts += '<a style="text-decoration:none" onclick="grid.detail(\'' + d.id + '\')" data-id="' + d.id + '" href="javascript:;" title="修改">\
	                            				<i class="layui-icon">&#xe642;</i>\
	                         			</a>';
						}
						if (obj.gridConfig.canDelete) {
							opts += '<a style="text-decoration:none" onclick="grid.del(\'' + d.id + '\')" data-id="' + d.id + '" href="javascript:;" title="删除">\
	                            				<i class="layui-icon">&#xe640;</i>\
	                         			</a>';
						}
						// 其他操作
						// 操作列
						h += '<td class="td-manage">' + opts + '</td>';
										
						// 行结束
						h += '</tr>';
						$(obj.config.tableBodyDiv).append(h);
					}
					
					// 分页
					$(obj.config.pageDiv).text(data.totalCount);	// 总数
					if (data.totalCount > obj.pageSize) {
						laypage.render({
						  elem: 'grid-page',
						  count: data.totalCount,
						  curr: obj.pageNum,
						  limits:[10,20,50,100],
						  jump: function(pageObj, first){
						  	// 设置分页大小和当前分页
						    obj.pageSize = pageObj.limit;
						    obj.pageNum = pageObj.curr;
						    
						    //首次不执行
						    if(!first){
						    	obj.search(pageObj.curr);
						    }
						  }
						});
					}
					
					// 加载表格事件 TODO
					// gridEvent();
				}else{
					$(obj.config.tableBodyDiv).html(getPageHtml(1, data.totalCount, 1,[]));	
				}
			},
			error:function(XHR, textStatus, errorThrown){
				alert('error: ' + errorThrown);
			}
		});
	},
	
	save : function(isUpdate) {
		var obj = this;
		if (!this.submiting) {
			$('#' + obj.config.saveFormId).attr('action', obj.config.gridPath + (isUpdate? 'update': 'save') + '.json' + this.config.queryString);
			// 提交
			$('#' + obj.config.saveFormId).ajaxSubmit({
				dataType: "json",
				beforeSubmit : function(){
					this.submiting = true;
				},
				success : function(data){
					$('#' + obj.config.saveFormId)[0].reset();
					this.submiting = false;
					if (data.success == 'Y') {
						alert('保存成功');
						obj.load();
						x_admin_close();
					} else {
						alert(data.tip);
					}
				}
			});
		}
	},
	
	detail : function(id) {
		var obj = this;
		// 弹出窗口
		obj.showSavePage(true);
		// 构建获取详情的地址
		var detailUrl = obj.config.gridPath + 'detail.json' + obj.config.queryString;
		if (detailUrl.indexOf('?') < 0) {
			detailUrl += '?id=' + id;
		} else {
			detailUrl += '&id=' + id;
		}
		// 请求数据，填充表单
		$.getJSON(detailUrl, function(json){
			var data = json.data;
			for (var i = 0; i < obj.gridConfig.columns.length; i++) {
				var g = obj.gridConfig.columns[i];
				if (g.type == 'ComboBox' || g.type == 'ComboBox') {
					var newDataIndex = g.dataIndex.replace('.name', '.id');
					$('#' + obj.config.saveFormId).find('[name="entity.' + newDataIndex + '"]').val(obj._getValue(data, newDataIndex));	
					$('#' + obj.config.saveFormId).find('[name="'+g.name+'temp"]').attr("value",obj._getValue(data, newDataIndex));	
				} else if (g.type == 'Date' || g.type == 'DateTime'){
					$('#' + obj.config.saveFormId).find('[name="entity.' + g.dataIndex + '"]').val(obj._getValue(data, g.dataIndex));
				} else if (g.type == 'editor'){
					var ue = UE.getEditor($("#editor_"+g.dataIndex).attr("id"));
					var text = obj._getValue(data, g.dataIndex);
					try {
						ue.execCommand('inserthtml', ''+text+'');
					} catch(e) {
						
					}
					ue.addListener("ready", function (text) {
						var obj = this;
						return function(text){
							obj.focus();
							ue.execCommand('inserthtml', ''+text+'');
						}
					}(text));
				} else {
					// TODO 暂不处理checkbox，redio
					$('#' + obj.config.saveFormId).find('[name="entity.' + g.dataIndex + '"]').val(obj._getValue(data, g.dataIndex));
				}
			}
			
		})
	},
	
	del : function(ids) {
		// 如果没有ids，获取所有选中的ids
		if (!ids) {
			// 获取所有选中的ids
			var selectedIds = '';
			$('[name=ids]').each(function(){
				if ($(this).is(":checked")) {
					selectedIds += $(this).val() + ',';
				}
			});
			// 至少选择一条记录
			if (ids == '') {
				alert('至少选择一条记录');
				return;
			}
			// 组织参数，赋值
			selectedIds = selectedIds.substring(0, selectedIds.length - 1);
			ids = selectedIds;
		}
		// 提示,确认后删除
		var obj = this;
		if (confirm('确定删除？删除后无法恢复！')) {
			$.ajax({
				url: obj.config.gridPath + 'delete.json' + obj.config.queryString,
				type: "POST",
				dataType: 'json',
				data: {'ids':ids},
				beforeSend: function(){
				},
				success: function (data) {
					if (data.success == 'Y') {
						alert('删除成功');
					} else {
						alert(data.tip);
					}
					obj.load();
				},
				error:function(XHR, textStatus, errorThrown){
					alert('error: ' + errorThrown);
				}
			});
		}
	},
	
	showSavePage : function(isUpdate) {
		x_admin_show('添加', this._initSavePage(isUpdate));
		// 重新渲染表单
		form.render();
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
		
		if (result.indexOf('${') > 0 || result.indexOf('@{') > 0 ) {
			for (var i = 0; i < this.gridConfig.columns.length; i++) {
				var c1 = this.gridConfig.columns[i];
				var reg = new RegExp('\\${' + c1.dataIndex + '}',"g");   
				result = result.replace(reg, this._getValue(d, c1.dataIndex));
				reg = new RegExp('@{' + c1.dataIndex + '}',"g");   
				result = result.replace(reg, this._getValue(d, c1.dataIndex));
				
				if (result.indexOf('${') < 0 && result.indexOf('@{') < 0 ) {
					break;
				}
			}
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
	
	_initSavePage : function(isUpdate){
		// 如果存在，删除
		if ($('#' + this.config.saveFormId)) {
			$('#' + this.config.saveFormId).remove();
		}
		// 创建表单
		$('body').append('<form class="layui-form" style="display:none; padding:10px 20px;" id="' + this.config.saveFormId + '" onsubmit="return false;" method="post"></form>');
		// 绑定事件
		var obj = this;
		$('#' + this.config.saveFormId).submit(function() {
			obj.save(isUpdate);
			return false;
		});
		// 生成列	
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			// 不能添加修改的，返回
			if (!isUpdate && !c.canAdd) {
				continue;
			}
			// 编辑时设置Id
			if (isUpdate && !c.canUpdate) {
				var a = '<input type="hidden" name="entity.' + c.dataIndex + '" />';
				$('#' + this.config.saveFormId).append(a);
				continue;
			}
			var labelName = (c.required? '*': '') + c.name;
			// 文本域
			if (c.type == 'TextField') {
				var a = '<div class="layui-form-item">\
							<label class="layui-form-label">' + labelName + '</label>\
							<div class="layui-input-block">\
						      <input type="text" name="entity.' + c.dataIndex + '" ' + (c.required? 'required': '') + ' placeholder="请输入' + c.name + '" autocomplete="off" class="layui-input">\
						    </div>\
						 </div>';
				$('#' + this.config.saveFormId).append(a);
			} else if (c.type == 'TextArea') {
				var a = '<div class="layui-form-item layui-form-text">\
				          <label class="layui-form-label">' + labelName + '</label>\
				          <div class="layui-input-block">\
				            <textarea name="entity.' + c.dataIndex + '" placeholder="请输入' + c.name + '" class="layui-textarea"></textarea>\
				          </div>\
				        </div>';
				$('#' + this.config.saveFormId).append(a);
			} else if (c.type == 'ComboBox') {
				// 生成Id
				var id = this.config.index + 'save_combox_' + c.dataIndex.replace('.', '');
				// 生成input
				var a = '<div class="layui-form-item">\
							<label class="layui-form-label">' + labelName + '</label>\
							<div class="layui-input-block">\
								<select name="search_' + c.dataIndex + '" id="' + id + '" lay-search lay-filter="save-form">\
									<option value="">请选择</option>\
								</select>\
							</div>\
						 </div>';
				$('#' + this.config.saveFormId).append(a);
				// 绑定combox控件事件
				initCombox(id, c.comboUrl?c.comboUrl :c.comboDataArray);
			} else if (c.type == 'Hidden') {
				var a = '<input type="hidden" name="entity.' + c.dataIndex + '" value="' + c.display + '" />';
				$('#' + this.config.saveFormId).append(a);
			}else if(c.type == 'DateTime' || c.type == 'Date'){
				// 生成Id
				var dateInputId = this.config.index + 'save_date_input_' + c.dataIndex;
				// 生成input
				var a = '<div class="layui-form-item">\
							<label class="layui-form-label xbs768">' + labelName + '</label>\
							<div class="layui-input-inline xbs768">\
								<input class="layui-input" name="search_' + c.dataIndex + '" placeholder="请选择' + c.name + '" id="' + dateInputId + '">\
							</div>\
						</div>';
				$('#' + this.config.saveFormId).append(a);
				// 绑定日历控件事件
				initDate(dateInputId, c.type,false);
			} else if(c.type == 'editor'){
				// TODO 暂不支持富文本
				initUEditor("#editor_"+c.dataIndex);
			} else {
				// TODO 暂不支持：单选、多选
			}
		}
		// 提交按钮
		var h = '  <div class="layui-form-item">\
			          <div class="layui-input-block">\
			            <button class="layui-btn" lay-submit="" lay-filter="formDemo">立即提交</button>\
			            <button type="reset" class="layui-btn layui-btn-primary">重置</button>\
			          </div>\
			        </div>';
		$('#' + this.config.saveFormId).append(h);
		// 返回jquery对象
		return $('#' + this.config.saveFormId);
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

function initDate(dateInputId, dateType, isRange) {
	layui.use(['laydate'], function(){
		laydate = layui.laydate;//日期插件
		
		laydate.render({ 
			elem: '#' + dateInputId,
			type: dateType? dateType.toLowerCase(): 'date',
			range: !!isRange? '~': false
		});

	});
}

function initCombox(id, data) {
	// 判断数据类型
	var comboUrl = '';
	var comboDataArray = '';
	if (data instanceof Array) {
		comboDataArray = data;
	} else {
		comboUrl = data;
	}
	// 绑定数据
	if (comboUrl) {	// 动态获取数据 TODO 待验证
		var a = function (id, comboUrl) {
			return function(id, comboUrl) {
				$.getJSON(basePath + comboUrl, function(data) {
					var b = '';
					$('#' + id).append('<option value="">' + "全部" + '</option>');
					for (var j = 0; j < data.length; j++) {
						var d = data[j];
						b += '<option value="' + d[1] + '">' + d[1] + '</option>';
					}
					$('#' + id).append(b);
					
				});
			}(id, comboUrl);
		};
		a(id, comboUrl);
	} else {// 静态数组数据：[{"id":"F","name":"女"},{"id":"M","name":"男"}],
		var a = function (id, comboDataArray) {
				return function(id, comboDataArray) {
					var b = '';
					for (var j = 0; j < comboDataArray.length; j++) {
						var d = comboDataArray[j];
						b += '<option value="' + d.id + '">' + d.name + '</option>';
					}
					$('#' + id).append(b);
				}(id, comboDataArray);
		};
		a(id, comboDataArray);
	}
	// 重新渲染表单
	form.render();
}

	