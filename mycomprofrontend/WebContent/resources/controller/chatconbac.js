app.controller('ChatController',function(FriendService,$scope,$rootScope,socket){
	alert('Entering Chat Controller');
	$scope.chats=[];
	$scope.stompClient = socket.stompClient;
	$scope.users=[];
	$scope.$on('sockConnected',function(event,frame){
		alert('SockConnected');
		$scope.userName=$rootScope.currentUser.username;
		$scope.stompClient.subscribe("/topic/join",function(message){
			console.log(message.body);
			user=message.body;
			if(user != $scope.userName && $.inArray(user,$scope.users) == -1){
				$scope.addUser(user);
				$scope.latestUser=user;
				$scope.$apply();
				$('#joinedChat').fadeIn(100).delay(5000).fadeOut(200);
			}
		});
	
		$scope.stompClient.subscribe('/app/join/'+$scope.userName,function(message){
			$scope.users=JSON.parse(message.body);
			$scope.$apply();	
		});
	});
	
	$scope.sendMessage = function(chat){
		chat.from = $scope.userName;
		$scope.stompClient.send("/app/chat",{},JSON.stringify(chat));
		$rootScope.$broadcast('sendingChat',chat);
		$scope.chat.message='';
	}
	
	$scope.capitalize = function(str){
		return str.chatAt(0).toUpperCase()+str.slice(1);
	}
	
	$scope.addUser = function(user){
		$scope.users.push(user);
		$scope.$apply();
	}
	

	$scope.$on('sockConnected',function(event,frame){
		$scope.userName=$rootScope.currentUser.username;
		$scope.user=$rootScope.currentUser.username;
		$scope.stompClient.subscribe("/queue/chats",function(message){
			$scope.processIncomingMessage(message,true);
		});
	
		$scope.stompClient.subscribe('/queue/chats/'+$scope.userName,function(message){
			//console.log("hello"+message);
			$scope.processIncomingMessage(message,false);
		});
	});
	
	$scope.$on('sendingChat',function(event,sentChat){
		chat=angular.copy(sentChat);
		chat.from='Me';
		chat.direction='outgoing';
		$scope.addChat(chat);
	});
	
	$scope.processIncomingMessage = function(message,isBroadcast){
		message=JSON.parse(message.body);
		message.direction='incoming';
		if(message.from != $scope.userName){
			$scope.addChat(message);
			$scope.$apply();
		}
	}
	
	$scope.addChat = function(chat){
		$scope.chats.push(chat);
	}
	
	//my code starts here

		FriendService.getFriendsList().then(function(response){
			$scope.friendsList=response.data;
		},function(response){
			alert("Internal Server Error!!!");
		})

		$scope.chatfriendfilter=function(friend){
			if($scope.users.indexOf(friend.username)!==-1){
				return true
			}
			else
				return false
		}

		$scope.$on('$locationChangeStart',function(event){
			var res=confirm("Are you sure you wat to leave chat room ?");
			if(res)
				$scope.stompClient.disconnect();
		});
})/**
 * 
 */