package io.archetype.infrastructure.hello.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.Response

@Path("/hello")
class lcoalhosHelloController() {

    @GET
    @Produces(APPLICATION_JSON)
    @Path("")
    fun hello() :Response{
        return Response.ok(mapOf(
            "message" to "Hello World"
        )).build()
    }
}
