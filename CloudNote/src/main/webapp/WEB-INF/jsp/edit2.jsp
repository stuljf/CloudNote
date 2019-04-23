<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath }/sources/js/ckeditor.js"></script>
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
</head>
<body>
	<form id="note_form" action="">
		<input name="nid" value="${note.id }" style="display: none;" />
		<textarea name="content" id="editor" cols="" rows="">
			${content }
		</textarea>
	</form>
	<script type="text/javascript">
	ClassicEditor.create( document.querySelector( '#editor' ) ,{
		 toolbar: [ "undo", "redo", "bold", "italic", "blockQuote", "imageUpload", "link", "numberedList", "bulletedList","heading" ],
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
									//$("#msg").html("已同步，"+(new Date()).toLocaleTimeString());
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
	$(document).ready(function() {
		editor.model.document.on('change:data',() => {
			if(newsync==oldsync){
				newsync+=1;
				console.log("c:"+newsync);
			}
		} );
		update();
	});
	</script>
</body>
</html>