
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

package org.jvnet.hudson.constants;

import java.io.File;

/**
 * @author martin.todorov@fredhopper.com
 */
public class PathConstants
{

    public static final String DIR_TARGET = "target";
    public static final String DIR_TARGET_TEST_CLASSES = DIR_TARGET + File.separatorChar + "test-classes";

    public static final String DIR_HUDSON = DIR_TARGET + File.separatorChar +"hudson";
    public static final String DIR_HUDSON_JOBS = DIR_HUDSON + File.separatorChar +"jobs";

    public static final String DIR_METABUILD = DIR_TARGET_TEST_CLASSES + File.separatorChar +"metabuild";

    public static final String DIR_POMS = DIR_TARGET_TEST_CLASSES + File.separatorChar + "poms";
    public static final String DIR_POMS_MOJOS = DIR_POMS + File.separatorChar +"mojos";

    public static final String POM_PLUGIN_JOBS = DIR_POMS_MOJOS + File.separatorChar +"pom-jobs.xml";
    public static final String POM_PLUGIN_HUDSON = DIR_POMS_MOJOS + File.separatorChar +"pom-hudson.xml";
    public static final String POM_PLUGIN_METABUILD = DIR_POMS_MOJOS + File.separatorChar +"pom-metabuild.xml";
    public static final String POM_PLUGIN_TEAM_CITY_TO_HUDSON = DIR_POMS_MOJOS + File.separatorChar +"pom-tc2hudson.xml";

    public static final String XML_HUDSON_JOBS = DIR_HUDSON +File.separatorChar +"jobs/jobs.xml";
    public static final String XML_HUDSON_CONFIG = DIR_HUDSON +File.separatorChar +"config.xml";
    public static final String XML_METABUILD = DIR_METABUILD +File.separatorChar +"metabuild.xml";

    public static final String TEAM_CITY_BASEDIR = DIR_TARGET_TEST_CLASSES + File.separator +"teamcity/config";
    public static final String TEAM_CITY_CONVERSION_DESTDIR = DIR_TARGET_TEST_CLASSES + File.separator +"tc2hudson/jobs";

}
