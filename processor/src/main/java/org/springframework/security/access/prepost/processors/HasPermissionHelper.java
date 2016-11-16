package org.springframework.security.access.prepost.processors;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;
import java.util.Set;
import java.util.StringJoiner;

public class HasPermissionHelper {

	private final TreeMaker make;
	private final Names names;

	public HasPermissionHelper(JavacProcessingEnvironment pe) {
		this.make = TreeMaker.instance(pe.getContext());
		this.names = Names.instance(pe.getContext());
	}

	public JCTree.JCAnnotation getPreAuthorizeAnnotation(final Set<String> values) {
		if (values.isEmpty()) {
			return null;
		}
		return this.make.Annotation(this.make.Ident(this.names.fromString("PreAuthorize")), List.<JCTree.JCExpression>of(this.getAssign(values)));
	}

	public JCTree.JCAssign getAssign(final Set<String> values) {
		return this.make.Assign(this.make.Ident(this.names.fromString("value")), this.make.Literal(getSpringElExpression(values)));
	}

	public String getSpringElExpression(final Set<String> values) {
		StringJoiner sj = new StringJoiner(",");
		values
			.stream()
			.forEach(value -> sj.add(getSanitizedString(value)));
		StringBuilder result = new StringBuilder();
		if (values.size() == 1) {
			result.append("hasAuthority(");
		} else {
			result.append("hasAnyAuthority(");
		}
		result.append(sj.toString()).append(')');
		return result.toString();
	}

	public String getSanitizedString(String value) {
		return '"' + value.replaceAll("\"", "\\\"") + '"';
	}
}
