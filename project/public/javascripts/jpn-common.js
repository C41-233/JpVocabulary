(function(){

String.prototype.isCJKCharacter = function(){
	return Linq.from(this).charCode().isAll(CharCode.isCJKCharacter)
}

String.prototype.isHiragana = function(){
	return Linq.from(this).charCode().isAll(CharCode.isHiragana)
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

})();


$(function(){

	$("#left-list").accordion({
		header: ".left-list-header",
		heightStyle: "content",
		event: "hoverintent",
		collapsible: false,
		active: $("#left-list .left-list-header-active").data("seq"),
	}).show()

});
