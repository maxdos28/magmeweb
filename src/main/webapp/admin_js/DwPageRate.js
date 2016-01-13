var dwpagerate_store = new Ext.data.JsonStore({
		proxy: new Ext.data.HttpProxy({
			url: 'admin/dw-page-view!rate.action'
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
				dwpagerate_grid.getEl().mask("加载数据中，请稍候...");
			},
			"load":function(_window){
				dwpagerate_grid.getEl().unmask();
			}
		}
	 }); 

var dwpagerate_search_from = new Ext.form.DateField({
	id: 'dwpagerate_from_date',
	format: 'Y-m-d',
	fieldLabel: 'Date of from',
	name: 'dob',
	width:80,
	allowBlank:true,
	editable:true
});

var dwpagerate_search_to = new Ext.form.DateField({
	id: 'dwpagerate_to_date',
	format: 'Y-m-d',
	fieldLabel: 'Date of to',
	name: 'dob',
	width:80,
	allowBlank:true,
	editable:true
});

var dwpagerate_grid = new Ext.Panel({
		id:"dwpagerate_grid",
		title: '黏着度',
		closable : true,
		width:500,
		height:300,
		layout:'fit',
		items: {
			xtype: 'linechart',	//线型图
			store: dwpagerate_store,
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
			   var str = String.format('时间:{0}\n黏着度:{1}'+'%',ne,record.get('count'))  
				return str;  
			 },
			listeners: {
//				itemclick: function(o){
//					var rec = dwpagerate_store.getAt(o.index);
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
		tbar : ['开始：',dwpagerate_search_from,'结束',dwpagerate_search_to,
		'-',{id:'dwpagerate_date_search',text:'查询'},{id: 'dwpagerate_excel_btn', text: '导出EXCEL'}]
	});

dwpagerate_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

//查询按钮
Ext.getCmp("dwpagerate_date_search").on('click',function(e){
	var from = Ext.getCmp("dwpagerate_from_date").getRawValue();
	var to = Ext.getCmp("dwpagerate_to_date").getRawValue();
	dwpagerate_store.reload({params:{'start':from,'end':to}});
});

//导出
Ext.getCmp("dwpagerate_excel_btn").on('click',function(e){
	var from = Ext.getCmp('dwpagerate_from_date').getRawValue();
	var to = Ext.getCmp('dwpagerate_to_date').getRawValue();
	var appWindow = window.open("http://www.magme.com/admin/dw-page-view!outputExcelRate.action?"
					+"start="+from+"&to=" + to);   
	appWindow.focus();
});