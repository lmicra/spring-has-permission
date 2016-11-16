package net.individual.apt.springHasPermission;

import com.google.common.collect.Sets;
import com.sun.source.util.Trees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class HasPermissionProcessor1 extends AbstractProcessor {

//	private HasPermissionTranslator hasPermissionTranslator;
	private JavacProcessingEnvironment pe;
	private Trees trees;
	private HasPermissionTranslator visitor;
//	private ProcessingEnvironment pe;
//	private Names names;
//	private TreeMaker treeMaker;
//	private Types typeUtils;
//	private Elements elementUtils;

	@Override
	public void init(ProcessingEnvironment processingEnvironment) {
		this.pe = (JavacProcessingEnvironment) processingEnvironment;
		this.trees = Trees.instance(pe);
//		this.visitor = new HasPermissionTranslator(TreeMaker.instance(this.pe.getContext()));
//		this.typeUtils = pe.getTypeUtils();
//		this.elementUtils = pe.getElementUtils();
//		this.treeMaker = TreeMaker.instance(pe.getContext());
//		this.names = Names.instance(pe.getContext());
//		this.hasPermissionTranslator = new HasPermissionTranslator(this.pe);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(HasPermission.class)) {
			JCTree.JCMethodDecl jcMethodDecl = (JCTree.JCMethodDecl) this.trees.getTree(element);
			jcMethodDecl.accept(visitor);
//			String permission = element.getAnnotation(HasPermission.class).value();
//			MethodTree tree = (MethodTree) this.trees.getTree(element);
//			tree.accept(tv, pe);
//			List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
//			System.out.println("annotationMirrors : " + annotationMirrors);
//			TreeMaker make = TreeMaker.instance(this.pe.getContext());
//			element.getModifiers().clear();
//			JCTree.JCModifiers modifiers = (JCTree.JCModifiers) ((MethodTree) this.trees.getTree(element)).getModifiers();
//			this.hasPermissionTranslator.translateToPreAuthorize(modifiers, permission);
		}

//		for (Element element : roundEnv.getElementsAnnotatedWith(HasPermissionContainer.class)) {
//			System.out.println("element : " + element);
//			String[] permissions = ImmutableSet.copyOf(element.getAnnotation(HasPermissionContainer.class).value()).stream().map(hasPermission -> hasPermission.value()).toArray(String[]::new);
//			System.out.println("permissions : " + Arrays.toString(permissions));
//			JCTree.JCModifiers modifiers = (JCTree.JCModifiers) ((MethodTree) this.trees.getTree(element)).getModifiers();
//			this.hasPermissionTranslator.translateToPreAuthorize(modifiers, permissions);
//		}
		return true;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Sets.newHashSet(HasPermission.class.getName(), HasPermissionContainer.class.getName());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return this.pe.getSourceVersion();
	}
}
