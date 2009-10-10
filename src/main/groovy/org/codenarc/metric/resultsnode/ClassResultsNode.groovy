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

/**
 * Represents a set of zero or more metric results for a single class
 *
 * @author Chris Mair
 * @version $Revision: 228 $ - $Date: 2009-09-29 21:52:31 -0400 (Tue, 29 Sep 2009) $
 */
class ClassResultsNode {
    final Map methods
    final List metricValues

    ClassResultsNode(List metricValues, Map methods) {
        this.methods = methods?.asImmutable()
        this.metricValues = metricValues?.asImmutable()
    }

//    def getMetric(String name) {
//        assert name
//        return null
//    }
}