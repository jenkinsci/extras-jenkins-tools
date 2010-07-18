
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

import java.io.Serializable;

/**
 * @author martin.todorov@fredhopper.com
 */
public class JobConfig implements Serializable
{

    public static final int BUILD_TYPE_UNKNOWN = 0;
    public static final int BUILD_TYPE_MAVEN = 1;
    public static final int BUILD_TYPE_ANT = 2;

    @XStreamAsAttribute
    private String scmURL;

    @XStreamAsAttribute
    private String projectName;

    @XStreamAsAttribute
    private String mavenGoals;

    @XStreamAsAttribute
    private String mavenOpts;

    @XStreamAsAttribute
    private String mavenArgs;

    @XStreamAsAttribute
    private String antTargets;

    @XStreamAsAttribute
    private String description;

    @XStreamAsAttribute
    private boolean allowGoalOverriding = true;


    public JobConfig()
    {
    }

    public String getScmURL()
    {
        return scmURL;
    }

    public void setScmURL(String scmURL)
    {
        this.scmURL = scmURL;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getMavenGoals()
    {
        if (System.getProperty("tc2hudson.disable.deploy") != null && mavenGoals != null)
        {
            mavenGoals = mavenGoals.replaceAll("site:deploy", " ");
            mavenGoals = mavenGoals.replaceAll("deploy", "package");
            mavenGoals = mavenGoals.replaceAll("  ", " ");
        }

        if (System.getProperty("tc2hudson.disable.site") != null && mavenGoals != null)
        {
            mavenGoals = mavenGoals.replaceAll("site:site", "");
            mavenGoals = mavenGoals.replaceAll("site:deploy", "");
            mavenGoals = mavenGoals.replaceAll("site", "");
        }

        return mavenGoals;
    }

    public void setMavenGoals(String mavenGoals)
    {
        this.mavenGoals = mavenGoals;
    }

    public String getMavenOpts()
    {
        return mavenOpts;
    }

    public void setMavenOpts(String mavenOpts)
    {
        this.mavenOpts = mavenOpts;
    }

    public String getMavenArgs()
    {
        return mavenArgs;
    }

    public void setMavenArgs(String mavenArgs)
    {
        this.mavenArgs = mavenArgs;
    }

    public int getBuilderType()
    {
        if (mavenGoals != null)
            return BUILD_TYPE_MAVEN;
        else if (antTargets != null)
            return BUILD_TYPE_ANT;
        else
            return BUILD_TYPE_UNKNOWN;
    }

    public String getAntTargets()
    {
        return antTargets;
    }

    public void setAntTargets(String antTargets)
    {
        this.antTargets = antTargets;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean allowsGoalOverriding()
    {
        return allowGoalOverriding;
    }

    public void setAllowGoalOverriding(boolean allowGoalOverriding)
    {
        this.allowGoalOverriding = allowGoalOverriding;
    }

    @Override
    public String toString()
    {
        return "projectName:     '" +projectName +"'\n" + (getModuleName() != null ?
               "    moduleName:  '" +getModuleName() +"'\n" : "") + (scmURL != null ?
               "    description: '" +description +"'\n" : "") + (scmURL != null ?
               "    scmURL:      '" +scmURL +"'\n" : "") + (mavenGoals != null ?
               "    maven goals: '" +getMavenGoals() +"'\n" : "") + (mavenOpts != null ?
               "    MAVEN_OPTS:  '" +getMavenOpts() +"'\n" : "") + (mavenArgs != null ?
               "    maven args:  '" + getMavenArgs() +"'\n" : "") + (antTargets != null ?
               "    antTargets:  '" +antTargets +"'" : "");
    }

    public String getModuleName()
    {
        if (scmURL != null)
            return scmURL.substring(scmURL.lastIndexOf("/")+1, scmURL.length()).trim();
        else
            return null;
    }

}