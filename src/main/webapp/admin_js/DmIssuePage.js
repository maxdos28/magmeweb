var dmissuepage_store = new Ext.data.JsonStore( {
	url : 'admin/dm-issue-page!page.action',
	root : 'data',
	totalProperty : 'total',
	//idProperty : 'dataDate','issuepageId',
	fields : [ 'dataDate','issueId','pageNo','issueNumber',
				'publishDate','publicationId','publicationName',
				'retention','clickNum','insertTime' ],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var dmissuepage_search_date = new Ext.form.DateField({
	id: 'dmissuepage_search_date',
	value: null,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:false
});

var dmissuepage_grid= new Ext.grid.EditorGridPanel( {
	id : 'dmissuepage_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '期刊页码报表',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [{
			header : "杂志名称",
			dataIndex : "publicationId",
			width : 130,
			renderer: function(value, cellmeta, record){
				return value +"_"+record.get('publicationName');
			}
		}, {
			header : "期刊名称",
			dataIndex : "issueId",
			width : 130,
			renderer: function(value, cellmeta, record){
				return value +"_"+record.get('issueNumber');
			}
		},{
			header : "页码",
			dataIndex : "pageNo",
			width : 130
		},{
			header : "停留时间(秒)",
			dataIndex : "retention",
			width : 130,
			renderer: function(value, cellmeta, record){
				return Math.round(value/1000);
			}
		},{
			header : "时间",
			dataIndex : "dataDate",
			width : 130
		}]
	}),
	region : 'center',
	store : dmissuepage_store,
	viewConfig: {
			forceFit: true,
			scrollToRecord:function(record){  
			var index = dmissuepage_grid.getStore().indexOf(record);  
			this.focusRow(index);
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : dmissuepage_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
					o['dmIssuePage.issueId'] = Ext.getCmp('dmissuepage_search_id').getRawValue();
					o['dmIssuePage.dataDate'] = Ext.getCmp('dmissuepage_search_date').getRawValue();}
		}
	}),
	tbar : ['期刊编号：',{xtype:'textfield',id:'dmissuepage_search_id'},
			'日期：',dmissuepage_search_date,
			'-',{id:'dmissuepage_search',text:'查询'},
				{id: 'dmissuepage_excel_btn', text: '导出EXCEL'}]
});

//查询按钮
Ext.getCmp("dmissuepage_search").on('click',function(e){
	var id = Ext.getCmp("dmissuepage_search_id").getRawValue();
	var date = Ext.getCmp('dmissuepage_search_date').getRawValue();
	dmissuepage_store.reload({params:{'page.start':0,'page.limit':50,
				'dmIssuePage.issueId':id,'dmIssuePage.dataDate':date}});
});

dmissuepage_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//导出
Ext.getCmp("dmissuepage_excel_btn").on('click',function(e){
	var id = Ext.getCmp("dmissuepage_search_id").getRawValue();
	var date = Ext.getCmp('dmissuepage_search_date').getRawValue();
	var appWindow = window.open("http://www.magme.com/admin/dm-issue-page!outputExcel.action?"
					+"dmIssuePage.issueId=" + id + "&dmIssuePage.dataDate="+date);   
	appWindow.focus();
});
