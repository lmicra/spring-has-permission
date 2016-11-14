package org.springframework.security.access.prepost.processors;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import java.util.stream.Collectors;

public class HasPermissionTranslator extends TreeTranslator {

	private final TreeMaker make;
	private final HasPermissionHelper helper;
	private final Name hasPermissionName;
	private final Names names;

	public HasPermissionTranslator(JavacProcessingEnvironment pe) {
		this.make = TreeMaker.instance(pe.getContext());
		this.helper = new HasPermissionHelper(pe);
		this.names = Names.instance(pe.getContext());
		this.hasPermissionName = this.names.fromString("HasPermission");
	}

	@Override
	public void visitMethodDef(JCTree.JCMethodDecl tree) {
		System.out.println("tree: " + tree);

		List<JCAnnotation> annotations;

		JCAnnotation preAuthorizeAnnotation = this.helper.getPreAuthorizeAnnotation("ahah");

		annotations = List.from(
			tree.mods.annotations.stream()
			.filter(annotation -> !this.hasPermissionName.equals(((JCTree.JCIdent) annotation.getAnnotationType()).getName()))
			.collect(Collectors.toList())
		).prepend(preAuthorizeAnnotation);

//		annotations = List.of(preAuthorizeAnnotation);
		JCTree.JCModifiers jCModifiers = this.make.Modifiers(tree.mods.flags, annotations);

		result = this.make.MethodDef(jCModifiers, tree.name.append(this.names.fromString("a	")), tree.restype, tree.typarams, tree.recvparam, tree.params, tree.thrown, tree.body, tree.defaultValue);

		System.out.println("result: " + result);
	}
}
