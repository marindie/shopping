/**
 * 
 */
var wony;
if(!wony) wony = {};
if(!wony.util) wony.util = {};
wony.util.module = {};
(function(window){

	Map = function(){
		this.map = new Object();
	};
	Map.prototype ={
			put : function(key, value){
				this.map[key] = value;
			},
			get : function(key){
				return this.map[key];
			},
			containsKey : function(key){
				return key in this.map;
			},
			containsValue : function(value){
				for(var prop in this.map){
					if(this.map[prop] == value)
						return true;
				}
			},
			isEmpty : function(key){
				return(this.size() == 0);
			},
			remove : function(key){
				delete this.map[key];
			},
			keys : function(){
				var keys = new Array();
				for(var prop in this.map){
					keys.push(prop);
				}
			},
			values : function(){
				var values = new Array();
				for(var prop in this.map){
					values.push(this.map[prop]);
				}
				return values;
			},
			size : function(){
				var count = 0;
				for(var prop in this.map){
					count++;
				}
				return count;
			}
	}
	
	serializeToObject = function(formData){
		var regex = /[?&]([^=#]+)=([^&#]*)/g,
	    params = new Object(),
	    match;
		
		while(match = regex.exec(formData)) {
			params.match[1] = match[2];
		}
		return params;
	};
	
})(window);
