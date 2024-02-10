package io.archetype.infrastructure.hello.controller

import jakarta.ws.rs.*


@Produces(value = ["application/json"])
@Path("/hello")
class HelloController() {

    @GET
    @Path("/")
    fun hello() :Map<String,Any>{
        return mapOf("message" to "Hello")
    }
}
