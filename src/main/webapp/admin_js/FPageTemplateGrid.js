var fpagetemplate_store = new Ext.data.JsonStore( {
	url : 'admin/f-page-template!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id', 'orderId', 'title', 'status'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var fpage_status_combo_store = new Ext.data.JsonStore({
	id : 'fpage_status_combo_store',
	fields:['id','value'],
	data : [{'id':'0','value':'无效'},{'id':'1','value':'生效'}]
});
var fpage_status_combo = new Ext.form.ComboBox({
	id : 'fpage_status_combo',
	store : fpage_status_combo_store,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});

var sm = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar =  new Ext.PagingToolbar({pageSize:50,store:fpagetemplate_store,displayInfo:true});
var tbar=[{xtype:'buttongroup',items:[{id:'fpagetemplate_add',text:'添加'},{id:'fpagetemplate_delete',text:'删除'},{id:'fpagetemplate_commit',text:'提交'},{id:'fpagetemplate_valid',text:'生效'},{id:'fpagetemplate_invalid',text:'失效'}]}];
var viewConfig = {forceFit:true,scrollToRecord:function(record){var index=fpagetemplate_grid.getStore().indexOf(record);this.focusRow(index);}};

var tf_noallow_blank=new Ext.form.TextField( {allowBlank : false});
var col_id={header:"模板ID",dataIndex:"id",width:10};
var col_orderId={header:"序号",dataIndex:"orderId",width:10,editor:tf_noallow_blank};
var col_title={header:"标题",dataIndex:"title",width:30,editor:tf_noallow_blank};
var col_status={header:"状态",dataIndex:"status",width:10,editor:fpage_status_combo,renderer:function(value,cellmeta,record){if(value==0){return '无效';}else{return '生效';}}};

var fpagetemplate_grid = new Ext.grid.EditorGridPanel({
	id : 'fpagetemplate_grid',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页管理-模板管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [col_id, col_orderId, col_title, col_status]
	}),
	region : 'center',
	store : fpagetemplate_store,
	viewConfig : viewConfig,
	sm : sm,
	bbar : bbar,
	tbar : tbar
});

//tab关闭事件
fpagetemplate_grid.on('beforeclose', beforeclose);

fpagetemplate_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpagetemplate_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpagetemplate_grid.getStore().remove(record);
			fpagetemplate_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_orderid = record.isModified('orderId');
			var ret_title = record.isModified('title');
			if (!ret_orderid || !ret_title) {
				//fpagetemplate_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpagetemplate_grid.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var add_btn = Ext.getCmp('fpagetemplate_add');
add_btn.on('click', function(e) {
	if (added) {
		var sm = fpagetemplate_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpagetemplate_grid.getStore().indexOf(record);
		fpagetemplate_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpagetemplate_grid.getStore().recordType;
	var p = new Plant( {
		id: "", 
		orderId : "",
		title : "",
		status : 0
	});
	fpagetemplate_grid.stopEditing();
	fpagetemplate_grid.getStore().add(p);
	fpagetemplate_grid.getSelectionModel().clearSelections();
	fpagetemplate_grid.getSelectionModel().selectLastRow();
	fpagetemplate_grid.getView().scrollToRecord(p);
	added = true;
});

var del_btn = Ext.getCmp('fpagetemplate_delete');
del_btn.on('click', function(e) {
	var records = fpagetemplate_grid.getSelectionModel().getSelections();
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
					var url = 'admin/f-page-template!delete.action';
					GridUtil.deleteSelected(records, url, fpagetemplate_grid);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn = Ext.getCmp('fpagetemplate_commit');
commit_btn.on('click', function(e) {
	if (added) {
		var sm = fpagetemplate_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpagetemplate_grid.getStore().indexOf(record);
		fpagetemplate_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpagetemplate_grid.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/f-page-template!commit.action',
		params : {
			'info' : info
		},
		success : function(e) {
			fpagetemplate_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var valid_btn = Ext.getCmp('fpagetemplate_valid');
valid_btn.on('click', function(e) {
	var records = fpagetemplate_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-template!valid.action',
		params : {
			'ids' : ids
		},
		success : function(e) {
			fpagetemplate_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});


var invalid_btn = Ext.getCmp('fpagetemplate_invalid');
invalid_btn.on('click', function(e) {
	var records = fpagetemplate_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-template!invalid.action',
		params : {
			'ids' : ids
		},
		success : function(e) {
			fpagetemplate_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});