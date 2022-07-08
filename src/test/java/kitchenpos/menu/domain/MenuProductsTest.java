package kitchenpos.menu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("메뉴 상품 일급 컬랙션")
class MenuProductsTest {
    private static final Long 후라이드_아이디 = 1L;
    private static final MenuProduct 단일_후라이드_메뉴_상품 = MenuProduct.of(후라이드_아이디, 1L);
    private static final MenuProduct 트리플_후라이드_메뉴_상품 = MenuProduct.of(후라이드_아이디, 3L);

    public static final List<MenuProduct> 메뉴_상품_리스트 = Arrays.asList(단일_후라이드_메뉴_상품, 트리플_후라이드_메뉴_상품);

    private MenuProducts 메뉴_상품들;

    @BeforeEach
    void setUp() {
        메뉴_상품들 = new MenuProducts(메뉴_상품_리스트);
    }

    @DisplayName("메뉴 상품을 추가할 수 있다.")
    @Test
    void 메뉴_상품_추가() {
        메뉴_상품들.add(MenuProduct.of(후라이드_아이디, 2L));

        assertThat(메뉴_상품들.value()).hasSize(3);
    }
}