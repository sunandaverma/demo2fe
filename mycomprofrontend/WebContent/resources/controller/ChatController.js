app.controller('ChatController',function(ChatService,$scope,$rootScope,FriendService){
	$scope.chats=ChatService.chats;
	$scope.users=ChatService.users;
	$scope.chat={}
	$scope.currentChatUser={}
	$scope.sendMessage = function(){
		$scope.chat.from = $rootScope.currentUser.username;
		ChatService.stompClient.send("/app/chat",{},JSON.stringify($scope.chat));
		$rootScope.$broadcast('sendingChat',$scope.chat);
		$scope.chat.message='';
	}
	
	$scope.capitalize = function(str){
		var x=String(''+str).charAt(0)
		return x.toUpperCase()+str.slice(1);
	}
		
	$scope.$on('sendingChat',function(event,sentChat){
		chat=angular.copy(sentChat);
		chat.from='Me';
		chat.direction='right';
		ChatService.addChat(chat);
	});

	$scope.$on('updateChatVar',function(event,flag){
		if(flag){
			$scope.chats=ChatService.chats;
			$("#chatBox").scrollTop($("#chatBox").prop("scrollHeight"));
		}
		else
			$scope.users=ChatService.users;		
		$scope.$apply();
		if(flag){
			$("#chatBox").scrollTop($("#chatBox").prop("scrollHeight"));
		}
	});
	
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
	$scope.setChatTo=function(username){
		$scope.chat.to=username;
		if($(window).width()<754){
			$("#chatUserTable").hide();
			$(".chatList").css('height','100%');
			}
		}
	
	$scope.chatFromFilter=function(friend){
		if($scope.chat.to==friend.to || ($scope.chat.to==friend.from && $rootScope.currentUser.username==friend.to)){
			return true;
		}
		else
			return false;
		}
})