(function(){

	window.global = window

	global.foreach = function(obj, func){
		if(obj.length){
			for(var i=0; i<obj.length; i++){
				var hint = func(obj[i], i)
				if(hint === false){
					break;
				}
			}
		}
	}

	//方法重载
	global.dispatch = function(){
		var context = dispatch.caller.arguments
		con:
		for(var i=0; i<arguments.length; i++){
			var entry = arguments[i]
			var func = entry[entry.length-1]
			if(entry.length-1 != context.length){
				continue
			}
			for(var j=0; j<context.length; j++){
				switch(entry[j]){
					case "string": 
					case "function":
					case "object":
					{
						if(typeof(context[j])!=entry[j]){
							continue con
						}
						break
					}
					default:{
						if(!(context[j] instanceof entry[j])){
							continue con
						}
						break
					}
				}
			}
			return func.apply(dispatch.caller, context)
		}
		throw "no overload function found."
	}

	//数组扩展
	Array.copy = function(){
		if(arguments.length == 2){
			var array = arguments[0]
			var start = arguments[1]
			var rst = []
			for(var i=start; i<array.length; i++){
				rst.push(array[i])
			}
			return rst
		}
	}

	//字符串扩展
	if(!String.prototype.trim){
		String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "")
		}
	}

	//输入参数都是int
	global.CharCode = {
		isCJKCharacter: function(code){
			return code>=0x4E00 && code<=0x9FCC
		},
		//平假名
		isHiragana: function(code){
			return code >= 0x3040 && code <= 0x309F
		},
		//片假名（包括其扩展）
		isKatakana: function(code){
			return code >= 0x30A0 && code <= 0x30FF || code >= 0x31F0 && code <= 0x31FF
		}
	}

	//虚拟按键
	global.VK = {
		ESC: 27,
		ENTER: 13
	}

})();

//Linq
(function(){
	global.Linq = {
		from: function(obj){
			if(obj instanceof String){
				return new StringEnumerable(obj)
			}
			return new ArrayEnumerable(obj)
		}
	}

	function IEnumerable(){
		this.foreach = function(action){
			var it = this.iterator()
			var i = 0
			while(it.moveNext()){
				action(it.current(), i++)
			}
		}
		this.isAll = function(predicate){
			var it = this.iterator()
			while(it.moveNext()){
				if(!predicate(it.current())){
					return false
				}
			}
			return true
		}
		this.isExist = function(predicate){
			var it = this.iterator()
			while(it.moveNext()){
				if(predicate(it.current())){
					return true
				}
			}
			return false
		}
		this.toArray = function(){
			var arr = []
			this.foreach(function(e){
				arr.push(e)
			})
			return arr
		}
		
		this.where = function(predicate){
			return new WhereEnumerable(this, predicate)
		}
		this.select = function(selector){
			return new SelectEnumerable(this, selector)
		}
	}
	
	function StringEnumerable(str){
		ArrayEnumerable.call(this, str)
		
		function CharCodeEnumerable(){
			IEnumerable.call(this)
			
			function Iterator(){
				var index = -1
				this.moveNext = function(){
					return ++index < str.length
				}
				this.current = function(){
					return str.charCodeAt(index)
				}
			}
			
			this.iterator = function(){
				return new Iterator()
			}
		}
		
		this.charCode = function(){
			return new CharCodeEnumerable()
		}
	}
	
	function ArrayEnumerable(arr){
		IEnumerable.call(this)
	
		function Iterator(){
			var index = -1
			this.moveNext = function(){
				return ++index < arr.length
			}
			this.current = function(){
				return arr[index]
			}
		}
		
		this.iterator = function(){
			return new Iterator()
		}
	}
	
	function WhereEnumerable(enumerable, predicate){
		IEnumerable.call(this)
		
		function Iterator(){
			var iterator = enumerable.iterator()
			
			var value
			this.moveNext = function(){
				while(iterator.moveNext()){
					value = iterator.current()
					if(predicate(value)){
						return true
					}
				}
				delete value
				return false
			}
			this.current = function(){
				return value
			}
		}
		this.iterator = function(){
			return new Iterator()
		}
	}
	
	function SelectEnumerable(enumerable, selector){
		IEnumerable.call(this)
		
		function Iterator(){
			var it = enumerable.iterator()
			var i = -1
			this.moveNext = function(){
				i++
				return it.moveNext()
			}
			this.current = function(){
				return selector(it.current(), i)
			}
		}
		this.iterator = function(){
			return new Iterator()
		}
	}
	
})();