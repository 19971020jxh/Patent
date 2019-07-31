<%@ page language="java" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>专利处理</title>
    <link rel="stylesheet" href="static/layui/css/layui.css"  media="all">
    <style>
        html * {
            margin: 0;
            padding: 0;
        }
        .wrapper {
            padding: 20px;
        }
    </style>
</head>
<body>
<div class="layui-container" style=margin-top:45px;">  
<!-- <form action="file" enctype="multipart/form-data" method="post">
    <table>
       
        <tr>
            <td>请选择文件:</td>
            <td><input type="file" name="file"></td>
        </tr>
        <tr>
            <td><input type="submit" value="上传"></td>
        </tr>
    </table>
</form> -->

<blockquote class="layui-elem-quote">
压缩包上传
<span id="zipTip" ><i class="layui-icon layui-icon-help"></i> </span>
</blockquote>
<div class="layui-upload" style="margin-top:15px;">
<div>
<form class="layui-form">
 <div class="layui-form-item">
    <label style="width:auto;padding:9px 0px;" class="layui-form-label">上传方式</label>
    <div class="layui-input-block" style="margin-left: 70px;">
      <input type="radio" name="radio" value="files" title="批量" checked>
      <input type="radio" name="radio" value="file" title="单个" >
    </div>
  </div>
</form>  
</div>
  <button type="button" class="layui-btn layui-btn-normal" id="upfile">选择文件</button>
  <button type="button" class="layui-btn" id="submit" onclick="layer.load(0, {shade: false});" >开始上传</button>
  <textarea id="rs-info" style="display:block;display: block;width: 100%;border-color: transparent;padding: 15px;">
  
  </textarea>
</div> 

</div>
<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js"></script>
<script src="static/layui/layui.all.js"></script>
<script>
$.fn.extend({
    txtaAutoHeight: function () {
        return this.each(function () {
            var $this = $(this);
            if (!$this.attr('initAttrH')) {
                $this.attr('initAttrH', $this.outerHeight());
            }
            setAutoHeight(this).on('input', function () {
                setAutoHeight(this);
            });
        });
        function setAutoHeight(elem) {
            var $obj = $(elem);
            return $obj.css({ height: $obj.attr('initAttrH'), 'overflow-y': 'hidden' }).height(elem.scrollHeight);
        }
    }
});
$(function(){
	$('#zipTip').hover(function(){
		layer.tips('压缩包格式目前支持zip,<br/>专利.zip:<br/>---专利1.zip<br/>---专利2.zip<br/>---.。。。', '#zipTip');
	});
});
layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
	  //选完文件后不自动上传
	  upload.render({
	    elem: '#upfile'
	    ,url: 'file'
	    ,auto: false
	    ,accept:'file'
	    ,data:{radio: function(){
	    	           return  $("input[name='radio']:checked").val();
	                           }
	    	  
	          }
	    ,bindAction: '#submit'
	    ,done: function(data){
	    	layer.close(layer.index);
	    	$('#rs-info').empty();
	    	$('#rs-info').text(JSON.stringify(data,null,4));
	    	$('#rs-info').txtaAutoHeight();
	    	//$("#rs-info").style.height = 'auto';
	    	//$("#rs-info").style.posHeight=textarea.scrollHeight; 
	    	//$('#rs-info').css('heigh', 'auto');
	    	//$('#rs-info').css('scrollTop' , '0'); //防抖动
	    	//$('#rs-info').css('height',$('#rs-info').css('scrollHeight') + 'px');	
	    }
	   ,error: function(index, upload){
		    layer.close(layer.index);
		    $('#rs-info').empty();
	    	$('#rs-info').text(JSON.stringify(data,null,4));
	    	$('#rs-info').txtaAutoHeight();
	    	//$("#rs-info").style.height = 'auto';
	    	//$("#rs-info").style.posHeight=textarea.scrollHeight; 
	    	//$('#rs-info').css('heigh', 'auto');
	    	//$('#rs-info').css('scrollTop' , '0'); //防抖动
	    	//$('#rs-info').css('height',$('#rs-info').css('scrollHeight') + 'px');	
	   }
	  });
	  
});

</script>
</body>
</html>
