package model;

import java.io.Serializable;

public class Addressee implements Serializable {
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

    @Override
    public String toString() {
        return "Addressee{" +
                "idAddressee=" + idAddressee +
                ", name='" + name + '\'' +
                '}';
    }
}
