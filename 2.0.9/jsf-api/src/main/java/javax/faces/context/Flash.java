/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2011 Oracle and/or its affiliates. All rights reserved.
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

package javax.faces.context;

import java.util.Map;

/**
 * <p class="changed_added_2_0">The <strong>Flash</strong> concept is
 * taken from <a target="_"
 * href="http://api.rubyonrails.com/classes/ActionController/Flash.html">Ruby
 * on Rails</a> and provides a way to pass temporary objects between the
 * user views generated by the faces lifecycle.  As in Rails, anything
 * one places in the flash will be exposed to the next view encountered
 * by the same user session and then cleared out.  It is important to
 * note that &#8220;next view&#8221; may have the same view id as
 * the previous view.</p>
 * 
 * <div class="changed_added_2_0">
 *
 * <p><b>Implementation Requirements</b></p>
 *
 * <p>The flash is a <span class="changed_modified_2_0_a">session</span>
 * scoped object that must be thread safe.</p>

 * <p>The implementation requirements will be described in terms of the
 * runtime traversing the JSF lifecycle.  The flash exposes a
 * <code>Map</code> interface over two logical maps.  The choice of
 * which logical map is accessed depends on the current faces lifecycle
 * phase.  One logical map is for the current traversal and the other is
 * for the next traversal.  During the execute portion of the lifecycle,
 * all flash accesses are sent to the current traversal map.  During the
 * render portion of the lifecycle, all flash accesses are sent to the
 * next traversal map.  On the next traversal through the lifecycle, the
 * implementation must ensure that the current traversal map is the next
 * traversal map of the previous traversal.  Here is an example for
 * illustration purposes only.</p>
 *
 * <ul>
 *
 * <p>Consider an initial request to the faces lifecycle</p>
 *
 * <p>Traversal N, execute phase: skipped on initial request.</p>
 *
 * <p>Traversal N, render phase: flash access goes to flash[N].</p>
 *
 * <p>Traversal N+1, execute phase: flash access goes to flash[N].</p>
 *
 * <p>Traversan N+1, render phase: flash access goes to flash[N+1].</p>
 *
 * </ul>
 *
 * <p>The implementation must ensure the proper behaviour of the flash
 * is preserved even in the case of a
 * <code>&lt;navigation-case&gt;</code> that contains a
 * <code>&lt;redirect /&gt;</code>.  The implementation must ensure the
 * proper behavior of the flash is preserved even in the case of
 * adjacent GET requests on the same session.  This allows Faces
 * applications to fully utilize the &#8220;Post/Redirect/Get&#8221;
 * design pattern.</p>
 *
 * <p>The implementation must allow the user to access the flash via the
 * EL implicit object <code>flash</code> and also via {@link
 * javax.faces.context.ExternalContext#getFlash}.  The implementation must
 * ensure that the flash is usable from both JSP and from Facelets for
 * JSF 2.  In addition to exposing the <code>Map</code> interface, there
 * are several features exposed as methods on the <code>Flash</code>
 * itself.  Each of these features may be accessed via the EL as well,
 * as described in the javadocs.</p>
 *
 * <p>EL Usage Example</p>

 * <ul>
 *
 * <p>First page</p>
 *
<code><pre>
&lt;html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:c="http://java.sun.com/jsp/jstl/core"&gt;
&lt;!-- extra code removed --&gt;
  &lt;c:set target="#{flash}" property="foo" value="fooValue" /&gt;

</pre></code>

 * <p>Next page</p>
 *
<code><pre>
&lt;html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"&gt;
&lt;!-- extra code removed --&gt;
  &lt;h:outputText value="#{flash.foo}" /&gt; will be "fooValue"
  without the quotes.

</pre></code>

 * </ul>
 *
 * <p>The same usage syntax must be available in JSP.</p>

 * <p>Note that extra action must be taken when using the flash in
 * concert with output components that cause the browser to issue a GET
 * request when clicked, such as <code>h:button</code> and
 * <code>h:link</code>.  The following example illustrates one way to
 * use the flash in such circumstances.</p>

 * <ul>

 * <p>First page</p>

<code><pre>
&lt;h:button id="nextButton" value="Next (button)" outcome="next.xhtml"&gt;
  &lt;f:param name="foo" value="bar"/&gt;
&lt;/h:button&gt;
</pre></code>

 * <p>Next page</p>

<code><pre>
&lt;html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:h="http://java.sun.com/jsf/html"&gt;
&lt;f:metadata&gt;
  &lt;f:viewParam name="foo" id="foo" value="#{flash.now.foo}" /&gt;
&lt;/f:metadata&gt;
&lt;head /&gt;&lt;body&gt;
foo = #{flash.foo}
&lt;/body&gt;
&lt;/html&gt;
</pre></code>

 * </ul>

 * <p>Note that this example uses <code>#{flash.now}</code> on the
 * second page.  This is because the value doesn't actuall enter the
 * flash until the server is processing the GET request sent by the
 * browser due to the button being clicked.</p>

 * </div>
 *
 * @since 2.0

 */
public abstract class Flash implements Map<String, Object> {
    

    /**
     * <p class="changed_added_2_0">Return the value of this JavaBeans
     * property for the flash for this session.  This value determines
     * whether or not any {@link javax.faces.application.FacesMessage}
     * instances queued in the current {@link
     * javax.faces.context.FacesContext} must be preserved so they are
     * accessible on the next traversal of the lifecycle on this
     * session, regardless of the request being a redirect after post,
     * or a normal postback.  <code>Map</code> accesses for the special
     * key &#8220;<code>keepMessages</code>&#8221; must return the value
     * of this JavaBeans property.</p>

     * <div class="changed_added_2_0">

     *
     * <p>EL Usage Example</p>

     * <ul>
     *
     * <p>First page</p>
     *
<code><pre>
&lt;html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:c="http://java.sun.com/jsp/jstl/core"&gt;
&lt;!-- extra code removed --&gt;
  &lt;c:set target="#{flash}" property="keepMessages" value="true" /&gt;

</pre></code>

     * <p>Next page</p>
     *
<code><pre>
&lt;html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"&gt;
&lt;!-- extra code removed --&gt;
  &lt;h:messages /&gt; Any messages present on the first page must be displayed on 
  this page.

</pre></code>

     * </ul>
     *
     * </div>
     *
     * @since 2.0
     *
     */
    public abstract boolean isKeepMessages();

    /**
     * <p class="changed_added_2_0">Setter for <code>keepMessages</code>
     * JavaBeans property.  See {@link #isKeepMessages}.</p>
     *
     * @param newValue the new value for this property on this session.
     *
     * @since 2.0
     */
    public abstract void setKeepMessages(boolean newValue);

    /**
     * <p class="changed_added_2_0">Return the value of this property
     * for the flash for this session.  This must be <code>false</code>
     * unless:</p>

     * <div class="changed_added_2_0">

     *
     * <ul>
     *
     *  <li><p>{@link #setRedirect} was called for the current
     *  lifecycle traversal with <code>true</code> as the
     *  argument.</p></li>
     *
     * <li><p>The current lifecycle traversal for this session is in the
     * &#8220;execute&#8221; phase and the previous traversal had {@link
     * #setRedirect} called with <code>true</code> as the
     * argument.</p></li>
     *
     * </div>

     */
    
    public abstract boolean isRedirect();

    /**
     * <p class="changed_added_2_0">Setting this property to
     * <code>true</code> indicates that the next request on this session
     * will be a redirect.  Recall that on a redirect, the server sends
     * a special response to the client instructing it to issue a new
     * request to a specific URI.  The implementation must insure that
     * reading the value of this property on that request will return
     * <code>true</code>.  </p>

     * <div class="changed_added_2_0">

     * <p>EL Usage Example</p>

     * <ul>
     *
<code><pre>
&lt;html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:c="http://java.sun.com/jsp/jstl/core"&gt;
&lt;!-- extra code removed --&gt;
  &lt;c:set target="#{flash}" property="redirect" value="true" /&gt;

</pre></code>

     * </ul>
     *
     * </div>

     * 
     * @param newValue the new value for this property on this session.
     *
     * @since 2.0
     *
     */
    
    public abstract void setRedirect(boolean newValue);

    /**
     * <p class="changed_added_2_0">Puts a value in the flash so that it
     * can be accessed on this traversal of the lifecycle, rather than
     * on the next traversal.  This is simply an alias for putting a
     * value in the request map.</p>
     *
     * <div class="changed_added_2_0">

     * <p>EL Usage Example</p>

     * <ul>
     *
<code><pre>
&lt;html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:c="http://java.sun.com/jsp/jstl/core"&gt;
&lt;!-- extra code removed --&gt;
  &lt;c:set target="#{flash.now}" property="bar" value="barValue" /&gt;

  &lt;p&gt;Value of \#{flash.now.bar}, should be barValue.&lt;/p&gt;

  &lt;h:outputText value="#{flash.now.bar}" /&gt;

</pre></code>

     * </ul>
     *
     * </div>
     *
     * @param key the key for this entry

     * @param value the value for this entry

     * @since 2.0

     */
    
    public abstract void putNow(String key, Object value);

    /**
     * <p class="changed_added_2_0">Causes a value stored with a
     * previous call to {@link #putNow}, its EL equivalent, or to the
     * request <code>Map</code>, to be promoted to the flash so that is
     * available on the next traversal through the lifecycle on this
     * session.</p>
     *
     * @param key if argument <code>key</code> is the name of an entry
     * previously stored to the flash on this traversal through the
     * lifecycle via a call to {@link #putNow}, or to a set to the EL
     * expression <code>#{flash.now.<i>&lt;key&gt;</i>}</code>, or to the
     * request <code>Map</code>, to be promoted to the flash as if a call
     * to <code>put()</code> or a set to the expression
     * <code>#{flash.<i>&lt;key&gt;</i>}</code> was being called.
     */
    
    public abstract void keep(String key);


    /**
     * <p class="changed_added_2_0">Called before the execution of every
     * lifecycle phase, this method allows implementations to take the
     * necessary actions to provide the Flash scope contract as it
     * applies to the request procesing lifecycle. </p>
     *
     * @param ctx the <code>FacesContext</code> for this request.
     */
    public abstract void doPrePhaseActions(FacesContext ctx);


    /**
     * <p class="changed_added_2_0">Called after the execution of every
     * lifecycle phase, this method allows implementations to take the
     * necessary actions to provide the Flash scope contract as it
     * applies to the request procesing lifecycle. </p>
     *
     * @param ctx the <code>FacesContext</code> for this request.
     */
    public abstract void doPostPhaseActions(FacesContext ctx);
    

}
