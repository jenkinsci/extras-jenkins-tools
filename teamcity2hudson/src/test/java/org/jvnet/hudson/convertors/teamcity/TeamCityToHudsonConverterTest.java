
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

import org.jvnet.hudson.convertors.teamcity.metadata.project.BuildType;
import org.jvnet.hudson.convertors.teamcity.metadata.project.Project;
import org.jvnet.hudson.convertors.teamcity.metadata.vcs.VCSRoot;
import org.jvnet.hudson.metadata.JobConfig;
import org.jvnet.hudson.parsers.TeamCityParser;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.jvnet.hudson.constants.PathConstants.TEAM_CITY_BASEDIR;

/**
 * @author martin.todorov@fredhopper.com
 */
public class TeamCityToHudsonConverterTest
{

    @Test
    public void testProjectConfigParsing()
    {
        TeamCityParser parser = new TeamCityParser();

        final List<VCSRoot> roots = parser.parseVCSRoots(TEAM_CITY_BASEDIR + "/vcs-roots.xml");

        Assert.assertNotNull(roots);

        TeamCityToHudsonConverter converter = new TeamCityToHudsonConverter();
        converter.setVcsRoots(roots);
        converter.setBaseDir(TEAM_CITY_BASEDIR);

        int i = 1;
        String[] projectFiles = converter.findProjects();
        for (String projectFile : projectFiles)
        {
            final Project project = parser.parseBuildTypes(TEAM_CITY_BASEDIR + File.separatorChar + projectFile);

            Assert.assertNotNull(project);

            if (project.getBuildTypes() != null)
            {
                for (BuildType buildType : project.getBuildTypes())
                {
                    System.out.println("Parsing modules [" +i +"/" +projectFiles.length +"] defined in TeamCity '" +
                                       new File(TEAM_CITY_BASEDIR + File.separatorChar + projectFile).getAbsolutePath() +
                                       "' project file...");

                    final JobConfig jobConfig = converter.convertProject(buildType);

                    if (jobConfig != null)
                    {
                        // TODO: Add the job config to a final list of migrated modules

                        System.out.println(jobConfig.toString());
                        System.out.println();
                    }
                    else
                    {
                        System.out.println("    Failed to migrate the project!");
                    }
                }
            }
            else
            {
                System.out.println("Skipping project " +projectFile + " ['" +project.getId() +" / " +project.getDescription() +"'] due to missing build type definitions!");
            }

            i++;
        }
    }

}
