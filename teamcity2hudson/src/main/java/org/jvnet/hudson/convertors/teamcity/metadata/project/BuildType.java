
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
import org.apache.commons.lang.StringUtils;
import org.jvnet.hudson.convertors.teamcity.metadata.Parameter;
import org.jvnet.hudson.convertors.teamcity.metadata.Parameters;

import java.util.List;

/**
 * This is the actual project.
 *
 * @author martin.todorov@fredhopper.com
 */
public class BuildType
{

    @XStreamAsAttribute
    private String id;

    /**
     * The project's name
     */
    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String description;

    @XStreamAlias(value = "run-type")
    @XStreamAsAttribute
    private String runType;

    @XStreamAlias(value = "checkout-on-server")
    @XStreamAsAttribute
    private boolean checkoutOnServer;

    @XStreamAlias(value = "checkout-on-agent")
    @XStreamAsAttribute
    private boolean checkoutOnAgent;

    @XStreamAlias(value = "labeling-type")
    @XStreamAsAttribute
    private String labelingType;

    @XStreamAlias(value = "labeling-pattern")
    @XStreamAsAttribute
    private String labelingPattern;

    private Parameters parameters;

    private Options options;

    @XStreamAlias(value = "run-parameters")
    private RunParameters runParameters;

    /**
     * These are the version control settings.
     */
    @XStreamAlias(value = "vcs-entry-ref")
    private VCSEntryRef vcsEntryRef;

    @XStreamAsAttribute
    private Requirements requirements;

    @XStreamAlias(value = "build-triggers")
    @XStreamAsAttribute
    private BuildTriggers buildTriggers;

    @XStreamAlias(value = "artifact-publishing")
    @XStreamAsAttribute
    private ArtifactPublishing artifactPublishing;

    @XStreamAsAttribute
    private Dependencies dependencies;

    @XStreamAlias(value = "artifact-dependencies")
    @XStreamAsAttribute
    private ArtifactDependencies artifactDependencies;

    @XStreamAsAttribute
    private Keep keep;


    public BuildType()
    {
    }

    public String getMavenGoals()
    {
        String mavenGoals = null;

        if (runParameters != null && runParameters.getParameters() != null)
        {
            for (Parameter runParameter : runParameters.getParameters())
            {
                if (runParameter.getName().equals("goals"))
                    mavenGoals = runParameter.getValue();
            }
        }

        return mavenGoals;
    }

    public String getMavenOpts()
    {
        if (getParameters() != null &&
            getParameters().getParameters() != null &&
            getParameters().getParameters().size() > 0)
        {
            List<Parameter> parameters = getParameters().getParameters();

            for (Parameter parameter : parameters)
            {
                if (parameter.getName().equals("env.MAVEN_OPTS"))
                    return StringUtils.trimToEmpty(parameter.getValue());
            }
        }

        return StringUtils.trimToEmpty(null);
    }

    /**
     * This actually represents the command-line.
     *
     * @return
     */
    public String getMavenArgs()
    {
        if (getRunParameters() != null &&
            getRunParameters().getParameters() != null &&
            getRunParameters().getParameters().size() > 0)
        {
            List<Parameter> parameters = getRunParameters().getParameters();

            for (Parameter parameter : parameters)
            {
                if (parameter.getName().equals("runnerArgs"))
                    return StringUtils.trimToEmpty(parameter.getValue());
            }
        }

        return StringUtils.trimToEmpty(null);
    }

    public String getSCMProjectName()
    {
        String projectName = null;

        if (getVcsEntryRef().getCheckoutRule() != null)
        {
            projectName = getVcsEntryRef().getCheckoutRule().getRule();

            if (projectName.indexOf("=") != -1)
                projectName = projectName.substring(0, projectName.indexOf("="));
        }

        return projectName != null ? projectName.trim() : null;
    }

    public String getAntTargets()
    {
        String antTargets = null;

        for (Parameter runParameter : runParameters.getParameters())
        {
            if (runParameter.getName().equals("target"))
                antTargets = runParameter.getValue();
        }

        return antTargets;
    }
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getRunType()
    {
        return runType;
    }

    public void setRunType(String runType)
    {
        this.runType = runType;
    }

    public boolean isCheckoutOnServer()
    {
        return checkoutOnServer;
    }

    public void setCheckoutOnServer(boolean checkoutOnServer)
    {
        this.checkoutOnServer = checkoutOnServer;
    }

    public boolean isCheckoutOnAgent()
    {
        return checkoutOnAgent;
    }

    public void setCheckoutOnAgent(boolean checkoutOnAgent)
    {
        this.checkoutOnAgent = checkoutOnAgent;
    }

    public String getLabelingType()
    {
        return labelingType;
    }

    public void setLabelingType(String labelingType)
    {
        this.labelingType = labelingType;
    }

    public String getLabelingPattern()
    {
        return labelingPattern;
    }

    public void setLabelingPattern(String labelingPattern)
    {
        this.labelingPattern = labelingPattern;
    }

    public RunParameters getRunParameters()
    {
        return runParameters;
    }

    public void setRunParameters(RunParameters runParameters)
    {
        this.runParameters = runParameters;
    }

    public Parameters getParameters()
    {
        return parameters;
    }

    public void setParameters(Parameters parameters)
    {
        this.parameters = parameters;
    }

    public Options getOptions()
    {
        return options;
    }

    public void setOptions(Options options)
    {
        this.options = options;
    }

    public VCSEntryRef getVcsEntryRef()
    {
        return vcsEntryRef;
    }

    public void setVcsEntryRef(VCSEntryRef vcsEntryRef)
    {
        this.vcsEntryRef = vcsEntryRef;
    }

    public Requirements getRequirements()
    {
        return requirements;
    }

    public void setRequirements(Requirements requirements)
    {
        this.requirements = requirements;
    }

    public BuildTriggers getBuildTriggers()
    {
        return buildTriggers;
    }

    public void setBuildTriggers(BuildTriggers buildTriggers)
    {
        this.buildTriggers = buildTriggers;
    }

    public ArtifactPublishing getArtifactPublishing()
    {
        return artifactPublishing;
    }

    public void setArtifactPublishing(ArtifactPublishing artifactPublishing)
    {
        this.artifactPublishing = artifactPublishing;
    }

    public Dependencies getDependencies()
    {
        return dependencies;
    }

    public void setDependencies(Dependencies dependencies)
    {
        this.dependencies = dependencies;
    }

    public ArtifactDependencies getArtifactDependencies()
    {
        return artifactDependencies;
    }

    public void setArtifactDependencies(ArtifactDependencies artifactDependencies)
    {
        this.artifactDependencies = artifactDependencies;
    }

    public Keep getKeep()
    {
        return keep;
    }

    public void setKeep(Keep keep)
    {
        this.keep = keep;
    }

}
