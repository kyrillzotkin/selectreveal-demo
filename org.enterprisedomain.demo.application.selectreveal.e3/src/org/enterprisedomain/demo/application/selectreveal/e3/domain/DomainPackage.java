/**
 */
package org.enterprisedomain.demo.application.selectreveal.e3.domain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.DomainFactory
 * @model kind="package"
 * @generated
 */
public interface DomainPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "domain";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/makeithappen/application/selectreveal/e3/domain/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "domain";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomainPackage eINSTANCE = org.enterprisedomain.demo.application.selectreveal.e3.domain.impl.DomainPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.enterprisedomain.demo.application.selectreveal.e3.domain.impl.RootObjectImpl <em>Root Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.impl.RootObjectImpl
	 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.impl.DomainPackageImpl#getRootObject()
	 * @generated
	 */
	int ROOT_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Model Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_OBJECT__MODEL_ELEMENTS = 0;

	/**
	 * The number of structural features of the '<em>Root Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Root Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_OBJECT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.enterprisedomain.demo.application.selectreveal.e3.domain.RootObject <em>Root Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root Object</em>'.
	 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.RootObject
	 * @generated
	 */
	EClass getRootObject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.enterprisedomain.demo.application.selectreveal.e3.domain.RootObject#getModelElements <em>Model Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Model Elements</em>'.
	 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.RootObject#getModelElements()
	 * @see #getRootObject()
	 * @generated
	 */
	EReference getRootObject_ModelElements();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DomainFactory getDomainFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.enterprisedomain.demo.application.selectreveal.e3.domain.impl.RootObjectImpl <em>Root Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.impl.RootObjectImpl
		 * @see org.enterprisedomain.demo.application.selectreveal.e3.domain.impl.DomainPackageImpl#getRootObject()
		 * @generated
		 */
		EClass ROOT_OBJECT = eINSTANCE.getRootObject();

		/**
		 * The meta object literal for the '<em><b>Model Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_OBJECT__MODEL_ELEMENTS = eINSTANCE.getRootObject_ModelElements();

	}

} //DomainPackage
