package ua.expo.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {
    private int id;
    private User user;
    private Exposition exposition;
    private int count;

    public Ticket() {
    }

    public Ticket(int id, User user, Exposition exposition, int count) {
        this.id = id;
        this.user = user;
        this.exposition = exposition;
        this.count = count;
    }
    public Ticket(User user, Exposition exposition){
        this.user = user;
        this.exposition = exposition;
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exposition getExposition() {
        return exposition;
    }

    public void setExposition(Exposition exposition) {
        this.exposition = exposition;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class Builder{
        private int id;
        private int count;
        private User user;
        private Exposition exposition;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setExposition(Exposition exposition) {
            this.exposition = exposition;
            return this;
        }

        public Builder setCount(int count){
            this.count = count;
            return this;
        }

        public Ticket build(){
            return new Ticket(id, user, exposition, count);
        }
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user=" + user +
                ", exposition=" + exposition +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                count == ticket.count &&
                Objects.equals(user, ticket.user) &&
                Objects.equals(exposition, ticket.exposition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, exposition, count);
    }
}
