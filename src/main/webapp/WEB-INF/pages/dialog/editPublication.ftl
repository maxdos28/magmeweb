<#macro main>
<div id="editPublicationDialog"  class="popContent">
	<fieldset>
		<h6>编辑杂志信息</h6>
    	<form method="post" id="editPublicationDialogForm">
        <div>
        	<em class="title">杂志名称</em>
        	<em><input id="publicationName" type="text" name="name" class="input g240" value=""></em>
        </div>
        <div>
        	<em class="title">类目</em>
        	<em>
        	    <select class="col-8" id="categoryId" name="categoryId" value="">
        	       <#if categoryList??>
        	            <#list categoryList as childcategory>
						    <option  value="${childcategory.id}">${childcategory.name}</option>
						</#list>
					</#if>
				</select>
			</em>
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
        			<input id="whratio" type="radio" name="whratio" value="1"  >
        			<span></span>
    			</label>
			</em>
		</div>
                   
        <div>
        	<em class="title">描述</em>
        	<em><input id="description" type="text" name="description" class="input g240" value=""></em>
        </div>

        <div>
        	<em class="title">刊号</em>
        	<em><input id="issn" type="text" name="issn" class="input g240" value=""></em>
        </div>
        
        <div>
        	<em class="title">出版人</em>
        	<em><input id="publisher" type="text" name="publisher" class="input g240" value=""></em>
        </div>

        <div>
        	<em class="title"> 期刊类型</em>
            <em>
                 <select name="issueType" id="issueType">
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
        </div>
        
        <div>
        	<em class="title">语言</em>
        	<em>
        	 <select class="col-8" id="languageId" name="languageId" value="">
				<option  value="1">中文</option>
				<option  value="2">英语</option>
			 </select>
			</em>
        </div>
        
        <div>
        	<em class="title"></em>
            <em><a href="javascript:void(0)" class="btnBS" id ="editPublicationSubmit">确定</a></em>
            <em><a id="cancel" href="javascript:void(0)" class="btnWS" >取消</a></em>
            <em id ="tipError" class="tipsError">请填写相关信息</em>
            <input type="hidden" name="id" value="">
        </div>
        </form>
    </fieldset>
</div>
</#macro>