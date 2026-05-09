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
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import java.util.HashMap;

public class NoneGraph {
    public CompiledGraph build() throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph noneGraph = new StateGraph("none-graph", keyStrategyFactory);
        NodeAction noneChatNode = state -> {
            String robotId = state.value("robot_id").orElse("");
            String language = state.value("language").orElse("CN");
            RobotChatResponse robotChatResponse = new RobotChatResponse();
            robotChatResponse.setRobotId(robotId);
            robotChatResponse.setLanguage(language);
            robotChatResponse.setEventType("NONE");
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        noneGraph.addNode("none", AsyncNodeAction.node_async((NodeAction)noneChatNode));
        noneGraph.addEdge("__START__", "none");
        noneGraph.addEdge("none", "__END__");
        return noneGraph.compile();
    }
}
