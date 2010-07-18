
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

import org.jvnet.hudson.metadata.HudsonConfig;
import org.jvnet.hudson.constants.PathConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author martin.todorov@fredhopper.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/core-context.xml"})
public class HudsonConfigGeneratorTest
{

    @Autowired
    @Qualifier("hudsonConfigGenerator")
    HudsonConfigGenerator hudsonConfigGenerator;


    @Test
    public void testJobGeneration()
    {
        String jdkHome = "/java/jdk1.6.0_19";
        String mavenHome = "/java/apache/maven-2.2.1";
        String antHome = "/java/apache/ant-1.8.0";
        String hudsonHome = "/java/hudson";
        String jettyHome = "/java/jetty";

        HudsonConfig hudsonConfig = new HudsonConfig();
        hudsonConfig.setJdkHome(jdkHome);
        hudsonConfig.setMavenHome(mavenHome);
        hudsonConfig.setAntHome(antHome);
        hudsonConfig.setJettyHome(jettyHome);
        hudsonConfig.setHudsonHome(hudsonHome);

        hudsonConfigGenerator.setHudsonConfig(hudsonConfig);
        hudsonConfigGenerator.setOutputDirectory(PathConstants.DIR_HUDSON);

        String configBody = hudsonConfigGenerator.generate();

        // Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(jdkHome) != -1);
        // Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(mavenHome) != -1);
        // Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(antHome) != -1);
        // Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(hudsonHome) != -1);
        // Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(jettyHome) != -1);
    }

}
