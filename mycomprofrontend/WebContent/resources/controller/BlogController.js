app.controller('BlogController',function(BlogService,$scope,$rootScope,$location,$route,$routeParams){
	$scope.blog={}
	$scope.blogs=[]
	$scope.commentLogin=function(){
			$rootScope.interceptURL=$location.path();
			$location.path('/login');
	}
	var x;
	$("#myFile").change(function(){
			x=this.files[0].size;
	});
	$scope.addBlog=function(){		
			if(x>307200){
				alert("Image size must be under 300KB !!!!!");
			return false;	
			}
				else{
					BlogService.addBlogPost($scope.blog).then(function(response){
						$("#file-upload-button").trigger('click');
						$location.path('/viewProfile/'+$rootScope.currentUser.username);
						alert("Blog added successfully!!!");
					},function(response){
						$('html,body').scrollTop(0);
						console.log(response.status);
						if(response.data.code==5){
							$scope.message="login first!!!";
							$location.path('/login');
						}
						else{		
							alert("Internal Server Error!!!");
						}
					});
				
		}
	}
	
	if($location.path().substring(0,12)=="/updateBlog/"){
		var id=$routeParams.id;
		BlogService.getBlogPostById(id).then(function(response){
			$scope.blog=response.data;
			if($scope.blog.blog_title==undefined)
				$location.path('/404Error');
		},function(response){
			alert("Internal Server Error!!!");
		})
	}

	if($location.path().substring(0,10)=="/viewBlog/"){
		var id=$routeParams.id;
		BlogService.getBlogPostById(id).then(function(response){
			$scope.blog=response.data;
			if($scope.blog.blog_title==undefined)
				$location.path('/404Error');
			BlogService.getAllBlogComment(id).then(function(response){
				$scope.allBlogComment=response.data;
			},function(response){
				console.log(response.status);
			})	
			
		},function(response){
			alert("Internal Server Error!!!");
		})
	}
	
	if($location.path()=='/blogs'){
		var approved='A';
		BlogService.getAllBlogPost(approved).then(function(response){
			console.log(response.data);
			$scope.blogs=response.data;
		},function(response){
			alert("Internal Server Error!!!");
		})
	}
	
	if($location.path()=='/blogsToApprove'){
		if($rootScope.currentUser.role=='admin'){
		var approved='NA';
		BlogService.getAllBlogPost(approved).then(function(response){
			$scope.blogs=response.data;
		},function(response){
			alert("Internal Server Error!!!");
		})
		approved='R';
		BlogService.getAllBlogPost(approved).then(function(response){
			$scope.rejectedBlogs=response.data;
		},function(response){
			alert("Internal Server Error!!!");
		})}
		else
			$location.path('/403Error');
	}
	
	$scope.approveBlog=function(id){
		BlogService.approveBlogPost(id).then(function(response){
			alert("Blog Post Approved");
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

	$scope.rejectBlog=function(id){
		BlogService.rejectBlogPost(id).then(function(response){
			alert("Blog Post Rejected");
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
	
	$scope.addBlogComment=function(){		
		$scope.blogComment.blogPost=$scope.blog;
		BlogService.addBlogComment($scope.blogComment).then(function(response){
			$scope.blogComment.commentText='';
			BlogService.getAllBlogComment($scope.blog.blog_id).then(function(response){
				$scope.allBlogComment=response.data;
			},function(response){
				console.log(response.status);
			})
		},function(response){
			console.log(response.status);
			if(response.data.code==5){
				$location.path('/login');
			}
			else{		
				alert("Internal Server Error!!!");
			}
		});
	}
	
	
})