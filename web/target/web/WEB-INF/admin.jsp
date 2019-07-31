<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>后台管理</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/font-awesome.css">
		<link rel="stylesheet" href="static/layui/css/layui.css"  media="all">
		<link href="https://cdn.bootcss.com/jstree/3.3.8/themes/default/style.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/alert/alertify.core.css" >
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/alert/alertify.bootstrap.css" >
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/bulma/bulma.min.css">
        <link href="https://cdn.bootcss.com/font-awesome/5.10.0-12/css/all.css" rel="stylesheet">
	</head>
	<body>
		<div class="columns"  >
		<div class="column is-one-fifth"></div> <!-- 占位div -->
			<div class="column is-one-fifth" style="position: fixed;"   >
				<nav class="panel" style="margin: 15px;">
				  <p class="panel-heading">
				    后台管理
				  </p>
				  <div class="panel-block">
				    <small>上次登录:</small>
				    <span>${user.lastTime }</span>
				  </div>
				  <a class="panel-block is-active" href="#1"  onclick="a_active(this)">
				    <span class="panel-icon">
				    <!--   <i class="fas  fa-key"></i> -->
				    </span>
				    账号信息
				  </a>
				  <a class="panel-block" href="#2" onclick="a_active(this)">
				    <span class="panel-icon">
				     <!--  <i class="fa fa-user-circle-o" aria-hidden="true"></i> -->
				    </span>
				    管理员信息
				  </a>
				  <a class="panel-block" href="#3" onclick="a_active(this)">
				    <span class="panel-icon">
				      <!-- <i class="fas fa-free-code-camp"></i> -->
				    </span>
				    专利信息
				  </a>
				   <a class="panel-block" onclick="a_active(this)">
				    <span class="panel-icon">
				      <!-- <i class="fas fa-book"></i> -->
				    </span>
				    字典管理
				  </a>
				  <a class="panel-block" href="#4" onclick="a_active(this)">
				    <span class="panel-icon">
				      <!-- <i class="fas  fa-bar-chart"></i> -->
				    </span>
				    类别概览
				  </a>
				  <a class="panel-block" href="#5" onclick="a_active(this)">
				    <span class="panel-icon">
				      <!-- <i class="fas fa-user-o"></i> -->
				    </span>
				    用户管理
				  </a>
				  <a class="panel-block"  onclick="a_active(this)" href="<%=request.getContextPath()%>/searchpage" >
				    <span class="panel-icon">
				     <!--  <i class="fas fa-info"></i> -->
				    </span>
				    主界面
				  </a>
				  <div class="panel-block" >
				    <a class="button is-link is-outlined is-fullwidth" href="admin/out" >
				      退出登录
				    </a>
				  </div>
				</nav>
				<!-- --------------------------------------- -->
			</div>
			<div class="column is-four-fifth" style="margin: 15px;"  >
				<!-- 账号管理------------------------>
				<div id="one" >
					<article class="message is-dark" style="width: 48%;display: inline-block;">
					  <div class="message-header">
					    <p>账户信息<a name="1" ></a></p>
					    <button class="delete" aria-label="delete"></button>
					  </div>
					  <div class="message-body">
						   <div class="field">
							  <div class="control has-icons-left has-icons-right">
							    <input id="admin-name" class="input is-success" type="text" placeholder="Text input" value="${user.name }">
							    <span class="icon is-small is-left">
							      <i class="fas fa-user"></i>
							    </span>
							    <span class="icon is-small is-right">
							      <i class="fas fa-check"></i>
							    </span>
							  </div>
							     <div class="field" style="margin-top: 4px;">
								  <p class="control has-icons-left">
								    <input id="admin-password" class="input" onblur="hiddenpass(this)" onfocus="seePassword(this)" type="password" placeholder="Password" value="${user.password }" >
								    <span class="icon is-small is-left">
								      <i class="fas fa-lock"></i>
								    </span>
								  </p>
								</div>
								<a class="button" onclick="updateRoot()" >确定</a>
								<a class="button is-pulled-right " onclick="deleteRoot()" >注销</a>
						</div>
					  </div>
					</article>
					<article class="message is-dark" style="width: 48%;margin-left: 2%; display: inline-block;">
					  <div class="message-header">
					    <p>添加管理</p>
					    <button class="delete" aria-label="delete"></button>
					  </div>
					  <div class="message-body">
						   <div class="field">
							  <div class="control has-icons-left has-icons-right">
							    <input id="add-input-name" class="input is-success" type="text" placeholder="Your name">
							    <span class="icon is-small is-left">
							      <i class="fas fa-user"></i>
							    </span>
							    <span class="icon is-small is-right">
							      <i class="fas fa-check"></i>
							    </span>
							  </div>
							     <div class="field" style="margin-top: 4px;">
								  <p class="control has-icons-left">
								    <input id="add-input-pass" class="input" onblur="hiddenpass(this)" onfocus="seePassword(this)" type="password" placeholder="Password">
								    <span class="icon is-small is-left">
								      <i class="fas fa-lock"></i>
								    </span>
								  </p>
								</div>
								<a class="button" onclick="addRoot(this)" >确定</a>
								<a class="button is-pulled-right " onclick="$('#add-input-name').val('');$('#add-input-pass').val('');">清除</a>
						</div>
					  </div>
					</article>
					<article id="m151" class="message is-primary"    >
					  <div class="message-header">
					    <p>管理员信息<a name="2" ></a></p>
					    <button class="delete" aria-label="delete"></button>
					  </div>
					  <div class="message-body">
						   <table class="table" style="width:100%;">
							  <thead>
							    <tr>
							      <th><abbr >ID</abbr></th>
							      <th>Name</th>
							      <th><abbr >最后登录</abbr></th>
							    </tr>
							  </thead>
							  <tfoot>
							    <tr>
							      <th><abbr >ID</abbr></th>
							      <th>Name</th>
							      <th><abbr >最后登录</abbr></th>
							    </tr>
							  </tfoot>
							  <tbody id="root_tbody" >
							  
							  </tbody>
							</table>
					  </div>
					</article>
				</div>
				<!-- 账号管理--------------------------- -->
				<!-- 专利管理--------------- -->
				<div id="two">
                  <!-- 
	                  专利数量.
	                  patent_type,人工标注分类
	                  ipc分类 
                  -->
                  <article class="message is-danger">
					  <div class="message-header">
					    <p>专利概览<a name="3" ></a></p>
					    <button class="delete" aria-label="delete"></button>
					  </div>
					  <div class="message-body">
						 <!-- 
						 专利数量
						 ipc分类种数
						 使用数量 
						 -->
						    <div class="field is-grouped is-grouped-multiline">
							  <div class="control">
							    <div class="tags has-addons">
							      <span class="tag is-dark">专利数量</span>
							      <span class="tag is-info">${_view.patentNumber }</span>
							    </div>
							  </div>
							   <div class="control">
							    <div class="tags has-addons">
							      <span class="tag is-dark">ipc种数</span>
							      <span class="tag is-success">${_view.ipcNumber }</span>
							    </div>
							  </div>
							   <div class="control">
							    <div class="tags has-addons">
							      <span class="tag is-dark">使用者</span>
							      <span class="tag is-primary">${_view.userNumber }</span>
							    </div>
							  </div>
							  <div class="control">
							    <div class="tags has-addons">
							      <span class="tag is-dark">下载次数</span>
							      <span class="tag is-primary">${_view.downCounts }</span>
							    </div>
							  </div>
							  <div class="control">
							    <div class="tags has-addons">
							      <span class="tag is-dark">人工标注分类</span>
							      <span class="tag is-primary">${_view.patentTypes }</span>
							    </div>
							  </div>
							</div>
					  </div>
					</article>
					<article class="message is-info">
					  <div class="message-header">
					    <p>人工标注分类<a name="4" ></a></p>
					    <button class="delete" aria-label="delete"></button>
					  </div>
					  <div class="message-body" style="height:400px;">
						<div id="rg-echarts-container" style="height: 100%"></div>
					  </div>
					</article>
					<article id="m245" class="message is-success">
					  <div class="message-header">
					    <p>ipc分类</p>
					    <button class="delete" aria-label="delete"></button>
					  </div>
					  <div class="message-body">
						<div id="jsTree" ></div>
					  </div>
					</article>
				</div>
				<!-- 专利管理 ---------------->
				<!-- 字典管理 ----------------------------->
				<div id="three">
<!-- --字典管理，要么iframe-要么跳转页面 -->
				</div>
				<!-- 字典管理 ------------------------->
				<!-- 用户管理 ----------------------------->
                <div id="four">
                     <article class="message is-warning">
					  <div class="message-header">
					    <p>用户管理<a name="5"></a></p>
					    <button class="delete" aria-label="delete"></button>
					  </div>
					  <div class="message-body">
						<!-- --layui表格------- -->
						<table class="layui-hide" id="user-table"></table>
					  </div>
					</article>
                </div>
				<!-- 用户管理-------------------->

			</div>
		</div>
		<div id="echartsrg" style="display:none" >${echartsrg }</div>
		<div id="jsTree-v" style="display:none" >${jsTree }</div>
		<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
		<script src="static/layui/layui.all.js"></script>
		<script src="https://cdn.bootcss.com/jstree/3.3.8/jstree.min.js"></script>
		<script src="https://cdn.bootcss.com/echarts/4.2.1-rc1/echarts.js"></script>
		<script  src="${pageContext.request.contextPath}/static/alert/alertify.min.js"></script>
		<script type="text/javascript">
		function hiddenpass(o){
				o.type="password";
		}
		function  seePassword(o){
				o.type="text";
		}
		function  a_active(o){
			$('.panel-block').not(o).attr('class','panel-block');
			$(o).attr('class','panel-block is-active');
		}
		$(function(){
			$('#jsTree').jstree({ 'core' : {
			    'data' : $.parseJSON($('#jsTree-v').text())
			} });
			if($('#m151').height()>375){
				$('#m151').attr('style','max-height:375px;overflow-y:scroll');
			}
			
			if($('#m245').height()>800){
				$('#m245').attr('style','max-height:800px;overflow-y:scroll');
			}
			$.ajax({
				type:"post",
				url:"admin/root/table",
				dataType:'json',
				success:function(data){
					 
				   var html="";
				   var users=data.data;
				   for(var index in users){
					   if(users[index].lastTime==undefined){
						   users[index].lastTime="暂未登录";
					   }
					   html=html+"<tr><th>"+users[index].id+"</th><td>"+users[index].name+"</td><td>"+users[index].lastTime+"</td></tr>";
				   }
				   $('#root_tbody').append(html);
				}
			});
		});
         function addRoot(btn){
        	 var name=$('#add-input-name').val();
        	 var pass=$('#add-input-pass').val();
        	 if(name.length<1||pass.length<3){
        		 alertify.error('用户名或密码过短！');
        		 return false;
        	 }
        	 $.ajax({
        		type:'post',
        		url:'admin/insert',
        		data:{name:name,pass:pass},
        		beforeSend:function(){
        			$(btn).attr('class','button is-loading');
        		},
        		success:function(str){
        			$(btn).attr('class','button');
        			alertify.log(str);
        		},
        		error:function(){
        			$(btn).attr('class','button');
        			alertify.error('出现未知错误！');
        		}
        	 });
         }
         function deleteRoot(){
        	 alertify.confirm("你确定注销自己的管理员账号吗？", function (e) {
        	    if (e) {
        	    	$.ajax({
        	    		type:'post',
        	    		url:'admin/delete',
        	    		data:{name:$('#admin-name').val(),pass:$('#admin-password').val()},
        	    		beforeSend:function(){
        	    			alertify.log('执行注销中...');
        	    		},
        	    		success:function(data){
        	    			if(data=="1"){
        	    				alertify.log('注销成功！');	
            	    			$(location).attr('href','${pageContext.request.contextPath}/login/page');
        	    			}
        	    			
        	    		},
        	    		error:function(){
        	    			alertify.error('出现未知错误！');
        	    		}
        	    	})
        	    	
        	    } else {
        	    	alertify.log('你已撤回！');	
        	    }
        	});
         }
         function updateRoot(){
            $.ajax({
	    		type:'post',
	    		url:'admin/update',
	    		data:{name:$('#admin-name').val(),pass:$('#admin-password').val()},
	    		beforeSend:function(){
	    			alertify.log('执行修改中...');
	    		},
	    		success:function(data){
	    			if(data=="1"){
	    				alertify.log('修改成功！');	
	    			}
	    			
	    		},
	    		error:function(){
	    			alertify.error('出现未知错误！');
	    		}
	    	})
         }
		</script>
		<script type="text/javascript">
		var dom = document.getElementById("rg-echarts-container");
		var myChart = echarts.init(dom);
		var app = {};
		option = null;
		option = {
		   /*  title : {
		        text: '人工标注分类',
		        subtext: '',
		        x:'center'
		    }, */
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		   
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true,
		                type: ['pie', 'funnel']
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'标记分类',
		            type:'pie',
		            radius : [30, 110],
		            center : ['45%', '50%'],
		            roseType : 'area',
		            data:$.parseJSON($('#echartsrg').text())
		        }
		    ]
		};
		;
		if (option && typeof option === "object") {
		    myChart.setOption(option, true);
		}
		</script>
		<script type="text/html" id="switchTpl">
  <!-- 这里的 checked 的状态只是演示 -->
  <input type="checkbox"  lay-skin="switch" lay-text="禁|能" lay-filter="canUse" value="{{d.id}}" name="{{d.name}}" {{d.canUse==false ? 'checked':''}} >
</script>
		<script>
layui.use('table', function(){
  var table = layui.table
  ,form = layui.form;
  table.render({
     elem: '#user-table'
    ,url:'${pageContext.request.contextPath}/admin/user/table'
   /*  ,cellMinWidth: 80 */
    ,cols: [[
/*        {type:'numbers'} */
/*       ,{type: 'checkbox'} */
      {field:'id', title:'ID'}
      ,{field:'name', title:'用户名'}
      ,{field:'password', title:'密码'}
      ,{field:'lastTime', title:'最后登录'}
      ,{field:'uploadCount', title: '上传次数'}
      ,{field:'downCount', title:'下载次数'}
      ,{field:'canUse', title:'能否使用', templet: '#switchTpl'}
      
    ]]
    ,page: true
  });
  
  //监听性别操作
  form.on('switch(canUse)', function(obj){
 // console.log(this.name+" "+this.value+" "+this)
   // layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
   if(obj.elem.checked){
	   alertify.error('禁用了'+this.name);
	   
   }else{
	   alertify.success('授权了'+this.name);
   }
   $.ajax({
	   type:"post",
	   url:"admin/user/shiro",
	   data:{id:this.value}
   })
   
   
  });
  
 
});
</script>
	</body>
	</html>