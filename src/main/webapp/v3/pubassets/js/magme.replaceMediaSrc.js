function mediaSrc(e){function t(e,t){return e.lastIndexOf(t)==e.length-t.length}var i=document.getElementsByTagName("source");if(i&&i.length){for(var a=0;a<i.length;a++){var n=i[a],s=e+n.getAttribute("src");(t(s,".mp3")||t(s,".mp4"))&&n.parentNode.setAttribute("src",s)}for(;i&&i.length;){var n=i[0];n.parentNode.removeChild(n)}}}