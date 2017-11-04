(function(){

$(function(){

	$("#btn-delete-word").click(function(){
		Dialog.confirm({
			text: "确定要删除片假名词吗？",
			yes: "删除",
			yesClass: "btn-danger",
			onConfirm: function(handle){
				Action.post(
					"/words/katakana/action/delete", 
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

	$("#editable-value").editable({
		active: function(val){
		}
	})
	
	$("#editable-meanings").editable({
		type: "textarea",
		active: function(val){
			var meanings = val.toLines()
			Action.post("/words/katakana/action/update-meanings", {id: DataMgr.id, meanings: meanings}, function(){
				location.reload()
			})
		}
	})
	
	$("#div-word-types input[type='checkbox']").change(function(){
		var value = $(this).is(":checked")
		var type = $(this).parent().text().trim()
		Action.post("/words/katakana/action/update-type", {id: DataMgr.id, type: type, value: value}, {
			complete: function(){
				location.reload()
			}
		})
	})
	
	$("#editable-alias").editable({
		active: function(val){
		}
	})
	
})

})()