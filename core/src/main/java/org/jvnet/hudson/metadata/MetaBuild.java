
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

package org.jvnet.hudson.metadata;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author martin.todorov@fredhopper.com
 */
@XStreamAlias(value = "meta-build")
public class MetaBuild
{

    @XStreamAlias(value = "hudson-config")
    private HudsonConfig hudsonConfig;

    @XStreamAlias(value = "job-configs")
    @XStreamImplicit(itemFieldName="job-config")
    private List<JobConfig> jobConfigs;

    @XStreamAlias(value = "goals")
    private Goals goals;


    public MetaBuild()
    {
    }

    public MetaBuild(List<JobConfig> jobConfigs)
    {
        this.jobConfigs = jobConfigs;
    }

    public HudsonConfig getHudsonConfig()
    {
        return hudsonConfig;
    }

    public void setHudsonConfig(HudsonConfig hudsonConfig)
    {
        this.hudsonConfig = hudsonConfig;
    }

    public List<JobConfig> getJobConfigs()
    {
        return jobConfigs;
    }

    public void setJobConfigs(List<JobConfig> jobConfigs)
    {
        this.jobConfigs = jobConfigs;
    }

    public Goals getGoals()
    {
        return goals;
    }

    public void setGoals(Goals goals)
    {
        this.goals = goals;
    }
    
}
