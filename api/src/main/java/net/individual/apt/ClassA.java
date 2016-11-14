package net.individual.apt;

import org.springframework.security.access.prepost.HasPermission;
import org.springframework.security.access.prepost.PreAuthorize;

public class ClassA {

	@HasPermission("isto")
	@HasPermission("isso")
	public void method1() {
		System.out.println("method1");
	}

	@HasPermission("isso")
	public void method2() {
		System.out.println("method2");
	}

	@PreAuthorize("pois")
	public void method3() {
		System.out.println("method3");
	}

	@PreAuthorize("é")
	public void method4() {
		System.out.println("method4");
	}
}
