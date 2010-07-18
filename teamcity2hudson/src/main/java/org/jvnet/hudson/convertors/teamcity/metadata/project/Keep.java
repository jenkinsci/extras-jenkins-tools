
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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author martin.todorov@fredhopper.com
 */
public class Keep
{

    @XStreamAsAttribute
    @XStreamAlias(value = "cleanup-level")
    private String cleanUpLevel;

    @XStreamAsAttribute
    private long days;

    @XStreamAsAttribute
    private long builds;


    public Keep()
    {
    }

    public String getCleanUpLevel()
    {
        return cleanUpLevel;
    }

    public void setCleanUpLevel(String cleanUpLevel)
    {
        this.cleanUpLevel = cleanUpLevel;
    }

    public long getDays()
    {
        return days;
    }

    public void setDays(long days)
    {
        this.days = days;
    }

    public long getBuilds()
    {
        return builds;
    }

    public void setBuilds(long builds)
    {
        this.builds = builds;
    }

}
