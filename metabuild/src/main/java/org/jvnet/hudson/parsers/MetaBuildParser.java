
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

package org.jvnet.hudson.parsers;

import com.thoughtworks.xstream.XStream;
import org.jvnet.hudson.metadata.Goals;
import org.jvnet.hudson.metadata.JobConfig;
import org.jvnet.hudson.metadata.MetaBuild;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author martin.todorov@fredhopper.com
 */
public class MetaBuildParser
{

    public String metaBuildFile;


    public MetaBuildParser()
    {
    }

    public MetaBuildParser(String metaBuildFile)
    {
        this.metaBuildFile = metaBuildFile;
    }

    public MetaBuild parse()
    {
        return parse(metaBuildFile);
    }

    public MetaBuild parse(String xmlFile)
    {
        try
        {
            FileInputStream fis = new FileInputStream(xmlFile);

            XStream xstream = new XStream();
            xstream.alias("metabuild", MetaBuild.class);
            xstream.alias("jobConfig", JobConfig.class);
            xstream.alias("goals", Goals.class);

            return (MetaBuild) xstream.fromXML(fis);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String getMetaBuildFile()
    {
        return metaBuildFile;
    }

    public void setMetaBuildFile(String metaBuildFile)
    {
        this.metaBuildFile = metaBuildFile;
    }

}
