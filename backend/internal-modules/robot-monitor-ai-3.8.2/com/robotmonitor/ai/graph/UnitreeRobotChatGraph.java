/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.cloud.ai.graph.CompiledGraph
 *  com.alibaba.cloud.ai.graph.KeyStrategy
 *  com.alibaba.cloud.ai.graph.KeyStrategyFactory
 *  com.alibaba.cloud.ai.graph.KeyStrategyFactoryBuilder
 *  com.alibaba.cloud.ai.graph.OverAllState
 *  com.alibaba.cloud.ai.graph.RunnableConfig
 *  com.alibaba.cloud.ai.graph.StateGraph
 *  com.alibaba.cloud.ai.graph.action.AsyncNodeAction
 *  com.alibaba.cloud.ai.graph.action.NodeAction
 *  com.alibaba.cloud.ai.graph.exception.GraphStateException
 *  com.alibaba.cloud.ai.graph.node.QuestionClassifierNode
 *  com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.config.service.IConfigAudioService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.robotmonitor.ai.graph;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.KeyStrategyFactoryBuilder;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.action.AsyncNodeAction;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.alibaba.cloud.ai.graph.node.QuestionClassifierNode;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import com.robotmonitor.ai.graph.AccessGraph;
import com.robotmonitor.ai.graph.FaqChatGraph;
import com.robotmonitor.ai.graph.FlightGraph;
import com.robotmonitor.ai.graph.IntroducingPlacesGraph;
import com.robotmonitor.ai.service.AiFlightService;
import com.robotmonitor.ai.service.ChatClientService;
import com.robotmonitor.ai.service.PlaceService;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.config.service.IConfigAudioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitreeRobotChatGraph {
    private static final Logger log = LoggerFactory.getLogger(UnitreeRobotChatGraph.class);

    public CompiledGraph build(ChatClientService chatClientService, PlaceService placeService, AiFlightService flightService, IConfigAudioService configAudioService) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("user_input", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("user_message", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("location", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("place_name", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph robotChatGraph = new StateGraph("yushu-robot-chat-graph", keyStrategyFactory);
        QuestionClassifierNode intentClassifier = QuestionClassifierNode.builder().chatClient(chatClientService.getToolsChatClient()).inputTextKey("user_message").categories(Map.of("FAQ", "FAQ", "INTRODUCING_PLACES", "INTRODUCING_PLACES", "FINDING_PLACES", "FINDING_PLACES", "FLIGHT", "FLIGHT", "ACCESS", "ACCESS", "OTHER", "OTHER")).classificationInstructions(List.of("\u4f60\u662f\u4e00\u4e2a\u529f\u80fd\u9009\u62e9\u52a9\u624b\uff0c\u8bf7\u6839\u636e\u7528\u6237\u7684\u63d0\u95ee\u5185\u5bb9\uff0c\u5339\u914d\u5173\u952e\u8bcd\u5e76\u7ed3\u5408\u8bed\u4e49\uff0c\u5224\u65ad\u7528\u6237\u7684\u771f\u5b9e\u610f\u56fe\uff0c\u5e76\u8fd4\u56de\u6240\u5c5e\u4e1a\u52a1\u7c7b\u578b\u3002\n\n\u4e1a\u52a1\u7c7b\u578b\u53ea\u80fd\u4ece\u4ee5\u4e0b5\u7c7b\u4e2d\u9009\u62e9\u8fd4\u56de\uff0c\u65e0\u6cd5\u5339\u914d\u65f6\u5fc5\u987b\u4e25\u683c\u8fd4\u56de\u201cOTHER\u201d\u3002\n\n\u3010\u8bf7\u4e25\u683c\u4ec5\u8fd4\u56de\u5982\u4e0b5\u79cd\u7c7b\u578b\u4e4b\u4e00\u3011\uff1a\n- FAQ\n- INTRODUCING_PLACES\n- FINDING_PLACES\n- FLIGHT\n- ACCESS\n- OTHER\n\n---\n\n\u3010\u5224\u65ad\u89c4\u5219\u5982\u4e0b\u3011\uff1a\n\n1. FAQ\uff08\u4ecb\u7ecd\u4e1a\u52a1\uff09\uff1a\n   - \u5305\u542b\u4ee5\u4e0b\u5173\u952e\u8bcd\uff1a\u4ecb\u7ecd\u3001\u8bb2\u4e00\u4e0b\u3001\u8bf4\u660e\u3001\u4e86\u89e3\u4e00\u4e0b\u3001\u80fd\u5426\u3001\u6709\u5565\u670d\u52a1\u3001\u5141\u8bb8\u4e0d\u5141\u8bb8\u3001\u600e\u4e48\u529e\u3001\u600e\u4e48\u5904\u7406\u3001\u5e26\u4e0d\u5e26\u3001\u5a74\u513f\u8f66\u3001\u5ba0\u7269\u3001\u6709\u65e0\u3001\u666e\u901a\u4f1a\u5458\u80fd\u8fdb\u3001\u8c01\u53ef\u4ee5\u8fdb\u3001\u51c6\u5165\u6761\u4ef6\u3001\u9700\u8981\u4ec0\u4e48\u8d44\u683c\u3001\u51e0\u70b9\u5f00\u3001\u8425\u4e1a\u65f6\u95f4\u3001\u5f00\u653e\u65f6\u95f4\u3001\u670d\u52a1\u65f6\u95f4\u7b49\uff1b\n   - \u8868\u793a\u7528\u6237\u5e0c\u671b\u4e86\u89e3\u6216\u4f7f\u7528\u67d0\u9879\u670d\u52a1\uff0c\u5982\uff1a\u503c\u673a\u3001\u53d6\u7968\u3001\u9884\u7ea6\u3001\u529e\u7406\u767b\u673a\u7b49\u3002\n   - \u6ce8\u610f\uff1a\n     - \u5373\u4fbf\u7528\u6237\u63d0\u5230\u8d35\u5bbe\u5ba4\u4f4d\u7f6e\uff0c\u53ea\u8981\u6ca1\u6709\u6280\u672f\u5165\u573a\u95ee\u9898\uff0c\u4e5f\u5c5e\u4e8e FAQ\uff1b\n     - \u65f6\u95f4\u7c7b\u95ee\u9898\uff08\u51e0\u70b9\u5f00\u3001\u8425\u4e1a\u65f6\u95f4\u7b49\uff09\u4f18\u5148\u5224\u5b9a\u4e3a FAQ\uff1b\n     - \u5982\u679c\u7528\u6237\u63d0\u95ee\u5305\u542b\u4f4d\u7f6e\u5173\u952e\u8bcd\uff0c\u4f46\u8bed\u4e49\u662f\u67e5\u8be2\u4e8b\u5b9e\u6216\u5e38\u8bc6\uff0c\u800c\u975e\u5b9e\u9645\u5bfb\u8def\uff0c\u5219\u5f52\u7c7b FAQ\u3002\n\n2. INTRODUCING_PLACES\uff08\u4ecb\u7ecd\u5730\u70b9\uff09\uff1a\n   -- \u5fc5\u987b\u5305\u542b\u4ee5\u4e0b\u5730\u70b9\u5173\u952e\u8bcd\uff1a{place_name}\n   -- \u8868\u793a\u7528\u6237\u60f3\u8981\u4e86\u89e3\u5173\u952e\u8bcd\u4e2d\u5730\u70b9\u7684\u4fe1\u606f\uff0c\u4f8b\u5982\uff1a\u4ecb\u7ecd\u4e00\u4e0b\u3001\u8bb2\u4e00\u4e0b\u3001\u4e86\u89e3\u4e00\u4e0b\u3001\u60f3\u77e5\u9053\u3001\u6709\u5565\u670d\u52a1\u3001\u8bf4\u660e\u4e00\u4e0bXX\uff08\u67d0\u67d0\u5fc5\u987b\u51fa\u73b0\u5728\u5173\u952e\u8bcd\u4e2d\uff09\u8ba4\u4e3a\u9700\u8981\u4ecb\u7ecd\u5730\u70b9\u3002\n\n3. FINDING_PLACES\uff08\u5bfb\u8def\u5bfc\u822a\uff09\uff1a\n   - \u5305\u542b\u4ee5\u4e0b\u5173\u952e\u8bcd\uff1a\u600e\u4e48\u8d70\u3001\u5728\u54ea\u3001\u600e\u4e48\u53bb\u3001\u8def\u7ebf\u3001\u65b9\u4f4d\u3001\u5165\u53e3\u3001\u4f4d\u7f6e\u7b49\uff1b\n   - **\u4e14\u7528\u6237\u610f\u56fe\u5fc5\u987b\u660e\u786e\u8868\u793a\u8981\u77e5\u9053\u5982\u4f55\u5230\u8fbe\u67d0\u5904\uff0c\u5426\u5219\u4e0d\u8981\u5224\u5b9a\u4e3a FINDING_PLACES**\uff1b\n   - **\u5982\u679c\u7528\u6237\u63d0\u95ee\u662f\u6a21\u7cca\u4f4d\u7f6e\u95ee\u9898\u3001\u4e8b\u5b9e\u67e5\u8be2\u3001\u65f6\u95f4\u67e5\u8be2\u6216\u4e00\u822c\u6027\u4fe1\u606f\uff08\u4f8b\u5982\u201c\u8fd9\u91cc\u662f\u54ea\u91cc\u201d\uff09\uff0c\u5fc5\u987b\u6392\u9664FINDING_PLACES**\u3002\n\n4. FLIGHT\uff08\u822a\u73ed\u52a8\u6001\uff09\uff1a\n   - \u660e\u786e\u5305\u542b\u822a\u73ed\u53f7\uff08\u5982 MU5121\u3001CA1234\uff09\uff1b\n   - \u7528\u6237\u60f3\u83b7\u53d6\u8be5\u822a\u73ed\u7684\u52a8\u6001\uff08\u8d77\u98de\u3001\u5230\u8fbe\u3001\u5ef6\u8bef\u7b49\uff09\u3002\n\n5. ACCESS\uff08\u8d35\u5bbe\u5ba4\u51c6\u5165\u529e\u7406\uff09\uff1a\n   - \u5305\u542b\u201c\u626b\u7801\u201d\u3001\u201c\u4e8c\u7ef4\u7801\u201d\u3001\u201c\u8bc6\u522b\u5931\u8d25\u201d\u3001\u201c\u8bc6\u522b\u4e0d\u51fa\u6765\u201d\u3001\u201c\u5237\u7801\u201d\u3001\u201c\u65e0\u6cd5\u8fdb\u5165\u201d\u3001\u201c\u8fdb\u95e8\u5931\u8d25\u201d\u3001\u201c\u534a\u8f6c\u5165\u201d\u3001\u201c\u8f6c\u5165\u201d\u3001\u201c\u8fdb\u4e0d\u6765\u201d\u3001\u201c\u8fdb\u4e0d\u53bb\u201d\u3001\u201c\u5206\u5165\u201d\u7b49\u8bcd\uff1b\n   - \u8868\u793a\u7528\u6237\u5728\u8fdb\u5165\u8d35\u5bbe\u5ba4\u65f6\u9047\u5230**\u6280\u672f\u95ee\u9898\u6216\u5165\u573a\u5931\u8d25**\uff1b\n   - **\u5355\u7eaf\u54a8\u8be2\u8d35\u5bbe\u5ba4\u653f\u7b56\u3001\u670d\u52a1\u3001\u529e\u7406\u4e1a\u52a1\u3001\u503c\u673a\u3001\u9884\u7ea6\u3001\u53d6\u7968\u7b49\u4e0d\u5c5e\u4e8e ACCESS**\uff1b\n   - \u5176\u4ed6\u573a\u666f\u7684\u201c\u8fdb\u4e0d\u53bb\u201d\u201c\u529e\u7406\u5931\u8d25\u201d\u7b49\u8868\u8ff0\u4e5f\u4e0d\u5c5e\u4e8e ACCESS\uff0c**\u4f8b\u5982\u503c\u673a\u3001\u9884\u7ea6\u3001\u53d6\u7968\u3001\u767b\u8bb0\u7b49\u4e1a\u52a1**\uff0c\u5fc5\u987b\u660e\u786e\u6307\u5411\u8d35\u5bbe\u5ba4\u5165\u573a\u95ee\u9898\u624d\u80fd\u5224\u5b9a\u4e3a ACCESS\u3002\n\n6. OTHER\uff08\u65e0\u6cd5\u8bc6\u522b\uff09\uff1a\n   - \u7528\u6237\u63d0\u95ee\u6a21\u7cca\uff0c\u6216\u8005\u4e0d\u5305\u542b\u4e0a\u8ff0\u4efb\u4e00\u7c7b\u7684\u7279\u5f81\u5173\u952e\u8bcd\u6216\u8bed\u4e49\u3002\n   - \u5982\u679c\u4f4d\u7f6e\u7c7b\u8bcd\u6c47\u65e0\u6cd5\u660e\u786e\u5bfc\u822a\u610f\u56fe\uff0c\u5e76\u4e14\u4e0d\u5c5e\u4e8e FAQ \u7684\u4fe1\u606f\u67e5\u8be2\uff0c\u5219\u5f52\u7c7b OTHER\u3002\n\n---\n\n\u3010\u5f3a\u5236\u6ce8\u610f\u4e8b\u9879\u3011\uff1a\n- **\u5728\u5224\u65ad FINDING_PLACES \u524d\uff0c\u5fc5\u987b\u5148\u786e\u8ba4\u7528\u6237\u610f\u56fe\u660e\u786e\u8868\u793a\u8981\u77e5\u9053\u5982\u4f55\u5230\u8fbe\u67d0\u5730\uff0c\u5426\u5219\u7edd\u4e0d\u5224\u5b9a\u4e3a FINDING_PLACES**\uff1b\n- **\u5728\u5224\u65ad INTRODUCING_PLACES \u524d\uff0c\u5fc5\u987b\u5148\u786e\u8ba4\u7528\u6237\u610f\u56fe\u660e\u786e\u8868\u793a\u8981\u4e86\u89e3\u5173\u952e\u8bcd\u5185\u5730\u70b9\u7684\u4fe1\u606f\uff0c\u5426\u5219\u7edd\u4e0d\u5224\u5b9a\u4e3a INTRODUCING_PLACES**\uff1b\n- \u5728\u5224\u5b9aFAQ\u524d\uff0c\u9700\u8981\u5148\u770b\u4e00\u4e0b\u662f\u5426\u662f\u5c5e\u4e8eINTRODUCING_PLACES\uff0c\u6b64\u4f18\u5148\u7ea7\u9ad8\u4e8eFAQ\uff0c\u4f46\u662f\u4e00\u5b9a\u8981\u7b26\u5408\u5173\u952e\u8bcd\uff0c\u5426\u5219\u4ecd\u7136\u662fFAQ\n- \u5982\u679c\u7528\u6237\u63d0\u95ee\u662f\u6a21\u7cca\u4f4d\u7f6e\u95ee\u9898\uff08\u4f8b\u5982\u201c\u8fd9\u91cc\u662f\u54ea\u91cc\u201d\u201c\u67d0\u67d0\u5730\u65b9\u662f\u4ec0\u4e48\u5730\u65b9\u201d\uff09\uff0c\u6216\u8005\u53ea\u662f\u67e5\u8be2\u4e8b\u5b9e/\u5e38\u8bc6\uff0c**\u7edd\u5bf9\u4e0d\u80fd\u5f52\u7c7b\u4e3a FINDING_PLACES**\uff1b\n- \u6a21\u7cca\u4f4d\u7f6e\u95ee\u9898\u9ed8\u8ba4\u5f52 FAQ\uff08\u5982\u679c\u662f\u4fe1\u606f\u67e5\u8be2\uff09\u6216 OTHER\uff08\u5982\u679c\u610f\u56fe\u4e0d\u660e\u786e\uff09\u3002\n\n---\n\n\u3010\u8bf7\u4ec5\u8fd4\u56de\u3011\uff1aFAQ / INTRODUCING_PLACES / FINDING_PLACES / FLIGHT / ACCESS / OTHER \u4e2d\u7684\u4e00\u4e2a\uff0c\u4e0d\u52a0\u6ce8\u91ca\u6216\u89e3\u91ca\u3002\n\n/no_think\n")).outputKey("intent_type").build();
        robotChatGraph.addNode("intent", AsyncNodeAction.node_async((NodeAction)intentClassifier));
        robotChatGraph.addEdge("__START__", "intent");
        NodeAction callFunctionNode = state -> {
            String intent = state.value("intent_type").orElse("OTHER");
            String userInput = state.value("user_input").orElse("");
            String robotId = state.value("robot_id").orElse("");
            String location = state.value("location").orElse("");
            String language = state.value("language").orElse("");
            RobotChatResponse robotChatResponse = new RobotChatResponse();
            CompiledGraph compiledGraph = switch (intent) {
                case "FAQ" -> new FaqChatGraph().build(chatClientService, configAudioService, false);
                case "INTRODUCING_PLACES" -> new IntroducingPlacesGraph().build(chatClientService, placeService, configAudioService);
                case "ACCESS" -> new AccessGraph().build(configAudioService);
                case "FLIGHT" -> new FlightGraph().build(chatClientService, flightService, configAudioService);
                default -> new FaqChatGraph().build(chatClientService, configAudioService, true);
            };
            String subThreadId = "sub-event-" + UUID.randomUUID();
            Optional subResult = compiledGraph.call(Map.of("user_input", userInput, "robot_id", robotId, "location", location, "language", language), RunnableConfig.builder().threadId(subThreadId).build());
            if (subResult.isPresent()) {
                Object resultObj = ((OverAllState)subResult.get()).value("response").orElse(null);
                if (resultObj instanceof RobotChatResponse) {
                    robotChatResponse = resultObj;
                } else if (resultObj instanceof Map) {
                    robotChatResponse = new RobotChatResponse((Map)resultObj);
                }
            }
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        robotChatGraph.addNode("callFunction", AsyncNodeAction.node_async((NodeAction)callFunctionNode));
        robotChatGraph.addEdge("intent", "callFunction");
        NodeAction answerNode = state -> {
            RobotChatResponse response = state.value("response").orElse(new RobotChatResponse("NONE", null));
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", response);
            return resultMap;
        };
        robotChatGraph.addNode("mainReply", AsyncNodeAction.node_async((NodeAction)answerNode));
        robotChatGraph.addEdge("callFunction", "mainReply");
        robotChatGraph.addEdge("mainReply", "__END__");
        return robotChatGraph.compile();
    }
}
