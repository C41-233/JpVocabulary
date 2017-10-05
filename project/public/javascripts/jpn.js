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

if(!String.prototype.trim){
	String.prototype.trin = function(){
		return this.replace(/(^\s*)|(\s*$)/g, "")
	}
}

})()