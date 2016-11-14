package org.springframework.security.access.prepost.processors;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import org.kohsuke.MetaInfServices;
import org.springframework.security.access.prepost.HasPermission;

@MetaInfServices(Processor.class)
@SupportedAnnotationTypes({"net.individual.apt.springHasPermission.annotation.HasPermission", "net.individual.apt.springHasPermission.annotation.HasPermissionContainer"})
public class HasPermissionProcessor extends AbstractProcessor {

	private JavacProcessingEnvironment pe;
	private JavacElements elems;
	private HasPermissionTranslator visitor;

	@Override
	public void init(ProcessingEnvironment processingEnvironment) {
		this.pe = (JavacProcessingEnvironment) processingEnvironment;
		this.visitor = new HasPermissionTranslator(this.pe);
		this.elems = this.pe.getElementUtils();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		roundEnv.getElementsAnnotatedWith(HasPermission.class).forEach(element -> {
			this.elems.getTree(element).accept(this.visitor);
		});
		return true;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
