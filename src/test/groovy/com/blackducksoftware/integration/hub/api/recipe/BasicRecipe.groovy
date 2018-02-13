package com.blackducksoftware.integration.hub.api.recipe

import org.junit.Before

import com.blackducksoftware.integration.hub.api.generated.component.ProjectRequest
import com.blackducksoftware.integration.hub.api.generated.enumeration.ProjectVersionDistributionType
import com.blackducksoftware.integration.hub.api.generated.enumeration.ProjectVersionPhaseType
import com.blackducksoftware.integration.hub.global.HubServerConfig
import com.blackducksoftware.integration.hub.request.builder.ProjectRequestBuilder
import com.blackducksoftware.integration.hub.rest.RestConnection
import com.blackducksoftware.integration.hub.rest.RestConnectionTestHelper
import com.blackducksoftware.integration.hub.service.HubDataServicesFactory
import com.blackducksoftware.integration.log.IntLogger
import com.blackducksoftware.integration.test.TestLogger

class BasicRecipe {
    static final String PROJECT_NAME = 'My Recipe Project'
    static final String PROJECT_VERSION_NAME = '0.0.1-SNAPSHOT'
    static final RestConnectionTestHelper restConnectionTestHelper = new RestConnectionTestHelper()

    HubDataServicesFactory hubDataServicesFactory;

    @Before
    void startRecipe() {
        /*
         * the integration logger used to display log messages from our code
         * within a 3rd party integration environment
         */
        IntLogger intLogger = new TestLogger()

        /*
         * any usage of the Hub API's has to begin with a url for the hub
         * server and either:
         *   an API key
         * --or--
         *   a username and a password (this is what we're using here)
         */
        HubServerConfig hubServerConfig = restConnectionTestHelper.getHubServerConfig()

        /*
         * next, we need to create the pieces needed for the
         * HubServicesFactory, the wrapper to get/use all the Hub API's
         */
        RestConnection restConnection = hubServerConfig.createCredentialsRestConnection(intLogger)
        hubDataServicesFactory = new HubDataServicesFactory(restConnection)
    }

    ProjectRequest createProjectRequest(String projectName, String projectVersionName) {
        /*
         * the ProjectRequestBuilder is a simple wrapper around creating a
         * ProjectRequest that will also include a ProjectVersionRequest to
         * create both a project in the Hub and a version for that created
         * project - a project must have at least one version
         */
        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder()
        projectRequestBuilder.projectName = projectName
        projectRequestBuilder.description = 'A sample testing project to demonstrate hub-common capabilities.'
        projectRequestBuilder.versionName = projectVersionName
        projectRequestBuilder.phase = ProjectVersionPhaseType.DEVELOPMENT.name()
        projectRequestBuilder.distribution = ProjectVersionDistributionType.OPENSOURCE.name()

        projectRequestBuilder.build()
    }
}