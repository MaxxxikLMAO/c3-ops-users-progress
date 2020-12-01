package cz.educanet.mavenz;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("users") //nastavuje path
@Produces(MediaType.APPLICATION_JSON) //vrací JSON

public class UserManager {

    private static List<Users> names = new ArrayList<Users>();

    private boolean BoolUser(Users user) {
        for(int i = 0; i < names.size(); i++) {
            if(names.get(i).getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

}

public class UsersResource {

    private static UserManager userManager = new UserManager();

    @GET
    public Response getAll() {
        return Response.ok(names).build();
    }

    @PUT
    @Path(("/{{username}}"))
    public Object edit(Users user, String username, String newUsername) {

        Users tempUser = new Users(username, "");
        if(BoolUser(tempUser)) {
            for(int i = 0; i < names.size(); i++) {
                if(names.get(i).getUsername().equals(user.getUsername())) {
                    names.get(i).renameUser(newUsername);
                    return Response.ok(" Přejmenování uživatele proběhlo úspěšně. ").build();
                }
            }
        } else {
            return Response.status(Response.Status.valueOf(" Uživatel neexistuje. ")).build();
        }
        return Response.status(Response.Status.valueOf(" Něco se pokazilo. "));
    }

    @POST
    public Response createUser(String username, String password) {

        Users tempUser = new Users(username, password);

        if(BoolUser(tempUser)) {
            return Response.status(Response.Status.valueOf(" Tento uživatel již existuje! ")).build();
        } else {
            names.add(tempUser);
            return Response.ok(" Uživatel byl úspěšně zaregistrován! ").build();
        }
    }

    @DELETE
    public Response delete(Users user){
        if(BoolUser(user)) {
            names.remove(user);
            return Response.ok(" Uživatel byl úspěšně odstraněn. ").build();
        } else {
            return Response.status(Response.Status.valueOf(" Uživatel neexistuje. ")).build();
        }
    }


}

/*UsersResource us = new UsersResource();
us.name
UsersResource us2 = new UsersResource();
us2.name*/
// users.add("Marek");
