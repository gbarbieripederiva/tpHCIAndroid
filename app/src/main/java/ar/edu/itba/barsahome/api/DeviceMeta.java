package ar.edu.itba.barsahome.api;

class DeviceMeta {
    private String codigo;

    public DeviceMeta(String codigo) {
        this.codigo = codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    @Override
    public String toString() {
        return String.format("%s",String.valueOf(this.codigo));
    }
}
