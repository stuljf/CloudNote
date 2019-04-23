<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp"%>

<tmp:edit title="云笔记">
	<jsp:body>
<script src="${pageContext.request.contextPath }/sources/js/ckeditor.js"
			type="text/javascript"></script>
<style type="text/css">
.ck-content {
	min-height: 500px;
}

#share {
	position: fixed;
	left: 0px;
	top: 0px;
	border-right: 1px solid rgba(0, 0, 0, 0.4);
	background-color: #f5f6f7;
	width: 400px;
	z-index: 5;
	display: none;
}
</style>
<div id="share">
<div style="margin:10px;">
	<button class="btn btn-info form-control" type="button" data-toggle="modal" data-target="#shareModal">邀请他人一起协作</button>
	</div>
	<div id="share_user"></div>
</div>
<div class="container">
<div class="row">
<div class="col-md-10 col-md-offset-1">
	<div>
		<input id="note-title" class="form-control" type="text" name="title"
							placeholder="请输入标题" value="${note.title }" />
		<button id="return_btn" class="btn btn-info pull-right" type="button"
							style="margin: 10px">返回</button>
		<button id="share_btn" class="btn btn-default pull-right"
							type="button" style="margin: 10px">共享</button>
	</div>
	
	<span id="msg"></span>
	<form id="note_form" action="">
		<input name="nid" value="${note.id }" style="display: none;" />
		<textarea name="content" id="editor" cols="" rows="">
			${content }
		</textarea>
	</form>
</div>
</div>
</div>
<!-- 分享模态框 -->
<div class="modal fade" id="shareModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">请选择好友</h4>
            </div>
            <div class="modal-body">
            	<div id="share_tip" class="alert alert-warning" style="display:none;"></div>
            	<form id="share_form">
	            	<input name="nid" style="display:none;" value="${note.id }"/>
	            	<select id="share_select" name="uid" class="form-control"></select>
            		<label class="radio-inline">
						<input type="radio" name="privacy" value="0"> 只读
					</label>
					<label class="radio-inline">
						<input type="radio" name="privacy" value="1" checked> 可编辑
					</label>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="share()">共享</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script>
ClassicEditor.create( document.querySelector( '#editor' ) ,{
	 toolbar: ["heading", "|", "undo", "redo", "bold", "italic", "blockQuote", "imageUpload", "link", "numberedList", "bulletedList"],
	 extraPlugins: [ ImgUploadAdapterPlugin ]
}).then( editor => {
	window.editor=editor;
}).catch( error => {
    console.error( error );
});
//文件上传适配器
class ImgUploadAdapter {
constructor( loader ) {
    this.loader = loader;
}
upload() {
    return this.loader.file
        .then( file => new Promise( ( resolve, reject ) => {
            this._initRequest();
            this._initListeners( resolve, reject, file );
            this._sendRequest( file );
        } ) );
}
abort() {
    
}
_initRequest() {
    const xhr = this.xhr = new XMLHttpRequest();
    xhr.open( 'POST', '${pageContext.request.contextPath }/edit/image', true );
    xhr.responseType = 'json';
}
_initListeners( resolve, reject, file ) {
    const xhr = this.xhr;
    const loader = this.loader;
    const errorText = "Couldn't upload file";

    xhr.addEventListener( 'error', () => reject( errorText ) );
    xhr.addEventListener( 'abort', () => reject() );
    xhr.addEventListener( 'load', () => {
        const response = xhr.response;
        if ( response ) {
        	if ( response.status != 200 ) {
            	return reject( response.msg );
        	}
        }
        else{
        	return reject(errorText);
        }
        resolve( {
            default: response.data
        } );
    } );
}
_sendRequest( file ) {
    const data = new FormData();
    data.append( 'upload', file );
    console.log(data);
    console.log(file);
    this.xhr.send( data );
}
}
//加载文件上传适配器
function ImgUploadAdapterPlugin( editor ) {
editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
    return new ImgUploadAdapter( loader );
};
}

var newsync=${note.sync};
var oldsync=${note.sync};
var timeout;
//同步数据
function update(){
	var id=${note.id };
	//从服务器获取最新的同步标记
	$.get("${pageContext.request.contextPath}/edit/sync?id="+id,
		function(data){
			//判断远程笔记版本是否大于本地
			if(data.data>oldsync){
				//数据同步到本地
				$.get("${pageContext.request.contextPath}/edit/content?id="+id,	
					function(content){
						editor.setData(content.data);
						//更新同步标记
						oldsync=newsync=data.data;
					});
			}else{
				//判断本地是否对笔记进行过修改
				if(data.data<newsync){
					//数据同步到服务器
					editor.updateSourceElement()
					$.post("${pageContext.request.contextPath }/edit/save",$("#note_form").serialize(),
						function(result) {
							if(result.status==200){
								$("#msg").html("已同步，"+(new Date()).toLocaleTimeString());
								//更新同步标记
								oldsync=newsync;
							}
						});
				}
			}
		}
	);
	//每秒钟定时检测
	timeout=setTimeout("update()",1000);
}
//共享笔记
function share(){
	$.get("${pageContext.request.contextPath}/user/share?"+$("#share_form").serialize(),	
			function(data){
				if(data.status){
					$("#shareModal").modal('hide');
					shareUser();
				}else{
					$("#share_tip").html(data.msg);
					$("#share_tip").show();
				}
			});
}
//获取共享用户
function shareUser(){
	$.get("${pageContext.request.contextPath}/user/shareUser?nid="+${note.id },	
			function(content){
				$("#share_user").html(content);
			});
}
//修改用户权限
function privacy(uid,select){
	var a=$(select).val();
	if(a!=2){
		$.get("${pageContext.request.contextPath}/user/change?nid="+${note.id }+"&uid="+uid+"&privacy="+a);
	}else{
		$.get("${pageContext.request.contextPath}/user/cancel?nid="+${note.id }+"&uid="+uid,	
				function(data){
					if(data.status==200){
						shareUser();
					}
				});
	}
}

$(document).ready(function() {
	var h = $(window).height();
	$("#share").css("height", h);

	$("#note-title").blur(function(){
		var title=$("#note-title").val();
		if(title!=null){
			$.get("${pageContext.request.contextPath}/note/rename?id="+${note.id }+"&title="+title);
		}
	});
	
	$("#share_btn").click(function() {
		if($("#share").is(":hidden")){
			shareUser();
			$("#share").fadeIn();
		} else{
			$("#share").fadeOut();
		}
	})

	$("#return_btn").click(function() {
		clearTimeout(timeout);
		editor.updateSourceElement()
		$.post("${pageContext.request.contextPath }/edit/save",
			$("#note_form").serialize());
		window.location="${pageContext.request.contextPath}/note/main";
	})
	
	editor.model.document.on('change:data',() => {
		if(newsync==oldsync){
			newsync+=1;
			console.log("c:"+newsync);
		}
	} );
	
	$('#shareModal').on('show.bs.modal', function () {
		$("#share_tip").hide();
		$.get("${pageContext.request.contextPath}/user/friend2",function(data){
			if(data.status==200){
				var users=data.data;
				$("#share_select").empty();
				for(user in users){
					var id=users[user].id;
					var name=users[user].name;
					$("#share_select").append("<option value='"+id+"'>"+name+"</option>");
				}
			}
		});
	});
	
	update();
})
</script>
</jsp:body>
</tmp:edit>