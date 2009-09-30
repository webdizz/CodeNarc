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

import org.codenarc.metric.AggregateMetricResults
import org.codenarc.metric.MetricResult

/**
 * An AggregateMetricResults implementation for the number metric values (BigDecimal, integer, etc.).
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class NumberMetricResults implements AggregateMetricResults {
    final Map children = [:]
    private sum = 0

    void add(String name, MetricResult metricResult) {
        children[name] = metricResult
        sum += metricResult.value
    }

    int getNumberOfChildren() {
        return children.size()
    }

    /**
     * @return the sum of children's values
     */
    Object getTotalValue() {
        return sum
    }

    /**
     * @return the average of the children's values
     */
    Object getAverageValue() {
        return children.isEmpty() ? 0 : (sum / getNumberOfChildren())
    }

    String toString() {
        "NumberAggregateMetricResults[numResults=${getNumberOfChildren()}, sum=$sum]"
    }

    private average(int sum, int count) {
        return sum && count ? sum / count as Integer : 0
    }

}