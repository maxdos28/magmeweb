var sort_store = new Ext.data.JsonStore( {
	url : 'admin/sort!getListJson.action',
	root : 'data.sortList',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'name','group','path'],
	pruneModifiedRecords : true
});

Ext.QuickTips.init();
var sort_grid= new Ext.grid.EditorGridPanel( {
	id : 'sort_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '展示分类',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [{
			header : "ID",
			dataIndex : "id",
			width : 50
		}, {
			header : "名称",
			dataIndex : "name",
			width : 50,
			editor:new Ext.form.TextField( {allowBlank : false,blankText : "不允许为空"})
		}, {
			header : "分组",
			dataIndex : "group",
			width : 50,
			editor:new Ext.form.TextField( {allowBlank : false,blankText : "不允许为空"})
		}, {
			header : "图片路径",
			dataIndex : "path",
			width : 100
		}]
	}),
	region : 'center',
	store : sort_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = sort_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm : new Ext.grid.RowSelectionModel({singleSelect:true}),
	tbar : [{xtype:'buttongroup',items:[
		{id:'sort_add',text:'添加'},
		{id:'sort_delete',text:'删除'},
		{id:'sort_commit',text:'提交'},
		{id:'sort_rel',text:'关联原始分类'}]},
		{
			xtype:'form',
			fileUpload : true,
			method:'POST',
			enctype:'multipart/form-data',
			width : 200,
			height : 25,
			id : 'upload-form_event',
			layout:'column',
			items:[{xtype:'textfield',inputType:'file',name : 'img',id:'himg_event'}]
		},
		{
		text:'上传图片',
		handler : function(e){
			var records = sort_grid.getSelectionModel().getSelections();
			if(records.length == 0){
				Ext.MessageBox.alert("提示","必须选择一个");
				return;
			} else if(records.length > 1){
				Ext.MessageBox.alert("提示","只能选择一个");
				return;
			}
			if (Ext.getCmp("himg_event").getRawValue() == "") {
				Ext.MessageBox.alert("提示","请选择上传文件");
				return;
			}
			var record = records[0];
			Ext.getCmp('upload-form_event').getForm().submit({
                    url: 'admin/sort!uploadJson.action',
                    params : {'uploadFileId':records[0].get('id')},
                    waitMsg: 'Uploading your image...',
                    success: function(form, action){
                    	record.set('path', action.result.newImgFileName);
                    },
                 failure : function(form, action)
                 {
                 	Ext.MessageBox.alert("提示","出错了！");
                 }
            });
		}}]
});
//tab关闭事件
sort_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

sort_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	sort_grid.stopEditing(false);
	if (added) {
		if (record.modified == null) {
			fpagetemplate_grid.getStore().remove(record);
			fpagetemplate_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_name = record.isModified('name');
			if (!ret_name) {
				//fpagetemplate_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示", "非空字段不能为空,否则数据不会被提交.");
				fpagetemplate_grid.getStore().getModifiedRecords().remove(record);
			}
		}
		added = false;
	}
});

var add_btn = Ext.getCmp('sort_add');
add_btn.on('click', function(e) {
	if (added) {
		var sm = sort_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = sort_grid.getStore().indexOf(record);
		sort_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
	}
	var Plant = sort_grid.getStore().recordType;
	var p = new Plant( {
		id: "", 
		name : ""
	});
	sort_grid.stopEditing();
	sort_grid.getStore().add(p);
	sort_grid.getSelectionModel().clearSelections();
	sort_grid.getSelectionModel().selectLastRow();
	sort_grid.getView().scrollToRecord(p);
	added = true;
});

var del_btn = Ext.getCmp('sort_delete');
del_btn.on('click', function(e) {
	var records = sort_grid.getSelectionModel().getSelections();
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
					Ext.Ajax.request( {
						url : 'admin/sort!deleteJson.action',
						params : records[0].data,
						success : function(e) {
							var rs=Ext.decode(e.responseText);
							if(rs.code==200){
								sort_grid.getStore().reload();
							}else{
								alert(rs.message);
								sort_grid.getStore().reload();
							}
						},
						failure : function(e) {
						}
					});
				}
			},
			animEl : 'mb4',
			icon : Ext.MessageBox.QUESTION
		});
	}

});

var commit_btn = Ext.getCmp('sort_commit');
commit_btn.on('click', function(e) {
	var url='admin/sort!updateJson.action';
	if (added) {
		var sm = sort_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = sort_grid.getStore().indexOf(record);
		sort_grid.getSelectionModel().fireEvent('rowdeselect', sm, row,
				record);
		url='admin/sort!addJson.action';
	}
	var records = sort_grid.getStore().getModifiedRecords();
	if (records.length <= 0) {
		return;
	}

	Ext.Ajax.request( {
		url : url,
		params : records[0].data,
		success : function(e) {
			var rs=Ext.decode(e.responseText);
			if(rs.code==200){
				sort_grid.getStore().reload();
			}else{
				alert(rs.message);
				sort_grid.getStore().reload();
			}
		},
		failure : function(e) {
		}
	});
});

var commit_btn = Ext.getCmp('sort_rel');
commit_btn.on('click', function(e) {
	var records = sort_grid.getSelectionModel().getSelections();
	if (records.length <= 0) {
		return;
	}
	var sortId=records[0].data.id;
	var grid1_store = new Ext.data.JsonStore( {
		url : 'admin/sort-category-rel!getListJson.action?sortId='+sortId,
		root : 'data.selectedCategoryList',
		totalProperty : 'total',
		idProperty : 'id',
		fields : [ 'id', 'name'],
		pruneModifiedRecords : true
	});
	grid1_store.load();
	var grid1= new Ext.grid.GridPanel( {
		frame : true,
		closable : true,
		columnWidth : .5,
		height:450,
		autoScroll : true,
		title : '已选中原始分类(可拖拽一行至右表格)',
		enableDragDrop   : true,
		ddGroup          : 'grid2',
		cm : new Ext.grid.ColumnModel( {
			defaults : {
				sortable : true
			},
			columns : [{
				header : "ID",
				dataIndex : "id",
				width : 50
			}, {
				header : "名称",
				dataIndex : "name",
				width : 50,
				editor:new Ext.form.TextField( {allowBlank : false,blankText : "不允许为空"})
			}]
		}),
		store : grid1_store,
		viewConfig: {forceFit: true},
	    sm : new Ext.grid.RowSelectionModel({singleSelect:true}),
		bbar : [{xtype:'buttongroup',items:[{id:'rel_save',text:'保存',
			handler:function(){
				var store=grid1.getStore();
				var len=store.getCount();
				var arr =new Array();
				for(var i=0;i<len;i++){
					arr.push(store.getAt(i).data.id);
				}
				var params={};
				params.sortId=sortId;
				if(arr.length>0){
					params.categoryIds=arr;
				}
				Ext.Ajax.request( {
					url : 'admin/sort-category-rel!relationJson.action',
					params : params,
					success : function(e) {
						var rs=Ext.decode(e.responseText);
						if(rs.code!=200){
							alert(rs.message);
						}else{
							alert("保存成功");
							win.close();
						}
						sort_grid.getStore().reload();
					},
					failure : function(e) {
					}
				});
			}}]}]
	});
	var grid2_store = new Ext.data.JsonStore( {
		url : 'admin/sort-category-rel!getListJson.action?sortId='+sortId,
		root : 'data.categoryList',
		totalProperty : 'total',
		idProperty : 'id',
		fields : [ 'id', 'name'],
		pruneModifiedRecords : true
	});
	grid2_store.load();
	var grid2= new Ext.grid.GridPanel( {
		frame : true,
		closable : true,
		columnWidth : .5,
		height:450,
		autoScroll : true,
		title : '未选中原始分类(可拖拽一行至左表格)',
		enableDragDrop   : true,
		ddGroup          : 'grid1',
		cm : new Ext.grid.ColumnModel( {
			defaults : {
				sortable : true
			},
			columns : [{
				header : "ID",
				dataIndex : "id",
				width : 50
			}, {
				header : "名称",
				dataIndex : "name",
				width : 50,
				editor:new Ext.form.TextField( {allowBlank : false,blankText : "不允许为空"})
			}]
		}),
		store : grid2_store,
		viewConfig: {forceFit: true},
	    sm : new Ext.grid.RowSelectionModel({singleSelect:true})
	});
	
	var win=new Ext.Window({
		id:"scr",
		width:500,
		height:500,
		layout:'fit',
		title:"关联原始分类",
		modal:true,
		items:new Ext.Panel({
			layout:"column",
			frame:true,
			items:[grid1,grid2]
		})
	});
	win.show();
	
    var grid1DropTargetEl =  grid1.getView().scroller.dom;
    var grid1DropTarget = new Ext.dd.DropTarget(grid1DropTargetEl, {
            ddGroup    : 'grid1',
            notifyDrop : function(ddSource, e, data){
                    var records =  ddSource.dragData.selections;
                    Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
                    grid1.store.add(records);
                    grid1.store.sort('id', 'ASC');
                    return true
            }
    });


    var grid2DropTargetEl = grid2.getView().scroller.dom;
    var grid2DropTarget = new Ext.dd.DropTarget(grid2DropTargetEl, {
            ddGroup    : 'grid2',
            notifyDrop : function(ddSource, e, data){
                    var records =  ddSource.dragData.selections;
                    Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
                    grid2.store.add(records);
                    grid2.store.sort('id', 'ASC');
                    return true
            }
    });
});

