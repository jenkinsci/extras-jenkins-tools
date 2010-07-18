
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

import org.jvnet.hudson.convertors.teamcity.metadata.project.BuildType;
import org.jvnet.hudson.convertors.teamcity.metadata.project.Project;
import org.jvnet.hudson.convertors.teamcity.metadata.vcs.VCSRoot;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author martin.todorov@fredhopper.com
 */
public class TeamCityParserTest
{

    TeamCityParser parser = new TeamCityParser();


    @Test
    public void testVCSParsing()
    {
        final List<VCSRoot> roots = parser.parseVCSRoots("target/test-classes/teamcity/config/vcs-roots.xml");

        Assert.assertNotNull(roots);

        System.out.println();
        for (VCSRoot root : roots)
        {
            System.out.println(root.toString());
            System.out.println();
        }
    }

    @Test
    public void testProjectConfigParsing()
    {
        Project project = parser.parseBuildTypes("target/test-classes/teamcity/config/FAS 6.5/project-config.xml");
        Assert.assertNotNull(project);
    }

    @Test
    public void testMavenOptsParsing()
    {
        Project project = parser.parseBuildTypes("target/test-classes/teamcity/config/FAS Modules/project-config.xml");

        Assert.assertNotNull(project);

        System.out.println();
        for (BuildType buildType : project.getBuildTypes())
        {
            if (buildType.getName().equals("Business Manager"))
            {
                Assert.assertNotNull(buildType.getMavenOpts());
                System.out.println("MAVEN_OPTS=" +buildType.getMavenOpts());
                System.out.println();
            }
        }

    }

    @Test
    public void testRunnerArgsParsing()
    {
        Project project = parser.parseBuildTypes("target/test-classes/teamcity/config/FAS Modules/project-config.xml");

        Assert.assertNotNull(project);

        System.out.println();
        for (BuildType buildType : project.getBuildTypes())
        {
            if (buildType.getName().equals("Business Manager"))
            {
                Assert.assertNotNull(buildType.getMavenArgs());
                System.out.println("Maven command-line: " +buildType.getMavenArgs());
                System.out.println();
            }
        }

    }

}
