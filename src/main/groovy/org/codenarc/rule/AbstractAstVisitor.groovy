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
package org.codenarc.rule

import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.SourceUnit
import org.codenarc.source.SourceCode
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.stmt.BlockStatement

/**
 * Abstract superclass for Groovy AST Visitors used with Rules
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
abstract class AbstractAstVisitor extends ClassCodeVisitorSupport implements AstVisitor {
    static final MAX_SOURCE_LINE_LENGTH = 50
    List violations = []
    Rule rule
    SourceCode sourceCode

    /**
     * Return the source line corresponding to the specified Statement
     * @param statement - the Groovy AST Statement
     */
    protected String sourceLine(ASTNode statement) {
        def line = (statement.lineNumber >= 0) ? sourceCode.lines[statement.lineNumber-1] : null

        // If the whole line fits, then include the whole line (trimmed).
        // Otherwise, start at the beginning of the Statement of interest, and remove chars in the middle.
        // TODO Handle statements that cross multiple lines?

        def source = line ? line.trim() : null
        if (source && source.size() > MAX_SOURCE_LINE_LENGTH) {
            def lengthOfFirstSegment = MAX_SOURCE_LINE_LENGTH - 12
            def startIndexOfFirstSegment = statement.columnNumber-1
            def endIndexOfFirstSegment = startIndexOfFirstSegment + lengthOfFirstSegment
            source = line[startIndexOfFirstSegment..endIndexOfFirstSegment] + '..' + source[-10..-1]
        }
        return source
    }

    /**
     * Add a new Violation to the list of violations found by this visitor
     * @param node - the Groovy AST Node
     * @param message - the message for the violation; defaults to null
     */
    protected void addViolation(ASTNode node, message=null) {
        def sourceLine = sourceLine(node)
        def lineNumber = node.lineNumber
        addViolation(sourceLine, lineNumber, message)
    }

    /**
     * Add a new Violation for the expression to the list of violations found by this visitor
     * @param node - the Groovy AST Node for the expression
     */
    protected void addViolationFromExpression(ASTNode node) {
        addViolation(node.text, node.lineNumber)
    }

    /**
     * Add a new Violation to the list of violations found by this visitor
     * @param sourceLine - the sourceLine for the violation
     * @param lineNumber - the line number of the violation
     * @param message - the message for the violation; defaults to null
     */
    protected void addViolation(String sourceLine, int lineNumber, String message=null) {
        violations.add(new Violation(rule:rule, sourceLine:sourceLine, lineNumber:lineNumber, message:message))
    }

    protected SourceUnit getSourceUnit() {
        return source
    }

    /**
     * Return true if the Statement is a block and it is empty (contains no "meaningful" statements).
     * This implementation also addresses some "weirdness" around some statement types (specifically finally)
     * where the BlockStatement answered false to isEmpty() even if it was.
     * @param statement - the Statement to check
     * @return true if the BlockStatement is empty
     */
    protected boolean isEmptyBlock(Statement statement) {
        return statement instanceof BlockStatement &&
            (statement.empty ||
            (statement.statements.size() == 1 && statement.statements[0].empty))
    }

    /**
     * Return true if the Statement is a block
     * @param statement - the Statement to check
     * @return true if the Statement is a block
     */
    protected boolean isBlock(Statement statement) {
        return statement instanceof BlockStatement
    }

}