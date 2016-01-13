var date = new Date();
date = Ext.util.Format.date(date,'Y-m-d');

var statweekm_store = new Ext.data.JsonStore( {
	url : 'admin/flow-stat-week-pub!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'pubId',
	fields : [ 'pubId','pubName','muCountM','pvCountM','rankM',"muCount","pvCount","rank","betweenWeek","para"],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var search_date = new Ext.form.DateField({
	id: 'search_date',
	value: new Date,
	format: 'Y-m-d',
	fieldLabel: 'Date for search',
	name: 'dob',
	width:82,
	allowBlank:true,
	editable:true
});

var statweekm_grid = new Ext.grid.EditorGridPanel( {
	id : 'statweekm_grid',
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
			header : "独立访问数(调整后)",
			dataIndex : "muCountM",
			width : 200
		}, {
			header : "pv量(调整后)",
			dataIndex : "pvCountM",
			width : 200
		}, {
			header : "排名(调整后)",
			dataIndex : "rankM",
			width : 200
		}, {
			header : "独立访问数",
			dataIndex : "muCount",
			width : 200
		}, {
			header : "pv量",
			dataIndex : "pvCount",
			width : 200
		}, {
			header : "排名",
			dataIndex : "rank",
			width : 200
		}, {
			header : "所在周",
			dataIndex : "betweenWeek",
			width : 200
		},{
			header : "调整系数",
			dataIndex : "para",
			width : 100,
			editor : new Ext.form.NumberField( {
				allowBlank : true
			})
		}]
	}),
	region : 'center',
	store : statweekm_store,
	viewConfig: {
		forceFit: true,
		scrollToRecord:function(record){  
			var index = statweekm_grid.getStore().indexOf(record);  
			this.focusRow(index);  
  		}
	},
	sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : statweekm_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
						o['date'] = date;
					}
		}
	}),
	tbar : ['日期：',search_date,{id:'search_btn',text:'查询'},'-','一键设定系数：',
		{
		    xtype: 'numberfield',
		    id: 'parameter',
		    width: 50
		},
		{
		    xtype: 'buttongroup',
		    items: [{
		        id: 'input_btn',
		        text: '生成修改'
		    }, {
		        id: 'modify_btn',
		        text: '发布'
		    }, {
		        id: 'output_btn',
		        text: '导出EXCEL'
		    }]
		}
	]
});

statweekm_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//查询按钮
Ext.getCmp("search_btn").on('click',function(e){
	date = Ext.getCmp("search_date").getRawValue();
	statweekm_store.reload({params:{'page.start':0,'page.limit':50,'date':date}});
});

//生成修改
Ext.getCmp("input_btn").on('click',function(e){
	
	var parameter = Ext.getCmp("parameter").getRawValue();
	
	var records = statweekm_grid.getStore().getModifiedRecords();
	if(records.length <= 0 && parameter == ''){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	
	Ext.MessageBox.show({
				title:'提示框',
				msg: '是否一键设定:',
				buttons:{"ok":"是","cancel":"否"},
				fn:function(btn,text){
					if(btn == 'ok') {
						Ext.Ajax.request({
							url : 'admin/flow-stat-week-pub!inputData.action',
							params :{'date' : date,'parameter' : parameter},
							success : function(e){
								statweekm_grid.getStore().reload();
								alert('操作已成功 ！');
							},
							failure : function(e){}
						});
					} else {
						Ext.Ajax.request({
							url : 'admin/flow-stat-week-pub!inputData.action',
							params :{'date' : date,'info' : info},
							success : function(e){
								statweekm_grid.getStore().reload();
								alert('操作已成功 ！');
							},
							failure : function(e){}
						});
					}
				}
			});
});

//发布按钮
Ext.getCmp("modify_btn").on('click',function(e){
	Ext.MessageBox.show({
					title:'提示框',
					msg: '确认发布:',
					buttons:{"ok":"是","cancel":"否"},
					fn:function(btn,text){
						if(btn == 'ok') {
							Ext.Ajax.request({
								url : 'admin/flow-stat-week-pub!release.action',
								params :{'date' : date},
								success : function(e){
									statweekm_grid.getStore().reload();
									alert('发布已成功 ！');
								},
								failure : function(e){}
							});
						}
					}
				});
});
//导出
Ext.getCmp("output_btn").on('click',function(e){
	var appWindow = window.open("http://www.magme.com/admin/flow-stat-week-pub!outputExcel.action?date="+date);   
	appWindow.focus();
});
