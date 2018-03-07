package sk.akademiasovy.tipos.server;

import java.util.Date;

public class Draw_Number {
    public int bet1;
    public int bet2;
    public int bet3;
    public int bet4;
    public int bet5;
    public Date date;

    public Draw_Number(int bet1, int bet2, int bet3, int bet4, int bet5, Date date) {
        this.bet1 = bet1;
        this.bet2 = bet2;
        this.bet3 = bet3;
        this.bet4 = bet4;
        this.bet5 = bet5;
        this.date = date;
    }
}
