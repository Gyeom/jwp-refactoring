package kitchenpos.table.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.table.acceptance.code.AcceptanceTest;
import kitchenpos.table.dto.TableGroupRequest;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;

public class TableGroupAcceptanceTestMethod extends AcceptanceTest {

    private static final String TABLE_GROUP_PATH = "/api/table-groups";
    private static final String SLASH = "/";

    public static ExtractableResponse<Response> 단체_등록_요청(TableGroupRequest params) {
        return AcceptanceTest.post(TABLE_GROUP_PATH, params);
    }

    public static ExtractableResponse<Response> 단체_등록되어_있음(TableGroupRequest params) {
        return 단체_등록_요청(params);
    }

    public static ExtractableResponse<Response> 단체_해제_요청(Long id) {
        return AcceptanceTest.delete(TABLE_GROUP_PATH + SLASH + id);
    }

    public static void 단체_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(AcceptanceTest.parseURIFromLocationHeader(response)).isNotBlank();
    }

    public static void 단체_해제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}