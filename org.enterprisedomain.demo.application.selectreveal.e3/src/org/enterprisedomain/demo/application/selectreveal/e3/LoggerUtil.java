/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * kyrill - initial API and implementation
 ******************************************************************************/
package org.enterprisedomain.demo.application.selectreveal.e3;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 *
 *
 */
public class LoggerUtil {

	public static void logException(Throwable exception) {
		final Bundle bundle = Platform.getBundle("org.eclipse.emf.ecp.makeithappen.application.selectreveal.e3");
		if (bundle == null) {
			return;
		}
		final Status status = new Status(IStatus.ERROR, bundle.getSymbolicName(), IStatus.ERROR, exception.getMessage(),
			exception);
		Platform.getLog(bundle).log(status);
	}

}
