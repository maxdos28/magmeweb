<#macro main>
		<div id="createPublicationDialog"  class="popContent">
            <fieldset>
            	<h6>创建杂志</h6>
                <form method="post" id="createPublicationDialogForm">
                <div>
                    <em class="title">类目</em>
                    <em>
                        <select name="categoryId" class="g100">
                           <#if categoryList??>
                                <#list categoryList as childcategory>
                                    <option value="${childcategory.id}">${childcategory.name}</option>
                                </#list>
                            </#if>
                        </select>
                    </em>
                </div>
                <div>
                    <em class="title">杂志名称</em>
                    <em><input type="text" class="input g330" name="name"></em>
                </div>
                <div>
                    <em class="title">描述</em>
                    <em><input type="text" class="input g330" name="description"></em>
                </div>
                <div>
                    <em class="title">刊号</em>
                    <em><input type="text" class="input g330" name="issn"></em>
                </div>
                <div>
                    <em class="title">出版人</em>
                    <em><input type="text" class="input g330" name="publisher"></em>
                </div>
				<div class="clip">
					<em class="title">杂志形状</em>
					<em class="type1">
						<label>
							<input type="radio" name="whratio" value="0" checked="true">
							<span></span>
						</label>
					</em>
	            	<em class="type2">
	            		<label>
	            			<input type="radio" name="whratio" value="1"  >
	            			<span></span>
            			</label>
    				</em>
				</div>
                <div>
                	<em class="title"> 期刊类型</em>
                    <em>
	                     <select name="issueType" class="g100">
	                        <option  value="2">周刊</option>
	                        <option  value="3">半月刊</option>
	                        <option  value="4">月刊</option>
	                        <option  value="5">双月刊</option>
	                        <option  value="6">季刊</option>
	                        <option  value="7">半年刊</option>
	                        <option  value="1">日报</option>
	                        <option  value="20">其它</option>
	                     </select>
                    </em>
                    <em class="title">语言</em>
                    <em>
                     <select name="languageId" class="g80">
                        <option  value="1">中文</option>
                        <option  value="2">英语</option>
                     </select>
                    </em>
                </div>
                
                <div>
                    
                </div>
                
                <div>
                	<em class="title"></em>
                    <em ><a href="#"  class="btnBS" id ="createPublicationSubmit">确定</a></em>
                    <em ><a href="#"  class="btnWS">取消</a></em>
                    <em id ="tipError" class="tipsError">请填写相关信息</em>
                </div>
                </form>
            </fieldset>
        </div>
</#macro>