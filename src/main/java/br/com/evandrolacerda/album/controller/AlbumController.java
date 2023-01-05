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

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private EntityManager entityManager;

    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @RequestMapping("/album")
    public String index(Model model, AlbumDto albumDto, HttpServletRequest request){

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Album> criteria = builder.createQuery( Album.class );

        Root<Album> root = criteria.from(Album.class);

        List<Predicate> predicates = new ArrayList<>();



        String title = request.getParameter("title");

        if( title != null ){
            predicates.add(
                    builder.like(root.get("title"), String.format("%%%s%%", title )  )
            );
        }

        System.out.println( title );
        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        List<Album> albums = entityManager.createQuery( criteria ).getResultList();


        model.addAttribute("albuns", albums );

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
