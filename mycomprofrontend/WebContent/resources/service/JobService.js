/**
 * 
 */
app.factory('JobService',function($http){
	var jobService={};
	var BASE_URL="http://localhost:8081/mycomprobackend"
		
	jobService.addJob=function(job){
		return $http.post(BASE_URL+"/addJob",job)
	}
	
	jobService.deleteJob=function(id){
		return $http['delete'](BASE_URL+"/deleteJob/"+id)
	}
	
	jobService.getJobById=function(id){
		return $http.get(BASE_URL+"/getJobById/"+id)
	}
	
	jobService.getAllJobs=function(){
		return $http.get(BASE_URL+"/getAllJobs")
	}
	
	jobService.checkIfApplied=function(id){
		return $http.get(BASE_URL+"/checkIfApplied/"+id)
	}
	
	jobService.getAllAppliedUser=function(id){
		return $http.get(BASE_URL+"/getAllAppliedUser/"+id)
	}
	
	jobService.applyForJob=function(id){
		return $http.get(BASE_URL+"/applyForJob/"+id)
	}
	
	return jobService;
})
