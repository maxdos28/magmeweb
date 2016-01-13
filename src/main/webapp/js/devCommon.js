$(function() {
	//空字符处理
	strEmpty = function(str) {
		return str?str:"";
	}
	//空数字处理
	numEmpty = function(str) {
		return str?str:"0";
	}
	trimDate = function(date) {
		if (date) {
			return date.substring(0, date.indexOf("T"));
		} else {
			return "";
		}
	}
	trimTime = function(date) {
		if (date) {
			return date.substring(date.indexOf("T")+1);
		} else {
			return "";
		}
	}
	trimDateTime = function(date) {
		if (date) {
			return date.replace(new RegExp("T","g"), " ");
		} else {
			return "";
		}
	}
	//得到当前日期
	getDateString = function (){
        var oDate = new Date();
         
        var month = oDate.getMonth() + 1;
        if (month <= 9)
          month = "0" + month
           
        var day = oDate.getDate();
        if (day <= 9)
          day = "0" + day;
        
        var sDate = oDate.getFullYear() + "-" + month + "-" + day;
        return sDate;
 }
	//数字判断,通过keypress事件
	isNumber = function(event) {
		var keyCode = event.which;  
	    if (keyCode >= 48 && keyCode <=57||keyCode==8||keyCode==45)  
	        return true;  
	    else  
	        return false;  
	}
	isDouble = function(event) {
		var keyCode = event.which;  
	    if (keyCode >= 48 && keyCode <=57||keyCode==8||keyCode==46)  
	        return true;  
	    else  
	        return false;  
	}
	isDate = function(date) {
		var a = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;  
		 return a.test(date);
	}
	//下面这个方法是将json对象转换为字符串  
	obj2str = function(o){  
	    var r = [];  
	    if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t").replace(/\s+/g,"\\s")+"\"";  
	    if(typeof o =="undefined") return "undefined";  
	    if(typeof o == "object"){  
	        if(o===null) return "null";  
	        else if(!o.sort){  
	            for(var i in o)  
	                r.push(i+":"+obj2str(o[i]))  
	            r="{"+r.join()+"}"  
	        }else{  
	            for(var i =0;i<o.length;i++)  
	                r.push(obj2str(o[i]))  
	            r="["+r.join()+"]"  
	        }  
	        return r;  
	    }  
	    return o.toString();  
	}  
	$("input[validate=int]").live("keypress",isNumber);
	$("input[validate=double]").live("keypress",isDouble);
	replace = function (str,oc,nc) 
	{ 
	   return newstart=str.replace(new RegExp(oc,"g"),nc); 
	} 
});