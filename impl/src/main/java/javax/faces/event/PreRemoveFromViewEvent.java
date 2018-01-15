/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
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

package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * <p class="changed_added_2_0">When an instance of this event is passed
 * to {@link SystemEventListener#processEvent} or {@link
 * ComponentSystemEventListener#processEvent}, the listener
 * implementation may assume that the <code>source</code> of this event
 * instance is a {@link UIComponent} instance that is about to be
 * removed from the view.  Therefore, the implementation may assume it
 * is safe to call {@link UIComponent#getParent}, {@link
 * UIComponent#getClientId}, and other methods that depend upon the
 * component instance being added into the view.</p> 

 * @since 2.0
 */
public class PreRemoveFromViewEvent extends ComponentSystemEvent {


    // ------------------------------------------------------------ Constructors


    private static final long serialVersionUID = 1715650557625760365L;

    /**
     * <p class="changed_added_2_0">Instantiate a new
     * <code>BeforeRemoveFromView</code> that indicates the argument
     * <code>component</code> is about to be removed from the view.</p>

     * @param component the <code>UIComponent</code> that is about to be
     * removed from the view.
     *
     * @throws IllegalArgumentException if <code>component</code> is
     *  <code>null</code>
     */
    public PreRemoveFromViewEvent(UIComponent component) {

        super(component);

    }

    /**
     * <p class="changed_added_2_3">Instantiate a new
     * <code>BeforeRemoveFromView</code> that indicates the argument
     * <code>component</code> is about to be removed from the view.</p>
     * 
     * @param facesContext the Faces context.
     * @param component the <code>UIComponent</code> that is about to be
     * removed from the view.
     * @throws IllegalArgumentException if <code>component</code> is
     *  <code>null</code>
     */
    public PreRemoveFromViewEvent(FacesContext facesContext, UIComponent component) {
        super(facesContext, component);
    }
    
    // --------------------------------------- Methods from ComponentSystemEvent


    /**
     * <p class="changed_added_2_0">Returns <code>true</code> if and
     * only if the argument <code>listener</code> is an instance of
     * {@link SystemEventListener}.</p>
     * 
     * @param listener the faces listener.
     * @return true if the listener is an appropriate one, false otherwise.
     */
    @Override
    public boolean isAppropriateListener(FacesListener listener) {

        return (listener instanceof SystemEventListener);
        
    }
    

}