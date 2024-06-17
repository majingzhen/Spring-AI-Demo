package com.matuto.springaidemo.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fc")
public class FunctionCallController {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Autowired
    private ZhiPuAiChatModel zhiPuChatModel;

    @GetMapping("/chat")
    public String fc(@RequestParam(value = "message") String message) {

        UserMessage userMessage = new UserMessage(message);

        ChatResponse response = zhiPuChatModel.call(new Prompt(List.of(userMessage),
                ZhiPuAiChatOptions.builder().withFunction("locationWeatherFunction").build()));

        return response.getResult().getOutput().getContent();

    }


}
