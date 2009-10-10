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
package org.codenarc.metric.resultsnode

import org.codenarc.test.AbstractTest
import org.codenarc.metric.NumberMetricResult

/**
 * Tests for ClassResultsNode
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class ClassResultsNodeTest extends AbstractTest {
    private static final METHODS = [a:1]
    private static final METRIC_VALUES = [33, 99]
    private classResultsNode

//    void testGetMetricThrowsExceptionForNullName() {
//        shouldFailWithMessageContaining('name') { classResultsNode.getMetric(null) }
//    }
//
//    void testGetMetricReturnsNullForNoSuchMetric() {
//        assert classResultsNode.getMetric('abc') == null
//    }

    void testGetMethodsReturnsMapPassedIntoConstructor() {
        assert classResultsNode.getMethods() == METHODS
        assert new ClassResultsNode(METRIC_VALUES, null).getMethods() == null
    }

    void testGetMetricsReturnsImmutableMap() {
        def map = classResultsNode.methods
        shouldFail(UnsupportedOperationException) { map['b'] = 23 }
    }

    void testGetMetricValuesReturnsMapPassedIntoConstructor() {
        assert classResultsNode.getMetricValues() == METRIC_VALUES
        assert new ClassResultsNode(null, METHODS).getMetricValues() == null
    }

    void testGetMetricValuesReturnsImmutableList() {
        def metricValues = classResultsNode.metricValues
        shouldFail(UnsupportedOperationException) { metricValues << 23 }
    }

//    void testGetMetricReturnsNamedMetric() {
//        def metric = "abc"
//        def metricResult = new NumberMetricResult(23)
//        classResultsNode.add(metric, metricResult)
//        assert classResultsNode.getMetric(metric) == metricResult
//    }

    void setUp() {
        super.setUp()
        classResultsNode = new ClassResultsNode(METRIC_VALUES, METHODS)
    }

}