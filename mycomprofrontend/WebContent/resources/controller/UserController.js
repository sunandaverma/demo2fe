app.controller('UserController',function(ChatService,UserService,$scope,$rootScope,$location,$cookieStore,$route,$routeParams){
	$scope.user={}
	if($location.path().substring(0,13)=='/editProfile/'){
		var id=$routeParams.id;
		if($rootScope.currentUser.username==id){
		UserService.getUser(id).then(function(response){
			$scope.user=response.data;
		},function(response){
			$location.path('/home');
		})}
		else
			$location.path('/403Error');
		
	}
	$scope.registerUser=function(){		
		UserService.registerUser($scope.user).then(function(response){
			alert("Registered successfully..");
			$location.path('/login');
		},function(response){
			$('html,body').scrollTop(0);
			console.log(response.status);
			$scope.message=response.data.message;
			$location.path('/register');
		});
	}
	
	$scope.validateUser=function(){
		UserService.validateUser($scope.user).then(function(response){
			$rootScope.currentUser=response.data
			$cookieStore.put("currentUser",response.data)
			ChatService.connect();
			if($rootScope.interceptURL!=undefined)
				$location.path($rootScope.interceptURL);
			else
				$location.path('/home');
		},function(response){
			$('html,body').scrollTop(0);
			$scope.message=response.data.message;
			$location.path('/login')
		})
	}
	
	$scope.getUser=function(){
		UserService.getUser().then(function(response){
			$scope.user=response.data;
		},function(response){
			$location.path('/home');
		}
	)}
	

	$scope.changePass=function(){		
		if($scope.currentPassword==$rootScope.currentUser.password){
			UserService.updateUser($rootScope.currentUser).then(function(response){
				$rootScope.currentUser.password=$scope.newPassword;
				alert("Password Changed successfully!!");
				$location.path('/home');
			},function(response){
				$('html,body').scrollTop(0);
				$scope.message="Error!!!";
				$location.path('/changePass');
		});
		}
		else{
			$('html,body').scrollTop(0);
			$scope.message="Wrong password!!!";
			$location.path('/changePass');		
		}
	
	}
	$scope.updateUser=function(){		
		UserService.updateUser($scope.user).then(function(response){
			$rootScope.currentUser.firstname=$scope.user.firstname;
			$rootScope.currentUser.lastname=$scope.user.lastname;
			$location.path('/viewProfile/'+$rootScope.currentUser.username);
			},function(response){
				$('html,body').scrollTop(0);
				$scope.message=response.data.message;
		});
	}
	
	if($rootScope.logoutMsg!=undefined){
		$scope.$on('$locationChangeStart',function(){
			$rootScope.logoutMsg='';
		});
	}
	
	if($rootScope.currentUser!=undefined&&($location.path()=='/login'||$location.path()=='/register')){
		$location.path('/')
	}
	
})

