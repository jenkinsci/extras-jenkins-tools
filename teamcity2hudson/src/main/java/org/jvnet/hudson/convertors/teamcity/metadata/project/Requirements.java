
/**
 * Copyright 2010 Fredhopper Research and Development
 *      http://www.fredhopper.com/
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
 *
 * This code has been developed at Fredhopper and is hereby contributed
 * to the Hudson Continuous Integration project.
 */

package org.jvnet.hudson.convertors.teamcity.metadata.project;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.jvnet.hudson.convertors.teamcity.metadata.Parameter;

import java.util.List;

/**
 * @author martin.todorov@fredhopper.com
 */
public class Requirements
{

    @XStreamImplicit(itemFieldName = "equals")
    List<Parameter> equalsConditions;

    @XStreamImplicit(itemFieldName = "does-not-equal")
    List<Parameter> doesNotEqualConditions;

    @XStreamImplicit(itemFieldName = "contains")
    List<Parameter> containsConditions;

    @XStreamImplicit(itemFieldName = "does-not-contain")
    List<Parameter> doesNotContainConditions;

    @XStreamImplicit(itemFieldName = "matches")
    List<Parameter> matchingConditions;

    @XStreamImplicit(itemFieldName = "does-not-match")
    List<Parameter> doesNotMatchConditions;


    public Requirements()
    {
    }

    public List<Parameter> getEqualsConditions()
    {
        return equalsConditions;
    }

    public void setEqualsConditions(List<Parameter> equalsConditions)
    {
        this.equalsConditions = equalsConditions;
    }

    public List<Parameter> getDoesNotEqualConditions()
    {
        return doesNotEqualConditions;
    }

    public void setDoesNotEqualConditions(List<Parameter> doesNotEqualConditions)
    {
        this.doesNotEqualConditions = doesNotEqualConditions;
    }

    public List<Parameter> getContainsConditions()
    {
        return containsConditions;
    }

    public void setContainsConditions(List<Parameter> containsConditions)
    {
        this.containsConditions = containsConditions;
    }

    public List<Parameter> getDoesNotContainConditions()
    {
        return doesNotContainConditions;
    }

    public void setDoesNotContainConditions(List<Parameter> doesNotContainConditions)
    {
        this.doesNotContainConditions = doesNotContainConditions;
    }

    public List<Parameter> getMatchingConditions()
    {
        return matchingConditions;
    }

    public void setMatchingConditions(List<Parameter> matchingConditions)
    {
        this.matchingConditions = matchingConditions;
    }

    public List<Parameter> getDoesNotMatchConditions()
    {
        return doesNotMatchConditions;
    }

    public void setDoesNotMatchConditions(List<Parameter> doesNotMatchConditions)
    {
        this.doesNotMatchConditions = doesNotMatchConditions;
    }

}
