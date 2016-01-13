var tagcomment_store = new Ext.data.JsonStore( {
	url : 'admin/tag-comment!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id','imageId','userId', 'contentInfo.id', 'contentInfo.content','isNew','status'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var tagcomment_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'无效'},{'value':1,'name':'有效'}]
});

var tagcomment_status_combo = new Ext.form.ComboBox({
	id : 'tagcomment_status_combo',
	store : tagcomment_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var tagcomment_grid= new Ext.grid.EditorGridPanel( {
	id : 'tagcomment_grid',
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
		columns : [ {
			header : "评论编号",
			dataIndex : "id",
			width : 50
		}, {
			header : "图片编号",
			dataIndex : "imageId",
			width : 50
		}, {
			header : "用户编号",
			dataIndex : "userId",
			width : 50
		}, {
			header : "内容编号",
			dataIndex : "contentInfo.id",
			width : 80
		},{
			header : "评论内容",
			dataIndex : "contentInfo.content",
			width : 200,
			renderer: function(value, cellmeta, record){
				return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
			}
		},{
			header : "",
			dataIndex : "isNew",
			width : 200,
			renderer: function(value, cellmeta, record){
				if (value == 0){
					return "已读"
				}else{
					return "未读";
				}
			}
		},{
			header : "状态",
			dataIndex : "status",
			width : 50,
			editor : tagcomment_status_combo,
			renderer: function(value, cellmeta, record){
				var record = tagcomment_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		}]
	}),
	region : 'center',
	store : tagcomment_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = tagtagcomment_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		id : 'tagcomment_paging',
		pageSize : 50,
		store : tagcomment_store,
		displayInfo : true
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'tagcomment_commit_btn',text:'提交'}]}
			,'评论编号：',{xtype:'textfield',id:'tagcomment_search_commentid'}
			,'图片编号：',{xtype:'textfield',id:'tagcomment_search_imageid'}
			,'用户编号：',{xtype:'textfield',id:'tagcomment_search_userid'}
			,'-',{id:'tagcomment_search_btn',text:'查询'}]
});

tagcomment_grid.on('beforeclose', function(panel) {
	tab.remove(panel, false);
	panel.hide();
	return false;
});

//查询按钮
Ext.getCmp("tagcomment_search_btn").on('click',function(e){
	var userid = Ext.getCmp("tagcomment_search_userid").getRawValue();
	var commentid = Ext.getCmp('tagcomment_search_commentid').getRawValue();
	var imageid = Ext.getCmp("tagcomment_search_imageid").getRawValue();
	tagcomment_store.reload({params:{'page.start':0,'page.limit':50,
									'userImageComment.id' : commentid,
									'userImageComment.userId' : userid,
									'userImageComment.imageId' : imageid}});
});

var tagcomment_commit_btn = Ext.getCmp('tagcomment_commit_btn');
tagcomment_commit_btn.on('click',function(e){
	var records = tagcomment_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/tag-comment!commit.action',
		params :{'info' : info},
		success : function(e){
			tagcomment_grid.getStore().reload();
		},
		failure : function(e){}
	});
});
