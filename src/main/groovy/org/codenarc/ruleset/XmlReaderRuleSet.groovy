/*
 * Copyright 2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.ruleset

import org.codenarc.util.PropertyUtil
import org.apache.log4j.Logger
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import groovy.xml.Namespace

/**
 * A <code>RuleSet</code> implementation that parses Rule definitions from XML read from a
 * <code>Reader</code>. Note that this class attempts to read and parse the XML from within
 * the constructor.
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class XmlReaderRuleSet implements RuleSet {
    static final LOG = Logger.getLogger(XmlReaderRuleSet)
    static final r = new Namespace('http://codenarc.org/ruleset/1.0')
    private List rules = []

    /**
     * Construct a new instance on the specified Reader
     * @param reader - the Reader from which the XML will be read; must not be null
     */
    XmlReaderRuleSet(Reader reader) {
        assert reader
        def xml = reader.text
        validateXml(xml)

        def ruleset = new XmlParser().parseText(xml)
        loadRuleSetRefElements(ruleset)
        loadRuleElements(ruleset)
        rules = rules.asImmutable()
    }

    /**
     * @return a List of Rule objects
     */
    List getRules() {
        return rules
    }

    //--------------------------------------------------------------------------
    // Internal Helper Methods
    //--------------------------------------------------------------------------

    private void loadRuleSetRefElements(ruleset) {
        ruleset[r.'ruleset-ref'].each { ruleSetRefNode ->
            def ruleSetPath = ruleSetRefNode.attribute('path')
            def refRuleSet = new XmlFileRuleSet(ruleSetPath)
            def allRules = refRuleSet.rules
            def filteredRuleSet = new FilteredRuleSet(refRuleSet)
            ruleSetRefNode[r.'include'].each { includeNode ->
                def includeRuleName = includeNode.attribute('name')
                filteredRuleSet.addInclude(includeRuleName)
            }
            ruleSetRefNode[r.'exclude'].each { excludeNode ->
                def excludeRuleName = excludeNode.attribute('name')
                filteredRuleSet.addExclude(excludeRuleName)
            }
            ruleSetRefNode[r.'rule-config'].each { configNode ->
                def configRuleName = configNode.attribute('name')
                def rule = allRules.find { it.name == configRuleName }
                assert rule, "Rule named [$configRuleName] referenced within <rule-config> was not found"
                configNode[r.property].each { p ->
                    def name = p.attribute('name')
                    def value = p.attribute('value')
                    PropertyUtil.setPropertyFromString(rule, name, value)
                }
            }
            rules.addAll(filteredRuleSet.rules)
        }
    }

    private void loadRuleElements(ruleset) {
        ruleset[r.rule].each { ruleNode ->
            def ruleClassName = ruleNode.attribute('class')
            def rule = Class.forName(ruleClassName.toString()).newInstance()
            rules << rule
            ruleNode[r.property].each { p ->
                def name = p.attribute('name')
                def value = p.attribute('value')
                PropertyUtil.setPropertyFromString(rule, name, value)
            }
        }
    }

    private void validateXml(String xml) {
        def factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        def schema = factory.newSchema(new StreamSource(getSchemaXmlInputStream()))
        def validator = schema.newValidator()
        validator.validate(new StreamSource(new StringReader(xml)))
    }

    private InputStream getSchemaXmlInputStream() {
        return getClass().classLoader.getResourceAsStream('ruleset-schema.xsd')
    }
}