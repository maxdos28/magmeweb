<#import "../publish/pubFamily.ftl" as pubFamily>
<#macro main>
    <!--topBar-->
    <div id="kanmiTopBar" name="kanmiTopBar" class="kanmiTopBar">
    	<div class="conCategoryV2" id="conCategory">
            <a href="javascript:void(0)" class="btn" id="btn"><strong>杂志分类</strong><span></span></a>
            <div id="conBody" class="conBody clearFix">
            	<#if childrenCategoryMap?exists  && parentCategories??>
	    		  <#list parentCategories as parentCategory>
	    		  	<div class="item clearFix <#if (parentCategory_index+1)==parentCategories?size>itemLast</#if>">
						<h3><a categoryId="${parentCategory.id}" <#if categoryId??  && (categoryId==parentCategory.id)> class="current" </#if> href="javascript:void(0)"> ${parentCategory.name}</a></h3>
		                <#assign children= childrenCategoryMap["${parentCategory.name}"] >
		                <div class="list">
		                    <ul>
		                       <#list children as child>  
				                   <li><a categoryId="${child.id}" <#if categoryId??  && (categoryId==child.id)> class="current" </#if> href="javascript:void(0)">${child.name}</a></li>
			                   </#list>
		                    </ul>
		                </div>
		             </div>
		          </#list>
                </#if>
                <div class="item letter clearFix hide">
	    			<h3><a href="javascript:void(0)">字母检索</a></h3>
	    			<div>
	                    <ul id="characterSearch" class="clearFix">
	                        <li><a letter="A" href="javascript:void(0)">A</a></li>
	                        <li><a letter="B" href="javascript:void(0)">B</a></li>
							<li><a letter="C" href="javascript:void(0)">C</a></li>
							<li><a letter="D" href="javascript:void(0)">D</a></li>
							<li><a letter="E" href="javascript:void(0)">E</a></li>
							<li><a letter="F" href="javascript:void(0)">F</a></li>
							<li><a letter="G" href="javascript:void(0)">G</a></li>
							<li><a letter="H" href="javascript:void(0)">H</a></li>
							<li><a letter="I" href="javascript:void(0)" class="hover">I</a></li>
							<li><a letter="J" href="javascript:void(0)">J</a></li>
							<li><a letter="K" href="javascript:void(0)">K</a></li>
							<li><a letter="L" href="javascript:void(0)">L</a></li>
							<li><a letter="M" href="javascript:void(0)">M</a></li>
							<li><a letter="N" href="javascript:void(0)">N</a></li>
							<li><a letter="O" href="javascript:void(0)">O</a></li>
							<li><a letter="P" href="javascript:void(0)">P</a></li>
							<li><a letter="Q" href="javascript:void(0)">Q</a></li>
							<li><a letter="R" href="javascript:void(0)">R</a></li>
							<li><a letter="S" href="javascript:void(0)">S</a></li>
							<li><a letter="T" href="javascript:void(0)">T</a></li>
							<li><a letter="U" href="javascript:void(0)">U</a></li>
							<li><a letter="V" href="javascript:void(0)">V</a></li>
							<li><a letter="W" href="javascript:void(0)">W</a></li>
							<li><a letter="X" href="javascript:void(0)">X</a></li>
							<li><a letter="Y" href="javascript:void(0)">Y</a></li>
							<li><a letter="Z" href="javascript:void(0)">Z</a></li>
	                    </ul>
	                </div>
	    		</div><!--    End-last   -->
	    	</div><!--   End-conBody    -->
	    	
	    	
		    <div class="conFooter png"></div>
	    </div><!--    End-conCategory    -->
        <@pubFamily.main />
    </div>
</#macro>