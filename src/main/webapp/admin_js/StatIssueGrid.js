var stat_store = new Ext.data.JsonStore( {
	url : 'admin/flow-stat!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'issueId',
	fields : [ 'issueId','publicatioId','publicatioName','issueNumber','sum'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var search_from = new Ext.form.DateField({
	id: 'from_date',
	format: 'Y-m-d',
	fieldLabel: 'Date of from',
	name: 'dob',
	width:80,
	allowBlank:true,
	editable:true
});

var search_to = new Ext.form.DateField({
	id: 'to_date',
	format: 'Y-m-d',
	fieldLabel: 'Date of to',
	name: 'dob',
	width:80,
	allowBlank:true,
	editable:true
});

var stat_grid = new Ext.grid.EditorGridPanel( {
	id : 'stat_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '统计信息',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "期刊编号",
			dataIndex : "issueId",
			width : 200
		},{
			header : "期刊封面",
			dataIndex : "issueId",
			width : 200,
			renderer:function(value, metadata, record){
				var pubid = record.get('publicatioId');
				var path = "http://static.magme.com/pdfprofile/" + pubid + "/100_"+ value +".jpg";
				return String.format('<image src='+path +' />');
			}
		}, {
			header : "杂志名称",
			dataIndex : "publicatioName",
			width : 200
		}, {
			header : "期刊刊号",
			dataIndex : "issueNumber",
			width : 200
		}, {
			header : "总流量",
			dataIndex : "sum",
			width : 200
		}, {
			header : "",
			dataIndex : "issueId",
			width : 200,
			renderer : function(value, metadata, record) {  
				var pubNm = record.get('publicatioName');
				var issueNm = record.get('issueNumber');
				return "<a href='#' onclick=javascript:ShowIssueStatWin('"+ value +"','"+pubNm+"','"+issueNm+"')>明细</a>";  
			} 
		}]
	}),
	region : 'center',
	store : stat_store,
	viewConfig: {
		forceFit: true,
		scrollToRecord:function(record){  
			var index = stat_grid.getStore().indexOf(record);  
			this.focusRow(index);  
  		}
	},
	sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : stat_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
						o['stat.id'] = Ext.getCmp('issue_search_name').getRawValue();
						o['stat.pubNm'] = Ext.getCmp('pub_search_name').getRawValue();
						o['stat.from'] = Ext.getCmp('from_date').getRawValue();
						o['stat.to'] = Ext.getCmp('to_date').getRawValue();
					}
		}
	}),
	tbar : ['杂志名：',{xtype:'textfield',id:'pub_search_name'},'期刊号：',{xtype:'textfield',id:'issue_search_name'},'开始：',search_from,'结束',search_to,'-',{id:'date_search',text:'查询'}]
});

function ShowIssueStatWin(id,pubNm,issueNm){
	
	//定义store  
	var chartStore= new Ext.data.JsonStore({
		typeAhead: true,
		lazyRender: true,
		editable: false,
		allowBlank: true,
		lazyInit: true,
		autoLoad : true,
		proxy: new Ext.data.HttpProxy({
			url: 'admin/flow-stat!detail.action?issueId='+id//数据来源
		}),
		fields: [{
			name: 'date',
			mapping: 'date'
		}, {
			name: 'count',
			mapping: 'count'
		}]
	 }); 

	//载入数据
	var linechart = new Ext.chart.LineChart({  
			title:'工单积压图表',	
			xtype:'linechart',  
			url: '/extjs/resources/charts.swf',  
			store: chartStore,
			//定义tip内容  
			tipRenderer : function(chart, record){
			   var ne = record.get('date');  
			   var str = String.format('时间:{0}\n流量:{1}',ne,record.get('count'))  
				return str;  
			 },  
			 //定义一个是折线图  
			 series: [{  
				type: 'line',  
				id: "billId", 
				displayName: pubNm + issueNm +"刊： 访问量", 
				xField: 'date',  
				yField: 'count',  
				style: {  
					color:0x99BBE8,  
					size: 20  
				}  
			}],  
				listeners:{  
					"show":function(){  
							var c = linechart.series;  
							//alert(c[1].store);  
							//c[1].style.color='#00ff00';  
							  
						}  
					},  
				//定义图表样式  
				chartStyle: {
				  legend:{  
					display: "top"  
				  },  
				  xAxis: {  
					color: 0x69aBc8,  
					majorTicks: {color: 0x69aBc8, length:4},  
					minorTicks: {color: 0x69aBc8, length: 2},  
					majorGridLines:{size: 1, color: 0xeeeeee}  
				  },  
				  yAxis: {  
					color: 0x69aBc8,  
					majorTicks: {color: 0x69aBc8, length: 4},  
					minorTicks: {color: 0x69aBc8, length: 2},  
					majorGridLines: {size: 1, color: 0xdfe8f6}  
				  }  
				}  
				
		});
	
	var chartWin = new Ext.Window({  
			title: '明细',  
			//layout: 'fit',  
			closable: true,  
			plain: true,  
			height: 300,  
			width: 1000,
			items: linechart  
		});
	chartWin.show();
}

stat_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//查询按钮
Ext.getCmp("date_search").on('click',function(e){
	var id = Ext.getCmp("issue_search_name").getRawValue();
	var pubNm = Ext.getCmp("pub_search_name").getRawValue();
	var from = Ext.getCmp("from_date").getRawValue();
	var to = Ext.getCmp("to_date").getRawValue();
	//alert(to);
	stat_store.reload({params:{'page.start':0,'page.limit':50,'stat.id':id,'stat.pubNm':pubNm,'stat.from':from,'stat.to':to}});
//	stat_store.reload({params:{'page.start':0,'page.limit':50,'stat.id':id}});
});


	
 