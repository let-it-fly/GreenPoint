package edu.dadaev.greenpoint.controller;

import edu.dadaev.greenpoint.dto.ResourceRequestDTO;
import edu.dadaev.greenpoint.dto.ResourceResponseDTO;
import edu.dadaev.greenpoint.service.ResourceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResourceResponseDTO> createResource(@RequestBody ResourceRequestDTO resourceRequestDTO){
        ResourceResponseDTO resourse = resourceService.createResourse(resourceRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(resourse);
    }
}
