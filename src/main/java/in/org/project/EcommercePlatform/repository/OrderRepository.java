package in.org.project.EcommercePlatform.repository;

import in.org.project.EcommercePlatform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
