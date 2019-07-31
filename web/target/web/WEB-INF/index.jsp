<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>引导首页</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="static/layui/css/layui.css"  media="all">
  <link rel="stylesheet" href="static/auroral.css"  media="all">
  <style type="text/css">
  #show{
   display: block; 
   position: relative;
   overflow: hidden;
  }
  </style>
</head>
<body  style="text-align:center">
<div id="show" >
    <div id="bian" class="auroral-northern" style="position: absolute;z-index:-1;"></div>
    <div class="auroral-stars" style="position: absolute;z-index:-1;"></div>

<div style="margin-top:25%;">
<a href="${pageContext.request.contextPath}/filepage" target="_blank" class="layui-btn">
<i class="layui-icon layui-icon-add-1"></i>
<span>添加页面</span>
</a>
<a href="${pageContext.request.contextPath}/searchpage" target="_blank" class="layui-btn  layui-btn-normal">
<i class="layui-icon layui-icon-search"></i>
<span>搜索页面</span>
</a>
<a href="${pageContext.request.contextPath}/dictionary/jsp" target="_blank" class="layui-btn layui-btn-warm">
<i class="layui-icon layui-icon-read"></i>
<span>字典页面</span>
</a>
</div>
<!-- <a href="http://www.layui.com" class="layui-btn">
<i class="layui-icon layui-icon-face-smile"></i>
<span>添加页面</span>
</a>
<a href="http://www.layui.com" class="layui-btn">
<i class="layui-icon layui-icon-face-smile"></i>
<span>添加页面</span>
</a> -->
</div>
<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js"></script>
<script type="text/javascript">

var winHeight = $(window).height(); 
$("#show").css("height",winHeight); 
$(window).resize(function() { 
    winHeight = $(window).height(); 
    $("#show").css("height",winHeight); 
});
i=1;
c1="auroral-northern";
c2="auroral-northern-intense";
c3="auroral-northern-dusk";
c4="auroral-northern-warm";
c5="auroral-agrabah";
c6="auroral-northern-dimmed ";
setInterval(function(){
	
    if(i==1){
    $('#bian').attr('class',c2);	
    }
    if(i==2){
        $('#bian').attr('class',c3);	
        }
    if(i==3){
        $('#bian').attr('class',c4);	
        }
    if(i==4){
        $('#bian').attr('class',c5);	
        }
    if(i==5){
        $('#bian').attr('class',c6);	
        }
    if(i==6){
        $('#bian').attr('class',c1);
        i=0;
        }
    i++;
},10000);
</script>
</body>
</html>