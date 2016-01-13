var fpageinfo_store_L0 = new Ext.data.JsonStore( {
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

var sm_L0 = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar_L0 =  new Ext.PagingToolbar({pageSize:50,store:fpageinfo_store_L0,displayInfo:true,
	listeners : {'beforechange':function(page,o){
		o['type'] = 'L2';
	}
  }
});
var tbar_L0=[{xtype:'buttongroup',items:[{id:'fpageinfo_L0_add',text:'添加'},{id:'fpageinfo_L0_delete',text:'删除'},{id:'fpageinfo_L0_commit',text:'提交'}]},
{
	xtype:'buttongroup',
	items : [{
		xtype:'form',
		fileUpload : true,
		method:'POST',
		enctype:'multipart/form-data', 
		width : 200,
		height : 25,
		id : 'upload-form_L0',
		layout:'column',
		items:[{xtype:'textfield',inputType:'file',name : 'img',id:'limg'}]
	},{
		text:'上传图片',
		handler : function(e){
			var records = fpageinfo_grid_L0.getSelectionModel().getSelections();
			if(records.length == 0){
				Ext.MessageBox.alert("提示","必须选择一个");
				return;
			} else if(records.length > 1){
				Ext.MessageBox.alert("提示","只能选择一个");
				return;
			}
			if (Ext.getCmp("limg").getRawValue() == "") {
				Ext.MessageBox.alert("提示","请选择上传文件");
				return;
			}
			var record = records[0];
			Ext.getCmp('upload-form_L0').getForm().submit({
                    url: 'admin/f-page-info!upload.action',
                    params : {'type':'L2', 'uploadFileId':records[0].get('id')},
                    waitMsg: 'Uploading your image...',
                    success: function(form, action){
                    	record.set('imgFile', action.result.newImgFileName);
                    	//fpageinfo_grid_L0.getStore().reload();
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
var viewConfig_L0 = {forceFit:true,scrollToRecord:function(record){var index=fpageinfo_grid_L0.getStore().indexOf(record);this.focusRow(index);}};

var tf_noallow_blank_L0=new Ext.form.TextField( {allowBlank : false});
var col_orderId_L0={header:"序号",dataIndex:"orderId",width:50,editor:tf_noallow_blank_L0};
var col_issueId_L0={header:"期刊ID",dataIndex:"issueId",width:50,editor:tf_noallow_blank_L0};
var col_imgFile_L0={header:"图片文件",dataIndex:"imgFile",width:50,editor:tf_noallow_blank_L0};

var fpageinfo_grid_L0 = new Ext.grid.EditorGridPanel({
	id : 'fpage_grid_L0',
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
		columns : [col_orderId_L0, col_issueId_L0, col_imgFile_L0]
	}),
	region : 'center',
	store : fpageinfo_store_L0,
	viewConfig : viewConfig_L0,
	sm : sm_L0,
	bbar : bbar_L0,
	tbar : tbar_L0
});

//tab关闭事件
fpageinfo_grid_L0.on('beforeclose', beforeclose);

fpageinfo_grid_L0.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageinfo_grid_L0.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageinfo_grid_L0.getStore().remove(record);
			fpageinfo_grid_L0.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_orderid = record.isModified('orderId');
			if (!ret_orderid) {
				//fpageinfo_grid_L0.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageinfo_grid_L0.getStore().remove(record);
			}
		}
		added = false;
	}
});

var add_btn_L0 = Ext.getCmp('fpageinfo_L0_add');
add_btn_L0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_L0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_L0.getStore().indexOf(record);
		fpageinfo_grid_L0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageinfo_grid_L0.getStore().recordType;
	var p = new Plant( {
		id: "", 
		orderId : "",
		publicationId : "",
		issueId : "",
		imgFile : "",
		status : 1
	});
	fpageinfo_grid_L0.stopEditing();
	fpageinfo_grid_L0.getStore().add(p);
	fpageinfo_grid_L0.getSelectionModel().clearSelections();
	fpageinfo_grid_L0.getSelectionModel().selectLastRow();
	fpageinfo_grid_L0.getView().scrollToRecord(p);
	added = true;
});

var del_btn_L0 = Ext.getCmp('fpageinfo_L0_delete');
del_btn_L0.on('click', function(e) {
	var records = fpageinfo_grid_L0.getSelectionModel().getSelections();
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
					GridUtil.deleteSelected(records, url, fpageinfo_grid_L0);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn_L0 = Ext.getCmp('fpageinfo_L0_commit');
commit_btn_L0.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid_L0.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid_L0.getStore().indexOf(record);
		fpageinfo_grid_L0.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageinfo_grid_L0.getStore().getModifiedRecords();
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
			'type' : 'L2',
			'info' : info
		},
		success : function(e) {
			fpageinfo_grid_L0.getStore().reload();
		},
		failure : function(e) {
		}
	});
});
