var user_ex_store = new Ext.data.JsonStore( {
	url : 'admin/user-ex!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'userId',
	fields : [ 'userId', 'nameZh', 'nameEn', 'phone', 'tel', 'imgPath', 'publisher', 'office','audit','isRecommend','event'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
var user_ex_audit_combo_store = new Ext.data.JsonStore({
	id : 'user_ex_audit_combo_store',
	fields : ['value','name'],
	data : [{'value':0,'name':'待认证'},{'value':1,'name':'认证'},{'value':2,'name':'认证未通过'}]
});
var user_ex_isRecommend_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'未推荐'},{'value':1,'name':'推荐'}]
});
var user_ex_event_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'未授权'},{'value':1,'name':'已授权'}]
});
var user_ex_event_audit_combo = new Ext.form.ComboBox({
	id : 'user_ex_event_audit_combo',
	store : user_ex_event_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var user_ex_event_search_combo = new Ext.form.ComboBox({
	id : 'user_ex_event_search_combo',
	store : user_ex_event_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : true,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var user_ex_audit_combo = new Ext.form.ComboBox({
	id : 'user_ex_audit_combo',
	store : user_ex_audit_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var user_ex_search_audit_combo = new Ext.form.ComboBox({
	id : 'user_ex_search_audit_combo',
	store : user_ex_audit_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : true,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var user_ex_isRecommend_combo = new Ext.form.ComboBox({
	id : 'user_ex_isRecommend_combo',
	store : user_ex_isRecommend_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var user_ex_search_isRecommend_combo = new Ext.form.ComboBox({
	id : 'user_ex_search_isRecommend_combo',
	store : user_ex_isRecommend_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : true,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var textField = new Ext.form.TextField( {
	allowBlank : false
});

var user_ex_grid= new Ext.grid.EditorGridPanel( {
	id : 'user_ex_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '编辑认证',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ 
		   { header : "用户ID", dataIndex : "userId", width : 130 }, 
			{ header : "中文名", dataIndex : "nameZh", width : 130, editor : textField },
			{ header : "英文名", dataIndex : "nameEn", width : 130, editor : textField },
			{ header : "手机号码", dataIndex : "phone", width : 130, editor : textField },
			{ header : "座机号码", dataIndex : "tel", width : 130, editor : textField },
			{ header : "杂志商ID", dataIndex : "publisher", width : 130 }, 
			{ header : "职位", dataIndex : "office", width : 130, editor : textField },
			{ header : "编辑认证", dataIndex : "audit", width : 130, editor : user_ex_audit_combo,
				renderer: function(value, cellmeta, record){
					var record = user_ex_audit_combo_store.query('value',value).itemAt(0);
					return record.get('name');
				}
			},
			{ header : "是否推荐用户", dataIndex : "isRecommend", width : 130, editor : user_ex_isRecommend_combo,
				renderer: function(value, cellmeta, record){
					var record = user_ex_isRecommend_combo_store.query('value',value).itemAt(0);
					return record.get('name');
				}
			},
			{ header : "自动事件转换", dataIndex : "event", width : 130, editor : user_ex_event_audit_combo,
				renderer: function(value, cellmeta, record){
					var record = user_ex_event_combo_store.query('value',value).itemAt(0);
					return record.get('name');
				}
			},
			{ header : "证件", dataIndex : "imgPath", width : 230 }
			]
	}),
	region : 'center',
	store : user_ex_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = user_ex_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		id : 'user_paging',
		pageSize : 50,
		store : user_ex_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['userEx.userId'] = Ext.getCmp("user_ex_search_id").getRawValue();
				o['userEx.publisher'] = Ext.getCmp('user_ex_search_publisher_id').getRawValue();
				o['userEx.audit'] = Ext.getCmp('user_ex_search_audit_combo').getValue();
				o['userEx.isRecommend'] = Ext.getCmp('user_ex_search_isRecommend_combo').getValue();
				o['userEx.event'] = Ext.getCmp('user_ex_event_search_combo').getValue();
				}
		}
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'user_ex_delete',text:'删除'},{id:'user_ex_commit',text:'提交'}]},
			'用户ID：',{xtype:'textfield',id:'user_ex_search_id'},
			'杂志商ID：',{xtype:'textfield',id:'user_ex_search_publisher_id'},
			'认证状态：',user_ex_search_audit_combo,
			'是否推荐：',user_ex_search_isRecommend_combo,
			'自动事件转换：',user_ex_event_search_combo,
			'-', {id:'user_ex_search',text:'查询'}]
});
//查询按钮
Ext.getCmp("user_ex_search").on('click',function(e){
	var id = Ext.getCmp("user_ex_search_id").getRawValue();
	var publisher = Ext.getCmp("user_ex_search_publisher_id").getRawValue();
	var audit = Ext.getCmp('user_ex_search_audit_combo').getValue();
	var isRecommend = Ext.getCmp('user_ex_search_isRecommend_combo').getValue();
	var event = Ext.getCmp('user_ex_event_search_combo').getValue();
	user_ex_store.reload({params:{'page.start':0,'page.limit':50,'userEx.userId' : id,
							   'userEx.publisher' : publisher,'userEx.audit' : audit,'userEx.isRecommend' : isRecommend,"userEx.event":event}});
});
//tab关闭事件
user_ex_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

user_ex_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	user_ex_grid.stopEditing(false);
});

var user_ex_commit_btn = Ext.getCmp('user_ex_commit');
user_ex_commit_btn.on('click',function(e){
	var records = user_ex_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/user-ex!commit.action',
		params :{'info' : info},
		success : function(){
			user_ex_grid.getStore().reload();
		},
		failure : function(){}
	});
});

var user_ex_del_btn = Ext.getCmp('user_ex_delete');
user_ex_del_btn.on('click',function(e){
	var records = user_ex_grid.getSelectionModel().getSelections();
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
					var url = 'admin/user-ex!delete.action';
					GridUtil.deleteSelected(records,url,user_ex_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});
//复写index.js中的此方法（原因：id获取失败）
GridUtil.deleteSelected = function(records, url, grid,from) {
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		var id = records[i].id;
		if(id == ''){ //avoid delete items have an empty id
			continue;
		}
		ids += id;
		if (i < records.length - 1) {
			ids += ',';
		}
	}
	Ext.Ajax.request({
		url : url,
		params : {
			'ids' : ids
		},
		success : function(e) {
			grid.getStore().reload();
		},
		failure : function(e) {
		}
	})
};