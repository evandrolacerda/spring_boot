package br.com.evandrolacerda.album.dto;

import javax.validation.constraints.NotBlank;

public class AlbumDto {

    @NotBlank(message="Campo requredido")
    private String title;

    @NotBlank(message = "Ano de Lançamento é requerido")
    private String releaseYear;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank(message = "Ano de Lançamento é requerido")
    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
}
