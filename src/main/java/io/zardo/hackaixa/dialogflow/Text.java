
package io.zardo.hackaixa.dialogflow;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text"
})
public class Text {

    @JsonProperty("text")
    private List<String> text = new ArrayList<>();

    @JsonProperty("text")
    public List<String> getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(List<String> text) {
        this.text = text;
    }

}
