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
							location.href = "/words/verb"
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
	
	$("#div-word-types input[type='checkbox']").change(function(){
		var value = $(this).is(":checked")
		var type = $(this).data("value")
		Action.post("/words/verb/action/update-type", {id: DataMgr.id, type: type, value: value}, {
			complete: function(){
				location.reload()
			}
		})
	})
	
	$("#btn-add-fixword").click(function(){
		var word = $("#input-fixword-value").val().trim()
		var meaning = $("#input-fixword-meaning").val().trim()
		if(!word || !meaning){
			return
		}
		Action.post("/words/verb/action/add-fixword", {id: DataMgr.id, value: word, meaning: meaning}, function(){
			location.reload()
		})
	})
	
	$(".btn-delete-fixword").click(function(){
		var word = $(this).parent().parent().find("td:nth-child(1)").text().trim()
		Action.post("/words/verb/action/delete-fixword", {id: DataMgr.id, value: word}, function(){
			location.reload()
		})
	})
	
	$(".editable-fixword-meaning").editable({
		active: function(val){
			val = val.trim()
			if(!val){
				return
			}
			var word = $(this).parents("tr").eq(0).find("td:nth-child(1)").text().trim()
			Action.post("/words/verb/action/update-fixword", {id: DataMgr.id, value: word, meaning: val}, function(){
				location.reload()
			})
		}
	})
})

})()