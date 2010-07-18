
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

import org.jvnet.hudson.metadata.HudsonConfig;
import org.jvnet.hudson.metadata.JobConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author martin.todorov@fredhopper.com
 */
public class HudsonConfigsParserTest
{

    @Test
    public void testHudsonConfigParsing()
    {
        HudsonConfigsParser parser = new HudsonConfigsParser();
        HudsonConfig hudsonConfig = parser.parseHudsonConfig("target/test-classes/hudson/config.xml");

        Assert.assertTrue(hudsonConfig != null);

        System.out.println("Hudson configuration:");
        System.out.println("   " +hudsonConfig.getJdkHome());
        System.out.println("   " +hudsonConfig.getMavenHome());
        System.out.println("   " +hudsonConfig.getAntHome());
        System.out.println("   " +hudsonConfig.getHudsonHome());
        System.out.println("   " +hudsonConfig.getJettyHome());
    }

    @Test
    public void testJobConfigParsing()
    {
        HudsonConfigsParser parser = new HudsonConfigsParser();

        final List<JobConfig> configs = parser.parseJobConfigs("target/test-classes/hudson/jobs/jobs.xml");

        Assert.assertNotNull(configs);

        System.out.println();
        for (JobConfig job : configs)
        {
            System.out.println(job.getProjectName());
            System.out.println("   " +job.getScmURL());
            System.out.println("   " +job.getMavenGoals());
            System.out.println();
        }
    }

}
