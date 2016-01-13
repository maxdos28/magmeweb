var issue_store = new Ext.data.JsonStore( {
	url : 'admin/issue!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime', 'updatedTime', 'processId', 'fileName',
			'keyword', 'publicationId', 'publicationName', 'issueNumber','description',
			'p0Size', 'p1Size', 'totalPages', 'popularIdx','publishDate', 'status','pubLevel'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
/*issue_store.on('load',function(){
	var str = GridUtil.uniquePro(this,'publicationId');
	publicationid_render_store.load();
});
var publicationid_render_store = new Ext.data.JsonStore({
	url : 'admin/publication!getByIds.action',
	fields : ['id','name']
});*/
var issue_status_combo_store1 = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'待审核'},{'value':1,'name':'上架'},{'value':2,'name':'下架'},{'value':3,'name':'后台下架'},
			{'value':4,'name':'待处理'},{'value':5,'name':'处理中'},{'value':6,'name':'处理失败'},{'value':7,'name':'重新转换中'},
			{'value':8,'name':'重新转换'}]
});
var issue_status_combo = new Ext.form.ComboBox({
	id : 'issue_status_combo',
	store : issue_status_combo_store1,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var issue_search_status_combo = new Ext.form.ComboBox({
	id : 'user_search_status',
	store : issue_status_combo_store1,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var issue_grid= new Ext.grid.EditorGridPanel( {
	id : 'issue_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '期刊管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "期刊编号",
			dataIndex : "id",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header:"出版商级别",dataIndex:"pubLevel",width:130,
			renderer:function(value,cellmeta,record){
							if(value==0){return '非一线';
							}else if(value==1){return '一线';
							}else{return '';}
					}
		},{
			header : "文件名",
			dataIndex : "fileName",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "关键字",
			dataIndex : "keyword",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "杂志编号",
			dataIndex : "publicationId",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false,
				readOnly : true
			})/*,
			renderer : function(value){
				var record = publicationid_render_store.query('id',value).itemAt(0);
				if(record){
					return record.get('name');
				} else {
					return value;
				}
			}*/
		},{
			header : "杂志名称",
			dataIndex : "publicationName",
			width : 130
		},{
			header : "刊号",
			dataIndex : "issueNumber",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "描述",
			dataIndex : "description",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : true
			})
		},{
			header : "首页大小",
			dataIndex : "p0Size",
			width : 200,
			editor : new Ext.form.TextField( {
				allowBlank : true
			})
		},{
			header : "次页大小",
			dataIndex : "p1Size",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "总页数",
			dataIndex : "totalPages",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "流行程度",
			dataIndex : "popularIdx",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "发布日期",
			dataIndex : "publishDate",
			width : 130,
			editor : new Ext.form.DateField( {
				allowBlank : false,
				format: 'Y-m-d'
			})
			,renderer:Ext.util.Format.dateRenderer('Y-m-d')
		},{
			header : "状态",
			dataIndex : "status",
			width : 130,
			editor : /*new Ext.form.TextField( {
				allowBlank : false
			})*/issue_status_combo,
			renderer: function(value, cellmeta, record){
				var record = issue_status_combo_store1.query('value',value).itemAt(0);
				if (record) {
					return record.get('name');
				}else{
					return value;
				}
			}
		},{
			header : "",
			dataIndex : "id",
			width : 80,
			renderer : function(value, metadata, record) {  
				var id = record.get('id');
				return "<a href='#' onclick=javascript:openAdminReader('"+ id +"')>阅读器</a>";  
			} 
		}]
	}),
	region : 'center',
	store : issue_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = issue_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		id : 'issue_paging',
		pageSize : 50,
		store : issue_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['issue.publicationName'] = Ext.getCmp("issue_forsearch_name").getRawValue();
				o['issue.issueNumber'] = Ext.getCmp("issue_search_issuenumber").getRawValue();}
		}
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'issue_add',text:'添加',disabled:true},{id:'issue_delete',text:'删除',disabled:true},{id:'issue_commit',text:'提交'},{id:'memery_commit',text:'更新内存'}]},
			'期刊名称：',{xtype:'textfield',id:'issue_forsearch_name'},'期刊号：',{xtype:'textfield',id:'issue_search_issuenumber'},'-',{id:'issue_search',text:'查询'}]
});
//查询按钮
Ext.getCmp("issue_search").on('click',function(e){
	var name = Ext.getCmp("issue_forsearch_name").getRawValue();
	var number = Ext.getCmp("issue_search_issuenumber").getRawValue();
	issue_store.reload({params:{'page.start':0,'page.limit':50,'issue.publicationName' : name,'issue.issueNumber' : number}});
});
//tab关闭事件
issue_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

issue_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	issue_grid.stopEditing(false);
	if(added){
		if(record.modified == null){ //如果没有任何修改，即点过add就deselect,由于add时会有空记录产生，将其删除
			issue_grid.getStore().remove(record);
			issue_grid.getStore().getModifiedRecords().remove(record);
		} else { //如果有修改，但不满足不为空的条件，只在modifiedrecords 删除此record，不在store中删除可保留记录信息
			var ret_processId = record.isModified('processId');
			var ret_fileName = record.isModified('fileName');
			var ret_keyword = record.isModified('keyword');
			var ret_publicationId = record.isModified('publicationId');
			var ret_publicationName = record.isModified('publicationName');
			var ret_issueNumber = record.isModified('issueNumber');
			var ret_publishDate = record.isModified('publishDate');
			//var ret_gender = record.isModified('status');
			if(!ret_processId||!ret_fileName||!ret_keyword||!ret_publicationId||!ret_publicationName||!ret_issueNumber||!ret_publishDate){
				//issue_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				issue_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});

var issue_add_btn = Ext.getCmp('issue_add');
issue_add_btn.on('click',function(e){
	if(added){
		var sm = issue_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = issue_grid.getStore().indexOf(record);
		issue_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = issue_grid.getStore().recordType;
	var p = new Plant({id:"", userName:'',password:'',nickName:'',email:'',status:'',lastLoginTime:null,gender:'',birthdate:'', occupation:'', education:'', hobbies:'',phone:'', astro:'', bloodType:'', address:'', province:'', city:'',recuserId:'',avatar:''});
	issue_grid.stopEditing();
	issue_grid.getStore().add(p);
	issue_grid.getSelectionModel().clearSelections();
	issue_grid.getSelectionModel().selectLastRow();
	issue_grid.getView().scrollToRecord(p);	
	added = true;
});

var issue_del_btn = Ext.getCmp('issue_delete');
issue_del_btn.on('click',function(e){
	var records = issue_grid.getSelectionModel().getSelections();
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
					//category_commit_btn.fireEvent('click');
					var url = 'admin/issue!delete.action';
					GridUtil.deleteSelected(records,url,issue_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});

var issue_commit_btn = Ext.getCmp('issue_commit');
issue_commit_btn.on('click',function(e){
	if(added){ //点击过添加生成空记录，未经过rowdeselect事件就提交
		var sm = issue_grid.getSelectionModel();
		var record = sm.getSelected();
		var ret_name = record.isModified('name');
		issue_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var records = issue_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/issue!commit.action',
		params :{'info' : info},
		success : function(e){
			issue_grid.getStore().reload();
		},
		failure : function(e){}
	});
});

// memeryCache更新
var memery_commit_btn = Ext.getCmp('memery_commit');
memery_commit_btn.on('click',function(e){
	Ext.Ajax.request({
		url : 'admin/issue!upmemory.action',
		success : function(e){
			alert('更新内存成功 ！')
		},
		failure : function(e){}
	});
});

function openAdminReader(issueId){
	window.open("/publish/mag-read!adminReader.action?id="+issueId);
}
