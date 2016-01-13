var fpageinfo_store_L1 = new Ext.data.JsonStore( {
	url : 'admin/f-page-info!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id', 'orderId', 'issueId', 'imgFile', 'status'],
	pruneModifiedRecords : true,
	paramNames : {
		type : 'type',
		start : 'page.start',
		limit : 'page.limit'
	}
});

var sm_L1 = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar_L1 =  new Ext.PagingToolbar({pageSize:50,store:fpageinfo_store_L1,displayInfo:true,
	listeners : {'beforechange':function(page,o){
		o['type'] = 'L1';
	}
  }
});
var tbar_L1=[{xtype:'buttongroup',items:[{id:'fpageinfo_L1_add',text:'添加'},{id:'fpageinfo_L1_delete',text:'删除'},{id:'fpageinfo_L1_commit',text:'提交'}]}];
var viewConfig_L1 = {forceFit:true,scrollToRecord:function(record){var index=fpageinfo_grid_L1.getStore().indexOf(record);this.focusRow(index);}};

var tf_noallow_blank_L1=new Ext.form.TextField( {allowBlank : false});
var col_orderId_L1={header:"序号",dataIndex:"orderId",width:50,editor:tf_noallow_blank_L1};
var col_issueId_L1={header:"期刊ID",dataIndex:"issueId",width:50,editor:tf_noallow_blank_L1};

var fpageinfo_grid_L1 = new Ext.grid.EditorGridPanel({
	id : 'fpage_grid_L1',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '热门杂志推荐',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [col_orderId_L1, col_issueId_L1]
	}),
	region : 'center',
	store : fpageinfo_store_L1,
	viewConfig : viewConfig_L1,
	sm : sm_L1,
	bbar : bbar_L1,
	tbar : tbar_L1
});

//tab关闭事件
fpageinfo_grid_L1.on('beforeclose', beforeclose);

fpageinfo_grid_L1.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageinfo_grid_L1.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageinfo_grid_L1.getStore().remove(record);
			fpageinfo_grid_L1.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_orderid = record.isModified('orderId');
			if (!ret_orderid) {
				//fpageinfo_grid_L1.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageinfo_grid_L1.getStore().remove(record);
			}
		}
		added = false;
	}
});

var add_btn_L1 = Ext.getCmp('fpageinfo_L1_add');
add_btn_L1.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_L1.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_L1.getStore().indexOf(record);
		fpageinfo_grid_L1.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageinfo_grid_L1.getStore().recordType;
	var p = new Plant( {
		id: "", 
		orderId : "",
		publicationId : "",
		issueId : "",
		imgFile : "",
		status : 1
	});
	fpageinfo_grid_L1.stopEditing();
	fpageinfo_grid_L1.getStore().add(p);
	fpageinfo_grid_L1.getSelectionModel().clearSelections();
	fpageinfo_grid_L1.getSelectionModel().selectLastRow();
	fpageinfo_grid_L1.getView().scrollToRecord(p);
	added = true;
});

var del_btn_L1 = Ext.getCmp('fpageinfo_L1_delete');
del_btn_L1.on('click', function(e) {
	var records = fpageinfo_grid_L1.getSelectionModel().getSelections();
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
					GridUtil.deleteSelected(records, url, fpageinfo_grid_L1);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn_L1 = Ext.getCmp('fpageinfo_L1_commit');
commit_btn_L1.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_L1.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_L1.getStore().indexOf(record);
		fpageinfo_grid_L1.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageinfo_grid_L1.getStore().getModifiedRecords();
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
			'type' : 'L1',
			'info' : info
		},
		success : function(e) {
			fpageinfo_grid_L1.getStore().reload();
		},
		failure : function(e) {
		}
	});
});
