<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <title>专利搜索.</title>
  <link rel="stylesheet" href="static/layui/css/layui.css">
  <style>
    body{margin: 10px;}
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
    .layui-inline{ border: none!important; }
    .layui-icon-edit{
    	display: none!important;    
    }
    .layui-icon-delete,.layui-icon-add-1{
    	border: 1px solid #999999;
    }
    .layui-icon-delete{
    	margin-left: -50px;
    }
    .layui-table-cell{
    height:auto;
    white-space: normal;
    }
    .layui-table td, .layui-table th, .layui-table-col-set, .layui-table-fixed-r, .layui-table-grid-down, .layui-table-header, .layui-table-page, .layui-table-tips-main, .layui-table-tool, .layui-table-total, .layui-table-view, .layui-table[lay-skin=line], .layui-table[lay-skin=row]{
    border-width:0px;
    border-style:none;
    border-color:transparent;
    }
    .layui-table-header{
    display:none;
    }
     tr{
     display:block;
     margin-bottom:15px;
     margin-top:15px;
    }
    .badge {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #777;
    border-radius: 10px;
    }
    .layui-inline{
    margin-top:5px;
    margin-bootom:5px;
    }
  </style>
</head>
<body>
<div class="layui-container" style="margin-top:45px;padding-bottom:15px;">
<div class="layui-row">
<div class="layui-input-inline">
<form class="layui-form">
 <input  type="radio" name="searcht" value="mohu" title="模糊搜索" checked>
 <input  type="radio" name="searcht" value="jingque" title="精确搜索" >
</form>
</div>   
<div id="mohu" class="layui-input-inline">   
<div class="layui-input-inline">
<input id="what" type="text" placeholder="为空查所有" class="layui-input" />
</div>
<div class="layui-input-inline">
<button onclick="search2()" class="layui-btn layui-btn-normal">🔍<span>搜索</span></button>
</div>
</div>
<div id="jingquebtn" class="layui-input-inline" style="display:none;">
<button onclick="jingquesearch()" class="layui-btn layui-btn-normal">🔍<span>搜索</span></button>
</div>
<c:if test="${user.isroot }">
<div class="layui-input-inline" style="float:right;margin-left:4px;">
<a class="layui-btn  layui-btn-normal" href="admin" >🔐<span>管理员</span></a>
</div>
</c:if>
<c:if test="${user.isroot==false }">
<div class="layui-input-inline" style="float:right;margin-left:4px;">
<a class="layui-btn  layui-btn-danger" href="admin/out" >⬅️<span>退出</span></a>
</div>
</c:if>
<div class="layui-input-inline" style="float:right;margin-left:4px;">
<a class="layui-btn  layui-btn-warm" href="filepage" >⬆️<span>上传</span></a>
</div>
<div class="layui-input-inline" style="float:right;">
<a class="layui-btn" href="insertpage" >➕<span>添加</span></a>
</div>

</div>
<div id="jingque" class="layui-row" style="display:none;margin-right:15px;margin-left:-42px;padding-top:15px;">
<span style="margin-left:55px;display:block;">精确查询,必须正确对应相应字段！</span>
<form class="layui-form">
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">申请号</label>
    <div class="layui-input-block">
      <input type="text" name="requestNumber" required  lay-verify="required" placeholder="申请号" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">发明名称</label>
    <div class="layui-input-block">
      <input type="text" name="createThing" required  lay-verify="required" placeholder="发明名称" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">公开号</label>
    <div class="layui-input-block">
      <input type="text" name="publicationNumber" required  lay-verify="required" placeholder="公开号" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">IPC分类号</label>
    <div class="layui-input-block">
      <input type="text" name="ipc" required  lay-verify="required" placeholder="IPC分类号" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">发明人</label>
    <div class="layui-input-block">
      <input type="text" name="createPeople" required  lay-verify="required" placeholder="发明人" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">申请日</label>
    <div class="layui-input-block">
      <input id="dateinput1"  type="text" name="filingDate" required  lay-verify="required" placeholder="申请日" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">公开日</label>
    <div class="layui-input-block">
      <input id="dateinput2"  type="text" name="publicationDate" required  lay-verify="required" placeholder="公开日" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">申请人</label>
    <div class="layui-input-block">
      <input type="text" name="proposer" required  lay-verify="required" placeholder="申请人" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">优先权号</label>
    <div class="layui-input-block">
      <input type="text" name="priorityNumber" required  lay-verify="required" placeholder="优先权号" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-inline layui-col-md4">
    <label class="layui-form-label">专利类别</label>
    <div class="layui-input-block">
      <select   name="patentType" >
        <option value=""></option>
         <c:forEach items="${patentType }" var="type" >
          <option value="${type['patentClass']}">${type['patentClass']}</option>
         </c:forEach>    
      </select>
    </div>
</div>
</form>
</div>
<div id="zuijintianjia" class="layui-row" style="margin:15px;"><span class="badge">最近添加</span></div>
<!--表格信息渲染-->
<table class="layui-hide" id="searchResult">   
</table>


</div>
</body>
 <!--右侧操作工具-->
<script type="text/html" id="barTool">
<button class="layui-btn layui-btn-danger layui-btn-xs" lay-event="detail" onclick="patentpage(this)" >详情</button>
</script>
<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-primary layui-btn-sm" onclick="getCheckData()" style="background-color: #f2f2f2;" ><i class="layui-icon layui-icon-export"></i></button>
  </div>
</script>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
<script src="static/layui/layui.all.js"></script>
<script>
laypage = layui.laypage; //分页
layer = layui.layer ;//弹层
table = layui.table ;//表格
upload = layui.upload ;//上传
element = layui.element ;//元素操作
function searcht(){
	var c= $('input[name="searcht"]:checked').attr('value');
	if(c=='mohu'){
		$('#mohu').fadeIn();
		$('#jingque').fadeOut();
		$('#jingquebtn').hide();
	}
	if(c=='jingque'){
		$('#jingque').fadeIn();
		$('#mohu').hide();
		$('#jingquebtn').show();
	}
}
function jingquesearch(){
	searchtext="serachform?"+
	"requestNumber="+$('input[name="requestNumber"]').val()+"&"+
	"createThing="+$('input[name="createThing"]').val()+"&"+
	"publicationNumber="+$('input[name="publicationNumber"]').val()+"&"+
	"ipc="+$('input[name="ipc"]').val()+"&"+
	"createPeople="+$('input[name="createPeople"]').val()+"&"+
	"filingDate="+$('input[name="filingDate"]').val()+"&"+
	"publicationDate="+$('input[name="publicationDate"]').val()+"&"+
	"proposer="+$('input[name="proposer"]').val()+"&"+
	"priorityNumber="+$('input[name="priorityNumber"]').val()+"&"+
	"patentType="+$('select[name="patentType"]').val();
	search();
}
function patentpage(obj){
	var id=$(obj).parents('td').prev().find('.patent-div').attr('patent-id');
	window.open('patent?id='+id);
}
$(function(){
	$('.layui-form-radio').click(function(){
		searcht();
	});
	layui.use('laydate', function(){
		  var laydate = layui.laydate;
		  
		  //时间表单控件
		  laydate.render({
		    elem:'#dateinput1'
		  });
		  laydate.render({
			    elem:'#dateinput2'
			  });
	});		  
	searchtext="search?what=null";
	search();
});
function search2(){
	searchtext=$('#what').val() //数据接口
	searchtext="search?what="+searchtext;
	search();
}
function getCheckData(){
	var data=table.checkStatus('searchResult').data;
	var array=new Array();
	for(var i in data){
		array.push($('.patent-div').eq(i).attr('patent-id'));
	}
	if(array.length>=1){
	window.open("todata?array="+array);
	}else{
		layer.msg('尚未选中元素!');
	}
	/* $.ajax({
		type:'post',
		url:'todata',
		data:{array:array},
		
	}); */
}
function search(){
	layer.load(0, {shade: false});
	layui.use([ 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
		  //执行一个 table 实例
		  table.render({
		      elem: '#searchResult' //表格id
		     ,url: searchtext
		     ,limits:[5,10,15]
		     ,toolbar: '#toolbar'
		     ,defaultToolbar:[]
		     ,limit:5
		     ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
		       layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
		      ,groups: 4 //只显示 1 个连续页码
		      ,first: false //不显示首页
		      ,last: false //不显示尾页
		      
		    }
		    ,done: function(res, curr, count){
		    	layer.close(layer.index);
		        //如果是异步请求数据方式，res即为你接口返回的信息。
		        $('#zuijintianjia').empty();
		    }
		    ,cols: [[ //表头
		      {type:'checkbox'}
			 ,{field: 'div', title: '简介', width:"85%"}
		     ,{fixed: 'right',title:'', width: 165, align:'center', toolbar: '#barTool', width:"10%"}//右侧查看，编辑操作
		    ]]
		  });
	

		}); 
	$('.layui-table-view').attr("style","boder-style:none");
}

</script>
</html>
