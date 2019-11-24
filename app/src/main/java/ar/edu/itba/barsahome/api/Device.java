package ar.edu.itba.barsahome.api;

import java.util.Objects;

public class Device {
    private String id;
    private String name;
    private Room room;
    private DeviceType type;
    private DeviceMeta meta;


    public Device(String name, DeviceType type, Room room, DeviceMeta meta) {
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

    public Device(String name, DeviceType type, DeviceMeta meta) {
        this.name = name;
        this.type = type;
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

    //State atributes

    //ac
    private String status;
    private Double temperature; //oven and refri
    private String mode;//refri
    private String verticalSwing;
    private String horizontalSwing;
    private String fanSpeed;

    //door
    private String lock;

    //oven

    private String heat;
    private String grill;
    private String convection;

    //blinds

    private Integer level;

    //refrigerator

    private Double freezerTemperature;

    //lamp

    private String color;
    private Integer brightness;

    //alarm

    ////////////////////////////////////////////////////////


    public void setLock(String lock) {
        this.lock = lock;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public void setGrill(String grill) {
        this.grill = grill;
    }

    public void setConvection(String convection) {
        this.convection = convection;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setFreezerTemperature(Double freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public String getLock() {
        return lock;
    }

    public String getHeat() {
        return heat;
    }

    public String getGrill() {
        return grill;
    }

    public String getConvection() {
        return convection;
    }

    public Integer getLevel() {
        return level;
    }

    public Double getFreezerTemperature() {
        return freezerTemperature;
    }

    public String getColor() {
        return color;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVerticalSwing(String verticalSwing) {
        this.verticalSwing = verticalSwing;
    }

    public void setHorizontalSwing(String horizontalSwing) {
        this.horizontalSwing = horizontalSwing;
    }

    public void setFanSpeed(String fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public String getStatus() {
        return status;
    }

    public String getVerticalSwing() {
        return verticalSwing;
    }

    public String getHorizontalSwing() {
        return horizontalSwing;
    }

    public String getFanSpeed() {
        return fanSpeed;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id) &&
                Objects.equals(name, device.name) &&
                Objects.equals(status, device.status) &&
                Objects.equals(temperature, device.temperature) &&
                Objects.equals(mode, device.mode) &&
                Objects.equals(verticalSwing, device.verticalSwing) &&
                Objects.equals(horizontalSwing, device.horizontalSwing) &&
                Objects.equals(fanSpeed, device.fanSpeed) &&
                Objects.equals(lock, device.lock) &&
                Objects.equals(heat, device.heat) &&
                Objects.equals(grill, device.grill) &&
                Objects.equals(convection, device.convection) &&
                Objects.equals(level, device.level) &&
                Objects.equals(freezerTemperature, device.freezerTemperature) &&
                Objects.equals(color, device.color) &&
                Objects.equals(brightness, device.brightness);
    }

}
