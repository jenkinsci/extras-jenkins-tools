
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

package org.jvnet.hudson.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jvnet.hudson.convertors.teamcity.TeamCityToHudsonConverter;
import org.jvnet.hudson.convertors.teamcity.metadata.project.BuildType;
import org.jvnet.hudson.convertors.teamcity.metadata.project.Project;
import org.jvnet.hudson.convertors.teamcity.metadata.vcs.VCSRoot;
import org.jvnet.hudson.generators.JobConfigGenerator;
import org.jvnet.hudson.generators.MetaBuildGenerator;
import org.jvnet.hudson.metadata.JobConfig;
import org.jvnet.hudson.metadata.MetaBuild;
import org.jvnet.hudson.parsers.TeamCityParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author          martin.todorov@fredhopper.com
 * @goal            convert
 * @requiresProject false
 */
public class TeamCityToHudsonConverterMojo extends AbstractMojo
{

    public static final String METABUILD_FILE_NAME = "metabuild.xml";

    /**
     * @parameter expression="${tc.basedir}" default-value="${basedir}"
     * @required
     */
    private String baseDir;

    /**
     * @parameter expression="${hudson.output.directory}" default-value="target/hudson/jobs"
     * @required
     */
    private String outputDirectory;

    /**
     * @parameter expression="${tc2hudson.scm.exclusions}"
     */
    private String excludeSCMPatterns;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        TeamCityParser parser = new TeamCityParser();

        final List<VCSRoot> roots = parser.parseVCSRoots(baseDir + "/vcs-roots.xml");

        TeamCityToHudsonConverter converter = new TeamCityToHudsonConverter();
        converter.setVcsRoots(roots);
        converter.setBaseDir(baseDir);

        List<JobConfig> jobConfigs = new ArrayList<JobConfig>();

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/core-context.xml");
        JobConfigGenerator generator = (JobConfigGenerator) appContext.getBean("jobConfigGenerator");

        int i = 1;
        String[] projectFiles = converter.findProjects();
        for (String projectFile : projectFiles)
        {
            final Project project = parser.parseBuildTypes(baseDir + File.separatorChar + projectFile);

            if (project.getBuildTypes() != null)
            {
                for (BuildType buildType : project.getBuildTypes())
                {
                    getLog().info("Parsing modules [" + i + "/" + projectFiles.length + "] defined in TeamCity '" +
                                  new File(baseDir + File.separatorChar + projectFile).getAbsolutePath() +
                                  "' project file...");

                    final JobConfig jobConfig = converter.convertProject(buildType);

                    if (jobConfig != null && jobConfig.getBuilderType() != 0 &&
                        jobConfig.getScmURL() != null && jobConfig.getScmURL().indexOf("null") == -1 &&
                        !matchesSCMExclusion(jobConfig))
                    {
                        // TODO: Add the job config to a final list of migrated modules

                        getLog().info(jobConfig.toString());

                        getLog().info(" + Generating config file for project '" + jobConfig.getProjectName() + "':");
                        getLog().info("   - Subversion URL: " + jobConfig.getScmURL());
                        getLog().info("   - Maven goals:    " + jobConfig.getMavenGoals());
                        getLog().info("");

                        File dir = new File(outputDirectory + File.separatorChar + jobConfig.getModuleName());
                        if (!dir.exists())
                            //noinspection ResultOfMethodCallIgnored
                            dir.mkdirs();

                        generator.setJobConfig(jobConfig);
                        generator.setOutputDirectory(dir.getAbsolutePath());
                        generator.generate();

                        jobConfigs.add(jobConfig);
                    }
                    else
                    {
                        getLog().error("    Failed to migrate the project!");

                        getLog().error("    + Causes:");
                        if (jobConfig != null && jobConfig.getBuilderType() == 0)
                            getLog().error("      - Unidentified build type! The module doesn't define Maven goals or Ant tasks.");

                        if (jobConfig != null && jobConfig.getScmURL() != null)
                            getLog().error("      - The project has no source control management settings.");

                        if (jobConfig != null && jobConfig.getScmURL() != null && jobConfig.getScmURL().indexOf("null") != -1)
                            getLog().error("      - Failed to properly parse the project's source control management settings (" + jobConfig.getScmURL() + ")");
                    }
                }
            }
            else
            {
                getLog().info("Skipping project " + projectFile + " ['" + project.getId() + " / " + project.getDescription() + "'] due to missing build type definitions!");
            }

            i++;
        }

        MetaBuildGenerator metaBuildGenerator = new MetaBuildGenerator(new MetaBuild(jobConfigs));
        metaBuildGenerator.setOutputDirectory(outputDirectory);
        metaBuildGenerator.generate();
    }

    public boolean matchesSCMExclusion(JobConfig jobConfig)
    {
        if (excludeSCMPatterns != null)
        {
            String[] exclusions = excludeSCMPatterns.split(",");

            for (String exclusion : exclusions)
            {
                if (jobConfig.getScmURL().indexOf(exclusion) != -1)
                    return true;
            }
        }

        return false;
    }

    public String getBaseDir()
    {
        return baseDir;
    }

    public void setBaseDir(String baseDir)
    {
        this.baseDir = baseDir;
    }

    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }

    public String getExcludeSCMPatterns()
    {
        return excludeSCMPatterns;
    }

    public void setExcludeSCMPatterns(String excludeSCMPatterns)
    {
        this.excludeSCMPatterns = excludeSCMPatterns;
    }

}
