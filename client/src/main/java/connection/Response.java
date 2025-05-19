package connection;

import commands.CommandsList;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 22L;

    private ResponseStatus responseStatus;
    private String response = "";
    private Collection<Dragon> collection;
    private CommandResponse type;
    private User user;

    public Response(){}


    public Response(ResponseStatus status, String response, CommandResponse type, User user){
        this.type = type;
        this.responseStatus = status;
        this.response = response;
        this.user = user;
    }

    public Response(ResponseStatus status, CommandResponse type){
        this.type = type;
        this.responseStatus = status;
    }

    public Response(ResponseStatus status, String response, CommandResponse type){
        this.response = response;
        this.responseStatus = status;
        this.type = type;
    }

    public Response(ResponseStatus status, String response, Collection<Dragon> collection, CommandResponse type){
        this.response = response;
        this.responseStatus = status;
        this.collection = collection.stream().sorted(Comparator.comparing(Dragon::getName)).toList();
        this.type = type;
    }

    public CommandResponse getType() {
        return type;
    }

    public ResponseStatus getResponseStatus(){
        return responseStatus;
    }

    public String getResponse(){
        return response;
    }

    public User getUser() {
        return user;
    }

    public Collection<Dragon> getCollection(){
        return collection;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Response response1 = (Response) object;
        return
                responseStatus == response1.responseStatus &&
                        Objects.equals(response, response1.response) &&
                        Objects.equals(collection, response1.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                responseStatus,
                response,
                collection
        );
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseStatus=" + responseStatus +
                ", response='" + response + '\'' +
                ", collection=" + collection +
                '}';
    }
}
