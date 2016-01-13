;$(document).ready(function(){
	//window.location.href
	//滚动加载
	var next=true;
	$Dashboard = $("#Dashboard");
	$("#loading").hide();
	
	var loadMore = function(){
		$(".clickLoadMore").hide();
		$("#loading").show();
		if(next){
			var path = window.location+"";
			var begin = $Dashboard.find("div.theme").length;
			
			var isq=$("#sq_sns_tag").val();
			if(begin%5==0 && begin>0){
				if(isq==0){
					$Dashboard = $("#Dashboard");
					var path = SystemProp.appServerUrl+"/sns/search.action";
					var key=$("#sns_queryStr").val();
					var t="sns";
					var data = {"begin":begin,"searchType":t,"queryStr":key};
					$.ajax({
						url : path,
						type : "post",
						async : false,
						data : data,
						success : function (rs){
							$Dashboard.append(rs);
							$("#loading").hide();
						}
					});
					if($Dashboard.find("div.theme").length%5==0)
						$(".clickLoadMore").show();
					else
						$(".clickLoadMore").hide();
				}else{
					var data = {"begin":begin};
					$.ajax({
						url : path,
						type : "post",
						async : false,
						data : data,
						success : function (rs){
							if(rs.indexOf("userHead")<0 && rs.indexOf("calendar")<0){
								$("#loading").hide();
							}
							$("#loading").hide();
							
							$Dashboard.append(rs);
						}
					});
					if($Dashboard.find("div.theme").length%5==0)
						$(".clickLoadMore").show();
				}
			}else{
				next=false;
				$("#loading").hide();
				$(".clickLoadMore").hide();
			}
		}
	};
	
		
	$(".clickLoadMore").live("click",loadMore);
	
	
	var begin = $Dashboard.find("div.theme").length;
	if(begin%5==0 && begin>0){
		$(".clickLoadMore").show();
	}else{
		$(".clickLoadMore").hide();
	}
});