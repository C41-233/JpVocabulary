$(function(){
	function actionCreateCharacter(){
		if($("#dialog-import-character").validate("hasError")){
			return
		}
		
		var jp = $("#import-character-input-jp").val()
		var cn = $("#import-character-input-cn").val()
		
		var pinyins = Validate.parsePinyins($("#import-character-input-pinyin").val())
		
		Action.post("/characters/action/create", {jp: jp, cn: cn, pinyins: pinyins}, function(data){
			location.href = data.href
		})
	}
	
	var dialog = $("#dialog-import-character").dialog({
		autoOpen: false,
		modal: true,
		resizable: false,
		draggable: false,
		width:	260,
		buttons: {
			"创建": actionCreateCharacter,
			"取消": function(){
				$(this).dialog("close")
			}
		},
		close: function(){
			$(this).validate("clear")
		}
	})
	$("#btn-import-character").click(function(){
		$("#dialog-import-character").dialog("open")
	})
	dialog.dialog("widget").find(".ui-dialog-buttonset button").addClass("btn btn-default btn-xs")
	
	$("#import-character-input-jp, #import-character-input-cn, #import-character-input-pinyin").keydown(function(e){
		if(e.keyCode == VK.ENTER && e.ctrlKey){
			actionCreateCharacter()
		}	
	})
	
	$("#dialog-import-character").validate({
		"#import-character-input-jp": Validate.isValidJpCharacter,
		"#import-character-input-cn": Validate.isValidCnCharacter,
		"#import-character-input-pinyin": function(val){
			try{
				var tokens = Validate.parsePinyins(val)
				if(tokens.length == 0){
					return false
				}
				return true
			} catch(e){
				return e
			}
		}
	})
	
})