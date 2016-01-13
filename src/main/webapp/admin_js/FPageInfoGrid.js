var fpageinfo_store = new Ext.data.JsonStore( {
	url : 'admin/f-page-info!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'orderId', 'title', 'imgPath', 'linkurl', 'status', 'type' ],
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

var fpageinfo_grid = new Ext.grid.EditorGridPanel( {
	id : 'fpage_grid',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页信息管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "序号",
			dataIndex : "orderId",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "标题",
			dataIndex : "title",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "分类",
			dataIndex : "type",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "图片路径",
			dataIndex : "imgPath",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "链接地址",
			dataIndex : "linkurl",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "状态",
			dataIndex : "status",
			width : 130,
			editor : fpage_status_combo,
			renderer : function(value, cellmeta, record){
				if(value == 0){
					return '无效';
				} else {
					return '生效';
				}
			}
		}]
	}),
	region : 'center',
	store : fpageinfo_store,
	viewConfig : {
		forceFit : true,
		scrollToRecord : function(record) {
			var index = fpageinfo_grid.getStore().indexOf(record);
			this.focusRow(index);
		}
	},
	sm : new Ext.grid.RowSelectionModel( {
		singleSelect : false
	}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : fpageinfo_store,
		displayInfo : true
	}),
	tbar : [ {
		xtype : 'buttongroup',
		items : [ {
			id : 'fpageinfo_add',
			text : '添加'
		}, {
			id : 'fpageinfo_delete',
			text : '删除'
		}, {
			id : 'fpageinfo_commit',
			text : '提交'
		}, {
			text : '生效',
			handler : function() {				
			}
		},{
			text : '失效',
			handler : function(){
			}
		}]
	},{
		xtype:'buttongroup',
		items : [{
			xtype:'form',
			fileUpload : true,
			width : 200,
			height : 25,
			id : 'upload-form',
			layout:'column',
			items:[{xtype:'textfield',inputType:'file',name : 'pic'}]
		},{
			text:'上传',
			handler : function(e){
				var records = fpageinfo_grid.getSelectionModel().getSelections();
				var record ='';
				if(records.length == 0){
					return;
				} else if(records.length > 1){
					Ext.MessageBox.alert("提示","只能选择一个");
					return;
				}
				Ext.getCmp('upload-form').getForm().submit({
	                    url: 'admin/f-page-info!upload.action',
	                    params : {'imgPath':records[0].get('imgPath'),'uploadFileId':records[0].get('id')},
	                    waitMsg: 'Uploading your photo...',
	                    success: function(fp, o){
	                        //msg('Success', 'Processed file "'+o.result.file+'" on the server');
	                    }
	            });
			}
		}]
	} ]
});

fpageinfo_grid.on('beforeclose', function(panel) {
	tab.remove(panel, false);
	panel.hide();
	return false;
});

//tab关闭事件
fpageinfo_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

fpageinfo_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageinfo_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageinfo_grid.getStore().remove(record);
			fpageinfo_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_title = record.isModified('title');
			var ret_imgPath = record.isModified('imgPath');
			var ret_linkurl = record.isModified('linkurl');
			if (!ret_title || !ret_imgPath || !ret_linkurl) {
				//fpageinfo_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageinfo_grid.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var add_btn = Ext.getCmp('fpageinfo_add');
add_btn.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid.getStore().indexOf(record);
		fpageinfo_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageinfo_grid.getStore().recordType;
	var p = new Plant( {
		id : "",
		orderId : "",
		title : "",
		imgPath : "",
		linkurl : "",
		status : 0,
		type : ""
	});
	fpageinfo_grid.stopEditing();
	fpageinfo_grid.getStore().add(p);
	fpageinfo_grid.getSelectionModel().clearSelections();
	fpageinfo_grid.getSelectionModel().selectLastRow();
	fpageinfo_grid.getView().scrollToRecord(p);
	added = true;
});

var del_btn = Ext.getCmp('fpageinfo_delete');
del_btn.on('click', function(e) {
	var records = fpageinfo_grid.getSelectionModel().getSelections();
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
					GridUtil.deleteSelected(records, url, fpageinfo_grid);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn = Ext.getCmp('fpageinfo_commit');
commit_btn.on('click', function(e) {
	if (added) {
		var sm = fpageinfo_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageinfo_grid.getStore().indexOf(record);
		fpageinfo_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageinfo_grid.getStore().getModifiedRecords();
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
			'info' : info
		},
		success : function(e) {
			fpageinfo_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var fp = new Ext.FormPanel({
		//renderTo : 'search-1',
	    region : 'center',
        width: 300,
        frame: true,
        header : false,
        title: 'File Upload Form',
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'textfield',
            fieldLabel: 'Name'
        },{
            xtype: 'textfield',
            inputType : 'file',
            name : 'upload'
        }],
        buttons: [{
            text: 'Save',
            handler: function(e){
                if(fp.getForm().isValid()){
	                fp.getForm().submit({
	                    url: 'file-upload.php',
	                    waitMsg: 'Uploading your photo...',
	                    success: function(fp, o){
	                        msg('Success', 'Processed file "'+o.result.file+'" on the server');
	                    }
	                });
                }
            }
        },{
            text: 'Reset',
            handler: function(e){
                fp.getForm().reset();
            }
        }]
    });
