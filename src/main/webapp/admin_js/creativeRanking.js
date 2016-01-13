var creative_ranking_L1 = new Ext.data.JsonStore( {
	url : 'admin/ext-creative-ranking!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id','creativeId','creativeTitle', 'sortOrder', 'creativeCategoryId', 'rankingType'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var sm_creative_ranking = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar_creative_ranking =  new Ext.PagingToolbar({pageSize:200,store:creative_ranking_L1,displayInfo:true,
	listeners : {'beforechange':function(page,o){
	}
  }
});
var tbar_L1=[{xtype:'buttongroup',items:[{id:'creative_ranking_add',text:'添加'},{id:'creative_ranking_delete',text:'删除'},{id:'creative_ranking_commit',text:'提交'}]}];

var cr_status_combo_store = new Ext.data.JsonStore({
	id : 'cr_status_combo_store',
	fields:['id','value'],
	data : [{'id':'1','value':'编辑推荐'},{'id':'2','value':'本周阅读榜'}]
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
	data : [{'id':'0','value':'首页'},
	        {'id':'1','value':'丽人'},
	        {'id':'2','value':'绅士'},
	        {'id':'3','value':'玩味'},
	        {'id':'4','value':'座驾'},
	        {'id':'5','value':'财界'},
	        {'id':'6','value':'情商'},
	        {'id':'7','value':'家居'}]
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
	if(value==0){
		return '首页';
	}else if(value==1){
		return '丽人';
	}else if(value==2){
		return '绅士';
	}else if(value==3){
		return '玩味';
	}else if(value==4){
		return '座驾';
	}else if(value==5){
		return '财界';
	}else if(value==6){
		return '情商';
	}else if(value==7){
		return '家居';
	}
}

var viewConfig_L1 = {forceFit:true,scrollToRecord:function(record){var index=creative_ranking.getStore().indexOf(record);this.focusRow(index);}};
var creative_ranking_noallow_blank_L1=new Ext.form.TextField( {allowBlank : false});
var col_creative_ranking_L1={header:"id",dataIndex:"id",width:20};
var col_creative_ranking_L2={header:"作品id",dataIndex:"creativeId",width:30,editor:creative_ranking_noallow_blank_L1};
var col_creative_ranking_L3={header:"标题(无需填写)",dataIndex:"creativeTitle",width:50};
var col_creative_ranking_L4={header:"排序",dataIndex:"sortOrder",width:20,editor:creative_ranking_noallow_blank_L1};
var col_creative_ranking_L5={header:"分类",dataIndex:"creativeCategoryId",width:30,editor:cr_category_combo,renderer:cr_category_combo_fn};
var col_creative_ranking_L6={header:"榜单类型",dataIndex:"rankingType",width:50,editor:cr_status_combo,renderer:function(value,cellmeta,record){if(value==1){return '编辑推荐';}else{return '本周阅读榜';}}};

var creative_ranking = new Ext.grid.EditorGridPanel({
	id : 'creative_ranking_L1',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页榜单管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ col_creative_ranking_L1,col_creative_ranking_L2,col_creative_ranking_L3,col_creative_ranking_L4,col_creative_ranking_L5,col_creative_ranking_L6]
	}),
	region : 'center',
	store : creative_ranking_L1,
	viewConfig : viewConfig_L1,
	sm : sm_creative_ranking,
	bbar : bbar_creative_ranking,
	tbar : tbar_L1
});

//tab关闭事件
creative_ranking.on('beforeclose', beforeclose);

creative_ranking.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	creative_ranking.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			creative_ranking.getStore().remove(record);
			creative_ranking.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_orderid = record.isModified('creativeId');
			if (!ret_orderid) {
				//creative_ranking.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				creative_ranking.getStore().remove(record);
			}
		}
		added = false;
	}
});

var add_btn_L1 = Ext.getCmp('creative_ranking_add');
add_btn_L1.on('click', function(e) {
	if (added) {
		var sm = creative_ranking.getSelectionModel();
		var record = sm.getSelected();
		var row = creative_ranking.getStore().indexOf(record);
		creative_ranking.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = creative_ranking.getStore().recordType;
	var p = new Plant( {
		id: "", 
		creativeId : "",
		sortOrder : "1",
		creativeCategoryId : "0",
		rankingType : "1"
	});
	creative_ranking.stopEditing();
	creative_ranking.getStore().add(p);
	creative_ranking.getSelectionModel().clearSelections();
	creative_ranking.getSelectionModel().selectLastRow();
	creative_ranking.getView().scrollToRecord(p);
	added = true;
});

var del_btn_L1 = Ext.getCmp('creative_ranking_delete');
del_btn_L1.on('click', function(e) {
	var records = creative_ranking.getSelectionModel().getSelections();
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
					var url = 'admin/ext-creative-ranking!delete.action';
					GridUtil.deleteSelected(records, url, creative_ranking);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn_L1 = Ext.getCmp('creative_ranking_commit');
commit_btn_L1.on('click', function(e) {
	if (added) {
		var sm = creative_ranking.getSelectionModel();
		var record = sm.getSelected();
		var row = creative_ranking.getStore().indexOf(record);
		creative_ranking.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = creative_ranking.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/ext-creative-ranking!commit.action',
		params : {
			'info' : info
		},
		success : function(e) {
			creative_ranking.getStore().reload();
		},
		failure : function(e) {
		}
	});
});
