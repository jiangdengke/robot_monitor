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

public class IntroducingPlacesGraph {
    public CompiledGraph build(ChatClientService chatClientService, PlaceService placeService, IConfigAudioService configAudioService) throws GraphStateException {
        KeyStrategyFactory keyStrategyFactory = new KeyStrategyFactoryBuilder().addPatternStrategy("user_input", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("robot_id", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("location", (KeyStrategy)new ReplaceStrategy()).addPatternStrategy("language", (KeyStrategy)new ReplaceStrategy()).build();
        StateGraph findingPlaceGraph = new StateGraph("introducing-places-graph", keyStrategyFactory);
        NodeAction placeNode = state -> {
            String userInput = state.value("user_input").orElse("");
            String robotId = state.value("robot_id").orElse("");
            String location = state.value("location").orElse("");
            String language = state.value("language").orElse("CN");
            RobotChatResponse robotChatResponse = new RobotChatResponse("INTRODUCING_PLACES");
            robotChatResponse.setRobotId(robotId);
            robotChatResponse.setLanguage(language);
            String placeNames = placeService.getPlaceNames(location, language);
            if (Strings.isNotBlank((String)placeNames)) {
                String prompt = "\u4f60\u662f\u4e00\u4e2a\u5730\u70b9\u8bc6\u522b\u52a9\u624b\uff0c\u8bf7\u4ece\u7528\u6237\u63d0\u95ee\u4e2d\u8bc6\u522b\u5730\u70b9\uff0c\u5e76\u8fd4\u56de\u8be5\u5730\u70b9\u7684\u6807\u51c6\u5730\u5740\u540d\u79f0\uff08\u7528\u4e8e\u6570\u636e\u5e93\u68c0\u7d22\uff09\uff0c\u5c3d\u91cf\u5339\u914d\u884c\u653f\u533a\u5212\u6216\u5e38\u89c1\u5b98\u65b9\u79f0\u8c13\u3002\n\u3010\u8bf7\u4e25\u683c\u4ec5\u8fd4\u56de\u5982\u4e0b\u4e4b\u4e00\u3011\uff1a\n%s\n\n\u5982\u679c\u7528\u6237\u8be2\u95ee\u7684\u5730\u70b9\u4e0d\u5728\u5217\u8868\u4e2d\uff0c\u8bf7\u8fd4\u56de\u7a7a\u5b57\u7b26\u4e32\"\"\u3002\n\u7528\u6237\u95ee\u9898\uff1a%s\n\u3010\u8bf7\u4ec5\u8fd4\u56de\u3011\uff1a%s \u6216\u8005\u7a7a\u5b57\u7b26\u4e32\"\"\u4e2d\u7684\u4e00\u4e2a\uff0c\u4e0d\u52a0\u6ce8\u91ca\u6216\u89e3\u91ca\u3002\n/no_think\n".formatted(placeNames, userInput, placeNames);
                String placeName = AiUtils.removeThinkTag(chatClientService.getToolsChatClient().prompt(prompt).options(ChatOptions.builder().temperature(Double.valueOf(0.0)).build()).advisors(a -> a.param("chat_memory_conversation_id", (Object)robotId)).call().content());
                if (Strings.isBlank((String)placeName)) {
                    robotChatResponse.setMessage(this.normalAnswer(userInput, robotId, language, chatClientService));
                } else {
                    PlaceInfo locationInfo = placeService.findPlaceInfo(location, placeName, language, robotId);
                    if (null != locationInfo && Strings.isNotBlank((String)locationInfo.getIntroduction())) {
                        robotChatResponse.setMessage(this.introduceAnswer(userInput, locationInfo.getIntroduction(), language, robotId, chatClientService));
                        robotChatResponse.setAreaId("" + locationInfo.getId());
                        robotChatResponse.setAreaName(locationInfo.getName());
                        robotChatResponse.setPictures(IntroducingPlacesGraph.convertToRobotChatImage(locationInfo.getPictures()));
                        robotChatResponse.setLocationInfo(locationInfo.getCoordinate());
                        robotChatResponse.setButtonName(IntroducingPlacesGraph.getButtonName(language));
                        if (Strings.isNotBlank((String)locationInfo.getAudioUrl())) {
                            robotChatResponse.setExtraInfo(locationInfo.getAudioUrl());
                        }
                    } else {
                        robotChatResponse.setMessage(this.normalAnswer(userInput, robotId, language, chatClientService));
                    }
                }
            } else {
                robotChatResponse.setMessage(this.normalAnswer(userInput, robotId, language, chatClientService));
            }
            HashMap<String, RobotChatResponse> resultMap = new HashMap<String, RobotChatResponse>();
            resultMap.put("response", robotChatResponse);
            return resultMap;
        };
        findingPlaceGraph.addNode("introducingPlaces", AsyncNodeAction.node_async((NodeAction)placeNode));
        findingPlaceGraph.addEdge("__START__", "introducingPlaces");
        findingPlaceGraph.addEdge("introducingPlaces", "__END__");
        return findingPlaceGraph.compile();
    }

    public static RobotChatResponse introducePlace(String robotId, String language, String location, String placeName, IConfigAudioService configAudioService, PlaceService placeService) {
        RobotChatResponse robotChatResponse = new RobotChatResponse("INTRODUCING_PLACES");
        robotChatResponse.setRobotId(robotId);
        robotChatResponse.setLanguage(language);
        if (Strings.isBlank((String)placeName)) {
            IntroducingPlacesGraph.setNotFindMessageAndVoice(robotChatResponse, language, location, configAudioService);
        } else {
            PlaceInfo locationInfo = placeService.findPlaceInfo(location, placeName, language, robotId);
            if (null != locationInfo && Strings.isNotBlank((String)locationInfo.getIntroduction())) {
                robotChatResponse.setMessage(locationInfo.getIntroduction());
                robotChatResponse.setAreaId("" + locationInfo.getId());
                robotChatResponse.setAreaName(locationInfo.getName());
                robotChatResponse.setPictures(IntroducingPlacesGraph.convertToRobotChatImage(locationInfo.getPictures()));
                robotChatResponse.setLocationInfo(locationInfo.getCoordinate());
                robotChatResponse.setButtonName(IntroducingPlacesGraph.getButtonName(language));
                if (Strings.isNotBlank((String)locationInfo.getAudioUrl())) {
                    robotChatResponse.setExtraInfo(locationInfo.getAudioUrl());
                }
            } else {
                IntroducingPlacesGraph.setNotFindMessageAndVoice(robotChatResponse, language, location, configAudioService);
            }
        }
        return robotChatResponse;
    }

    private String normalAnswer(String userInput, String robotId, String language, ChatClientService chatClientService) {
        String prompt = userInput + "\n\u8bf7\u4f7f\u7528\u201c%s\u201d\u8bed\u8a00\u6765\u56de\u7b54\n".formatted(AiUtils.getPromptLanguage(language));
        return AiUtils.removeThinkTag(chatClientService.getKnowledgeBaseChat().prompt(prompt).advisors(a -> a.param("chat_memory_conversation_id", (Object)robotId)).call().content());
    }

    private String introduceAnswer(String userInput, String introduction, String language, String robotId, ChatClientService chatClientService) {
        String answerPrompt = "\u7528\u6237\u7684\u63d0\u95ee\u5982\u4e0b\uff1a%s\n\u7528\u6237\u9700\u8981\u67e5\u8be2\u7684\u5730\u70b9\u4ecb\u7ecd\u4fe1\u606f\u5982\u4e0b\uff1a%s\n\n\u8bf7\u6839\u636e\u5730\u70b9\u4fe1\u606f\u91cd\u65b0\u7ec4\u7ec7\u8bed\u8a00\n\n/no_think\n".formatted(userInput, introduction) + "\n\u8bf7\u4f7f\u7528\u201c%s\u201d\u8bed\u8a00\u6765\u56de\u7b54\n".formatted(AiUtils.getPromptLanguage(language));
        return AiUtils.removeThinkTag(chatClientService.getChatClient().prompt(answerPrompt).advisors(a -> a.param("chat_memory_conversation_id", (Object)robotId)).call().content());
    }

    private static List<RobotChatImage> convertToRobotChatImage(List<PlaceImage> placeImages) {
        if (CollectionUtils.isEmpty(placeImages)) {
            return null;
        }
        return placeImages.stream().map(placeImage -> new RobotChatImage(placeImage.getImageName(), placeImage.getImageData())).collect(Collectors.toList());
    }

    private static String getButtonName(String language) {
        return switch (language) {
            case "EN" -> "More details";
            case "RU" -> "\u041f\u043e\u0434\u0440\u043e\u0431\u043d\u0435\u0435";
            default -> "\u66f4\u591a\u8be6\u60c5";
        };
    }

    private static void setNotFindMessageAndVoice(RobotChatResponse robotChatResponse, String language, String location, IConfigAudioService configAudioService) {
        AiUtils.setDefaultMessageAndVoice(configAudioService, language, location, "PLACE_NOT_FOUND", robotChatResponse);
    }
}
