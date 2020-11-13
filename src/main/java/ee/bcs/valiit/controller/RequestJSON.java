package ee.bcs.valiit.controller;

import java.math.BigDecimal;

public class RequestJSON {
    public String money;

    // KUI MUL ON ENDA LISATUD KONSTRUKTOR, SIIS TULEB ALATI LISADA KA DEFAULT CONSTRUCTOR
    public RequestJSON() {
    }

    public RequestJSON(String money){
        this.money = money;
    }

}
