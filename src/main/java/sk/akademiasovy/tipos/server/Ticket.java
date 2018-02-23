package sk.akademiasovy.tipos.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket extends sun.security.krb5.internal.Ticket {
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
}