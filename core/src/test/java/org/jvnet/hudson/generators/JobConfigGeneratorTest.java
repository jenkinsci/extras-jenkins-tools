
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

import org.jvnet.hudson.metadata.JobConfig;
import org.junit.Assert;
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
public class JobConfigGeneratorTest
{

    public static final String DIR_TEST_CLASSES = "target/test-classes/hudson/jobs";


    @Autowired
    @Qualifier("jobConfigGenerator")
    JobConfigGenerator jobConfigGenerator;


    @Test
    public void testJobGenerationForMavenProject()
    {
        final String scmURL = "https://dev.foo.com/subversion/trunk/test-project1";
        final String mavenGoals = "clean install";

        JobConfig jobConfig = new JobConfig();
        jobConfig.setMavenGoals(mavenGoals);
        jobConfig.setProjectName("test-project1");
        jobConfig.setScmURL(scmURL);

        jobConfigGenerator.setJobConfig(jobConfig);
        jobConfigGenerator.setOutputDirectory(DIR_TEST_CLASSES);

        String configBody = jobConfigGenerator.generate();

        Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(scmURL) != -1);
        Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(mavenGoals) != -1);
    }

    @Test
    public void testJobGenerationForAntProject()
    {
        final String scmURL = "https://dev.foo.com/subversion/trunk/test-project1";
        final String targets = "clean compile";

        JobConfig jobConfig = new JobConfig();
        jobConfig.setAntTargets(targets);
        jobConfig.setProjectName("test-project1");
        jobConfig.setScmURL(scmURL);

        jobConfigGenerator.setJobConfig(jobConfig);
        jobConfigGenerator.setOutputDirectory(DIR_TEST_CLASSES);

        String configBody = jobConfigGenerator.generate();

        Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(scmURL) != -1);
        Assert.assertTrue("Failed to replace values in the Velocity template!", configBody.indexOf(targets) != -1);
    }

}
