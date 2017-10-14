//selection
(function($){
	var proxy = {
		get: function($this){
			if($this.length == 0){
				return null;
			}
			var dom = $this[0]
			return {
				start: dom.selectionStart,
				end: dom.selectionEnd
			}
		},
		set: function($this, options){
			var start, end
			if(options.length == 1){
				start = options[0].start
				end = options[0].end
			}
			else if(options.length > 1){
				start = options[0]
				end = options[1]
			}
			else{
				return $this
			}
			$this.each(function(){
				this.setSelectionRange(start, end)
			})
			return $this
		},
		insert: function($this, options){
			var text = options[0]
			var selection = this.get($this)
			var value = $this.val()
			
			value = value.substring(0, selection.start) + text + value.substring(selection.end)
			
			$this.val(value)
			
			var pos = selection.start+text.length
			this.set($this, [pos, pos])
			return $this
		}
	}

	$.fn.extend({
		"selection": function(method){
			if(arguments.length == 0){
				return proxy.get(this)
			}
			var args = Array.copy(arguments, 1)
			return proxy[method](this, args)
		}
	})
	
})(window.jQuery);

//validate
(function($){
	var proxy = {
		clear: function(obj){
			obj.find(".validate-error").tooltip("hide").data("tooltip-title", "")
			obj.find(".validate-error").removeClass("validate-error")
			obj.find("input, textarea").val("").trigger("validate.check")
			return obj
		},
		hasError: function(obj){
			return obj.find(".validate-error").length > 0
		}
	}

	$.fn.extend({
		"validate": function(option){
			if(typeof(option)=="object"){
				for(var key in option){
					this.find(key).each(function(){
						bind($(this), option[key])
					})
				}
				return this
			}
			
			var args = Array.copy(arguments, 1)
			proxy[option](this, args)
		}
	})
	
	function bind(obj, func){
		obj.on("validate.check", function(){
			var rst = func($(this).val())
			if(rst === true){
				$(this).removeClass("validate-error")
				$(this).data("tooltip-title", "").tooltip("hide")
			}
			else if(rst === false){
				$(this).addClass("validate-error")
				$(this).data("tooltip-title", "").tooltip("hide")
			}
			else if(typeof(rst)=="string"){
				$(this).addClass("validate-error")
				$(this).data("tooltip-title", rst).tooltip("show")
			}
		}).bind("input", function(){
			$(this).trigger("validate.check")
		}).trigger("validate.check")
		
		obj.tooltip({
			title: function(){
				return $(this).data("tooltip-title")
			},
			trigger: "manual",
			animation: false
		})
	}
})(window.jQuery);

//Dialog
(function($){
	window.Dialog = {
		confirm: function(args){
			var options = $.extend({
				yes: "确定",
				no: "取消"
			}, args)
		
			var buttons = {}
			buttons[options.yes] = function(){
				if(options.onConfirm){
					options.onConfirm({
						close: function(){
							dialog.dialog("close")
						}
					})
				}
			}
			
			buttons[options.no] = function(){
				dialog.dialog("close")
			}
			
			var dialog = $("<div>"+options.text+"</div>").dialog({
				modal: true,
				resizable: false,
				draggable: false,
				title: options.title,
				buttons: buttons,
				position: {at: "center top"},
				close: function(){
					$(this).dialog("destroy")
				}
			})
			
			var widget = dialog.dialog("widget")
			var yesButton = widget.find(".ui-dialog-buttonset button:nth-child(1)")
			yesButton.addClass("btn btn-xs")
			if(options.yesClass){
				yesButton.addClass(options.yesClass)
			}
			else{
				yesButton.addClass("btn-default")
			}
			
			var noButton = widget.find(".ui-dialog-buttonset button:nth-child(2)")
			noButton.addClass("btn btn-xs")
			if(options.noClass){
				noButton.addClass(options.yesClass)
			}
			else{
				noButton.addClass("btn-default")
			}
		}
	}
})(window.jQuery);

//ajax
(function($){
	global.Action = {
		post: function(){
			var url, data = {}, success, fail, complete
			dispatch(
				["string", "object", function(_url, _data){
					url = _url
					data = _data
				}],
				["string", "object", "function", function(_url, _data, _success){
					url = _url
					data = _data
					success = _success
				}],
				["string", "object", "object", function(_url, _data, _func){
					url = _url
					data = _data
					success = _func.success
					fail = _func.fail
					complete = _func.complete
				}],
				["string", "function", function(_url, _success){
					url = _url
					success = _success
				}]
			)
			$.post(url, data, function(data){
				if(data.result == 0){
					success(data)
				}
				else{
					alert(data.message)
					if(fail){
						fail(data)
					}
				}
				if(complete){
					complete(data)
				}
			}).fail(function(data){
				var jobj = {
					result: data.status, 
					message: data.statusText
				}
				if(data.status == 400){
					alert(data.status+" "+data.statusText+" "+data.responseText)
				}
				else{
					alert(data.status+" "+data.statusText)
				}
				if(fail){
					fail(jobj)
				}
				if(complete){
					complete(jobj)
				}
			})
		}
	}
})(window.jQuery);

//editable
(function($){
	var proxy = {
		error: function($this){
			$this.trigger("editable-error")
		}
	}

	$.fn.extend({
		"editable": function(method){
			if(arguments.length == 1 && typeof(arguments[0])=="object"){
				return init(this, arguments[0])
			}
			if(method == "error"){
				return proxy.error(this)
			}
		}
	})
	
	function init(editables, options){
		var args = $.extend(
			{type: 'text'}, 
			options
		)
		
		editables.each(function(){
			var editable = $(this)
			
			editable.addClass("editable")
		
			var input
			if(args.type == "textarea"){
				input = $("<div class='input-group'><textarea class='form-control editable-input'/><span class='input-group-btn'><button class='btn btn-default editable-btn-ok' type='button' style='margin-left:10px'>修改</button></span></div>")
			}
			else{
				args.type = 'text'
				input = $("<div class='input-group'><input class='form-control editable-input'/><span class='input-group-btn'><button class='btn btn-default editable-btn-ok' type='button'>修改</button></span></div>")
			}
			input.hide()
			editable.after(input)
			
			var inputField = input.find(".editable-input")
			var okButton = input.find(".editable-btn-ok")
			
			if(args.placeholder){
				inputField.attr("placeholder", args.placeholder)
			}
			
			function getText(){
				var text = editable.data("editable-value")
				if(text != undefined){
					return text
				}
				else{
					return editable.text()
				}
			}

			var isActive = false
			
			function doActive(){
				inputField.removeClass("validate-error")
				editable.hide()
				
				var text = getText()
				inputField.val(text)
				input.show()
				
				input.find(".editable-input").focus()
				isActive = true
				
				if(args.type == "textarea"){
					var rows = text.split("\n").length
					inputField.attr("rows", rows)
				}
			}
			
			function doInactive(){
				inputField.removeClass("validate-error")
				input.hide()
				editable.show()
				isActive = false
			}
			
			function doEditable(){
				inputField.removeClass("validate-error")
				var value = inputField.val()
				var oldValue = getText()
				if(value == oldValue){
					doInactive()
					return
				}
				
				if(args.active){
					editable.each(function(){
						args.active.call(this, value, oldValue)
					})
				}
			}
			
			editable.on("editable-error", function(){
				inputField.addClass("validate-error")
			})
			
			editable.click(function(){
				doActive()
			})
			okButton.click(function(){
				doEditable()
			})
			inputField.keydown(function(e){
				if(e.keyCode == VK.ESC){
					doInactive()
					return
				}
				if(args.type=='text' && e.keyCode == VK.ENTER){
					doEditable()
					return
				}
				if(args.type=='textarea' && e.keyCode == VK.ENTER && e.ctrlKey){
					doEditable()
					return
				}
			})
			
			$(document).on("click", function(e){
				if(!isActive){
					return
				}
				if(e.target == editable[0] || e.target == input[0]){
					return
				}
				var obj = $(e.target).parents()
				for(var i=0; i<obj.length; i++){
					if(obj[i]==editable[0] || obj[i]==input[0]){
						return;
					}
				}
				
				doInactive()
			})
		})
		return editables
	}
})(window.jQuery);

//input-group
(function($){
	$.fn.extend({
		inputable: function(options){
			var args = $.extend({
				label: "确定"
			}, options)
			var wrapper = $("<div class='input-group'></div>")
			this.wrap(wrapper).after("<span class='input-group-btn'><button class='btn btn-default btn-ok'>"+args.label+"</button></span>")
			
			this.addClass("form-control")
			
			this.each(function(){
				var self = this
				var btn = $(this).parent().find(".btn-ok")
				btn.click(function(){
					if(args.active){
						args.active.call(self, $(self).val())
					}
				})
				$(this).keydown(function(e){
					if(e.keyCode == VK.ENTER){
						btn.click()
					}
				})
			})
			return this
		}
	})
})(window.jQuery);

//css
(function($){
	$(function(){
		$(".pre-hide").removeClass("pre-hide").hide()
	})
})(window.jQuery);