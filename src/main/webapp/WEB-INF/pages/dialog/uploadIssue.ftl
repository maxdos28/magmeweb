<#macro main>
	<div id="uploadIssueDialog" class="popContent popRegister">
    	<fieldset>
    		<h6>上传期刊</h6>
        	<form method="post" id="uploadIssueForm" enctype="multipart/form-data" onsubmit="return false;">
        	<div>
            	<em class="title">杂志名称</em>
            	<em><select class="col-8" id="publicationId" name="publicationId"></select></em>
            </div>
            <div>
            	<em class="title">文件</em>
            	<em><input type="file" id="issueFile" name="issueFile" /></em>
            </div>
            <div>
            	<em class="title">期刊号</em>
                <em class="g100">
                	<select id="year" name="year" class="g70" >
	                	<option value="2010">2010</option>
	                	<option value="2011">2011</option>
	                	<option value="2012">2012</option>
	                	<option value="2013">2013</option>
	                </select>
                	&nbsp;年
                </em>
                <em class="g80">
                	<select id="month" name="month" class="g50" >
	                    <option selected="selected" value="01">01</option>
					   	<option value="02">02</option>
					   	<option value="03">03</option>
					   	<option value="04">04</option>
					   	<option value="05">05</option>
					   	<option value="06">06</option>
					   	<option value="07">07</option>
					   	<option value="08">08</option>
					   	<option value="09">09</option>
					   	<option value="10">10</option>
					   	<option value="11">11</option>
					   	<option value="12">12</option>
	                </select>
	                &nbsp;月
                </em>
                <#assign days=1..31 />
            	<em class="date">
            		<input type="text" class="g70 input" id="day" name="day"/>
					<div id="selectDateBox">
						<#list days as day>
							<a>${day}</a>
						</#list>
						<a class="text clear">增刊</a>
                        <a class="text">副刊</a>
                        <a class="text">纪念刊</a>
					</div>
                </em>
            </div>
            <div>
            	<em class="title">发布日期</em>
            	<em><input type="text" id="publishDate" name="publishDate" class="input" /></em>
            </div>
            <div>
            	<em class="title">关键词</em>
            	<em><input type="text" class="input" name="keyword" /></em><em>关键词便于用户搜索</em>
            </div>
            
            <div>
            	<em class="title">描述</em>
            	<em><textarea rows="4" class="input g400" name="description"></textarea></em>
            </div>
            <div>
            	<em class="title"><input type="hidden" name="fileName" id="fileName" value=""/></em>
                <em ><a id ="uploadIssueSubmit" href="javascript:void(0)" class="btnBS">确定</a></em>
                <em ><a id="cancel" href="javascript:void(0)" class="btnWS" >取消</a></em>
                <em id ="tipError" class="tipsError">请填写相关信息</em>
            </div>
            
            </form>
        </fieldset>
	</div>
	
	
	<script>
    	$(function(){
    		var myDate = new Date();
    		$("#year").val(myDate.getFullYear());
    		$("#month").val((myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1))
    		var removeDate = function(year){
    			var febDays = 28;
    			if(year=='2012'){
					febDays = 29;
    			}
    			$("#selectDateBox a").filter(function(i){
    				return i>(febDays-1) && i<31;
    			}).hide();
    		};
			$("#day").unbind("focus").bind('focus',function(){
				var year = $("#year").val();
				var month = $("#month").val();
				$("#selectDateBox a").show();
				switch(month){
					case '04':
					case '06':
					case '09':
					case '11':
						$("#selectDateBox a").eq(30).hide();
						break;
					case '02':
						removeDate(year);
						break;
				}
				$("#selectDateBox").slideDown(400);
			});
			$("#day").blur(function(){
				setTimeout(function(){$("#selectDateBox").slideUp(200)},100);
			});
			$("#selectDateBox a").unbind('click').bind('click',function(){
				$("#day").val($(this).html());
			})
		});	
    </script>
</#macro>