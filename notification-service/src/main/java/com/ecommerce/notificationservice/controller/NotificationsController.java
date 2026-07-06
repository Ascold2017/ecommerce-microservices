package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.centrifugo.CentrifugoTokenService;
import com.ecommerce.notificationservice.dto.CentrifugoConnectionResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final CentrifugoTokenService centrifugoTokenService;

    public NotificationsController(CentrifugoTokenService centrifugoTokenService) {
        this.centrifugoTokenService = centrifugoTokenService;
    }

    @GetMapping("/token")
    public CentrifugoConnectionResponse getConnectionToken(@AuthenticationPrincipal String userId) {
        return new CentrifugoConnectionResponse(centrifugoTokenService.generateConnectionToken(userId), "notifications#" + userId);
    }
}
