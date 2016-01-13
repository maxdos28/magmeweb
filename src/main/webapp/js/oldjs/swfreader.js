$(document).ready(function(){
	$.jquerytagbox("#issueStatic",0);
});

	function getFlashMovieObject(num) {
		if (navigator.appName.indexOf("Microsoft Internet") == -1) {
			
			if (document.embeds && document.embeds["StatisticsTool" + (num + 1)])
				return document.embeds["StatisticsTool" + (num + 1)];
			//return $('#embed[StatisticsTool]')
		} else // if (navigator.appName.indexOf("Microsoft Internet")!=-1)
		{
			return document.getElementById("StatisticsTool" + num);
		}
	}

	function callAs(num,issueId,publicationName,publishDate) {
		var obj = getFlashMovieObject(num);
		if(num == 1){
			obj.statisticsToolInit(issueId,publicationName,publishDate,"http://www.magme.com/publish/flow-show!dataI.action","1");
		}else if(num==3){
			obj.statisticsToolInit(issueId,publicationName,publishDate,"http://www.magme.com/publish/flow-show!dataIICount.action","2");
		}else if(num == 5){
			obj.statisticsToolInit(issueId,publicationName,publishDate,"http://www.magme.com/publish/flow-show!dataIITime.action","3");
		}
	}

	function dataShow(){
		var issueId = $('#issueId').val();
		var publicationName = $('#publicationName').val();
		var publishDate = $('#publishDate').val();
		var id = $(".ctrl .current").attr("id");
		if (id ==="ftab")
		{
			callAs(1,issueId,publicationName,publishDate);
		}else if (id ==="stab")
		{
			callAs(3,issueId,publicationName,publishDate);	
		}
		else{
			callAs(5,issueId,publicationName,publishDate);
		}
	}