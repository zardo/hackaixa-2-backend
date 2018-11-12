package io.zardo.hackaixa.dialogflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "displayName"
})
public class Intent {

    @JsonProperty("name")
    private String name;

    @JsonProperty("displayName")
    private String displayName;

    private Boolean isFallback;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean isFallback() {
        return isFallback;
    }

    public void setIsFallback(Boolean fallback) {
        isFallback = fallback;
    }
}
