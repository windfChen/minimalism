var nav;
var index = 0;
var dropBeforeParentNode;
var editorFlag = false;
Ext.onReady(function() {
	Ext.ux.TabCloseMenu = function() {
		var tabs, menu, ctxItem;
		this.init = function(tp) {
			tabs = tp;
			tabs.on('contextmenu', onContextMenu);
		}
		function onContextMenu(ts, item, e) {
			if (!menu) { // create context menu on first right click
				menu = new Ext.menu.Menu([{
					id : tabs.id + '-close',
					text : '关闭标签',
					iconCls : "close",
					handler : function() {
						tabs.remove(ctxItem);
					}
				},{
					id : tabs.id + '-close-others',
					text : '关闭其他标签',
					iconCls : "close",
					handler : function() {
						tabs.items.each(function(item) {
							if (item.closable && item != ctxItem) {
								tabs.remove(item);
							}
						});
					}
				},{
					id : tabs.id + '-close-all',
					text : '关闭所有标签',
					iconCls : "close",
					handler : function() {
						tabs.items.each(function(item) {
							tabs.remove(item);
						});
					}
				}, "-" ,{
					id : tabs.id + '-refresh',
					text : '刷新',
					iconCls : "refresh",
					handler : function() {
						tabs.getItem(ctxItem.id).show();
						var iframe = document.getElementById("iframe-" + ctxItem.id);
						iframe.contentWindow.location.reload()
					}
				}
				]);
			}
			ctxItem = item;
			var items = menu.items;
			items.get(tabs.id + '-close').setDisabled(!item.closable);
			var disableOthers = true;
			tabs.items.each(function() {
				if (this != item && this.closable) {
					disableOthers = false;
					return false;
				}
			});
			items.get(tabs.id + '-close-others').setDisabled(disableOthers);
			menu.showAt(e.getPoint());
		}
		
	};

	/* 添加、修改 节点 */
	function formWin(node, str) {
		var title, url, nodeText = '';
		if (node.isLeaf()) {
			nodeText = node.text;
			title = '修改菜单名称';
			url = '/sso/peExttree_updateMenuName.action';
		} else {
			if (str == "add") {
				title = '添加新目录';
				url = '/sso/peExttree_addDirMenu.action';
			} else if (str == "edit") {
				nodeText = node.text;
				title = '修改目录';
				url = '/sso/peExttree_updateDirMenuName.action';

			}
		}
		
		Ext.Ajax.request({
			url : '/sso/peExttree_getNameByNodeId.action',
			params : {
				nodeId : node.id
			},
			success : function(response, opts) {
				response = Ext.util.JSON.decode(response.responseText);
				var oldName = response.info;
				createFormWin(node, title, url, str, nodeText, oldName);
			}
		});
		
	}		

	/* 删除分类 */
	function removeCategory(currentNode) {
		Ext.Ajax.request({
			url : '/sso/peExttree_deleteDirMenu.action', // 请求的地址
			method : "POST",
			params : {
				nodeId : currentNode.id
			}, // 发送的参数
			success : function(response, option) {
				response = Ext.util.JSON.decode(response.responseText);
				if (response.success == 'true') {
					removeNode(currentNode);
				} else {
					alert(response.info);
				}
			},
			failure : function(response, option) {
				response = Ext.util.JSON.decode(response.responseText);
				alert(response.info);
			}
		});
	}
			
	/* 删除一个TreeNode */
	function removeNode(node) {
		node.remove();
	}		
			
	/* 创建一个TreeNode */
	function createNode(node, id, text) {
		var newNode = new Ext.tree.TreeNode({
			id : id,
			text : text,
			leaf : false,
			draggable : true,
			icon : '/js/extjs/images/default/tree/folder.gif'
		});

		node.appendChild(newNode);
		node.leaf = false;
		node.expand();
	}
	
	/* 创建一个window */
	function createFormWin(node, title, url, str, nodeText,oldName) {
		var nameField = new Ext.form.TextField({
			fieldLabel : '名称',
			value : nodeText,
			anchor : '90%',
			allowBlank : false,
			itemCls : 'self-define',
			blankText : '名称不能为空',
			maxLength : 10,
			maxLengthText : '最长为10位'
		});
		
		var oldNameField  = new Ext.form.TextField({
			fieldLabel : '默认名称',
			value : oldName,
			anchor : '90%',
			disabled : true
		});
		
		var win = new Ext.Window({
			title : title,
			frame : true,
			layout : 'form',
			width : 400,
			height : 150,
			modal : true,
			labelAlign : 'right',
			buttonAlign : 'center',
			items : [nameField,oldNameField ],
			buttons : [{
				text : '保存',
				handler : function() {
					if (nameField.isValid()) {
						var value = nameField.getValue();
						Ext.Ajax.request({
							url : url,
							params : {
								nodeId : node.id,
								nodeName : value
							},
							success : function(response, opts) {
								if (node.isLeaf()) {
									node.setText(value);
								} else {
									if (str == "add") {
										var respText = Ext.util.JSON.decode(response.responseText);
										createNode(node,respText.id,value);
									} else if (str == "edit") {
										node.setText(value);
									}
								}
								win.close();
							},
							failure : function(response, opts) {
								alert('设置失败');
							}
						});
					}
				}
			}]
		});
		win.show();
	}		

	var loader = new Ext.tree.TreeLoader({
		url : basePath + '/menu.json?id=1'
	});
	loader.on('beforeload', function(treeloader, node) {
		treeloader.baseParams = {
			id : node.id,
			method : 'tree',
			editorFlag: editorFlag
		};
	}, this);
	var root = new Ext.tree.AsyncTreeNode({
		id : 'root',
		draggable : false,
		singleClickExpand : false,
		text : "功能列表"
	});		
	var nav = new Ext.tree.TreePanel({
		region : 'center',
		root : root,
		useArrows : true, // 使用箭头
		enableDD : true,
		lines : true,// 虚线
		autoScroll : true,// 如果树的高超出这个树的面板 则自动显示滚动条
		loader : loader,
		border : false,
		listeners : {
			'click' : function(node, event) {
				if (node.isLeaf() && editorFlag != true) {
					// 为叶子节点时，点击进入链接
					var nname = node.text;
					var nhref = node.attributes.url;
					nhref = basePath + nhref;
					event.stopEvent();
					addTab(nname, nhref, node.id);

				} else {
					if (node.isExpanded()) {
						node.collapse(true);
					} else {
						node.expand(false);
					}
					event.stopEvent();
				}
			},
			'nodedrop' : function(e) {// 拖拽
				var dropNode = e.dropNode;
				if (e.target != nav.root) {
					var pId = e.target.id;// append
					if (e.point == 'below' || e.point == 'above') {
						pId = e.target.parentNode.id;
					}
					Ext.Ajax.request({
						url : '/sso/peExttree_moveMenu.action',
						params : {
							point : e.point,
							nodeId : dropNode.id,
							upNodeId : e.target.id,
							parentId : pId
						},
						failure : function(response, opts) {
							var parentNodePath = e.dropNode.parentNode.getPath();
							dropBeforeParentNode.reload(false);
							expandPath(parentNodePath);
							alert('移动失败');
						}
					});
				}
			},
			'contextmenu' : function(node, e) {// 右键修改
				if(editorFlag == true){
					if (node.isLeaf()) {
						formWin(node, "");
					} else {
						var nodeSelected = node;
						nodeSelected.select();
						var dirMenu = new Ext.menu.Menu({
							items : [ {
								text : "添加目录",
								handler : function() {
									formWin(nodeSelected, "add");
								}
							}, "-", {
								text : "编辑目录",
								handler : function() {
									formWin(nodeSelected, "edit");
								}
							}, "-", {
								text : "删除空目录",
								handler : function() {
									/*
									 * Ext.MessageBox.confirm("确认删除",
									 * "是否要删除指定内容？", function(button, text) {
									 * if (button == "yes") {
									 * removeCategory(nodeSelected); } });
									 */
									Ext.MessageBox.alert("提示", "暂未开放");
								}
							} ]
						});
						dirMenu.showAt(e.getPoint());
					}
				}
			}
		}
	});
	
	// nav.syncSize();
	function expandPath(path) {
		nav.expandPath(path);
	}
	nav.on("nodedragover", function(e) {
		var node = e.dropNode.parentNode;
		dropBeforeParentNode = node;
		var l = node.childNodes.length;
		if (l == 1) {
			node.getUI().getIconEl().src = '/js/extjs/images/default/tree/folder.gif';
		}
	});
	
	
	// 展开两级--yinxu
	function expandSL(){
		root.expand(false, false, function() {
			root.expandChildNodes(false);
		});
	}
	
	function show() {
		nav.render(Ext.getBody());
		// nav.getRootNode().toggle();
	}
	var hiddenPkgs = [];// 隐藏包数组		
			
	// 过滤的函数如下。
	function filterTree(e) {
		var text = e.target.value;// 获得文本框的value
		Ext.each(hiddenPkgs, function(n) {// 遍历隐藏包数组
			n.ui.show();// 显示符合条件的节点
		});
		if (!text) {// 如果没有过滤条件
			filter.clear();// 清空过滤器
			return;// 返回
		}
		nav.expandAll();// 展开所有树节点
		var re = new RegExp(Ext.escapeRe(text), 'i');// 关键的代码
		filter.filterBy(function(n) {// 关键
			var textval = n.text;
			return !n.isLeaf() || re.test(n.text);
		});
		// hide empty packages that weren't filtered
		nav.root.cascade(function(n) {
			if (!n.isLeaf() && n.ui.ctNode.offsetHeight < 3) {
				n.ui.hide();
				hiddenPkgs.push(n);
			}
			if (n.id != 'root') {
				if (!n.isLeaf() && n.ui.ctNode.offsetHeight >= 3
						&& hasChild(n, re) == false) {
					n.ui.hide();
					hiddenPkgs.push(n);
				}
			}
		});
	}
			
	// 没有叶子节点的目录屏蔽掉
	function hasChild(n, re) {
		var str = false;
		n.cascade(function(n1) {
			if (n1.isLeaf() && re.test(n1.text)) {
				str = true;
				return;
			}
		});
		return str;
	}
			
	// 创建一个数过滤器对象
	var filter = new Ext.tree.TreeFilter(nav, {
		clearBlank : true,
		autoClear : true
	});
	var hd = new Ext.Panel({
		border : false,
		layout : 'anchor',
		region : 'north',
		cls : 'docs-header',
		height : 25,
		width : 202,
		items : [ new Ext.Toolbar({
			cls : 'top-toolbar',
			items : [ ' ', new Ext.form.TextField({
				width : 125,
				emptyText : '搜索',
				listeners : {
					render : function(f) {
						f.el.on('keydown', filterTree, f, {
							buffer : 350
						});
					}
				}
			}), ' ', ' ', {
				iconCls : 'icon-expand-all',
				tooltip : 'Expand All',
				handler : function() {
					nav.root.expand(true);
				}
			}, '-', {
				iconCls : 'icon-collapse-all',
				tooltip : 'Collapse All',
				handler : function() {
					nav.root.collapse(true);
				}
			}, '-', {
				iconCls : 'editor',
				tooltip : 'editor',
				menu:  new Ext.menu.Menu([{
					iconCls : "",
					text: '编辑列表',
					handler : function() {
						editorFlag = editorFlag && true;
						if(editorFlag == false){
							editorFlag = true;
							 Ext.Msg.alert('提示', '编辑状态开启');
						}else{
							editorFlag = false;
							 Ext.Msg.alert('提示', '编辑状态关闭');
						}
						//nav.getLoader().load(nav.getRootNode());
						nav.getRootNode().reload();
						expandSL();
					}
				},{
					text : '一键恢复',
					iconCls : "",
					handler : function() {
						Ext.Msg.confirm('提示','一键恢复将还原成默认设置,是否继续!',function(btn, text){
							if(btn == 'yes' || btn =='ok'){
								Ext.Ajax.request({
									url: '/sso/peExttree_clearnUserDefine.action',
									success : function(response, option) {
										response = Ext.util.JSON.decode(response.responseText);
										nav.getRootNode().reload();
										expandSL();
										Ext.Msg.alert('提示', response.info);
									},
									failure : function(response, option) {
										response = Ext.util.JSON.decode(response.responseText);
										Ext.Msg.alert('提示', '恢复失败!');
									}	
								});	
							}
						});
						
					}
				}]) 
			} ]
		}) ]
	});
	

	var urlArr = [];
	var url = "/sso/managerMenu_getHotAccessMenu.action";
	$.ajax({
		type : "POST",
		timeout : 10000,
		url : encodeURI(url),
		cache : false,
		async : false,
		success : function(data) {
			urlArr = Ext.decode(data);
		}
	});

	if (urlArr.length == 0) {
		urlArr = [ {
			nid : '5',
			name : '角色管理',
			url : basePath + '/dev/'
		} ];
	}
	var _tabPanels = [];
	for ( var i = 0; i < urlArr.length; i++) {
		var vo = urlArr[i];
		var nhref = vo.url;

		var flag = nhref.lastIndexOf('?');
		if (flag != -1) {
			nhref += '&';
		} else {
			nhref += '?';
		}
		nhref += 'menuId=' + vo.nid + '&menuName=' + vo.name;

		var panel = {
			id : vo.nid,
			title : vo.name,
			closable : true,
			autoScroll : true,
			html : "<iframe width='100%' id='iframe-" + vo.nid
					+ "' height='100%' frameborder='0' src='" + nhref
					+ "'></iframe>" // 
		}
		_tabPanels.push(panel);
	}

	var pnNorth = new Ext.BoxComponent({ // raw
		region : 'north',
		el : 'north',
		minSize : 500,
		height : 37
	});

	var pnWest = new Ext.Panel({
		region : 'west',
		id : 'west-panel',
		layout : 'border',
		title : '功能列表',
		split : true,
		width : 225,
		minSize : 175,
		maxSize : 400,
		autoHeight : false,
		collapsible : true,
		items : [ hd, nav ]
	});
	var pnCenter = new Ext.TabPanel({
		id : 'center',
		region : 'center',
		deferredRender : true,
		activeTab : 0,
		enableTabScroll : true,
		layoutOnTabChange : true,
		items : _tabPanels,
		plugins : new Ext.ux.TabCloseMenu()

	});
	var viewport = new Ext.Viewport({
		layout : 'border',
		frame : true,
		items : [ pnNorth, pnWest, pnCenter ]
	});
	if (Ext.get("hideit") != null) {
		Ext.get("hideit").on('click', function() {
			var w = Ext.getCmp('west-panel');
			w.collapsed ? w.expand() : w.collapse();
		});
	}
	expandSL();
	show();
});		

function addTab(nname, nhref, nId) {
	var tabs = Ext.getCmp("center");
	var flag = nhref.lastIndexOf('?');
	if (flag != -1) {
		nhref += '&';
	} else {
		nhref += '?';
	}
	nhref += 'menuId=' + nId + '&menuName=' + nname;

	var t = tabs.getItem(nId);
	if (t) {
		tabs.getItem(nId).show();
	} else {
		tabs.add({
			id : nId,
			title : nname,
			html : "<iframe width='100%' id='iframe-" + nId
				+ "' height='100%' frameborder='0' src='" + nhref
				+ "'></iframe>",
			closable : true,
			autoScroll : true,
			deferredRender : false
		}).show();
	}
}