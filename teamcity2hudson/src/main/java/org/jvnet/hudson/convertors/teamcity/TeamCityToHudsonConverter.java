
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
package org.jvnet.hudson.convertors.teamcity;

import org.apache.maven.model.FileSet;
import org.jvnet.hudson.convertors.teamcity.metadata.Parameter;
import org.jvnet.hudson.convertors.teamcity.metadata.project.BuildType;
import org.jvnet.hudson.convertors.teamcity.metadata.vcs.VCSRoot;
import org.jvnet.hudson.metadata.JobConfig;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.util.DirectoryScanner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author martin.todorov@fredhopper.com
 */
public class TeamCityToHudsonConverter
{

    public static final int PROJECT_TYPE_MAVEN = 1;
    public static final int PROJECT_TYPE_ANT = 2;

    private String baseDir;

    private List<VCSRoot> vcsRoots;

    private Logger log = new ConsoleLogger(ConsoleLogger.LEVEL_INFO, "console");


    public TeamCityToHudsonConverter()
    {
    }

    public JobConfig convertProject(BuildType buildType)
    {
        if (buildType.getRunType().equals("Ant"))
        {
            return createProject(buildType, PROJECT_TYPE_ANT);
        }
        else if (buildType.getRunType().equals("Maven2"))
        {
            return createProject(buildType, PROJECT_TYPE_MAVEN);
        }
        else
        {
            getLog().warn("Failed to recognize build type for " + buildType.toString() + "! This project will not be converted!");
            return null;
        }
    }

    public JobConfig createProject(BuildType buildType, int projectType)
    {
        if (buildType.getVcsEntryRef() == null)
        {
            getLog().warn("This module [" +buildType.getName() +"] lacks a <vcs-entry-ref> tag and it is not possible to migrate it!");
            return null;
        }

        VCSRoot root = getVCSRootById(buildType.getVcsEntryRef().getRootId());

        JobConfig jobConfig = new JobConfig();

        if (projectType == PROJECT_TYPE_MAVEN)
            jobConfig.setMavenGoals(buildType.getMavenGoals());
        else if (projectType == PROJECT_TYPE_ANT)
            jobConfig.setAntTargets(buildType.getAntTargets());
        else
            ;

        jobConfig.setProjectName(buildType.getName().trim());
        jobConfig.setScmURL(normalizeSCMPath(buildType, root));
        jobConfig.setMavenOpts(buildType.getMavenOpts());
        jobConfig.setMavenArgs(buildType.getMavenArgs());

        return jobConfig;
    }

    private String normalizeSCMPath(BuildType buildType, VCSRoot root)
    {
        return (getSCMBaseURL(root, buildType) != null ? getSCMBaseURL(root, buildType).trim() : null) +
               "/" + buildType.getSCMProjectName();
    }

    private VCSRoot getVCSRootById(String rootId)
    {
        for (VCSRoot root : vcsRoots)
        {
            if (root.getId().equals(rootId))
                return root;
        }

        getLog().warn("Failed to resolve VCS root '" +rootId + "'." +
                     " The logical explanation is that this VCS root must have been" +
                     " removed somewhere along the history of the project. It will, therefore, be ignored.");

        return null;
    }

    private String getSCMBaseURL(VCSRoot vcsRoot, BuildType buildType)
    {
        String scmBaseUrl = null;

        if (vcsRoot != null && vcsRoot.getParameters() != null)
        {
            for (Parameter parameter : vcsRoot.getParameters())
            {
                if (parameter.getName().equals("url"))
                    scmBaseUrl = parameter.getValue().trim();
            }
        }

        return scmBaseUrl;
    }

    public String[] findProjects()
    {
        DirectoryScanner scanner = new DirectoryScanner();

        FileSet fileSet = new FileSet();
        fileSet.setDirectory(baseDir);

        // Include default artifact types:
        fileSet.addInclude("**/**/project-config.xml");

        File resourceDirectory = new File(fileSet.getDirectory());
        String[] finalIncludes = new String[fileSet.getIncludes().size()];

        finalIncludes = fileSet.getIncludes().toArray(finalIncludes);

        getLog().info("Includes: " + Arrays.toString(finalIncludes));

        scanner.setBasedir(resourceDirectory);
        scanner.setIncludes(finalIncludes);

        scanner.addDefaultExcludes();
        scanner.scan();

        return scanner.getIncludedFiles();
    }

    public String getBaseDir()
    {
        return baseDir;
    }

    public void setBaseDir(String baseDir)
    {
        this.baseDir = baseDir;
    }

    public List<VCSRoot> getVcsRoots()
    {
        return vcsRoots;
    }

    public void setVcsRoots(List<VCSRoot> vcsRoots)
    {
        this.vcsRoots = vcsRoots;
    }

    public Logger getLog()
    {
        return log;
    }

    public void setLog(Logger log)
    {
        this.log = log;
    }

}
