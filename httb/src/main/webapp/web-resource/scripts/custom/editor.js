/**
 * 创建CKEditor编辑框
 */
var createCkEditor_ht =function(inp,uploadUrl){
    CKEDITOR.replace(inp,{
    	 filebrowserImageUploadUrl:uploadUrl,
    	 height: '130px',
    	 width: '65%' ,
    	 toolbar : 
             [ [
                  'Source','-','Maximize',
                  'Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat',
                  'Bold','Italic','Underline','Strike','-',
                  'NumberedList','BulletedList','Outdent','Indent','Blockquote','-',
                  'Image','Table','HorizontalRule','Smiley','SpecialChar','Link'
             ]],
    } );
};