$.fn.MD_TextcolumnIcon=function(e){return $(this).each(function(){var t=$(this);0==e?t.width()<t.find(">div")[0].scrollWidth&&(t.addClass("MD_Textcolumn_icon"),t.css({paddingBottom:15})):1==e&&t.height()<t.find(">div")[0].scrollHeight&&(t.addClass("MD_Textcolumn_icon_ver"),t.css({paddingRight:15}))})};