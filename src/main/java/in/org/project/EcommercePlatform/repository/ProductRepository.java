package in.org.project.EcommercePlatform.repository;

import in.org.project.EcommercePlatform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
