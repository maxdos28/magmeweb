var fpageinfo_store_H0 = new Ext.data.JsonStore( {
	url : 'admin/f-page-info!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id', 'orderId', 'title', 'description', 'publicationId', 'issueId', 'pageNo', 'imageId', 'imgFile', 'status', 'reserved2', 'createdTime', 'updatedTime'],
	pruneModifiedRecords : true,
	paramNames : {
		type : 'type',
		start : 'page.start',
		limit : 'page.limit'
	}
});

var fpage_status_combo_store_H0 = new Ext.data.JsonStore({
	id : 'fpage_status_combo_store_H0',
	fields:['id','value'],
	data : [{'id':'0','value':'无效'},{'id':'1','value':'生效'}]
});
var fpage_status_combo_H0 = new Ext.form.ComboBox({
	id : 'fpage_status_combo_H0',
	store : fpage_status_combo_store_H0,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});

var sm_H0 = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar_H0 =  new Ext.PagingToolbar({pageSize:50,store:fpageinfo_store_H0,displayInfo:true,
	listeners : {'beforechange':function(page,o){
		o['type'] = 'H0';
	}
  }
});
var tbar_H0=[{xtype:'buttongroup',items:[{id:'fpageinfo_H0_add',text:'添加'},{id:'fpageinfo_H0_delete',text:'删除'},{id:'fpageinfo_H0_commit',text:'提交'},{id:'fpageinfo_H0_valid',text:'生效'},{id:'fpageinfo_H0_invalid',text:'失效'}]},
{
	xtype:'buttongroup',
	items : [{
		xtype:'form',
		fileUpload : true,
		method:'POST',
		enctype:'multipart/form-data', 
		width : 200,
		height : 25,
		id : 'upload-form_H0',
		layout:'column',
		items:[{xtype:'textfield',inputType:'file',name : 'img',id:'himg'}]
	},{
		text:'上传图片(小)',
		handler : function(e){
			var records = fpageinfo_grid_H0.getSelectionModel().getSelections();
			if(records.length == 0){
				Ext.MessageBox.alert("提示","必须选择一个");
				return;
			} else if(records.length > 1){
				Ext.MessageBox.alert("提示","只能选择一个");
				return;
			}
			if (Ext.getCmp("himg").getRawValue() == "") {
				Ext.MessageBox.alert("提示","请选择上传文件");
				return;
			}
			var record = records[0];
			Ext.getCmp('upload-form_H0').getForm().submit({
                    url: 'admin/f-page-info!upload.action',
                    params : {'type':'H0', 'uploadFileId':records[0].get('id')},
                    waitMsg: 'Uploading your image...',
                    success: function(form, action){
                    	record.set('imgFile', action.result.newImgFileName);
                    	//fpageinfo_grid_H0.getStore().reload();
                        //msg('Success', 'Processed file "'+o.result.file+'" on the server');
                    },
                 failure : function(form, action)
                 {
                 	Ext.MessageBox.alert("提示","出错了！");
                 }
            });
		}
	},{
		text:'上传图片(大)',
		handler : function(e){
			var records = fpageinfo_grid_H0.getSelectionModel().getSelections();
			if(records.length == 0){
				Ext.MessageBox.alert("提示","必须选择一个");
				return;
			} else if(records.length > 1){
				Ext.MessageBox.alert("提示","只能选择一个");
				return;
			}
			alert(Ext.getCmp("himg").getRawValue());
			if (Ext.getCmp("himg").getRawValue() == "") {
				Ext.MessageBox.alert("提示","请选择上传文件");
				return;
			}
			var record = records[0];
			Ext.getCmp('upload-form_H0').getForm().submit({
                    url: 'admin/f-page-info!upload.action',
                    params : {'type':'H0', 'uploadFileId':records[0].get('id')},
                    waitMsg: 'Uploading your image...',
                    success: function(form, action){
                    	record.set('reserved2', action.result.newImgFileName);
                    	//fpageinfo_grid_H0.getStore().reload();
                        //msg('Success', 'Processed file "'+o.result.file+'" on the server');
                    },
                 failure : function(form, action)
                 {
                 	Ext.MessageBox.alert("提示","出错了！");
                 }
            });
		}
	}]
}];
var viewConfig_H0 = {forceFit:true,scrollToRecord:function(record){var index=fpageinfo_grid_H0.getStore().indexOf(record);this.focusRow(index);}};

var tf_noallow_blank_H0=new Ext.form.TextField( {allowBlank : false});
var col_id_H0={header:"切米ID",dataIndex:"id",width:50};
var col_orderId_H0={header:"序号",dataIndex:"orderId",width:50,editor:tf_noallow_blank_H0};
var col_title_H0={header:"标题",dataIndex:"title",width:50,editor:tf_noallow_blank_H0};
var col_description_H0={header:"描述",dataIndex:"description",width:50,editor:tf_noallow_blank_H0};
var col_publicationId_H0={header:"杂志ID",dataIndex:"publicationId",width:50,editor:tf_noallow_blank_H0};
var col_issueId_H0={header:"期刊ID",dataIndex:"issueId",width:50,editor:tf_noallow_blank_H0};
var col_pageNo_H0={header:"页码",dataIndex:"pageNo",width:50,editor:tf_noallow_blank_H0};
var col_imageId_H0={header:"图片ID",dataIndex:"imageId",width:50,editor:tf_noallow_blank_H0};
var col_imgFile_H0={header:"图片文件(小)",dataIndex:"imgFile",width:50,editor:tf_noallow_blank_H0};
var col_reserved2_H0={header:"图片文件(大)",dataIndex:"reserved2",width:50,editor:tf_noallow_blank_H0};
var col_status_H0={header:"状态",dataIndex:"status",width:50,editor:fpage_status_combo_H0,renderer:function(value,cellmeta,record){if(value==0){return '无效';}else{return '生效';}}};
var col_createdTime_H0={header:"创建时间",dataIndex:"createdTime",width:50};
var col_updatedTime_H0={header:"更新时间",dataIndex:"updatedTime",width:50};

var fpageinfo_grid_H0 = new Ext.grid.EditorGridPanel({
	id : 'fpage_grid_H0',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页管理-切米',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [col_id_H0, col_orderId_H0, col_title_H0, col_description_H0, col_publicationId_H0, col_issueId_H0, col_pageNo_H0, col_imageId_H0, col_imgFile_H0, col_reserved2_H0, col_status_H0, col_createdTime_H0, col_updatedTime_H0]
	}),
	region : 'center',
	store : fpageinfo_store_H0,
	viewConfig : viewConfig_H0,
	sm : sm_H0,
	bbar : bbar_H0,
	tbar : tbar_H0
});

//tab关闭事件
fpageinfo_grid_H0.on('beforeclose', beforeclose);

fpageinfo_grid_H0.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageinfo_grid_H0.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageinfo_grid_H0.getStore().remove(record);
			fpageinfo_grid_H0.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_orderid = record.isModified('orderId');
			var ret_title = record.isModified('title');
			var ret_description = record.isModified('description');
			var ret_publicationid = record.isModified('publicationId');
			var ret_issueid = record.isModified('issueId');
			var ret_pageno = record.isModified('pageNo');
			var ret_imageid = record.isModified('imageId');
			var ret_imgfile = record.isModified('imgFile');
			var ret_reserved2 = record.isModified('reserved2');
			if (!ret_orderid || !ret_title || !ret_description || !ret_publicationid || !ret_issueid || !ret_pageno || !ret_imageid || !ret_imgfile || !ret_reserved2) {
				//fpageinfo_grid_H0.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageinfo_grid_H0.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var add_btn_H0 = Ext.getCmp('fpageinfo_H0_add');
add_btn_H0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_H0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_H0.getStore().indexOf(record);
		fpageinfo_grid_H0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageinfo_grid_H0.getStore().recordType;
	var p = new Plant( {
		id: "", 
		orderId : "0",
		title : "",
		description : "",
		publicationId : "",
		issueId : "",
		pageNo : "",
		imageId : "",
		imgFile : "",
		status : 0
	});
	fpageinfo_grid_H0.stopEditing();
	fpageinfo_grid_H0.getStore().add(p);
	fpageinfo_grid_H0.getSelectionModel().clearSelections();
	fpageinfo_grid_H0.getSelectionModel().selectLastRow();
	fpageinfo_grid_H0.getView().scrollToRecord(p);
	added = true;
});

var del_btn_H0 = Ext.getCmp('fpageinfo_H0_delete');
del_btn_H0.on('click', function(e) {
	var records = fpageinfo_grid_H0.getSelectionModel().getSelections();
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
					GridUtil.deleteSelected(records, url, fpageinfo_grid_H0);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn_H0 = Ext.getCmp('fpageinfo_H0_commit');
commit_btn_H0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_H0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_H0.getStore().indexOf(record);
		fpageinfo_grid_H0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageinfo_grid_H0.getStore().getModifiedRecords();
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
			'type' : 'H0',
			'info' : info
		},
		success : function(e) {
			fpageinfo_grid_H0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var valid_btn_H0 = Ext.getCmp('fpageinfo_H0_valid');
valid_btn_H0.on('click', function(e) {
	var records = fpageinfo_grid_H0.getSelectionModel().getSelections();
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
			'type' : 'H0',
			'ids' : ids
		},
		success : function(e) {
			fpageinfo_grid_H0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});


var invalid_btn_H0 = Ext.getCmp('fpageinfo_H0_invalid');
invalid_btn_H0.on('click', function(e) {
	var records = fpageinfo_grid_H0.getSelectionModel().getSelections();
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
			'type' : 'H0',
			'ids' : ids
		},
		success : function(e) {
			fpageinfo_grid_H0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});
