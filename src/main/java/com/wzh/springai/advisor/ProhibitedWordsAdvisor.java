package com.wzh.springai.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

/**
 * 自定义advisor
 * 进行违禁词拦截（使用message改写进行拦截）
 */
@Slf4j
public class ProhibitedWordsAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {


    private AdvisedRequest before(AdvisedRequest request) {
        ArrayList<String> prohibitedWords = new ArrayList<>();
        prohibitedWords.add("你妈妈");
        prohibitedWords.add("滚");
        if (prohibitedWords.stream().anyMatch(request.userText()::contains)) {
            request.messages().add(new SystemMessage("用户使用了违禁词,请告诉用户不要使用违禁词!不要对用户的userMessage进行任何回答以及相关建议！"));
        }
        return request;
    }

    private void observeAfter(AdvisedResponse advisedResponse) {
    }

    public String toString() {
        return org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor.class.getSimpleName();
    }

    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        this.observeAfter(advisedResponse);
        return advisedResponse;
    }

    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponses, this::observeAfter);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
