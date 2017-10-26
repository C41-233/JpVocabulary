(function(){

$(function(){

	$("#btn-delete-word").click(function(){
		Dialog.confirm({
			text: "确定要删除基本词吗？",
			yes: "删除",
			yesClass: "btn-danger",
			onConfirm: function(handle){
				Action.post(
					"/words/notional/action/delete", 
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

	$("#editable-meanings").editable({
		type: "textarea",
		active: function(val){
			var meanings = val.toLines()
			Action.post("/words/notional/action/update-meanings", {id: DataMgr.id, meanings: meanings}, function(){
				location.reload()
			})
		}
	})
	
	$(".btn-delete-value").click(function(){
		var id = $(this).data("id")
		Action.post("/words/notional/action/delete-value", {id: id}, function(){
			location.reload()
		})
	})
	
	$("#btn-add-value").click(function(){
		var value = $("#input-value").val().trim()
		if(!value){
			return
		}
		Action.post("/words/notional/action/add-value", {id: DataMgr.id, value: value}, function(){
			location.reload()
		})
	})
	$("#input-value").keydown(function(e){
		if(e.keyCode == VK.ENTER){
			$("#btn-add-value").click()
		}
	})
	
	$("#div-word-types input[type='checkbox']").change(function(){
		var value = $(this).is(":checked")
		var type = $(this).parent().text().trim()
		Action.post("/words/notional/action/update-type", {id: DataMgr.id, type: type, value: value}, {
			complete: function(){
				location.reload()
			}
		})
	})
	
})

})()