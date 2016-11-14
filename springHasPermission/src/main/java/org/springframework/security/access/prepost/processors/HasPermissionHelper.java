package org.springframework.security.access.prepost.processors;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;
import org.springframework.security.access.prepost.PreAuthorize;

public class HasPermissionHelper {

	private final TreeMaker make;
	private final Names names;
	private final GetElement getElement;

	public HasPermissionHelper(JavacProcessingEnvironment pe) {
		this.make = TreeMaker.instance(pe.getContext());
		this.names = Names.instance(pe.getContext());
		this.getElement = new GetElement(pe);
	}

	public JCTree.JCAnnotation getPreAuthorizeAnnotation(final String value) {
		return this.make.Annotation(this.make.Ident(this.getElement.apply(PreAuthorize.class)), List.<JCTree.JCExpression>of(this.getAssign(value)));
	}

	public JCTree.JCAssign getAssign(final String value) {
		return this.make.Assign(this.make.Ident(this.names.fromString("value")), this.make.Literal(value));
	}
}
