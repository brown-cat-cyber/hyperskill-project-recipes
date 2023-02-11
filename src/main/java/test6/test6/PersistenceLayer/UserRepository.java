package test6.test6.PersistenceLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test6.test6.BusienessLayer.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
