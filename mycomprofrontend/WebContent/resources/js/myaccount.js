$(document).ready(function(){
	$("#firstnameSave").hide();
	$("#lastnameSave").hide();
	$("#addressSave").hide();
	$("#emailSave").hide();
	$("#mobileSave").hide();
	$("#firstnameBtn").click(function(){
		$("#firstnameField").prop( "disabled", false ).addClass("form-control").focus();
		$(this).hide();
		$("#firstnameSave").show();
	});
	$("#lastnameBtn").click(function(){
		$("#lastnameField").prop( "disabled", false ).addClass("form-control").focus();
		$(this).hide();
		$("#lastnameSave").show();
	});
	$("#addressBtn").click(function(){
		$("#addressField").prop( "disabled", false ).addClass("form-control").focus();
		$(this).hide();
		$("#addressSave").show();
	});
	$("#emailBtn").click(function(){
		$("#emailField").prop( "disabled", false ).addClass("form-control").focus();
		$(this).hide();
		$("#emailSave").show();
	});
	$("#mobileBtn").click(function(){
		$("#mobileField").prop( "disabled", false ).addClass("form-control").focus();
		$(this).hide();
		$("#mobileSave").show();
	});
});
