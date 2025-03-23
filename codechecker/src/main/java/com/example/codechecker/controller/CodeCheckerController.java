package com.example.codechecker.controller;

import com.example.codechecker.service.CodeCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

@Controller
public class CodeCheckerController {

    @Autowired
    private CodeCheckerService codeCheckerService;

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/check")
    public String checkResult(@RequestParam("code") String code, Model model) {
        // Synchronous analysis for a one-shot check
        String result = codeCheckerService.analyzeCode(code).block();
        model.addAttribute("code", code);
        model.addAttribute("result", result);
        return "index";
    }

    // New endpoint for real-time analysis using SSE
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<ServerSentEvent<String>> streamAnalysis(@RequestParam("code") String code) {
        return codeCheckerService.analyzeCodeStream(code);
    }
}
