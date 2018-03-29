/**
 * 
 */
app.factory('FriendService',function($http){
	var friendService={};
	var BASE_URL="http://localhost:8081/mycomprobackend"
	
	friendService.sendFriendRequest=function(toId){
		return $http.get(BASE_URL+"/sendFriendRequest/"+toId)
	}
	friendService.getSuggestedFriends=function(){
		return $http.get(BASE_URL+"/getSuggestedFriends")
	}
	friendService.getFriendsList=function(){
		return $http.get(BASE_URL+"/getFriendsList")
	}
	friendService.getFriendRequests=function(){
		return $http.get(BASE_URL+"/getFriendRequests")
	}
	friendService.getSentRequests=function(){
		return $http.get(BASE_URL+"/getSentRequests")
	}
	friendService.updateFriendStatus=function(username,status){
		return $http.get(BASE_URL+"/updateFriendStatus/"+username+"/"+status)
	}
	friendService.getFriend=function(username){
		return $http.get(BASE_URL+"/getFriend/"+username)
	}
	
	return friendService;
})