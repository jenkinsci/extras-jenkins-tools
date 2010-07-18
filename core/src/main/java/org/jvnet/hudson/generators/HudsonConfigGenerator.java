
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
import org.jvnet.hudson.metadata.HudsonConfig;
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
public class HudsonConfigGenerator implements Generator
{

    public static final String HUDSON_CONFIG_TEMPLATE = "/META-INF/velocity/hudson/config.xml.vm";
    public static final String CONFIG_FILE_NAME = "config.xml";

    private VelocityEngine velocityEngine;

    private HudsonConfig hudsonConfig;

    private String outputDirectory;


    public HudsonConfigGenerator()
    {
    }

    @Override
    public String generate()
    {
        String configBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, HUDSON_CONFIG_TEMPLATE, getModel());

        BufferedWriter bufferedWriter = null;
        try
        {
            File file = new File(outputDirectory);
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
            file = new File(outputDirectory + File.separatorChar + CONFIG_FILE_NAME);

            bufferedWriter = new BufferedWriter(new FileWriter(file));
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

    private Map getModel()
    {
        HashMap<String, String> model = new LinkedHashMap<String, String>();

        model.put("jdkHome", hudsonConfig.getJdkHome());
        model.put("mavenHome", hudsonConfig.getMavenHome());
        model.put("antHome", hudsonConfig.getAntHome());
        model.put("hudsonHome", hudsonConfig.getHudsonHome());
        model.put("jettyHome", hudsonConfig.getJettyHome());
        
        return model;
    }

    public HudsonConfig getHudsonConfig()
    {
        return hudsonConfig;
    }

    public void setHudsonConfig(HudsonConfig hudsonConfig)
    {
        this.hudsonConfig = hudsonConfig;
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

}
