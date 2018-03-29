package mycompro.mycomprobackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import mycompro.mycomprobackend.model.Chat;

@Controller
public class SockController {
	private final SimpMessagingTemplate messagingTemplate;
	private List<String> users = new ArrayList<String>();
	public void onLogout(String user){
		users.remove(user);
		messagingTemplate.convertAndSend("/topic/leave",user);
	}	
	@Autowired
	public SockController(SimpMessagingTemplate messagingTemplate){
		this.messagingTemplate=messagingTemplate;
	}
	
	@SubscribeMapping("/join/{username}")
	public List<String> join(@DestinationVariable("username") String username){
		if(!users.contains(username))
		users.add(username);
	messagingTemplate.convertAndSend("/topic/join",username);
	return users;
	}
	
	@MessageMapping("/chat")
	public void chatReveived(Chat chat){
		if("all".equals(chat.getTo()))
			messagingTemplate.convertAndSend("/queue/chats",chat);
		else{
			messagingTemplate.convertAndSend("/queue/chats/"+chat.getTo(),chat);
			messagingTemplate.convertAndSend("/queue/chats/"+chat.getFrom(),chat);
			}
	}
}
