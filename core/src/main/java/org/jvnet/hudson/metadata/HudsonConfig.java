
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

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author martin.todorov@fredhopper.com
 */
public class HudsonConfig
{

    @XStreamAsAttribute
    private String jdkHome;

    @XStreamAsAttribute
    private String mavenHome;

    @XStreamAsAttribute
    private String antHome;

    @XStreamAsAttribute
    private String hudsonHome;

    @XStreamAsAttribute
    private String jettyHome;

    @XStreamAsAttribute
    private Goals goals;


    public HudsonConfig()
    {
    }

    public String getJdkHome()
    {
        return jdkHome;
    }

    public void setJdkHome(String jdkHome)
    {
        this.jdkHome = jdkHome;
    }

    public String getMavenHome()
    {
        return mavenHome;
    }

    public void setMavenHome(String mavenHome)
    {
        this.mavenHome = mavenHome;
    }

    public String getAntHome()
    {
        return antHome;
    }

    public void setAntHome(String antHome)
    {
        this.antHome = antHome;
    }

    public String getHudsonHome()
    {
        return hudsonHome;
    }

    public void setHudsonHome(String hudsonHome)
    {
        this.hudsonHome = hudsonHome;
    }

    public String getJettyHome()
    {
        return jettyHome;
    }

    public void setJettyHome(String jettyHome)
    {
        this.jettyHome = jettyHome;
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
