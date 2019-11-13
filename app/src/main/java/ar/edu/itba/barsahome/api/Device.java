package ar.edu.itba.barsahome.api;

public class Device {
    private String id;
    private String name;
    private Room room;
    private DeviceType type;
    private DeviceMeta meta;

    public Device(String name,DeviceType type,Room room, DeviceMeta meta) {
        this.name = name;
        this.room=room;
        this.type=type;
        this.meta = meta;
    }

    public Device(String id,DeviceType type, String name,Room room, DeviceMeta meta) {
        this.id = id;
        this.type=type;
        this.room=room;
        this.name = name;
        this.meta = meta;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setMeta(DeviceMeta meta) {
        this.meta = meta;
    }

    public DeviceMeta getMeta() {
        return this.meta;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Devic: %s - %s - %s - %s - %s",
                String.valueOf(this.name),
                String.valueOf(this.type),
                String.valueOf(this.room),
                String.valueOf(this.id),
                String.valueOf(this.meta));
    }
}
