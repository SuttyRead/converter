package com.converter.resource;

import com.converter.model.DocumentResponse;
import com.converter.service.ParseService;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Controller;

import java.io.IOException;
@Slf4j
@Controller
@Path("/parse")
public class ParseResource {

    private final ParseService parseService;

    public ParseResource(ParseService parseService) {
        this.parseService = parseService;
    }

    @GET
    @Path("/html")
    @Produces(MediaType.APPLICATION_JSON)
    public Response parseHtml(@QueryParam(value = "path") @NotNull String path) throws IOException {
        boolean isValidUrl = UrlValidator.getInstance().isValid(path);
        if (!isValidUrl){
            log.error("Invalid url: " + path);
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "url is invalid").build();
        }

        DocumentResponse documentResponse = parseService.parseHtml(path);
        return Response.ok(documentResponse).build();
    }

}
