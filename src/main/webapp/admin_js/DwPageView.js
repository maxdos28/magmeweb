var dwpageview_store = new Ext.data.JsonStore({
		proxy: new Ext.data.HttpProxy({
			url: 'admin/dw-page-view!visits.action'
		}),
		fields: [{
			name: 'date',
			mapping: 'date'
		}, {
			name: 'count',
			mapping: 'count'
		}],
		listeners: {
			"beforeload":function(_window){
				dwpageview_grid.getEl().mask("加载数据中，请稍候...");
			},
			"load":function(_window){
				dwpageview_grid.getEl().unmask();
			}
		}
	 }); 

var dwpageview_search_from = new Ext.form.DateField({
	id: 'dwpageview_from_date',
	format: 'Y-m-d',
	fieldLabel: 'Date of from',
	name: 'dob',
	width:80,
	allowBlank:true,
	editable:true
});

var dwpageview_search_to = new Ext.form.DateField({
	id: 'dwpageview_to_date',
	format: 'Y-m-d',
	fieldLabel: 'Date of to',
	name: 'dob',
	width:80,
	allowBlank:true,
	editable:true
});

var dwpageview_grid = new Ext.Panel({
		id:"dwpageview_grid",
		title: '黏着访问量',
		closable : true,
		width:500,
		height:300,
		layout:'fit',
		items: {
			xtype: 'linechart',	//线型图
			store: dwpageview_store,
			xField: 'date',
			yField: 'count',
			 series: [{  
				type: 'line',  
				id: "billId", 
				displayName: "趋势图", 
				xField: 'date',  
				yField: 'count',  
				style: {  
					color:0x99BBE8
				}  
			}],
			//定义tip内容  
			tipRenderer : function(chart, record){
			   var ne = record.get('date');  
			   var str = String.format('时间:{0}\n黏着访问量:{1}',ne,record.get('count'))  
				return str;  
			 },
			listeners: {
//				itemclick: function(o){
//					var rec = dwpageview_store.getAt(o.index);
//					Ext.example.msg('Item Selected', 'You chose {0}.', rec.get('count'));
//				}
			},
		   //定义图表样式  
			chartStyle: {
			  legend:{  
				display: "top"  
			  },  
			  xAxis: {  
				color: 0x69aBc8,  
				labelRotation:45,
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
		},
		tbar : ['开始：',dwpageview_search_from,'结束',dwpageview_search_to,
		'-',{id:'dwpageview_date_search',text:'查询'},{id: 'dwpageview_excel_btn', text: '导出EXCEL'}]
	});

dwpageview_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//查询按钮
Ext.getCmp("dwpageview_date_search").on('click',function(e){
	var from = Ext.getCmp("dwpageview_from_date").getRawValue();
	var to = Ext.getCmp("dwpageview_to_date").getRawValue();
	dwpageview_store.reload({params:{'start':from,'end':to}});
});

//导出
Ext.getCmp("dwpageview_excel_btn").on('click',function(e){
	var from = Ext.getCmp('dwpageview_from_date').getRawValue();
	var to = Ext.getCmp('dwpageview_to_date').getRawValue();
	var appWindow = window.open("http://www.magme.com/admin/dw-page-view!outputExcelView.action?"
					+"start="+from+"&to=" + to);   
	appWindow.focus();
});