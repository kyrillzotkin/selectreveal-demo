/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.enterprisedomain.demo.application.selectreveal.e3.domain.RootObject;

/**
 *
 */
public class SelectRevealHelper {

	private Object selection;
	private Object part;

	public void prepare() {
		final String partId = "org.eclipse.emf.ecp.ui.ModelExplorerView"; //$NON-NLS-1$
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final ISelection selection = page.getSelection(partId);
		if (selection.isEmpty()) {
			this.selection = null;
			return;
		}
		if (selection instanceof IStructuredSelection) {
			this.selection = ((IStructuredSelection) selection).getFirstElement();
		}
		part = page.findView(partId);
	}

	public void selectReveal(Resource newResource) {
		if (selection == null) {
			return;
		}
		if (!(selection instanceof EObject) && !(selection instanceof ECPContainer)) {
			return;
		}
		final ECPContainer container = ECPUtil.getModelContext(
			(ECPModelContextProvider) ECPUtil.getECPProviderRegistry().getProvider(DemoProvider.ID)
				.getAdapter(DemoProvider.class),
			newResource);
		if (container.equals(selection) && !newResource.getContents().isEmpty()
			&& newResource.getContents().get(0) instanceof RootObject
			&& !((RootObject) newResource.getContents().get(0)).getModelElements().isEmpty()) {
			selectReveal(((RootObject) newResource.getContents().get(0)).getModelElements().get(0));
		} else {
			final TreeIterator<Object> newIterator = EcoreUtil.getAllProperContents(newResource, true);
			while (newIterator.hasNext()) {
				final Object newObject = newIterator.next();
				if (EcoreUtil.equals((EObject) newObject, (EObject) selection)) {
					selectReveal(newObject);
					break;
				}
			}
		}
	}

	private void selectReveal(Object newObject) {
		if (part instanceof ISetSelectionTarget) {
			((ISetSelectionTarget) part).selectReveal(new StructuredSelection(newObject));
		} else if (part instanceof IViewerProvider) {
			final Viewer viewer = ((IViewerProvider) part).getViewer();
			if (viewer != null) {
				viewer.setSelection(new StructuredSelection(newObject), true);
			}
		}
	}

}
