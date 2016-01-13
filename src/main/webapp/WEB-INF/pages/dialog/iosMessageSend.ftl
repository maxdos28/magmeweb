<#macro main>
 <div id="adminIosMessage" class="popContent" style="width:650px;">
            <h6>IOS通知</h6>
        	<div class="conBody">
        	<fieldset>
                <div>
                    <em class="floatr">还可输入<span id="conIosMessage">60</span>个字</em><em>消息内容</em>
                    <em class="clear"><textarea class="input g610" id="iosMessage" maxLength="60"></textarea><input type="hidden" id="appIdHidden" value="" ></input><input type="hidden" id="iosIdHidden" value="" ></input></em>
                </div>
                <hr class="clear" />
                <div>
                    <em class="g80"><label><input type="radio" name="sendType" value="1" checked />立即发送</label></em><em><label class="g80"><input type="radio" name="sendType" value="2"  />定时发送</label><input type="text" id="sendTime" name="sendTime" class="input g190" /></em>
                </div>
                <script>
                	$(function(){
						//字数限制
						$("#iosMessage").focus(function(){
							$(window).bind("keyup",getIosMessage);
						}).blur(function(){
							$(window).unbind("keyup",getIosMessage);
						});
						function getIosMessage(){
							if($("#iosMessage").val().length>60){
								$("#iosMessage").val( $("#iosMessage").val().slice(0,60))
							}
							$("#conIosMessage").text(60-$("#iosMessage").val().length);
						}
					
					});
                </script>
            </fieldset>
        </div>
            <div class="actionArea tRight">
                <a class="btnSM" id="iosMessageChanel" href="javascript:void(0);">取消</a>&nbsp;&nbsp;<a class="btnAM" id="iosMessageOk" href="javascript:void(0);">发送</a>
            </div>
</div>
</#macro>