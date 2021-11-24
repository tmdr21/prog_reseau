package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Addressee")
public class Addressee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAddressee;
    private String name;

    public Addressee(){}

    public Addressee(long idAddressee, String name) {
        this.idAddressee = idAddressee;
        this.name = name;
    }

    public Addressee(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public String toString() {
        return "Addressee{" +
                "idAddressee=" + idAddressee +
                ", name='" + name + '\'' +
                '}';
    }
}
