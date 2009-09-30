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

import org.codenarc.metric.abc.AbstractAbcTest

/**
 * Tests for NumberMetricResult
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class NumberMetricResultTest extends AbstractAbcTest {

    void testPassingNullIntoConstructorThrowsException() {
        shouldFailWithMessageContaining('number') { new NumberMetricResult(null) } 
    }

    void testGetValueIsSameIntegerValuePassedIntoConstructor() {
        def result = new NumberMetricResult(23)
        assert result.getValue() == 23
    }

    void testGetValueIsSameBigDecimalValuePassedIntoConstructor() {
        def result = new NumberMetricResult(0.23456)
        assert result.getValue() == 0.23456
    }

}