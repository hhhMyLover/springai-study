package com.wzh.springai.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * reReading advisor
 * 两次读取用户的提问，提升命中率
 */
@Slf4j
public class ReReadingAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

	/**
	 * 请求发送前，改写Prompt
	 * @param advisedRequest
	 * @return
	 */
	private AdvisedRequest before(AdvisedRequest advisedRequest) {

		Map<String, Object> advisedUserParams = new HashMap<>(advisedRequest.userParams());
		advisedUserParams.put("re2_input_query", advisedRequest.userText());

		// advise 变量传递方法
//		advisedRequest.updateContext(content -> {
//			content.put("userParams", advisedUserParams);
//			return content;
//		});

		// 调用方法
//		advisedRequest.adviseContext().get("userParams");

		return AdvisedRequest.from(advisedRequest)
				.userText("""
			    {re2_input_query}
			    Read the question again: {re2_input_query}
			    """)
				.userParams(advisedUserParams)
				.build();
	}

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
		return chain.nextAroundCall(this.before(advisedRequest));
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
		return chain.nextAroundStream(this.before(advisedRequest));
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}