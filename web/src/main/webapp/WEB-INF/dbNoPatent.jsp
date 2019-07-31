<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 当存在，该专利的索引，但该专利已经在数据库被删除时，显示该页面 -->
<title>出错了。。。！</title>
<link rel="stylesheet" href="static/layui/css/layui.css">
</head>
<body>
<div id="id" style="display:none;">${id }</div>
<form id="update" action="updatepage" method="post">
<input type="hidden" name="id" value="${id }">
</form>  
<script src="http://www.jq22.com/jquery/1.11.1/jquery.min.js"></script>
<script src="static/layui/layui.all.js"></script>
<script type="text/javascript">
$(function(){
	
	layer.confirm('该专利信息,在数据库中并不存在,是否删除该专利索引？', {
		  title:"提示",
		  btn: ['删除','添加'] //按钮
		}, function(){
		var id=$('#id').text();
		  //删除
		  $.ajax({
			  type:"post",
			  url:"deleteOfIndex",
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
		  //添加
			$('#update').submit();
		});
});
</script>
</body>
</html>