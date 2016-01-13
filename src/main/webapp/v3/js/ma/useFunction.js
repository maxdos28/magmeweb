
function fnReadyTable(){
	var tableID=1;
	var tdID =1;
	var tableNum = $('table.table').size();
	for(tableID;tableID<=tableNum;tableID++) {
		$('table.table').get(tableID-1).lang='table'+tableID;
		var tdNum = $('table.table[lang*=table'+tableID+'] tr:first td').size();
		for(tdID; tdID <= tdNum; tdID++) {
			$('table.table tr td:nth-child('+tdID+')').addClass('t'+tdID);
		};
	};
	//"tr" add double color & "tr" add hover style--------------------------------------------//
	var $JQtableBg = $("table.JQtableBg");
	var $JQtableHover = $("table.JQtableBg tbody tr");
	$JQtableBg.each(function(){
		$(this).find("tbody tr:odd").addClass('bgColorTable');
	});
	$JQtableHover.live("mouseover",function(){
	$(this).addClass("bgTrHover");
	});
	$JQtableHover.live("mouseout",function(){
		$(this).removeClass("bgTrHover");
	});
	function addJQtableBg(element) {
		element.addClass('bgColorTable');
	};
}

$(function(){
    fnReadyTable();
})
