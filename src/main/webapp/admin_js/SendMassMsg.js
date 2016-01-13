var msgsend_store = new Ext.data.JsonStore( {
	url : 'admin/user!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'userName', 'nickName', 'email',,'gender',
			'birthdate', 'occupation', 'education', 'hobbies',
			'phone', 'astro', 'bloodType', 'address', 'province', 'city','recuserId','avatar' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit',
		status : 'user.status'
	}
});

var chksm  = new Ext.grid.CheckboxSelectionModel();
 chksm.handleMouseDown = Ext.emptyFn;//主要是这句

var msgsend_grid= new Ext.grid.EditorGridPanel( {
	id : 'msgsend_grid',
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
		columns : [ 
			chksm
		, {
			header : "用户ID",
			dataIndex : "id",
			width : 130
		}, {
			header : "用户名",
			dataIndex : "userName",
			width : 130
		},{
			header : "昵称",
			dataIndex : "nickName",
			width : 130
		},{
			header : "邮箱",
			dataIndex : "email",
			width : 130
		},{
			header : "性别",
			dataIndex : "gender",
			width : 130,
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
			width : 130
		},{
			header : "教育水平",
			dataIndex : "education",
			width : 130
		},{
			header : "爱好",
			dataIndex : "hobbies",
			width : 130
		},{
			header : "联系电话",
			dataIndex : "phone",
			width : 130
		},{
			header : "星座",
			dataIndex : "astro",
			width : 130
		},{
			header : "血型",
			dataIndex : "bloodtype",
			width : 130
		},{
			header : "街道地址",
			dataIndex : "address",
			width : 130
		},{
			header : "省",
			dataIndex : "province",
			width : 130
		},{
			header : "市",
			dataIndex : "city",
			width : 130
		},{
			header : "推荐用户",
			dataIndex : "recuserid",
			width : 130
		}]
	}),
	region : 'center',
	store : msgsend_store,
	viewConfig: {
		forceFit: true,
		scrollToRecord:function(record){  
			var index = msgsend_grid.getStore().indexOf(record);  
			this.focusRow(index);  
  		}
	},
	sm: chksm,
	bbar : new Ext.PagingToolbar( {
		id : 'msgsend_user_paging',
		pageSize : 50,
		store : msgsend_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['user.status'] = 1;
				o['user.id'] = Ext.getCmp('msgsend_user_search_id').getRawValue();
				o['user.userName'] = Ext.getCmp('msgsend_user_search_name').getRawValue();
				o['user.nickName'] = Ext.getCmp('msgsend_user_search_nm').getRawValue();
				o['user.email'] = Ext.getCmp('msgsend_user_search_mail').getRawValue();
				}
		}
	}),
	tbar : [{xtype:'buttongroup',items:[
	{
		xtype:'form',
		fileUpload : true,
		method:'POST',
		enctype:'multipart/form-data', 
		width : 200,
		height : 25,
		id : 'sendmsg_form',
		layout:'column',
		items:[{xtype:'textfield',inputType:'file',name : 'userFile'}]
	},
	{
		text:'指定群发',
		handler : function(e){
			var records = msgsend_grid.getSelectionModel().getSelections();
			
			var ids = '';
			for ( var i = 0; i < records.length; i++) {
				var id = '';
				id = records[i].get('id');
				if(id == ''){ //avoid delete items have an empty id
					continue;
				}
				ids += id;
				if (i < records.length - 1) {
					ids += ',';
				}
			}
			sendMassUser(ids);
		}
	},{
		text:'无指定群发',
		handler : function(e){
			sendAllUser();
		}
	}]},
			'用户编号: ',{xtype:'textfield',id:'msgsend_user_search_id',width:80},
			'用户名：',{xtype:'textfield',id:'msgsend_user_search_name',width:80},
			'昵称：',{xtype:'textfield',id:'msgsend_user_search_nm',width:80},
			'邮箱：',{xtype:'textfield',id:'msgsend_user_search_mail',width:80},
			'-',{id:'msgsend_user_search',text:'查询'}]
});
//查询按钮
Ext.getCmp("msgsend_user_search").on('click',function(e){
	var userid =  Ext.getCmp("msgsend_user_search_id").getRawValue();
	var name = Ext.getCmp("msgsend_user_search_name").getRawValue();
	var nm = Ext.getCmp("msgsend_user_search_nm").getRawValue();
	var email = Ext.getCmp("msgsend_user_search_mail").getRawValue();
	//var file = Ext.getCmp("userFile").getRawValue();

	msgsend_store.reload({params:{'page.start':0,'page.limit':50,'user.id' : userid,'user.userName' : name,
									'user.nickName' : nm,'user.email' : email,'user.status' : 1}});
});

//tab关闭事件
msgsend_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

function sendMassUser(ids) {
	Ext.MessageBox.show({
				title:'系统消息',
				msg: '内容:',
				width:300,  
				buttons:{"ok":"发送","cancel":"取消"},
				multiline:true,
				fn:function(btn,text){
					if(btn == 'ok') {
						Ext.getCmp('sendmsg_form').getForm().submit({
							waitTitle : '提示',
							waitMsg : '正在发送,请稍候...',
							url: 'admin/send-mass-msg!msgSend.action',
							params : {'ids' : ids,'message' : text,'user.status' : 1},
							success: function(form, action){
								Ext.MessageBox.alert("提示",action.result.msg + "条信息,发送成功！");
								msgsend_store.reload({params:{'page.start':0,'page.limit':50,'user.status' : 1}});
							},
							failure : function(form, action)
							{
								Ext.MessageBox.alert("提示","发送失败！");
							}
						});
					}
				}
			});
}

function sendAllUser() {
	Ext.MessageBox.show({
				title:'系统消息',
				msg: '<font color=red>向所有注册用户发送此系统消息</font>,内容: ',
				width:300,  
				buttons:{"ok":"发送","cancel":"取消"},
				multiline:true,
				fn:function(btn,text){
					if(btn == 'ok') {
						Ext.getCmp('sendmsg_form').getForm().submit({
							waitTitle : '提示',
							waitMsg : '正在发送,请稍候...',
							url: 'admin/send-mass-msg!msgSendAll.action',
							params : {'message' : text,'user.status' : 1},
							success: function(form, action){
								Ext.MessageBox.alert("提示",action.result.msg + "条信息,发送成功！");
								msgsend_store.reload({params:{'page.start':0,'page.limit':50,'user.status' : 1}});
							},
							failure : function(form, action)
							{
								Ext.MessageBox.alert("提示","发送失败！");
							}
						});
					}
				}
			});
}