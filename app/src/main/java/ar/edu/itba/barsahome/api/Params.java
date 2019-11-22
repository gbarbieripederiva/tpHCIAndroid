package ar.edu.itba.barsahome.api;

public class Params {

    private String strg;
    private Integer intg;
    private Boolean bool;
    private double doub;

    public Params(String strg, Integer intg, Boolean bool, Double doub){
        this.strg = strg;
        this.intg = intg;
        this.bool = bool;
        this.doub = doub;
    }


    public String getStrg() {
        return strg;
    }

    public Integer getIntg() {
        return intg;
    }

    public Boolean getBool() {
        return bool;
    }

    public Double getDoub() {
        return doub;
    }

    public void setStrg(String strg) {
        this.strg = strg;
    }

    public void setIntg(Integer intg) {
        this.intg = intg;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public void setDoub(Double doub) {
        this.doub = doub;
    }
}
