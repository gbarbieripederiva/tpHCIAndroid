package ar.edu.itba.barsahome.api;

public class DeviceType {
    private String id;
    private String name;

    public DeviceType(String id){
        this.id=id;
    }

    public DeviceType(String id,String name) {
        this.id = id;
        this.name=name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s - %s",this.id,this.name);
    }
}
