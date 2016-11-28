/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.blackducksoftware.integration.hub.dataservices.policystatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.blackducksoftware.integration.hub.api.HubRequestService;
import com.blackducksoftware.integration.hub.api.policy.PolicyStatusItem;
import com.blackducksoftware.integration.hub.api.policy.PolicyStatusRequestService;
import com.blackducksoftware.integration.hub.api.project.ProjectItem;
import com.blackducksoftware.integration.hub.api.project.ProjectRequestService;
import com.blackducksoftware.integration.hub.api.project.version.ProjectVersionItem;
import com.blackducksoftware.integration.hub.api.project.version.ProjectVersionRequestService;
import com.blackducksoftware.integration.hub.exception.BDRestException;
import com.blackducksoftware.integration.hub.exception.HubIntegrationException;
import com.blackducksoftware.integration.hub.exception.MissingUUIDException;
import com.blackducksoftware.integration.hub.exception.ProjectDoesNotExistException;
import com.blackducksoftware.integration.hub.exception.UnexpectedHubResponseException;
import com.blackducksoftware.integration.hub.rest.RestConnection;

public class PolicyStatusDataService extends HubRequestService {
    private final ProjectRequestService projectRequestService;

    private final ProjectVersionRequestService projectVersionRequestService;

    private final PolicyStatusRequestService policyStatusRequestService;

    public PolicyStatusDataService(final RestConnection restConnection, final ProjectRequestService projectRequestService,
            final ProjectVersionRequestService projectVersionRequestService, final PolicyStatusRequestService policyStatusRequestService) {
        super(restConnection);
        this.projectRequestService = projectRequestService;
        this.projectVersionRequestService = projectVersionRequestService;
        this.policyStatusRequestService = policyStatusRequestService;
    }

    public PolicyStatusItem getPolicyStatusForProjectAndVersion(final String projectName,
            final String projectVersionName)
            throws IOException, URISyntaxException, BDRestException, ProjectDoesNotExistException,
            HubIntegrationException, MissingUUIDException, UnexpectedHubResponseException {
        final ProjectItem projectItem = projectRequestService.getProjectByName(projectName);
        final String versionsUrl = projectItem.getLink("versions");

        final List<ProjectVersionItem> projectVersions = projectVersionRequestService.getAllProjectVersions(versionsUrl);
        final String policyStatusUrl = findPolicyStatusUrl(projectVersions, projectVersionName);

        return policyStatusRequestService.getItem(policyStatusUrl);
    }

    private String findPolicyStatusUrl(final List<ProjectVersionItem> projectVersions, final String projectVersionName)
            throws UnexpectedHubResponseException {
        for (final ProjectVersionItem version : projectVersions) {
            if (projectVersionName.equals(version.getVersionName())) {
                return version.getLink("policy-status");
            }
        }

        return null;
    }

}
