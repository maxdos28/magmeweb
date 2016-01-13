var grid2= new Ext.grid.GridPanel({
    store: new Ext.data.Store({
        dataUrl:'',
    }),
    colModel : new Ext.grid.ColumnModel({
	id : 'model',
	columns: [
    { header: "Ticker", width: 60, sortable: true},
    { header: "Company Name", width: 150, sortable: true},
    { header: "Market Cap.", width: 100, sortable: true},
    { header: "$ Sales", width: 100, sortable: true},
    { header: "Employees", width: 100, sortable: true, resizable: false}
 ]}),
    /*columns: [
        {id:'company', header: "Company", width: 200, sortable: true, dataIndex: 'company',
        	editor : new Ext.form.TextField({
			allowBlank : false
		})},
        {header: "Price", width: 120, sortable: true, renderer: Ext.util.Format.usMoney, dataIndex: 'price'},
        {header: "Change", width: 120, sortable: true, dataIndex: 'change'},
        {header: "% Change", width: 120, sortable: true, dataIndex: 'pctChange'},
        {header: "Last Updated", width: 135, sortable: true, renderer: Ext.util.Format.dateRenderer('m/d/Y'), dataIndex: 'lastChange'}
    ],*/
    viewConfig: {
        forceFit: true,
    },
    sm: new Ext.grid.RowSelectionModel({singleSelect:true}),
    region : 'center',
    width:600,
    height:300,
    frame:true,
    title:'Framed with Checkbox Selection and Horizontal Scrolling',
    iconCls:'icon-grid',
    bbar : new Ext.PagingToolbar( {
		pageSize : 20,
		store : store,
		displayInfo : true
	})
});

var colModel = new Ext.grid.ColumnModel({
	id : 'model',
	columns: [
    { header: "Ticker", width: 60, sortable: true},
    { header: "Company Name", width: 150, sortable: true},
    { header: "Market Cap.", width: 100, sortable: true},
    { header: "$ Sales", width: 100, sortable: true},
    { header: "Employees", width: 100, sortable: true, resizable: false}
 ]});
