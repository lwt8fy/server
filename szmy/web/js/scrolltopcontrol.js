function back_top(){
	h = $(window).height();
	t = $(document).scrollTop();
	if(t > h){
		$('#go_top').show();
	}else{
		$('#go_top').hide();
	}
}
$(document).ready(function(e) {
	$('body').append('<a id="go_top" href="javascript:void(0)"></a>');	
	back_top();
	$('#go_top').click(function(){
	$(document).scrollTop(0);	
	});
});

$(window).scroll(function(e){
	back_top();		
})
