package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.ResourceRequestDTO;
import edu.dadaev.greenpoint.dto.ResourceResponseDTO;
import edu.dadaev.greenpoint.security.CustomUserDetails;
import edu.dadaev.greenpoint.service.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceController {

    private ResourceService resourceService;

    @GetMapping("/resources")
    public ResponseEntity<List<ResourceResponseDTO>> getResources(){
        return ResponseEntity.ok(resourceService.getResources());
    }

    @PostMapping("/resources")
    public ResponseEntity<ResourceResponseDTO> createResource(@ModelAttribute ResourceRequestDTO resourceRequestDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        ResourceResponseDTO resourse = resourceService.createResourse(resourceRequestDTO, userDetails.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(resourse);
    }

}
