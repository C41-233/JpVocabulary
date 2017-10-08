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
				
				var tokens = []
				foreach($("#import-character-input-pinyin").val().split("\n"), function(token){
					token = token.trim()
					if(token != ""){
						tokens.push(token)
					}
				})
				
				Action.post("/characters/action/create", {jp: jp, cn: cn, pinyins: tokens}, function(data){
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
		"#import-character-input-jp": function(val){
			return val.length == 1
		},
		"#import-character-input-cn": function(val){
			return val.length == 1
		},
		"#import-character-input-pinyin": function(val){
			var tokens = []
			var error = false
			foreach(val.split("\n"), function(token){
				token = token.trim()
				if(token != ""){
					if(!token.match(/^[a-z]{1,6}[1-4]$/)){
						error = token
						return false
					}
					tokens.push(token)
				}
			})
			if(error){
				return error + "不符合拼音规范"
			}
			if(tokens.length == 0){
				return false
			}
			return true
		}
	})
	
})