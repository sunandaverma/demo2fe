$(document).ready(function(){
	$("<div class='file-preview '><div class='file-drop-zone'><div class='file-drop-zone-title'>Click here to choose file..</div><img id='file-preview-image' style='height:100%;'/></div><button class='btn btn-primary btn-block myBoldFont' id='file-upload-button'>Upload</button></div>").insertAfter("#myFile");
	$('#file-preview-image').hide();
	$('#file-upload-button').hide();
	$('.file-drop-zone').click(function(){
		$('#myFile').trigger('click');
	});
	var x;
	$('#myFile').change(function(){
		x=document.getElementById('myFile').files[0];
		var reader=new FileReader();
		reader.onload=function(){
			$('#file-preview-image').attr('src',reader.result);
			$('.file-drop-zone-title').hide();
			$('#file-preview-image').show();
			$('#file-upload-button').show();
		}
		reader.readAsDataURL(x);
	});
	$('#file-upload-button').click(function (){
		var data=new FormData();
		data.append('image',x);
		$.ajax({
			url:$('#myFile').attr('data-upload-url'),
			type:'POST',
			data:data,
			enctype:'multipart/form-data',
			cache:false,
			processData:false,
			contentType:false,
			success:function(res){
			if($('#myFile').attr('success-reload')){
				alert('image uploaded successfully');
				location.reload();}
			},
			error:function(res){
				alert(res.status);
			}
		});	
	});
});

/*
 * HTML
 *<input type="file" data-upload-url="http://localhost:8081/mycomprobackend/addBlogPicture" name="image" class="form-control" id="myFile" success-reload="true"/>
*/

/*
* CSS
*	#myFile{
	display:none;
	}
	.file-preview {
	    border-radius: 5px;
	    border: 1px solid #ddd;
	    padding: 5px;
	    width: 100%;
	    margin-bottom: 5px;
	}
	.file-drop-zone {
	    border: 1px dashed #aaa;
	    border-radius: 4px;
	    height:350px;
	    text-align: center;
	    vertical-align: middle;
	    margin: 12px 15px 12px 12px;
	    padding: 5px;
	}
	.file-drop-zone-title {
	    color: #aaa;
	    font-size: 1.6em;
	    padding: 85px 10px;
	    cursor: default;
	}
*/