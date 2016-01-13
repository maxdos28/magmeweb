var dialog_store = new Ext.data.JsonStore( {
	url : 'admin/dialog!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'eventCode','userId','nickName', 'content','relation.content'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var dialog_grid = new Ext.grid.EditorGridPanel( {
	id : 'dialog_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '问答管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "活动Id",
			dataIndex : "eventCode",
			width : 60
		}, {
			header : "提问者ID",
			dataIndex : "userId",
			width : 60
		}, {
			header : "提问者昵称",
			dataIndex : "nickName",
			width : 60
		}, {
			header : "提问内容",
			dataIndex : "content",
			width : 200,
			editor : new Ext.form.TextField( {
				allowBlank : false
			}),
			renderer: function(value, cellmeta, record){
				return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
			}
			
		}, {
			header : "回答内容",
			dataIndex : "relation.content",
			width : 200,
			editor : new Ext.form.TextField( {
				allowBlank : false
			}),
			renderer: function(value, cellmeta, record){
				return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
			}
		}]
	}),
	region : 'center',
	store : dialog_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = dialog_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : dialog_store,
		displayInfo : true
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'dialog_delete',text:'删除'},{id:'dialog_answer',text:'回答'}]}]
});

dialog_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

dialog_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	dialog_grid.stopEditing(false);
	if(added){
		if(record.modified == null){
			dialog_grid.getStore().remove(record);
			dialog_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_title = record.isModified('name');
			var ret_parentName = record.isModified('level');
			if(!ret_title || !ret_parentName){
				//admin_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				dialog_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});

var dialog_del_btn = Ext.getCmp('dialog_delete');
dialog_del_btn.on('click',function(e){
	var records = dialog_grid.getSelectionModel().getSelections();
	if(records.length <= 0){
		return;
	} else {
		var ret;
		Ext.MessageBox.show({
           title:'删除',
           msg: '确定要删除？删除后不能恢复！',
           buttons: Ext.MessageBox.YESNO,
           fn: function(btn){
				if(btn == 'yes') {
					var url = 'admin/dialog!delete.action';
					GridUtil.deleteSelected(records,url,dialog_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});

var dialog_answer_btn = Ext.getCmp('dialog_answer');
dialog_answer_btn.on('click',function(e){
	var records = dialog_grid.getSelectionModel().getSelections();
	if(records.length <= 0){
		return;
	} else {
		var arr = new Array();
		for(var i = 0;i < records.length;i++){
			arr.push(records[i].data);
		}
		var info = Ext.encode(arr);
		Ext.MessageBox.show({  
		                title:'回答',  
		                msg:'内容:',  
		                width:300,  
		                buttons:Ext.MessageBox.OKCANCEL,  
		                multiline:true,  
		                fn:function(btn,text){
							if(btn == 'ok') {
								var url = 'admin/dialog!answer.action';
								//alert(arr);  
								Ext.Ajax.request({
									url : url,
									params : {
										'info' : info,'answer' : text
									},
									success : function(e) {
										dialog_grid.getStore().reload();
									},
									failure : function(e) {
									}
								});
							}
		                }  
		            });
	}
});