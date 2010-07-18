
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
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.jvnet.hudson.convertors.teamcity.metadata.Parameter;

import java.util.List;

/**
 * @author martin.todorov@fredhopper.com
 */
public class BuildTriggers
{

    @XStreamAsAttribute
    @XStreamAlias(value = "sleeping-interval-sec")
    private long sleepingInterval;

    @XStreamAsAttribute
    @XStreamAlias(value = "vcs")
    private VCS vcs;

    @XStreamImplicit(itemFieldName = "scheduler")
    private List<Parameter> schedulers;

    @XStreamImplicit(itemFieldName = "triggered-by")
    private List<Parameter> triggers;

    @XStreamAlias(value = "failed-build")
    private FailedBuild failedBuild;


    public BuildTriggers()
    {
    }

    public long getSleepingInterval()
    {
        return sleepingInterval;
    }

    public void setSleepingInterval(long sleepingInterval)
    {
        this.sleepingInterval = sleepingInterval;
    }

    public VCS getVcs()
    {
        return vcs;
    }

    public void setVcs(VCS vcs)
    {
        this.vcs = vcs;
    }

    public List<Parameter> getSchedulers()
    {
        return schedulers;
    }

    public void setSchedulers(List<Parameter> schedulers)
    {
        this.schedulers = schedulers;
    }

    public List<Parameter> getTriggers()
    {
        return triggers;
    }

    public void setTriggers(List<Parameter> triggers)
    {
        this.triggers = triggers;
    }

    public FailedBuild getFailedBuild()
    {
        return failedBuild;
    }

    public void setFailedBuild(FailedBuild failedBuild)
    {
        this.failedBuild = failedBuild;
    }

}
