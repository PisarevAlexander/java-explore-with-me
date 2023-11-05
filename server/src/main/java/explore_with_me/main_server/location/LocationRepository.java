package explore_with_me.main_server.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Location repository
 */

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Find by lat and lon location
     * @param lat the latitude
     * @param lon the longitude
     * @return the optional of location
     */

    Optional<Location> findByLatAndLon(Float lat, Float lon);
}