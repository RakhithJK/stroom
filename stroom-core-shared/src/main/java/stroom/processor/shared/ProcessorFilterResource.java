/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.processor.shared;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fusesource.restygwt.client.DirectRestService;
import stroom.meta.shared.FindMetaCriteria;
import stroom.util.shared.ResourcePaths;
import stroom.util.shared.RestResource;
import stroom.util.shared.ResultPage;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(value = "processorFilter - /v1")
@Path("/processorFilter" + ResourcePaths.V1)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ProcessorFilterResource extends RestResource, DirectRestService {
    @POST
    @Path("find")
    @ApiOperation(
            value = "Finds processors and filters matching request",
            response = ResultPage.class)
    ProcessorListRowResultPage find(FetchProcessorRequest request);

    @POST
    @ApiOperation(
            value = "Creates a filter",
            response = ProcessorFilter.class)
    ProcessorFilter create(CreateProcessorFilterRequest request);

    @GET
    @Path("/{id}")
    @ApiOperation(
            value = "Gets a filter",
            response = ProcessorFilter.class)
    ProcessorFilter read(@PathParam("id") Integer id);

    @PUT
    @Path("/{id}")
    @ApiOperation(
            value = "Updates a filter",
            response = ProcessorFilter.class)
    ProcessorFilter update(@PathParam("id") Integer id, ProcessorFilter processorFilter);

    @DELETE
    @Path("/{id}")
    @ApiOperation(
            value = "Deletes a filter",
            response = ProcessorFilter.class)
    void delete(@PathParam("id") Integer id);

    @PUT
    @Path("/{id}/priority")
    @ApiOperation(value = "Sets the priority for a filter")
    void setPriority(@PathParam("id") Integer id, Integer priority);

    @PUT
    @Path("/{id}/enabled")
    @ApiOperation(value = "Sets the enabled/disabled state for a filter")
    void setEnabled(@PathParam("id") Integer id, Boolean enabled);

    @POST
    @Path("/reprocess")
    @ApiOperation(value = "Create a filter to reprocess data")
    List<ReprocessDataInfo> reprocess(FindMetaCriteria criteria);
}