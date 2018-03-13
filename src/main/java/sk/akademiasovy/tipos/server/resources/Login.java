package sk.akademiasovy.tipos.server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sk.akademiasovy.tipos.server.Credentials;
import sk.akademiasovy.tipos.server.Registration;
import sk.akademiasovy.tipos.server.User;
import sk.akademiasovy.tipos.server.db.MySQL;

@Path("/auth")
public class Login {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkCredentials(Credentials credential){
        System.out.println(credential.getUsername());
        MySQL mySQL = new MySQL();
        User user=mySQL.getUser(credential.username, credential.password);
        if(user==null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else{
            String result;
            result="{\"firstname\":\""+user.getFirstname()+"\" , ";
            result+="\"lastname\":\""+user.getLastname()+"\" , ";
            result+="\"email\":\""+user.getEmail()+"\" , ";
            result+="\"login\":\""+user.getLogin()+"\" , ";
            result+="\"token\":\""+user.getToken()+"\"}";
            return Response.ok(result,MediaType.APPLICATION_JSON_TYPE).build();
        }

    }

    @GET
    @Path("/logout/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@PathParam("token")  String token){
        MySQL mySQL = new MySQL();
        mySQL.logout( token);
        return Response.ok().build();
    }

    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  createNewUser (Registration registration) {
        MySQL mySQL = new MySQL();
        boolean exist = mySQL.checkIfEmailOrLoginExist(registration.login.trim(),registration.email.trim());
        if(exist) {
            return Response.status(406).build();
        }else{
            System.out.println("go on with registration");
            mySQL.insertNewUserIntoDb(registration);
        }
        return Response.status(201).build();
    }

}