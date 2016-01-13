var fpageinfo_store_J0 = new Ext.data.JsonStore( {
	url : 'admin/f-page-info!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id', 'orderId', 'title', 'issueId', 'pageNo', 'status', 'reserved1', 'createdTime', 'updatedTime'],
	pruneModifiedRecords : true,
	paramNames : {
		type : 'type',
		start : 'page.start',
		limit : 'page.limit'
	}
});

var fpage_status_combo_store_J0 = new Ext.data.JsonStore({
	id : 'fpage_status_combo_store_J0',
	fields:['id','value'],
	data : [{'id':'0','value':'无效'},{'id':'1','value':'生效'}]
});
var fpage_status_combo_J0 = new Ext.form.ComboBox({
	id : 'fpage_status_combo_J0',
	store : fpage_status_combo_store_J0,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});

var sm_J0 = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar_J0 =  new Ext.PagingToolbar({pageSize:50,store:fpageinfo_store_J0,displayInfo:true,
	listeners : {'beforechange':function(page,o){
		o['type'] = 'J0';
	}
}
});
var tbar_J0=[{xtype:'buttongroup',items:[{id:'fpageinfo_J0_add',text:'添加'},{id:'fpageinfo_J0_delete',text:'删除'},{id:'fpageinfo_J0_commit',text:'提交'},{id:'fpageinfo_J0_valid',text:'生效'},{id:'fpageinfo_J0_invalid',text:'失效'}]}];
var viewConfig_J0 = {forceFit:true,scrollToRecord:function(record){var index=fpageinfo_grid_J0.getStore().indexOf(record);this.focusRow(index);}};

var tf_noallow_blank_J0=new Ext.form.TextField( {allowBlank : false});
var col_orderId_J0={header:"序号",dataIndex:"orderId",width:50,editor:tf_noallow_blank_J0};
var col_title_J0={header:"标题",dataIndex:"title",width:50,editor:tf_noallow_blank_J0};
//var col_description_J0={header:"描述",dataIndex:"description",width:50,editor:tf_noallow_blank_J0};
//var col_publicationId_J0={header:"杂志ID",dataIndex:"publicationId",width:50,editor:tf_noallow_blank_J0};
var col_issueId_J0={header:"期刊ID",dataIndex:"issueId",width:50,editor:tf_noallow_blank_J0};
var col_pageNo_J0={header:"页码",dataIndex:"pageNo",width:50,editor:tf_noallow_blank_J0};
//var col_tagId={header:"标签ID",dataIndex:"tagId",width:50,editor:tf_noallow_blank_J0};
//var col_imgFile_J0={header:"图片文件",dataIndex:"imgFile",width:50,editor:tf_noallow_blank_J0};
var col_status_J0={header:"状态",dataIndex:"status",width:50,editor:fpage_status_combo_J0,renderer:function(value,cellmeta,record){if(value==0){return '无效';}else{return '生效';}}};
var col_reserved1_J0={header:"标签样式",dataIndex:"reserved1",width:50,editor:tf_noallow_blank_J0};
var col_createdTime_J0={header:"创建时间",dataIndex:"createdTime",width:50};
var col_updatedTime_J0={header:"更新时间",dataIndex:"updatedTime",width:50};

var fpageinfo_grid_J0 = new Ext.grid.EditorGridPanel({
	id : 'fpage_grid_J0',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页管理-云标签',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [col_orderId_J0, col_title_J0, col_issueId_J0, col_pageNo_J0, col_status_J0, col_reserved1_J0, col_createdTime_J0, col_updatedTime_J0]
	}),
	region : 'center',
	store : fpageinfo_store_J0,
	viewConfig : viewConfig_J0,
	sm : sm_J0,
	bbar : bbar_J0,
	tbar : tbar_J0
});

//tab关闭事件
fpageinfo_grid_J0.on('beforeclose', beforeclose);

fpageinfo_grid_J0.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageinfo_grid_J0.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageinfo_grid_J0.getStore().remove(record);
			fpageinfo_grid_J0.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_orderid = record.isModified('orderId');
			var ret_title = record.isModified('title');
			var ret_issueid = record.isModified('issueId');
			var ret_pageno = record.isModified('pageNo');
			if (!ret_orderid || !ret_title || !ret_issueid || !ret_pageno) {
				//fpageinfo_grid_J0.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageinfo_grid_J0.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var add_btn_J0 = Ext.getCmp('fpageinfo_J0_add');
add_btn_J0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_J0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_J0.getStore().indexOf(record);
		fpageinfo_grid_J0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageinfo_grid_J0.getStore().recordType;
	var p = new Plant( {
		id: "", 
		orderId : "",
		title : "",
		issueId : "",
		pageNo : "",
		status : 0
	});
	fpageinfo_grid_J0.stopEditing();
	fpageinfo_grid_J0.getStore().add(p);
	fpageinfo_grid_J0.getSelectionModel().clearSelections();
	fpageinfo_grid_J0.getSelectionModel().selectLastRow();
	fpageinfo_grid_J0.getView().scrollToRecord(p);
	added = true;
});

var del_btn_J0 = Ext.getCmp('fpageinfo_J0_delete');
del_btn_J0.on('click', function(e) {
	var records = fpageinfo_grid_J0.getSelectionModel().getSelections();
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
					var url = 'admin/f-page-info!delete.action';
					GridUtil.deleteSelected(records, url, fpageinfo_grid_J0);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn_J0 = Ext.getCmp('fpageinfo_J0_commit');
commit_btn_J0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_J0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_J0.getStore().indexOf(record);
		fpageinfo_grid_J0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageinfo_grid_J0.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/f-page-info!commit.action',
		params : {
			'type' : 'J0',
			'info' : info
		},
		success : function(e) {
			fpageinfo_grid_J0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var valid_btn_J0 = Ext.getCmp('fpageinfo_J0_valid');
valid_btn_J0.on('click', function(e) {
	var records = fpageinfo_grid_J0.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-info!valid.action',
		params : {
			'type' : 'J0',
			'ids' : ids
		},
		success : function(e) {
			fpageinfo_grid_J0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});


var invalid_btn_J0 = Ext.getCmp('fpageinfo_J0_invalid');
invalid_btn_J0.on('click', function(e) {
	var records = fpageinfo_grid_J0.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-info!invalid.action',
		params : {
			'type' : 'J0',
			'ids' : ids
		},
		success : function(e) {
			fpageinfo_grid_J0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});
