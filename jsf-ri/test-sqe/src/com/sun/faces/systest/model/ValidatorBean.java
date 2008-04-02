/*
 * $Id: ValidatorBean.java,v 1.2 2005/08/22 22:11:40 ofung Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt. 
 * See the License for the specific language governing
 * permission and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.    
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * [Name of File] [ver.__] [Date]
 * 
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.systest.model;

import com.sun.faces.systest.TestValidator01;

import javax.faces.event.AbortProcessingException;
import javax.faces.validator.Validator;


public class ValidatorBean extends Object {

    public ValidatorBean() {
    }

    private Validator validator = null;
    public Validator getValidator() {
        if (validator == null) {
            return new TestValidator01();
        }
        return validator;
    }
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    private Validator doubleValidator = null;
    public Validator getDoubleValidator() {
        if (doubleValidator == null) {
            return new javax.faces.validator.DoubleRangeValidator();
        }
        return doubleValidator;
    }
    public void setDoubleValidator(Validator doubleValidator) {
        this.doubleValidator = doubleValidator;
    }

    private Validator lengthValidator = null;
    public Validator getLengthValidator() {
//        if (lengthValidator == null) {
//System.out.println("RETURN VAL INSTANCE..");
//            return new javax.faces.validator.LengthValidator();
//        }
        return lengthValidator;
    }
    public void setLengthValidator(Validator lengthValidator) {
        this.lengthValidator = lengthValidator;
System.out.println("SET VAL INSTANCE..");
    }

    private Validator longRangeValidator = null;
    public Validator getLongRangeValidator() {
        if (longRangeValidator == null) {
            return new javax.faces.validator.LongRangeValidator();
        }
        return longRangeValidator;
    }
    public void setLongRangeValidator(Validator longRangeValidator) {
        this.longRangeValidator = longRangeValidator;
    }

}
