app.controller('ViewProfileController',function(BlogService,UserService,FriendService,$scope,$rootScope,$location,$cookieStore,$route,$routeParams){
	$scope.user={}
	$scope.blogs=[]
	$scope.friend={}
	if($location.path().substring(0,13)=='/viewProfile/'){
		var id=$routeParams.id;
		UserService.getUser(id).then(function(response){
			$scope.user=response.data;
		},function(response){
			$location.path('/home');
		})
		BlogService.getAllBlogPostByUser(id).then(function(response){
			$scope.blogs=response.data;
			if($rootScope.currentUser.username!=$scope.user.username)
				$scope.x=function(temp){
				if(temp.approved=='NA'||temp.approved=='R')
					return false;
				return true;
			}
		},function(response){
			alert("Internal Server Error!!!");
		})
		FriendService.getFriend(id).then(function(response){
			$scope.friend=response.data;
		},function(response){
			alert("Internal Server Error!!!");
		})
	}

	$scope.deleteBlog=function(id){		
		BlogService.deleteBlogPost(id).then(function(response){
			alert("BlogPost deleted successfully!!!");
			$route.reload();
		},function(response){
			$('html,body').scrollTop(0);
			console.log(response.status);
			if(response.data.code==6){
				$scope.message=response.data.message;
				$location.path('/403Error');
		}
			else if(response.data.code==5){
				$scope.message="login first!!!";
				$location.path('/login');
			}
			else{		
				alert("Internal Server Error!!!");
				$route.reload();
				}
		});
	}


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

})