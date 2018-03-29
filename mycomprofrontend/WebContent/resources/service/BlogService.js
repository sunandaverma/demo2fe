/**
 * 
 */
app.factory('BlogService',function($http){
	var blogService={};
	var BASE_URL="http://localhost:8081/mycomprobackend"
		
	blogService.addBlogPost=function(blog){
		return $http.post(BASE_URL+"/addBlogPost",blog)
	}
	
	blogService.deleteBlogPost=function(id){
		return $http['delete'](BASE_URL+"/deleteBlogPost/"+id)
	}
	
	blogService.getAllBlogPost=function(approved){
		return $http.get(BASE_URL+"/getAllBlogPost/"+approved)
	}
	
	blogService.getAllBlogPostByUser=function(id){
		return $http.get(BASE_URL+"/getAllBlogPostByUser/"+id)
	}
	
	blogService.getBlogPostById=function(id){
		return $http.get(BASE_URL+"/getBlogPostById/"+id)
	}
	
	blogService.approveBlogPost=function(id){
		return $http.get(BASE_URL+"/approveBlogPost/"+id)
	}
	
	blogService.rejectBlogPost=function(id){
		return $http.get(BASE_URL+"/rejectBlogPost/"+id)
	}
	
	blogService.getAllBlogComment=function(id){
		return $http.get(BASE_URL+"/getAllBlogComment/"+id)
	}
	
	blogService.addBlogComment=function(blogComment){
		return $http.post(BASE_URL+"/addBlogComment",blogComment)
	}
	
	return blogService;
})
