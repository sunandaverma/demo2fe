app.controller('JobController',function(JobService,$scope,$location,$route,$routeParams,$rootScope){
	$scope.job={}
	$scope.jobs=[]
	$scope.appliedUsers=[]
	$scope.jobLogin=function(){
		$rootScope.interceptURL=$location.path();
		$location.path('/login');
	}
	$scope.addJob=function(){		
		JobService.addJob($scope.job).then(function(response){
			alert("Job added successfully!!!");
			$location.path('/jobs');
		},function(response){
			$('html,body').scrollTop(0);
			console.log(response.status);
			if(response.data.code==6){
				$scope.message=response.data.message;
				$location.path('/403Error');
			}
			else if(response.data.code==5){
				$location.path('/login');
			}
			else{		
				alert("Internal Server Error!!!");
				$location.path('/jobs');
			}
		});
	}
	
	$scope.deleteJob=function(id){		
		JobService.deleteJob(id).then(function(response){
			alert("Job deleted successfully!!!");
			$location.path('/jobs');
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
				$location.path('/jobs');
			}
		});
	}
	
	
	if($location.path()=='/jobs'){
		JobService.getAllJobs().then(function(response){
			$scope.jobs=response.data;
		},function(response){
			alert("Internal Server Error!!!");
		})
	}
	if($location.path().substring(0,11)=="/updateJob/"){
		var id=$routeParams.id;
		JobService.getJobById(id).then(function(response){
			$scope.job=response.data;
		},function(response){
			alert("Internal Server Error!!!");
			$location.path('/jobs');
		})
	}
	
	if($location.path().substring(0,9)=="/viewJob/"){
		var id=$routeParams.id;
		JobService.getJobById(id).then(function(response){
			$scope.job=response.data;
			if($scope.job.job_title==undefined)
				$location.path('/404Error');
			
		},function(response){
			alert("Internal Server Error!!!");
			$location.path('/jobs');
		})
		if($rootScope.currentUser!=undefined)
		JobService.checkIfApplied(id).then(function(response){
			$scope.applied=response.data;
		},function(response){
			alert(respose.data.message);
		})
	}
	

	$scope.applyForJob=function(id){		
		JobService.applyForJob(id).then(function(response){
			alert("Applied successfully!!!");
			$route.reload();
		},function(response){
			$('html,body').scrollTop(0);
			console.log(response.status);
			if(response.data.code==5){
				$scope.message="login first!!!";
				$location.path('/login');
			}
			else{		
				alert("Internal Server Error!!!");
				$location.path('/jobs');
			}
		});
	}
	

	if($location.path().substring(0,17)=="/viewAppliedUser/"){
		var id=$routeParams.id;
		JobService.getJobById(id).then(function(response){
			$scope.job=response.data;
		},function(response){
			alert("Internal Server Error!!!");
			$location.path('/jobs');
		})
		JobService.getAllAppliedUser(id).then(function(response){
			$scope.appliedUsers=response.data;
		},function(response){
			$('html,body').scrollTop(0);
			console.log(response.status);
			if(response.data.code==6){
				$scope.message=response.data.message;
				$location.path('/403Error');
			}
			else if(response.data.code==5){
				$location.path('/login');
			}
			else{		
				alert("Internal Server Error!!!");
			}
		})
	}
	
})