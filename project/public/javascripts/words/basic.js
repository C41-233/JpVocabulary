$(function(){
	$(".example-sentence").each(function(){
		var s = this.innerHTML
		s = s.replace("{", "<span class='target'>")
		s = s.replace("}", "</span>")
		this.innerHTML = s
	})
	$("#data-body").show()
})