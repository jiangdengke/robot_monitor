package com.robotmonitor.framework.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class LegacyWebMvcBindingConfig {
    @Bean
    public static BeanPostProcessor legacyArgumentResolverPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RequestMappingHandlerAdapter adapter && adapter.getArgumentResolvers() != null) {
                    List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
                    resolvers.add(new LegacyNamedValueArgumentResolver());
                    resolvers.addAll(adapter.getArgumentResolvers());
                    adapter.setArgumentResolvers(resolvers);
                }
                return bean;
            }
        };
    }

    private static final class LegacyNamedValueArgumentResolver implements HandlerMethodArgumentResolver {
        private final ConversionService conversionService = DefaultConversionService.getSharedInstance();

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
            if (pathVariable != null) {
                return isBlank(pathVariable.name()) && isBlank(pathVariable.value());
            }
            RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
            return requestParam != null && isBlank(requestParam.name()) && isBlank(requestParam.value());
        }

        @Override
        public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
        ) throws Exception {
            PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
            if (pathVariable != null) {
                return resolvePathVariable(parameter, pathVariable, webRequest, binderFactory);
            }
            RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
            return resolveRequestParam(parameter, requestParam, webRequest, binderFactory);
        }

        @SuppressWarnings("unchecked")
        private Object resolvePathVariable(
            MethodParameter parameter,
            PathVariable annotation,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
        ) throws Exception {
            Map<String, String> variables = (Map<String, String>) webRequest.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                RequestAttributes.SCOPE_REQUEST
            );
            String name = inferPathVariableName(parameter, variables, webRequest);
            if (name == null || variables == null || !variables.containsKey(name)) {
                if (annotation.required()) {
                    throw new MissingPathVariableException(name == null ? "" : name, parameter);
                }
                return null;
            }
            return convertValue(variables.get(name), parameter, name, webRequest, binderFactory);
        }

        private Object resolveRequestParam(
            MethodParameter parameter,
            RequestParam annotation,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
        ) throws Exception {
            Map<String, String[]> parameters = webRequest.getParameterMap();
            String name = inferNamedValueByIndex(parameter, parameters.keySet(), RequestParam.class);
            String[] rawValues = name == null ? null : parameters.get(name);
            if ((rawValues == null || rawValues.length == 0) && hasDefaultValue(annotation)) {
                rawValues = new String[] {annotation.defaultValue()};
            }
            if (rawValues == null || rawValues.length == 0) {
                if (annotation.required()) {
                    throw new MissingServletRequestParameterException(name == null ? "" : name, parameter.getParameterType().getSimpleName());
                }
                return null;
            }
            Object rawValue = parameter.getParameterType().isArray() ? rawValues : rawValues[0];
            return convertValue(rawValue, parameter, name, webRequest, binderFactory);
        }

        private Object convertValue(
            Object rawValue,
            MethodParameter parameter,
            String name,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
        ) throws Exception {
            if (rawValue == null) {
                return null;
            }
            Class<?> targetType = parameter.getParameterType();
            Object value = rawValue;
            if (targetType.isArray() && rawValue instanceof String text) {
                value = text.contains(",") ? text.split(",") : new String[] {text};
            }
            if (!targetType.isArray() && rawValue instanceof String[] values) {
                value = values.length == 0 ? null : values[0];
            }
            if (value == null || targetType.isInstance(value)) {
                return value;
            }
            if (binderFactory != null) {
                WebDataBinder binder = binderFactory.createBinder(webRequest, null, name);
                return binder.convertIfNecessary(value, targetType, parameter);
            }
            return conversionService.convert(value, wrapperType(targetType));
        }

        private String inferPathVariableName(
            MethodParameter parameter,
            Map<String, String> variables,
            NativeWebRequest webRequest
        ) {
            if (variables == null || variables.isEmpty()) {
                return null;
            }
            List<String> names = pathVariableNamesInPattern(webRequest, variables.keySet());
            if (names.isEmpty()) {
                names = new ArrayList<>(variables.keySet());
            }
            if (names.size() == 1) {
                return names.get(0);
            }
            int index = unnamedAnnotationIndex(parameter, PathVariable.class);
            return index >= 0 && index < names.size() ? names.get(index) : null;
        }

        private List<String> pathVariableNamesInPattern(NativeWebRequest webRequest, Collection<String> availableNames) {
            Object pattern = webRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
            if (pattern == null) {
                return List.of();
            }
            String text = pattern.toString();
            List<String> names = new ArrayList<>();
            int offset = 0;
            while (offset < text.length()) {
                int start = text.indexOf('{', offset);
                if (start < 0) {
                    break;
                }
                int end = text.indexOf('}', start + 1);
                if (end < 0) {
                    break;
                }
                String name = text.substring(start + 1, end);
                int colon = name.indexOf(':');
                if (colon >= 0) {
                    name = name.substring(0, colon);
                }
                if (availableNames.contains(name)) {
                    names.add(name);
                }
                offset = end + 1;
            }
            return names;
        }

        private String inferNamedValueByIndex(
            MethodParameter parameter,
            Collection<String> availableNames,
            Class<? extends Annotation> annotationType
        ) {
            if (availableNames == null || availableNames.isEmpty()) {
                return null;
            }
            List<String> names = new ArrayList<>(availableNames);
            if (names.size() == 1) {
                return names.get(0);
            }
            int index = unnamedAnnotationIndex(parameter, annotationType);
            return index >= 0 && index < names.size() ? names.get(index) : null;
        }

        private int unnamedAnnotationIndex(MethodParameter parameter, Class<? extends Annotation> annotationType) {
            int targetIndex = parameter.getParameterIndex();
            Annotation[][] annotations = parameter.getExecutable().getParameterAnnotations();
            int index = 0;
            for (int i = 0; i < annotations.length && i <= targetIndex; i++) {
                if (hasUnnamedAnnotation(annotations[i], annotationType)) {
                    if (i == targetIndex) {
                        return index;
                    }
                    index++;
                }
            }
            return -1;
        }

        private boolean hasUnnamedAnnotation(Annotation[] annotations, Class<? extends Annotation> annotationType) {
            return Arrays.stream(annotations).anyMatch(annotation -> {
                if (!annotationType.isInstance(annotation)) {
                    return false;
                }
                if (annotation instanceof PathVariable pathVariable) {
                    return isBlank(pathVariable.name()) && isBlank(pathVariable.value());
                }
                if (annotation instanceof RequestParam requestParam) {
                    return isBlank(requestParam.name()) && isBlank(requestParam.value());
                }
                return false;
            });
        }

        private boolean hasDefaultValue(RequestParam annotation) {
            return !ValueConstants.DEFAULT_NONE.equals(annotation.defaultValue());
        }

        private Class<?> wrapperType(Class<?> type) {
            if (!type.isPrimitive()) {
                return type;
            }
            if (type == int.class) {
                return Integer.class;
            }
            if (type == long.class) {
                return Long.class;
            }
            if (type == boolean.class) {
                return Boolean.class;
            }
            if (type == double.class) {
                return Double.class;
            }
            if (type == float.class) {
                return Float.class;
            }
            if (type == short.class) {
                return Short.class;
            }
            if (type == byte.class) {
                return Byte.class;
            }
            if (type == char.class) {
                return Character.class;
            }
            return type;
        }

        private static boolean isBlank(String value) {
            return value == null || value.trim().isEmpty();
        }
    }
}
