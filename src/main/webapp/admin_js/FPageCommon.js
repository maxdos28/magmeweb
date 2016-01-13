//var fpage_status_combo_store = new Ext.data.JsonStore({
//	id : 'fpage_status_combo_store',
//	fields:['id','value'],
//	data : [{'id':'0','value':'无效'},{'id':'1','value':'生效'}]
//});
//var fpage_status_combo = new Ext.form.ComboBox({
//	id : 'fpage_status_combo',
//	store : fpage_status_combo_store,
//	displayField : 'value',
//	valueField : 'id',
//	editable : false,
//	triggerAction : 'all',
//	mode : 'local'
//});

//列定义 begin
//var tf_allow_blank=new Ext.form.TextField( {allowBlank : true});
//var tf_noallow_blank=new Ext.form.TextField( {allowBlank : false});
//var col_orderId={header:"序号",dataIndex:"orderId",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_title={header:"标题",dataIndex:"title",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_description={header:"描述",dataIndex:"description",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_publicationId={header:"杂志ID",dataIndex:"publicationId",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_issueId={header:"期刊ID",dataIndex:"issueId",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_pageNo={header:"页码",dataIndex:"pageNo",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_tagId={header:"标签ID",dataIndex:"tagId",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_imgFile={header:"图片文件",dataIndex:"imgFile",width:50,editor:new Ext.form.TextField( {allowBlank : false})};
//var col_status={header:"状态",dataIndex:"status",width:50,editor:fpage_status_combo,renderer:function(value,cellmeta,record){if(value==0){return '无效';}else{return '生效';}}};
//var col_createdTime={header:"创建时间",dataIndex:"createdTime",width:50};
//var col_updatedTime={header:"更新时间",dataIndex:"updatedTime",width:50};
//列定义 end

//var sm = new Ext.grid.RowSelectionModel({singleSelect:false});


var beforeclose = function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
};