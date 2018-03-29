app.factory('UserService',function($http){
	var userService={};
	var BASE_URL="http://localhost:8081/mycomprobackend"
		
	userService.registerUser=function(user){
		return $http.post(BASE_URL+"/registerUser",user)
	}
	
	userService.updateUser=function(user){
		return $http.post(BASE_URL+"/updateUser",user)
	}
	
	userService.validateUser=function(user){
		return $http.post(BASE_URL+"/login",user)
	}
	
	userService.logout=function(){
		return $http.get(BASE_URL+"/logout")
	}
	
	userService.getUser=function(id){
		return $http.get(BASE_URL+"/getUser/"+id)
	}
	return userService;
})
