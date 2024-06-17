package com.matuto.springaidemo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ChatClintController {

    // 智能对话的客户端

//    @Autowired
//    private ChatClient chatClient;
//
//
//    @GetMapping("/chat")
//    public String generation(@RequestParam("message") String message) {
//        // prompt 提示词
//        return this.chatClient.prompt()
//                // 用户信息，即用户输入
//                .user(message)
//                // 远程请求
//                .call()
//                // 返回一个文本
//                .content();
//    }
//
//    @GetMapping(value = "/stream", produces = "text/html;charset=utf-8")
//    public Flux<String> stream(@RequestParam("message") String message) {
//        Flux<String> output = chatClient.prompt()
//                .user(message)
//                // 单独设置角色
//                .system("你是一个计算机老师")
//                // 流式回答
//                .stream()
//                .content();
//        return output;
//    }



}
