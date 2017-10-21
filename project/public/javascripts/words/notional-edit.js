(function(){

$(function(){

	$("#editable-meanings").editable({
		type: "textarea",
		active: function(val){
		}
	})
	
	$(".btn-delete-value").click(function(){
		var id = $(this).data("id")
		Action.post("/words/notional/action/delete-value", {id: id}, function(){
			location.reload()
		})
	})
	
})

})()