var category_store = new Ext.data.JsonStore( {
	url : 'admin/category!page.action',
	root : 'data',
	totalProperty : 'total',
	idProperty : 'id',
	fields : [ 'id', 'createdTime','updatedTime','name', 'description','parentId'],
	pruneModifiedRecords : true,
	paramNames : {
		start : 'page.start',
		limit : 'page.limit'
	}
});
var category_parent_combo_store = new Ext.data.JsonStore({
	id : 'category_parent_combo_store',
	url : 'admin/category!parent.action',
	fields : ['id','name']
	//data : [{'value':'汽车旅行'},{'value':'时尚生活'},{'value':'商业财经'},{'value':'科技数码'},{'value':'游戏动漫'},{'value':'新闻社会'},{'value':'娱乐休闲'},{'value':'运动健康'}]
});
//category_parent_combo_store.load();
var category_parent_combo = new Ext.form.ComboBox({
	id : 'category_parent_combo',
	store : category_parent_combo_store,
	displayField : 'name',
	valueField : 'id',
	editable : false,
	triggerAction : 'all',
	mode : 'local'
});
category_parent_combo.on('beforequery',function(){
	var record = category_grid.getSelectionModel().getSelected();
	category_parent_combo_store.reload({params:{'id':record.get('id')}});
});
var category_grid = new Ext.grid.EditorGridPanel( {
	id : 'category_grid',
	frame : false,
	closable : true,
	//width : '100%',
	//height : '100%',
	autoScroll : true,
	title : '分类管理',
	cm : new Ext.grid.ColumnModel( {
		defaults : {
			sortable : true
		},
		columns : [ {
			header : "层级",
			dataIndex : "parentId",
			width : 130,
			renderer : function(value){
				if (value == 0){
					return "一级";
				}else{
					return "二级"
				}
			}
		}, {
			header : "名字",
			dataIndex : "name",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		}, {
			header : "描述",
			dataIndex : "description",
			width : 130,
			editor : new Ext.form.TextField( {
				allowBlank : false
			})
		},{
			header : "父级名称",
			dataIndex : "parentId",
			width : 130,
			editor : category_parent_combo,
			renderer : function(value, cellmeta, record){
				for(var i=0;i<category_parent_combo_store.data.length;i++){
					var id = category_parent_combo_store.getAt(i).get('id');
					if(id == record.get('parentId')){
						return category_parent_combo_store.getAt(i).get('name');
					}
				}
			}
		}]
	}),
	region : 'center',
	store : category_store,
	viewConfig: {
        forceFit: true,
        scrollToRecord:function(record){  
	    	var index = category_grid.getStore().indexOf(record);  
	    	this.focusRow(index);  
  		}
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:false}),
	bbar : new Ext.PagingToolbar( {
		pageSize : 50,
		store : category_store,
		displayInfo : true
	}),
	tbar : [{xtype:'buttongroup',items:[{id:'category_add',text:'添加'},{id:'category_delete',text:'删除'},{id:'category_commit',text:'提交'}]}]
});

category_grid.on('beforeclose',function(panel){
	tab.remove(panel,false);
	panel.hide();
	return false;
});

category_grid.getSelectionModel().on('rowdeselect',function(sm,row,record){
	category_grid.stopEditing(false);
	if(added){
		if(record.modified == null){
			category_grid.getStore().remove(record);
			category_grid.getStore().getModifiedRecords().remove(record);
		} else {
			var ret_title = record.isModified('name');
			var ret_description = record.isModified('description');
			if(!ret_title || !ret_description){
				//category_grid.getStore().remove(record);
				Ext.MessageBox.alert("提示","非空字段不能为空,否则数据不会被提交.");
				category_grid.getStore().remove(record);
				category_grid.getStore().getModifiedRecords().remove(record);
			}
		}		
		added = false;
	}
});

var category_add_btn = Ext.getCmp('category_add');
category_add_btn.on('click',function(e){
	if(added){
		var sm = category_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = category_grid.getStore().indexOf(record);
		category_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var Plant = category_grid.getStore().recordType;
	var p = new Plant({id:"", name:"",description:"",parentId:"0"});
	category_grid.stopEditing();
	category_grid.getStore().add(p);
	category_grid.getSelectionModel().clearSelections();
	category_grid.getSelectionModel().selectLastRow();
	category_grid.getView().scrollToRecord(p);	
	added = true;
});

var category_del_btn = Ext.getCmp('category_delete');
category_del_btn.on('click',function(e){
	if(added){
		var sm = category_grid.getSelectionModel();
		var record = sm.getSelected();
		category_grid.getStore().remove(record);
		category_grid.getStore().getModifiedRecords().remove(record);
		added = false;
	}
	var records = category_grid.getSelectionModel().getSelections();
	if(records.length <= 0){
		return;
	} else {
		var ret;
		Ext.MessageBox.show({
           title:'删除',
           msg: '确定要删除？删除后不能恢复！',
           buttons: Ext.MessageBox.YESNO,
           fn: function(btn){
				if(btn == 'yes') {
					//category_commit_btn.fireEvent('click');
					var url = 'admin/category!delete.action';
					GridUtil.deleteSelected(records,url,category_grid);
				}
           },
           animEl: 'mb4',
           icon: Ext.MessageBox.QUESTION
        });
	}
	
});

var category_commit_btn = Ext.getCmp('category_commit');
category_commit_btn.on('click',function(e){
	if(added){
		var sm = category_grid.getSelectionModel();
		var record = sm.getSelected();
		var row = category_grid.getStore().indexOf(record);
		/*var ret_name = record.isModified('name');
		if(!ret_name){
			category_grid.getStore().remove(record);
			category_grid.getStore().getModifiedRecords().remove(record);
		}
		added = false;*/
		category_grid.getSelectionModel().fireEvent('rowdeselect',sm,row,record);
	}
	var records = category_grid.getStore().getModifiedRecords();
	if(records.length <= 0){
		return;
	}
	var arr = new Array();
	for(var i = 0;i < records.length;i++){
		arr.push(records[i].data);
	}
	var info = Ext.encode(arr);
	Ext.Ajax.request({
		url : 'admin/category!commit.action',
		params :{'info' : info},
		success : function(o){
			var obj = Ext.util.JSON.decode(o.responseText);
			if(obj.reload == 'true'){
				category_parent_combo_store.reload();
			}			
			category_grid.getStore().reload();
		},
		failure : function(e){}
	});
});