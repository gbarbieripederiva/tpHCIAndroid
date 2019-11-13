package ar.edu.itba.barsahome.api;

public class RoomMeta {
    private String img;

    public RoomMeta(String img) {
        this.img = img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getImg());
    }
}
