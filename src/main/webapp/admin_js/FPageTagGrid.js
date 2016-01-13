var fpagetag_store = new Ext.data.JsonStore( {
	url : 'admin/f-page-tag!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'tagId', 'orderId', 'title', 'description', 'pageNo', 'issueId', 'publicationId' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var fpagetag_grid = new Ext.grid.EditorGridPanel( {
	id : 'fpagetag_grid',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页tag管理',
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
			header : "描述",
			dataIndex : "description",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "页数",
			dataIndex : "pageNo",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "期刊编号",
			dataIndex : "issueId",
			width : 130,
			editor : new Ext.form.TextField({
				allowBlank : false
			})
		},{
			header : "杂志编号",
			dataIndex : "publicationId",
			width : 130
		}]
	}),
	region : 'center',
	store : fpagetag_store,
	viewConfig : {
		forceFit : true,
		scrollToRecord : function(record) {
			var index = fpagetag_grid.getStore().indexOf(record);
			this.focusRow(index);
		}
	},
	sm : new Ext.grid.RowSelectionModel( {
		singleSelect : false
	}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : fpagetag_store,
		displayInfo : true
	}),
	tbar : [ {
		xtype : 'buttongroup',
		items : [ {
			id : 'fpagetag_add',
			text : '添加',
			disabled : false
		}, {
			id : 'fpagetag_delete',
			text : '删除',
			disabled : false
		}, {
			id : 'fpagetag_commit',
			text : '提交',
			disabled : false
		}]
	} ]
});
fpagetag_grid.on('beforeclose', function(panel) {
	tab.remove(panel, false);
	panel.hide();
	return false;
});
//tab关闭事件
fpagetag_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});
fpagetag_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpagetag_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpagetag_grid.getStore().remove(record);
			fpagetag_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_title = record.isModified('title');
			var ret_pageNo = record.isModified('pageNo');
			var ret_issueId = record.isModified('issueId');
			var ret_publicationId = record.isModified('publicationId');
			if (!ret_title || !ret_pageNo || !ret_issueId || !ret_publicationId) {
				//fpagetag_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpagetag_grid.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var tag_add_btn = Ext.getCmp('fpagetag_add');
tag_add_btn.on('click', function(e) {
	if (added) {
		var sm = fpagetag_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpagetag_grid.getStore().indexOf(record);
		fpagetag_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpagetag_grid.getStore().recordType;
	var p = new Plant( {
		id : "",
		orderId : "",
		title : "",
		imgPath : "",
		linkurl : "",
		status : 0,
		type : ""
	});
	fpagetag_grid.stopEditing();
	fpagetag_grid.getStore().add(p);
	fpagetag_grid.getSelectionModel().clearSelections();
	fpagetag_grid.getSelectionModel().selectLastRow();
	fpagetag_grid.getView().scrollToRecord(p);
	added = true;
});

var tag_del_btn = Ext.getCmp('fpagetag_delete');
tag_del_btn.on('click', function(e) {
	var records = fpagetag_grid.getSelectionModel().getSelections();
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
					var url = 'admin/f-page-tag!delete.action';
					var from = 'fpagetag';
					GridUtil.deleteSelected(records, url, fpagetag_grid,from);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var tag_commit_btn = Ext.getCmp('fpagetag_commit');
tag_commit_btn.on('click', function(e) {
	if (added) {
		var sm = fpagetag_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpagetag_grid.getStore().indexOf(record);
		fpagetag_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpagetag_grid.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/f-page-tag!commit.action',
		params : {
			'info' : info
		},
		success : function(e) {
			fpagetag_grid.getStore().reload();
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
