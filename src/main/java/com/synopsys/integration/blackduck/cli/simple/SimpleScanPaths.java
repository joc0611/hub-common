/**
 * hub-common
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
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
 */
package com.synopsys.integration.blackduck.cli.simple;

public class SimpleScanPaths {
    private final String pathToJavaExecutable;
    private final String pathToOneJar;
    private final String pathToScanExecutable;

    public SimpleScanPaths(final String pathToJavaExecutable, final String pathToOneJar, final String pathToScanExecutable) {
        this.pathToJavaExecutable = pathToJavaExecutable;
        this.pathToOneJar = pathToOneJar;
        this.pathToScanExecutable = pathToScanExecutable;
    }

    public String getPathToJavaExecutable() {
        return pathToJavaExecutable;
    }

    public String getPathToOneJar() {
        return pathToOneJar;
    }

    public String getPathToScanExecutable() {
        return pathToScanExecutable;
    }

}
