
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

import com.thoughtworks.xstream.XStream;
import org.jvnet.hudson.metadata.Goals;
import org.jvnet.hudson.metadata.JobConfig;
import org.jvnet.hudson.metadata.MetaBuild;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author martin.todorov@fredhopper.com
 */
public class MetaBuildGenerator implements Generator
{

    public static final String METABUILD_FILE_NAME = "metabuild.xml";

    private MetaBuild metaBuild;

    private String outputDirectory;

    private Logger log = new ConsoleLogger(ConsoleLogger.LEVEL_INFO, "console");


    public MetaBuildGenerator()
    {
    }

    public MetaBuildGenerator(MetaBuild metaBuild)
    {
        this.metaBuild = metaBuild;
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
        XStream xstream = new XStream(); // require XPP3 library
        xstream.alias("metabuild", MetaBuild.class);
        xstream.alias("jobConfig", JobConfig.class);
        xstream.alias("goals", Goals.class);

        // Serialize
        String xml = xstream.toXML(metaBuild);

        BufferedWriter bufferedWriter = null;
        try
        {
            // TODO: There's clearly room for this to be improved:
            File file = new File(new File(outputDirectory).getParentFile().getAbsolutePath());
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
            file = new File(file.getAbsolutePath() + File.separatorChar +METABUILD_FILE_NAME);

            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(xml);
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

        System.out.println(xml);

        return xml;
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

    public Logger getLog()
    {
        return log;
    }

    public void setLog(Logger log)
    {
        this.log = log;
    }

}
