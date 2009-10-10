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

/**
 * A NumberMetricResult that aggregates multiple values
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class AggregateNumberMetricResult implements AggregateMetricResult {
    private sum
    private count

    AggregateNumberMetricResult(children) {
        assert children != null
        sum = children.values().inject(0) { value, child -> value + child.totalValue }
        count = children.size()
    }

    int getCount() {
        return count
    }

    Object getTotalValue() {
        return sum
    }

    Object getAverageValue() {
        return sum && count ? sum / count : 0
    }

    String toString() {
        "NumberAggregateMetricResult[total=${}getTotalValue()}, average=${getAverageValue()}]"
    }

}