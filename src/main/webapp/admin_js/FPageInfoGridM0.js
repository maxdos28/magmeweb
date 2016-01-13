var fpageinfo_store_M0 = new Ext.data.JsonStore( {
	url : 'admin/f-page-info!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id', 'orderId', 'title'],
	pruneModifiedRecords : true,
	paramNames : {
		type : 'type',
		start : 'page.start',
		limit : 'page.limit'
	}
});

var sm_M0 = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar_M0 =  new Ext.PagingToolbar({pageSize:50,store:fpageinfo_store_M0,displayInfo:true,
	listeners : {'beforechange':function(page,o){
		o['type'] = 'M0';
	}
}
});
var tbar_M0=[{xtype:'buttongroup',items:[{id:'fpageinfo_M0_add',text:'添加'},{id:'fpageinfo_M0_delete',text:'删除'},{id:'fpageinfo_M0_commit',text:'提交'}]}];
var viewConfig_M0 = {forceFit:true,scrollToRecord:function(record){var index=fpageinfo_grid_M0.getStore().indexOf(record);this.focusRow(index);}};

var tf_noallow_blank_M0=new Ext.form.TextField( {allowBlank : false});
var col_orderId_M0={header:"序号",dataIndex:"orderId",width:50,editor:tf_noallow_blank_M0};
var col_title_M0={header:"标题",dataIndex:"title",width:50,editor:tf_noallow_blank_M0};

var fpageinfo_grid_M0 = new Ext.grid.EditorGridPanel({
	id : 'fpage_grid_M0',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页管理-推荐标签',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [col_orderId_M0, col_title_M0]
	}),
	region : 'center',
	store : fpageinfo_store_M0,
	viewConfig : viewConfig_M0,
	sm : sm_M0,
	bbar : bbar_M0,
	tbar : tbar_M0
});

//tab关闭事件
fpageinfo_grid_M0.on('beforeclose', beforeclose);

fpageinfo_grid_M0.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageinfo_grid_M0.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageinfo_grid_M0.getStore().remove(record);
			fpageinfo_grid_M0.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_title = record.isModified('title');
			if (!ret_title) {
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageinfo_grid_M0.getStore().remove(record);
			}
		}
		added = false;
	}
});

var add_btn_M0 = Ext.getCmp('fpageinfo_M0_add');
add_btn_M0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_M0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_M0.getStore().indexOf(record);
		fpageinfo_grid_M0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageinfo_grid_M0.getStore().recordType;
	var p = new Plant( {
		id: "", 
		orderId : "1",
		title : "",
		status: "1"
	});
	fpageinfo_grid_M0.stopEditing();
	fpageinfo_grid_M0.getStore().add(p);
	fpageinfo_grid_M0.getSelectionModel().clearSelections();
	fpageinfo_grid_M0.getSelectionModel().selectLastRow();
	fpageinfo_grid_M0.getView().scrollToRecord(p);
	added = true;
});

var del_btn_M0 = Ext.getCmp('fpageinfo_M0_delete');
del_btn_M0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_M0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_M0.getStore().indexOf(record);
		fpageinfo_grid_M0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	
	var records = fpageinfo_grid_M0.getSelectionModel().getSelections();
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
					GridUtil.deleteSelected(records, url, fpageinfo_grid_M0);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn_M0 = Ext.getCmp('fpageinfo_M0_commit');
commit_btn_M0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_M0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_M0.getStore().indexOf(record);
		fpageinfo_grid_M0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageinfo_grid_M0.getStore().getModifiedRecords();
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
			'type' : 'M0',
			'info' : info
		},
		success : function(e) {
			fpageinfo_grid_M0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});


