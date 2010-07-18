
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
import org.jvnet.hudson.generators.JobConfigGenerator;
import org.jvnet.hudson.metadata.JobConfig;
import org.jvnet.hudson.metadata.MetaBuild;
import org.jvnet.hudson.parsers.MetaBuildParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * @author  martin.todorov@fredhopper.com
 *
 * @goal    generate
 */
public class MetaBuildMojo extends AbstractMojo
{

    /**
     * @parameter expression="${metabuild.in}" default-value="metabuild.xml"
     * @required
     */
    private String metaBuildInputFile;

    /**
     * @parameter expression="${metabuild.out}" default-value="metabuild.xml"
     * @required
     */
    private String metaBuildOutputFile;

    /**
     * @parameter expression="${dir.output}" default-value="."
     * @required
     */
    private String outputDirectory;

    private MetaBuild metaBuild;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {

        // TODO:
        // TODO: 1) Parse the metabuild.xml

        MetaBuildParser parser = new MetaBuildParser(metaBuildInputFile);
        metaBuild = parser.parse();

        // TODO: 2) Generate job config.xml-s
        // TODO:
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/core-context.xml");
        JobConfigGenerator generator = (JobConfigGenerator) appContext.getBean("jobConfigGenerator");
        for (JobConfig jobConfig : metaBuild.getJobConfigs())
        {
            getLog().info(jobConfig.toString());

            getLog().info(" + Generating config file for project '" + jobConfig.getProjectName() + "':");
            getLog().info("   - Subversion URL: " + jobConfig.getScmURL());
            getLog().info("   - Maven goals:    " + jobConfig.getMavenGoals());
            getLog().info("");

            File dir = new File(outputDirectory + File.separatorChar + jobConfig.getModuleName());
            if (!dir.exists())
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();

            // if (jobConfig.allowsGoalOverriding() && metaBuild.getGoals().get)
            //    jobConfig.set

            generator.setJobConfig(jobConfig);
            generator.setOutputDirectory(dir.getAbsolutePath());
            generator.generate();
        }
    }

    public String getMetaBuildInputFile()
    {
        return metaBuildInputFile;
    }

    public void setMetaBuildInputFile(String metaBuildInputFile)
    {
        this.metaBuildInputFile = metaBuildInputFile;
    }

    public String getMetaBuildOutputFile()
    {
        return metaBuildOutputFile;
    }

    public void setMetaBuildOutputFile(String metaBuildOutputFile)
    {
        this.metaBuildOutputFile = metaBuildOutputFile;
    }

    public MetaBuild getMetaBuild()
    {
        return metaBuild;
    }

    public void setMetaBuild(MetaBuild metaBuild)
    {
        this.metaBuild = metaBuild;
    }

    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }

}
