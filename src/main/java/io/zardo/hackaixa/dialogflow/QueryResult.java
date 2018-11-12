package io.zardo.hackaixa.dialogflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "queryText",
        "action",
        "parameters",
        "allRequiredParamsPresent",
        "fulfillmentMessages",
        "intent",
        "intentDetectionConfidence",
        "languageCode"
})
public class QueryResult {

    @JsonProperty("queryText")
    private String queryText;

    @JsonProperty("action")
    private String action;

    @JsonProperty("parameters")
    private Parameters parameters;

    @JsonProperty("allRequiredParamsPresent")
    private Boolean allRequiredParamsPresent;

    @JsonProperty("fulfillmentMessages")
    private List<FulfillmentMessage> fulfillmentMessages = null;

    @JsonProperty("intent")
    private Intent intent;

    @JsonProperty("intentDetectionConfidence")
    private Double intentDetectionConfidence;

    @JsonProperty("languageCode")
    private String languageCode;

    @JsonProperty("queryText")
    public String getQueryText() {
        return queryText;
    }

    @JsonProperty("queryText")
    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

    @JsonProperty("parameters")
    public Parameters getParameters() {
        return parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    @JsonProperty("allRequiredParamsPresent")
    public Boolean getAllRequiredParamsPresent() {
        return allRequiredParamsPresent;
    }

    @JsonProperty("allRequiredParamsPresent")
    public void setAllRequiredParamsPresent(Boolean allRequiredParamsPresent) {
        this.allRequiredParamsPresent = allRequiredParamsPresent;
    }

    @JsonProperty("fulfillmentMessages")
    public List<FulfillmentMessage> getFulfillmentMessages() {
        return fulfillmentMessages;
    }

    @JsonProperty("fulfillmentMessages")
    public void setFulfillmentMessages(List<FulfillmentMessage> fulfillmentMessages) {
        this.fulfillmentMessages = fulfillmentMessages;
    }

    @JsonProperty("intent")
    public Intent getIntent() {
        return intent;
    }

    @JsonProperty("intent")
    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @JsonProperty("intentDetectionConfidence")
    public Double getIntentDetectionConfidence() {
        return intentDetectionConfidence;
    }

    @JsonProperty("intentDetectionConfidence")
    public void setIntentDetectionConfidence(Double intentDetectionConfidence) {
        this.intentDetectionConfidence = intentDetectionConfidence;
    }

    @JsonProperty("languageCode")
    public String getLanguageCode() {
        return languageCode;
    }

    @JsonProperty("languageCode")
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

}
