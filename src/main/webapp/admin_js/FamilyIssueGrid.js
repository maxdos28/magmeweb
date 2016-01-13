var familyissue_store = new Ext.data.JsonStore( {
	url : 'admin/family-issue!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id','title', 'appendTitle', 'content', 'publicationId', 'issueId', 'status', 'familyName','pageNum','familyCategoryId' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
var familyissue_publication_combo_store = new Ext.data.JsonStore({
	url : 'admin/family-issue!publication.action',
	fields : ['id','name']
});
/*familyissue_publication_combo_store.on('load',function(){
	familyissue_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
});*/
var familyissue_issue_combo_store = new Ext.data.JsonStore({
	url : 'admin/family-issue!issue.action',
	fields : ['id','issueNumber']
});
var familyissue_status_combo_store = new Ext.data.JsonStore({
	fields : ['id','name'],
	data : [{'id':'0','name':'无效'},{'id':'1','name':'生效'}]
});
var familyissue_familyname_combo_store = new Ext.data.JsonStore({
	fields : ['id','name'],
	data : [{'id':'car','name':'汽车'},{'id':'fashion','name':'时尚'},{'id':'beauty','name':'美女'}]
});
var familyissue_publication_combo = new Ext.form.ComboBox({
	id : 'familyissue_publication_combo',
	store : familyissue_publication_combo_store,
	displayField : 'name',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});
var familyissue_issue_combo = new Ext.form.ComboBox({
	id : 'familyissue_issue_combo',
	store : familyissue_issue_combo_store,
	displayField : 'issueNumber',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});
var familyissue_status_combo = new Ext.form.ComboBox({
	id : 'familyissue_status_combo',
	store : familyissue_status_combo_store,
	displayField : 'name',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});
var familyissue_familyName_combo = new Ext.form.ComboBox({
	id : 'familyissue_familyName_combo',
	store : familyissue_familyname_combo_store,
	displayField : 'name',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});
familyissue_publication_combo.on('select',function(){
	var record = familyissue_grid.getSelectionModel().getSelected();
	record.set('issueId','');
});
familyissue_issue_combo.on('beforequery',function(){
	var record = familyissue_grid.getSelectionModel().getSelected();
	familyissue_issue_combo_store.load({params:{'publicationId':record.get('publicationId')}});
});

var family_categoryid_store = new Ext.data.JsonStore({
	url : 'admin/family-issue!getByFamilyCategoryIds.action',
	fields : ['id','name']
});

var family_categoryid_combo = new Ext.form.ComboBox({
	id : 'family_categoryid_combo',
	store : family_categoryid_store,
	mode : 'local',
	valueField : 'id',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

family_categoryid_combo.on('beforequery',function(){
	family_categoryid_store.load();
});

var familyissue_grid = new Ext.grid.EditorGridPanel( {
	id : 'familyissue_grid',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : 'familyissue',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "序号",
			dataIndex : "id",
			width : 50,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "大标题",
			dataIndex : "title",
			width : 120,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "附加标题",
			dataIndex : "appendTitle",
			width : 120,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "内容",
			dataIndex : "content",
			width : 120,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "杂志",
			dataIndex : "publicationId",
			width : 120,
			editor : familyissue_publication_combo
			/*renderer : function(value,cell,record){
				for(var i=0;i<familyissue_publication_combo_store.data.length;i++){
					var id = familyissue_publication_combo_store.getAt(i).get('id');
					if(id == record.get('publicationId')){
						return familyissue_publication_combo_store.getAt(i).get('name');
					}
				}
			}*/
		}, {
			header : "期刊",
			dataIndex : "issueId",
			width : 120,
			editor : familyissue_issue_combo
			/*renderer : function(value,cell,record){
				for(var i=0;i<familyissue_issue_combo_store.data.length;i++){
					var id = familyissue_issue_combo_store.getAt(i).get('id');
					if(id == record.get('issueId')){
						return familyissue_issue_combo_store.getAt(i).get('issueNumber');
					}
				}
			}*/
		},{
			header : "状态",
			dataIndex : "status",
			width : 120,
			editor : familyissue_status_combo,
			renderer : function(value){
				if(value == '0') return '无效';
				else return '生效';
			}
		},{
			header : "组名",
			dataIndex : "familyName",
			width : 120,
			editor : familyissue_familyName_combo
		},{
			header : "页码",
			dataIndex : "pageNum",
			width : 120,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "所属ID",
			dataIndex : "familyCategoryId",
			width : 120,
			editor : family_categoryid_combo
		}]
	}),
	region : 'center',
	store : familyissue_store,
	viewConfig : {
		forceFit : true,
		scrollToRecord : function(record) {
			var index = familyissue_grid.getStore().indexOf(record);
			this.focusRow(index);
		}
	},
	sm : new Ext.grid.RowSelectionModel( {
		singleSelect : false
	}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : familyissue_store,
		displayInfo : true
	}),
	tbar : [ {
		xtype : 'buttongroup',
		items : [ {
			id : 'fpagetag_add',
			text : '添加',
			disabled : false
		}, {
			id : 'fpagetag_delete',
			text : '删除',
			disabled : false
		}, {
			id : 'fpagetag_commit',
			text : '提交',
			disabled : false
		},{
			id:'familyissue_memory_commit',
			text:'更新内存',
			disabled : false
		}]
	} ]
});

var familyissue_memory_commit_btn = Ext.getCmp('familyissue_memory_commit');
familyissue_memory_commit_btn.on('click',function(e){
	Ext.Ajax.request({
		url : 'admin/family-issue!upMemory.action', // 更新memoryCach
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

familyissue_grid.on('beforeclose', function(panel) {
	tab.remove(panel, false);
	panel.hide();
	return false;
});

//tab关闭事件
familyissue_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

familyissue_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	familyissue_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			familyissue_grid.getStore().remove(record);
			familyissue_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_title = record.isModified('title');
			var ret_pageNo = record.isModified('appendTitle');
			var ret_issueId = record.isModified('content');
			var ret_publicationId = record.isModified('publicationId');
			var ret_issueId = record.isModified('issueId');
			if (!ret_title||!ret_pageNo||!ret_issueId||!ret_publicationId||!ret_issueId) {
				//familyissue_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				familyissue_grid.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var tag_add_btn = Ext.getCmp('fpagetag_add');
tag_add_btn.on('click', function(e) {
	if (added) {
		var sm = familyissue_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = familyissue_grid.getStore().indexOf(record);
		familyissue_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = familyissue_grid.getStore().recordType;
	var p = new Plant( {
		id : "",
		title : "",
		appendTitle : "",
		content : "",
		publicationId : "",
		issueId : "",
		status : 0,
		familyName : "",
		pageNum : "",
		familyCategoryId : ""
	});
	familyissue_grid.stopEditing();
	familyissue_grid.getStore().add(p);
	familyissue_grid.getSelectionModel().clearSelections();
	familyissue_grid.getSelectionModel().selectLastRow();
	familyissue_grid.getView().scrollToRecord(p);
	added = true;
});

var tag_del_btn = Ext.getCmp('fpagetag_delete');
tag_del_btn.on('click', function(e) {
	var records = familyissue_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	} else {
		var ret;
		Ext.MessageBox.show( {
			title : '删除',
			msg : '确定要删除？删除后不能恢复！',
			buttons : Ext.MessageBox.YESNO,
			fn : function(btn) {
				if (btn == 'yes') {
					var url = 'admin/family-issue!delete.action';
					GridUtil.deleteSelected(records, url, familyissue_grid);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var tag_commit_btn = Ext.getCmp('fpagetag_commit');
tag_commit_btn.on('click', function(e) {
	if (added) {
		var sm = familyissue_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = familyissue_grid.getStore().indexOf(record);
		familyissue_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = familyissue_grid.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/family-issue!commit.action',
		params : {
			'info' : info
		},
		success : function(e) {
			familyissue_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var fp = new Ext.FormPanel({
		//renderTo : 'search-1',
	    region : 'center',
        width: 300,
        frame: true,
        header : false,
        title: 'File Upload Form',
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'textfield',
            fieldLabel: 'Name'
        },{
            xtype: 'textfield',
            inputType : 'file',
            name : 'upload'
        }],
        buttons: [{
            text: 'Save',
            handler: function(){
                if(fp.getForm().isValid()){
	                fp.getForm().submit({
	                    url: 'file-upload.php',
	                    waitMsg: 'Uploading your photo...',
	                    success: function(fp, o){
	                        msg('Success', 'Processed file "'+o.result.file+'" on the server');
	                    }
	                });
                }
            }
        },{
            text: 'Reset',
            handler: function(e){
                fp.getForm().reset();
            }
        }]
    });
