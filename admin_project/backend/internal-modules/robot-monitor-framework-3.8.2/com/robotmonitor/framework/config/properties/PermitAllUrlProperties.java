/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.annotation.Anonymous
 *  org.apache.commons.lang3.RegExUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
 */
package com.robotmonitor.framework.config.properties;

import com.robotmonitor.common.annotation.Anonymous;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class PermitAllUrlProperties
implements InitializingBean,
ApplicationContextAware {
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private ApplicationContext applicationContext;
    private List<String> urls = new ArrayList<String>();
    public String ASTERISK = "*";

    public void afterPropertiesSet() {
        RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping)this.applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map map = mapping.getHandlerMethods();
        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = (HandlerMethod)map.get(info);
            Anonymous method = (Anonymous)AnnotationUtils.findAnnotation((Method)handlerMethod.getMethod(), Anonymous.class);
            Optional.ofNullable(method).ifPresent(anonymous -> Objects.requireNonNull(info.getPathPatternsCondition().getPatternValues()).forEach(url -> this.urls.add(RegExUtils.replaceAll((String)url, (Pattern)PATTERN, (String)this.ASTERISK))));
            Anonymous controller = (Anonymous)AnnotationUtils.findAnnotation((Class)handlerMethod.getBeanType(), Anonymous.class);
            Optional.ofNullable(controller).ifPresent(anonymous -> Objects.requireNonNull(info.getPathPatternsCondition().getPatternValues()).forEach(url -> this.urls.add(RegExUtils.replaceAll((String)url, (Pattern)PATTERN, (String)this.ASTERISK))));
        });
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    public List<String> getUrls() {
        return this.urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
