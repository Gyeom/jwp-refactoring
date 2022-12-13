package kitchenpos.fixture;

import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import kitchenpos.dto.OrderTableRequest;
import kitchenpos.dto.TableGroupRequest;

import java.util.Arrays;
import java.util.List;

public class TableGroupTestFixture {

    public static TableGroupRequest createTableGroupRequest(List<OrderTableRequest> orderTables) {
        return TableGroupRequest.of(null, null, orderTables);
    }

    public static TableGroup createTableGroup() {
        return TableGroup.of(1L, Arrays.asList(
                OrderTable.of(1L, null, 10, true),
                OrderTable.of(2L, null, 10, true)
        ), Arrays.asList(
                OrderTable.of(1L, null, 10, true),
                OrderTable.of(2L, null, 10, true)
        ));
    }
}
