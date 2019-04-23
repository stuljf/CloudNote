ClassicEditor
        .create( document.querySelector( '#editor' ) ,{
        	 toolbar: ["heading", "|", "undo", "redo", "bold", "italic", "blockQuote", "imageUpload", "link", "numberedList", "bulletedList"],
        	 extraPlugins: [ ImgUploadAdapterPlugin ],
        })
        .then( editor => {
			window.editor = editor;
		} )
        .catch( error => {
            console.error( error );
        } );
   
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
            xhr.open( 'POST', '${pageContext.request.contextPath }/note/image', true );
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
    
    function ImgUploadAdapterPlugin( editor ) {
        editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
            return new ImgUploadAdapter( loader );
        };
    }