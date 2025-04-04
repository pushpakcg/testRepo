package com.faweb.domain.saml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Issuer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature"/>
 *         &lt;element name="Subject">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SubjectConfirmation">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SubjectConfirmationData">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="NotOnOrAfter" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                                     &lt;attribute name="Recipient" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Method" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Conditions">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AudienceRestriction">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Audience" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="NotBefore" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                 &lt;attribute name="NotOnOrAfter" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AttributeStatement">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Attribute" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AttributeValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AuthnStatement">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AuthnContext">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AuthnContextClassRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="AuthnInstant" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IssueInstant" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "issuer",
    "signature",
    "subject",
    "conditions",
    "attributeStatement",
    "authnStatement"
})
@XmlRootElement(name = "Assertion", namespace = "urn:oasis:names:tc:SAML:2.0:assertion")
public class Assertion {

    @XmlElement(name = "Issuer", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
    protected String issuer;
    @XmlElement(name = "Signature", required = true)
    protected Signature signature;
    @XmlElement(name = "Subject", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
    protected Assertion.Subject subject;
    @XmlElement(name = "Conditions", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
    protected Assertion.Conditions conditions;
    @XmlElement(name = "AttributeStatement", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
    protected Assertion.AttributeStatement attributeStatement;
    @XmlElement(name = "AuthnStatement", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
    protected Assertion.AuthnStatement authnStatement;
    @XmlAttribute(name = "ID", required = true)
    protected String id;
    @XmlAttribute(name = "IssueInstant", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issueInstant;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;

    /**
     * Gets the value of the issuer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Sets the value of the issuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuer(String value) {
        this.issuer = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link Signature }
     *     
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature }
     *     
     */
    public void setSignature(Signature value) {
        this.signature = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link Assertion.Subject }
     *     
     */
    public Assertion.Subject getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assertion.Subject }
     *     
     */
    public void setSubject(Assertion.Subject value) {
        this.subject = value;
    }

    /**
     * Gets the value of the conditions property.
     * 
     * @return
     *     possible object is
     *     {@link Assertion.Conditions }
     *     
     */
    public Assertion.Conditions getConditions() {
        return conditions;
    }

    /**
     * Sets the value of the conditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assertion.Conditions }
     *     
     */
    public void setConditions(Assertion.Conditions value) {
        this.conditions = value;
    }

    /**
     * Gets the value of the attributeStatement property.
     * 
     * @return
     *     possible object is
     *     {@link Assertion.AttributeStatement }
     *     
     */
    public Assertion.AttributeStatement getAttributeStatement() {
        return attributeStatement;
    }

    /**
     * Sets the value of the attributeStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assertion.AttributeStatement }
     *     
     */
    public void setAttributeStatement(Assertion.AttributeStatement value) {
        this.attributeStatement = value;
    }

    /**
     * Gets the value of the authnStatement property.
     * 
     * @return
     *     possible object is
     *     {@link Assertion.AuthnStatement }
     *     
     */
    public Assertion.AuthnStatement getAuthnStatement() {
        return authnStatement;
    }

    /**
     * Sets the value of the authnStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assertion.AuthnStatement }
     *     
     */
    public void setAuthnStatement(Assertion.AuthnStatement value) {
        this.authnStatement = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the issueInstant property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssueInstant() {
        return issueInstant;
    }

    /**
     * Sets the value of the issueInstant property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssueInstant(XMLGregorianCalendar value) {
        this.issueInstant = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Attribute" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AttributeValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "attribute"
    })
    public static class AttributeStatement {

        @XmlElement(name = "Attribute", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
        protected List<Assertion.AttributeStatement.Attribute> attribute;

        /**
         * Gets the value of the attribute property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attribute property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttribute().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Assertion.AttributeStatement.Attribute }
         * 
         * 
         */
        public List<Assertion.AttributeStatement.Attribute> getAttribute() {
            if (attribute == null) {
                attribute = new ArrayList<Assertion.AttributeStatement.Attribute>();
            }
            return this.attribute;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="AttributeValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "attributeValue"
        })
        public static class Attribute {

            @XmlElement(name = "AttributeValue", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
			private List<String> attributeValue;
            @XmlAttribute(name = "Name", required = true)
            protected String name;

            /**
             * Gets the value of the attributeValue property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
           
            /**
             * Sets the value of the attributeValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
           

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

			public List<String> getAttributeValue() {
				return attributeValue;
			}

			public void setAttributeValue(List<String> attributeValue) {
				this.attributeValue = attributeValue;
			}

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="AuthnContext">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AuthnContextClassRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="AuthnInstant" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "authnContext"
    })
    public static class AuthnStatement {

        @XmlElement(name = "AuthnContext", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
        protected Assertion.AuthnStatement.AuthnContext authnContext;
        @XmlAttribute(name = "AuthnInstant", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar authnInstant;

        /**
         * Gets the value of the authnContext property.
         * 
         * @return
         *     possible object is
         *     {@link Assertion.AuthnStatement.AuthnContext }
         *     
         */
        public Assertion.AuthnStatement.AuthnContext getAuthnContext() {
            return authnContext;
        }

        /**
         * Sets the value of the authnContext property.
         * 
         * @param value
         *     allowed object is
         *     {@link Assertion.AuthnStatement.AuthnContext }
         *     
         */
        public void setAuthnContext(Assertion.AuthnStatement.AuthnContext value) {
            this.authnContext = value;
        }

        /**
         * Gets the value of the authnInstant property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getAuthnInstant() {
            return authnInstant;
        }

        /**
         * Sets the value of the authnInstant property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setAuthnInstant(XMLGregorianCalendar value) {
            this.authnInstant = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="AuthnContextClassRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "authnContextClassRef"
        })
        public static class AuthnContext {

            @XmlElement(name = "AuthnContextClassRef", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
            protected String authnContextClassRef;

            /**
             * Gets the value of the authnContextClassRef property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAuthnContextClassRef() {
                return authnContextClassRef;
            }

            /**
             * Sets the value of the authnContextClassRef property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAuthnContextClassRef(String value) {
                this.authnContextClassRef = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="AudienceRestriction">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Audience" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="NotBefore" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       &lt;attribute name="NotOnOrAfter" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "audienceRestriction"
    })
    public static class Conditions {

        @XmlElement(name = "AudienceRestriction", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
        protected Assertion.Conditions.AudienceRestriction audienceRestriction;
        @XmlAttribute(name = "NotBefore", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar notBefore;
        @XmlAttribute(name = "NotOnOrAfter", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar notOnOrAfter;

        /**
         * Gets the value of the audienceRestriction property.
         * 
         * @return
         *     possible object is
         *     {@link Assertion.Conditions.AudienceRestriction }
         *     
         */
        public Assertion.Conditions.AudienceRestriction getAudienceRestriction() {
            return audienceRestriction;
        }

        /**
         * Sets the value of the audienceRestriction property.
         * 
         * @param value
         *     allowed object is
         *     {@link Assertion.Conditions.AudienceRestriction }
         *     
         */
        public void setAudienceRestriction(Assertion.Conditions.AudienceRestriction value) {
            this.audienceRestriction = value;
        }

        /**
         * Gets the value of the notBefore property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getNotBefore() {
            return notBefore;
        }

        /**
         * Sets the value of the notBefore property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setNotBefore(XMLGregorianCalendar value) {
            this.notBefore = value;
        }

        /**
         * Gets the value of the notOnOrAfter property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getNotOnOrAfter() {
            return notOnOrAfter;
        }

        /**
         * Sets the value of the notOnOrAfter property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setNotOnOrAfter(XMLGregorianCalendar value) {
            this.notOnOrAfter = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Audience" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "audience"
        })
        public static class AudienceRestriction {

            @XmlElement(name = "Audience", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
            protected String audience;

            /**
             * Gets the value of the audience property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAudience() {
                return audience;
            }

            /**
             * Sets the value of the audience property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAudience(String value) {
                this.audience = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="SubjectConfirmation">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SubjectConfirmationData">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="NotOnOrAfter" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *                           &lt;attribute name="Recipient" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Method" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "subjectConfirmation"
    })
    public static class Subject {

        @XmlElement(name = "SubjectConfirmation", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
        protected Assertion.Subject.SubjectConfirmation subjectConfirmation;

        /**
         * Gets the value of the subjectConfirmation property.
         * 
         * @return
         *     possible object is
         *     {@link Assertion.Subject.SubjectConfirmation }
         *     
         */
        public Assertion.Subject.SubjectConfirmation getSubjectConfirmation() {
            return subjectConfirmation;
        }

        /**
         * Sets the value of the subjectConfirmation property.
         * 
         * @param value
         *     allowed object is
         *     {@link Assertion.Subject.SubjectConfirmation }
         *     
         */
        public void setSubjectConfirmation(Assertion.Subject.SubjectConfirmation value) {
            this.subjectConfirmation = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="SubjectConfirmationData">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="NotOnOrAfter" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
         *                 &lt;attribute name="Recipient" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="Method" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "subjectConfirmationData"
        })
        public static class SubjectConfirmation {

            @XmlElement(name = "SubjectConfirmationData", namespace = "urn:oasis:names:tc:SAML:2.0:assertion", required = true)
            protected Assertion.Subject.SubjectConfirmation.SubjectConfirmationData subjectConfirmationData;
            @XmlAttribute(name = "Method", required = true)
            protected String method;

            /**
             * Gets the value of the subjectConfirmationData property.
             * 
             * @return
             *     possible object is
             *     {@link Assertion.Subject.SubjectConfirmation.SubjectConfirmationData }
             *     
             */
            public Assertion.Subject.SubjectConfirmation.SubjectConfirmationData getSubjectConfirmationData() {
                return subjectConfirmationData;
            }

            /**
             * Sets the value of the subjectConfirmationData property.
             * 
             * @param value
             *     allowed object is
             *     {@link Assertion.Subject.SubjectConfirmation.SubjectConfirmationData }
             *     
             */
            public void setSubjectConfirmationData(Assertion.Subject.SubjectConfirmation.SubjectConfirmationData value) {
                this.subjectConfirmationData = value;
            }

            /**
             * Gets the value of the method property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMethod() {
                return method;
            }

            /**
             * Sets the value of the method property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMethod(String value) {
                this.method = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="NotOnOrAfter" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
             *       &lt;attribute name="Recipient" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class SubjectConfirmationData {

                @XmlAttribute(name = "NotOnOrAfter", required = true)
                @XmlSchemaType(name = "dateTime")
                protected XMLGregorianCalendar notOnOrAfter;
                @XmlAttribute(name = "Recipient", required = true)
                protected String recipient;

                /**
                 * Gets the value of the notOnOrAfter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getNotOnOrAfter() {
                    return notOnOrAfter;
                }

                /**
                 * Sets the value of the notOnOrAfter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setNotOnOrAfter(XMLGregorianCalendar value) {
                    this.notOnOrAfter = value;
                }

                /**
                 * Gets the value of the recipient property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRecipient() {
                    return recipient;
                }

                /**
                 * Sets the value of the recipient property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRecipient(String value) {
                    this.recipient = value;
                }

            }

        }

    }

}
