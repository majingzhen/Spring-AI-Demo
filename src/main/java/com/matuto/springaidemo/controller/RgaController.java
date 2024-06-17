package com.matuto.springaidemo.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rga")
public class RgaController {

    @Autowired
    private VectorStore vectorStore;

    @GetMapping("/chat")
    public List<Document> chat(@RequestParam(value = "message") String message) {

        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("country", "UK", "year", 2020)),
                new Document("The World is Big and Salvation Lurks Around the Corner", Map.of()),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("country", "NL", "year", 2023)));
        // Add the documents to Redis
        vectorStore.add(documents);

        List<Document> results = vectorStore.similaritySearch(
                SearchRequest
                        .query("Spring")
                        .withTopK(5));
        return results;

    }

}
