(function(){

String.prototype.isCJKCharacter = function(){
	return Linq.from(this).charCode().isAll(function(c){
		return CharCode.isCJKCharacter(c) || c=='〇' || c=='ヶ'
	})
}

String.prototype.isHiragana = function(){
	return Linq.from(this).charCode().isAll(function(c){
		return CharCode.isHiragana(c) || c=='ー'
	})
}

String.prototype.isKatakana = function(){
	return Linq.from(this).charCode().isAll(function(c){
		return CharCode.isKatakana(c) || c=='ー'
	})
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
		return val.length > 0 && val.isCJKCharacter()
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

$.fn.jpDialog = function(params){
	var args = $.extend({
		create: function(){}
	}, params)
	var options = {
		autoOpen: false,
		modal: true,
		resizable: false,
		draggable: false,
		closeOnEscape: false,
		open: function(){
			$(document).on("keydown", esc)
		},
		close: function(){
			$(this).validate("clear")
			$(document).off("keydown", esc)
		}
	}
	if(args.width){
		options.width = args.width
	}
	
	options.buttons = {
		"创建": args.create, 
		"取消": function(){
			$(this).dialog("close")
		}
	}
	
	var dialog = this.dialog(options)
	dialog.dialog("widget").find(".ui-dialog-buttonset button").addClass("btn btn-default btn-xs")
	if(args.trigger){
		$(args.trigger).click(function(){
			dialog.dialog("open")
		})
	}
	
	$(this).find("input[type='text'], textarea").keydown(function(e){
		if(e.keyCode == VK.ENTER && e.ctrlKey){
			args.create()
		}
	})
	
	var esc = function(e){
		if(e.keyCode == VK.ESC){
			dialog.dialog("close")
		}
	}
	
}

})();

//dump
$(function(){
	$("#btn-mysqldump").click(function(){
		Dialog.wait(function(handle){
			Action.post("/action/dump", {}, {
				complete: function(){
					handle.close()
				}	
			})
		})
	})
})

$(function(){
	$(document).keyup(function(e){
		if(e.target==document.body){
			switch(e.keyCode){
				case VK._1: $("#btn-import-character").click(); break
				case VK._2: $("#btn-import-notional-word").click(); break
				case VK._3: $("#btn-import-verb-word").click(); break
				case VK._4: $("#btn-import-adj-word").click(); break
				case VK._5: $("#btn-import-katakana-word").click(); break
			}
		}
	})
})

//添加汉字
$(function(){
	$("#dialog-import-character").jpDialog({
		width:	260,
		create: function(){
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
	$("#dialog-import-notional-word").jpDialog({
		width:	420,
		create: function(){
			if($("#dialog-import-notional-word").validate("hasError")){
				return
			}
			var values = $("#import-notional-word-input-words").val().toLines()
			var meanings = $("#import-notional-word-input-meanings").val().toLines()
			var types = Linq.from($("#import-notional-word-types input[type='checkbox']:checked")).select(function(q){
				return $(q).parent().text().trim()
			}).toArray()
			
			Action.post("/words/notional/action/create", {values: values, meanings: meanings, types: types}, function(data){
				location.reload()
			})
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
		},
		"#import-notional-word-types": function(){
			return $(this).find(":checked").length > 0
		}
	})
})

//添加动词
$(function(){
	$("#dialog-import-verb-word").jpDialog({
		width:	420,
		create: function(){
			if($("#dialog-import-verb-word").validate("hasError")){
				return
			}
			
			var values = $("#import-verb-word-input-words").val().toLines()
			var meanings = $("#import-verb-word-input-meanings").val().toLines()
			var types = Linq.from($("#import-verb-word-types input[type='checkbox']:checked"))
				.select(function(q){
					if($(q).data("value")){
						return $(q).data("value")
					}
					else{
						return $(q).parent().text().trim()
					}
				})
				.toArray()
			
			Action.post("/words/verb/action/create", {values: values, meanings: meanings, types: types}, function(data){
				location.reload()
			})
		},
		trigger: "#btn-import-verb-word"
	})
	
	$("#dialog-import-verb-word").validate({
		"#import-verb-word-input-words": function(val){
			return Linq.from(val.split("\n")).select(function(s){
				return s.trim()
			}).where(function(s){
				return s != ""
			}).isExist(function(s){
				return Validate.isValidSyllable(s)
			})
		},
		"#import-verb-word-types": function(){
			return $(this).children("div:nth-child(1), div:nth-child(2)").find(":checked").length > 0
				&& $(this).children("div:nth-child(3)").find(":checked").length > 0
		}
	})
})

//添加形容词
$(function(){
	$("#dialog-import-adj-word").jpDialog({
		width:	420,
		create: function(){
			if($("#dialog-import-adj-word").validate("hasError")){
				return
			}
			
			var values = $("#import-adj-word-input-words").val().toLines()
			var meanings = $("#import-adj-word-input-meanings").val().toLines()
			var types = Linq.from($("#import-adj-word-types input[type='checkbox']:checked"))
				.select(function(q){
					return $(q).parent().text().trim()
				})
				.toArray()
			
			Action.post("/words/adj/action/create", {values: values, meanings: meanings, types: types}, function(data){
				location.reload()
			})
		},
		trigger: "#btn-import-adj-word"
	})
	
	$("#dialog-import-adj-word").validate({
		"#import-adj-word-input-words": function(val){
			var list = Linq.from(val.split("\n")).select(function(s){
				return s.trim()
			}).where(function(s){
				return s != ""
			})
			
			return list.isExist(function(s){
				return Validate.isValidSyllable(s)
			}) && list.isAll(function(s){
				return s.match(/い$/)
			})
		}
	})
})

//添加片假名词
$(function(){
	$("#dialog-import-katakana-word").jpDialog({
		width:	420,
		create: function(){
			if($("#dialog-import-katakana-word").validate("hasError")){
				return
			}
			
			var value = $("#import-katakana-word-input-value").val().trim()
			var meanings = $("#import-katakana-word-input-meanings").val().toLines()
			var types = Linq.from($("#import-katakana-word-types input[type='checkbox']:checked"))
				.select(function(q){
					return $(q).parent().text().trim()
				})
				.toArray()
			var alias = $("#import-katakana-word-input-alias").val().trim()
			var context = $("#import-katakana-word-alias-types :checked").parent().text().trim()
			
			Action.post("/words/katakana/action/create", {
				value: value,
				meanings: meanings,
				types: types,
				alias: alias,
				context: context
			}, function(data){
				location.reload()
			})
		},
		trigger: "#btn-import-katakana-word"
	})
	
	$("#dialog-import-katakana-word").validate({
		"#import-katakana-word-input-value": function(val){
			return val.length>0 && CharCode.isKatakana(val.charCodeAt(0))
		},
		"#import-katakana-word-types": function(){
			return $(this).find(":checked").length > 0
		},
		"#import-katakana-word-alias-types": function(){
			return $(this).find(":checked").length == 1
		}
	})
})