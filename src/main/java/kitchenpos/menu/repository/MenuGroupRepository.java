package kitchenpos.menu.repository;

import kitchenpos.menu.domain.MenuGroupV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroupV2, Long> {
}
