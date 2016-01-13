<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/iosApp.js"></script>
<#import "../dialog/viewApp.ftl" as ap>
<!--body-->
   <div class="conLeftMiddleRight" menu="iosManageAdmin" label="appNew" >
        <div class="con36 conTools">
        	<fieldset>
            	<div>
                	<em class="g50">版权信息</em>
                    <em class="g240"><input name="information" type="text" maxlength="100" value="上海居冠软件有限公司" class="input g200" /></em>
                	<em class="g50">应用名称</em>
                    <em class="g240"><input name="name" type="text" maxlength="5" class="input g200" /></em>
                	<em class="g40">关键字</em>
                    <em><input type="text" name="appKeyword" maxlength="300" class="input g200" /></em>
                </div>
                <hr />
            	<div>
                	<em class="g50">一级分类</em>
                    <em class="g240"><select name="firstType">
                    <option value="Books">书籍</option></select></em>
                	<em class="g50">二级分类</em>
                    <em><select name="secondType">
                    <option value="Business">Business</option>          
					<option value="Catalogs">Catalogs</option> 
					<option value="Education">Education</option>          
					<option value="Entertainment">Entertainment</option>    
					<option value="Finace">Finace</option>			  
					<option value="Food Drink">Food&Drink</option>		  
					<option value="Games">Games</option>			 
					<option value="Health Fitness">Health & Fitness</option> 
					<option value="Lifestyle">Lifestyle</option>		  
					<option value="Medical">Medical</option>		  
					<option value="Music">Music</option>			  
					<option value="Navigation">Navigation</option>		  
					<option value="News">News</option>			  
					<option value="Photo Video">Photo & Video</option>	  
					<option value="Productivity">Productivity</option>
					<option value="Reference">Reference</option>
					<option value="Social Networking">Social Networking</option>
					<option value="Sports">Sports</option>
					<option value="Travel">Travel</option>
					<option value="Utilities">Utilities</option>
					<option value="Weather">Weather</option>
                    
                    </select></em>
                </div>
                <hr />
            	<div>
                	<em class="g50">所有者</em>
                    <em class="g80"><label><input name="userType" value="0" checked  type="radio" />magme</label></em>
                    <em class="g160"><label><input name="userType" value="1" type="radio" />出版商</label></em>
                    <em class="g50">描述</em>
                    <em><input type="text" name="description" maxlength="400" class="input g360" /></em>
                </div>
                <hr />
				<div class="list selectList">
                	<div><strong>已选择的杂志社<a class="javascript:vhod(0)" id="publisherSelect">点击选择杂志社</a></strong>
                    	<p><a class="current">暂无</a></p>
                    </div>
                </div>
				<div class="list">
						
						<#list ['A', 'B', 'C', 'D', 'E', 'F', 'G','H', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z'] as iNum>
							<#if publisherList??>
		                    	<div><strong>${iNum}</strong>
		                    	<p>
	                    		<#list publisherList as publisher>
	                    			<#if publisher.webSite?? &&publisher.webSite==iNum>
	                    				<a publsherId="${publisher.id}">${publisher.publishName}</a>
	                    			</#if>
	                    		</#list>
	                    		</p>
	                   			 </div>
                    		</#if>
						</#list>
							<#if publisherList??>
		                    	<div><strong>其他</strong>
		                    	<p>
	                    		<#list publisherList as publisher>
	                    			<#if publisher.webSite?? &&(publisher.webSite=='0' || publisher.webSite=='1' || publisher.webSite=='2' || publisher.webSite=='3' || publisher.webSite=='4' || publisher.webSite=='5' || publisher.webSite=='6' || publisher.webSite=='7') || publisher.webSite=='8' || publisher.webSite=='9'>
	                    				<a publsherId="${publisher.id}">${publisher.publishName}</a>
	                    			</#if>
	                    		</#list>
	                    		</p>
	                   			 </div>
                    		</#if>
                </div>
                <div class="actionArea tRight">
                	<a href="javascript:void(0)" id="publisherOk" class="btnAS">确认</a>
                </div>
                <script>
                	$(function(){
						//杂志社选择
						$(".con36 .list:eq(1) div a").click(function(){
							if($(this).hasClass("current")){
								$(this).removeClass("current");
							}else{
								$(this).addClass("current");
							}
							var tempHtml="";
							var i=0;
							
							var selectUserType = $("input[name='userType']:checked").val();
							if(selectUserType==0){
								$(".con36 .list:eq(1) div a.current").each(function(i){
									tempHtml=tempHtml+"<a class='selected' publsherId='"+$(this).attr("publsherId")+"'>"+$(this).html()+"</a>";
								});
							}else{
								var obj = $(this);
								$(".con36 .list div a.current").removeClass();
								tempHtml="<a class='selected' publsherId='"+$(this).attr("publsherId")+"'>"+$(this).html()+"</a>";
								obj.addClass("current");
							}
							$(".con36 .selectList div p").html(tempHtml);
							if($(".con36 .selectList div p").html()==""){
								$(".con36 .selectList div p").html("<a class='current'>暂无</a>");
							}
						});
						$(".con36 .list div strong a").toggle(function(){
							$(".con36 .list:eq(1)").slideDown(300);
						},function(){
							$(".con36 .list:eq(1)").slideUp(300);
						});
					});
                </script>
            </fieldset>
        </div>
        <div class="conB con31">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g50"><label><input id="publicationAll" type="checkbox" />全选</label></td>
                    <td class="g300">杂志社名</td>
                    <td>杂志名</td>
                    <td class="g100">级别</td>
                  </tr>
                </thead>
                <tbody id="publicationTbody">
                    <tr>
                        <td colspan=4>无记录</td>
                    </tr>
                   
                </tbody>
            </table>
        </div>
        <div class="conFooter tCenter">
            <a href="javascript:void(0)" class="btnBB" id="addApp" >加入APP</a>
            <a href="javascript:void(0)" class="btnSB">取消</a>
		</div>
</div>
<@ap.main class />