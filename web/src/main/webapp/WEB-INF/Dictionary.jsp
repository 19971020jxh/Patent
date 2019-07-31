<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
  <title>字典设置</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
  <style type="text/css">
  .layui-table-tool-temp{
   padding-right:15px;
  }
  </style>
</head>
<body>
<div class="layui-container" style="margin-top:15px;margin-bottom:15px;">
<div class="layui-row" >
<div class="layui-col-md6">
<form id="selectdictonary" class="layui-form" action="jsp">
 <div class="layui-form-item">
    <label class="layui-form-label">字典选择</label>
    <div class="layui-input-block">
      <input   type="radio" name="dictonary" value="user" title="关键字字典" <c:if test="${Dictionary =='user'}">checked</c:if>>
      <input   type="radio" name="dictonary" value="stopword" title="停用词字典" <c:if test="${Dictionary =='stopword'}">checked</c:if>>
    </div>
  </div>
 </form> 
</div>

<!--搜索框-->
  <div class="layui-input-block layui-col-md3 layui-col-md-offset9" style="margin-top:5px;margin-right:15px;margin-left: 190px;" >
    <input  type="text" id="search_input"  lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
       <button  type="button" class="layui-btn" lay-submit="" onclick="search()" data-type="getInfo"style="margin-top: -55px;margin-left: 300px;" >搜索</button>
  </div>
</div>
<!--表格区域-->
<table class="layui-hide" id="test" lay-filter="test" ></table>

<!--表格右侧操作按钮-->
<div class="layui-row" >
<blockquote class="layui-elem-quote">测试</blockquote>
<form class="layui-form">
<div class="layui-form-item layui-form-text">
      <textarea id="testText" name="text"  class="layui-textarea"></textarea>
</div>
<div class="layui-form-item">
      <button type="button" class="layui-btn" onclick="test()">测试</button>
  </div>
</form>
</div>
<div class="layui-row" >
<blockquote class="layui-elem-quote">分词结果</blockquote>
<textarea id="test-rs"   class="layui-textarea"></textarea>
</div>


<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!--上传按钮-->
<script type="text/html" id="toolbar" >
  <span class="layui-btn" style="margin-bottom:15px;height:42px; ">
           <button type="button" style="height:42px;" class="layui-btn" id="test3"><i class="layui-icon"></i>上传文件</button>
   </span> 
    <span class="layui-btn"  style="margin-bottom:15px;height:42px; ">
           <button type="button" style="height:42px;" class="layui-btn" onclick="addOne()">添加</button>
    </span> 
    <span id="batchtip"   style="margin-bottom:15px;height:42px; ">
    <i class="layui-icon layui-icon-help" style="margin-left:5px;margin-top:5px; font-size:14px;" ></i>      
    </span> 
    <span class="layui-btn"  style="margin-bottom:15px;height:42px; display:inline-block;float:right;">
      <a type="button" style="height:42px;" class="layui-btn" href="${DictionaryTxt}">导出</a>
    </span> 
     <span  style="margin-bottom:15px;height:42px;display:inline-block;float:right;margin-right:15px;">
      <button id="renew-btn" type="button" style="height:45px;margin-left:10px;" class="layui-btn" onclick="renew()">更新</button>
    </span> 
</script>     
<script type="text/html" id="addOneDiv" >
<div style="padding:15px;">
<form class="layui-form">
  <div class="layui-form-item">
    <label class="layui-form-label" style="text-align:left">词</label>
    <div class="layui-input-block" style="margin-left"90px;">
      <input type="text" id="add-content" lay-verify="title" autocomplete="off" placeholder="词" class="layui-input">
    </div>
  </div>
  
  
  <div class="layui-form-item">
    <label class="layui-form-label" style="text-align:left">词性</label>
    <div class="layui-input-block" style="margin-left"90px;">
      <input type="text" id="add-partOfSpeech" lay-verify="title" autocomplete="off" placeholder="词性" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label" style="text-align:left">权重</label>
    <div class="layui-input-block" style="margin-left"90px;">
      <input type="text" id="add-weight" lay-verify="title" autocomplete="off" placeholder="权重" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label" style="text-align:left">分类</label>
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
<script src="${pageContext.request.contextPath}/static/layui/layui.all.js" charset="utf-8"></script>
<script>
function test(){
	$.ajax({
		type:'post',
		url:'test',
		data:{text:$('#testText').val()},
		success:function(data){
			$('#test-rs').val(data);
		},
		error:function(){
			layer.msg('请求失败!');
		}
		
	})
}
//关闭页面时，更新
window.onbeforeunload =(function(){
	if($('#renew-btn').attr('class').indexOf('layui-btn-disabled')<0){
		renew();
	}
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
$(function(){
	$('#batchtip').mouseover(function(){
		batchTip();
	});
	$('.layui-form-radio').click(function(){
		$('#selectdictonary').submit();
		renew();//更新字典
	});
});
function batchTip(){
	layer.tips('关键字批量上传格式:<br/>词词性 权重 分类<br/> 阿/n 1 类别', '#batchtip', {
		  tips: [1, '#3595CC'],
		  time: 2000
		});
}
function addOneSubmit(){
	$.ajax({
		type:"POST",
		url:"${DictionaryInsert}",
		data:{content:$('#add-content').val(),
			  partOfSpeech:$('#add-partOfSpeech').val(),
			  weight:$('#add-weight').val(),
			  type:$('#add-type').val()
			 },
		success:function(data){
			if(data==1){
			layer.msg("添加成功！");
			layer.close(addOpen);//关闭！
			$('#renew-btn').attr('class',"layui-btn");//移除更新禁用！
		//alert($('#renew-btn').attr('class'));
			tableIns.reload({// 刷新！
    			url:'${DictionaryPage}',
    			page:{curr:1}//从第一页开始
    		});
			}else{
				layer.msg("添加失败！");	
			}
		},
		error:function(){
			layer.msg("操作失败！");
		}

	})
}
layui.use(['table','upload',  'form', 'laypage','layer'], function(){

   var form = layui.form ,
   laypage=layui.laypage,
   table = layui.table,

   upload = layui.upload,
   layer = layui.layer ,
   $ = layui.jquery
    
//表格渲染,tableIns=>是全局变量,下面搜索时，重载表格要用！
    tableIns =table.render({
    elem: '#test',
    id: 'test',
//where: { type: "all" },
    url:'${DictionaryPage}',//在此处填写地址
    toolbar: '#toolbar',
    defaultToolbar:false,
    title: '字典数据表',
    cols: [[${table}]],
    limit:10
      ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
        layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']//自定义分页布局 
        ,limits:[5,10,15]
        ,first: false //不显示首页
        ,last: false //不显示尾页
      }
    , done: function (res) {
    	
    	console.log(res); 
    }

  });
  //单元格编辑
  table.on('edit(test)', function(obj){ 
  var value = obj.value 
  //得到修改后的值 ,
  data = obj.data 
  //得到所在行所有key值 ,
  //field = obj.field; 
  //得到字段
  layui.use('jquery',function(){ 
  var $=layui.$;
  $.ajax({ 
  type: 'get', 
  url: "${Dictionaryupdate}", // ajax请求路径  
  data: { id:data.id, content:data.content,partOfSpeech:data.partOfSpeech,type:data.type,weight:data.weight}, 
  success: function(data){ 
 //alert(data);
  if(data>0){
  layer.msg('修改成功'); 
  $('#renew-btn').attr('class',"layui-btn");//移除更新禁用！
  }else{
  layer.msg('失败');
  }
  },
  error:function(){
	  layer.msg('更新失败！');
  }
  }); 
  }); 
  });


  
  //上传文件
  upload.render({
    elem: '#test3'
    ,url: '${DictionaryUpload}'
    ,accept: 'file' //普通文件
    ,done: function(res){//回调函数，后台返回信息在这接受
      //如果上传失败
    //alert(res);  
      if(res.code==0){
        return layer.msg('上传失败');
      }
      else{//alert('上传成功！')
    	  tableIns.reload({
    			url:'${DictionaryPage}',
    			page:{curr:1}//从第一页开始
    		});
    	  $('#renew-btn').attr('class',"layui-btn");//移除更新禁用！
      return layer.msg('上传成功');
      }//上传成功
    }
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    //console.log(obj)
    if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
      									 		obj.del();
                                layer.close(index);
      									
//     ajax删除 									
                    $.ajax({
                        url: "${Dictionarydelete}",
                        type: "POST",
                        data:{"id":data.id},
                   // dataType: "json",                      
                        success: function(data){
   															
                            if(data==1){
                                obj.del();
                                layer.close(index);
                                layer.msg("删除成功", {icon: 6});
                                $('#renew-btn').attr('class',"layui-btn");//移除更新禁用！
                            }else{
                                layer.msg("删除失败", {icon: 5});
                            }
                        },
                        error:function(){
                        	layer.msg("操作失败!");
                        }

                    });//${DictionaryPage}
                });
            }
        
    
  });
  
    });
//搜索
function search(){
	tableIns.reload({
		url:'${DictionaryPage}',
		where:{searchWhat:$('#search_input').val()},
		page:{
			curr:1//从第一页开始！
		}
	});
} 
function addOne(){
	//全局变量
	addOpen=layer.open({
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框
		  area: ['450px', '320px'], //宽高
		  content: $('#addOneDiv').text()
	});
	$('#renew-btn').attr('class',"layui-btn");//移除更新禁用！
}
</script>
</div>
<div id="nowDictionary" style="display:none;" >${Dictionary}</div>
</body>

</html>

