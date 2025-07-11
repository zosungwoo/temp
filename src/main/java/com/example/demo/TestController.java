package com.example.demo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/test")
    public ResponseEntity<Void> getRestaurantCandidates(@RequestBody MemberInteractionMessage memberInteractionMessage){
        testService.sendTestMessage(memberInteractionMessage);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test2")
    public ResponseEntity<Void> getRestaurantCandidates2(){
        testService.test();
        return ResponseEntity.ok().build();
    }

}
