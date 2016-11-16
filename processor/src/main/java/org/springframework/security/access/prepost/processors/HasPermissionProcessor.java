package org.springframework.security.access.prepost.processors;

import com.google.common.collect.ImmutableSet;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import org.kohsuke.MetaInfServices;
import org.springframework.security.access.prepost.HasPermission;
import org.springframework.security.access.prepost.HasPermissionContainer;

@MetaInfServices(Processor.class)
@SupportedAnnotationTypes({"org.springframework.security.access.prepost.HasPermission", "org.springframework.security.access.prepost.HasPermissionContainer"})
public class HasPermissionProcessor extends AbstractProcessor {

	private JavacProcessingEnvironment pe;
	private JavacElements elems;

	@Override
	public void init(ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);
		this.pe = (JavacProcessingEnvironment) processingEnvironment;
		this.elems = this.pe.getElementUtils();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		roundEnv.getElementsAnnotatedWith(HasPermission.class).forEach(element -> {
			this.elems.getTree(element).accept(new HasPermissionTranslator(this.pe, ImmutableSet.of(element.getAnnotation(HasPermission.class).value())));
		});
		roundEnv.getElementsAnnotatedWith(HasPermissionContainer.class).forEach(element -> {
			this.elems.getTree(element).accept(new HasPermissionTranslator(this.pe, ImmutableSet.copyOf(element.getAnnotationsByType(HasPermission.class)).stream().map(HasPermission::value).collect(Collectors.toSet())));
		});
		return false;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
