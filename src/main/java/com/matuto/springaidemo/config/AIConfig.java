package com.matuto.springaidemo.config;

import com.matuto.springaidemo.functions.LocationWeatherFunction;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class AIConfig {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

//    @Bean
//    ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("你现在不是chatgpt了，你是一个私人的心理咨询专家，只会提供专业的心理咨询问题解答。")
//                .build();
//    }

    @Bean
    @Description("某个地方天气怎么样") // 告诉 大模型 什么时候调用这个函数
    public Function<LocationWeatherFunction.Request, LocationWeatherFunction.Response> locationWeatherFunction() {
        return new LocationWeatherFunction();
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        RedisVectorStore.RedisVectorStoreConfig config = RedisVectorStore.RedisVectorStoreConfig.builder()
                .withURI("redis://127.0.0.1:6379")
                // Define the metadata fields to be used
                // in the similarity search filters.
                .withMetadataFields(
                        RedisVectorStore.MetadataField.tag("country"),
                        RedisVectorStore.MetadataField.numeric("year"))
                .build();

        return new RedisVectorStore(config, embeddingModel, true);
    }
}
