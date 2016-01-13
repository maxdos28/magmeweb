/*
 * Translated default messages for the jQuery validation plugin.
 * Language: CN
 * Author: Fayland Lam <fayland at gmail dot com>
 */
jQuery.extend(jQuery.validator.messages, { 
        required: "必填项",
		remote: "请修正该字段",
		email: "格式错误",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "格式错误",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("长度最多是 {0} "),
		minlength: jQuery.validator.format("长度最少是 {0} "),
		rangelength: jQuery.validator.format("长度介于 {0} 和 {1} 之间"),
		range: jQuery.validator.format("介于 {0} 和 {1} 之间"),
		max: jQuery.validator.format("最大为 {0}"),
		min: jQuery.validator.format("最小为 {0}")
});
//手机号码验证 
jQuery.validator.addMethod("mobile", function(value, element) { 
var length = value.length; 
var mobile = /^(((1[0-9][0-9]{1}))+\d{8})$/ 
return this.optional(element) || (length == 11 && mobile.test(value)); 
}, "格式错误"); 

// 电话号码验证 
jQuery.validator.addMethod("phone", function(value, element) { 
var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/; 
return this.optional(element) || (tel.test(value)); 
}, "格式错误"); 

// 邮政编码验证 
jQuery.validator.addMethod("zipCode", function(value, element) { 
var tel = /^[0-9]{6}$/; 
return this.optional(element) || (tel.test(value)); 
}, "格式错误"); 
//邮政编码验证 
jQuery.validator.addMethod("eggNos", function(value, element) { 
var tel = /^(([0-9]{1,100}$)|(([0-9]{1,100})+((\,|\-){1}))+([0-9]{1,100}))$/; 
return this.optional(element) || (tel.test(value)); 
}, "格式错误"); 
//日期验证 
jQuery.validator.addMethod("custDate", function(value, element) { 
var tel = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/; 
return this.optional(element) || (tel.test(value)); 
}, "格式错误"); 
