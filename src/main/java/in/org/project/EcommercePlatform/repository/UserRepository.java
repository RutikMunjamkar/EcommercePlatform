package in.org.project.EcommercePlatform.repository;

import in.org.project.EcommercePlatform.entity.User;
import in.org.project.EcommercePlatform.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String userName);

    @Query("SELECT u FROM User u JOIN u.roles r where r=:role")
    List<User> findByRole(@Param("role") RoleType role);
}
