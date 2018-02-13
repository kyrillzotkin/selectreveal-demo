package org.enterprisedomain.demo.application.selectreveal.e3;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecp.core.util.ECPFilterProvider;
import org.enterprisedomain.demo.application.selectreveal.e3.domain.DomainPackage;

public class FilterProvider implements ECPFilterProvider {

	public FilterProvider() {

	}

	@Override
	public Set<String> getHiddenPackages() {
		final Set<String> packages = new HashSet<String>();
		packages.add(DomainPackage.eNS_URI);
		return packages;
	}

}
