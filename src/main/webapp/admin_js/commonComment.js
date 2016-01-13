var common_comment_L1 = new Ext.data.JsonStore( {
	url : 'admin/comment!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id','userId','objectId', 'param', 'type', 'status','content'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var sm_common_comment = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar_common_comment =  new Ext.PagingToolbar({pageSize:200,store:common_comment_L1,displayInfo:true,
	listeners : {'beforechange':function(page,o){
	}
  }
});
var tbar_L1=[{xtype:'buttongroup',items:[{id:'common_comment_delete',text:'删除'}]}];

var cr_status_combo_store = new Ext.data.JsonStore({
	id : 'cr_status_combo_store',
	fields:['id','value'],
	data : [{'id':'1','value':'正常'},{'id':'0','value':'删除'}]
});

var cr_status_combo = new Ext.form.ComboBox({
	id : 'cr_status_combo',
	store : cr_status_combo_store,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});


var cr_category_combo_store = new Ext.data.JsonStore({
	id : 'cr_category_combo_store',
	fields:['id','value'],
	data : [{'id':'page','value':'期刊'},
	        {'id':'magzine','value':'杂志'}]
});

var cr_category_combo = new Ext.form.ComboBox({
	id : 'cr_category_combo',
	store : cr_category_combo_store,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});

var cr_category_combo_fn=function(value,cellmeta,record){
	if(value=='page'){
		return '期刊';
	}else if(value=='magzine'){
		return '杂志';
	}
}

var viewConfig_L1 = {forceFit:true,scrollToRecord:function(record){var index=common_comment.getStore().indexOf(record);this.focusRow(index);}};
var common_comment_noallow_blank_L1=new Ext.form.TextField( {allowBlank : false});
var col_common_comment_L1={header:"id",dataIndex:"id",width:20};
var col_common_comment_L2={header:"用户id",dataIndex:"userId",width:30,editor:common_comment_noallow_blank_L1};
var col_common_comment_L3={header:"对象id",dataIndex:"objectId",width:30};
var col_common_comment_L4={header:"页码",dataIndex:"param",width:20};
var col_common_comment_L5={header:"类型",dataIndex:"type",width:20,editor:cr_category_combo,renderer:cr_category_combo_fn};
var col_common_comment_L6={header:"状态",dataIndex:"status",width:20,editor:cr_status_combo,renderer:function(value,cellmeta,record){if(value==1){return '正常';}else{return '删除';}}};
var col_common_comment_L7={header:"内容",dataIndex:"content",width:100};


var common_comment = new Ext.grid.EditorGridPanel({
	id : 'common_comment_L1',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '通用评论管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ col_common_comment_L1,col_common_comment_L2,col_common_comment_L3,col_common_comment_L4,col_common_comment_L5,col_common_comment_L6,col_common_comment_L7]
	}),
	region : 'center',
	store : common_comment_L1,
	viewConfig : viewConfig_L1,
	sm : sm_common_comment,
	bbar : bbar_common_comment,
	tbar : tbar_L1
});

//tab关闭事件
common_comment.on('beforeclose', beforeclose);

var del_btn_L1 = Ext.getCmp('common_comment_delete');
del_btn_L1.on('click', function(e) {
	var records = common_comment.getSelectionModel().getSelections();
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
					var url = 'admin/comment!commit.action';
					GridUtil.deleteSelected(records, url, common_comment);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});
