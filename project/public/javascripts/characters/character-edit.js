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
			val = val.trim()
			if(!Validate.isValidJpCharacter(val)){
				$(this).editable("error")
				return
			}
			Action.post("/characters/action/update-jp", {id: DataMgr.id, value: val}, function(){
				location.reload()
			})
		}
	})
	$("#editable-character-cn").editable({
		active: function(val){
			val = val.trim()
			if(!Validate.isValidCnCharacter(val)){
				$(this).editable("error")
				return
			}
			Action.post("/characters/action/update-cn", {id: DataMgr.id, value: val}, function(){
				location.reload()
			})
		}
	})
	$("#editable-character-pinyins").editable({
		type: "textarea",
		active: function(val){
			try{
				var pinyins = Validate.parsePinyins(val)
				if(pinyins.length==0){
					$(this).editable("error")
					return
				}
				Action.post("/characters/action/update-pinyins", {id: DataMgr.id, values: pinyins}, function(){
					location.reload()
				})
			}
			catch(e){
				$(this).editable("error")
				return
			}
		}
	})
	
	$("#input-add-syllable").inputable({
		label: "添加",
		active: function(value){
			value = value.trim()
			if(!Validate.isValidSyllable(value)){
				$(this).addClass("error")
				return
			}
			
			Action.post("/characters/action/add-syllable", {id: DataMgr.id, syllable: value}, function(){
				location.reload()
			})
		}
	}).on("input", function(){
		$(this).removeClass("error")
	})
})