
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

package org.jvnet.hudson.generators;

import org.apache.velocity.app.VelocityEngine;
import org.jvnet.hudson.metadata.JobConfig;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author martin.todorov@fredhopper.com
 */
public class JobConfigGenerator implements Generator
{

    public static final String JOB_CONFIG_TEMPLATE_MAVEN2 = "/META-INF/velocity/job/config-Maven2.xml.vm";
    public static final String JOB_CONFIG_TEMPLATE_ANT = "/META-INF/velocity/job/config-Ant.xml.vm";

    public static final String CONFIG_FILE_NAME = "config.xml";

    private VelocityEngine velocityEngine;

    private JobConfig jobConfig;

    private String outputDirectory;

    private Logger log = new ConsoleLogger(ConsoleLogger.LEVEL_INFO, "console");


    public JobConfigGenerator()
    {
    }

    /**
     * This returns a String for easier integration with the test cases.
     * Please, note that the actual writing is also handled here.
     *
     * @return
     */
    @Override
    public String generate()
    {
        String configBody = getConfigBody(jobConfig.getBuilderType());

        BufferedWriter bufferedWriter = null;
        try
        {
            File outputFile = new File(outputDirectory + File.separatorChar + CONFIG_FILE_NAME).getAbsoluteFile();

            if (outputFile.exists())
                getLog().warn("The output file '" +outputFile +"' already exists!");
            
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            bufferedWriter.write(configBody);
            bufferedWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bufferedWriter != null)
            {
                try
                {
                    bufferedWriter.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return configBody;
    }

    private String getConfigBody(int builderType) throws RuntimeException
    {
        switch (builderType)
        {
            case JobConfig.BUILD_TYPE_MAVEN:
                return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                                                                   JOB_CONFIG_TEMPLATE_MAVEN2,
                                                                   getModel(jobConfig.getBuilderType()));
            case JobConfig.BUILD_TYPE_ANT:
                return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                                                                   JOB_CONFIG_TEMPLATE_ANT,
                                                                   getModel(jobConfig.getBuilderType()));
            default:
                throw new RuntimeException("WARNING!!! Unknown build type!");
        }
    }

    private Map getModel(int builderType) throws RuntimeException
    {
        switch (builderType)
        {
            case JobConfig.BUILD_TYPE_MAVEN:
                return getMavenModel();
            case JobConfig.BUILD_TYPE_ANT:
                return getAntModel();
            default:
                throw new RuntimeException("WARNING!!! Unknown build type!");
        }
    }

    private Map getMavenModel()
    {
        HashMap<String, String> model = new LinkedHashMap<String, String>();

        model.put("scmURL", jobConfig.getScmURL());
        model.put("mavenGoals", jobConfig.getMavenGoals());
        model.put("mavenOpts", jobConfig.getMavenOpts());
        model.put("mavenArgs", jobConfig.getMavenArgs());

        return model;
    }

    private Map getAntModel()
    {
        HashMap<String, String> model = new LinkedHashMap<String, String>();

        model.put("scmURL", jobConfig.getScmURL());
        model.put("targets", jobConfig.getAntTargets());

        return model;
    }

    public JobConfig getJobConfig()
    {
        return jobConfig;
    }

    public void setJobConfig(JobConfig jobConfig)
    {
        this.jobConfig = jobConfig;
    }

    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }

    public VelocityEngine getVelocityEngine()
    {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine)
    {
        this.velocityEngine = velocityEngine;
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
