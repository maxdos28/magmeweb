var tag_store = new Ext.data.JsonStore( {
	url : 'admin/tag!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'userId','publicationId','issueId','pageNo','keyword','description','clickNum','enjoyNum','commentNum','status','audit'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var tag_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'无效'},{'value':1,'name':'有效'}]
});

var tag_audit_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'不通过'},{'value':1,'name':'通过'}]
});

var tag_status_combo = new Ext.form.ComboBox({
	id : 'tag_status_combo',
	store : tag_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var tag_audit_combo = new Ext.form.ComboBox({
	id : 'tag_audit_combo',
	store : tag_audit_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

Ext.QuickTips.init();
var tag_grid= new Ext.grid.EditorGridPanel( {
	id : 'tag_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : 'tag管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [{
			header : "标签编号",
			dataIndex : "id",
			width : 50
		}, {
			header : "创建者编号",
			dataIndex : "userId",
			width : 50
		}, {
			header : "杂志编号",
			dataIndex : "publicationId",
			width : 50
		}, {
			header : "期刊编号",
			dataIndex : "issueId",
			width : 50
		}, {
			header : "页数",
			dataIndex : "pageNo",
			width : 50
		}, {
			header : "关键字",
			dataIndex : "keyword",
			width : 50,
			renderer: function(value, cellmeta, record){
				return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
			}
		}, {
			header : "描述",
			dataIndex : "description",
			width : 130,
			renderer: function(value, cellmeta, record){
				return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
			}
		}, {
			header : "点击数",
			dataIndex : "clickNum",
			width : 50
		}, {
			header : "喜欢数",
			dataIndex : "enjoyNum",
			width : 50
		}, {
			header : "评论数",
			dataIndex : "commentNum",
			width : 50
		},{
			header : "状态",
			dataIndex : "status",
			width : 50,
			renderer: function(value, cellmeta, record){
				var record = tag_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "审核状态",
			dataIndex : "audit",
			width : 50,
			renderer: function(value, cellmeta, record){
				var record = tag_audit_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		}]
	}),
	region : 'center',
	store : tag_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = tag_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		id : 'tag_paging',
		pageSize : 50,
		store : tag_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['userImage.userId'] = Ext.getCmp("search_user_id").getRawValue();
				o['userImage.id'] = Ext.getCmp("search_id").getRawValue();
				}
			}
	}),
	tbar : [{xtype:'buttongroup',items:[
		{id:'tag_pass_btn',text:'通过',tooltip:"审核状态操作"},{id:'tag_refused_btn',text:'拒绝',tooltip:"审核状态操作"},
		{id:'tag_enable_btn',text:'生效',tooltip:"状态操作"},{id:'tag_disable_btn',text:'无效',tooltip:"状态操作"}]}
			,'用户编号：',{xtype:'textfield',id:'search_user_id'}
			,'标签编号：',{xtype:'textfield',id:'search_id'},'-',{id:'tag_search',text:'查询'}]
});
//查询按钮
Ext.getCmp("tag_search").on('click',function(e){
	var userId = Ext.getCmp("search_user_id").getRawValue();
	var id = Ext.getCmp("search_id").getRawValue();
	tag_store.reload({params:{'page.start':0,'page.limit':50,
					   'userImage.userId' : userId,'userImage.id' : id}});
});
//tab关闭事件
tag_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

tag_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	tag_grid.stopEditing(false);
});

var tag_pass_btn = Ext.getCmp('tag_pass_btn');
tag_pass_btn.on('click',function(e){
	var info = getinfo();
	Ext.Ajax.request({
		url : 'admin/tag!commit.action',
		params :{'info' : info,'userImage.audit' : 1},
		success : function(e){
			tag_grid.getStore().reload();
		},
		failure : function(e){}
	});
});

var tag_refused_btn = Ext.getCmp('tag_refused_btn');
tag_refused_btn.on('click',function(e){
	var info = getinfo();
	Ext.Ajax.request({
		url : 'admin/tag!commit.action',
		params :{'info' : info,'userImage.audit' : 0},
		success : function(e){
			tag_grid.getStore().reload();
		},
		failure : function(e){}
	});
});

var tag_enable_btn = Ext.getCmp('tag_enable_btn');
tag_enable_btn.on('click',function(e){
	var info = getinfo();
	Ext.Ajax.request({
		url : 'admin/tag!commit.action',
		params :{'info' : info,'userImage.status' : 1},
		success : function(e){
			tag_grid.getStore().reload();
		},
		failure : function(e){}
	});
});

var tag_disable_btn = Ext.getCmp('tag_disable_btn');
tag_disable_btn.on('click',function(e){
	var info = getinfo();
	Ext.Ajax.request({
		url : 'admin/tag!commit.action',
		params :{'info' : info,'userImage.status' : 0},
		success : function(e){
			tag_grid.getStore().reload();
		},
		failure : function(e){}
	});
});

function getinfo() {
	var records = tag_grid.getSelectionModel().getSelections();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	return Ext.encode(arr);
}

