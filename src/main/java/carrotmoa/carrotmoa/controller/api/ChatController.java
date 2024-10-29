package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {
    @GetMapping("/chat{id}")
    public ResponseEntity<List<ChatMessage>> getChatmessage(@PathVariable long id){
        ChatMessage testMessage = new ChatMessage(1L,"test1","test");
        return new ResponseEntity<List<ChatMessage>>(List.of(testMessage), HttpStatus.OK);

    }

}
