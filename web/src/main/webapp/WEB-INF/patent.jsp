<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>${patent.createThing }-专利资料</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="static/layui/css/layui.css"  media="all">
</head>
<style type="text/css">
    .addword{
     margin:6px;
	 padding:2px;
     display:inline-block;
     cursor:pointer;
     box-shadow: 2px 2px 2px #5BC0DE;
    }
    .n0{background: #ff8000;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n1{background: #0080ff;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n2{background: #ffd9ec;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n3{background: #84c1ff;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n4{background: #c1ffe4;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n5{background: #ffffce;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n6{background: #ffe6d9;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n7{background: #d9b3b3;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n8{background: #cdcd9a;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n9{background: #ffbfff;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n10{background:#a3d1d1;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n11{background: #b8b8dc;min-width:50px; min-height: 24px;border-radius:6px;text-align:center}
	.n12{background: #9f35ff;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n13{background: #ffed97;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n14{background: #bbffbb;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n15{background: #deffac;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n16{background: #ca8eff;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n17{background: #aaaaff;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n18{background: #ffb5b5;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n19{background: #97cbff;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n20{background: #e2c2de;min-width:65px; min-height: 24px;border-radius:6px;text-align:center}
	.n21{background: #bebebe;min-width:90px; min-height: 24px;border-radius:6px;text-align:center}
	.n22{background: #ff0000;min-width:90px; min-height: 24px;border-radius:6px;text-align:center}
	/* span[class^="n"]{
	display:inline-block;
	 margin:6px;
	 height:24px;
	 width:auto;
	 padding:2px;
	} */
	label{
		border: 1px solid #888888;
		padding:8px 10px!important;
		background-color: #CCCCCC;
		text-align: left!important;	
		width: 17.28571rem!important;	
	}
	.layui-inline{
		width: 100%;
	}
	.layui-input-inline{
		width: 25.42857rem!important;
	}
	.well {
	text-indent:25px;
    min-height: 20px;
    padding: 19px;
    margin-bottom: 20px;
    background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
    box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
    font-size:16px; 
    line-height:1.5;
}
blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
}
.layui-row:hover blockquote{
color: #5bc0de;
}
blockquote {
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-end: 40px;
}
</style>
<body> 
<div style="display:none;" id="id">${patent.id }</div>   
<form id="update" action="updatepage" method="post">
<input type="hidden" name="id" value="${patent.id }">
</form>    
<div class="layui-container" style="margin-top:45px;margin-bottom:45px;">    	
<h1 style="text-align:center;margin:15px;">${patent.createThing }</h1>
  <!--专利基本信息表单-->
  <div class="layui-btn-group" style="float:right ;margin-bottom:15px;">
	  <a href="excel?id=${patent.id }" class="layui-btn layui-btn-sm layui-btn-normal" type="button">
	  📥<span>下载</span>
	  </a>
	  <button onclick="update()"  class="layui-btn layui-btn-sm layui-btn-warm" type="button">
	  ✍️<span>修改</span>
	  </button>
	  <button onclick="del()" class="layui-btn  layui-btn-sm layui-btn-danger" type="button">
	  ❌<span>删除</span>
	  </button>
  </div>
 
 
<table style="margin-top:15px;"  width='100%' border='0' cellpadding='0' cellspacing='1' bgcolor='#cccccc' style='font-size:14px;'>
<tr>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>申请号<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.requestNumber }</td>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>申请日<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.filingDate }</td>
<tr>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>公开（公告）号<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.publicationNumber }</td>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>公开（公告）日<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.publicationDate }</td>
<tr>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>IPC分类号<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.ipc }</td>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>申请（专利权）人<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.proposer }</td>
<tr>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>发明人<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.createPeople }</td>
<td height='30' width='20%' bgcolor='#EFEFEF' style='padding-left:10px;'>优先权号<td>
<td bgcolor='#FFFFFF' width='30%' style='padding-left:10px;word-wrap: break-word;break-all;'>${patent.priorityNumber }</td>
</table>
<!--  摘要 -->
<c:if test="${not empty patent.remark }">
  <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>摘要</h2></blockquote>
  <div class="well">
  <p>
   	${patent.form_remark }
  </p>
  </div>
  </div>
</c:if>

<!--权利要求书-->
<c:if test="${not empty patent.profitRequest }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>权利要求书</h2></blockquote>
  <div class="well">
  
  <p>${patent.form_profitRequest}</p>
  </div>
  </div>
</c:if>
<!--技术领域-->
<c:if test="${not empty patent.technicalField }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>技术领域</h2></blockquote>
  <div class="well">
  <p>
   	${patent.technicalField }
  </p>
  </div>
  </div>
</c:if>
<!--背景技术-->                  
<c:if test="${not empty patent.backgroundTechnology }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>背景技术</h2></blockquote>
  <div class="well">
  <p>         
   	${patent.backgroundTechnology }
  </p>
  </div>
  </div>
</c:if>

<!--发明内容-->
<c:if test="${not empty patent.summaryInvention }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>发明内容</h2></blockquote>
  <div class="well">
  <p>
   	${patent.summaryInvention }
  </p>
  </div>
  </div>
</c:if>
<!-- 实用新型内容 -->
<c:if test="${not empty patent.practicalContent }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>实用新型内容</h2></blockquote>
  <div class="well">
  <p>
   	${patent.practicalContent }
  </p>
  </div>
  </div>
</c:if>
<!-- 附图说明 -->
<c:if test="${not empty patent.descriptionDrawings }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>附图说明</h2></blockquote>
  <div class="well">
  <p>
   	${patent.descriptionDrawings }
  </p>
  </div>
  </div>
</c:if>
<!--具体实施方式-->
<c:if test="${not empty patent.specificMethods }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>具体实施方式</h2></blockquote>
  <div class="well">
  <p>
   	${patent.specificMethods }
  </p>
  </div>
  </div>
</c:if>
<!--类别-->
<c:if test="${not empty patent.patentType }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>专利类别</h2></blockquote>
  <div class="well">
  <p>
   	${patent.patentType }
  </p>
  </div>
  </div>
</c:if>
<!--改善-->
<c:if test="${not empty patent.better }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>改善</h2></blockquote>
  <div class="well">
  <p>
   	${patent.better }
  </p>
  </div>
  </div>
</c:if>
<!--恶化-->
<c:if test="${not empty patent.worsen }">
 <div class="layui-row" style="margin-top:45px;">
  <blockquote><h2>恶化</h2></blockquote>
  <div class="well">
  <p>
   	${patent.worsen }
  </p>
  </div>
  </div>
</c:if>


<div  class="layui-row layui-col-space15" style="margin-top:45px;">
<div class="layui-col-md9">
<blockquote class="layui-elem-quote" style="margin-top:45px;margin-inline-end:0px;">
<span>分词结果</span>

 <button id="renew-btn" type="button" style="float:right; height:45px;margin-left:10px;margin-top: -11px;" class="layui-btn layui-btn-disabled" onclick="renew()">更新</button>

</blockquote>
<fieldset class="layui-elem-field layui-field-title">
  <legend>未被记录</legend>
  <div id="analyzers" class="layui-field-box">
   <c:forEach var="analyzer" items="${patent.analyzer }" >
    <span class="fenci addword" onclick="addOne(this)" >${analyzer }</span>
   </c:forEach>
  </div>
</fieldset>

<fieldset class="layui-elem-field layui-field-title">
  <legend>被记录</legend>
  <div id="analyzersExist" class="layui-field-box">
   <c:forEach var="analyzer" items="${patent.analyzersExist }" >
    <span class="fenci addword">${analyzer }</span>
   </c:forEach>
  </div>
</fieldset>
</div>
<div class="layui-col-md3">
<blockquote class="layui-elem-quote" style="margin-top:45px;margin-inline-end:0px;">新词发现</blockquote>
<div id="newworddiv">
</div>
</div>
</div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js"></script>
<script src="static/layui/layui.all.js"></script>
<script type="text/html" id="addOneDiv" >
<div style="padding:15px;">
<form class="layui-form">
  <div class="layui-form-item">
    <span class="layui-form-label" style="text-align:left">词</span>
    <div class="layui-input-block" style="margin-left"90px;">
      <input type="text" id="add-content" lay-verify="title" autocomplete="off" placeholder="词" class="layui-input">
    </div>
  </div>
  
  
  <div class="layui-form-item">
    <span class="layui-form-label" style="text-align:left">词性</span>
    <div class="layui-input-block" style="margin-left"90px;">
      <input type="text" id="add-partOfSpeech" lay-verify="title" autocomplete="off" placeholder="词性" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <span class="layui-form-label" style="text-align:left">权重</span>
    <div class="layui-input-block" style="margin-left"90px;">
      <input type="text" id="add-weight" lay-verify="title" autocomplete="off" placeholder="权重" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <span class="layui-form-label" style="text-align:left">分类</span>
    <div class="layui-input-block" style="margin-left"90px;">
      <input type="text" id="add-type" lay-verify="title" autocomplete="off" placeholder="分类" class="layui-input">
    </div>
  </div>
 <div class="layui-form-item">
  <input type="button" onclick="addOneSubmit()"  lay-verify="title" autocomplete="off" value="提交" class="layui-input">
 </div>
</form>
</div>
</script>    
<script type="text/javascript">
//发送ajax新词发现
$(function(){
	var text="";
	//获取专利内容.
	$('.well').each(function(){
	  text=text+$(this).text().trim();
	});
	$.ajax({
		type:"post",
		url:"findnewword",
		data:{text:text},
		dataType:"json",
		success:function(data){
			var list= data.newwords;
			$('#newworddiv').empty();
			if(list!=null){
			for(var word in list){
				$('#newworddiv').append("<span class='addword newword' onclick='addOne(this)' style='min-width:50px; min-height: 24px;border-radius:6px;text-align:center' >"+list[word]+"</span>");
			}
			}else{
				$('#newworddiv').append("未发现新词!");
			}
		},
		error:function(){
			$('#newworddiv').empty();
			$('#newworddiv').append("请求异常!");
		}
	});
});
//更新字典
function renew(){
	$.ajax({
		type:"post",
		url:"${DictionaryRenew}",
		success:function(data){
			if(data==1){
				layer.msg('字典更新成功!');
				$('#renew-btn').attr('class',"layui-btn layui-btn-disabled");//更新禁用！
			}else{
				layer.msg('字典更新失败!');
			}
		},
		error:function(){
			layer.msg('请求失败!');
		}
	});
}
function addOne(da){
	
	//全局变量
	addOpen=layer.open({
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框
		  area: ['450px', '320px'], //宽高
		  content: $('#addOneDiv').text()
	});
	//全局变量
	$da=$(da);
	var text=$(da).text();
	var array=new Array();
	array=text.split("/");
	$('#add-content').val(array[0]);
	$('#add-partOfSpeech').val(array[1]);
	
}
function addOneSubmit(){
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/dictionary/user/insert",
		data:{content:$('#add-content').val(),
			  partOfSpeech:$('#add-partOfSpeech').val(),
			  weight:$('#add-weight').val(),
			  type:$('#add-type').val()
			 },
		success:function(data){
			if(data==1){
			layer.msg("添加成功！");
			layer.close(addOpen);//关闭！
          //  $da.attr("class",$da.attr('class').replace('addword',""));
			$da.removeAttr('onclick');//移除点击事件.
			
			$da.fadeOut();
			if($da.attr('class').indexOf('newword')==-1){
				$('#analyzersExist').append($da);
				$da.fadeIn();
				$('#renew-btn').attr('class',"layui-btn");//移除更新禁用！
			}
			}else{
				layer.msg("添加失败！");	
			}
		},
		error:function(){
			layer.msg("操作失败！");
		}

	})
}

$(function(){
	$('.fenci').each(function(){
		var text=$(this).text();
		if(text.match("/n.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n0");//名词
			$(this).attr('cixing',"n");
		}
		if(text.match("/t.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n1");//时间词
			$(this).attr('cixing',"t");
		}
		if(text.match("/s.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n2");//处所词
			$(this).attr('cixing',"s");
		}
		if(text.match("/f.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n3");//方位词
			$(this).attr('cixing',"f");
		}
		if(text.match("/v.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n4");//动词
			$(this).attr('cixing',"v");
		}
		if(text.match("/a.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n5");//形容词
			$(this).attr('cixing',"a");
		}
		if(text.match("/b.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n6");//形容词
			$(this).attr('cixing',"b");
		}
		if(text.match("/z.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n7");//状态词
			$(this).attr('cixing',"z");
		}
		if(text.match("/r.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n8");//代词
			$(this).attr('cixing',"r");
		}
		if(text.match("/m.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n9");//数词
			$(this).attr('cixing',"m");
		}
		if(text.match("/q.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n10");//量词
			$(this).attr('cixing',"q");
		}
		if(text.match("/d.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n11");//副词
			$(this).attr('cixing',"d");
		}
		if(text.match("/p.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n12");//介词
			$(this).attr('cixing',"p");
		}
		if(text.match("/c.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n13");//连词
			$(this).attr('cixing',"c");
		}
		if(text.match("/u.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n14");//助词
			$(this).attr('cixing',"u");
		}
		if(text.match("/e.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n15");//叹词
			$(this).attr('cixing',"e");
		}
		if(text.match("/y.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n16");//语气词
			$(this).attr('cixing',"y");
		}
		if(text.match("/o.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n17");//拟声词
			$(this).attr('cixing',"o");
		}
		if(text.match("/w.*")){
			$(this).attr("class",$(this).attr("class")+" "+"n18");//标点符号
			$(this).attr('cixing',"w");
		}
	});
	//--相同颜色的聚在一起.
	var array=$('#analyzers .fenci');
	array.sort(function(a,b){
		var ac=$(a).attr('cixing');
		var bc=$(b).attr('cixing');
		if(ac<bc){
			return -1;
		}else{
			return 1;
		}
	});
	$('#analyzers').empty().append(array);
});
function update(){
	$('#update').submit();
}
function del(){
	

	layer.confirm('你确定删除该专利吗？', {
	  title:"提示",	
	  anim:6,
	  btn: ['确定','取消'] //按钮
	}, function(){
		var id=$('#id').text();
		$.ajax({
			type:"post",
			url:"delete",
			data:{id:id},
			beforeSend: function(){
				layer.load(0, {shade: false});
			},
			success:function(data){
				layer.close(layer.index);
				if(data=="yes"){
					if(confirm('删除成功,是否关闭当前页面？')){
						window.opener=null;
						window.open('','_self');
						window.close();
					}
				}
			},
			error:function(){
				layer.close(layer.index);
				alert('请求失败！');
			}
		});
	}, function(){
	  layer.close(layer.index);
	});
	
	
	
}

</script>
</body>
</html>
