var feedback_category_store = new Ext.data.JsonStore( {
	url : 'admin/feed-back-category!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', , 'name', 'status'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var feedbackcategory_status_combo_store = new Ext.data.JsonStore({
	fields : ['value','name'],
	data : [{'value':0,'name':'无效'},{'value':1,'name':'正常'}]
});
var feedbackcategory_status_combo = new Ext.form.ComboBox({
	id : 'feedbackcategory_status_combo',
	store : feedbackcategory_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});
var feedbackcategory_search_status_combo = new Ext.form.ComboBox({
	id : 'feedbackcategory_search_status_combo',
	store : feedbackcategory_status_combo_store,
	mode : 'local',
	valueField : 'value',
	displayField : 'name',
	editable  : false,
	forceSelection : true,
	triggerAction: 'all',
	lazyRender:true
});

var feedback_category_grid = new Ext.grid.EditorGridPanel( {
	id : 'feedback_category_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '反馈种类状态',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "Id",
			dataIndex : "id",
			width : 200
		}, {
			header : "种类名称",
			dataIndex : "name",
			width : 200,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "种类状态",
			dataIndex : "status",
			width : 200,
			editor : feedbackcategory_status_combo,
			renderer: function(value, cellmeta, record){
				var record = feedbackcategory_status_combo_store.query('value',value).itemAt(0);
				return record.get('name');
			}
		}]
	}),
	region : 'center',
	store : feedback_category_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = feedback_category_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : feedback_category_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
				o['feedBackCategory.status'] = feedbackcategory_search_status_combo.getValue();
				}
			}
	}),
	tbar: [{
		xtype: 'buttongroup',
		items: [{
			id: 'feedbackcategory_add',
			text: '添加'
		},{
			id: 'feedbackcategory_delete',
			text: '删除'
		}, {
			id: 'feedbackcategory_commit',
			text: '提交'
		}]
	}, '状态：', feedbackcategory_search_status_combo, '-',
	{
		id: 'feedbackcategory_search',
		text: '查询'
	}]
});

//查询按钮
Ext.getCmp("feedbackcategory_search").on('click',function(e){
	var status = feedbackcategory_search_status_combo.getValue();
	//var status = Ext.getCmp('feedbackcategory_status_combo_store').getValue();
	//alert(feedbackcategory_search_status_combo.getValue())
	feedback_category_store.reload({params:{'page.start':0,'page.limit':50,'feedBackCategory.status':status}});
});

feedback_category_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	feedback_category_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			feedback_category_grid.getStore().remove(record);
			feedback_category_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_name = record.isModified('name');
			if (!ret_name) {
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				feedback_category_grid.getStore().remove(record);
				feedback_category_grid.getStore().getModifiedRecords().remove(record);
 			}else{
			}
		}
		added = false;
	}
});

var feedbackcategory_add_btn = Ext.getCmp('feedbackcategory_add');
feedbackcategory_add_btn.on('click',function(e){
	if(added){
		var sm = feedback_category_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = feedback_category_grid.getStore().indexOf(record);
		feedback_category_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = feedback_category_grid.getStore().recordType;
	var p = new Plant({id:"", name:"", status:1});
	feedback_category_grid.stopEditing();
	feedback_category_grid.getStore().add(p);
	feedback_category_grid.getSelectionModel().clearSelections();
	feedback_category_grid.getSelectionModel().selectLastRow();
	feedback_category_grid.getView().scrollToRecord(p);	
	added = true;
});

var feedbackcategory_delete_btn = Ext.getCmp('feedbackcategory_delete');
feedbackcategory_delete_btn.on('click',function(e){
	if(added){
		var sm = feedback_category_grid.getSelectionModel();
		var record = sm.getSelected();
		feedback_category_grid.getStore().remove(record);
		feedback_category_grid.getStore().getModifiedRecords().remove(record);
		added = false;
	}
	var records = feedback_category_grid.getSelectionModel().getSelections();
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
					var url = 'admin/feed-back-category!delete.action';
					GridUtil.deleteSelected(records,url,feedback_category_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
});

var feedbackcategory_commit_btn = Ext.getCmp('feedbackcategory_commit');
feedbackcategory_commit_btn.on('click',function(e){
	if(added){
//		var sm = feedback_category_grid.getSelectionModel();
//		var record = sm.getSelected();
//		var row = feedback_category_grid.getStore().indexOf(record);
//		var ret_name = record.isModified('name');
//		if(!ret_name){
//			Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
//			return;
//		}
//		added = false;
		var sm = feedback_category_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = feedback_category_grid.getStore().indexOf(record);
		feedback_category_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record)
	}
	var records = feedback_category_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/feed-back-category!commit.action',
		params :{'info' : info},
		success : function(e){
			feedback_category_grid.getStore().reload();
		},
		failure : function(e){}
	});
});
Ext.getCmp('feedbackcategory_search_status_combo').setValue(1); 