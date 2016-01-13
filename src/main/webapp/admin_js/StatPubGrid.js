var pubstat_store = new Ext.data.JsonStore( {
	url : 'admin/flow-stat-pub!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'publicationId',
	fields : [ 'publicationId','publicatioName','sum'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var pubstat_grid = new Ext.grid.EditorGridPanel( {
	id : 'pubstat_grid',
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
			header : "杂志编号",
			dataIndex : "publicationId",
			width : 200
		}, {
			header : "杂志名称",
			dataIndex : "publicatioName",
			width : 200
		}, {
			header : "流量",
			dataIndex : "sum",
			width : 200
		}, {
			header : "",
			dataIndex : "publicationId",
			width : 200,
			renderer : function(value, metadata, record) {
				var pubNm = record.get('publicatioName');
				return "<a href='#' onclick=javascript:ShowPubStatWin('"+ value +"','"+pubNm+"')>明细</a>";  
			} 
		}]
	}),
	region : 'center',
	store : pubstat_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = pubstat_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : pubstat_store,
		displayInfo : true,
		listeners : {'beforechange':function(page,o){
						o['stat.id'] = Ext.getCmp('pub_search_id').getRawValue();
					}
		}
	}),
	tbar : ['杂志编号：',{xtype:'textfield',id:'pub_search_id'},'-',{id:'pub_search',text:'查询'}]
});

pubstat_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//查询按钮
Ext.getCmp("pub_search").on('click',function(e){
	var id = Ext.getCmp("pub_search_id").getRawValue();
	pubstat_store.reload({params:{'page.start':0,'page.limit':50,'stat.id':id}});
});

function ShowPubStatWin(id,pubNm){
	
	//定义store
	var chartStore= new Ext.data.JsonStore({
		typeAhead: true,
		lazyRender: true,
		editable: false,
		allowBlank: true,
		lazyInit: true,
		autoLoad : true,
		proxy: new Ext.data.HttpProxy({
			url: 'admin/flow-stat-pub!detail.action?pubId='+id//数据来源
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
				displayName: pubNm + '：访问量', 
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