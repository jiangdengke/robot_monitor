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
import com.robotmonitor.ai.utils.AiUtils;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.config.service.IConfigAudioService;
import java.util.HashMap;

public class AccessGraph {
    public CompiledGraph build(IConfigAudioService configAudioService) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("location", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph accessGraph = new StateGraph("access-graph", keyStrategyFactory);
        NodeAction faqChatNode = state -> {
            String robotId = state.value("robot_id").orElse("");
            String location = state.value("location").orElse("");
            String language = state.value("language").orElse("CN");
            RobotChatResponse robotChatResponse = AccessGraph.access(robotId, language, location, configAudioService);
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        accessGraph.addNode("access", AsyncNodeAction.node_async((NodeAction)faqChatNode));
        accessGraph.addEdge("__START__", "access");
        accessGraph.addEdge("access", "__END__");
        return accessGraph.compile();
    }

    public static RobotChatResponse access(String robotId, String language, String location, IConfigAudioService configAudioService) {
        RobotChatResponse robotChatResponse = new RobotChatResponse();
        robotChatResponse.setRobotId(robotId);
        robotChatResponse.setLanguage(language);
        robotChatResponse.setEventType("ACCESS");
        AiUtils.setDefaultMessageAndVoice(configAudioService, language, location, "ACCESS_START", robotChatResponse);
        robotChatResponse.setCmd(Long.valueOf(789012L));
        return robotChatResponse;
    }
}
