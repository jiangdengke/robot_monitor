/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.cloud.ai.graph.CompiledGraph
 *  com.alibaba.cloud.ai.graph.KeyStrategy
 *  com.alibaba.cloud.ai.graph.KeyStrategyFactory
 *  com.alibaba.cloud.ai.graph.KeyStrategyFactoryBuilder
 *  com.alibaba.cloud.ai.graph.StateGraph
 *  com.alibaba.cloud.ai.graph.action.AsyncNodeAction
 *  com.alibaba.cloud.ai.graph.action.NodeAction
 *  com.alibaba.cloud.ai.graph.exception.GraphStateException
 *  com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.config.service.IConfigAudioService
 *  org.apache.logging.log4j.util.Strings
 *  org.springframework.ai.chat.client.ChatClient
 */
package com.robotmonitor.ai.graph;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.KeyStrategyFactoryBuilder;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.action.AsyncNodeAction;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import com.robotmonitor.ai.service.ChatClientService;
import com.robotmonitor.ai.utils.AiUtils;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.config.service.IConfigAudioService;
import java.util.HashMap;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.chat.client.ChatClient;

public class FaqChatGraph {
    public CompiledGraph build(ChatClientService chatClientService, IConfigAudioService configAudioService, boolean isOther) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("user_input", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("location", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph faqChatGraph = new StateGraph("faq-chat-graph", keyStrategyFactory);
        NodeAction faqChatNode = state -> {
            String userInput = state.value("user_input").orElse("");
            String robotId = state.value("robot_id").orElse("");
            String location = state.value("location").orElse("");
            String language = state.value("language").orElse("CN");
            String prompt = userInput + "\n\u8bf7\u4f7f\u7528\u201c%s\u201d\u8bed\u8a00\u6765\u56de\u7b54\n".formatted(AiUtils.getPromptLanguage(language));
            ChatClient chatClient = isOther ? chatClientService.getOtherKnowledgeBaseChat() : chatClientService.getKnowledgeBaseChat();
            String message = AiUtils.removeThinkTag(chatClient.prompt(prompt).advisors(a -> a.param("chat_memory_conversation_id", (Object)robotId)).call().content());
            RobotChatResponse robotChatResponse = FaqChatGraph.faq(isOther, robotId, message, language, location, configAudioService);
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        faqChatGraph.addNode("faqChat", AsyncNodeAction.node_async((NodeAction)faqChatNode));
        faqChatGraph.addEdge("__START__", "faqChat");
        faqChatGraph.addEdge("faqChat", "__END__");
        return faqChatGraph.compile();
    }

    public static RobotChatResponse faq(boolean isOther, String robotId, String message, String language, String location, IConfigAudioService configAudioService) {
        RobotChatResponse robotChatResponse = new RobotChatResponse(isOther ? "OTHER" : "FAQ");
        if (Strings.isNotBlank((String)message) && message.contains("Unknown")) {
            AiUtils.setDefaultMessageAndVoice(configAudioService, language, location, "KNOWLEDGE_CHAT_UNKNOWN", robotChatResponse);
        } else {
            robotChatResponse.setMessage(message);
        }
        robotChatResponse.setRobotId(robotId);
        robotChatResponse.setLanguage(language);
        return robotChatResponse;
    }
}
