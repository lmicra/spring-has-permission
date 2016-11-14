package net.individual.apt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.access.prepost.HasPermission;
import org.springframework.security.access.prepost.HasPermissionContainer;
import org.springframework.security.access.prepost.PreAuthorize;

public class Main {

	private static void showAnnotations(Method method, Class<? extends Annotation> annotationClass) {
		Annotation annotation = method.getAnnotation(annotationClass);
		if (annotation != null) {
			System.out.println(method.getName() + " : " + annotationClass.getSimpleName() + " : " + annotation);
		}
	}

	private static final Set<String> METHODS = Stream.of("method1", "method2", "method3", "method4").collect(Collectors.toSet());
	private static final Set<Class<? extends Annotation>> ANNOTATIONS = Stream.of(PreAuthorize.class, HasPermission.class, HasPermissionContainer.class).collect(Collectors.toSet());

	public static void main(String[] args) throws NoSuchMethodException {
		for (String methodName : METHODS) {
			Method method = ClassA.class.getDeclaredMethod(methodName, new Class<?>[0]);
			for (Class<? extends Annotation> annotation : ANNOTATIONS) {
				showAnnotations(method, annotation);
			}
		}
	}
}
