package kitchenpos.application;

import kitchenpos.dao.MenuGroupDao;
import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuGroupServiceTest {

    @Autowired
    MenuGroupDao menuGroupDao;
    
    @Autowired
    MenuGroupService menuGroupService;
    
    @DisplayName("메뉴그룹을 생성해보자")
    @Test
    public void createMenuGroup() throws Exception {
        //given
        MenuGroup menuGroup = new MenuGroup();
        String menuGroupName = "테스트메뉴그룹";
        menuGroup.setName(menuGroupName);

        //when
        MenuGroup savedMenuGroup = menuGroupService.create(menuGroup);

        //then
        assertNotNull(savedMenuGroup.getId());
        assertThat(savedMenuGroup.getName()).isEqualTo(menuGroupName);
    }

    @DisplayName("메뉴그룹의 목록을 출력해보자자")
    @Test
    public void listMenuGroup() throws Exception {
        //given
        MenuGroup menuGroup = new MenuGroup();
        String menuGroupName = "테스트메뉴그룹";
        menuGroup.setName(menuGroupName);
        MenuGroup savedMenuGroup = menuGroupDao.save(menuGroup);

        //when
        List<MenuGroup> menuGroups = menuGroupService.list();

        //then
        assertNotNull(menuGroups);
    }
}
