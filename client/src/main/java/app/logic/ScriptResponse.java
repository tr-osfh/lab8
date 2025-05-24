package app.logic;

import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import seClasses.Dragon;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class ScriptResponse {
    public static void process(ArrayList<Response> listOfResponses, Localizer localizer, MainWindow mw){

        for (Response response : listOfResponses){
            CommandResponse type = response.getType();

            switch (type){
                case HELP:
                    DialogManager.help("HelpBtn", response.getCommandCollection().stream()
                            .map(localizer::getKeyString).collect(Collectors.joining("\n")), localizer);
                    return;
                case HEAD, FILTER_CONTAINS_NAME, FILTER_STARTS_WITH_NAME:
                    if (response.getDragons() != null){
                        mw.setFiltered(true);
                        PriorityQueue<Dragon> dragons = new PriorityQueue<>();
                        dragons.addAll(response.getDragons());
                        mw.setCollection(dragons);
                    } else {
                        DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                    }
                    return;
                case INFO:
                    var message = MessageFormat.format(
                            localizer.getKeyString("InfoReturn"),
                            response.getInfo().getType(),
                            response.getInfo().getNumberOfDragons(),
                            response.getInfo().getYourDragons(),
                            localizer.getDate(response.getInfo().getDateOfInit()));
                    DialogManager.inform("Info", message, localizer);
                    return;
                case ADD, ADD_IF_MIN, UPDATE, REMOVE_LOWER, REMOVE_BY_ID, CLEAR:
                    DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                    return;
                case SUM_OF_AGE:
                    if (response.getResponseStatus().equals(ResponseStatus.OK)) {
                        DialogManager.inform("Info", localizer.getKeyString("SumOfAgeBtn") + response.getResponse(), localizer);
                    } else if (response.getResponseStatus().equals(ResponseStatus.ERROR)) {
                        DialogManager.inform("Info", localizer.getKeyString("NoAgeData"), localizer);
                    }
                    return;
            }
        }
    }
}
