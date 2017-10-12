(function(){

global.Validate = {
	isValidJpCharacter: function(val){
		return val.length == 1 && val.isCJKCharacter()
	},
	isValidCnCharacter: function(val){
		return val.length == 1 && val.isCJKCharacter()
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

})();