package connection;

import seClasses.Dragon;
import seClasses.Info;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 22L;

    private ArrayList<String> comandCollection;
    private ResponseStatus responseStatus;
    private String response = "";
    private Collection<Dragon> collection;
    private CommandResponse type;
    private User user;
    private Info info;
    private PriorityBlockingQueue<Dragon> dragons;


    public Response(){}

    public Response(ResponseStatus status, Info info, CommandResponse type, User user){
        this.responseStatus = status;
        this.info = info;
        this.type = type;
        this.user = user;
    }


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

    public Response(ResponseStatus status, PriorityBlockingQueue<Dragon> dragons){
        this.responseStatus = status;
        this.dragons = dragons;
    }

    public Response(ResponseStatus status, String response, PriorityBlockingQueue<Dragon> dragons, CommandResponse type){
        this.response = response;
        this.type = type;
        this.responseStatus = status;
        this.dragons = dragons;
    }

    public CommandResponse getType() {
        if (type == null){
            return CommandResponse.DEFAULT;
        }
        return type;
    }

    public PriorityBlockingQueue<Dragon> getDragons(){
        return dragons;
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

    public Collection<Dragon> getCollection() {
        return collection;
    }

    public ArrayList<String> getCommandCollection() {
        return comandCollection;
    }

    public Info getInfo() {
        return info;
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