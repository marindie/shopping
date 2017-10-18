/**
 * 
 */
var wony;
if(!wony) wony = {};
//if(!wony.util) wony.util = {};
//wony.util.module = {};
function foo(salutation, three, two, one) {
    alert(salutation + " " + this.name + " - " + three + " " + two + " " + one);
}

(function(window){
	
	Json = {
			toObject : function(formData){
				return serializeToObject(formData);
			}
	};
	
	function serializeToObject(formData){
		var regex = /([^=#]+)=([^&#]*)&{0,1}/g,
	    params = new Object(),
	    match = new Array();
		
		while(match = regex.exec(formData)) {
			params[match[1]] = match[2];
		}
		return params;
	}
	
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
			},			
			serializeToObject : function(formData){
				var regex = /([^=#]+)=([^&#]*)&{0,1}/g,
			    params = new Object(),
			    match;
				
				while(match = regex.exec(formData)) {
					params[match[1]] = match[2];
				}
				return params;				
			}
	}
	
	Thing = function(name) {
	    this.name = name;
	}
	Thing.prototype.doSomething = function(callback) {
	    // Call our callback, but using our own instance as the context
	    callback.apply(this, ['Hi', 3, 2, 1]);
	}
	
	window.wony = wony;
})(window);
