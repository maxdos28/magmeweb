var fpageitem_store = new Ext.data.JsonStore( {
	url : 'admin/f-page-item!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id', 'templateId', 'orderId', 'type', 'title', 'description', 'publicationId','issueId', 'imageId', 'eventId', 'status', 'createdTime', 'updatedTime'],
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

var fpage_type_combo_store = new Ext.data.JsonStore({
	id : 'fpage_type_combo_store',
	fields:['id','value'],
	data : [{'id':'1','value':'杂志'},{'id':'2','value':'图片'},{'id':'3','value':'事件'}]
});
var fpage_type_combo = new Ext.form.ComboBox({
	id : 'fpage_type_combo',
	store : fpage_type_combo_store,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});

var sm = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar =  new Ext.PagingToolbar({pageSize:50,store:fpageitem_store,displayInfo:true});
var tbar=[{xtype:'buttongroup',items:[{id:'fpageitem_add',text:'添加'},{id:'fpageitem_delete',text:'删除'},{id:'fpageitem_commit',text:'提交'},{id:'fpageitem_valid',text:'生效'},{id:'fpageitem_invalid',text:'失效'}]}];
var viewConfig = {forceFit:true,scrollToRecord:function(record){var index=fpageitem_grid.getStore().indexOf(record);this.focusRow(index);}};

var tf_noallow_blank=new Ext.form.TextField( {allowBlank : false});
var tf_allow_blank=new Ext.form.TextField( {allowBlank : true});
var col_templateId={header:"模板ID",dataIndex:"templateId",width:50,editor:tf_noallow_blank};
var col_orderId={header:"序号",dataIndex:"orderId",width:50,editor:tf_noallow_blank};
var col_type={header:"元素类型",dataIndex:"type",width:50,editor:fpage_type_combo,renderer:function(value,cellmeta,record){if(value==1){return '杂志';}else if(value==2){return '图片';}else if(value==3){return '事件';}}};
var col_title={header:"标题",dataIndex:"title",width:50,editor:tf_allow_blank};
//var col_description={header:"描述",dataIndex:"description",width:50,editor:tf_allow_blank};
var col_publicationId={header:"杂志ID",dataIndex:"publicationId",width:50,editor:tf_allow_blank};
var col_issueId={header:"期刊ID",dataIndex:"issueId",width:50,editor:tf_allow_blank};
var col_imageId={header:"切米ID",dataIndex:"imageId",width:50,editor:tf_allow_blank};
var col_eventId={header:"事件ID",dataIndex:"eventId",width:50,editor:tf_allow_blank};
var col_status={header:"状态",dataIndex:"status",width:50,editor:fpage_status_combo,renderer:function(value,cellmeta,record){if(value==0){return '无效';}else{return '生效';}}};
var col_createdTime={header:"创建时间",dataIndex:"createdTime",width:50};
var col_updatedTime={header:"更新时间",dataIndex:"updatedTime",width:50};


var fpageitem_grid = new Ext.grid.EditorGridPanel({
	id : 'fpageitem_grid',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页管理-模板元素管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [col_templateId, col_orderId, col_type, col_title, col_publicationId, col_issueId, col_imageId, col_eventId, col_status, col_createdTime, col_updatedTime]
	}),
	region : 'center',
	store : fpageitem_store,
	viewConfig : viewConfig,
	sm : sm,
	bbar : bbar,
	tbar : tbar
});

//tab关闭事件
fpageitem_grid.on('beforeclose', beforeclose);

fpageitem_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageitem_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageitem_grid.getStore().remove(record);
			fpageitem_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_templateid = record.isModified('templateId');
			var ret_orderid = record.isModified('orderId');
			
			if (!ret_templateid || !ret_orderid) {
				//fpageitem_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageitem_grid.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var add_btn = Ext.getCmp('fpageitem_add');
add_btn.on('click', function(e) {
	if (added) {
		var sm = fpageitem_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageitem_grid.getStore().indexOf(record);
		fpageitem_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageitem_grid.getStore().recordType;
	var p = new Plant( {
		id: "", 
		templateId : "",
		orderId : "",
		type : "1",
		title : "",
		description : "",
		publicationId : "",
		issueId : "",
		imageId : "",
		eventId : "",
		status : 0
	});
	fpageitem_grid.stopEditing();
	fpageitem_grid.getStore().add(p);
	fpageitem_grid.getSelectionModel().clearSelections();
	fpageitem_grid.getSelectionModel().selectLastRow();
	fpageitem_grid.getView().scrollToRecord(p);
	added = true;
});

var del_btn = Ext.getCmp('fpageitem_delete');
del_btn.on('click', function(e) {
	var records = fpageitem_grid.getSelectionModel().getSelections();
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
					var url = 'admin/f-page-item!delete.action';
					GridUtil.deleteSelected(records, url, fpageitem_grid);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn = Ext.getCmp('fpageitem_commit');
commit_btn.on('click', function(e) {
	if (added) {
		var sm = fpageitem_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageitem_grid.getStore().indexOf(record);
		fpageitem_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageitem_grid.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		var ret_type = records[i].get('type');
		var ret_publicationid = records[i].get('publicationId');
		var ret_issueid = records[i].get('issueId');
		var ret_imageid = records[i].get('imageId');
		var ret_eventid = records[i].get('eventId');
		
		if (ret_type==1 && (!ret_publicationid||!ret_issueid)) {
			//fpageitem_grid.getStore().remove(record);
			Ext.MessageBox.alert("提示", "杂志类型必须填写杂志ID和期刊ID.");
			return;
		}
		else if(ret_type==2 && !ret_imageid){
			Ext.MessageBox.alert("提示", "图片类型必须填写图片ID.");
			return;
		}
		else if(ret_type==3 && !ret_eventid){
			Ext.MessageBox.alert("提示", "事件类型必须填写事件ID.");
			return;
		}
		
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/f-page-item!commit.action',
		params : {
			'info' : info
		},
		success : function(e) {
			fpageitem_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var valid_btn = Ext.getCmp('fpageitem_valid');
valid_btn.on('click', function(e) {
	var records = fpageitem_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-item!valid.action',
		params : {
			'ids' : ids
		},
		success : function(e) {
			fpageitem_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});


var invalid_btn = Ext.getCmp('fpageitem_invalid');
invalid_btn.on('click', function(e) {
	var records = fpageitem_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-item!invalid.action',
		params : {
			'ids' : ids
		},
		success : function(e) {
			fpageitem_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});