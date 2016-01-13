var advertise_store = new Ext.data.JsonStore( {
	url : 'admin/advertise!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime', 'updatedTime', 'status', 'userId',
			'userTypeId', 'keyword', 'title', 'description','linkurl',
			'imgurl', 'mediaurl', 'adType'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
var advertise_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'无效'},{'value':1,'name':'有效'}]
});
var advertise_usertypeid_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'出版商'},{'value':1,'name':'admin'}]
});
var advertise_status_combo = new Ext.form.ComboBox({
	id : 'advertise_status_combo',
	store : advertise_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var advertise_usertypeid_combo = new Ext.form.ComboBox({
	id : 'advertise_usertypeid_combo',
	store : advertise_usertypeid_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var advertise_grid= new Ext.grid.EditorGridPanel( {
	id : 'advertise_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '广告管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "状态",
			dataIndex : "status",
			width : 130,
			editor : advertise_status_combo
		}, {
			header : "用户ID",
			dataIndex : "userId",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "用户类型",
			dataIndex : "userTypeId",
			width : 130,
			editor : advertise_usertypeid_combo,
			renderer : function(value){
				if(value == 1){
					return '出版商';
				}else if(value == 2){
					return 'admin';
				}
			}
		},{
			header : "关键字",
			dataIndex : "keyword",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "广告标题",
			dataIndex : "title",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "广告描述",
			dataIndex : "description",
			width : 130,
			editor : new Ext.form.TextArea( {
				allowBlank : false
			}),
			renderer: function(value, cellmeta, record){
				return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
			}
		},{
			header : "广告链接",
			dataIndex : "linkurl",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "图片链接",
			dataIndex : "imgurl",
			width : 200,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "多媒体路径",
			dataIndex : "mediaurl",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "广告类别",
			dataIndex : "adType",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}
		]
	}),
	region : 'center',
	store : advertise_store,
	viewConfig: {
        //autoFill : true,
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = advertise_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		id : 'issue_paging',
		pageSize : 50,
		store : advertise_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['issue.publicationName'] = Ext.getCmp("issue_search_name").getRawValue();
				o['issue.issueNumber'] = Ext.getCmp("issue_search_issuenumber").getRawValue();}
		}
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'advertise_add',text:'添加'},{id:'advertise_delete',text:'删除'},{id:'advertise_commit',text:'提交'}]}]
});
//查询按钮
/*Ext.getCmp("issue_search").on('click',function(){
	var name = Ext.getCmp("issue_search_name").getRawValue();
	var number = Ext.getCmp("issue_search_issuenumber").getRawValue();
	advertise_store.reload({params:{'page.start':0,'page.limit':50,'issue.publicationName' : name,'issue.issueNumber' : number}});
});*/
//tab关闭事件
advertise_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

advertise_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	advertise_grid.stopEditing(false);
	if(added){
		if(record.modified == null){ //如果没有任何修改，即点过add就deselect,由于add时会有空记录产生，将其删除
			advertise_grid.getStore().remove(record);
			advertise_grid.getStore().getModifiedRecords().remove(record);
		} else { //如果有修改，但不满足不为空的条件，只在modifiedrecords 删除此record，不在store中删除可保留记录信息
			//var ret_status = record.isModified('status');
			var ret_userId = record.isModified('userId');
			var ret_userTypeId = record.isModified('userTypeId');
			var ret_keyword = record.isModified('keyword');
			var ret_title = record.isModified('title');
			var ret_linkurl = record.isModified('linkurl');
			var ret_adType = record.isModified('adType');
			//var ret_gender = record.isModified('status');
			if(!ret_userId||!ret_userTypeId||!ret_keyword||!ret_title||!ret_linkurl||!ret_adType){
				//advertise_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				advertise_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});

var advertise_add_btn = Ext.getCmp('advertise_add');
advertise_add_btn.on('click',function(e){
	if(added){
		var sm = advertise_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = advertise_grid.getStore().indexOf(record);
		advertise_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = advertise_grid.getStore().recordType;
	var p = new Plant({id:"", status:0,userId:'',userTypeId:'',keyword:'',title:'',description:'',linkurl:'',imgurl:'', mediaurl:'', adType:''});
	advertise_grid.stopEditing();
	advertise_grid.getStore().add(p);
	advertise_grid.getSelectionModel().clearSelections();
	advertise_grid.getSelectionModel().selectLastRow();
	advertise_grid.getView().scrollToRecord(p);	
	added = true;
});

var issue_del_btn = Ext.getCmp('advertise_delete');
issue_del_btn.on('click',function(e){
	var records = advertise_grid.getSelectionModel().getSelections();
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
					var url = 'admin/advertise!delete.action';
					GridUtil.deleteSelected(records,url,advertise_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});

var advertise_commit_btn = Ext.getCmp('advertise_commit');
advertise_commit_btn.on('click',function(e){
	if(added){ //点击过添加生成空记录，未经过rowdeselect事件就提交
		var sm = advertise_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = advertise_grid.getStore().indexOf(record);
		advertise_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var records = advertise_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/advertise!commit.action',
		params :{'info' : info},
		success : function(e){
			advertise_grid.getStore().reload();
		},
		failure : function(e){}
	});
});
