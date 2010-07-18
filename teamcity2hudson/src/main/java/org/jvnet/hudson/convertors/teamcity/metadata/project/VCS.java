
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
public class VCS
{

    @XStreamAsAttribute
    private boolean enabled;

    @XStreamAsAttribute
    @XStreamAlias(value = "quiet-period")
    private long quietPeriod;

    @XStreamAsAttribute
    @XStreamAlias(value = "quiet-period-mode")
    private String quietPeriodMode;

    @XStreamAsAttribute
    @XStreamAlias(value = "ignore-files")
    IgnoreFiles ignoreFiles;


    public VCS()
    {
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public long getQuietPeriod()
    {
        return quietPeriod;
    }

    public void setQuietPeriod(long quietPeriod)
    {
        this.quietPeriod = quietPeriod;
    }

    public String getQuietPeriodMode()
    {
        return quietPeriodMode;
    }

    public void setQuietPeriodMode(String quietPeriodMode)
    {
        this.quietPeriodMode = quietPeriodMode;
    }

    public IgnoreFiles getIgnoreFiles()
    {
        return ignoreFiles;
    }

    public void setIgnoreFiles(IgnoreFiles ignoreFiles)
    {
        this.ignoreFiles = ignoreFiles;
    }

}
