<#macro main>
 		<div id="conSubMenu" class="con conSubMenu">
        	<ul>
            	<li class="<#if firstMenu=="1">current</#if>"><a name="userCenterMenu" href="javascript:void(0)">用户空间</a>
                </li>
            	<li class="sub <#if firstMenu=="2">current</#if>"><a href="javascript:void(0)">订阅收藏</a>
                	<ul <#if firstMenu=="2">style="display: block;"</#if>>
                        <li <#if firstMenu=="2"&&secondMenu=="0">class="current"</#if>><a name="favoriteMenu" href="javascript:void(0)">我的收藏</a></li>
                        <li <#if firstMenu=="2"&&secondMenu=="1">class="current"</#if>><a name="subscribeMenu" href="javascript:void(0)">我的订阅</a></li>
                    </ul>
                </li>
            	<li class="<#if firstMenu=="3">current</#if>"><a name="tagMenu" href="javascript:void(0)">我的标签</a>
                </li>
            	<li class="sub" <#if firstMenu=="4">current</#if>><a href="javascript:void(0)">好友关注</a>
            		<ul <#if firstMenu=="4">style="display: block;"</#if>>
                        <li <#if firstMenu=="4"&&secondMenu=="0">class="current"</#if>><a name="friendMenu" href="javascript:void(0)">我的好友</a></li>
                        <li <#if firstMenu=="4"&&secondMenu=="1">class="current"</#if>><a name="followMenu" href="javascript:void(0)">我的关注</a></li>
                        <li <#if firstMenu=="4"&&secondMenu=="2">class="current"</#if>><a name="waitFriendMenu" href="javascript:void(0)">好友申请</a></li>
                    </ul>
                </li>
            	<li class="<#if firstMenu=="5">current</#if>"><a name="messageMenu" href="javascript:void(0)">我的信息</a>
                </li>
            </ul>
        </div>
</#macro>