var publication_store = new Ext.data.JsonStore( {
	url : 'admin/publication!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime', 'updatedTime', 'name', 'description',
			'categoryId', 'languageId', 'popularIdx', 'status', 'publisherId','sortOrder','needCompress','frontChop','needPrint','issn','publisher' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
publication_store.on('load',function(e){
	var str = GridUtil.uniquePro(this,'publisherId');
	publisherid_render_store.load({params:{'ids':str}});
	//var str_category = GridUtil.uniquePro(this,'categoryId');
	//categoryid_render_store.load({params:{'ids':str_category}});
	categoryid_render_store.load();
});
//可公用
var publisherid_render_store = new Ext.data.JsonStore({
	url : 'admin/publisher!getByIds.action',
	fields : ['id','userName']
});
publisherid_render_store.on('load',function(){
	publication_grid.getView().refresh(false); //刷新view
});
var categoryid_render_store = new Ext.data.JsonStore({
	url : 'admin/category!getByChildIds.action',
	fields : ['id','name']
});
categoryid_render_store.on('load',function(){
	publication_grid.getView().refresh(false); //刷新view
});
var publication_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'待审核'},{'value':1,'name':'上架'},{'value':2,'name':'下架'},{'value':3,'name':'后台下架'}]
});
var publication_status_combo = new Ext.form.ComboBox({
	id : 'publication_status_combo',
	store : publication_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var categoryid_render_combo = new Ext.form.ComboBox({
	id : 'categoryid_render_combo',
	store : categoryid_render_store,
	mode : 'local',
	valueField : 'id',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var publication_search_status_combo = new Ext.form.ComboBox({
	id : 'publication_search_status_combo',
	store : publisher_status_combo_store,
	width : 100,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var compress_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'需要'},{'value':1,'name':'不需要'}]
});
var compress_status_combo = new Ext.form.ComboBox({
	id : 'compress_status_combo',
	store : compress_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var print_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':1,'name':'需要'},{'value':0,'name':'不需要'}]
});
var print_status_combo = new Ext.form.ComboBox({
	id : 'print_status_combo',
	store : print_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var chop_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'不使用'},{'value':1,'name':'使用'}]
});
var chop_status_combo = new Ext.form.ComboBox({
	id : 'chop_status_combo',
	store : chop_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var publication_grid= new Ext.grid.EditorGridPanel( {
	id : 'publication_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '杂志管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "杂志名",
			dataIndex : "name",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "描述",
			dataIndex : "description",
			width : 150,
			editor : new Ext.form.TextArea( {
				allowBlank : false
			})
		},{
			header : "类目id",
			dataIndex : "categoryId",
			width : 50,
			editor : categoryid_render_combo
		},{
			header : "流行程度",
			dataIndex : "popularIdx",
			width : 50,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "状态",
			dataIndex : "status",
			width : 50,
			editor : publication_status_combo,
			renderer : function(value){
				if(value == 0){
					return '待审核';
				}else if(value == 1){
					return '上架';
				}else {
					return '下架';
				}
			}
		},{
			header : "出版商名称",
			dataIndex : "publisherId",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false,
				readOnly : true
			}),
			renderer : function(value){
				var record = publisherid_render_store.query('id',value).itemAt(0);
				if(record){
					return record.get('userName');
				} else {
					return value;
				}
			}
		},{
			header : "杂志排名",
			dataIndex : "sortOrder",
			width : 50,
			editor : new Ext.form.TextField( {
				allowBlank : true
			})
		},{
			header : "压缩",
			dataIndex : "needCompress",
			width : 60,
			editor : compress_status_combo,
			renderer: function(value, cellmeta, record){
				var record = compress_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "前端切割",
			dataIndex : "frontChop",
			width : 60,
			editor : chop_status_combo,
			renderer: function(value, cellmeta, record){
				var record = chop_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "打印",
			dataIndex : "needPrint",
			width : 60,
			editor : print_status_combo,
			renderer: function(value, cellmeta, record){
				var record = print_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "刊号",
			dataIndex : "issn",
			width : 150,
			editor : new Ext.form.TextField( {
				allowBlank : true
			})
		},{
			header : "出版人",
			dataIndex : "publisher",
			width : 100,
			editor : new Ext.form.TextField( {
				allowBlank : true
			})
		}

		]
	}),
	region : 'center',
	store : publication_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = publication_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : publication_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
						o['publication.name'] = Ext.getCmp('publication_search_name').getRawValue();
					}
		}
	}),
	tbar: [{
	    xtype: 'buttongroup',
	    items: [{
	        id: 'publication_add',
	        text: '添加',
	        disabled: true
	    }, {
	        id: 'publication_delete',
	        text: '删除',
	        disabled: true
	    }, {
	        id: 'publication_commit',
	        text: '提交'
	    }, {
	        id: 'publication_memory_commit',
	        text: '更新内存',
	        disabled: false
	    }]
	}, '杂志名：',
	{
	    xtype: 'textfield',
	    id: 'publication_search_name'
	}, '-',
	{
	    id: 'publication_search',
	    text: '查询'
	}]
});
//查询按钮
Ext.getCmp("publication_search").on('click',function(e){
	var name = Ext.getCmp("publication_search_name").getRawValue();
	//var status = publication_search_status_combo.getValue();
	publication_store.reload({params:{'page.start':0,'page.limit':50,'publication.name':name}});
});
publication_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});
publication_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	publication_grid.stopEditing(false);
	if(added){
		if(record.modified == null){
			publication_grid.getStore().remove(record);
			publication_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_name = record.isModified('name');
			var ret_description = record.isModified('description');
			var ret_categoryId = record.isModified('categoryId');
			var ret_languageId = record.isModified('languageId');
			var ret_popularidx = record.isModified('popularidx');
			var ret_publisherId = record.isModified('publisherId');
			if(!ret_name||!ret_description||!ret_categoryId||!ret_languageId||!ret_popularidx||!ret_publisherId){
				//publication_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				publication_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});
var publication_add_btn = Ext.getCmp('publication_add');
publication_add_btn.on('click',function(e){
	if(added){
		var sm = publication_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = publication_grid.getStore().indexOf(record);
		publication_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = publication_grid.getStore().recordType;
	var p = new Plant({id:"", userName:'',password:'',pulishName:'',email:'',status:'',lastLoginTime:new Date(),contact1:'',contactPhone1:'',contact2:'',contactPhone2:'',owner:'',companyPhone:'',fax:'',address:'',provinceId:'',cityId:'',logo:''});
	publication_grid.stopEditing();
	publication_grid.getStore().add(p);
	publication_grid.getSelectionModel().clearSelections();
	publication_grid.getSelectionModel().selectLastRow();
	publication_grid.getView().scrollToRecord(p);	
	added = true;
});
var publication_del_btn = Ext.getCmp('publication_delete');
publication_del_btn.on('click',function(e){
	var records = publication_grid.getSelectionModel().getSelections();
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
					var url = 'admin/publication!delete.action';
					GridUtil.deleteSelected(records,url,publication_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});
var publication_commit_btn = Ext.getCmp('publication_commit');
publication_commit_btn.on('click',function(e){
	if(added){ //点击过添加生成空记录，未经过rowdeselect事件就提交
		var sm = publication_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = publication_grid.getStore().indexOf(record);
		publication_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var records = publication_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/publication!commit.action',
		params :{'info' : info},
		success : function(e){
			publication_grid.getStore().reload();
			//此处没有判断是否需要刷新,必要时优化
			if(tab.getItem('issue_grid')){ //级联更新 杂志状态下架，期刊也下架 
				issue_grid.getStore().reload();
			}
		},
		failure : function(e){}
	});
});

var publication_memory_commit_btn = Ext.getCmp('publication_memory_commit');
publication_memory_commit_btn.on('click',function(e){
		Ext.Ajax.request({
			url : 'admin/publication!upMemory.action',
			//params : {
			//	'info' : info,'answer' : text
			//},
			success : function(e) {
				alert('更新内存成功 ！')
			},
			failure : function(e) {
			}
		});
	
});