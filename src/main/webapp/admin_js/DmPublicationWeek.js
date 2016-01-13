var dmpublicationWeek_store = new Ext.data.JsonStore( {
	url : 'admin/dm-publication-week!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'publicationId',
	fields : [ 'publicationId','publicationName',
				'totalPv','totalUv','webPv','webUv','embedPv',
				'embedUv','snsPv','snsUv','wapPv','wapUv',
				'otherPv','otherUv' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var dmpublicationWeek_search_from = new Ext.form.DateField({
	id: 'dmpublicationWeek_search_from',
	value: null,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:false
});

var dmpublicationWeek_search_to = new Ext.form.DateField({
	id: 'dmpublicationWeek_search_to',
	value: null,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:false
});

var dmpublicationWeek_grid= new Ext.grid.EditorGridPanel( {
	id : 'dmpublicationWeek_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '杂志阅读排行报表',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [{
			header : "杂志名称",
			dataIndex : "publicationId",
			width : 130,
			renderer: function(value, cellmeta, record){
				return value +"_"+ record.get('publicationName');
			}
		}, {
			header : "阅读总pv",
			dataIndex : "totalPv",
			width : 130
		},{
			header : "网站阅读pv",
			dataIndex : "webPv",
			width : 130
		},{
			header : "embed阅读pv",
			dataIndex : "embedPv",
			width : 130
		},{
			header : "sns阅读pv",
			dataIndex : "snsPv",
			width : 130
		},{
			header : "手机wap阅读pv",
			dataIndex : "wapPv",
			width : 130
		},{
			header : "其它pv",
			dataIndex : "otherPv",
			width : 130
		}, {
			header : "阅读总uv",
			dataIndex : "totalUv",
			width : 130
		},{
			header : "网站阅读uv",
			dataIndex : "webUv",
			width : 130
		},{
			header : "embed阅读uv",
			dataIndex : "embedUv",
			width : 130
		},{
			header : "sns阅读uv",
			dataIndex : "snsUv",
			width : 130
		},{
			header : "手机wap阅读uv",
			dataIndex : "wapUv",
			width : 130
		},{
			header : "其它uv",
			dataIndex : "otherUv",
			width : 130
		},{
			header : "时间",
			dataIndex : "otherUv",
			width : 130
		}]
	}),
	region : 'center',
	store : dmpublicationWeek_store,
	viewConfig: {
			forceFit: true,
			scrollToRecord:function(record){  
			var index = dmpublicationWeek_grid.getStore().indexOf(record);  
			this.focusRow(index);
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : dmpublicationWeek_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
					o['dmPublication.publicationId'] = Ext.getCmp('dmpublicationWeek_search_id').getRawValue();
					o['dmPublication.fromDate'] = Ext.getCmp('dmpublicationWeek_search_from').getRawValue();
					o['dmPublication.toDate'] = Ext.getCmp('dmpublicationWeek_search_to').getRawValue();}
		}
	}),
	tbar : ['杂志编号：',{xtype:'textfield',id:'dmpublicationWeek_search_id'},
			'从：',dmpublicationWeek_search_from,
			'至：',dmpublicationWeek_search_to,
			'-',{id:'dmpublicationWeek_search',text:'查询'},
				{id: 'pubWeek_excel_btn', text: '导出EXCEL'}]
});

//查询按钮
Ext.getCmp("dmpublicationWeek_search").on('click',function(e){
	var id = Ext.getCmp("dmpublicationWeek_search_id").getRawValue();
	var from = Ext.getCmp('dmpublicationWeek_search_from').getRawValue();
	var to = Ext.getCmp('dmpublicationWeek_search_to').getRawValue();
	dmpublicationWeek_store.reload({params:{'page.start':0,'page.limit':50,
				'dmPublication.publicationId':id,'dmPublication.fromDate':from,'dmPublication.toDate':to}});
});

dmpublicationWeek_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//导出
Ext.getCmp("pubWeek_excel_btn").on('click',function(e){
	var id = Ext.getCmp("dmpublicationWeek_search_id").getRawValue();
	var from = Ext.getCmp('dmpublicationWeek_search_from').getRawValue();
	var to = Ext.getCmp('dmpublicationWeek_search_to').getRawValue();
	var appWindow = window.open("http://www.magme.com/admin/dm-publication-week!outputExcel.action?"
					+"dmPublication.publicationId=" + id + "&dmPublication.fromDate="+from+"&dmPublication.toDate=" + to);   
	appWindow.focus();
});
