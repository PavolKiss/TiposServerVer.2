package sk.akademiasovy.tipos.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    @JsonProperty("bet1")
    public int bet1;
    @JsonProperty("bet2")
    public int bet2;
    @JsonProperty("bet3")
    public int bet3;
    @JsonProperty("bet4")
    public int bet4;
    @JsonProperty("bet5")
    public int bet5;
    @JsonProperty("token")
    public String token;
    @JsonProperty("login")
    public String login;

    public Ticket(int bet1, int bet2, int bet3, int bet4, int bet5) {
        this.bet1 = bet1;
        this.bet2 = bet2;
        this.bet3 = bet3;
        this.bet4 = bet4;
        this.bet5 = bet5;
    }
}