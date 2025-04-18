package com.faweb.domain.saml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="SignedInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CanonicalizationMethod">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SignatureMethod">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Reference">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Transforms">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Transform" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="DigestMethod">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="DigestValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="URI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SignatureValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KeyInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="X509Data">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="X509Certificate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
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
    "signedInfo",
    "signatureValue",
    "keyInfo"
})
@XmlRootElement(name = "Signature")
public class Signature {

    @XmlElement(name = "SignedInfo", required = true)
    protected Signature.SignedInfo signedInfo;
    @XmlElement(name = "SignatureValue", required = true)
    protected String signatureValue;
    @XmlElement(name = "KeyInfo", required = true)
    protected Signature.KeyInfo keyInfo;

    /**
     * Gets the value of the signedInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Signature.SignedInfo }
     *     
     */
    public Signature.SignedInfo getSignedInfo() {
        return signedInfo;
    }

    /**
     * Sets the value of the signedInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature.SignedInfo }
     *     
     */
    public void setSignedInfo(Signature.SignedInfo value) {
        this.signedInfo = value;
    }

    /**
     * Gets the value of the signatureValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureValue() {
        return signatureValue;
    }

    /**
     * Sets the value of the signatureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureValue(String value) {
        this.signatureValue = value;
    }

    /**
     * Gets the value of the keyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Signature.KeyInfo }
     *     
     */
    public Signature.KeyInfo getKeyInfo() {
        return keyInfo;
    }

    /**
     * Sets the value of the keyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Signature.KeyInfo }
     *     
     */
    public void setKeyInfo(Signature.KeyInfo value) {
        this.keyInfo = value;
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
     *         &lt;element name="X509Data">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="X509Certificate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
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
        "x509Data"
    })
    public static class KeyInfo {

        @XmlElement(name = "X509Data", required = true)
        protected Signature.KeyInfo.X509Data x509Data;

        /**
         * Gets the value of the x509Data property.
         * 
         * @return
         *     possible object is
         *     {@link Signature.KeyInfo.X509Data }
         *     
         */
        public Signature.KeyInfo.X509Data getX509Data() {
            return x509Data;
        }

        /**
         * Sets the value of the x509Data property.
         * 
         * @param value
         *     allowed object is
         *     {@link Signature.KeyInfo.X509Data }
         *     
         */
        public void setX509Data(Signature.KeyInfo.X509Data value) {
            this.x509Data = value;
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
         *         &lt;element name="X509Certificate" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "x509Certificate"
        })
        public static class X509Data {

            @XmlElement(name = "X509Certificate", required = true)
            protected String x509Certificate;

            /**
             * Gets the value of the x509Certificate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getX509Certificate() {
                return x509Certificate;
            }

            /**
             * Sets the value of the x509Certificate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setX509Certificate(String value) {
                this.x509Certificate = value;
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
     *         &lt;element name="CanonicalizationMethod">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SignatureMethod">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Reference">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Transforms">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Transform" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="DigestMethod">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="DigestValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="URI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "canonicalizationMethod",
        "signatureMethod",
        "reference"
    })
    public static class SignedInfo {

        @XmlElement(name = "CanonicalizationMethod", required = true)
        protected Signature.SignedInfo.CanonicalizationMethod canonicalizationMethod;
        @XmlElement(name = "SignatureMethod", required = true)
        protected Signature.SignedInfo.SignatureMethod signatureMethod;
        @XmlElement(name = "Reference", required = true)
        protected Signature.SignedInfo.Reference reference;

        /**
         * Gets the value of the canonicalizationMethod property.
         * 
         * @return
         *     possible object is
         *     {@link Signature.SignedInfo.CanonicalizationMethod }
         *     
         */
        public Signature.SignedInfo.CanonicalizationMethod getCanonicalizationMethod() {
            return canonicalizationMethod;
        }

        /**
         * Sets the value of the canonicalizationMethod property.
         * 
         * @param value
         *     allowed object is
         *     {@link Signature.SignedInfo.CanonicalizationMethod }
         *     
         */
        public void setCanonicalizationMethod(Signature.SignedInfo.CanonicalizationMethod value) {
            this.canonicalizationMethod = value;
        }

        /**
         * Gets the value of the signatureMethod property.
         * 
         * @return
         *     possible object is
         *     {@link Signature.SignedInfo.SignatureMethod }
         *     
         */
        public Signature.SignedInfo.SignatureMethod getSignatureMethod() {
            return signatureMethod;
        }

        /**
         * Sets the value of the signatureMethod property.
         * 
         * @param value
         *     allowed object is
         *     {@link Signature.SignedInfo.SignatureMethod }
         *     
         */
        public void setSignatureMethod(Signature.SignedInfo.SignatureMethod value) {
            this.signatureMethod = value;
        }

        /**
         * Gets the value of the reference property.
         * 
         * @return
         *     possible object is
         *     {@link Signature.SignedInfo.Reference }
         *     
         */
        public Signature.SignedInfo.Reference getReference() {
            return reference;
        }

        /**
         * Sets the value of the reference property.
         * 
         * @param value
         *     allowed object is
         *     {@link Signature.SignedInfo.Reference }
         *     
         */
        public void setReference(Signature.SignedInfo.Reference value) {
            this.reference = value;
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
         *       &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class CanonicalizationMethod {

            @XmlAttribute(name = "Algorithm", required = true)
            protected String algorithm;

            /**
             * Gets the value of the algorithm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAlgorithm() {
                return algorithm;
            }

            /**
             * Sets the value of the algorithm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAlgorithm(String value) {
                this.algorithm = value;
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
         *         &lt;element name="Transforms">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Transform" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="DigestMethod">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="DigestValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="URI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "transforms",
            "digestMethod",
            "digestValue"
        })
        public static class Reference {

            @XmlElement(name = "Transforms", required = true)
            protected Signature.SignedInfo.Reference.Transforms transforms;
            @XmlElement(name = "DigestMethod", required = true)
            protected Signature.SignedInfo.Reference.DigestMethod digestMethod;
            @XmlElement(name = "DigestValue", required = true)
            protected String digestValue;
            @XmlAttribute(name = "URI", required = true)
            protected String uri;

            /**
             * Gets the value of the transforms property.
             * 
             * @return
             *     possible object is
             *     {@link Signature.SignedInfo.Reference.Transforms }
             *     
             */
            public Signature.SignedInfo.Reference.Transforms getTransforms() {
                return transforms;
            }

            /**
             * Sets the value of the transforms property.
             * 
             * @param value
             *     allowed object is
             *     {@link Signature.SignedInfo.Reference.Transforms }
             *     
             */
            public void setTransforms(Signature.SignedInfo.Reference.Transforms value) {
                this.transforms = value;
            }

            /**
             * Gets the value of the digestMethod property.
             * 
             * @return
             *     possible object is
             *     {@link Signature.SignedInfo.Reference.DigestMethod }
             *     
             */
            public Signature.SignedInfo.Reference.DigestMethod getDigestMethod() {
                return digestMethod;
            }

            /**
             * Sets the value of the digestMethod property.
             * 
             * @param value
             *     allowed object is
             *     {@link Signature.SignedInfo.Reference.DigestMethod }
             *     
             */
            public void setDigestMethod(Signature.SignedInfo.Reference.DigestMethod value) {
                this.digestMethod = value;
            }

            /**
             * Gets the value of the digestValue property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDigestValue() {
                return digestValue;
            }

            /**
             * Sets the value of the digestValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDigestValue(String value) {
                this.digestValue = value;
            }

            /**
             * Gets the value of the uri property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getURI() {
                return uri;
            }

            /**
             * Sets the value of the uri property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setURI(String value) {
                this.uri = value;
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
             *       &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class DigestMethod {

                @XmlAttribute(name = "Algorithm", required = true)
                protected String algorithm;

                /**
                 * Gets the value of the algorithm property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAlgorithm() {
                    return algorithm;
                }

                /**
                 * Sets the value of the algorithm property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAlgorithm(String value) {
                    this.algorithm = value;
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
             *         &lt;element name="Transform" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                "transform"
            })
            public static class Transforms {

                @XmlElement(name = "Transform", required = true)
                protected List<Signature.SignedInfo.Reference.Transforms.Transform> transform;

                /**
                 * Gets the value of the transform property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the transform property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getTransform().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Signature.SignedInfo.Reference.Transforms.Transform }
                 * 
                 * 
                 */
                public List<Signature.SignedInfo.Reference.Transforms.Transform> getTransform() {
                    if (transform == null) {
                        transform = new ArrayList<Signature.SignedInfo.Reference.Transforms.Transform>();
                    }
                    return this.transform;
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
                 *       &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Transform {

                    @XmlAttribute(name = "Algorithm", required = true)
                    protected String algorithm;

                    /**
                     * Gets the value of the algorithm property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAlgorithm() {
                        return algorithm;
                    }

                    /**
                     * Sets the value of the algorithm property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAlgorithm(String value) {
                        this.algorithm = value;
                    }

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
         *       &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class SignatureMethod {

            @XmlAttribute(name = "Algorithm", required = true)
            protected String algorithm;

            /**
             * Gets the value of the algorithm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAlgorithm() {
                return algorithm;
            }

            /**
             * Sets the value of the algorithm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAlgorithm(String value) {
                this.algorithm = value;
            }

        }

    }

}
