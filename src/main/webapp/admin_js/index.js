Ext.onReady(function() {
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items : [ tree, tab ]
	});
	
})

var tab = new Ext.TabPanel( {
	region : 'center',
	closable : true,
	width : '80%',
	height : '100%'
});

//全局变量
var added = false;

//util
var GridUtil = {};
GridUtil.deleteSelected = function(records, url, grid,from) {
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		var id = '';
		id = records[i].get('id');
		if(id == ''){ //avoid delete items have an empty id
			continue;
		}
		ids += id;
		if (i < records.length - 1) {
			ids += ',';
		}
	}
	Ext.Ajax.request({
		url : url,
		params : {
			'ids' : ids
		},
		success : function(e) {
			grid.getStore().reload();
		},
		failure : function(e) {
		}
	})
};
GridUtil.uniquePro = function(obj, proName){
	var count = obj.getCount();
	var str = '';
	for(var i = 0;i < count; i++){
		var record = obj.getAt(i);
		var publisherid = record.get(proName);
		if(str.indexOf(publisherid) == -1){
			str += publisherid;
			str += ',';
		}
	}
	str = str.substring(0,str.length - 1);
	return str;
};

Ext.Ajax.on('requestcomplete',function(conn,response,options) {
	if (typeof response.getResponseHeader != 'undefined'){
	    if(typeof response.getResponseHeader("sessionstatus")!='undefined'){  
	        Ext.Msg.alert('提示', '会话超时，请重新登录!', function(){  
	            window.location = 'http://www.magme.com/admin_login.html';   
	        });  
	    }  
	}
});