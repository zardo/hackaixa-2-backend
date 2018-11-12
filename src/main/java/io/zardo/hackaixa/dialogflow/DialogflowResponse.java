package io.zardo.hackaixa.dialogflow;

import java.util.ArrayList;
import java.util.List;

public class DialogflowResponse {

    private String fulfillmentText;

    private List<FulfillmentMessage> fulfillmentMessages = new ArrayList<>();

    public DialogflowResponse() {
        fulfillmentMessages.add(new FulfillmentMessage());
    }

    public String getFulfillmentText() {
        return fulfillmentText;
    }

    public void setFulfillmentText(String fulfillmentText) {
        this.fulfillmentText = fulfillmentText;
    }

    public List<FulfillmentMessage> getFulfillmentMessages() {
        return fulfillmentMessages;
    }

    public void setFulfillmentMessages(List<FulfillmentMessage> fulfillmentMessages) {
        this.fulfillmentMessages = fulfillmentMessages;
    }

    public void addResponseText(String text){
        fulfillmentMessages.get(0).getText().getText().add(text);
    }
}
