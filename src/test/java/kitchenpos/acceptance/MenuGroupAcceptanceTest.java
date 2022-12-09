package kitchenpos.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static kitchenpos.acceptance.MenuGroupAcceptanceStep.*;
import static kitchenpos.domain.MenuGroupTestFixture.createMenuGroup;

@DisplayName("메뉴 그룹 관련 인수 테스트")
public class MenuGroupAcceptanceTest extends AcceptanceTest {

    private MenuGroup 중국집_1인_메뉴_세트;

    @BeforeEach
    public void setUp() {
        super.setUp();
        중국집_1인_메뉴_세트 = createMenuGroup(1L, "중국집_1인_메뉴_세트");
    }

    @DisplayName("메뉴 그룹을 생성한다.")
    @Test
    void create() {
        // when
        ExtractableResponse<Response> response = 메뉴_그룹_생성_요청(중국집_1인_메뉴_세트);

        // then
        메뉴_그룹_생성됨(response);
    }

    @DisplayName("메뉴 그룹 목록을 조회한다.")
    @Test
    void list() {
        // given
        List<ExtractableResponse<Response>> 메뉴_그룹_목록 = Collections.singletonList(등록된_메뉴_그룹(중국집_1인_메뉴_세트));

        // when
        ExtractableResponse<Response> response = 메뉴_그룹_목록_조회_요청();

        // then
        메뉴_그룹_목록_응답됨(response);
        메뉴_그룹_목록_포함됨(response, 메뉴_그룹_목록);
    }
}
