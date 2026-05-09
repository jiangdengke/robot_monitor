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
 *  com.robotmonitor.common.utils.DateUtils
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
import com.robotmonitor.ai.converter.MyBeanOutputConverter;
import com.robotmonitor.ai.domain.AiFlightInfo;
import com.robotmonitor.ai.domain.FlightRequestInfo;
import com.robotmonitor.ai.service.AiFlightService;
import com.robotmonitor.ai.service.ChatClientService;
import com.robotmonitor.ai.utils.AiUtils;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.config.service.IConfigAudioService;
import java.util.HashMap;

public class FlightGraph {
    public CompiledGraph build(ChatClientService chatClientService, AiFlightService flightService, IConfigAudioService configAudioService) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("user_input", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph flightPlaceGraph = new StateGraph("flight-graph", keyStrategyFactory);
        NodeAction flightNode = state -> {
            String userInput = state.value("user_input").orElse("");
            String robotId = state.value("robot_id").orElse("");
            String language = state.value("language").orElse("CN");
            RobotChatResponse robotChatResponse = new RobotChatResponse();
            robotChatResponse.setRobotId(robotId);
            robotChatResponse.setLanguage(language);
            robotChatResponse.setEventType("FLIGHT");
            String prompt = "\u4f60\u662f\u4e00\u4e2a\u822a\u73ed\u52a8\u6001\u8bc6\u522b\u52a9\u624b\uff0c\u8bf7\u4ece\u7528\u6237\u7684\u63d0\u95ee\u4e2d\u8bc6\u522b\u822a\u73ed\u76f8\u5173\u7684\u4fe1\u606f\uff0c\u5982\u679c\u4fe1\u606f\u4e2d\u6ca1\u6709\u65e5\u671f\u548c\u65f6\u95f4\uff0c\u822a\u73ed\u65e5\u671f\u8fd4\u56de\u7a7a\n\u7528\u6237\u7684\u95ee\u9898\uff1a%s\n\u6807\u51c6\u822a\u73ed\u53f7\uff0c\u4f8b\u5982\uff1aMU4321\n\u6807\u51c6\u65e5\u671f\uff0c\u4f8b\u5982\uff1a20240101\n/no_think\n".formatted(userInput);
            FlightRequestInfo flightRequestInfo = (FlightRequestInfo)chatClientService.getToolsChatClient().prompt(prompt).call().entity(new MyBeanOutputConverter(FlightRequestInfo.class));
            if (null != flightRequestInfo) {
                AiFlightInfo flight = flightService.findFlight(flightRequestInfo.getFlightNo(), flightRequestInfo.getFlightDate());
                if (null != flight) {
                    String answerPrompt = "\u7528\u6237\u7684\u63d0\u95ee\u5982\u4e0b\uff1a%s\n\u7528\u6237\u9700\u8981\u67e5\u8be2\u7684\u822a\u73ed\u4fe1\u606f\uff0c\u5f53\u524d\u65f6\u95f4\u662f%s\uff0c\u67e5\u8be2\u5230\u7684\u822a\u73ed\u4fe1\u606f\u5982\u4e0b\uff1a%s\n\n\u8bf7\u6839\u636e\u4ee5\u4e0b\u822a\u73ed\u72b6\u6001\u5224\u65ad\u5e76\u7ec4\u7ec7\u8bed\u8a00\n\n\u6ce8\u610f\uff1a\u9700\u8981\u68c0\u67e5`latestOffStatus`\u3001`domFlightAbstate`\u3001`intFlightAbstate`\u7b49\u5b57\u6bb5\u4ee5\u786e\u5b9a\u6b63\u786e\u72b6\u6001\u3002`\n\n/no_think\n".formatted(userInput, DateUtils.dateTimeNow((String)"yyyy\u5e74MM\u6708dd\u65e5HH\u70b9mm\u5206ss\u79d2"), flight.toString()) + "\n\u8bf7\u4f7f\u7528\u201c%s\u201d\u8bed\u8a00\u6765\u56de\u7b54\n".formatted(AiUtils.getPromptLanguage(language));
                    String message = AiUtils.removeThinkTag(chatClientService.getChatClient().prompt(answerPrompt).advisors(a -> a.param("chat_memory_conversation_id", (Object)robotId)).call().content());
                    robotChatResponse.setMessage(message);
                } else {
                    AiUtils.setDefaultMessageAndVoice(configAudioService, language, robotId, "FLIGHT_NOT_FOUND", robotChatResponse);
                }
            } else {
                AiUtils.setDefaultMessageAndVoice(configAudioService, language, robotId, "KNOWLEDGE_CHAT_UNKNOWN", robotChatResponse);
            }
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        flightPlaceGraph.addNode("flight", AsyncNodeAction.node_async((NodeAction)flightNode));
        flightPlaceGraph.addEdge("__START__", "flight");
        flightPlaceGraph.addEdge("flight", "__END__");
        return flightPlaceGraph.compile();
    }

    public static RobotChatResponse flight(String robotId, String language, String message, boolean isFindFlight, IConfigAudioService configAudioService) {
        RobotChatResponse robotChatResponse = new RobotChatResponse();
        robotChatResponse.setRobotId(robotId);
        robotChatResponse.setLanguage(language);
        robotChatResponse.setEventType("FLIGHT");
        if (isFindFlight) {
            robotChatResponse.setMessage(message);
        } else {
            AiUtils.setDefaultMessageAndVoice(configAudioService, language, robotId, "FLIGHT_NOT_FOUND", robotChatResponse);
        }
        return robotChatResponse;
    }
}
