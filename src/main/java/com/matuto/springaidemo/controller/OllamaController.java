package com.matuto.springaidemo.controller;

import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ollama")
public class OllamaController {

    private final OllamaChatModel chatModel;


    @Autowired
    public OllamaController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/mutil")
    public String mutilModel(@RequestParam(value = "message", defaultValue = "你从这个图片看到了什么？") String message) throws IOException {
        // 将图片转换为二进制流
        byte[] imageData = new ClassPathResource("/multimodal.test.png").getContentAsByteArray();

        var userMessage = new UserMessage(message,
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG, imageData)));

        ChatResponse response = chatModel.call(
                new Prompt(List.of(userMessage), OllamaOptions.create().withModel("llava")));
        return response.getResults().get(0).getOutput().getContent();
    }


}
