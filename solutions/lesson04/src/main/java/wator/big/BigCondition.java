package wator.big;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class BigCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Map<String, Object> attrs = annotatedTypeMetadata.getAnnotationAttributes(Big.class.getName());
        boolean annotationValue = Boolean.TRUE.equals(attrs.get("value"));
        boolean propertyValue = "true".equals(System.getProperty("big"));
        return annotationValue == propertyValue;
    }
}
