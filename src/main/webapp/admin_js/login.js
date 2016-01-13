Ext.QuickTips.init();
LoginWindow = Ext.extend(Ext.Window, {
	title : '后台登陆系统',
	width : 265,
	height : 170,
	collapsible : true,
	defaults : {
		border : false
	},
	buttonAlign : 'center',
	createFormPanel : function(e) {
		return new Ext.form.FormPanel( {
			bodyStyle : 'padding-top:6px',
			defaultType : 'textfield',
			labelAlign : 'right',
			labelWidth : 55,
			labelPad : 0,
			frame : true,
			defaults : {
				allowBlank : false,
				width : 158
			},
			items : [ {
				//cls : 'admin.userName',
				name : 'admin.userName',
				fieldLabel : '帐号',
				blankText : '帐号不能为空'
			}, {
				//cls : 'admin.password',
				name : 'admin.password',
				fieldLabel : '密码',
				blankText : '密码不能为空',
				inputType : 'password'
			}, {
				//cls : 'code',
				name : 'code',
				id : 'code',
				fieldLabel : '验证码',
				width : 90,
				blankText : '验证码不能为空'
			} ]
		});
	},
	login : function() {
		this.fp.form.submit( {
			//waitMsg : '正在登录......',
			method : 'post',
			url : 'admin/admin!login.action',
			success : function(form, action) {
				//alert(action.result.message);
				window.location.href = 'main.html';
			},
			failure : function(form, action) {
				//form.reset();
				alert(action.result.message);
				window.location.href = 'admin_login.html';
			}
		});
	},
	initComponent : function() {
		LoginWindow.superclass.initComponent.call(this);
		this.fp = this.createFormPanel();
		this.add(this.fp);
		this.addButton('登陆', this.login, this);
		this.addButton('重置', function() {
			this.fp.form.reset();
		}, this);

	}
});

Ext.onReady(function() {
	var win = new LoginWindow();
	win.show();
	var bd = Ext.getDom('code');
	var bd2 = Ext.get(bd.parentNode);
	bd2.createChild( {
		tag : 'img',
		src : 'admin/admin!checkCode.action',
		align : 'absbottom'
	});
});