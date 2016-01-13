<div id="popLoginRegister" class="popContent">
	<div class="jqueryTagBox" id="popLoginTab">
           <div class="ctrl">
               <div>登录</div>
               <div>注册</div>
           </div>
           <div class="doorList">
               <div class="item">
                   <form id="popLoginForm" action="" onsubmit="return false;" method="post">
                       <ul class="select">
                           <li class="current"><a href="javascript:void(0)"><img src="images/icon/sns32/32-magme.gif" /></a></li>
                           <li><a href="javascript:void(0)" name="login_qq"><img src="images/icon/sns32/32-qq.gif" /></a></li>
                           <li><a href="javascript:void(0)" name="login_renren"><img src="images/icon/sns32/32-renren.gif" /></a></li>
                           <li><a href="javascript:void(0)" name="login_weibo"><img src="images/icon/sns32/32-weibo.gif" /></a></li>
                           <li><a href="javascript:void(0)" name="login_baidu"><img src="images/icon/sns32/32-baidu.gif" /></a></li>
                           <li><a href="javascript:void(0)" name="login_kqixin"><img src="images/icon/sns32/32-kaixin001.gif" /></a></li>
                       </ul>
                       <div class="con">
                           <fieldset>
                               <div>
                                   <em class="title" >用户名/邮箱</em>
                                   <em><input class="input" name="userName" type="text" /></em>
                                   <em class="tipsRight" style="display:block;"></em>
                                   <em class="tipsError" style="display:block;">请填写相关信息</em>
                               </div>
                               <div>
                                   <em class="title" >密码</em>
                                   <em><input class="input" name="password" type="password" /></em>
                                   <em class="tipsWrong" style="display:block;"></em>
                               </div>
                               <div>
                                   <em class="title" ></em>
                                   <em class="m0"><a class="btnOS" name="signIn" href="#" >登录</a></em>
                                   <em class="remanber"><label><input type="checkbox" checked/>下次自动登录</label></em>
                                   <em class="tipsError">请填写相关信息</em>
                               </div>
                               <div>
                                   <em class="title" ></em>
                                   <em><a id="popForgetPassword" href="javascript:void(0)">忘记密码？</a></em>
                                   <em><a id="popNewUserReg"href="javascript:void(0)">新用户注册</a></em>
                                   <script>
                                   	$(function(){
							$("#popNewUserReg").click(function(){
								$("#popLoginTab>.ctrl>div:eq(1)").click();
							})
						})
                                   </script>
                               </div>
                           </fieldset>
                       </div>
                   </form>                        
               </div>
               <div class="item" style="display:none;">
                   <form id="popRegisterForm" action="" onsubmit="return false;" method="post">
                       <div class="con">
                           <fieldset>
                               <div>
                                   <em class="title" >用户名</em>
                                   <em><input class="input" name="userName" type="text" /></em>
                                   <em class="tipsRight" style="display:block;"></em>
                                   <em class="tipsError" style="display:block;">请填写相关信息</em>
                               </div>
                               <div>
                                   <em class="title" >邮箱</em>
                                   <em><input class="input" name="email" type="text" /></em>
                                   <em class="tipsWrong" style="display:block;"></em>
                               </div>
                               <div>
                                   <em class="title" >密码</em>
                                   <em><input class="input" name="password" type="password" /></em>
                               </div>
                               <div>
                                   <em class="title" >确认密码</em>
                                   <em><input class="input" name="password" type="password" /></em>
                                   <em class="tipsRight"></em>
                               </div>
                               <div>
                                   <em class="title" ></em>
                                   <em class="m0"><a class="btnBS" name="subimt" href="#" >注册</a></em>
                                   <em class="loading"></em>
                                   <em class="tipsError" style="display:block;">请填写相关信息</em>
                               </div>
                           </fieldset>
                       </div>
                    </form>                                   
               </div>
           </div>
   	</div>
</div>