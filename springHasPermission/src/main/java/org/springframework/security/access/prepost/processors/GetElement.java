package org.springframework.security.access.prepost.processors;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import java.util.List;
import javax.lang.model.element.Element;

/**
 * Helper class to get an element that can be directly used when creating new expressions.
 */
public class GetElement {

	private final JavacProcessingEnvironment javacProcessingEnvironment;

	public GetElement(JavacProcessingEnvironment javacProcessingEnvironment) {
		this.javacProcessingEnvironment = javacProcessingEnvironment;
	}

	public Symbol apply(Class<?> javaClass) {
		return (Symbol) getPackageElements(javaClass)
			.stream()
			.filter(element -> element.getSimpleName().toString().equals(javaClass.getSimpleName()))
			.findAny()
			.orElseThrow(() -> new AssertionError("Unable to get " + javaClass));
	}

	private List<? extends Element> getPackageElements(Class<?> javaClass) {
		return javacProcessingEnvironment.getElementUtils().getPackageElement(javaClass.getPackage().getName()).getEnclosedElements();
	}
}
