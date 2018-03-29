app.controller('FriendController',function(FriendService,$scope,$location,$route,$routeParams){
$scope.friendsList={};

$scope.sendRequest=function(toId){
	FriendService.sendFriendRequest(toId).then(function(response){
		alert("Friend Request Sent !!");
		$route.reload();
	},function(response){
		alert("Internal Server Error!!!");
	})
}

$scope.acceptRequest=function(fromId){
	FriendService.updateFriendStatus(fromId,'A').then(function(response){
		alert("you and "+fromId+" are now friends");
		$route.reload();
	},function(response){
		alert("Internal Server Error!!!");
	})
}

$scope.rejectRequest=function(fromId){
	FriendService.updateFriendStatus(fromId,'R').then(function(response){
		$route.reload();
	},function(response){
		alert("Internal Server Error!!!");
	})
}

if($location.path()=='/getSuggestedFriends'){
	FriendService.getSuggestedFriends().then(function(response){
		$scope.friendsList=response.data;
		console.log(response.data);
	},function(response){
		alert("Internal Server Error!!!");
	})
}

if($location.path()=='/getFriendsList'){
	FriendService.getFriendsList().then(function(response){
		$scope.friendsList=response.data;
	},function(response){
		alert("Internal Server Error!!!");
	})
}

if($location.path()=='/getFriendRequests'){
	FriendService.getFriendRequests().then(function(response){
		$scope.friendsList=response.data;
	},function(response){
		alert("Internal Server Error!!!");
	})
}

if($location.path()=='/getSentRequests'){
	FriendService.getSentRequests().then(function(response){
		$scope.friendsList=response.data;
	},function(response){
		alert("Internal Server Error!!!");
	})
}


})