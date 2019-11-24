package ar.edu.itba.barsahome.api;

public class DeviceMeta {
    private String codigo;
    private String fav;

    public DeviceMeta(String codigo) {
        this.codigo = codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    @Override
    public String toString() {
        return String.format("%s - %s",String.valueOf(this.codigo),String.valueOf(this.fav));
    }
}
