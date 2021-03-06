package org.thekiddos.manager.api.controllers;

import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.api.model.MessageListDTO;
import org.thekiddos.manager.gui.controllers.MessengerController;
import org.thekiddos.manager.services.WaiterChatService;

@Slf4j
@Controller
@RequestMapping("/api/chat")
public class WaiterChatController {
    private final WaiterChatService waiterChatService;
    private final MessengerController messengerController;

    @Autowired
    public WaiterChatController( WaiterChatService waiterChatService, MessengerController messengerController ) {
        this.waiterChatService = waiterChatService;
        this.messengerController = messengerController;
    }

    @PostMapping
    private ResponseEntity<MessageListDTO> processAcknowledgment() {
        waiterChatService.setAcknowledged();
        ResponseEntity<MessageListDTO> responseEntity = new ResponseEntity<>( waiterChatService.getPendingMessages(), HttpStatus.OK );
        waiterChatService.clearPendingMessages();
        if ( Util.isGuiInitialized() ) {
            Platform.runLater( messengerController::setWaiterMessagesRead );
        }
        else {
            log.warn( "GUI not initialized. Testing Mode is assumed." );
        }
        return responseEntity;
    }
}
