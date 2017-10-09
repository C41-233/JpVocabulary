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
String.prototype.isCJKCharacter = function(){
	if(this.length == 0){
		return false
	}
	for(var i=0; i<this.length; i++){
		var ch = this.charCodeAt(i)
		if(!CharCode.isCJKCharacter(ch)){
			return false
		}
	}
	return true
}

global.CharCode = {
	isCJKCharacter: function(code){
		return code>=0x4E00 && code<=0x9FCC
	}
}

//虚拟按键
global.VK = {
	ESC: 27,
	ENTER: 13
}

})()