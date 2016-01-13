<#import "publisherNav.ftl" as publisherNav> 
<div class="body bodyKanmi noBg" id="kanmiWall">
	<script>
		$(function(){
			$.jquerytagbox("#conPublisherHome",0);
			$('#kanmiWall').masonry({itemSelector: '.item'});
		});
		</script>
		<!--[if lt IE 7]>
		<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
		<script>
		  DD_belatedPNG.fix('.png');
		  document.execCommand("BackgroundImageCache",false,true);
		</script>
		<![endif]-->
	</script>
	<link href="${systemProp.staticServerUrl}/v3/style/channelPublisher.css" rel="stylesheet" type="text/css" />
	<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
	<script src="${systemProp.staticServerUrl}/v3/js/publisherPic.js"></script>
	<!--topBar-->
	<@publisherNav.main "publisherPic"/>
	<!--bodyContent-->
	<div class="bodyContent bodyQiemi clearFix">
			 <#if userImageList?? && (userImageList?size>0)>     
		            <#list userImageList as userImage>
					<div class="item">
				    	<a class="photo" href="${systemProp.appServerUrl}/user-image!show.action?imageId=${userImage.id}">
				            <img height="<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width}<#else>200</#if>px" src="${systemProp.tagServerUrl+userImage.path200}" />
				            <h5>${(userImage.description)!""}</h5>
				        </a>
				        <div class="tools"><em favTypeId="pic_${userImage.id}" class="iconHeart">${userImage.enjoyNum}</em><em shareTypeId="pic_${userImage.id}" class="iconShare">分享</em></div>
				        <div class="info">
				        	<a class="user" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}"><img src="<#if ((userImage.userAvatar30)??)&&(userImage.userAvatar30!="")>${systemProp.profileServerUrl+userImage.userAvatar30}<#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" /></a>
				            <p>由<a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}">${(userImage.userNickName)!""}</a>创建，出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${userImage.issueId}">[${(userImage.publicationName)!""}]</a></p>
				        </div>
				    </div>            
		            </#list>
		      </#if>
        </div>
    </div>
    </div>
</div>
</body>