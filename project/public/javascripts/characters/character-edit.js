$(function(){
	$("#btn-delete-character").click(function(){
		Dialog.confirm({
			text: "确定要删除汉字吗？",
			yes: "删除",
			yesClass: "btn-danger",
			no: "取消",
			onConfirm: function(handle){
				Action.post(
					"/characters/action/delete", 
					{}, 
					{
						success: function(){
							location.href = "/characters"
						},
						complete: function(){
							handle.close()
						}
					}	
				)
			}
		})
	})
	
	$("#editable-character-jp").editable({
		active: function(val){
			alert(val)
		}
	})
	$("#editable-character-cn").editable({
		
	})
	$("#editable-character-pinyins").editable({
		type: "textarea"
	})
	
	$("#btn-add-syllable").click(function(){
		var value = $("#input-add-syllable").val()
		Action.post("/characters/action/add-syllable", {id: DataMgr.id, syllable: value}, function(){
			location.reload()
		})
	})
})