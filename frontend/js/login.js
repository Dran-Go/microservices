$(function() {
	var root = "http://139.159.132.32:8800";
	var sessionId;
	$("input[type='password'][data-eye]").each(function(i) {
		var $this = $(this),
			id = 'eye-password-' + i,
			el = $('#' + id);

		$this.wrap($("<div/>", {
			style: 'position:relative',
			id: id
		}));

		$this.css({
			paddingRight: 60
		});
		$this.after($("<div/>", {
			html: 'Show',
			class: 'btn btn-primary btn-sm',
			id: 'passeye-toggle-'+i,
		}).css({
				position: 'absolute',
				right: 10,
				top: ($this.outerHeight() / 2) - 12,
				padding: '2px 7px',
				fontSize: 12,
				cursor: 'pointer',
		}));

		$this.after($("<input/>", {
			type: 'hidden',
			id: 'passeye-' + i
		}));

		var invalid_feedback = $this.parent().parent().find('.invalid-feedback');

		if(invalid_feedback.length) {
			$this.after(invalid_feedback.clone());
		}

		$this.on("keyup paste", function() {
			$("#passeye-"+i).val($(this).val());
		});
		$("#passeye-toggle-"+i).on("click", function() {
			if($this.hasClass("show")) {
				$this.attr('type', 'password');
				$this.removeClass("show");
				$(this).removeClass("btn-outline-primary");
			}else{
				$this.attr('type', 'text');
				$this.val($("#passeye-"+i).val());				
				$this.addClass("show");
				$(this).addClass("btn-outline-primary");
			}
		});
	});

	$(".login-form").submit(function(event) {
		var form = $(this);
		event.preventDefault();
		if (form[0].checkValidity() === true) {
			$.ajax({
				type: "GET",
				url: root + "/api/user/login",
				data: form.serialize(),
				dataType: "json",//预期服务器返回的数据类型
				async: false,
				success: function(result) {
					if(result.code == 200){
						sessionStorage.setItem("Authorization", result.data);
						window.location.href="index.html"
					}else {
						alert(result.message);
					}
				},
				error: function () {
					alert("网络繁忙，请稍后再试");
				}}
				);
		}else {
			form.addClass('was-validated');
		}
	});
});
