var reg = /1\d{10}/;
var wait = 60;
function sendsms(o,url) {
	if (wait == 0) {
		o.removeAttribute("disabled");
		o.innerHTML = "重新发送验证码";
		wait = 60;
	} else {
		if (wait == 60) {
			var sj = $("#mobile").val();
			if (sj != "" && reg.test(sj)) {
				$.post(url+"/sendSMS.html?da="
						+ new Date().getTime(), {
					phone : sj
				}, function(data) {
				});
			} else {
				alert("请输入正确手机号码!");
				return;
			}
		}
		o.setAttribute("disabled", true);
		o.innerHTML = "重新发送(" + wait + ")";
		wait--;
		setTimeout(function() {
			sendsms(o)
		}, 1000)
	}

}
function sendsms2(o,url) {
	if (wait == 0) {
		o.removeAttribute("disabled");
		o.value = "重新发送验证码";
		wait = 60;
	} else {
		if (wait == 60) {
			var sj = $("#mobile").val();
			if (sj != "" && reg.test(sj)) {
				$.post(url+"/sendSMS.html?da="
						+ new Date().getTime(), {
					phone : sj
				}, function(data) {
				});
			} else {
				alert("请输入正确手机号码!");
				return;
			}
		}
		o.setAttribute("disabled", true);
		o.value = "重新发送(" + wait + ")";
		wait--;
		setTimeout(function() {
			sendsms2(o)
		}, 1000)
	}

}