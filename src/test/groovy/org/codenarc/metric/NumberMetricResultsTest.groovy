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
package org.codenarc.metric

import org.codenarc.test.AbstractTest

/**
 * Tests for NumberMetricResults
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class NumberMetricResultsTest extends AbstractTest {
    private static final METRIC = [:] as Metric
    private static final BD = [0.23, 5.01, 3.67]
    private numberMetricResults

    void testAverageValueForNoChildrenIsZero() {
        assert numberMetricResults.averageValue == 0
    }

    void testTotalValueForNoChildrenIsZero() {
        assert numberMetricResults.totalValue == 0
    }

    void testNumberOfChildrenForNoChildrenIsZero() {
        assert numberMetricResults.numberOfChildren == 0
    }

    void testAverageValueForASingleMetricIsThatMetricValue() {
        numberMetricResults.add('x', new NumberMetricResult(METRIC, 99.5))
        assert numberMetricResults.averageValue == 99.5
    }

    void testTotalValueForASingleMetricIsThatMetricValue() {
        numberMetricResults.add('x', new NumberMetricResult(METRIC, 99.5))
        assert numberMetricResults.totalValue == 99.5
    }

    void testAverageValueForSeveralIntegerMetricsIsTheAverageOfTheMetricValues() {
        addThreeIntegerChildMetricResults()
        assert numberMetricResults.averageValue == 25 / 3
    }

    void testTotalValueForSeveralIntegerMetricsIsTheSumOfTheMetricValues() {
        addThreeIntegerChildMetricResults()
        assert numberMetricResults.totalValue == 25
    }


    void testTotalValueForSeveralBigDecimalMetricsIsTheSumOfTheMetricValues() {
        addThreeBigDecimalChildMetricResults()
        assert numberMetricResults.totalValue == BD[0] + BD[1] + BD[2] 
    }

    void testAverageValueForSeveralBigDecimalMetricsIsTheAverageOfTheMetricValues() {
        addThreeBigDecimalChildMetricResults()
        assert numberMetricResults.averageValue == (BD[0] + BD[1] + BD[2]) / 3 
    }

    void testCorrectNumberOfChildrenForSeveralChildResults() {
        addThreeIntegerChildMetricResults()
        assert numberMetricResults.numberOfChildren == 3
    }

    void setUp() {
        super.setUp()
        numberMetricResults = new NumberMetricResults()
    }

    private void addThreeIntegerChildMetricResults() {
        addThreeChildMetricResults(21, 1, 3)
    }

    private void addThreeBigDecimalChildMetricResults() {
        addThreeChildMetricResults(BD[0], BD[1], BD[2])
    }

    private void addThreeChildMetricResults(x, y, z) {
        numberMetricResults.add('x', new NumberMetricResult(METRIC, x))
        numberMetricResults.add('y', new NumberMetricResult(METRIC, y))
        numberMetricResults.add('z', new NumberMetricResult(METRIC, z))
    }
}