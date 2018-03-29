app.directive('markable',function(){
	return function(scope, element, attrs){
		element.on('click',function(){
			$("td").removeClass("active").css('color','white');
			element.addClass("active").css('color','black');
		});
	}
});

app.factory('ChatService',function($rootScope){
	var chatService={}
	var userName;
	chatService.users=[];
	chatService.chats=[];
	addUser = function(user){
		chatService.users.push(user);
	}
	
	chatService.addChat = function(chat){
		chatService.chats.push(chat);
	}
	

	processIncomingMessage = function(message){
		message=JSON.parse(message.body);
		message.direction='left';
		if(message.from != userName){
			chatService.addChat(message);
		}
	}
	
	chatService.connect=function(){
	userName=$rootScope.currentUser.username;
	
	var socket = new SockJS('http://localhost:8081/mycomprobackend/portfolio');
	
	var stompClient=Stomp.over(socket);
	
	stompClient.connect({},function(frame){
		stompClient.subscribe("/topic/join",function(message){
			console.log(message.body+" join");
			user=message.body;
			if(user != userName && $.inArray(user,chatService.users) == -1){
				addUser(user);
				$rootScope.$broadcast('updateChatVar',false);
			}
		});
		stompClient.subscribe("/topic/leave",function(message){
			console.log(message.body+" leave");
			user=message.body;
			if(user != userName){
				chatService.users.splice(chatService.users.indexOf(user),1);
				$rootScope.$broadcast('updateChatVar',false);
			}
		});
		stompClient.subscribe('/app/join/'+userName,function(message){
			chatService.users=JSON.parse(message.body);
			$rootScope.$broadcast('updateChatVar',false);
		});
		stompClient.subscribe("/queue/chats",function(message){
			processIncomingMessage(message,true);
			$rootScope.$broadcast('updateChatVar',true);
		});
	
		stompClient.subscribe('/queue/chats/'+userName,function(message){
			processIncomingMessage(message,false);
			$rootScope.$broadcast('updateChatVar',true);
		});
	});
	chatService.stompClient=stompClient;
	}
	
	return chatService;
});	
