
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
import org.jvnet.hudson.convertors.teamcity.metadata.project.Dependencies;
import org.jvnet.hudson.convertors.teamcity.metadata.project.Project;
import org.jvnet.hudson.convertors.teamcity.metadata.project.VCSEntryRef;
import org.jvnet.hudson.convertors.teamcity.metadata.vcs.Scope;
import org.jvnet.hudson.convertors.teamcity.metadata.vcs.VCSRoot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author martin.todorov@fredhopper.com
 */
public class TeamCityParser
{


    public TeamCityParser()
    {
    }

    public List<VCSRoot> parseVCSRoots(String xmlFile)
    {
        try
        {
            FileInputStream fis = new FileInputStream(xmlFile);

            XStream xstream = new XStream();
            xstream.alias("vcs-root", VCSRoot.class);
            xstream.alias("scope", Scope.class);

            // Lists
            xstream.alias("param", List.class);
            xstream.alias("vcs-roots", List.class);

            xstream.autodetectAnnotations(true);

            //noinspection unchecked
            return (List<VCSRoot>) xstream.fromXML(fis);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        // Avoid NullPointerExceptions
        return new ArrayList<VCSRoot>();
    }

    // public List<BuildType> parseBuildTypes(String xmlFile)
    public Project parseBuildTypes(String xmlFile)
    {
        try
        {
            FileInputStream fis = new FileInputStream(xmlFile);

            XStream xstream = new XStream();
            xstream.alias("project", Project.class);
            xstream.alias("vcs-entry-ref", VCSEntryRef.class);

            // Lists
            xstream.alias("build-type", List.class);

            xstream.autodetectAnnotations(true);

            //noinspection unchecked
            return (Project) xstream.fromXML(fis);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        // Avoid NullPointerExceptions
        // return new ArrayList<BuildType>();

        return null;
    }

}
