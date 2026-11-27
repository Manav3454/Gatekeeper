package com.GateKeeper.Analytic_service.Controller;


import com.GateKeeper.Analytic_service.DTO.Response.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    private String servicename;
    private String servicedescription;

    public HealthController(@Value("${info.service.description}") String servicedescription,
                            @Value("${info.service.name}") String sservicename) {
        this.servicedescription = servicedescription;
        this.servicename = sservicename;
    }

    @GetMapping
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok
                (new HealthResponse(servicename, servicedescription, "UP"));
    }
}