<script type="text/javascript" src="${systemProp.staticServerUrl}/widget/js/jquery.showTagArea.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/widget/js/showTag.js"></script>



<!--body-->
<div class="body pageWidget clearFix" style="padding-bottom:0; margin-bottom:0; margin-top:20px;">
	<div>
		<input id="tagId" type="hidden" value="${tag.id}"/>
		<input id="tagWidth" type="hidden" value="${tag.width}"/>
		<input id="tagHeight" type="hidden" value="${tag.height}"/>
		<input id="tagLeft" type="hidden" value="${tag.x}"/>
		<input id="tagTop" type="hidden" value="${tag.y}"/>
		<input id="imgUrl" type="hidden" value="${systemProp.magServerUrl+"/"+tag.publicationId+"/"+tag.issueId+"/b"+tag.pageNo+".jpg"}"/>
		<input id="tagUrl" type="hidden" value="${systemProp.tagServerUrl+tag.path}"/>
	</div>

	<div class="outer">
        <!--sideLeft-->
        <div class="side236">
            <div class="conC conUserList">
                <h2 class="clearFix">
        		<a href="#" target="_blank">
        		<#if (tag.user.avatar)??>
        			<img src="${systemProp.profileServerUrl+tag.user.avatar30}" />
        		<#else>
        			<img src="${systemProp.staticServerUrl}/widget/images/head30.gif" />
        		</#if>
        		<strong>${(tag.user.nickName)!"用户名"}</strong>
        		</a>
        		<span>共有${(tag.user.statsMap.totalTagNum)!"0"}个标签</span>
                </h2>
                <div class="conBody">
                    <div id="tagList" begin="0" size="9" userId="${tag.user.id}" class="inner clearFix">
            		<#if tagList??>
            		<#list tagList as tag1>
            			<a href="/widget/widget!showTag.action?id=${tag1.id}"><img src="${systemProp.tagServerUrl+tag1.path68}" title="${tag1.description}"/></a>
            		</#list>
            		</#if>
                </div>
                <div class="control">
                	<a id="turnR" class="turn right <#if (tagList?size<9)>stop</#if>" href="javascript:void(0)"></a>
                    <a id="turnL" class="turn left stop" href="javascript:void(0)"></a>
                </div>
                </div>
            </div>
        </div>
        

        <!--sideMiddle-->
        <div class="side484" style="margin-bottom:0;">
            <div class="conC conBigMgzShow bigMgzShow1" id="conBigMgzShow">
                <a class="btnToggle" href="javascript:void(0)"></a>
                <img bigimg="bigImg" newopen="${systemProp.domain+"/publish/mag-read.action?widget=1&id="+tag.issueId+"&pageId="+tag.pageNo}" src="${systemProp.magServerUrl+"/"+tag.publicationId+"/"+tag.issueId+"/b"+tag.pageNo+".jpg"}"/>
                <div class="mask" title="点击阅读杂志" ></div>
                <div class="showArea" title="点击阅读杂志"><img src="${systemProp.tagServerUrl+tag.path}" /></div>
                <p>${tag.description}</p>
                <div class="info clearFix">
                   <#if widget_auth?? && widget_auth.type!='tencent'>
                   <a id="share" class="iconShare" href="javascript:void(0)">分享</a>
                   </#if>
                    <span>创建于${tag.createdTime?string("yyyy-MM-dd")}</span>
                </div>
            </div>
            
            
        </div>
     
               
            
        </div>
	</div>

<script type="text/javascript">
$(function(){
	$("img[newopen]").live('click',function(){
		var newopen = $(this).attr("newopen");
		window.location.href=newopen;
	});

	<#if widget_auth??&&widget_auth.type=='renren'>
	
	$("#share").live('click',function(){
		showDialog('http://widget.renren.com/dialog/feed?display=iframe&app_id=${widget_auth.apiID}&action_name=%e6%9f%a5%e7%9c%8b%e6%a0%87%e7%ad%be&action_link=http%3a%2f%2fapps.renren.com%2fmagzine%2fshowTag.html%3fid%3d${tag.id}&url=http%3a%2f%2fapps.renren.com%2fmagzine%2fshowTag.html%3fid%3d${tag.id}&name=%e9%98%85%e8%af%bb%e6%9d%82%e5%bf%97%e3%80%8a${tag.issue.publicationName}%e3%80%8b&description=%e6%88%91%e6%ad%a3%e5%9c%a8%e7%9c%8b%e8%bf%99%e6%9c%ac%e6%9d%82%e5%bf%97%e3%80%8a${tag.issue.publicationName}%e3%80%8b%ef%bc%8c%e8%a7%89%e5%be%97%e4%b8%8d%e9%94%99%ef%bc%8c%e6%8e%a8%e8%8d%90%e4%bd%a0%e4%b9%9f%e7%9c%8b%e4%b8%80%e4%b8%8b%ef%bc%8c%e6%88%91%e4%bb%ac%e6%8f%90%e4%be%9b%e7%b2%be%e7%be%8e%e7%9a%84%e7%94%b5%e5%ad%90%e6%9d%82%e5%bf%97%ef%bc%8c%e5%8c%85%e5%90%ab%e6%95%b0%e7%a0%81%e6%9d%82%e5%bf%97%ef%bc%8c%e6%97%b6%e5%b0%9a%e6%9d%82%e5%bf%97%ef%bc%8c%e6%b1%bd%e8%bd%a6%e6%9d%82%e5%bf%97%ef%bc%8c%e8%b4%a2%e7%bb%8f%e6%9d%82%e5%bf%97%e5%8f%af%e4%bb%a5%e5%85%8d%e8%b4%b9%e5%9c%a8%e7%ba%bf%e7%9c%8b%e3%80%82&image=${systemProp.magServerUrl}%2f${tag.publicationId}%2f${tag.issueId}%2fb${tag.pageNo}.jpg','分享');
		return false;
	});	
	
	<#elseif widget_auth??&&widget_auth.type=='kaixin'&&widget_auth.sessionKey??>
	$("#share").live('click',function(){
		var date=new Date(); 
		var call_id=Date.UTC(date.getYear(),date.getMonth()+1,date.getDay(),date.getHours(),date.getMinutes(),date.getSeconds(),date.getMilliseconds()); 
		
		var para_api_key="api_key=${widget_auth.apiKey}";
		var para_call_id="call_id="+call_id;
		var para_link="link=http://www.kaixin001.com/!app_magzine/?id=${tag.id}";
		var para_linktext="linktext=";
		var para_method="method=actions.sendNewsFeed";
		var para_pic="pic=${systemProp.magServerUrl+"/"+tag.publicationId+"/"+tag.issueId+"/"+tag.pageNo+".jpg"}";
		var para_session_key="session_key=${widget_auth.sessionKey}";
		var para_text="text=";
		var para_v="v=1.2";
		
		//console.log(para_api_key+para_call_id+para_link+para_linktext+para_method+para_pic+para_session_key+para_text+para_v+"${widget_auth.secretKey}");
		var sig = hex_md5(para_api_key+para_call_id+para_link+para_linktext+para_method+para_pic+para_session_key+para_text+para_v+"${widget_auth.secretKey}");
		var para_sig="sig="+sig;
	
		var url=para_api_key+"&"+para_call_id+"&"+para_link+"&"+para_linktext+"&"+para_method+"&"+para_pic+"&"+para_session_key+"&"+para_text+"&"+para_v+"&"+para_sig;
		showKxDialog(base64AndRep(url));

	    return false;
	});
	
	<#elseif widget_auth??&&widget_auth.type=='sina'&&widget_auth.sinaUid??>
	$("#share").live('click',function(){
		WB2.anyWhere(function(W){
		    // 获取评论列表
		W.parseCMD("/statuses/update.json", function(sResult, bStatus){
		    if(bStatus == true) {
				alert("分享成功");
		    }
		    else{
		    	alert("您已经分享过！");
		    }
		},{
			status : encodeURI('我正在看这本杂志《${tag.issue.publicationName}》，觉得不错，推荐你也看一下，我们提供精美的电子杂志，包含数码杂志，时尚杂志，汽车杂志，财经杂志可以免费在线看。http://www.magme.com/widget/widget!showTag.action?id=${tag.id}')
		    //status : 'abc'
		},{
		    method: 'post'
		});
		});
	
	});
	</#if>
})

</script>