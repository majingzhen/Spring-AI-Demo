package com.matuto.springaidemo.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/model")
public class ChatModelController {

    // 智能对话的客户端
//    @Autowired
//    private OpenAiChatModel chatModel;


    @Autowired
    private OpenAiImageModel openaiImageModel;

    @Autowired
    private OpenAiAudioSpeechModel openaiAudioSpeechModel;

    @Autowired
    private OpenAiAudioTranscriptionModel openAiTranscriptionModel;


//    @GetMapping("/chat")
//    public String generation(@RequestParam("message") String message) {
//        ChatResponse output = chatModel.call(
//                new Prompt(
//                        // 输入，提示词
//                        message, // = new UserMessage(message),
//                        // 配置项
//                        OpenAiChatOptions.builder()
//                                // 设置模型
//                                //.withModel("gpt-4-32k")
//                                // 温度，值越高，输出越随机 0 - 1 之间
//                                .withTemperature(0.4f)
//                                .build()
//                ));
//        return output.getResult().getOutput().getContent();
//    }
//
//    @GetMapping(value = "/stream", produces = "text/html;charset=utf-8")
//    public Flux<ChatResponse> stream(@RequestParam("message") String message) {
//        Flux<ChatResponse> output = chatModel.stream(
//                        new Prompt(
//                                message,
//                                OpenAiChatOptions.builder()
//                                        //.withModel("gpt-4-32k")
//                                        .withTemperature(0.4f)
//                                        .build()
//                        )
//                );
//        return output;
//    }

    @GetMapping(value = "/testImage")
    public String testImage(@RequestParam("message") String message) {
        ImageResponse response = openaiImageModel.call(
                // 图片提示词
                new ImagePrompt(message,
                        OpenAiImageOptions.builder()
                                // 让图片更加清晰
                                .withQuality("hd")
                                .withN(4)
                                .withHeight(1024)
                                .withWidth(1024).build())

        );
        return response.getResult().getOutput().getUrl();
    }

    @GetMapping(value = "/testAudio")
    public String testAudio(@RequestParam("message") String message) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                // 模型
                .withModel("tts-1")
                // 人声模型
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                // 文件格式
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                // 语速
                .withSpeed(1.0f)
                .build();
        // message 生成的内容
        SpeechPrompt speechPrompt = new SpeechPrompt(message, speechOptions);
        SpeechResponse response = openaiAudioSpeechModel.call(speechPrompt);
        // mp3 文件的二进制流
        byte[] output = response.getResult().getOutput();
        // 保存到本地
        try {
            writeByteArrayToMp3(output, System.getProperty("user.dir"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }

    public static void writeByteArrayToMp3(byte[] byteArray, String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath + "/test.mp3");
        fos.write(byteArray);
        fos.close();
    }

    @GetMapping(value = "/testTranscription")
    public String testTranscription() {

        // 生成语音翻译的可选配置
        var transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                // 温度，不需要任何加工
                .withTemperature(0f)
                .build();

        var audioFile = new ClassPathResource("/test.mp3");
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        AudioTranscriptionResponse response = openAiTranscriptionModel.call(transcriptionRequest);
        return response.getResult().getOutput();
    }

}
