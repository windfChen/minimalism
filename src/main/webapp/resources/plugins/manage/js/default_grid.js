function Grid(config) {	
	var defaultConfig = {
		tableDiv : '#table',
		titleDiv : '#title',
		addBtn : '#add_btn',
		delBtn : '#del_btn',
		tableBodyDiv : '#grid',
		tableSelectAllInput : '#select_all_input',
		tableSelectInput : '.grid_id',
		pageDiv : '#page',
		tableForm : '#form',
		menusDiv : '#menus_div',
		tableTitles : undefined,
		tableTrHtml : undefined,
		tableTdHtmls : [],
		listPage : '#listPage',
		savePage : '#savePage',
		userSavePage : false,
		saveTdHtmls : [],
		searchDiv : '#table_search'
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
		return function(pageNum){
			obj.load(pageNum);
		}
	}(this);
	
}

Grid.prototype = {
	
	init : function () {
		var obj = this;
		$.getJSON('grid.json' + queryString, function(gridConfig){
			obj.gridConfig = gridConfig.data;
			obj.initGrid();
			obj.load();
		})
	},
	
	initGrid : function () {
		// 初始化标题
		$('title').html(this.gridConfig.title);
		$(this.config.titleDiv).html(this.gridConfig.title);
		
		// 添加删除功能
		var obj = this;
		if (this.gridConfig.canAdd) {
			$(this.config.addBtn).show();
			$(this.config.addBtn).click(function(){
				obj.showSavePage();
			});
		}
		if (this.gridConfig.canDelete) {
			$(this.config.delBtn).show();
			$(this.config.delBtn).click(function(){
				obj.del();
			});
		}
		
		// 计算宽度
		var titleCount = 0;
		var nameIndex = -1;
		var optIndex = -1;
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			if (c.canList) {
				if(nameIndex == -1 && (c.name.indexOf('名称') > -1 || c.name.indexOf('登录名') > -1 || c.name.indexOf('标题') > -1)) {
					nameIndex = titleCount;
				}
				if(c.name == '操作') {
					optIndex = titleCount;
				}
				titleCount ++;
			}
		}
		var avgDataColumnWidth = parseInt((12 - 1) / (titleCount));
		this.idColunmWidth = 1;
		for (var i = 0; i < titleCount; i ++) {
			this.dataColumnWidth[i] = avgDataColumnWidth;
		}
		// 如果有剩余，加到一列
		var less = (12 - 1) % (titleCount);
		if (less > 0) {
			if(nameIndex != -1) {
				this.dataColumnWidth[nameIndex] = avgDataColumnWidth + less;
			} else if(optIndex != -1) {
				this.dataColumnWidth[optIndex] = avgDataColumnWidth + less;
			} else {
				this.dataColumnWidth[0] = avgDataColumnWidth + less;
			}
		}
		
		
		this.initTitle();
		this.initMenu();
		this.initSearch();
	},
	
	initSearch : function () {
		if(this.gridConfig.canSearch){
				var searchHTML = '<div class="clearfix work_table"><div class="search_box"></div><div class="col-md-1 col-xs-2"><label></label><input type="button" value="搜 索" class="searchBtn" style="width:50px;height:30px"></input></div></div>';
			$(this.config.searchDiv).html(searchHTML);
			var listIndex = 0;
			for (var i = 0; i < this.gridConfig.columns.length; i++) {
				var c = this.gridConfig.columns[i];
				if (c.canSearch) {
					
					if (c.type == 'ComboBox') {
							var _dataIndex = c.dataIndex.replace('.', '');
							var h = '<div class="col-md-1 col-xs-2 center_table_t">' + c.name + ':</div>\
								<div class="col-md-2 col-xs-2">\
									<div class="center_table_input"><select class="search_input" name="search_' + (c.dataIndex.replace('.name', '.id')) + '" id="combox_' + _dataIndex + '"></select></div>\
							</div>';
							$(this.config.searchDiv).find('.search_box').append(h);
							if (c.comboUrl) {
								var a = function (_dataIndex, comboUrl) {
									return function(_dataIndex, comboUrl) {
										$.getJSON(basePath + comboUrl, function(data) {
											var b = '';
											for (var j = 0; j < data.length; j++) {
												var d = data[j];
												b += '<option value="' + d[0] + '">' + d[1] + '</option>';
											}
											$('#combox_' + _dataIndex).append(b);
											
										});
									}(_dataIndex, comboUrl);
								};
								a(_dataIndex, c.comboUrl);
							} else {// [{"id":"F","name":"女"},{"id":"M","name":"男"}],
								var a = function (_dataIndex, comboDataArray) {
										return function(_dataIndex, comboDataArray) {
											var b = '';
											for (var j = 0; j < c.comboDataArray.length; j++) {
												var d = c.comboDataArray[j];
												b += '<option value="' + d.id + '">' + d.name + '</option>';
											}
											$('#combox_' + _dataIndex).append(b);
										}(_dataIndex, comboDataArray);
								};
								a(_dataIndex, c.comboDataArray);
							}
					} else{
						var h = '<div class="col-md-1 col-xs-2 center_table_t">' + c.name + ':</div>\
										<div class="col-md-2 col-xs-2">\
											<div class="center_table_input"><input class="search_input" type="text" name="search_'+c.dataIndex+'"/></div>\
									</div>';

						$(this.config.searchDiv).find('.search_box').append(h);
					}
				}
			}
			var obj = this;
			$('.searchBtn').click(function(){
				obj.searchBtn();
			});
		}
		
	},

	searchBtn : function () {
		var listIndex = 0;
		var searchData = {};
		for (var i = 0; i < this.gridConfig.columns.length; i++) {
			var c = this.gridConfig.columns[i];
			if (c.canSearch) {
				var value = $('[name=search_'+c.dataIndex+']').val();
				searchData[c.dataIndex] = value;
			}
		}
		//请求
		this.load(this.pageNum,searchData);
	},

	initTitle : function () {

		// 初始化表头
		if (!this.config.tableTitles) {
			var tableTitle = '<li id="title_li" class="col-xs-' + this.idColunmWidth + '"><span class="table"><label class="label_check"><div class="btn_check"></div><input id="select_all_input" type="checkbox"/></label></span></li>';
			var listIndex = 0;
			for (var i = 0; i < this.gridConfig.columns.length; i++) {
				var c = this.gridConfig.columns[i];
				
				if (c.canList) {
					tableTitle += '<li class="col-xs-' + this.dataColumnWidth[listIndex++] + '">' + c.name + '</li>';
				}
			}
			$(this.config.tableBodyDiv).html(tableTitle);
		} else {
			$(this.config.tableBodyDiv).html(this.config.tableTitles);
		}
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
			url: 'list.json' + queryString,
			type: "GET",
			dataType: 'json',
			data: searchData,
			beforeSend: function(){
			},
			success: function (data) {
				obj.initTitle();
				if (data.models && data.models.length > 0) {	
					for (var i = 0; i < data.models.length; i++) {
						var d = data.models[i];
						var h = '';
						if (!obj.config.tableTrHtml) {
							h = '<li class="col-xs-' + obj.idColunmWidth + '"> <span class="table"> <label class="label_check"> <div class="btn_check"></div> \
								<input type="checkbox" class="grid_id" data_id="' + d.id + '" name=""/> </label> </span> </li>';
						}
						var listIndex = 0;
						for (var j = 0; j < obj.gridConfig.columns.length; j++) {
							var c = obj.gridConfig.columns[j];
							if (c.canList) {
								if (obj.config.tableTdHtmls.length > 0) {
									var td = $(obj.config.tableTdHtmls[listIndex++]);
									obj._innerObject(td).append(obj._getColumnDisplay(d, c));
									h += obj._replaceValue(td.prop("outerHTML"), d);
								} else {
									h += '<li class="col-xs-' + obj.dataColumnWidth[listIndex++] + '">' + obj._getColumnDisplay(d, c) + '</li>';
								}
							}
						}	
						if (obj.config.tableTrHtml) {
							var tr = $(obj.config.tableTrHtml);
							obj._innerObject(tr).append(h);
							h = tr.prop("outerHTML");
						}
						$(obj.config.tableBodyDiv).append(h);
					}
					
					if (true || data.totalCount > obj.pageSize) {
						$(obj.config.pageDiv).html(getPageHtml(pageNum, data.totalCount, obj.pageSize));
					}
					
					gridEvent();
					
					$('.edit_a').click(function () {
						obj.detail($(this).attr('data-id'))
					});
				}
					
				
			},
			error:function(XHR, textStatus, errorThrown){
				alert('error: ' + errorThrown);
			}
		});
	},
	
	_innerObject : function($obj) {
		var children = $($obj).children();
		if (children.length > 0) {
			return this._innerObject($(children[children.length - 1]));
		}
		return $obj;
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
			
			if (result.indexOf('<a>编辑</a>') > -1) {
				var menuStyleClass = this._btnStyle('编辑');
				result = result.replace('<a>编辑</a>', '<a href="javascript:void(0)" class="edit_a" data-id="' + this._getValue(d, 'id') + '"><i class="iconfont ' + menuStyleClass + '"></i>编辑</a>')
			}
			
			/*
			if (result.indexOf('</a>') > -1) {
				var content = $('<div>' + result + '</div>');
				$('<div>' + result + '</div>').find('a').each(function(){
					var html = $(this).html();
					var menuStyleClass = obj._btnStyle(html);
					if (menuStyleClass) {
						html = '<i class="iconfont ' + menuStyleClass + '">' + html;
					}
					$(this).html(html);
				});
				result = content.html();
			}*/
						
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
		var menuStyleClass = '';
		if (name.indexOf('返回') > -1) {
			menuStyleClass = 'icon-arrLeft';
		}
		if (name.indexOf('添加') > -1) {
			menuStyleClass = 'icon-tianjia';
		}
		if (name.indexOf('修改') > -1 || name.indexOf('编辑') > -1) {
			menuStyleClass = 'icon-bianji';
		}
		if (name.indexOf('删除') > -1) {
			menuStyleClass = 'icon-weibiaoti--';
		}
		if (name.indexOf('发布') > -1) {
			menuStyleClass = 'icon-fabu';
		}
		return menuStyleClass;
	},
	
	del : function() {
		var ids = this.getSelections();
		if (ids == '') {
			alert('至少选择一条记录');
			return;
		}
		
		confirm('确定删除？删除后无法恢复！',function (){
			$.ajax({	
				async:true,
				url: 'delete.json' + queryString,
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
					
					load();
				},
				error:function(XHR, textStatus, errorThrown){
					alert('error: ' + errorThrown);
				}
			});
		})
	},
	
	initSavePage : function(isUpdate){
		if (!this.config.userSavePage) {
			$(this.config.savePage).html('<div class="mod_title1">\
							<div class="pull-right work_qxbtn">\
								<a href="javascript:;" id="return_btn"><i class="iconfont icon-arrLeft"></i>返回</a>\
							</div>\
							<h3 class="pull-left">' + this.gridConfig.title + '-添加</h3>\
						</div>\
						<div class="work_fabu">\
							<form id="form" action="' + (isUpdate? 'update': 'save') + '.json' + queryString + '" method="post">'	+		
								'<div id="form_inputs"></div>\
								<div class="clearfix work_table">\
									<div class="col-md-12 col-xs-12"><button type="submit" class="work_save trans" >保存</button></div>\
								</div>\
							</form>\
						</div>');
						
						for (var i = 0; i < this.gridConfig.columns.length; i++) {
							var c = this.gridConfig.columns[i];
							
							if (!isUpdate && !c.canAdd) {
								continue;
							}
							
							if (isUpdate && !c.canUpdate) {
								var a = '<input type="hidden" name="entity.' + c.dataIndex + '" />';
								$('#form_inputs').append(a);
								continue;
							}
							
							if (c.type == 'TextField') {
								var a = '<div class="clearfix work_table">\
									<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
									<div class="col-md-11 col-xs-10">\
										<div class="center_table_input"><input type="text" name="entity.' + c.dataIndex + '" placeholder="请输入' + c.name + '" class="work_input"/></div>\
									</div>\
								</div>';
								$('#form_inputs').append(a);
							} else if (c.type == 'TextArea') {
								var a = '<div class="clearfix work_table">\
									<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
									<div class="col-md-11 col-xs-10">\
										<div class="center_table_text"><textarea name="entity.' + c.dataIndex + '" placeholder="请输入' + c.name + '" class="work_input"></textarea></div>\
									</div>\
								</div>';
								$('#form_inputs').append(a);
							} else if (c.type == 'ComboBox') {
								var _dataIndex = c.dataIndex.replace('.', '');
								var a = '<div class="clearfix work_table">\
									<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
									<div class="col-md-11 col-xs-10">\
										<div class="center_table_input"><select name="entity.' + (c.dataIndex.replace('.name', '.id')) + '" id="combox_' + _dataIndex + '"></select></div>\
									</div>\
								</div>';
								$('#form_inputs').append(a);
								if (c.comboUrl) {
									var a = function (_dataIndex, comboUrl) {
										return function(_dataIndex, comboUrl) {
											$.getJSON(basePath + comboUrl, function(data) {
												var b = '';
												for (var j = 0; j < data.length; j++) {
													var d = data[j];
													b += '<option value="' + d[0] + '">' + d[1] + '</option>';
												}
												$('#combox_' + _dataIndex).append(b);
												
											});
										}(_dataIndex, comboUrl);
									};
									a(_dataIndex, c.comboUrl);
								} else {// [{"id":"F","name":"女"},{"id":"M","name":"男"}],
									var b = '';
									for (var j = 0; j < c.comboDataArray.length; j++) {
										var d = c.comboDataArray[j];
										b += '<option value="' + d.id + '">' + d.name + '</option>';
									}
									$('#combox_' + _dataIndex).append(b);
								}
							} else if (c.type == 'Hidden') {
								var a = '<input type="hidden" name="entity.' + c.dataIndex + '" value="' + c.display + '" />';
								$('#form_inputs').append(a);
							}else if(c.type == 'datePicker'){
								var a = '<div class="clearfix work_table">\
									<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
									<div class="col-md-3 col-xs-4">\
									<div class="center_table_input"><input name="entity.' + c.dataIndex + '" type="text" class="work_input work_date" name="entity.' + c.dataIndex + '"  onClick="WdatePicker()"/></div>\
								</div>';
								$('#form_inputs').append(a);
							}else if(c.type == 'radio'){
								if(c.comboUrl){
									var a = function (c) {
										return function(c) {
											$.getJSON(basePath + c.comboUrl, function(data) {
												var b = '<div class="clearfix work_table">\
												<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
												<div class="col-md-8">\
													<span class="table">';
												for (var j = 0; j < data.data.length; j++) {
													var d = data.data[j];
													if(j == 0){
														b += '<label class="label_radio ell col-md-8 r_on">\
														<div class="btn_radio"></div>\
														<input type="radio" name="entity.' + c.dataIndex + '" checked="checked" value="' + d.id + '"/>' + d.name +'</label>';
													}else{
														b += '<label class="label_radio ell col-md-8">\
															<div class="btn_radio"></div>\
															<input type="radio" name="entity.' + c.dataIndex + '" value="' + d.id + '"/>' + d.name +'</label>';
													}
												}
												b += '</span></div></div>';
												//$('#radio').append(b);
												$('#form_inputs').append(b);
												gridEvent();
											});
										}(c);
									};
									a(c);
								}else{
									var b = '<div class="clearfix work_table">\
												<div class="col-md-1 col-xs-2 center_table_t">*' + c.name + ':</div>\
												<div class="col-md-8">\
													<span class="table">';
									for (var j = 0; j < c.comboDataArray.length; j++) {
										var d = c.comboDataArray[j];
										if(j == 0){
											b += '<label class="label_radio r_on">\
											<div class="btn_radio"></div>\
											<input type="radio" checked="checked" \
											value="'+ d.id +'" name="entity.' + c.dataIndex + '"/>'+d.name+'</label>';
										}else{
											b += '<label class="label_radio">\
											<div class="btn_radio"></div>\
											<input type="radio"  \
											value="'+ d.id +'" name="entity.' + c.dataIndex + '"/>'+d.name+'</label>';
										}
										
									}
									b += '</span></div></div>';
									$('#form_inputs').append(b);
									gridEvent();
								}
							}else if(c.type == 'editor'){
								var a = '<div class="clearfix work_table">\
											<div class="col-md-1 col-xs-2 center_table_t">*'+c.name+':</div>\
											<div class="col-md-11 col-xs-10">\
												<div><textarea id="editor_'+c.dataIndex+'" name="entity.' + c.dataIndex + '" class="work_text" ></textarea></div>\
											</div>\
										</div>';
								$('#form_inputs').append(a);
								//ueditor start
								initUEditor("#editor_"+c.dataIndex);
								
							}else if (c.type == 'TextFieldCanEmpty') {
								var a = '<div class="clearfix work_table">\
									<div class="col-md-1 col-xs-2 center_table_t">' + c.name + ':</div>\
									<div class="col-md-11 col-xs-10">\
										<div class="center_table_input"><input type="text" name="entity.' + c.dataIndex + '" placeholder="请输入' + c.name + '" class="work_input"/></div>\
									</div>\
								</div>';
								$('#form_inputs').append(a);
							} 
							
						}
									
			var obj = this;
			$('#return_btn').click(function(){
				obj.showSavePage();
			});
			$('#form').submit(function() {
				obj.save();
				return false;
			});
		}
		
	},
	
	detail : function(id) {
		this.showSavePage(true);
		var obj = this;
		var detailUrl = 'detail.json' + queryString;
		if (detailUrl.indexOf('?') < 0) {
			detailUrl += '?id=' + id;
		} else {
			detailUrl += '&id=' + id;
		}
		$.getJSON(detailUrl, function(json){
			$("#form").attr("action","update.json");
			var data = json.data[0].entity;
			for (var i = 0; i < obj.gridConfig.columns.length; i++) {
				var g = obj.gridConfig.columns[i];
				if (g.type == 'ComboBox' || g.type == 'ComboBox') {
					var newDataIndex = g.dataIndex.replace('.name', '.id');
					$('#form').find('[name="entity.' + newDataIndex + '"]').val(obj._getValue(data, newDataIndex));	
				} else if (g.type == 'datePicker'){
					$('#form').find('[name="entity.' + g.dataIndex + '"]').val(obj._getValue(data, g.dataIndex).substring(0, 10));
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
				}else if (g.type == 'radio'){
					var val = obj._getValue(data, g.dataIndex);
					$('#form').find('[name="entity.' + g.dataIndex + '"][checked="checked"]').attr("checked","");
					$('#form').find('[name="entity.' + g.dataIndex + '"][checked="checked"]').parent().attr("class","label_radio");
					$('#form').find('[name="entity.' + g.dataIndex + '"][value="'+val+'"]').attr("checked","checked");
					$('#form').find('[name="entity.' + g.dataIndex + '"][value="'+val+'"]').parent().attr("class","label_radio r_on");
				} else {
					$('#form').find('[name="entity.' + g.dataIndex + '"]').val(obj._getValue(data, g.dataIndex));
				}
			}
		})
	},
	
	showSavePage : function(isUpdate) {
		this.initSavePage(isUpdate);
		if ($(this.config.listPage).is(":hidden")) {
			$(this.config.listPage).show();
			$(this.config.savePage).hide();
		} else {
			$(this.config.listPage).hide();
			$(this.config.savePage).show();
		}
	},
	
	save : function() {
		var obj = this;
		if (!this.submiting) {
			
			// 验证
			
			// 提交
			$(obj.config.tableForm).ajaxSubmit({
				dataType: "json",
				beforeSubmit : function(){
					this.submiting = true;
				},
				success : function(data){
					$(obj.config.tableForm)[0].reset();
					this.submiting = false;
					if (data.success == 'Y') {
						alert('保存成功');
						obj.showSavePage();
						obj.load();
					} else {
						alert(data.tip);
					}
				}
			});
			
		}
	},
	
	getSelections : function () {
		var ids = '';
		$(this.config.tableSelectInput).each(function() {
			if (this.checked) {
				if (ids.length != '') {
					ids += ',';
				}
				ids += $(this).attr('data_id');
			}
		});
		return ids;
	},
	
	initMenu : function () {
		var obj = this;
		for (var i = 0; i < this.gridConfig.menus.length; i++) {
			var menu = this.gridConfig.menus[i];
			
			var menuStyleClass = obj._btnStyle(menu.name);
			var a = '<a href="javascript:;" id="menu_' + i + '"><i class="iconfont ' + menuStyleClass + '"></i>' + menu.name + '</a>';
			
			$(obj.config.menusDiv).append(a);
			
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
								window.location = actionAddress;
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
	
	getCombox : function (c) {
		
		var comboBoxStory = {}
		function getStory(c) {

			return function(fieldConfig) {
				var myStore = comboBoxStory[fieldConfig.dataIndex];
				if (!myStore) {
					if (c.comboUrl) {
						myStore = new Ext.data.SimpleStore({
							proxy: new Ext.data.HttpProxy({
								url: basePath + fieldConfig.comboUrl
							}),
							fields: ['id', 'name'],
							remoteSort: true
						});
					} else if (c.comboDataArray) {
						var data = [];
						for (var i = 0; i < c.comboDataArray.length; i++) {
							var d = [c.comboDataArray[i].id, c.comboDataArray[i].name];
							data[data.length] = d;
						}
						myStore = new Ext.data.SimpleStore({
							data: data,
							fields: ['id', 'name'],
							remoteSort: true
						});
					}
					comboBoxStory[fieldConfig.dataIndex] = myStore;
				}
				return myStore;
			}(c);

		}
	}
	
}

//ueditor
function initUEditor(dom){
		var $ueDom = $(dom);
		var hasEditor = $ueDom.attr("hasEditor");
		if(hasEditor !== '1'){
			$ueDom.attr("hasEditor","1");
			var domId = $ueDom.attr("id");
			var maxlength = $ueDom.attr("maxlength");
			if(maxlength == undefined || maxlength==null || maxlength==0){
				maxlength = 10000;
			}
			UE.delEditor(domId);
			var ue = UE.getEditor(domId,{
				zIndex:10,
				maximumWords:maxlength,
				wordCount: true,
				elementPathEnabled: false,
				initialFrameWidth: 840,
				initialFrameHeight:230,
				autoHeightEnabled:false
			});		
			
			ue.addListener('ready', function() {
				$ueDom.attr("hasEditor","1");
				this.focus(); //编辑器加载完成后，让编辑器拿到焦点
			});
		}
}//end ueditor
	