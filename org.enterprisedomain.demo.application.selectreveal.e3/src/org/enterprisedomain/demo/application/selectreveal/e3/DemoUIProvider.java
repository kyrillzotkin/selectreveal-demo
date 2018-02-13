package org.enterprisedomain.demo.application.selectreveal.e3;

import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.ui.CompositeStateObserver;
import org.eclipse.emf.ecp.spi.ui.DefaultUIProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DemoUIProvider extends DefaultUIProvider {

	public DemoUIProvider() {
		super(DemoProvider.ID);
	}

	@Override
	public Control createNewProjectUI(Composite parent, CompositeStateObserver observer,
		ECPProperties projectProperties) {
		final Composite control = new Composite(parent, SWT.NONE);
		control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		control.setLayout(new GridLayout(1, false));
		observer.compositeChangedState(control, true, projectProperties);
		return control;
	}

}
