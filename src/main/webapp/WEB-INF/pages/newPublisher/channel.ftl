<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script>
	var curSortId="${sortId!''}";
</script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/channel.js"></script>
    <div class="conLeftMiddleRight" menu="editor" label="channel">
    	<div class="con25 conTools">
        	<fieldset>
            	<div class="top">
                	<em>
                		<select id="sortId">
                			<option value="">请选择分类</option>
                			<#if sortList??>
                			<#list sortList as sort>
                				<option value="${(sort.id)!''}" <#if sortId??&&sortId==sort.id >selected="true"</#if>>${(sort.name)!''}</option>
                			</#list>
                			</#if>
                		</select>
                	</em>
                	<em>
                		<select name="channelView.mode">
	                		<option value="1" <#if channelView??&&channelView.mode ==2 ><#else>selected="true"</#if>>图片模式</option>
	                		<option value="2" <#if channelView??&&channelView.mode ==2 >selected="true"</#if>>文本模式</option>
                		</select>
                	</em>
                    <em><a class="btnWB" id="saveChannelViewBtn" href="javascript:void(0)">保存</a></em>
                </div>
            </fieldset>
        </div>
        
        <div class="conB con26 jqueryTagBox" id="channelTab">
            <div class="ctrl">
                <div>封面故事</div>
                <div>主题管理</div>
                <div id="subjectEventLable">主题事件</div>
                <div>广告管理</div>
            </div>
            <div class="doorList">
                <div class="item item1">
                    <fieldset>
                    	<form id="channelBannerForm">
                    	<input type="hidden" name="channelBanner.id"/>
                    	<h2>新建/编辑 封面故事</h2>
                        <div class="floatl g320">
                            <div>
                                <em>标题</em>
                                <em class="g170"><input name="channelBanner.title" type="text" class="input g150" /></em>
                                <em>权重</em>
                                <em class="g80"><input name="channelBanner.weight" type="text" class="input g40" /></em>
                            </div>
                            <div>
                                <em>图片</em>
                                <em class="g280"><input id="pic" name="pic" type="file" class="g250" /></em>
                            </div>
                            <div>
                                <em>链接</em>
                                <em><input type="text" name="channelBanner.url" class="input g190" /></em>
                                 <em><label><input name="channelBanner.type" type="checkbox" />站外</label><label><input name="channelBanner.indexkey" type="checkbox" />首页封面</label></em>
                            </div>
                        </div>
                        <div class="floatl">
                            <em>描述</em>
                            <em><textarea name="channelBanner.description" class="input g260"></textarea></em>
                        </div>
                        <div class="clear">
                            <em><a class="btnWS" id="resetChannelBannerBtn" href="javascript:void(0)">清空</a></em>
                            <em><a class="btnBS" id="saveChannelBannerBtn" href="javascript:void(0)">保存</a></em>
                        </div>
                        <hr />
                        </form>
                    </fieldset>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
                      <thead>
                        <tr>
                            <td class="g60">ID</td>
                            <td class="g150">标题</td>
                            <td>图片</td>
                            <td class="g250">链接</td>
                             <td class="g60">目标</td>
                            <td class="g40">权重</td>
                            <td class="g100">操作</td>
                          </tr>
                        </thead>
                        <tbody id="channelBannerList">
                        	<#if channelBannerList??>
                        	<#list channelBannerList as channelBanner>
                            <tr channelBannerId="${(channelBanner.id)!''}">
                                <td>${(channelBanner.id)!''}</td>
                                <td>${(channelBanner.title)!''}</td>
                                <td><#if (channelBanner.path)?? && channelBanner.path!=""><a href="${systemProp.newPublisherServerUrl+channelBanner.path}" target="banerPath">${systemProp.newPublisherServerUrl+channelBanner.path}</a></#if></td>
                                <td><#if (channelBanner.url)??&& channelBanner.url!=""><a href="${channelBanner.url}" target="banerPath">${channelBanner.url}</a></#if></td>
                                <td><#if (channelBanner.indexkey)?? && channelBanner.indexkey==1>A</#if><#if (channelBanner.type)?? && channelBanner.type ==1>站外<#else>站内</#if>&nbsp;</td>
                                <td>${(channelBanner.weight)!''}</td>
                                <td><a class="btn" name="editChannelBanner" href="#channelTab">编辑</a><a class="btn" name="deleteChannelBanner"  href="javascript:void(0)">删除</a></td>
                            </tr>
                            </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
                <div class="item item2" id="channelSubjectForm">
                    <fieldset>
                    	<input type="hidden" name="channelSubject.id"/>                    
                    	<h2>新建/编辑 主题</h2>
                        <div>
                            <em>名称</em>
                            <em class="g300"><input type="text" name="channelSubject.name" class="input g250" /></em>
                            <em>权重</em>
                            <em class="g80"><input type="text" name="channelSubject.weight" class="input g30" /></em>
                            <em><a class="btnWS" id="resetChannelSubjectBtn" href="javascript:void(0)">清空</a></em>
                            <em><a class="btnBS" id="saveChannelSubjectBtn" href="javascript:void(0)">保存</a></em>
                        </div>
                        <hr />
                    </fieldset>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
                      <thead>
                        <tr>
                            <td class="g60">ID</td>
                            <td>名称</td>
                            <td class="g60">权重</td>
                            <td class="g90">操作</td>
                          </tr>
                        </thead>
                        <tbody id="channelSubjectList">
                        	<#if channelSubjectList??>
                        	<#list channelSubjectList as channelSubject>
                            <tr channelSubjectId="${(channelSubject.id)!''}">
                                <td>${(channelSubject.id)!""}</td>
                                <td>${(channelSubject.name)!""}</td>
                                <td>${(channelSubject.weight)!""}</td>
                                <td><a class="btn" name="editChannelSubject" href="#channelTab">编辑</a><a class="btn" name="deleteChannelSubject"  href="javascript:void(0)">删除</a></td>
                            </tr>
                            </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
                <div class="item item3">
                    <fieldset>
                        <div>
                            <em>
                            	<select name="subjectList">
                            		<option value="">请选择主题</option>
                            	</select>
                            </em>
                        </div>
                        <hr />
                    </fieldset>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg mgb20">
                      <thead>
                        <tr>
                            <td class="g100">事件ID</td>
                            <td class="g200">事件标题</td>
                            <td class="g100">查看</td>
                            <td class="g100">权重</td>
                            <td class="g100">操作</td>
                          </tr>
                        </thead>
                        <tbody id="subjectEventList">

                        </tbody>
                    </table>
                    <fieldset>
                    	<h2>事件查询条件</h2>
                        <div>
                        <form id="searchEventForm">
                            <em class="g200"><a class="btnBS" name="batchAddToSubjectBtn" href="javascript:void(0)">批量加入主题</a></em>
                            <em>创建时间</em>
                            <em><input type="text" name="createdTimeStart" class="input g80" /></em>
                            <em class="g120"><input type="text" name="createdTimeEnd" class="input g80" /></em>
                            <em>事件ID</em>
                            <em class="g120"><input type="text" name="id" class="input g80" /></em>
                            <em>事件标题</em>
                            <em class="g180"><input type="text" name="title" class="input g150" /></em>
                            <em><a class="btnBS" name="searchEventBtn" href="javascript:void(0);">搜索</a></em>
                        </form>
                        </div>
                        <hr />
                    </fieldset>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg mgb20">
                      <thead>
                        <tr>
                            <td class="g30"><label><input id="batchEventCheck"type="checkbox" /></label></td>
                            <td class="g50">事件ID</td>
                             <td class="g70">缩略图</td>
                            <td class="g200">事件标题</td>
                            <td class="g100">查看</td>
                            <td class="g100">操作</td>
                          </tr>
                        </thead>
                        <tbody id="eventList">
                        </tbody>
                    </table>
                    <div>
                    	<div id="eventListPageadd" class="conB gotoPage"></div>
						 <div id="eventListPage" class="conB changePage" ></div>
					</div>
                </div>
                <div class="item item4">
                    <fieldset>
                        <h2>广告管理</h2>
						<textarea name="channelAd.content" class="conEdit">${(channelAd.content)!""}</textarea>
                        <div>
                            <em><a class="btnBS" id="saveChannelAdBtn" href="javascript:void(0)">保存</a></em>
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
		<div class="conFooter">
        <div class="conB con27 tCenter">
        	<a class="btnWB" name="previewChannelBtn" href="javascript:void(0)">预览</a>
        	<a class="btnBB" name="releaseChannelBtn" href="javascript:void(0)">发布</a>
        </div>
        </div>
    </div>