package ua.expo.model.entity;

import ua.expo.model.entity.enums.ExpositionStatus;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Exposition implements Serializable {
    private int id;
    private int price;
    private String theme;
    private String shortDescription;
    private String fullDescription;
    private Date date;
    private Date date_to;
    private ExhibitionHall hall;
    private ExpositionStatus expositionStatus = ExpositionStatus.OK;

    public Exposition() {
    }

    public Exposition(int id, int price,
                      String theme, String shortDescription,
                      String fullDescription, Date date,Date date_to, ExhibitionHall hall, ExpositionStatus expositionStatus) {
        this.id = id;
        this.price = price;
        this.theme = theme;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.date = date;
        this.date_to = date_to;
        this.hall = hall;
        this.expositionStatus = expositionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public ExhibitionHall getHall() {
        return hall;
    }

    public void setHall(ExhibitionHall hall) {
        this.hall = hall;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ExpositionStatus getExpositionStatus() {
        return expositionStatus;
    }

    public void setExpositionStatus(ExpositionStatus expositionStatus) {
        this.expositionStatus = expositionStatus;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public static class Builder{
        private int id;
        private int price;
        private String theme;
        private String shortDescription;
        private String fullDescription;
        private Date date;
        private Date date_to;
        private ExhibitionHall hall;
        private ExpositionStatus expositionStatus = ExpositionStatus.OK;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setTheme(String theme) {
            this.theme = theme;
            return this;
        }

        public Builder setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder setFullDescription(String fullDescription) {
            this.fullDescription = fullDescription;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setHall(ExhibitionHall hall) {
            this.hall = hall;
            return this;
        }

        public Builder setExpositionStatus(ExpositionStatus expositionStatus){
            this.expositionStatus = expositionStatus;
            return this;
        }


        public Builder setDateTo(Date date_to){
            this.date_to = date_to;
            return this;
        }
        public Exposition build(){
            return new Exposition(id, price, theme, shortDescription
                    , fullDescription, date, date_to, hall, expositionStatus);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exposition that = (Exposition) o;
        return id == that.id &&
                price == that.price &&
                Objects.equals(theme, that.theme) &&
                Objects.equals(shortDescription, that.shortDescription) &&
                Objects.equals(fullDescription, that.fullDescription) &&
                Objects.equals(date, that.date) &&
                Objects.equals(hall, that.hall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, theme, shortDescription, fullDescription, date, hall);
    }

    @Override
    public String toString() {
        return "Exposition{" +
                "id=" + id +
                ", price=" + price +
                ", theme='" + theme + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", date=" + date +
                ", hall=" + hall +
                '}';
    }
}
