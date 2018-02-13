package org.enterprisedomain.demo.application.selectreveal.e3;

import java.net.URISyntaxException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.ui.util.ECPModelElementOpener;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class ModelElementOpener implements ECPModelElementOpener {

	public ModelElementOpener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void openModelElement(Object modelElement, ECPProject project) {
		final String editorId = "org.eclipse.emfforms.editor.ecore.genericxmieditor";
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart result = null;
		URI uri = null;
		try {
			if (modelElement instanceof EObject) {
				if (((EObject) modelElement).eResource() != null) {
					uri = ((EObject) modelElement).eResource().getURI();
				}
				final IPath workspaceRoot = ResourcesPlugin.getWorkspace().getRoot().getRawLocation();
				result = IDE.openEditor(page,
					new java.net.URI(
						"file" + IPath.DEVICE_SEPARATOR + IPath.SEPARATOR + IPath.SEPARATOR //$NON-NLS-1$
							+ workspaceRoot.append(uri.toPlatformString(false)).toOSString()),
					editorId, true);
				final EList<Adapter> editorAdapters = ((GenericEditor) result).getEditingDomain().getResourceSet()
					.eAdapters();
				final EList<Adapter> providerAdapters = ((ResourceSet) ECPUtil.getECPProviderRegistry()
					.getProvider(DemoProvider.ID).getAdapter(DemoProvider.class).getAdapter(ResourceSet.class))
						.eAdapters();
				for (final Adapter adapter : providerAdapters) {
					if (adapter instanceof EContentAdapter && !editorAdapters.contains(adapter)) {
						editorAdapters.add(adapter);
					}
				}
			}
		} catch (final PartInitException e) {
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}

	}

}
