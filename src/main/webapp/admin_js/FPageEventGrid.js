var fpageevent_store = new Ext.data.JsonStore( {
	url : 'admin/f-page-event!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : ['id','weight','reserved1','title','description', 'publicationId','issueId', 'pageNo', 'endPageNo', 'imgFile','width','height','status','isPubCover','isRecommend','isSuitMobile','createdTime', 'updatedTime','adId','advertise.id','advertise.status','advertise.startTime','advertise.endTime'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});

var fpage_status_combo_store = new Ext.data.JsonStore({
	id : 'fpage_status_combo_store',
	fields:['id','value'],
	data : [{'id':'0','value':'无效'},{'id':'1','value':'生效'},{'id':'2','value':'待发布'}]
});
var fpage_status_combo = new Ext.form.ComboBox({
	id : 'fpage_status_combo',
	store : fpage_status_combo_store,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});

var fpage_yesno_combo_store = new Ext.data.JsonStore({
	id : 'fpage_yesno_combo_store',
	fields:['id','value'],
	data : [{'id':'0','value':'否'},{'id':'1','value':'是'}]
});
var fpage_yesno_combo = new Ext.form.ComboBox({
	id : 'fpage_yesno_combo',
	store : fpage_yesno_combo_store,
	displayField : 'value',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});

var sm = new Ext.grid.RowSelectionModel({singleSelect:false});
var bbar =  new Ext.PagingToolbar({pageSize:50,store:fpageevent_store,displayInfo:true,
	listeners : {'beforechange':function(page,o){
						o['fpageEvent.createdDate'] = document.fpageSearchForm.createdDate.value;
						o['fpageEvent.publicationId'] = document.fpageSearchForm.publicationId.value;
						o['fpageEvent.title'] = document.fpageSearchForm.title.value;
						o['fpageEvent.sortName'] = document.fpageSearchForm.sortName.value;
						o['fpageEvent.issueId'] = document.fpageSearchForm.issueId.value;
						o['fpageEvent.description'] = document.fpageSearchForm.description.value;
						
						o['fpageEvent.status'] = document.fpageSearchForm.status.value;
						o['fpageEvent.isRecommend'] = document.fpageSearchForm.isRecommend.value;
						o['fpageEvent.reserved1'] = document.fpageSearchForm.level.value;
						o['fpageEvent.isPubCover'] = document.fpageSearchForm.isPubCover.value;
						o['fpageEvent.isSuitMobile'] = document.fpageSearchForm.isSuitMobile.value;
						o['fpageEvent.reserved2'] = document.fpageSearchForm.eventtype.value;
					}
				}
});
var tbar=[{xtype:'buttongroup',items:[{id:'fpageevent_add',text:'添加'},{id:'fpageevent_delete',text:'删除'},{id:'fpageevent_commit',text:'提交'},{id:'fpageevent_valid',text:'生效'},{id:'fpageevent_invalid',text:'失效'},{id:'fpageevent_preview',text:'预览'},{id:'fpageevent_publish',text:'发布'}]}, 
{
	xtype:'buttongroup',
	items : [{
		xtype:'form',
		fileUpload : true,
		method:'POST',
		enctype:'multipart/form-data',
		width : 200,
		height : 25,
		id : 'upload-form-fpageevent',
		layout:'column',
		items:[{xtype:'textfield',inputType:'file',name : 'img',id:'fimg_fpageevent'}]
	},{
		text:'上传图片',
		handler : function(e){
			var records = fpageevent_grid.getSelectionModel().getSelections();
			if(records.length == 0){
				Ext.MessageBox.alert("提示","必须选择一个");
				return;
			} else if(records.length > 1){
				Ext.MessageBox.alert("提示","只能选择一个");
				return;
			}
			if (Ext.getCmp("fimg_fpageevent").getRawValue() == "") {
				Ext.MessageBox.alert("提示","请选择上传文件");
				return;
			}
			var record = records[0];
			Ext.getCmp('upload-form-fpageevent').getForm().submit({
                    url: 'admin/f-page-event!upload.action',
                    params : {'uploadFileId':records[0].get('id')},
                    waitMsg: 'Uploading your image...',
                    success: function(form, action){
                    	if(action.result.success=='true'){
	                    	record.set('imgFile', action.result.newImgFileName);
	                    	record.set('width', action.result.width);
	                    	record.set('height', action.result.height);
                    	}
                    	else{
                    		Ext.MessageBox.alert("提示",action.result.msg);
                    	}
                    	//fpageevent_grid.getStore().reload();
                        //msg('Success', 'Processed file "'+o.result.file+'" on the server');
                    },
                 failure : function(form, action)
                 {
                 	Ext.MessageBox.alert("提示","出错了！");
                 }
            });
		}
	}]
},
'<form name=fpageSearchForm method=post><table>' +
'<tr>' +
'<td>创建日期</td><td><input type=text name=createdDate size=4 maxlength=8></td>' +
'<td>杂志ID</td><td><input type=text name=publicationId size=2></td>' +
'<td>标题</td><td><input type=text name=title size=4></td>' +
//'<td>开始页码</td><td><input type=text name=pageNo size=1></td>' +
'<td>状态</td>' +
'<td>' +
'<select name=status>' +
'<option value="" selected>全部</option>' +
'<option value=0>无效</option>' +
'<option value=1>生效</option>' +
'<option value=2>待发布</option></select>' +
'</td>' +
'<td>是否推荐</td>' +
'<td>' +
'<select name=isRecommend>' +
'<option value="" selected>全部</option>' +
'<option value=0>否</option>' +
'<option value=1>是</option>' +
'</select></td>' +
//'<td>图片宽度</td><td><input type=text name=width size=2 maxlength=3></td>' +
'<td>出版商类型</td>' +
'<td>' +
'<select name=level>' +
'<option value="" selected>全部</option>' +
'<option value=1>一线</option>' +
'<option value=0>非一线</option>' +
'</select></td>' +
'</tr>' +
'<tr>' +
'<td>分类名</td><td><input type=text name=sortName size=4></td>' +
'<td>期刊ID</td><td><input type=text name=issueId size=2></td>' +
'<td>描述</td><td><input type=text name=description size=4></td>' +
//'<td>结束页码</td><td><input type=text name=endPageNo size=1></td>' +
'<td>是否封面</td>' +
'<td><select name=isPubCover><option value="" selected>全部</option><option value=0>否</option><option value=1>是</option></select></td>' +
'<td>适合手机</td>' +
'<td><select name=isSuitMobile><option value="" selected>全部</option><option value=0>否</option><option value=1>是</option></select></td>' +
//'<td>图片高度</td><td><input type=text name=height size=2 maxlength=3></td>' +
'<td>事件类型</td>' +
'<td>' +
'<select name=eventtype>' +
'<option value="" selected>全部</option>' +
'<option value=0>普通</option>' +
'<option value=1>广告</option>' +
'</select></td>' +
'</tr>' +
'</table>' +
'</form>',
'-',{id:'fpageevent_search',text:'查询'}];

var viewConfig = {forceFit:true,scrollToRecord:function(record){var index=fpageevent_grid.getStore().indexOf(record);this.focusRow(index);}};
var tf_noallow_blank=new Ext.form.TextField( {allowBlank : false});
var tf_allow_blank=new Ext.form.TextField( {allowBlank : true});
var col_id={header:"事件ID",dataIndex:"id",width:20};
var col_weight={header:"权重",dataIndex:"weight",width:20,editor:tf_noallow_blank};
var col_level={header:"出版商级别",dataIndex:"reserved1",width:20,
				renderer:function(value,cellmeta,record){
							if(value==0){return '非一线';}else if(value==1){return '一线';}else{return '';}
					}
				};
var col_title={header:"标题",dataIndex:"title",width:50,editor:tf_noallow_blank};
var col_description={header:"描述",dataIndex:"description",width:50,editor:tf_noallow_blank};
var col_publicationId={header:"杂志ID",dataIndex:"publicationId",width:20,editor:tf_noallow_blank};
var col_issueId={header:"期刊ID",dataIndex:"issueId",width:20,editor:tf_noallow_blank};
var col_pageNo={header:"开始页码",dataIndex:"pageNo",width:25,editor:tf_noallow_blank};
var col_endPageNo={header:"结束页码",dataIndex:"endPageNo",width:25,editor:tf_noallow_blank};
var col_imgFile={header:"图片",dataIndex:"imgFile",width:23};
var col_width={header:"图片宽度",dataIndex:"width",width:23};
var col_height={header:"图片高度",dataIndex:"height",width:23};
var col_status={header:"状态",dataIndex:"status",width:25,editor:fpage_status_combo,renderer:function(value,cellmeta,record){if(value==0){return '无效';}else if(value==1){return '生效';}else if(value==2){return '待发布';}}};
var col_isPubCover={header:"封面",dataIndex:"isPubCover",width:17,editor:fpage_yesno_combo,renderer:function(value,cellmeta,record){if(value==0){return '否';}else if(value==1){return '是';}}};
var col_isRecommend={header:"推荐",dataIndex:"isRecommend",width:17,editor:fpage_yesno_combo,renderer:function(value,cellmeta,record){if(value==0){return '否';}else if(value==1){return '是';}}};
var col_isSuitMobile={header:"手机",dataIndex:"isSuitMobile",width:17,editor:fpage_yesno_combo,renderer:function(value,cellmeta,record){if(value==0){return '否';}else if(value==1){return '是';}}};
var col_createdTime={header:"创建时间",dataIndex:"createdTime",width:23};
var col_eventtype={header:"事件类型",dataIndex:"adId",width:23,
		renderer:function(value,cellmeta,record){
					if(value){return '广告';}else{return '普通';}
				}
	};
var col_adstatus={header:"广告状态",dataIndex:"advertise.status",width:23,
		renderer:function(value,cellmeta,record){
					var adid = record.get('advertise.id');
					if (adid) {
						var start = record.get('advertise.startTime');
						var end = record.get('advertise.endTime');
						if (!end){
							return '无效';
						}
						var endtmp = end.replace(/\-/gi,"/");
						var time = new Date(endtmp).getTime();
						var date = new Date().getTime();
						if (value == 5 && date <= time) {
							return '有效';
						}else{
							return '无效';
						}
					}else{
						return '';
					}
				}
	};
//var col_updatedTime={header:"更新时间",dataIndex:"updatedTime",width:45};


var fpageevent_grid = new Ext.grid.EditorGridPanel({
	id : 'fpageevent_grid',
	frame : false,
	//width : '100%',
	closable : true,
	//height : '100%',
	autoScroll : true,
	title : '首页管理-事件管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [col_id,col_weight,col_level,col_title,col_description,col_publicationId,col_issueId,col_pageNo,col_endPageNo,col_imgFile,col_width,col_height,col_status,col_isPubCover,col_isRecommend,col_isSuitMobile,col_createdTime,col_eventtype,col_adstatus]
	}),
	region : 'center',
	store : fpageevent_store,
	viewConfig : viewConfig,
	sm : sm,
	bbar : bbar,
	tbar : tbar
});

//tab关闭事件
fpageevent_grid.on('beforeclose', beforeclose);

fpageevent_grid.getSelectionModel().on('rowdeselect', function(sm, row, record) {
	fpageevent_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpageevent_grid.getStore().remove(record);
			fpageevent_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_publicationid = record.get('publicationId');
			var ret_issueid = record.get('issueId');
			var ret_pageno = record.get('pageNo');
			var ret_endpageno = record.get('endPageNo');
			var ret_imgfile = record.get('imgFile');
			var ret_width = record.get('width');
			var ret_height = record.get('height');

			//结束页面如果为空则默认为开始页码
			if(ret_pageno && ret_pageno != 0 && !ret_endpageno){
				record.set("endPageNo", ret_pageno);
				ret_endpageno = ret_pageno;
			}
			
			if (!ret_publicationid || !ret_issueid || !ret_pageno || !ret_endpageno || !ret_imgfile || !ret_width || !ret_height) {
				//fpageevent_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpageevent_grid.getStore().getModifiedRecords().remove(record);
			}
			
		}
		added = false;
	}
});


//查询按钮
var search_btn = Ext.getCmp('fpageevent_search');
search_btn.on('click', function(e) {
	var createdDate = document.fpageSearchForm.createdDate.value;
	var title = document.fpageSearchForm.title.value;
	var description = document.fpageSearchForm.description.value;
	//var width = document.fpageSearchForm.width.value;
	//var height = document.fpageSearchForm.height.value;
	var publicationId = document.fpageSearchForm.publicationId.value;
	var issueId = document.fpageSearchForm.issueId.value;
	//var pageNo = document.fpageSearchForm.pageNo.value;
	//var endPageNo = document.fpageSearchForm.endPageNo.value;
	var sortName = document.fpageSearchForm.sortName.value;
	var status = document.fpageSearchForm.status.value;
	var isRecommend = document.fpageSearchForm.isRecommend.value;
	var isPubCover = document.fpageSearchForm.isPubCover.value;
	var isSuitMobile = document.fpageSearchForm.isSuitMobile.value;
	var level = document.fpageSearchForm.level.value;
	var eventtype = document.fpageSearchForm.eventtype.value;
	
	fpageevent_store.reload({params:{'page.start':0,'page.limit':50,'fpageEvent.createdDate' : createdDate,
							   'fpageEvent.title' : title,'fpageEvent.description' : description,
							//   'fpageEvent.width' : width,'fpageEvent.height' : height,
							   'fpageEvent.publicationId' : publicationId,'fpageEvent.issueId' : issueId,
							//   'fpageEvent.pageNo' : pageNo,'fpageEvent.endPageNo' : endPageNo,
							   'fpageEvent.sortName' : sortName,
							   'fpageEvent.status' : status,'fpageEvent.isRecommend' : isRecommend,'fpageEvent.isPubCover' : isPubCover,
							   'fpageEvent.isSuitMobile' : isSuitMobile,'fpageEvent.reserved1':level,
							   'fpageEvent.reserved2' : eventtype}});
});

var add_btn = Ext.getCmp('fpageevent_add');
add_btn.on('click', function(e) {
	if (added) {
		var sm = fpageevent_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageevent_grid.getStore().indexOf(record);
		fpageevent_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = fpageevent_grid.getStore().recordType;
	var p = new Plant( {
		id: "", 
		weight: 0, 
		publicationId : "",
		issueId : "",
		pageNo : "",
		imgFile : "",
		status : 2,
		isPubCover : 0,
		isRecommend : 0,
		isSuitMobile : 1
	});
	fpageevent_grid.stopEditing();
	fpageevent_grid.getStore().add(p);
	fpageevent_grid.getSelectionModel().clearSelections();
	fpageevent_grid.getSelectionModel().selectLastRow();
	fpageevent_grid.getView().scrollToRecord(p);
	added = true;
});

var del_btn = Ext.getCmp('fpageevent_delete');
del_btn.on('click', function(e) {
	var records = fpageevent_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	} else {
		var ret;
		Ext.MessageBox.show( {
			title : '删除',
			msg : '确定要删除？删除后不能恢复！',
			buttons : Ext.MessageBox.YESNO,
			fn : function(btn) {
				if (btn == 'yes') {
					var url = 'admin/f-page-event!delete.action';
					GridUtil.deleteSelected(records, url, fpageevent_grid);
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn = Ext.getCmp('fpageevent_commit');
commit_btn.on('click', function(e) {
	if (added) {
		var sm = fpageevent_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = fpageevent_grid.getStore().indexOf(record);
		fpageevent_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var records = fpageevent_grid.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}
	var arr = new Array();
	for ( var i = 0; i < records.length; i++) {
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request( {
		url : 'admin/f-page-event!commit.action',
		params : {
			'info' : info
		},
		success : function(e) {
			fpageevent_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var valid_btn = Ext.getCmp('fpageevent_valid');
valid_btn.on('click', function(e) {
	var records = fpageevent_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-event!valid.action',
		params : {
			'ids' : ids
		},
		success : function(e) {
			fpageevent_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});


var invalid_btn = Ext.getCmp('fpageevent_invalid');
invalid_btn.on('click', function(e) {
	var records = fpageevent_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var ids = '';
	for ( var i = 0; i < records.length; i++) {
		ids += records[i].data.id + ',';
		//arr.push(records[i].data.id);
	}
	Ext.Ajax.request( {
		url : 'admin/f-page-event!invalid.action',
		params : {
			'ids' : ids
		},
		success : function(e) {
			fpageevent_grid.getStore().reload();
		},
		failure : function(e) {
		}
	});
});

var preview_btn = Ext.getCmp('fpageevent_preview');
preview_btn.on('click', function(e) {
	window.open('http://www.magme.com/index!preview.action?timestamp='+((new Date).getTime()));
});

var publish_btn = Ext.getCmp('fpageevent_publish');
publish_btn.on('click', function(e) {
	window.open('http://www.magme.com/index!publish.action?timestamp='+((new Date).getTime()));
});