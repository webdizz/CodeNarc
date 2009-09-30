/*
* Copyright 2009 the original author or authors.
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
package org.codenarc.metric.loc

import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codenarc.metric.NumberMetricResults
import org.codenarc.source.SourceCode
import org.codenarc.util.AstUtil

/**
 * Abstract superclass for method-based metrics.
 *
 * @author Chris Mair
 * @version $Revision: 225 $ - $Date: 2009-09-23 21:54:13 -0400 (Wed, 23 Sep 2009) $
 */
abstract class AbstractMethodMetric {
    SourceCode sourceCode

    abstract def calculate(MethodNode methodNode)
    abstract def calculate(ClosureExpression closureExpression)

    def calculate(ClassNode classNode) {
        def aggregateMetricResults = createAggregateMetricResults()

        def realMethods = classNode.methods.findAll { methodNode -> !AstUtil.isFromGeneratedSourceCode(methodNode) }
        realMethods.each { methodNode ->
            def methodResults = calculate(methodNode)
            aggregateMetricResults.add(methodNode.name, methodResults)
        }

        def closureFields = classNode.fields.find { fieldNode -> !AstUtil.isFromGeneratedSourceCode(fieldNode) &&
            fieldNode.initialExpression instanceof ClosureExpression }
        closureFields.each { fieldNode ->
            def fieldResults = calculate(fieldNode.initialExpression)
            aggregateMetricResults.add(fieldNode.name, fieldResults)
        }

        return aggregateMetricResults
    }

    protected createAggregateMetricResults() {
        return new NumberMetricResults()
    }
}
