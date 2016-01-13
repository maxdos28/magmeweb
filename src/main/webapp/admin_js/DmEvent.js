var dmevent_store = new Ext.data.JsonStore( {
	url : 'admin/dm-event!page.action',
	root : 'data',
	//totalProperty : 'eventCategoryId',
	fields : [ 'eventId','eventTitle','uv','publicationName' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var dmevent_search_from = new Ext.form.DateField({
	id: 'dmevent_search_from',
	value: null,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:false
});

var dmevent_search_to = new Ext.form.DateField({
	id: 'dmevent_search_to',
	value: null,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:false
});

var dmevent_grid= new Ext.grid.EditorGridPanel( {
	id : 'dmevent_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '事件排行报表',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [{
			header : "事件编号",
			dataIndex : "eventId",
			width : 130
		},{
			header : "事件标题",
			dataIndex : "eventTitle",
			width : 130
		},{
			header : "点击次数",
			dataIndex : "uv",
			width : 130
		},{
			header : "出自杂志",
			dataIndex : "publicationName",
			width : 130
		}]
	}),
	region : 'center',
	store : dmevent_store,
	viewConfig: {
			forceFit: true,
			scrollToRecord:function(record){  
			var index = dmevent_grid.getStore().indexOf(record);  
			this.focusRow(index);
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : dmevent_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
					//o['statDmPagenator.eventCategoryId'] = Ext.getCmp('dmevent_search_id').getRawValue();
					o['statDmPagenator.startDate'] = Ext.getCmp('dmevent_search_from').getRawValue();
					o['statDmPagenator.endDate'] = Ext.getCmp('dmevent_search_to').getRawValue();}
		}
	}),
	tbar : ['从：',dmevent_search_from,
			'至：',dmevent_search_to,
			'-',{id:'dmevent_search',text:'查询'},
				{id: 'event_excel_btn', text: '导出EXCEL'}]
});

//查询按钮
Ext.getCmp("dmevent_search").on('click',function(e){
	var from = Ext.getCmp('dmevent_search_from').getRawValue();
	var to = Ext.getCmp('dmevent_search_to').getRawValue();
	dmevent_store.reload({params:{'page.start':0,'page.limit':50,
				'statDmPagenator.startDate':from,'statDmPagenator.endDate':to}});
});

dmevent_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//导出
Ext.getCmp("event_excel_btn").on('click',function(e){
	var from = Ext.getCmp('dmevent_search_from').getRawValue();
	var to = Ext.getCmp('dmevent_search_to').getRawValue();
	var appWindow = window.open("http://www.magme.com/admin/dm-event!outputExcel.action?"
					+ "statDmPagenator.startDate="+from+"&statDmPagenator.endDate=" + to);   
	appWindow.focus();
});
