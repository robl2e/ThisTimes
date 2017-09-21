
package com.robl2e.thistimes.data.model.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Headline {

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("kicker")
    @Expose
    private String kicker;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Headline() {
    }

    /**
     * 
     * @param kicker
     * @param main
     */
    public Headline(String main, String kicker) {
        super();
        this.main = main;
        this.kicker = kicker;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
