var adagency_store = new Ext.data.JsonStore( {
	url : 'admin/ad-agency!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime', 'updatedTime', 'userName', 'password',
			'companyName', 'email', 'status', 'lastLoginTime', 'contact',
			'contactPhone', 'contactType', 'contactNumber', 'address',
			'webSite', 'fax', 'logo' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var adagency_status_combo_store = new Ext.data.JsonStore({
	id : 'adagency_status_combo_store',
	fields : ['value','name'],
	data : [{'value':0,'name':'未审核'},{'value':1,'name':'审核通过'},{'value':2,'name':'锁定'},{'value':3,'name':'注销'}]
});
var adagency_status_combo = new Ext.form.ComboBox({
	id : 'adagency_status_combo',
	store : adagency_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var adagency_search_status_combo = new Ext.form.ComboBox({
	id : 'adagency_search_status_combo',
	store : adagency_status_combo_store,
	width : 100,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var adagency_grid= new Ext.grid.EditorGridPanel( {
	id : 'adagency_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '广告商管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "用户名",
			dataIndex : "userName",
			width : 130
		}, {
			header : "密码",
			dataIndex : "password",
			width : 130
		},{
			header : "公司名称",
			dataIndex : "companyName",
			width : 130
		},{
			header : "邮箱",
			dataIndex : "email",
			width : 130
		},{
			header : "状态",
			dataIndex : "status",
			width : 130,
			editor : /*new Ext.form.TextField( {
				allowBlank : false
			})*/adagency_status_combo,
			renderer: function(value, cellmeta, record){
				var record = adagency_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "上次登录时间",
			dataIndex : "lastLoginTime",
			width : 130
		},{
			header : "联系人姓名",
			dataIndex : "contact",
			width : 130
		},{
			header : "联系人电话",
			dataIndex : "contactPhone",
			width : 130
		},{
			header : "联系方式",
			dataIndex : "contactType",
			width : 130
		},{
			header : "联系号码",
			dataIndex : "contactNumber",
			width : 130
		},{
			header : "通信地址",
			dataIndex : "address",
			width : 130
		},{
			header : "网址",
			dataIndex : "webSite",
			width : 130
		},{
			header : "传真",
			dataIndex : "fax",
			width : 130
		},{
			header : "广告商头像",
			dataIndex : "logo",
			width : 130
		}]
	}),
	region : 'center',
	store : adagency_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = adagency_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : adagency_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
					o['adAgency.userName'] = Ext.getCmp('adagency_search_name').getRawValue();
					o['adAgency.status'] = Ext.getCmp('adagency_search_status_combo').getValue();}
		}
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'adagency_commit',text:'提交'}]},
			'用户名：',{xtype:'textfield',id:'adagency_search_name'},'状态：',adagency_search_status_combo,'-',{id:'adagency_search',text:'查询'}]
});

//查询按钮
Ext.getCmp("adagency_search").on('click',function(e){
	var name = Ext.getCmp("adagency_search_name").getRawValue();
	
	var status = Ext.getCmp('adagency_search_status_combo').getValue();
	adagency_store.reload({params:{'page.start':0,'page.limit':50,'adAgency.userName':name,'adAgency.status':status}});
});

adagency_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

adagency_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	adagency_grid.stopEditing(false);
	if(added){
		if(record.modified == null){
			adagency_grid.getStore().remove(record);
			adagency_grid.getStore().getModifiedRecords().remove(record);
		} else {
//			var ret_username = record.isModified('userName');
//			var ret_password = record.isModified('password');
//			var ret_publishName = record.isModified('publishName');
//			var ret_email = record.isModified('email');
//			//var ret_title = record.isModified('status');
//			var ret_contact1 = record.isModified('contact1');
//			var ret_contactPhone1 = record.isModified('contactPhone1');
//			var ret_owner = record.isModified('owner');
//			var ret_companyPhone = record.isModified('companyPhone');
//			var ret_fax = record.isModified('fax');
//			if(!ret_username||!ret_password||!ret_publishName||!ret_email||!ret_contact1||!ret_contactPhone1||!ret_owner||!ret_companyPhone||!ret_fax){
//				//adagency_grid.getStore().remove(record);
//				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
//				adagency_grid.getStore().getModifiedRecords().remove(record);
//			}
		}		
		added = false;
	}
});

//var adagency_add_btn = Ext.getCmp('adagency_add');
//adagency_add_btn.on('click',function(e){
//	if(added){
//		var sm = adagency_grid.getSelectionModel();
//		var record = sm.getSelected();
//		var row = adagency_grid.getStore().indexOf(record);
//		adagency_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
//	}
//	var Plant = adagency_grid.getStore().recordType;
//	var p = new Plant({id:"", userName:'',password:'',pulishName:'',email:'',status:'',lastLoginTime:new Date(),contact1:'',contactPhone1:'',contact2:'',contactPhone2:'',owner:'',companyPhone:'',fax:'',address:'',provinceId:'',cityId:'',logo:''});
//	adagency_grid.stopEditing();
//	adagency_grid.getStore().add(p);
//	adagency_grid.getSelectionModel().clearSelections();
//	adagency_grid.getSelectionModel().selectLastRow();
//	adagency_grid.getView().scrollToRecord(p);	
//	added = true;
//});
//
//var adagency_del_btn = Ext.getCmp('adagency_delete');
//adagency_del_btn.on('click',function(e){
//	var records = adagency_grid.getSelectionModel().getSelections();
//	if(records.length <= 0){
//		return;
//	} else {
//		var ret;
//		Ext.MessageBox.show({
//           title:'删除',
//           msg: '确定要删除？删除后不能恢复！',
//           buttons: Ext.MessageBox.YESNO,
//           fn: function(btn){
//				if(btn == 'yes') {
//					//category_commit_btn.fireEvent('click');
//					var url = 'admin/adagency!delete.action';
//					GridUtil.deleteSelected(records,url,adagency_grid);
//				}
//           },
//           animEl: 'mb4',
//           icon: Ext.MessageBox.QUESTION
//        });
//	}
//	
//});

var adagency_commit_btn = Ext.getCmp('adagency_commit');
adagency_commit_btn.on('click',function(e){
//	if(added){ //点击过添加生成空记录，未经过rowdeselect事件就提交
//		var sm = adagency_grid.getSelectionModel();
//		var record = sm.getSelected();
//		var row = adagency_grid.getStore().indexOf(record);
//		adagency_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
//	}
	var records = adagency_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/ad-agency!commit.action',
		params :{'info' : info},
		success : function(e){
			adagency_grid.getStore().reload();
		},
		failure : function(e){}
	});
});