/*;$(function($){
	
$(".delete").live("click",function() {
	if($(this).hasClass('delete')){
		var _$this=this;
		var cre=$(_$this).parents(".theme").find(".content").find(".tools").attr("cre");
		if(confirm("删除后不能恢复，确定要删除吗？"))
	    {
			$.ajax({
				url :SystemProp.appServerUrl+"/sns/creative!del.action",
				type : "POST",
				data : {"cid":cre},
				dataType : 'json',
				success: function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						$(_$this).parents(".theme").remove();
						}else{
							alert(rs.mess);
						}
					}
				});
		    }
		}
	});
});*/
