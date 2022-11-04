package br.com.evandrolacerda.album.entity;

import javax.persistence.*;

@Entity
public class Album {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    private Long id;
    @Column
    private String title;

    @Column
    private String yearRelease;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYearRelease() {
        return yearRelease;
    }

    public void setYearRelease(String yearRelease) {
        this.yearRelease = yearRelease;
    }
}
