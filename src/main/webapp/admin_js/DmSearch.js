var dmsearch_store = new Ext.data.JsonStore( {
	url : 'admin/dm-search!page.action',
	root : 'data',
	//totalProperty : 'searchCategoryId',
	fields : [ 'keyword','searchTimes'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var dmsearch_search_from = new Ext.form.DateField({
	id: 'dmsearch_search_from',
	value: null,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:false
});

var dmsearch_search_to = new Ext.form.DateField({
	id: 'dmsearch_search_to',
	value: null,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:false
});

var dmsearch_grid= new Ext.grid.EditorGridPanel( {
	id : 'dmsearch_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '站内搜索报表',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [{
			header : "搜索名词",
			dataIndex : "keyword",
			width : 130
		},{
			header : "搜索次数",
			dataIndex : "searchTimes",
			width : 130
		}]
	}),
	region : 'center',
	store : dmsearch_store,
	viewConfig: {
			forceFit: true,
			scrollToRecord:function(record){  
			var index = dmsearch_grid.getStore().indexOf(record);  
			this.focusRow(index);
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : dmsearch_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
					o['statDmPagenator.startDate'] = Ext.getCmp('dmsearch_search_from').getRawValue();
					o['statDmPagenator.endDate'] = Ext.getCmp('dmsearch_search_to').getRawValue();}
		}
	}),
	tbar : ['从：',dmsearch_search_from,
			'至：',dmsearch_search_to,
			'-',{id:'dmsearch_search',text:'查询'},
				{id: 'search_excel_btn', text: '导出EXCEL'}]
});

//查询按钮
Ext.getCmp("dmsearch_search").on('click',function(e){
	var from = Ext.getCmp('dmsearch_search_from').getRawValue();
	var to = Ext.getCmp('dmsearch_search_to').getRawValue();
	dmsearch_store.reload({params:{'page.start':0,'page.limit':50,
							'statDmPagenator.startDate':from,'statDmPagenator.endDate':to}});
});

dmsearch_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//导出
Ext.getCmp("search_excel_btn").on('click',function(e){
	var from = Ext.getCmp('dmsearch_search_from').getRawValue();
	var to = Ext.getCmp('dmsearch_search_to').getRawValue();
	var appWindow = window.open("http://www.magme.com/admin/dm-search!outputExcel.action?"
					+ "statDmPagenator.startDate="+from+"&statDmPagenator.endDate=" + to);   
	appWindow.focus();
});
