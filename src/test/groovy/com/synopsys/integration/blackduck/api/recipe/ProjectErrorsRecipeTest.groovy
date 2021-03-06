package com.synopsys.integration.blackduck.api.recipe

import com.synopsys.integration.blackduck.api.generated.component.ProjectRequest
import com.synopsys.integration.blackduck.api.generated.view.ProjectView
import com.synopsys.integration.blackduck.exception.DoesNotExistException
import com.synopsys.integration.blackduck.service.ProjectService
import com.synopsys.integration.rest.exception.IntegrationRestException
import com.synopsys.integration.test.annotation.IntegrationTest
import org.junit.After
import org.junit.Test
import org.junit.experimental.categories.Category

import static org.junit.Assert.*

@Category(IntegrationTest.class)
class ProjectErrorsRecipeTest extends BasicRecipe {
    static final String PROJECT_NAME_NOT_FOUND = 'Project Name That Should Never Exist'

    private final String uniqueName = PROJECT_NAME_NOT_FOUND + System.currentTimeMillis()

    @Test
    void testTryingToFindProjectThatDoesNotExist() {
        /*
         * Let's try and find a project that doesn't exist, which should throw a DoesNotExistException
         */
        ProjectService projectService = hubServicesFactory.createProjectService()
        try {
            ProjectView projectView = projectService.getProjectByName(uniqueName)
            fail('Should have throws a DoesNotExistException')
        } catch (Exception e) {
            assertTrue(e instanceof DoesNotExistException)
            assertEquals(String.format('This Project does not exist. Project: %s', uniqueName), e.getMessage())
        }
    }

    @Test
    void testTryingToCreateAProjectThatAlreadyExists() {
        /*
         * First, create a project with a unique name
         */
        ProjectRequest projectRequest = createProjectRequest(uniqueName, PROJECT_VERSION_NAME)
        ProjectService projectService = hubServicesFactory.createProjectService()
        String projectUrl = projectService.createHubProject(projectRequest)

        /*
         * Try to create a project with the same name, which should throw an Exception
         */
        try {
            projectService.createHubProject(projectRequest)
            fail('Should have thrown an IntegrationRestException')
        } catch (Exception e) {
            assertTrue(e instanceof IntegrationRestException)
            //since the project already existed, a 412 Precondition Failed http error response should occur
            assertEquals(412, ((IntegrationRestException) e).httpStatusCode)
        }
    }

    @After
    void cleanup() {
        def projectService = hubServicesFactory.createProjectService()
        try {
            ProjectView createdProject = projectService.getProjectByName(uniqueName)
            projectService.deleteHubProject(createdProject)
        } catch (DoesNotExistException e) {
            //we may or may not have created a project, so there may not be something to delete
        }
    }
}
