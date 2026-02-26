package com.example.ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import com.example.ai.domain.request.AdCopyRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

    // content => 단순 텍스트 답변
    @SuppressWarnings("null")
    public String exam1(String userInput, String systemMessage) {
        return chatClient.prompt().user(userInput).system(systemMessage).call().content();
    }

    // chatResponse => 토큰 수, 모델명, 종료 이유 등 메타데이터 등 전체 응답 정보
    @SuppressWarnings("null")
    public ChatResponse exam2(String userInput, String systemMessage) {
        return chatClient.prompt().user(userInput).system(systemMessage).call().chatResponse();
    }

    // prompt()
    @SuppressWarnings("null")
    public ChatResponse exam3(String userInput, String systemMessage, String model) {

        // openai chat completion 모델에서 사용하는 옵션인 모델지정, 최대토큰설정 등이 가능
        ChatOptions chatOptions = ChatOptions.builder().model(model).build();

        Prompt prompt = Prompt.builder()
                .messages(SystemMessage.builder().text(systemMessage).build(),
                        UserMessage.builder().text(userInput).build())
                .chatOptions(chatOptions)
                .build();

        return chatClient.prompt(prompt).call().chatResponse();
    }

    @SuppressWarnings("null")
    public String exam4(String userInput, String conversionId) {
        return chatClient.prompt().advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversionId)).user(userInput)
                .call().content();
    }

    // 리액트에서 프롬프트를 입력받는다
    @SuppressWarnings("null")
    public String adGenerate(AdCopyRequest request) {
        Prompt prompt = Prompt.builder()
                .messages(
                        UserMessage.builder().text(PromptFactory.render(request.name(),
                                request.brand(),
                                request.strength(),
                                request.tone(),
                                request.keyword(),
                                request.value())).build())
                .build();

        return chatClient.prompt(prompt).call().content();
    }

    public EmbeddingResponse embed(String message) {
        @SuppressWarnings("null")
        EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(message));
        return embeddingResponse;
    }
}
