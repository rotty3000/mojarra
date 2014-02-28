/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2010 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

// TestFacesMessage.java

package com.sun.faces.application;
import com.sun.faces.cactus.ServletFacesTestCase;
import com.sun.faces.util.Util;

import java.io.*;

import javax.faces.application.FacesMessage;


/**
 *
 *  <B>TestFacesMessage</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 */

/**
 * This class tests the <code>FacesMessage</code> class
 * functionality.  It uses the xml configuration file:
 * <code>web/test/WEB-INF/faces-navigation.xml</code>.
 */
public class TestFacesMessage extends ServletFacesTestCase {
    
//
// Protected Constants
//
    
//
// Class Variables
//
    
//
// Instance Variables
//
    
// Attribute Instance Variables
    
// Relationship Instance Variables
    
//
// Constructors and Initializers
//
    
    public TestFacesMessage() {
        super("TestFacesMessage");
    }
    
    
    public TestFacesMessage(String name) {
        super(name);
    }
//
// Class methods
//
    
//
// Methods from TestCase
//
    
//
// General Methods
//
    
    public void testSerializeable() {
        FacesMessage message = null;
        
        // Case 0 (nothing)
        message = new FacesMessage();
        persistAndCheck(message);

        // Case 1 (summary)
        message = new FacesMessage("This is a bad error.");
        persistAndCheck(message);
        
        // Case 2 (summary & detail)
        message = new FacesMessage("This is a bad error.", "This is a really bad error.");
        persistAndCheck(message);
        
        // Case 3 (severity, summary & detail)
        message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "This is a bad error.",
                "This is a really bad error.");
        persistAndCheck(message);
    }
    
    private void persistAndCheck(FacesMessage message) {
        FacesMessage message1 = null;
        String mSummary, mSummary1 = null;
        String mDetail, mDetail1 = null;
        String severity, severity1 = null;
        ByteArrayOutputStream bos = null;
        ByteArrayInputStream bis = null;
        
        mSummary = message.getSummary();
        mDetail = message.getDetail();
        severity = message.getSeverity().toString();
        
        try {
            bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(message);
            oos.close();
            byte[] bytes = bos.toByteArray();
            InputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(in);
            message1 = (FacesMessage)ois.readObject();
            ois.close();
            mSummary1 = message1.getSummary();
            mDetail1 = message1.getDetail();
            severity1 = message1.getSeverity().toString();
            if (null != mSummary1) {
                assertTrue(mSummary1.equals(mSummary));
            } else {
                assertTrue(mSummary == null);
            }
            if (null != mDetail1) {
                assertTrue(mDetail1.equals(mDetail));
            } else {
                assertTrue(mDetail == null);
            }
            if (null != severity1) {
                assertTrue(severity1.equals(severity));
            } else {
                assertTrue(severity == null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        } 
    }
    
} // end of class TestFacesMessage

