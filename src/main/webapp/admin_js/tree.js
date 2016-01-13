var tree = new Ext.tree.TreePanel( {
	region : 'west',
	split : true,
	useArrows : true,
	autoScroll : true,
	animate : true,
	enableDD : false,
	containerScroll : true,
	border : true,
	header : true,
	collapsible : true,
	title : 'ITEMS',
	height : 600,
	width : 140,
	root : {
		nodeType : 'async',
		text : 'root',
		id : 'root'
	},
	dataUrl : 'admin/admin!tree.action',
	rootVisible : false,
	selModel : new Ext.tree.DefaultSelectionModel(),
	bbar : [{
			text : '退出系统',
			width: 140,
			handler : function() {
				window.location = '/admin_login.html'; 
			}
		}]
});
Ext.chart.Chart.CHART_URL = '/extjs/resources/charts.swf';
tree.on('click',function(node,e){
	if(node.text == '首页模板管理'){
		tab.add(fpagetemplate_grid);
		fpagetemplate_grid.show();
		fpagetemplate_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '首页元素管理'){
		tab.add(fpageitem_grid);
		fpageitem_grid.show();
		fpageitem_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '首页事件管理'){
		tab.add(fpageevent_grid);
		fpageevent_grid.show();
		fpageevent_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '切米'){
		tab.add(fpageinfo_grid_H0);
		fpageinfo_grid_H0.show();
		fpageinfo_grid_H0.getStore().load({params:{'type' :'H0' , 'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '推荐标签'){
		tab.add(fpageinfo_grid_M0);
		fpageinfo_grid_M0.show();
		fpageinfo_grid_M0.getStore().load({params:{'type' :'M0' , 'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '推荐杂志'){
		tab.add(fpageinfo_grid_L0);
		fpageinfo_grid_L0.show();
		fpageinfo_grid_L0.getStore().load({params:{'type' :'L2' , 'page.start' : 0,'page.limit' : 50}});
	}else if(node.text == '推荐热门杂志'){
		tab.add(fpageinfo_grid_L1);
		fpageinfo_grid_L1.show();
		fpageinfo_grid_L1.getStore().load({params:{'type' :'L1' , 'page.start' : 0,'page.limit' : 50}});
	}else if(node.text == '首页榜单管理'){
		tab.add(creative_ranking);
		creative_ranking.show();
		creative_ranking.getStore().load({params:{'page.start' : 0,'page.limit' : 200}});
	}
	else if(node.text == '原始分类'){
		tab.add(category_grid);
		category_grid.show();
		category_parent_combo_store.load(); //为了渲染parentName,不能保证数据能渲染，因为都是异步的，若有问题再解决
		category_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '展示分类'){
		tab.add(sort_grid);
		sort_grid.show();
		sort_grid.getStore().load();
	} else if(node.text == '出版商管理'){
		tab.add(publisher_grid);
		publisher_grid.show();
		publisher_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '通用评论管理'){
		tab.add(common_comment);
		common_comment.show();
		common_comment.getStore().load({params:{'page.start' : 0,'page.limit' : 200}});
	} else if(node.text == '广告商管理'){
		tab.add(adagency_grid);
		adagency_grid.show();
		adagency_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '用户管理'){
		tab.add(user_grid);
		user_grid.show();
		user_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '编辑认证管理'){
		tab.add(user_ex_grid);
		user_ex_grid.show();
		user_ex_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '期刊管理'){
		tab.add(issue_grid);
		issue_grid.show();
		issue_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == 'familyissue'){
		tab.add(familyissue_grid);
		familyissue_grid.show();
		familyissue_publication_combo_store.load();	
		familyissue_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == 'familycategory'){
		tab.add(familycate_grid);
		familycate_grid.show();
		familycate_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '杂志管理'){
		tab.add(publication_grid);
		publication_grid.show();
		publication_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '切米管理'){
		tab.add(tag_grid);
		tag_grid.show();
		tag_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '广告管理'){
		tab.add(advertise_grid);
		advertise_grid.show();
		advertise_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '问答管理'){
		tab.add(dialog_grid);
		dialog_grid.show();
		dialog_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '管理员管理'){
		tab.add(admin_grid);
		admin_grid.show();
		admin_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '期刊流量'){
		tab.add(stat_grid);
		stat_grid.show();
		stat_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '杂志流量'){
		tab.add(pubstat_grid);
		pubstat_grid.show();
		pubstat_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '杂志周排名'){
		tab.add(statweek_grid);
		statweek_grid.show();
		statweek_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50,'date':new Date}});
	} else if(node.text == '杂志周排名（制作）'){
		tab.add(statweekm_grid);
		statweekm_grid.show();
		statweekm_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50,'date':new Date}});
	} else if(node.text == '标签评论管理'){
		tab.add(tagcomment_grid);
		tagcomment_grid.show();
		tagcomment_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '反馈内容管理'){
		tab.add(feedback_grid);
		feedback_grid.show();
		feedback_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50, 'feedBack.status': 1}});
	} else if(node.text == '反馈种类管理'){
		tab.add(feedback_category_grid);
		feedback_category_grid.show();
		feedback_category_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '消息群发'){
		tab.add(msgsend_grid);
		msgsend_grid.show();
		msgsend_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50,'user.status' : 1}});
	} else if(node.text == '杂志阅读排行'){
		tab.add(dmpublication_grid);
		dmpublication_grid.show();
		dmpublication_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50,'user.status' : 1}});
	} else if(node.text == '杂志阅读周排行'){
		tab.add(dmpublicationWeek_grid);
		dmpublicationWeek_grid.show();
		//dmpublicationWeek_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50,'user.status' : 1}});
	} else if(node.text == '期刊阅读报表'){
		tab.add(dmissue_grid);
		dmissue_grid.show();
		dmissue_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '期刊页码报表'){
		tab.add(dmissuepage_grid);
		dmissuepage_grid.show();
		dmissuepage_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '统计明细图表'){
//		tab.add({
//			id:node.id, 
//			title : 'index',
////			autoLoad:true,
//// 			contentType: 'html',
////			loadMask: true,
////			frame : true,
////			scripts:true,
////			html : "<iframe width=100% height=100% src='http://www.magme.com/index.action' />"
//			closable:true,
//			html: 
//				"<html><head><title>JSChart</title>"+
//				"</head><body><div id='graph'>Loading graph...</div>" +
//			//	"<script type='text/javascript' src='admin_js/chart.js'></script></body>" +
//				"</html>",
//			listeners:{
//				'activate':function(){
//					var myData = new Array([10, 2], [15, 0], [18, 3], [19, 6], [20, 8.5], [25, 10], [30, 9], [35, 8], [40, 5], [45, 6], [50, 2.5]);
//					var myChart = new JSChart('graph', 'line');
//					myChart.setDataArray(myData);
//					myChart.setLineColor('#8D9386');
//					myChart.setLineWidth(4);
//					myChart.setTitleColor('#7D7D7D');
//					myChart.setAxisColor('#9F0505');
//					myChart.setGridColor('#a4a4a4');
//					myChart.setAxisValuesColor('#333639');
//					myChart.setAxisNameColor('#333639');
//					myChart.setTextPaddingLeft(0);
//					myChart.setAxisValuesAngle(90);
//					myChart.draw();
//				}
//			}
//		});
//		var n = tab.getComponent(node.id);
//		tab.setActiveTab(n);
		window.open("http://www.magme.com/stat/dm-adview.action","_blank");
	} else if(node.text == '事件排行报表'){
		tab.add(dmevent_grid);
		dmevent_grid.show();
		//dmevent_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '站内搜索报表'){
		tab.add(dmsearch_grid);
		dmsearch_grid.show();
		//dmsearch_grid.getStore().load({params:{'page.start' : 0,'page.limit' : 50}});
	} else if(node.text == '黏着访问量'){
		tab.add(dwpageview_grid);
		dwpageview_grid.show();
	} else if(node.text == '黏着度'){
		tab.add(dwpagerate_grid);
		dwpagerate_grid.show();
	}
	
});