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
 * Tests for MethodResultsNode
 *
 * @author Chris Mair
 * @version $Revision: 228 $ - $Date: 2009-09-29 21:52:31 -0400 (Tue, 29 Sep 2009) $
 */
class MethodResultsNodeTest extends AbstractTest {

    private static final NAME = 'xyz'
    private static final METRIC_VALUES = [33, 99]
    private methodResultsNode

    void testConstructorThrowsExceptionForNullOrEmptyName() {
        shouldFailWithMessageContaining('methodName') { new MethodResultsNode(null, METRIC_VALUES) }
    }

    void testGetMethodNameReturnsNamePassedIntoConstructor() {
        assert methodResultsNode.methodName == NAME
    }

    void testGetMetricValuesReturnsMapPassedIntoConstructor() {
        assert methodResultsNode.getMetricValues() == METRIC_VALUES
        assert new MethodResultsNode(NAME, null).getMetricValues() == null
    }

    void testGetMetricValuesReturnsImmutableList() {
        def metricValues = methodResultsNode.metricValues
        shouldFail(UnsupportedOperationException) { metricValues << 23 }
    }

    void setUp() {
        super.setUp()
        methodResultsNode = new MethodResultsNode(NAME, METRIC_VALUES)
    }

}