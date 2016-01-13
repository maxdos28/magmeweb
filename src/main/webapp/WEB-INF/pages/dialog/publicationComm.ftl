<#macro target>

<div id="adminMgzTarget" class="popContent">
	<form id="publicationTargetForm" method="post" action="#">
	        	<h6>杂志定位</h6>
	        	<fieldset>
	            	<div>
	            		<em class="title">性别比例</em>
	            		<em class="g170">
		            		<em>男性</em>
		            		<input type="text" maxlength="3" id="data_c31" name="data_c31" class="input g30" /><em>%</em>
		            		<em>女性</em><input type="text" id="data_c40" maxlength="3" name="data_c40" class="input g30" /><em>%</em>
	            		</em>
	            		<em class="title">年龄层</em>
	            		<em><select id="data_c32" name="data_c32" >
				            	<OPTION  value="">请选择</OPTION>
				            	<OPTION  value="6-12">6-12</OPTION>
								<OPTION  value="12-18">12-18</OPTION>
								<OPTION  value="18-25">18-25</OPTION>
								<OPTION  value="25-40">25-40</OPTION>
								<OPTION  value="40-60">40-60</OPTION>
								<OPTION  value="大于60">大于60</OPTION>
							</select>
						</em>
					</div>
	            	<div><em class="title">区域</em><em class="g170"><select id="data_c33" name="data_c33" >
	            		<option value="">请选择</option>
	            		<#if categoryList??>
							<#list categoryList as category>
								<option value="${(category.id)!''}">${(category.name)!''}</option>
							</#list>
							</#if>
	            	</select></em><em class="title">传播地区</em><em><select id="data_c34" name="data_c34" >
	            	<option value="">请选择</option>
	            	<OPTION  value="北京">北京</OPTION>
					<OPTION  value="上海">上海</OPTION>
					<OPTION  value="天津">天津</OPTION>
					<OPTION  value="广州">广州</OPTION>
	            	</select></em></div>
	            	<div>
	            		<em class="title">子女状况</em>
	            		<em class="g170"><select id="data_c35" name="data_c35" >
			            	<option value="">请选择</option>
			            	<OPTION value="无">无</OPTION>
							<OPTION  value="有">有</OPTION></select>
						</em>
						<em class="title">个人月收入</em>
						<em><select id="data_c36" name="data_c36" >
							<option value="">请选择</option>
							<OPTION  value="低于1000">低于1000</OPTION>
							<OPTION  value="1000-3000">1000-3000</OPTION>
							<OPTION  value="3001-6000">3001-6000</OPTION>
							<OPTION  value="6001-10000">6001-10000</OPTION>
							<OPTION  value="10001-20000">10001-20000</OPTION>
							<OPTION  value="高于20000">高于20000</OPTION>
						</select></em>
					</div>
	            	<div>
	            		<em class="title">学历</em><em class="g170"><select id="data_c37" name="data_c37" >
			            	<option value="">请选择</option>
			            	<OPTION value="小学/初中">小学/初中</OPTION>
							<OPTION  value="中专/高中">中专/高中</OPTION>
							<OPTION  value="大专/本科">大专/本科</OPTION>
							<OPTION  value="研究生或以上">研究生或以上</OPTION>
						</select></em>
						<em class="title">婚姻状况</em>
						<em>
						<em>已婚</em><input type="text" id="data_c38" maxlength="3" name="data_c38" class="input g30" /><em>%</em> 
						<em>未婚</em><input type="text" id="data_c41" maxlength="3" name="data_c41" class="input g30" /><em>%</em>
						</em>
					</div>
	            	<div><em class="title">家庭月收入</em><em class="g170"><select id="data_c39" name="data_c39" >
	            	<option value="">请选择</option>
	            	<OPTION value="低于3000">低于3000</OPTION>
<OPTION  value="3000-5000">3000-5000</OPTION>
<OPTION  value="5001-10000">5001-10000</OPTION>
<OPTION  value="10001-20000">10001-20000</OPTION>
<OPTION  value="20001-50000">20001-50000</OPTION>
<OPTION  value="高于50000">高于50000</OPTION>
	            	</select></em></div>
	            </fieldset>
	</form>
	<div class="actionArea tRight"><a href="javascript:void(0)" id="publicationTargetFormSubmit" class="btnBB" >关闭</a></div>
</div>
</#macro>

<#macro editPublisher>
<div id="userInfoDialog" class="popContent">
	<h6>查看出版商</h6>
	<fieldset>
		<form id="editForm" method="post" action="" onsubmit="return false;">
		<div class="floatl g350">
			<em class="title">用户名</em>
			<em><input id="userName" name="userName" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="floatl">
			<em class="title"><span>*</span>杂志社名称</em>
			<em><input id="publishName" name="publishName" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="clear floatl g350">
			<em class="title"><span>*</span>邮箱</em>
			<em><input id="email" name="email" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="floatl">
			<em class="title">法人</em>
			<em><input id="owner" name="owner" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<hr class="clear" />
		<div class="floatl g350">
			<em class="title">联系人1</em>
			<em><input id="contact1" name="contact1" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="floatl">
			<em class="title">联系人电话1</em>
			<em><input id="contactPhone1" name="contactPhone1" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="clear floatl g350">
			<em class="title">联系人2</em>
			<em><input id="contact2" name="contact2" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="floatl">
			<em class="title">联系人电话2</em>
			<em><input id="contactPhone2" name="contactPhone2" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="clear floatl g350">
			<em class="title">公司电话</em>
			<em><input id="companyPhone" name="companyPhone" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="floatl">
			<em class="title">传真号</em>
			<em><input id="fax" name="fax" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		 <div class="clear floatl g350">
			<em class="title">省份</em>
			<em><input id="provinceId" name="provinceId" value="" type="text" class="input g200" disabled="disabled" /></em>
		</div>
		<div class="floatl">
			<em class="title">城市</em>
			<em><input id="cityId" name="cityId" value="" type="text" class="input g200" disabled="disabled" /></em>
		</div>	                    
		<div class="clear floatl g350">
			<em class="title">新浪微博账号</em>
			<em><input id="weibouid" name="weiboUid" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="floatl">
			<em class="title">新浪微博效验码</em>
			<em><input id="weiboverifier" name="weiboVerifier" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="clear floatl g350">
			<em class="title">网站地址</em>
			<em><input id="webSite" name="webSite" value="" disabled="disabled" type="text" class="input g200" /></em>
		</div>
		<div class="floatl">
			<em class="title">其他联系方式</em>
			<em>
			   <select id="normalContactType" name="normalContactType"  disabled="">
					 <option value="1">QQ</option>
					  <option value="2">MSN</option>
					  <option value="3">其它</option>
			   </select>	                 	
			</em>
			<em><input id="normalContact" name="normalContact" class="input g130" type="text" value="" disabled="disabled"  /></em>
		</div>	                    
		<div class="clear floatl">
			<em class="title">详细地址</em>
			<em><input id="address" name="address" value="" disabled="disabled" type="text" class="input g550" /></em>
		</div>
		</form>
	</fieldset>
	<div class="actionArea tRight"></div>
</div>
</#macro>

<#macro uploadIssue>
 <div id="uploadIssueDialog" class="popContent">
	    	<h6>上传期刊</h6>
	    	<fieldset>
	        	<form method="post" id="uploadIssueForm" enctype="multipart/form-data" onsubmit="return false;">
	            <div>
	            	<em class="title">文件</em>
	            	<em><input type="file" id="issueFile" name="issueFile" /></em>
	            </div>
	            <div>
	            	<em class="title">关键词</em>
	            	<em><input type="text" class="input" name="keyword" /></em>
	            </div>
	            <div>
	            	<em class="title">发布日期</em>
	            	<em><input type="text" id="publishDate" name="publishDate" class="input" /></em>
	            	<em>(1985-03-21)</em>
	            </div>
	            <div>
	            	<em class="title">杂志名称</em>
	            	<em><input type="hidden" id="publicationId" name="publicationId" class="input" /><input type="text" id="publicationName" name="publicationName" class="input" /></em>
	            </div>
	            <div>
	            	<em class="title">期刊号</em>
                    <em class="g100"><select id="year" name="year" class="g70" >
			        	<option value="2010">2010</option>
			        	<option value="2011">2011</option>
			        	<option value="2012">2012</option>
			        	<option value="2013">2013</option>
                    </select>
                    &nbsp;年
                    </em>
                    <em class="g80"><select id="month" name="month" class="g50" >
                    	<option selected="selected">1</option>
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
	        <div id="progress-bar" title="上传进度" class="upload" style="display:none;">
    	<strong>上传进度:</strong>
    	<span id="current_size">0</span>
    	<span>/</span>
    	<span id="max_size">0</span>
    	<div class="inner"><sub id="bar-inner"></sub></div>
        <span id="persent">0%</span>
        <a id="cancelUpload" href="javascript:void(0)">取消</a>
    </div>
     <div >提示：关闭本窗口或刷新本页面将取消上传期刊！</div>
		</div>
</#macro>