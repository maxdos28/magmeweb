var user_date = new Date();
user_date = Ext.util.Format.date(user_date,'Y-m-d');

var statweek_store = new Ext.data.JsonStore( {
	url : 'admin/flow-stat-week-pub-user!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'pubId',
	fields : [ 'pubId','pubName','muCountM','pvCountM','rankM',"betweenWeek"],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var user_search_date = new Ext.form.DateField({
	id: 'user_search_date',
	value: new Date,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:true
});

var statweek_grid = new Ext.grid.EditorGridPanel( {
	id : 'statweek_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '杂志周排行',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "杂志编号",
			dataIndex : "pubId",
			width : 100
		},{
			header : "杂志名称",
			dataIndex : "pubName",
			width : 200
		}, {
			header : "独立访问数",
			dataIndex : "muCountM",
			width : 200
		}, {
			header : "pv量",
			dataIndex : "pvCountM",
			width : 200
		}, {
			header : "排名",
			dataIndex : "rankM",
			width : 200
		}, {
			header : "所在周",
			dataIndex : "betweenWeek",
			width : 200
		}]
	}),
	region : 'center',
	store : statweek_store,
	viewConfig: {
		forceFit: true,
		scrollToRecord:function(record){  
			var index = statweek_grid.getStore().indexOf(record);  
			this.focusRow(index);  
  		}
	},
	sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : statweek_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
						o['date'] = user_date;
					}
		}
	}),
	tbar : ['日期：',user_search_date,{id:'user_search_btn',text:'查询'}]
});

statweek_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//查询按钮
Ext.getCmp("user_search_btn").on('click',function(e){
	user_date = Ext.getCmp("user_search_date").getRawValue();
	statweek_store.reload({params:{'page.start':0,'page.limit':50,'date':user_date}});
});

