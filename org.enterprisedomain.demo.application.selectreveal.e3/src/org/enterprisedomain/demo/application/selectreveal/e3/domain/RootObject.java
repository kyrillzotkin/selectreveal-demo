/**
 */
package org.enterprisedomain.demo.application.selectreveal.e3.domain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.enterprisedomain.demo.application.selectreveal.e3.domain.RootObject#getModelElements <em>Model Elements</em>}</li>
 * </ul>
 *
 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.DomainPackage#getRootObject()
 * @model
 * @generated
 */
public interface RootObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Model Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Elements</em>' containment reference list.
	 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.DomainPackage#getRootObject_ModelElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<EObject> getModelElements();

} // RootObject
