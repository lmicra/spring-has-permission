package org.springframework.security.access.prepost.processors;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import org.kohsuke.MetaInfServices;
import org.springframework.security.access.prepost.PreAuthorize;

@MetaInfServices(Processor.class)
@SupportedAnnotationTypes("net.individual.apt.springHasPermission.annotation.PreAuthorize")
public class PreAuthorizeProcessor extends AbstractProcessor {

	private JavacProcessingEnvironment pe;
	private JavacElements elems;

	@Override
	public void init(ProcessingEnvironment processingEnvironment) {
		this.pe = (JavacProcessingEnvironment) processingEnvironment;
		this.elems = this.pe.getElementUtils();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		roundEnv.getElementsAnnotatedWith(PreAuthorize.class).forEach(element -> {
			JCTree jcTree = this.elems.getTree(element);
			System.out.println("preauthorize tree: " + jcTree);
		});
		return false;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
