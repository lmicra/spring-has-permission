package org.springframework.security.access.prepost.processors;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import java.util.Set;

public class HasPermissionTranslator extends TreeTranslator {

	private final HasPermissionHelper helper;
	private final Set<String> values;

	public HasPermissionTranslator(JavacProcessingEnvironment pe, Set<String> values) {
		this.helper = new HasPermissionHelper(pe);
		this.values = values;
	}

	@Override
	public void visitMethodDef(JCTree.JCMethodDecl tree) {
		JCAnnotation preAuthorizeAnnotation = this.helper.getPreAuthorizeAnnotation(this.values);
		if (preAuthorizeAnnotation != null) {
			tree.mods.annotations = List.from(tree.mods.annotations).append(preAuthorizeAnnotation);
		}
		result = tree;
	}
}
