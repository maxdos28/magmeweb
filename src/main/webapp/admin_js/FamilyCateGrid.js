var familycate_store = new Ext.data.JsonStore( {
	url : 'admin/family-cate!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'name', 'description', 'parentid', 'categoryid', 'status', 'sortOrder' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
// 类目下拉框对应
var familycate_cateid_store = new Ext.data.JsonStore({
	url : 'admin/category!getByChildIds.action',
	fields : ['id','name']
});

var familycate_cateid_combo = new Ext.form.ComboBox({
	id : 'familycate_cateid_combo',
	store : familycate_cateid_store,
	mode : 'local',
	valueField : 'id',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

familycate_cateid_combo.on('beforequery',function(){
	familycate_cateid_store.load();
});

var familycate_status_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'无效'},{'value':1,'name':'有效'}]
});

var familycate_status_combo = new Ext.form.ComboBox({
	id : 'familycate_status_combo',
	store : familycate_status_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var familycate_grid = new Ext.grid.EditorGridPanel( {
	id : 'familycate_grid',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : 'familycate',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "群族分类名称",
			dataIndex : "name",
			width : 120,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "描述",
			dataIndex : "description",
			width : 120,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "所属父类",
			dataIndex : "parentid",
			width : 120,
			editor : new Ext.form.NumberField( {
				allowBlank : false
			})
		}, {
			header : "类目",
			dataIndex : "categoryid",
			width : 120,
			editor : familycate_cateid_combo
		}, {
			header : "状态",
			dataIndex : "status",
			width : 120,
			editor : familycate_status_combo
		}, {
			header : "顺序",
			dataIndex : "sortOrder",
			width : 120,
			editor : new Ext.form.NumberField( {
				allowBlank : true
			})
		}]
	}),
	region : 'center',
	store : familycate_store,
	viewConfig : {
		forceFit : true,
		scrollToRecord : function(record) {
			var index = familycate_grid.getStore().indexOf(record);
			this.focusRow(index);
		}
	},
	sm : new Ext.grid.RowSelectionModel( {
		singleSelect : false
	}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : familycate_store,
		displayInfo : true
	}),
	tbar : [ {
		xtype : 'buttongroup',
		items : [ {
			id : 'familycate_add',
			text : '添加',
			disabled : false
		}, {
			id : 'familycate_delete',
			text : '删除',
			disabled : false
		}, {
			id : 'familycate_commit',
			text : '提交',
			disabled : false
		}, {
			id:'familycate_memory_commit',
			text:'更新内存',
			disabled : false
		}]
	} ]
});

familycate_grid.on('beforeclose', function(panel) {
	tab.remove(panel, false);
	panel.hide();
	return false;
});

//tab关闭事件
familycate_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

familycate_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	familycate_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			familycate_grid.getStore().remove(record);
			familycate_grid.getStore().getModifiedRecords().remove(record);
			added = false;
		} else {
			var ret_name = record.isModified('name');
			var ret_parentid = record.isModified('parentid');
			var ret_categoryid = record.isModified('categoryid');
			var ret_status = record.isModified('status');
			if (!ret_name||!ret_parentid||!ret_categoryid||!ret_status) {
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				familycate_grid.getStore().getModifiedRecords().remove(record);
			}else{
				added = false;
			}
		}
	}
});

var familycate_add_btn = Ext.getCmp('familycate_add');
familycate_add_btn.on('click', function(e) {
	if (added) {
		var sm = familycate_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = familycate_grid.getStore().indexOf(record);
		familycate_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = familycate_grid.getStore().recordType;
	var p = new Plant( {
		id : "",
		name : "",
		description : "",
		parentid : "",
		categoryid : "",
		status : "",
		sortOrder : ""
	});
	familycate_grid.stopEditing();
	familycate_grid.getStore().add(p);
	familycate_grid.getSelectionModel().clearSelections();
	familycate_grid.getSelectionModel().selectLastRow();
	familycate_grid.getView().scrollToRecord(p);
	added = true;
});

var familycate_delete_btn = Ext.getCmp('familycate_delete');
familycate_delete_btn.on('click', function(e) {
	var records = familycate_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	} else {
		var ret;
		Ext.MessageBox.show( {
			title : '删除',
			msg : '确定要删除？删除后不能恢复！',
			buttons : Ext.MessageBox.YESNO,
			fn : function(btn) {
				if (btn == 'yes') {
					var url = 'admin/family-cate!delete.action';
					GridUtil.deleteSelected(records, url, familycate_grid);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var familycate_commit_btn = Ext.getCmp('familycate_commit');
familycate_commit_btn.on('click', function(e) {

	if (added) {
		var sm = familycate_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = familycate_grid.getStore().indexOf(record);
		familycate_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = familycate_grid.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/family-cate!commit.action',
		params : {
			'info' : info
		},
		success : function(e) {
			familycate_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

// 更新内存
var familycate_memory_commit_btn = Ext.getCmp('familycate_memory_commit');
familycate_memory_commit_btn.on('click',function(e){
	Ext.Ajax.request({
		url : 'admin/family-cate!upMemory.action', // 更新memoryCach
		//params : {
		//	'info' : info,'answer' : text
		//},
		success : function(e) {
			alert('更新内存成功 ！')
		},
		failure : function(e) {}
	});
});