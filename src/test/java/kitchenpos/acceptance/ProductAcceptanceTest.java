package kitchenpos.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.acceptance.ProductAcceptanceStep.*;
import static kitchenpos.domain.ProductTestFixture.createProduct;

@DisplayName("상품 관련 인수 테스트")
public class ProductAcceptanceTest extends AcceptanceTest {

    private Product 짜장면;
    private Product 단무지;

    @BeforeEach
    public void setUp() {
        super.setUp();
        짜장면 = createProduct(1L, "감자튀김", BigDecimal.valueOf(8000L));
        단무지 = createProduct(2L, "콜라", BigDecimal.valueOf(0L));
    }

    @DisplayName("상품을 생성한다.")
    @Test
    void create() {
        // when
        ExtractableResponse<Response> response = 상품_생성_요청(짜장면);

        // then
        상품_생성됨(response);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void list() {
        // given
        List<ExtractableResponse<Response>> 등록된_상품_목록 = Arrays.asList(등록된_상품(짜장면), 등록된_상품(단무지));

        // when
        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        // then
        상품_목록_응답됨(response);
        상품_목록_포함됨(response, 등록된_상품_목록);
    }
}
