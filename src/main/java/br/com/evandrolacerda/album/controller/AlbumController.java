package br.com.evandrolacerda.album.controller;

import br.com.evandrolacerda.album.dto.AlbumDto;
import br.com.evandrolacerda.album.entity.Album;
import br.com.evandrolacerda.album.repository.AlbumRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.JSONArray;

import javax.validation.Valid;

@Controller
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @RequestMapping("/album")
    public String index( Model model, AlbumDto albumDto ){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(
                URI.create("https://jsonplaceholder.typicode.com/albums/")
        ).build();

        String responseBody = client.sendAsync( request, HttpResponse.BodyHandlers.ofString() )
                .thenApply( HttpResponse::body )
                .join();

        Set<String> nonRepeatedAlbums = parse( responseBody );
        model.addAttribute("albuns", nonRepeatedAlbums );

        return "album";
    }

    private Set<String> parse(String responseBody ) {
        JSONArray albums = new JSONArray( responseBody );
        Set<String> albumsNames = new HashSet<>();

        for( int i = 0; i < albums.length(); i++ ){
            JSONObject album = albums.getJSONObject(i);
            albumsNames.add( album.getString("title").toUpperCase(Locale.ROOT));
        }

        return albumsNames;

    }

    @PostMapping("/album")
    public String registerAlbum(@Valid AlbumDto albumDto, BindingResult bindingResult ){

        if( bindingResult.hasErrors() ){

            return "album";

        }

        Album album = new Album();
        album.setTitle( albumDto.getTitle() );
        album.setYearRelease( albumDto.getReleaseYear() );


        albumRepository.save( album );

        System.out.println(albumDto.getReleaseYear());
        System.out.println(albumDto.getTitle());


        return "redirect:/album";
    }
}
