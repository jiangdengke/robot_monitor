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
 *  com.robotmonitor.common.core.domain.robot.RobotChatImage
 *  com.robotmonitor.common.core.domain.robot.RobotChatResponse
 *  com.robotmonitor.config.service.IConfigAudioService
 *  org.apache.logging.log4j.util.Strings
 *  org.springframework.ai.chat.prompt.ChatOptions
 *  org.springframework.util.CollectionUtils
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
import com.robotmonitor.ai.domain.PlaceImage;
import com.robotmonitor.ai.domain.PlaceInfo;
import com.robotmonitor.ai.service.ChatClientService;
import com.robotmonitor.ai.service.PlaceService;
import com.robotmonitor.ai.utils.AiUtils;
import com.robotmonitor.common.core.domain.robot.RobotChatImage;
import com.robotmonitor.common.core.domain.robot.RobotChatResponse;
import com.robotmonitor.config.service.IConfigAudioService;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.util.CollectionUtils;

public class FindPlaceGraph {
    public CompiledGraph build(ChatClientService chatClientService, PlaceService placeService, IConfigAudioService configAudioService) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("user_input", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("location", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph findingPlaceGraph = new StateGraph("finding-place-graph", keyStrategyFactory);
        NodeAction placeNode = state -> {
            String userInput = state.value("user_input").orElse("");
            String robotId = state.value("robot_id").orElse("");
            String location = state.value("location").orElse("");
            String language = state.value("language").orElse("CN");
            RobotChatResponse robotChatResponse = new RobotChatResponse();
            robotChatResponse.setRobotId(robotId);
            robotChatResponse.setLanguage(language);
            robotChatResponse.setEventType("FINDING_PLACES");
            String placeNames = placeService.getPlaceNames(location, language);
            if (Strings.isNotBlank((String)placeNames)) {
                String prompt = "\u4f60\u662f\u4e00\u4e2a\u5730\u70b9\u8bc6\u522b\u52a9\u624b\uff0c\u8bf7\u4ece\u7528\u6237\u63d0\u95ee\u4e2d\u8bc6\u522b\u5730\u70b9\uff0c\u5e76\u8fd4\u56de\u8be5\u5730\u70b9\u7684\u6807\u51c6\u5730\u5740\u540d\u79f0\uff08\u7528\u4e8e\u6570\u636e\u5e93\u68c0\u7d22\uff09\uff0c\u5c3d\u91cf\u5339\u914d\u884c\u653f\u533a\u5212\u6216\u5e38\u89c1\u5b98\u65b9\u79f0\u8c13\u3002\n\u3010\u8bf7\u4e25\u683c\u4ec5\u8fd4\u56de\u5982\u4e0b\u4e4b\u4e00\u3011\uff1a\n%s\n\n\u5982\u679c\u7528\u6237\u8be2\u95ee\u7684\u5730\u70b9\u4e0d\u5728\u5217\u8868\u4e2d\uff0c\u8bf7\u8fd4\u56de\u7a7a\u5b57\u7b26\u4e32\"\"\u3002\n\u7528\u6237\u95ee\u9898\uff1a%s\n\u3010\u8bf7\u4ec5\u8fd4\u56de\u3011\uff1a%s \u6216\u8005\u7a7a\u5b57\u7b26\u4e32\"\"\u4e2d\u7684\u4e00\u4e2a\uff0c\u4e0d\u52a0\u6ce8\u91ca\u6216\u89e3\u91ca\u3002\n/no_think\n".formatted(placeNames, userInput, placeNames);
                String placeName = AiUtils.removeThinkTag(chatClientService.getToolsChatClient().prompt(prompt).options(ChatOptions.builder().temperature(Double.valueOf(0.0)).build()).advisors(a -> a.param("chat_memory_conversation_id", (Object)robotId)).call().content());
                if (Strings.isBlank((String)placeName)) {
                    FindPlaceGraph.setNotFindMessageAndVoice(robotChatResponse, language, location, configAudioService);
                } else {
                    PlaceInfo locationInfo = placeService.findPlaceInfo(location, placeName, language, robotId);
                    if (null == locationInfo) {
                        FindPlaceGraph.setNotFindMessageAndVoice(robotChatResponse, language, location, configAudioService);
                    } else {
                        robotChatResponse.setAreaId("" + locationInfo.getId());
                        robotChatResponse.setAreaName(locationInfo.getName());
                        robotChatResponse.setMessage(FindPlaceGraph.getMessage(language, locationInfo.getName()));
                        robotChatResponse.setPictures(FindPlaceGraph.convertToRobotChatImage(locationInfo.getPictures()));
                        robotChatResponse.setLocationInfo(locationInfo.getCoordinate());
                        robotChatResponse.setButtonName(FindPlaceGraph.getButtonName(language));
                        if (Strings.isNotBlank((String)locationInfo.getAudioUrl())) {
                            robotChatResponse.setExtraInfo(locationInfo.getAudioUrl());
                        }
                    }
                }
            } else {
                FindPlaceGraph.setNotFindMessageAndVoice(robotChatResponse, language, location, configAudioService);
            }
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        findingPlaceGraph.addNode("findPlace", AsyncNodeAction.node_async((NodeAction)placeNode));
        findingPlaceGraph.addEdge("__START__", "findPlace");
        findingPlaceGraph.addEdge("findPlace", "__END__");
        return findingPlaceGraph.compile();
    }

    public static RobotChatResponse findingPlace(String robotId, String language, String location, String placeName, IConfigAudioService configAudioService, PlaceService placeService) {
        RobotChatResponse robotChatResponse = new RobotChatResponse();
        robotChatResponse.setRobotId(robotId);
        robotChatResponse.setLanguage(language);
        robotChatResponse.setEventType("FINDING_PLACES");
        if (Strings.isBlank((String)placeName)) {
            FindPlaceGraph.setNotFindMessageAndVoice(robotChatResponse, language, location, configAudioService);
        } else {
            PlaceInfo locationInfo = placeService.findPlaceInfo(location, placeName, language, robotId);
            if (null == locationInfo) {
                FindPlaceGraph.setNotFindMessageAndVoice(robotChatResponse, language, location, configAudioService);
            } else {
                robotChatResponse.setAreaId("" + locationInfo.getId());
                robotChatResponse.setAreaName(locationInfo.getName());
                robotChatResponse.setMessage(FindPlaceGraph.getMessage(language, locationInfo.getName()));
                robotChatResponse.setPictures(FindPlaceGraph.convertToRobotChatImage(locationInfo.getPictures()));
                robotChatResponse.setLocationInfo(locationInfo.getCoordinate());
                robotChatResponse.setButtonName(FindPlaceGraph.getButtonName(language));
                if (Strings.isNotBlank((String)locationInfo.getAudioUrl())) {
                    robotChatResponse.setExtraInfo(locationInfo.getAudioUrl());
                }
            }
        }
        return robotChatResponse;
    }

    private static List<RobotChatImage> convertToRobotChatImage(List<PlaceImage> placeImages) {
        if (CollectionUtils.isEmpty(placeImages)) {
            return null;
        }
        return placeImages.stream().map(placeImage -> new RobotChatImage(placeImage.getImageName(), placeImage.getImageData())).collect(Collectors.toList());
    }

    private static void setNotFindMessageAndVoice(RobotChatResponse robotChatResponse, String language, String location, IConfigAudioService configAudioService) {
        AiUtils.setDefaultMessageAndVoice(configAudioService, language, location, "PLACE_NOT_FOUND", robotChatResponse);
    }

    private static String getMessage(String language, String name) {
        return switch (language) {
            case "EN" -> "Found " + name + "\uff0cDo you need guidance?";
            case "RU" -> "\u041d\u0430\u0439\u0434\u0435\u043d " + name + ", \u043d\u0443\u0436\u043d\u0430 \u043b\u0438 \u0432\u0430\u043c \u043f\u043e\u043c\u043e\u0449\u044c \u0441 \u043d\u0430\u0432\u0438\u0433\u0430\u0446\u0438\u0435\u0439?";
            default -> "\u5df2\u627e\u5230" + name + "\uff0c\u8bf7\u95ee\u662f\u5426\u9700\u8981\u5f15\u5bfc\uff1f";
        };
    }

    private static String getButtonName(String language) {
        return switch (language) {
            case "EN" -> "Guide Me";
            case "RU" -> "\u0412\u0435\u0434\u0438 \u043c\u0435\u043d\u044f";
            default -> "\u4e3a\u6211\u5e26\u8def";
        };
    }
}
