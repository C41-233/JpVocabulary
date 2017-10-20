(function(){

String.prototype.isCJKCharacter = function(){
	return Linq.from(this).charCode().isAll(CharCode.isCJKCharacter)
}

String.prototype.isHiragana = function(){
	return Linq.from(this).charCode().isAll(CharCode.isHiragana)
}

String.prototype.toLines = function(){
	return Linq.from(this.split("\n")).select(function(s){
		return s.trim()
	}).where(function(s){
		return s != ""
	}).toArray()
}

global.Validate = {
	isValidJpCharacter: function(val){
		return val.length == 1 && val.isCJKCharacter()
	},
	isValidCnCharacter: function(val){
		return val.length == 1 && val.isCJKCharacter()
	},
	isValidSyllable: function(val){
		return val.length > 0 && val.isHiragana()
	},
	parsePinyins: function(arr){
		var tokens = []
		var error = false
		
		foreach(arr.split("\n"), function(token){
			token = token.trim()
			if(token != ""){
				if(!token.match(/^[a-z]{1,6}[1-4]$/)){
					throw token + "不符合拼音规范"
				}
				tokens.push(token)
			}
		})
		return tokens
	}
}

$.fn.jpDialog = function(args){
	var options = {
		autoOpen: false,
		modal: true,
		resizable: false,
		draggable: false,
		closeOnEscape: false,
		close: function(){
			$(this).validate("clear")
		}
	}
	if(args.width){
		options.width = args.width
	}
	if(args.buttons){
		options.buttons = args.buttons
	}
	
	var dialog = this.dialog(options)
	dialog.dialog("widget").find(".ui-dialog-buttonset button").addClass("btn btn-default btn-xs")
	if(args.trigger){
		$(args.trigger).click(function(){
			dialog.dialog("open")
		})
	}
}

})();

//dump
$(function(){
	$("#btn-mysqldump").click(function(){
		Dialog.wait(function(handle){
			Action.post("/action/dump", {}, {
				success: function(){
					alert("备份完成")
				},
				complete: function(){
					handle.close()
				}	
			})
		})
	})
})

$(function(){
	//左侧列表
	$("#left-list").accordion({
		header: ".left-list-header",
		heightStyle: "content",
		event: "hoverintent",
		collapsible: false,
		active: $("#left-list .left-list-header-active").data("seq"),
	}).show()
})
	
//添加汉字
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
	
	var dialog = $("#dialog-import-character").jpDialog({
		width:	260,
		buttons: {
			"创建": actionCreateCharacter,
			"取消": function(){
				$(this).dialog("close")
			}
		},
		trigger: "#btn-import-character"
	})
	
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

//添加基本词
$(function(){
	function actionCreateNotional(){
		if($("#dialog-import-notional-word").validate("hasError")){
			return
		}
		var values = $("#import-notional-word-input-words").val().toLines()
		var meanings = $("#import-notional-word-input-meanings").val().toLines()
		var types = Linq.from($("#import-notional-word-types input[type='checkbox']:checked")).select(function(q){
			return $(q).parent().text().trim()
		}).toArray()
		
		Action.post("/words/action/create", {values: values, meanings: meanings, types: types}, function(){
			
		})
	}

	var dialog = $("#dialog-import-notional-word").jpDialog({
		width:	420,
		buttons: {
			"创建": actionCreateNotional,
			"取消": function(){
				$(this).dialog("close")
			}
		},
		trigger: "#btn-import-notional-word"
	})
	
	$("#dialog-import-notional-word").validate({
		"#import-notional-word-input-words": function(val){
			return Linq.from(val.split("\n")).select(function(s){
				return s.trim()
			}).where(function(s){
				return s != ""
			}).isExist(function(s){
				return Validate.isValidSyllable(s)
			})
		}
	})
})
