function login(username,pwd) {
	$("#header").hide();
	$("[name=bor_id]").val(username);
	$("[name=bor_verification]").val(pwd);
	//$(":submit:first").click();
}
