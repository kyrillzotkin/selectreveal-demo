package org.enterprisedomain.demo.application.selectreveal.e3;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextAdapter;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskPackage;
import org.eclipse.emf.ecp.spi.core.DefaultProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.enterprisedomain.demo.application.selectreveal.e3.domain.DomainFactory;
import org.enterprisedomain.demo.application.selectreveal.e3.domain.RootObject;
import org.enterprisedomain.demo.application.selectreveal.e3.domain.util.DomainAdapterFactory;

public class DemoProvider extends DefaultProvider implements IAdapterFactory {

	public static final String ID = "org.enterprisedomain.demo.application.selectreveal.e3.id"; //$NON-NLS-1$

	private final Map<String, URI> projectNameToURIMap = new HashMap<String, URI>();

	private final Adapter resourceSaveAdapter = new EContentAdapter() {

		@SuppressWarnings("unchecked")
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			Resource resource = null;
			if (notification.getEventType() == Notification.ADD && notification.getNotifier() instanceof EObject) {
				final Object feature = notification.getFeature();
				if (feature instanceof EReference) {
					final EReference eReference = (EReference) feature;
					if (eReference.isContainment()) {
						resource = ((EObject) notification.getNewValue()).eResource();
					}
				}
			}
			if (resource == null) {
				return;
			}
			final InternalProject project = (InternalProject) ECPUtil.getModelContext(DemoProvider.this, resource);
			if (project != null) {
				getSelectRevealHelper().prepare();
				try {
					resource.save(Collections.emptyMap());
				} catch (final IOException ex) {
					LoggerUtil.logException(ex);
				}
				project.notifyObjectsChanged((Collection<Object>) (Collection<?>) Arrays.asList(project), true);
				getSelectRevealHelper().selectReveal(resource);
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
		if (adapterType.isAssignableFrom(ADAPTERS[0])) {
			return (T) resourceSet;
		}
		if (adapterType.isAssignableFrom(ADAPTERS[1])) {
			return (T) this;
		}
		return super.getAdapter(adaptable, adapterType);
	}

	private SelectRevealHelper selectRevealer;

	/**
	 * @return
	 */
	protected SelectRevealHelper getSelectRevealHelper() {
		if (selectRevealer == null) {
			selectRevealer = new SelectRevealHelper();
		}
		return selectRevealer;
	}

	protected ResourceSet resourceSet = new ResourceSetImpl();

	public DemoProvider() {
		super(ID);
		resourceSet.eAdapters().add(resourceSaveAdapter);
	}

	@Override
	public void handleLifecycle(ECPContainer context, LifecycleEvent event) {
		super.handleLifecycle(context, event);
		if (context instanceof InternalProject) {
			final InternalProject project = (InternalProject) context;
			switch (event) {
			case INIT:
				initProject(project);
				break;
			case CREATE:
			case DISPOSE:
			case REMOVE:
			}
		}
	}

	@Override
	public boolean hasCreateProjectWithoutRepositorySupport() {
		return true;
	}

	private void initProject(final InternalProject project) {
		try {
			final IProject eProject = ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName());
			if (!eProject.exists()) {
				eProject.create(new NullProgressMonitor());
			}
			eProject.open(new NullProgressMonitor());
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		Resource resource = resourceSet.getResource(getURI(project.getName()), false);
		if (resource == null) {
			resource = resourceSet.createResource(getURI(project.getName()));
		} else {
			try {
				resource.load(Collections.emptyMap());
			} catch (final IOException ex) {
				LoggerUtil.logException(ex);
			}
		}
		if (resource.getContents().isEmpty()) {
			final RootObject root = DomainFactory.eINSTANCE.createRootObject();
			resource.getContents().add(root);
			// root.eAdapters().add(new AdapterImpl() {
			//
			// @Override
			// public void notifyChanged(Notification msg) {
			// if (msg.getFeatureID(RootObject.class) == DomainPackage.ROOT_OBJECT__MODEL_ELEMENTS) {
			// project.notifyObjectsChanged(
			// (Collection<Object>) (Collection<?>) Collections.singleton(project), true);
			// }
			// }
			//
			// });
			try {
				resource.save(Collections.emptyMap());
			} catch (final IOException e) {
				LoggerUtil.logException(e);
			}
		}
		project.getVisiblePackages().add(EcorePackage.eINSTANCE);
		project.getVisiblePackages().add(project.getEditingDomain().getResourceSet().getPackageRegistry()
			.getEPackage(TaskPackage.eINSTANCE.getNsURI()));
		project.getEditingDomain().getResourceSet().eAdapters()
			.add(new AdapterFactoryEditingDomain.EditingDomainProvider(project.getEditingDomain()));
	}

	@Override
	public EditingDomain createEditingDomain(InternalProject project) {
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(
				new AdapterFactory[] { new DomainAdapterFactory(), new CustomReflectiveItemProviderAdapterFactory(),
					new ComposedAdapterFactory(EMF_ADAPTER_FACTORY) }),
			new BasicCommandStack(), resourceSet);
		editingDomain.getResourceSet().eAdapters().add(new ECPModelContextAdapter(project));
		return editingDomain;
	}

	@Override
	public void cloneProject(InternalProject arg0, InternalProject arg1) {

	}

	@Override
	public boolean isThreadSafe() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doDelete(InternalProject arg0, Collection<Object> arg1) {
		// TODO Auto-generated method stub

	}

	// protected class DemoProjectObserver extends EContentAdapter {
	//
	// private final InternalProject project;
	// private final DemoProvider provider;
	//
	// public DemoProjectObserver(InternalProject project, DemoProvider provider) {
	// this.project = project;
	// this.provider = provider;
	// }
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public void notifyChanged(Notification notification) {
	// super.notifyChanged(notification);
	//
	// if (notification.getNotifier() instanceof EObject) {
	// provider.notifyProviderChangeListeners(notification);
	// final EObject eObject = (EObject) notification.getNotifier();
	// final Object[] eObjects = new Object[] { eObject };
	// project.notifyObjectsChanged((Collection<Object>) Arrays.asList(eObjects), false);
	//
	// final Object feature = notification.getFeature();
	// if (feature instanceof EReference) {
	// final EReference eReference = (EReference) feature;
	//
	// if (eReference.isContainment() && notification.getNewValue() instanceof EObject) {
	// project.notifyObjectsChanged(Collections.singleton(notification.getNewValue()), true);
	// }
	//
	// } else if (feature instanceof EAttribute) {
	// final EAttribute eAttribute = (EAttribute) feature;
	//
	// if (notification.getNewValue() instanceof EObject) {
	// project.notifyObjectsChanged(Collections.singleton(notification.getNewValue()), true);
	// }
	//
	// } else if (feature instanceof EOperation) {
	// final EOperation eOperation = (EOperation) feature;
	//
	// if (notification.getNewValue() instanceof EObject) {
	// project.notifyObjectsChanged(Collections.singleton(notification.getNewValue()), true);
	// }
	// }
	// return;
	// }
	//
	// if (notification.getNotifier() instanceof Resource) {
	// project.notifyObjectsChanged(
	// (Collection<Object>) (Collection<?>) Collections.singleton(notification.getNotifier()), true);
	// }
	// // if (notification.getNotifier() instanceof Notifier) {
	// // provider.notifyProviderChangeListeners(notification);
	// //
	// // if (notification.getNewValue() instanceof EObject) {
	// // project.notifyObjectsChanged(Collections.singleton(notification.getNewValue()),
	// // true);
	// // }
	// // if ((notification.getEventType() == ((Notification.REMOVE |
	// // Notification.REMOVE_MANY)
	// // & notification.getEventType())) && notification.getOldValue()
	// // instanceof EObject) {
	// // Collection<Object> oldObjects = null;
	// // if (notification.getOldValue() instanceof Collection<?>)
	// // oldObjects = (Collection<Object>) notification.getOldValue();
	// // else
	// // oldObjects = Collections.singleton(notification.getOldValue());
	// // ECPUtil.getECPObserverBus().notify(ECPProjectContentChangedObserver.class).objectsChanged(project,
	// // oldObjects);
	// // }
	// // }
	// }
	//
	// }

	@Override
	public EList<? extends Object> getElements(InternalProject arg0) {
		return ((RootObject) resourceSet.getResource(getURI(arg0.getName()), true).getContents().get(0))
			.getModelElements();
	}

	@Override
	public void fillChildren(ECPContainer context, Object parent, InternalChildrenList childrenList) {
		if (parent instanceof ECPProject) {
			final Resource resource = resourceSet.getResource(getURI(((ECPProject) parent).getName()), true);
			if (!resource.getContents().isEmpty()) {
				childrenList
					.addChildren(((RootObject) resource
						.getContents().get(0)).getModelElements());
			}
		}
		super.fillChildren(context, parent, childrenList);
	}

	@Override
	public void doSave(InternalProject project) {
		super.doSave(project);
		try {
			resourceSet.getResource(getURI(project.getName()), false).save(Collections.emptyMap());
		} catch (final IOException ex) {
			// Do NOT catch all Exceptions ("catch (Exception e)")
			// Log AND handle Exceptions if possible
			//
			// You can just uncomment one of the lines below to log an exception:
			// logException will show the logged excpetion to the user
			LoggerUtil.logException(ex);
			// ModelUtil.logException("YOUR MESSAGE HERE", ex);
			// logWarning will only add the message to the error log
			// ModelUtil.logWarning("YOUR MESSAGE HERE", ex);
			// ModelUtil.logWarning("YOUR MESSAGE HERE");
			//
			// If handling is not possible declare and rethrow Exception
		}
	}

	@Override
	public boolean isDirty(InternalProject project) {
		return super.isDirty(project) || resourceSet.getResource(getURI(project.getName()), false).isModified();
	}

	@Override
	public Notifier getRoot(InternalProject arg0) {
		final Resource resource = resourceSet.getResource(getURI(arg0.getName()), true);
		if (!resource.getContents().isEmpty()) {
			return resource.getContents().get(0);
		}
		return null;
	}

	private URI getURI(String projectName) {
		if (!projectNameToURIMap.containsKey(projectName)) {
			final URI uri = URI.createPlatformResourceURI(projectName + "/file.xmi", true);
			projectNameToURIMap.put(projectName, uri);
		}
		return projectNameToURIMap.get(projectName);
	}

	@Override
	public ECPContainer getModelContext(Object element) {
		if (element instanceof Resource) {
			final URI uri = ((Resource) element).getURI();
			final String projectName = uri.segment(uri.segmentCount() - 2);
			for (final InternalProject project : getOpenProjects()) {
				if (project.getName().equals(projectName)) {
					return project;
				}
			}
		} else if (element instanceof EObject) {
			return getModelContextFromAdapter(((EObject) element).eContainer().eResource());
		}
		return super.getModelContext(element);
	}

	private static final Class<?>[] ADAPTERS = { ResourceSet.class, ECPProvider.class };

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@Override
	public Class<?>[] getAdapterList() {
		return ADAPTERS;
	}

}
