$(function(){
	$("#left-list").accordion({
		header: ".left-list-header",
		heightStyle: "content",
		event: "hoverintent",
		collapsible: false,
		active: $("#left-list .left-list-header-active").data("seq"),
	}).show()
	
	var dialog = $("#dialog-import-character").dialog({
		autoOpen: false,
		modal: true,
		resizable: false,
		draggable: false,
		width:	260,
		buttons: {
			"创建": function(){
				if($("#dialog-import-character").validate("hasError")){
					return
				}
				var jp = $("#import-character-input-jp").val()
				var cn = $("#import-character-input-cn").val()
				
				var pinyins = Validate.parsePinyins($("#import-character-input-pinyin").val())
				
				Action.post("/characters/action/create", {jp: jp, cn: cn, pinyins: pinyins}, function(data){
					location.href = data.href
				})
			},
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