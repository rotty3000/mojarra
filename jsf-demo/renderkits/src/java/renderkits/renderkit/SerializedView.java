/*
 * $Id: SerializedView.java,v 1.2 2005/08/22 22:09:30 ofung Exp $
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

package renderkits.renderkit;

import java.io.Serializable;
 
/**
 * <p>Convenience struct for encapsulating tree structure and
 * component state.</p> 
 */
public class SerializedView extends Object implements Serializable {
    private Object structure = null;
    private Object state = null;
                                                                                                                           
    public SerializedView(Object newStructure, Object newState) {
        structure = newStructure;
        state = newState;
    }
                                                                                                                           
    public Object getStructure() {
        return structure;
    }
                                                                                                                           
    public Object getState() {
        return state;
    }
}

