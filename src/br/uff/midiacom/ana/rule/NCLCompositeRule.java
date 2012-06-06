/********************************************************************************
 * This file is part of the API for NCL Authoring - aNa.
 *
 * Copyright (c) 2011, MidiaCom Lab (www.midiacom.uff.br)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * All advertising materials mentioning features or use of this software must
 *    display the following acknowledgment:
 *        This product includes the API for NCL Authoring - aNa
 *        (http://joeldossantos.github.com/aNa).
 *
 *  * Neither the name of the lab nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without specific
 *    prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY MIDIACOM LAB AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE MÍDIACOM LAB OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *******************************************************************************/
package br.uff.midiacom.ana.rule;

import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.NCLElementImpl;
import br.uff.midiacom.ana.datatype.ncl.NCLParsingException;
import br.uff.midiacom.ana.datatype.enums.NCLElementAttributes;
import br.uff.midiacom.ana.datatype.enums.NCLElementSets;
import br.uff.midiacom.ana.datatype.enums.NCLOperator;
import br.uff.midiacom.ana.datatype.ncl.NCLIdentifiableElementPrototype;
import br.uff.midiacom.xml.XMLException;
import br.uff.midiacom.xml.datatype.elementList.ElementList;
import br.uff.midiacom.xml.datatype.elementList.IdentifiableElementList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Class that represents a composite rule.
 * 
 * <br/>
 * 
 * This element defines the attributes:
 * <ul>
 *  <li><i>id</i> - id of the composite rule element. This attribute is required.</li>
 *  <li><i>operator</i> - boolean operator relating the composite rule children
 *                        elements. This attribute is required.</li>
 * </ul>
 * 
 * <br/>
 * 
 * This element has as children the elements:
 * <ul>
 *  <li><i>rule</i> - element representing a simple rule inside the composite
 *                    rule. The composite rule can have none or several rule
 *                    elements.</li>
 *  <li><i>compositeRule</i> - element representing a composite rule inside the
 *                             composite rule. The composite rule can have none
 *                             or several composite rule elements.</li>
 * </ul>
 * 
 * Note that the composite rule must have at least one child element, which can
 * be a simple or a composite rule.
 * 
 * @param <T>
 * @param <P>
 * @param <I> 
 */
public class NCLCompositeRule<T extends NCLTestRule,
                              P extends NCLElement,
                              I extends NCLElementImpl,
                              Eb extends NCLBindRule>
        extends NCLIdentifiableElementPrototype<T, P, I>
        implements NCLTestRule<T, P, Eb> {

    protected NCLOperator operator;
    protected IdentifiableElementList<T, T> rules;
    
    protected ElementList<Eb,P> references;


    /**
     * Composite rule constructor.
     * 
     * @throws XMLException 
     *          if an error occur while creating the element.
     */
    public NCLCompositeRule() throws XMLException {
        super();
        rules = new IdentifiableElementList<T, T>();
        references = new ElementList<Eb,P>();
    }
    
    
    public NCLCompositeRule(String id) throws XMLException {
        super();
        rules = new IdentifiableElementList<T, T>();
        references = new ElementList<Eb,P>();
        setId(id);
    }


    /**
     * Set the composite rule boolean operator relating the composite rule
     * children elements. This attribute is required and can not be set to
     * <i>null</i>. The possible operators to be used are defined in the
     * enumeration <i>NCLOperator</i>.
     *
     * @param operator
     *          composite rule operator from the enumeration <i>NCLOperator</i>.
     * @throws XMLException 
     *          if the value representing the operator is null.
     */
    public void setOperator(NCLOperator operator) throws XMLException {
        if(operator == null)
            throw new XMLException("Null operator.");
        
        NCLOperator aux = this.operator;
        this.operator = operator;
        impl.notifyAltered(NCLElementAttributes.OPERATOR, aux, operator);
    }


    /**
     * Returns the composite rule boolean operator relating the composite rule
     * children elements or <i>null</i> if the attribute is not defined. The
     * possible operators to be used are defined in the enumeration
     * <i>NCLOperator</i>.
     * 
     * @return
     *          composite rule operator from the enumeration <i>NCLOperator</i>
     *          or <i>null</i> if the operator is not defined.
     */
    public NCLOperator getOperator() {
        return operator;
    }


    /**
     * Adds a rule to the composite rule. The rule can be a simple rule or a
     * composite rule. The composite rule must have at least one rule.
     * 
     * @param rule
     *          element representing a rule. This rule can be a simple rule or a
     *          composite rule.
     * @return
     *          true if the rule was added.
     * @throws XMLException 
     *          if the element representing the rule is null.
     */
    public boolean addRule(T rule) throws XMLException {
        if(rules.add(rule, (T) this)){
            impl.notifyInserted(NCLElementSets.RULES, rule);
            return true;
        }
        return false;
    }


    /**
     * Removes a rule of the composite rule. The rule can be a simple rule or a
     * composite rule. The composite rule must have at least one rule.
     * 
     * @param rule
     *          element representing a rule. This rule can be a simple rule or a
     *          composite rule.
     * @return
     *          true if the rule was removed.
     * @throws XMLException 
     *          if the element representing the rule is null.
     */
    public boolean removeRule(T rule) throws XMLException {
        if(rules.remove(rule)){
            impl.notifyRemoved(NCLElementSets.RULES, rule);
            return true;
        }
        return false;
    }


    /**
     * Removes a rule of the composite rule. The rule can be a simple rule or a
     * composite rule. The composite rule must have at least one rule.
     * 
     * @param id
     *          string representing the id of the element representing a rule.
     *          This rule can be a simple rule or a composite rule.
     * @return
     *          true if the rule was removed.
     * @throws XMLException 
     *          if the string is null or empty.
     */
    public boolean removeRule(String id) throws XMLException {
        if(rules.remove(id)){
            impl.notifyRemoved(NCLElementSets.RULES, id);
            return true;
        }
        return false;
    }


    /**
     * Verifies if the composite rule has a specific element representing
     * a rule. The rule can be a simple rule or a composite rule. The composite
     * rule must have at least one rule.
     * 
     * @param rule
     *          element representing a rule. This rule can be a simple rule or a
     *          composite rule.
     * @return
     *          true if the composite rule has the rule element.
     * @throws XMLException 
     *          if the element representing the rule is null.
     */
    public boolean hasRule(T rule) throws XMLException {
        return rules.contains(rule);
    }


    /**
     * Verifies if the composite rule has a rule with a specific id. The rule can
     * be a simple rule or a composite rule. The composite rule must have at
     * least one rule.
     * 
     * @param id
     *          string representing the id of the element representing a rule.
     *          This rule can be a simple rule or a composite rule.
     * @return
     *          true if the composite rule has the rule element.
     * @throws XMLException 
     *          if the string is null or empty.
     */
    public boolean hasRule(String id) throws XMLException {
        return rules.get(id) != null;
    }


    /**
     * Verifies if the composite rule has at least one rule. The rule can
     * be a simple rule or a composite rule. The composite rule must have at
     * least one rule.
     * 
     * @return 
     *          true if the composite rule has at least rule.
     */
    public boolean hasRule() {
        return !rules.isEmpty();
    }


    /**
     * Returns the list of rules that a composite rule have. The rule can
     * be a simple rule or a composite rule. The composite rule must have at
     * least one rule.
     * 
     * @return 
     *          element list with all rules.
     */
    public IdentifiableElementList<T, T> getRules() {
        return rules;
    }


    @Override
    public String parse(int ident) {
        String space, content;

        if(ident < 0)
            ident = 0;

        // Element indentation
        space = "";
        for(int i = 0; i < ident; i++)
            space += "\t";


        // param element and attributes declaration
        content = space + "<compositeRule";
        content += parseAttributes();
        content += ">\n";

        content += parseElements(ident + 1);

        content += "</compositeRule>\n";

        return content;
    }


    @Override
    public void load(Element element) throws NCLParsingException {
        NodeList nl;

        try{
            loadId(element);
            loadOperator(element);
        }
        catch(XMLException ex){
            String aux = getId();
            if(aux != null)
                aux = "(" + aux + ")";
            else
                aux = "";
            
            throw new NCLParsingException("CompositeRule" + aux + ":\n" + ex.getMessage());
        }

        try{
            // create the child nodes
            nl = element.getChildNodes();
            for(int i=0; i < nl.getLength(); i++){
                Node nd = nl.item(i);
                if(nd instanceof Element){
                    Element el = (Element) nl.item(i);

                    loadRules(el);
                    loadCompositeRules(el);
                }
            }
        }
        catch(XMLException ex){
            String aux = getId();
            if(aux != null)
                aux = "(" + aux + ")";
            else
                aux = "";
            
            throw new NCLParsingException("CompositeRule" + aux + " > " + ex.getMessage());
        }
    }
    
    
    protected String parseAttributes() {
        String content = "";
        
        content += parseId();
        content += parseOperator();
        
        return content;
    }
    
    
    protected String parseElements(int ident) {
        String content = "";
        
        content += parseRules(ident);
        
        return content;
    }
    
    
    protected String parseId() {
        String aux = getId();
        if(aux != null)
            return " id='" + aux + "'";
        else
            return "";
    }
    
    
    protected void loadId(Element element) throws XMLException {
        String att_name, att_var;
        
        // set the id (required)
        att_name = NCLElementAttributes.ID.toString();
        if(!(att_var = element.getAttribute(att_name)).isEmpty())
            setId(att_var);
        else
            throw new NCLParsingException("Could not find " + att_name + " attribute.");
    }
    
    
    protected String parseOperator() {
        NCLOperator aux = getOperator();
        if(aux != null)
            return " operator='" + aux.toString() + "'";
        else
            return "";
    }
    
    
    protected void loadOperator(Element element) throws XMLException {
        String att_name, att_var;
        
        // set the operator (required)
        att_name = NCLElementAttributes.OPERATOR.toString();
        if(!(att_var = element.getAttribute(att_name)).isEmpty())
            setOperator(NCLOperator.getEnumType(att_var));
        else
            throw new NCLParsingException("Could not find " + att_name + " attribute.");
    }
    
    
    protected String parseRules(int ident) {
        if(!hasRule())
            return "";
        
        String content = "";
        for(T aux : rules)
            content += aux.parse(ident);
        
        return content;
    }
    
    
    protected void loadRules(Element element) throws XMLException {
        //create the rules
        if(element.getTagName().equals(NCLElementAttributes.RULE.toString())){
            T inst = createRule(); 
            addRule(inst);
            inst.load(element);
        }
    }
    
    
    protected void loadCompositeRules(Element element) throws XMLException {
        // create the compositeRules
        if(element.getTagName().equals(NCLElementAttributes.COMPOSITERULE.toString())){
            T inst = createCompositeRule();
            addRule(inst);
            inst.load(element);
        }
    }
    
    
    @Override
    public T findRule(String id) throws XMLException {
        T result;
        
        if(getId().equals(id))
            return (T) this;
        
        for(T rule : rules){
            result = (T) rule.findRule(id);
            if(result != null)
                return result;
        }
        
        return null;
    }
    
    
    @Override
    public boolean addReference(Eb reference) throws XMLException {
        return references.add(reference, null);
    }
    
    
    @Override
    public boolean removeReference(Eb reference) throws XMLException {
        return references.remove(reference);
    }
    
    
    @Override
    public ElementList getReferences() {
        return references;
    }


    /**
     * Function to create the child element <i>compositeRule</i>.
     * This function must be overwritten in classes that extends this one.
     *
     * @return
     *          element representing the child <i>compositeRule</i>.
     */
    protected T createCompositeRule() throws XMLException {
        return (T) new NCLCompositeRule();
    }


    /**
     * Function to create the child element <i>rule</i>.
     * This function must be overwritten in classes that extends this one.
     *
     * @return
     *          element representing the child <i>rule</i>.
     */
    protected T createRule() throws XMLException {
        return (T) new NCLRule();
    }
}
