(function(){

$(function(){

	$("#btn-delete-word").click(function(){
		Dialog.confirm({
			text: "确定要删除动词吗？",
			yes: "删除",
			yesClass: "btn-danger",
			onConfirm: function(handle){
				Action.post(
					"/words/verb/action/delete", 
					{id: DataMgr.id}, 
					{
						success: function(){
							location.href = DataMgr.refer
						},
						complete: function(){
							handle.close()
						}
					}	
				)
			}
		})
	})

	$(".btn-delete-value").click(function(){
		var id = $(this).data("id")
		Action.post("/words/verb/action/delete-value", {id: id}, function(){
			location.reload()
		})
	})
	
	$("#editable-meanings").editable({
		type: "textarea",
		active: function(val){
			var meanings = val.toLines()
			Action.post("/words/verb/action/update-meanings", {id: DataMgr.id, meanings: meanings}, function(){
				location.reload()
			})
		}
	})
		
	$("#btn-add-value").click(function(){
		var value = $("#input-value").val().trim()
		if(!value){
			return
		}
		Action.post("/words/verb/action/add-value", {id: DataMgr.id, value: value}, function(){
			location.reload()
		})
	})
	$("#input-value").keydown(function(e){
		if(e.keyCode == VK.ENTER){
			$("#btn-add-value").click()
		}
	})
	
})

})()