var admin_store = new Ext.data.JsonStore( {
	url : 'admin/admin!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime','updatedTime','userName', 'password','lastLoginTime','level'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var admin_grid = new Ext.grid.EditorGridPanel( {
	id : 'admin_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '分类管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "名字",
			dataIndex : "userName",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "权限",
			dataIndex : "level",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}]
	}),
	region : 'center',
	store : admin_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = admin_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : admin_store,
		displayInfo : true
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'admin_add',text:'添加'},{id:'admin_delete',text:'删除'},{id:'admin_commit',text:'提交'}]}]
});

admin_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

admin_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	admin_grid.stopEditing(false);
	if(added){
		if(record.modified == null){
			admin_grid.getStore().remove(record);
			admin_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_title = record.isModified('userName');
			var ret_parentName = record.isModified('level');
			if(!ret_title || !ret_parentName){
				//admin_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				admin_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});

var admin_add_btn = Ext.getCmp('admin_add');
admin_add_btn.on('click',function(e){
	if(added){
		var sm = admin_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = admin_grid.getStore().indexOf(record);
		admin_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = admin_grid.getStore().recordType;
	var p = new Plant({id:"", name:"",level:""});
	admin_grid.stopEditing();
	admin_grid.getStore().add(p);
	admin_grid.getSelectionModel().clearSelections();
	admin_grid.getSelectionModel().selectLastRow();
	admin_grid.getView().scrollToRecord(p);	
	added = true;
});

var admin_del_btn = Ext.getCmp('admin_delete');
admin_del_btn.on('click',function(e){
	var records = admin_grid.getSelectionModel().getSelections();
	if(records.length <= 0){
		return;
	} else {
		var ret;
		Ext.MessageBox.show({
           title:'删除',
           msg: '确定要删除？删除后不能恢复！',
           buttons: Ext.MessageBox.YESNO,
           fn: function(btn){
				if(btn == 'yes') {
					//admin_commit_btn.fireEvent('click');
					var url = 'admin/admin!delete.action';
					GridUtil.deleteSelected(records,url,admin_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});

var admin_commit_btn = Ext.getCmp('admin_commit');
admin_commit_btn.on('click',function(e){
	if(added){
		var sm = admin_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = admin_grid.getStore().indexOf(record);
		/*var ret_name = record.isModified('name');
		if(!ret_name){
			admin_grid.getStore().remove(record);
			admin_grid.getStore().getModifiedRecords().remove(record);
		}
		added = false;*/
		admin_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var records = admin_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/admin!commit.action',
		params :{'info' : info},
		success : function(o){
			//var obj = Ext.util.JSON.decode(o.responseText);
			admin_grid.getStore().reload();
		},
		failure : function(e){}
	});
});