package br.com.evandrolacerda.album.repository;

import br.com.evandrolacerda.album.entity.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long > {

    List<Album> findByTitle(String title );

    Album findById( long id );


}
