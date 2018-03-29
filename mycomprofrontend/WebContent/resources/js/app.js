var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/home',{
		templateUrl:'views/home.html',
		controller:'HomeController'
	})
	.when('/',{
		templateUrl:'views/home.html',
		controller:'HomeController'
	})
	.when('/aboutUs',{
		templateUrl:'views/aboutUs.html'
	})
	.when('/register',{
		templateUrl:'views/register.html',
		controller:'UserController'
	})
	.when('/changePass',{
		templateUrl:'views/changePass.html',
		controller:'UserController',
		resolve:{factory:checkLogin}
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/addJob',{
		templateUrl:'views/addJob.html',
		controller:'JobController',
		resolve:{factory:checkLogin}

	})
	.when('/updateJob/:id',{
		templateUrl:'views/addJob.html',
		controller:'JobController',
		resolve:{factory:checkLogin}
	})
	.when('/viewJob/:id',{
		templateUrl:'views/viewJob.html',
		controller:'JobController'
	})
	.when('/jobs',{
		templateUrl:'views/AllJobs.html',
		controller:'JobController'
	})
	.when('/viewAppliedUser/:id',{
		templateUrl:'views/viewAppliedUser.html',
		controller:'JobController',
		resolve:{factory:checkLogin}
	})
	.when('/403Error',{
		templateUrl:'views/403.html'
	})
	.when('/addBlog',{
		templateUrl:'views/addBlog.html',
		controller:'BlogController',
		resolve:{factory:checkLogin}
	})
	.when('/updateBlog/:id',{
		templateUrl:'views/addBlog.html',
		controller:'BlogController',
		resolve:{factory:checkLogin}
	})
	.when('/viewBlog/:id',{
		templateUrl:'views/viewBlog.html',
		controller:'BlogController'
	})
	.when('/blogsToApprove',{
		templateUrl:'views/blogToApproved.html',
		controller:'BlogController',
		resolve:{factory:checkLogin}
	})
	.when('/blogs',{
		templateUrl:'views/allBlogs.html',
		controller:'BlogController'
	})
	.when('/getSuggestedFriends',{
		templateUrl:'views/suggestedFriends.html',
		controller:'FriendController',
		resolve:{factory:checkLogin}
	})
	.when('/getFriendsList',{
		templateUrl:'views/myFriends.html',
		controller:'FriendController',
		resolve:{factory:checkLogin}
	})
	.when('/getFriendRequests',{
		templateUrl:'views/friendRequests.html',
		controller:'FriendController',
		resolve:{factory:checkLogin}
	})
	.when('/getSentRequests',{
		templateUrl:'views/sentRequests.html',
		controller:'FriendController',
		resolve:{factory:checkLogin}
	})
	.when('/viewProfile/:id',{
		templateUrl:'views/myAccount.html',
		controller:'ViewProfileController',
		resolve:{factory:checkLogin}
	})
	.when('/editProfile/:id',{
		templateUrl:'views/updateUser.html',
		controller:'UserController',
		resolve:{factory:checkLogin}
	})
	.when('/chatRoom',{
		templateUrl:'views/chatRoom.html',
		controller:'ChatController',
		resolve:{factory:checkLogin}
	})
	.otherwise({
		templateUrl:'views/404.html'
	})
})
app.run(function($rootScope,$cookieStore,$route,UserService,ChatService,$location){
	
	$rootScope.logout=function(){
        
		UserService.logout().then(function(response){
				$rootScope.interceptURL=undefined;
				$rootScope.logoutMsg="Loggedout Successfully.."
        		ChatService.stompClient.disconnect();
        		delete $rootScope.currentUser
        		$cookieStore.remove("currentUser")
        		$location.path('/login');
        },function(response){
        	$rootScope.logoutMsg=response.data.message;
        	$location.path('/login')
        })		
	}
	
	if($rootScope.currentUser==undefined)
		$rootScope.currentUser=$cookieStore.get("currentUser")
	if($rootScope.currentUser!=undefined){
		ChatService.connect();
	}
	$rootScope.$on('updateChatVar',function(event,flag){
		if(flag){
			if(	$location.path()!='/chatRoom'){
		$("#note").animate({top:'10px'}, "slow")
	    $("#note").animate({top:'10px'}, "slow")
	    $("#note").animate({top:'-160px'}, "slow")
		}}
	});

})
var checkLogin=function($rootScope,$location){
	if($rootScope.currentUser==undefined){
		$rootScope.interceptURL=$location.path();
		$rootScope.logoutMsg="Login First !!!!"
		$location.path('/login');}
	else
		return true;
}