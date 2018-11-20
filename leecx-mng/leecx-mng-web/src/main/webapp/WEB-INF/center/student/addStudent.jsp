<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>首页</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>学生管理</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>添加学生</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
                        
	<div class="row">
    	<div class="col-md-12">
			<br/>
			<!-- 意见反馈 start -->
			<div class="tabbable-line boxless tabbable-reversed">
            	<div class="portlet box green-jungle">
                	<div class="portlet-title">
                    	<div class="caption">
                    		<i class="icon-plus"></i>添加学生
                    	</div>
					</div>
					
					<div class="portlet-body form">
					
	                    <!-- BEGIN FORM-->
	                    <form id="addStudentForm" class="form-horizontal">
		                    <div class="form-body">
		                    
		                    	<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>名字：</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
		                            		<input id="name" name="name" type="text" class="form-control" placeholder="输入名字">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>电话：</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
		                            		<input id="telephone" name="telephone" type="text" class="form-control" placeholder="输入电话">
		                            	</div>
									</div>
								</div>
								
								<div class="form-group">
		                        	<label class="col-md-3 control-label"><span class="field-required"> * </span>头像</label>
		                            <div class="col-md-4">
		                            	<div id="input-error">
	                            			<input type="hidden" id="icon" name="icon" class="form-control"/>

											<input id="file" type="file" name="file" data-url="<%=request.getContextPath() %>/student/iconUpload.action" accept="image/*"/>
											
											<div id="iconContent"></div>
	                            		</div>
	                            		
									</div>
								</div>
		                                                  
							</div>
	                                                        
							<div class="form-actions">
			                    <div class="row">
			                        <div class="col-md-offset-3 col-md-9">
			                            <button type="submit" class="btn green-jungle">提 交</button>
			                            <button type="reset" class="btn grey-salsa btn-outline">取  消</button>
			                        </div>
			                    </div>
							</div>
						</form>
						<!-- END FORM-->
						
					</div>
				</div>
			</div>
                            
		</div>
	</div>
	
<script type="text/javascript">
    
    $("#file").fileupload({
    	pasteZone: "#iconContent",
    	dataType: "json",
    	done: function(e, data) {
    		console.log(data);
    		debugger;
    		if (data.result.status != '200') {
    			alert("长传失败...");
    		} else {
    			var iconServer = $("#iconServer").val();
    			var url = iconServer + data.result.data;
    			$("#iconContent").html("<img src='" + url + "' />");
    			$("#icon").attr("value", data.result.data);
    		}
    		
    	}
    });
    
    var submitBgm = function() {
    	App.blockUI();
    	$('#addStudentForm').ajaxSubmit({
    		url: $('#hdnContextPath').val() + '/student/saveOrUpdate.action',
    		type: 'POST',
    		success: function(data) {
    			
    			if (data.status == 200 && data.msg == 'OK') {
    				SweetAlert.success("添加成功！");
    				App.unblockUI();
    				$('#name').val('');
                	$('#telephone').val('');
                	$('#icon').val('');
                	$("#iconContent").html("");
                	
    			} else {
    				SweetAlert.error(data.msg);
    				App.unblockUI();
    			}
    			
    			
    		},
    		error: function (response, ajaxOptions, thrownError) {
	        	Error.displayError(response, ajaxOptions, thrownError); 
	        	App.unblockUI();
       		}
    	});
    }
    
    $('#addStudentForm').validate({
    	errorElement: 'span', //default input error message container
        errorClass: 'help-block', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "", // validate all fields including form hidden input
        rules: {
        	name: {
                required: true,
            },
            telephone: {
                required: true,
                digits: true
            },
            path: {
                required: true
            }
        },
        messages: {
            name: {
                required: "名字不能为空.",
            },
            telephone:{
            	required: "歌曲不能为空.",
            	digits: "请输入一个数字"
            },
            path: {
                required: "路径不能为空."
            }
        },
        invalidHandler: function(event, validator) { //display error alert on form submit   
            $('.alert-danger', $('#addStudentForm')).show();
        },

        highlight: function(element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },
        success: function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function(error, element) {
            error.insertAfter(element.closest('#input-error'));
        },
        submitHandler: function(form) {
        	// FIXME
        	submitBgm();
        }
    });
    
    
</script>
