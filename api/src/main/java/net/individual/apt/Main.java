package net.individual.apt;

import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.HasPermission;
import org.springframework.security.access.prepost.HasPermissionContainer;
import org.springframework.security.access.prepost.PreAuthorize;

public class Main {

	private static void showAnnotations(Method method, Class<? extends Annotation> annotationClass) {
		System.out.print(method.getName() + " : " + annotationClass.getSimpleName());
		Annotation annotation = method.getAnnotation(annotationClass);
		if (annotation != null) {
			System.out.println(" : " + annotation);
		} else {
			System.out.println("");
		}
	}

	private static void springShowAnnotations(Method method, Class<? extends Annotation> annotationClass) {
		System.out.print(method.getName() + " : " + annotationClass.getSimpleName());
		Annotation annotation = AnnotationUtils.findAnnotation(method, annotationClass);
		if (annotation != null) {
			System.out.println(" : " + annotation);
		} else {
			System.out.println("");
		}
	}

	private static final Set<String> METHODS = ImmutableSet.of("method1", "method2", "method3", "method4");
	private static final Set<Class<? extends Annotation>> ANNOTATIONS = ImmutableSet.of(PreAuthorize.class, HasPermission.class, HasPermissionContainer.class);

	public static void main(String[] args) throws NoSuchMethodException {
		ImmutableSet.copyOf(ClassA.class.getDeclaredMethods())
			.stream()
			.filter(method -> METHODS.contains(method.getName()))
			.forEach(method -> {
				ANNOTATIONS
					.forEach(annotationClass -> showAnnotations(method, annotationClass));
			});
	}
}
