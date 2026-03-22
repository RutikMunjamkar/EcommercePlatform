package in.org.project.EcommercePlatform.repository;

import in.org.project.EcommercePlatform.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Long> {

}
