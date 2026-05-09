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
import java.util.HashMap;

public class NormalChatGraph {
    public CompiledGraph build(ChatClientService chatClientService) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("user_input", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph normalChatGraph = new StateGraph("normal-chat-graph", keyStrategyFactory);
        NodeAction normalChatNode = state -> {
            String userInput = state.value("user_input").orElse("");
            String robotId = state.value("robot_id").orElse("");
            String language = state.value("language").orElse("CN");
            String prompt = userInput + "\n\u8bf7\u4f7f\u7528\u201c%s\u201d\u8bed\u8a00\u6765\u56de\u7b54\n".formatted(AiUtils.getPromptLanguage(language));
            String message = AiUtils.removeThinkTag(chatClientService.getChatClient().prompt(prompt).advisors(a -> a.param("chat_memory_conversation_id", (Object)robotId)).call().content());
            RobotChatResponse robotChatResponse = new RobotChatResponse("OTHER", message);
            robotChatResponse.setRobotId(robotId);
            robotChatResponse.setLanguage(language);
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        normalChatGraph.addNode("normalChat", AsyncNodeAction.node_async((NodeAction)normalChatNode));
        normalChatGraph.addEdge("__START__", "normalChat");
        normalChatGraph.addEdge("normalChat", "__END__");
        return normalChatGraph.compile();
    }
}
