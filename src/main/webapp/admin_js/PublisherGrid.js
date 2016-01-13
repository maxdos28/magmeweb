var publisher_store = new Ext.data.JsonStore( {
	url : 'admin/publisher!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime', 'updatedTime', 'userName', 'password',
			'publishName', 'email', 'status', 'lastLoginTime', 'contact1',
			'contactPhone1', 'contact2', 'contactPhone2', 'owner',
			'companyPhone', 'fax', 'address', 'provinceId', 'cityId', 'logo','level' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var publisher_status_combo_store = new Ext.data.JsonStore({
	id : 'publisher_status_combo_store',
	fields : ['value','name'],
	data : [{'value':0,'name':'未激活'},{'value':4,'name':'已激活待审核'},{'value':1,'name':'审核通过'},{'value':2,'name':'锁定'},{'value':3,'name':'注销'}]
});
var publisher_status_combo = new Ext.form.ComboBox({
	id : 'publisher_status_combo',
	store : publisher_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var publisher_search_status_combo = new Ext.form.ComboBox({
	id : 'publisher_search_status_combo',
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

var level_status_combo_store = new Ext.data.JsonStore({
	id : 'level_status_combo_store',
	fields : ['value','name'],
	data : [{'value':0,'name':'非一线'},{'value':1,'name':'一线'}]
});
var level_status_combo = new Ext.form.ComboBox({
	id : 'level_status_combo',
	store : level_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var publisher_grid= new Ext.grid.EditorGridPanel( {
	id : 'publisher_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '出版商管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "用户名",
			dataIndex : "userName",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "密码",
			dataIndex : "password",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "杂志社",
			dataIndex : "publishName",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "邮箱",
			dataIndex : "email",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "状态",
			dataIndex : "status",
			width : 130,
			editor : /*new Ext.form.TextField( {
				allowBlank : false
			})*/publisher_status_combo,
			renderer: function(value, cellmeta, record){
				var record = publisher_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "上次登录时间",
			dataIndex : "lastLoginTime",
			width : 200
		},{
			header : "联系人1",
			dataIndex : "contact1",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "电话1",
			dataIndex : "contactPhone1",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "联系人2",
			dataIndex : "contact2",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "电话2",
			dataIndex : "contactPhone2",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "法人",
			dataIndex : "owner",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "企业电话",
			dataIndex : "companyPhone",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "传真",
			dataIndex : "fax",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "地址",
			dataIndex : "address",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "省",
			dataIndex : "provinceId",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "市",
			dataIndex : "cityId",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "logo",
			dataIndex : "logo",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "出版商级别",
			dataIndex : "level",
			width : 130,
			editor : /*new Ext.form.TextField( {
				allowBlank : false
			})*/level_status_combo,
			renderer: function(value, cellmeta, record){
				var record = level_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		}]
	}),
	region : 'center',
	store : publisher_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = publisher_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : publisher_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
						o['publisher.publishName'] = Ext.getCmp('publisher_search_name').getRawValue();
						o['publisher.status'] = publisher_search_status_combo.getValue();
					}
		}
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'publisher_add',text:'添加'},{id:'publisher_delete',text:'删除'},{id:'publisher_commit',text:'提交'}]},
			'杂志社名：',{xtype:'textfield',id:'publisher_search_name'},'状态：',publisher_search_status_combo,'-',{id:'publisher_search',text:'查询'}]
});

//查询按钮
Ext.getCmp("publisher_search").on('click',function(e){
	var name = Ext.getCmp("publisher_search_name").getRawValue();
	var status = publisher_search_status_combo.getValue();
	//var status = Ext.getCmp('publisher_status_combo_store').getValue();
	publisher_store.reload({params:{'page.start':0,'page.limit':50,'publisher.publishName':name,'publisher.status':status}});
});

publisher_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

publisher_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	publisher_grid.stopEditing(false);
	if(added){
		if(record.modified == null){
			publisher_grid.getStore().remove(record);
			publisher_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_username = record.isModified('userName');
			var ret_password = record.isModified('password');
			var ret_publishName = record.isModified('publishName');
			var ret_email = record.isModified('email');
			//var ret_title = record.isModified('status');
			var ret_contact1 = record.isModified('contact1');
			var ret_contactPhone1 = record.isModified('contactPhone1');
			var ret_owner = record.isModified('owner');
			var ret_companyPhone = record.isModified('companyPhone');
			var ret_fax = record.isModified('fax');
			if(!ret_username||!ret_password||!ret_publishName||!ret_email||!ret_contact1||!ret_contactPhone1||!ret_owner||!ret_companyPhone||!ret_fax){
				//publisher_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				publisher_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});

var publisher_add_btn = Ext.getCmp('publisher_add');
publisher_add_btn.on('click',function(e){
	if(added){
		var sm = publisher_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = publisher_grid.getStore().indexOf(record);
		publisher_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = publisher_grid.getStore().recordType;
	var p = new Plant({id:"", userName:'',password:'',pulishName:'',email:'',status:'1',lastLoginTime:new Date(),contact1:'',contactPhone1:'',contact2:'',contactPhone2:'',owner:'',companyPhone:'',fax:'',address:'',provinceId:'',cityId:'',logo:'',level:'0'});
	publisher_grid.stopEditing();
	publisher_grid.getStore().add(p);
	publisher_grid.getSelectionModel().clearSelections();
	publisher_grid.getSelectionModel().selectLastRow();
	publisher_grid.getView().scrollToRecord(p);	
	added = true;
});

var publisher_del_btn = Ext.getCmp('publisher_delete');
publisher_del_btn.on('click',function(e){
	var records = publisher_grid.getSelectionModel().getSelections();
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
					var url = 'admin/publisher!delete.action';
					GridUtil.deleteSelected(records,url,publisher_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});

var publisher_commit_btn = Ext.getCmp('publisher_commit');
publisher_commit_btn.on('click',function(e){
	if(added){ //点击过添加生成空记录，未经过rowdeselect事件就提交
		var sm = publisher_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = publisher_grid.getStore().indexOf(record);
		publisher_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var records = publisher_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/publisher!commit.action',
		params :{'info' : info},
		success : function(e){
			publisher_grid.getStore().reload();
		},
		failure : function(e){}
	});
});