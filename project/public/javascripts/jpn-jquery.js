//selection
(function($){

	$.fn.extend({
		"selection": function(method){
			if(arguments.length == 0){
				return get(this)
			}
			var args = Array.copy(arguments, 1)
			switch(method){
				case "get": return get(this)
				case "set": return set(this, args)
				case "insert": return insert(this, args)
			}
		}
	})

	function get($this){
		if($this.length == 0){
			return null;
		}
		var dom = $this[0]
		return {
			start: dom.selectionStart,
			end: dom.selectionEnd
		}
	}
	
	function set($this, options){
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
	}
	
	function insert($this, options){
		var text = options[0]
		var selection = get($this)
		var value = $this.val()
		
		value = value.substring(0, selection.start) + text + value.substring(selection.end)
		
		$this.val(value)
		
		var pos = selection.start+text.length
		set($this, [pos, pos])
		return $this
	}
	
})(window.jQuery)

