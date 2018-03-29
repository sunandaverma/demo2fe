app.controller('HomeController',function(BlogService,JobService,$scope,$rootScope,$location,$route,$routeParams){
	$scope.blogs=[]
	$scope.jobs=[]
	var approved='A';
		BlogService.getAllBlogPost(approved).then(function(response){
			$scope.blogs=response.data;
			if($scope.blogs.length>5){
			$scope.blogs=$scope.blogs.slice(0,5);
			}
		},function(response){
			alert("Internal Server Error!!!");
		})	
		JobService.getAllJobs().then(function(response){
			$scope.jobs=response.data;
		},function(response){
			alert("Internal Server Error!!!");
		})	
})