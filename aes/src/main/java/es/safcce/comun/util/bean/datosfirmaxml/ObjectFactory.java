//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.16 at 12:03:14 PM CET 
//


package es.safcce.comun.util.bean.datosfirmaxml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the safccefirma package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: safccefirma
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Firma }
     * 
     */
    public Firma createFirma() {
        return new Firma();
    }

    /**
     * Create an instance of {@link DatosFirma }
     * 
     */
    public DatosFirma createDatosFirma() {
        return new DatosFirma();
    }

    /**
     * Create an instance of {@link DatosFirmante }
     * 
     */
    public DatosFirmante createDatosFirmante() {
        return new DatosFirmante();
    }

    /**
     * Create an instance of {@link HashDocumento }
     * 
     */
    public HashDocumento createHashDocumento() {
        return new HashDocumento();
    }

}
