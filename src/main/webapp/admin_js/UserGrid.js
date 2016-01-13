var user_store = new Ext.data.JsonStore( {
	url : 'admin/user!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime', 'updatedTime', 'userName', 'password',
			'nickName', 'email', 'status', 'lastLoginTime','gender',
			'birthdate', 'occupation', 'education', 'hobbies',
			'phone', 'astro', 'bloodType', 'address', 'province', 'city','recuserId','avatar','reserve1','reserve2','reserve3' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
var user_status_combo_store = new Ext.data.JsonStore({
	id : 'user_status_combo_store',
	fields : ['value','name'],
	data : [{'value':0,'name':'未审核'},{'value':1,'name':'审核'},{'value':2,'name':'锁定'},{'value':3,'name':'注销'}]
});
var user_gender_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':''},{'value':1,'name':'男'},{'value':2,'name':'女'}]
});
var user_status_combo = new Ext.form.ComboBox({
	id : 'user_status_combo',
	store : user_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var user_search_status_combo = new Ext.form.ComboBox({
	id : 'user_search_status_combo',
	store : user_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : true,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var user_grid= new Ext.grid.EditorGridPanel( {
	id : 'user_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '用户管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "用户ID",
			dataIndex : "id",
			width : 130
		}, {
			header : "注册时间",
			dataIndex : "createdTime",
			width : 130,
			renderer:function(v){
				return (Date.parseDate(v, 'Y-m-d H:i:s')).format('Y-m-d H');
			}
		}, {
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
			header : "昵称",
			dataIndex : "nickName",
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
			})*/user_status_combo,
			renderer: function(value, cellmeta, record){
				var record = user_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		},{
			header : "上次登录时间",
			dataIndex : "lastLoginTime",
			width : 200
		},{
			header : "性别",
			dataIndex : "gender",
			width : 130,
			editor : new Ext.form.ComboBox( {
				id : 'user_gender_combo',
				store : user_gender_combo_store,
				mode : 'local',
				valueField : 'value',
				displayField : 'name',
				editable  : false,
				forceSelection : true,
				triggerAction: 'all',
				lazyRender:true
			}),
			renderer: function(value, cellmeta, record){
				var ret;
				if(value == 0) ret = '';
				else if(value == 1) ret = '男';
				else ret = '女'; return ret;
			}
		},{
			header : "生日",
			dataIndex : "birthdate",
			width : 200,
			renderer:function(v){
				return v!=null?v.substring(0,10):null;
			}
		},{
			header : "职业",
			dataIndex : "occupation",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "教育水平",
			dataIndex : "education",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "爱好",
			dataIndex : "hobbies",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "联系电话",
			dataIndex : "phone",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "星座",
			dataIndex : "astro",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "血型",
			dataIndex : "bloodtype",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "街道地址",
			dataIndex : "address",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "省",
			dataIndex : "province",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "市",
			dataIndex : "city",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "推荐用户",
			dataIndex : "recuserid",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "头像",
			dataIndex : "avatar",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}]
	}),
	region : 'center',
	store : user_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = user_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		id : 'user_paging',
		pageSize : 50,
		store : user_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['user.userName'] = Ext.getCmp('user_search_name').getRawValue();
				o['user.status'] = Ext.getCmp('user_search_status_combo').getValue();
				}
		}
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'user_add',text:'添加',disabled:true},{id:'user_delete',text:'删除',disabled:true},{id:'user_commit',text:'提交'},{id:'user_resetpassword',text:'重置密码'}]},
			'用户名：',{xtype:'textfield',id:'user_search_name'},
			'昵称：',{xtype:'textfield',id:'user_search_nm'},
			'邮箱：',{xtype:'textfield',id:'user_search_mail'},
			'状态：',user_search_status_combo,'-',{id:'user_search',text:'查询'}]
});
//查询按钮
Ext.getCmp("user_search").on('click',function(e){
	var name = Ext.getCmp("user_search_name").getRawValue();
	//var status = user_search_status_combo.getValue(); 这种方法突然获取不到了，通过ID可以获取。似乎变量所指内容清空了。。
	var status = Ext.getCmp('user_search_status_combo').getValue();
	var nm = Ext.getCmp("user_search_nm").getRawValue();
	var email = Ext.getCmp("user_search_mail").getRawValue();
	user_store.reload({params:{'page.start':0,'page.limit':50,'user.userName' : name,
							   'user.nickName' : nm,'user.email' : email,'user.status' : status}});
});
//tab关闭事件
user_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

user_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	user_grid.stopEditing(false);
	if(added){
		if(record.modified == null){
			user_grid.getStore().remove(record);
			user_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_username = record.isModified('userName');
			var ret_password = record.isModified('password');
			var ret_nickName = record.isModified('nickName');
			var ret_email = record.isModified('email');
			//var ret_title = record.isModified('status');
			var ret_gender = record.isModified('gender');
			if(!ret_username||!ret_password||!ret_nickName||!ret_email||!ret_gender){
				//user_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				user_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});

var user_add_btn = Ext.getCmp('user_add');
user_add_btn.on('click',function(e){
	if(added){
		var sm = user_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = user_grid.getStore().indexOf(record);
		user_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = user_grid.getStore().recordType;
	var p = new Plant({id:"", userName:'',password:'',nickName:'',email:'',status:'',lastLoginTime:null,gender:'',birthdate:'', occupation:'', education:'', hobbies:'',phone:'', astro:'', bloodType:'', address:'', province:'', city:'',recuserId:'',avatar:''});
	user_grid.stopEditing();
	user_grid.getStore().add(p);
	user_grid.getSelectionModel().clearSelections();
	user_grid.getSelectionModel().selectLastRow();
	user_grid.getView().scrollToRecord(p);	
	added = true;
});

var user_del_btn = Ext.getCmp('user_delete');
user_del_btn.on('click',function(e){
	var records = user_grid.getSelectionModel().getSelections();
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
					var url = 'admin/user!delete.action';
					GridUtil.deleteSelected(records,url,user_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});

var user_commit_btn = Ext.getCmp('user_commit');
user_commit_btn.on('click',function(e){
	if(added){ //点击过添加生成空记录，未经过rowdeselect事件就提交
		var sm = user_grid.getSelectionModel();
		var record = sm.getSelected();
		var ret_name = record.isModified('name');
		user_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var records = user_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/user!commit.action',
		params :{'info' : info},
		success : function(){
			user_grid.getStore().reload();
		},
		failure : function(){}
	});
});
var user_reset_btn = Ext.getCmp('user_resetpassword');
user_reset_btn.on('click',function(e){
	var records = user_grid.getSelectionModel().getSelections();
	//var info = Ext.encode(records);
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/user!reset.action',
		params : {'info' : info},
		success : function(e){
			user_grid.getStore().reload();
		},
		failure : function(e){}
	});
});