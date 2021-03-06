package org.n3r.quartz.glass.job.annotation;

import org.codehaus.jackson.annotate.JsonProperty;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Bean that can be used in jsp files and in json serialisations.
 *
 * @author damien bourdette
 */
public class JobBean {
    @JsonProperty
    private String description;

    @JsonProperty
    private boolean disallowConcurrentExecution;

    @JsonProperty
    private boolean persistJobDataAfterExecution;

    @JsonProperty
    private List<JobArgumentBean> arguments;

    public static JobBean fromClass(Class<?> jobClass) {
        JobBean jobBean = new JobBean();

        jobBean.description = getDescription(jobClass);
        jobBean.disallowConcurrentExecution = isDisallowConcurrentExecution(jobClass);
        jobBean.persistJobDataAfterExecution = isPersistJobDataAfterExecution(jobClass);
        jobBean.arguments = JobArgumentBean.fromClass(jobClass);

        return jobBean;
    }

    public static String getDescription(Class<?> jobClass) {
        GlassJob annotation = getAnnotation(jobClass, GlassJob.class);

        if (annotation == null) return "";

        return annotation.description();
    }

    public static boolean isDisallowConcurrentExecution(Class<?> jobClass) {
        return getAnnotation(jobClass, DisallowConcurrentExecution.class) != null;
    }

    public static boolean isPersistJobDataAfterExecution(Class<?> jobClass) {
        return getAnnotation(jobClass, PersistJobDataAfterExecution.class) != null;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDisallowConcurrentExecution() {
        return disallowConcurrentExecution;
    }

    public boolean isPersistJobDataAfterExecution() {
        return persistJobDataAfterExecution;
    }

    private static <T extends Annotation> T getAnnotation(Class<?> jobClass, Class<T> annotationClass) {
        if (jobClass == null) return null;

        return jobClass.getAnnotation(annotationClass);
    }
}
