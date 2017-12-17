$(function(){
	$(".example-sentence").each(function(){
		var s = this.innerHTML
		s = s.replace(/{/g, "<span class='target'>")
		s = s.replace(/}/g, "</span>")
		this.innerHTML = s
	})
	$("#data-body").show()
})