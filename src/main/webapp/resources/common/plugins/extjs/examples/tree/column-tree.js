/*
 * Ext JS Library 2.3.0
 * Copyright(c) 2006-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){
    var tree = new Ext.tree.ColumnTree({
        width: 1000,
        height:600,
//        autoHeight:true,
        rootVisible:false,
        autoScroll:true,
        title: 'Example Tasks',
        lines : true,// 虚线
        renderTo: Ext.getBody(),
        listeners : {
        	'checkChange':function(){}
        },
        columns:[{
            header:'Task',
            width:300,
            dataIndex:'text'
        },{
            header:'all',
            width:150,
            dataIndex:'all'
        },{
            header:'list',
            width:200,
            dataIndex:'look'
        },{
            header:'others',
            width:300,
            dataIndex:'others'
        }/*,{
            header:'delete',
            width:100,
            dataIndex:'del'
        },{
            header:'update',
            width:100,
            dataIndex:'up'
        }*/],

        loader: new Ext.tree.TreeLoader({
           dataUrl:'/sso/admin/pePriCategoryConfig_getTreeData.action',
            listeners :{'beforeload':function(treeloader, node) {
            	if(node.id == '0'){
				treeloader.baseParams = {
					nodeId : node.id,
					role:'40288aae34ac763d0134acfb561c0003',
					method : 'tree'
				};
				}else{
						return false;
					}
			}},
//        	dataUrl:"column-data.json",
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
            
        }),
	
        root: new Ext.tree.AsyncTreeNode({
            text:'Tasks',
            id:'0'
        }),
        expanded: true,
        tbar : [{
					iconCls : 'icon-collapse-all',
					tooltip : '合并',
					handler : function() {
						tree.collapseAll();
					}
				}, {
					iconCls : 'icon-expand-all',
					tooltip : '展开',
					handler : function() {
						tree.expandAll();
					}
				}],
        buttons:[{
        	text:'back',
        	handler:function(){
//        		 ;
        		var nodeIds = document.getElementsByName("nodeName");
        		var menuIds = '';
        		for(var i=0;i<nodeIds.length;i++){
        			if(nodeIds[i].checked){
        				menuIds+=nodeIds[i].value+","
        			}
        		}
				Ext.Ajax.request({
					url: '/sso/admin/pePriCategoryConfig_saveRoleMenus.action',
				    params:{
						role:'40288aae34ac763d0134acfb561c0003',
						menuIds:menuIds
					},
					success: function(response, opts) {
						alert('设置成功');
				  	},
					failure: function(response, opts) {
						alert('设置失败');
				 	}
				});
			}
		}]
    });

    tree.expandAll();
});