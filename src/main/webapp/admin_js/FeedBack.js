var feedback_store = new Ext.data.JsonStore( {
	url : 'admin/feed-back!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'userId','nickName', , 'content', 'status', 'comment'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit',
		status : 'feedBack.status'
	}
});

var feedback_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'无效'},{'value':1,'name':'待处理'},{'value':2,'name':'已处理'}]
});
var feedback_status_combo = new Ext.form.ComboBox({
	id : 'feedback_status_combo',
	store : feedback_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var feedback_search_status_combo = new Ext.form.ComboBox({
	id : 'feedback_search_status_combo',
	store : feedback_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var feedback_grid = new Ext.grid.EditorGridPanel( {
	id : 'feedback_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '反馈种类状态',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [{
			header : "处理状态",
			dataIndex : "status",
			width : 100,
			editor : feedback_status_combo,
			renderer: function(value, cellmeta, record){
				var record = feedback_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "用户编号",
			dataIndex : "userId",
			width : 100
		},{
			header : "用户昵称",
			dataIndex : "nickName",
			width : 100
		}, {
			header : "反馈内容",
			dataIndex : "content",
			width : 700,
			editor : new Ext.form.TextArea( {
				allowBlank : false,
				readOnly : true
			}),
			renderer: function(value, cellmeta, record){
				return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
			}
		},{
			header : "",
			dataIndex : "userId",
			width : 50,
			renderer : function(value, metadata, record) {  

				if (record.get("userId")=="") {
					return null;
				}else {
					var index = feedback_grid.getStore().indexOf(record);
					return "<a href='#' onclick=javascript:ShowSendMsgWin("+ index +")>发信</a>";  
				}
			} 
		},{
			header : "备注",
			dataIndex : "comment",
			width : 200,
			editor : new Ext.form.TextField( {
				allowBlank : true
			})
		}]
	}),
	region : 'center',
	store : feedback_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = feedback_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : feedback_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['feedBack.status'] = feedback_search_status_combo.getValue();
				}
		}
	}),
	tbar: [{
		xtype: 'buttongroup',
		items: [{
			id: 'feedback_commit',
			text: '提交'
		}]
	}, '状态：', feedback_search_status_combo, '-',
	{
		id: 'feedback_search',
		text: '查询'
	}]
});

function ShowSendMsgWin(idx){
	var record = feedback_grid.getSelectionModel().getSelected();
	var arr = new Array();
	arr.push(record.data);
	var info = Ext.encode(arr);
	Ext.MessageBox.show({  
				title:'系统消息',  
				msg:'内容: ',
				width:300,  
				buttons:{"ok":"发送","cancel":"取消"},
				multiline:true,
				value:"反馈信息已处理",
				fn:function(btn,text){
					if(btn == 'ok') {
						var url = 'admin/feed-back!msgSend.action';
						Ext.Ajax.request({
							url : url,
							params : {
								'info' : info,'message' : text
							},
							success : function(e) {
								feedback_grid.getStore().reload();
							},
							failure : function(e) {
							}
						});
					}
				}
			});
}

//查询按钮
Ext.getCmp("feedback_search").on('click',function(e){
	var status = feedback_search_status_combo.getValue();
	//var status = Ext.getCmp('feedback_status_combo_store').getValue();
	//alert(feedback_search_status_combo.getValue())
	feedback_store.reload({params:{'page.start':0,'page.limit':50,'feedBack.status':status}});
});

var feedback_commit_btn = Ext.getCmp('feedback_commit');
feedback_commit_btn.on('click',function(e){
	var records = feedback_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/feed-back!commit.action',
		params :{'info' : info},
		success : function(e){
			feedback_grid.getStore().reload();
		},
		failure : function(e){}
	});
});
Ext.getCmp('feedback_search_status_combo').setValue(1); 