package com.matuto.springaidemo.controller;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiImageModel;
import org.springframework.ai.zhipuai.ZhiPuAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhipu")
public class ZhiPuImageController {

    private final ZhiPuAiImageModel zhipuaiImageModel;
    @Autowired
    public ZhiPuImageController(ZhiPuAiImageModel zhipuaiImageModel) {
        this.zhipuaiImageModel = zhipuaiImageModel;
    }

    @GetMapping(value = "/testImage")
    public String testImage(@RequestParam("message") String message) {
        ImageResponse response = zhipuaiImageModel.call(
                // 图片提示词
                new ImagePrompt(message,
                        ZhiPuAiImageOptions.builder()
                                .build())

        );
        return response.getResult().getOutput().getUrl();
    }

}
