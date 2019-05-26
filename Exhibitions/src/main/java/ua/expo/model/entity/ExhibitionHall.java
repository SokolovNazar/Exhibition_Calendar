package ua.expo.model.entity;

import ua.expo.model.entity.enums.HallStatus;

import java.io.Serializable;
import java.util.Objects;

public class ExhibitionHall implements Serializable {
    private int id;
    private String name;
    private String information;
    private HallStatus status = HallStatus.OK;

    public ExhibitionHall(){
    }

    public ExhibitionHall(int id, String name, String information, HallStatus status){
        this.id = id;
        this.name = name;
        this.information = information;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public HallStatus getStatus() {
        return status;
    }

    public void setStatus(HallStatus status) {
        this.status = status;
    }

    public static class Builder{
        private int id;
        private String name;
        private String information;
        private HallStatus status = HallStatus.OK;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setInformation(String information) {
            this.information = information;
            return this;
        }

        public Builder setHallStatus(HallStatus status){
            this.status = status;
            return this;
        }

        public ExhibitionHall build(){
            return new ExhibitionHall(id, name, information, status);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExhibitionHall hall = (ExhibitionHall) o;
        return id == hall.id &&
                Objects.equals(name, hall.name) &&
                Objects.equals(information, hall.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, information);
    }

    @Override
    public String toString() {
        return "ExhibitionHall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                '}';
    }

}
