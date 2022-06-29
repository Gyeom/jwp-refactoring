package kitchenpos.order.dto;

import java.util.List;

public class TableGroupRequest {
    private List<Long> orderTableIds;

    private TableGroupRequest() {
    }

    public TableGroupRequest(List<Long> orderTableIds) {
        this.orderTableIds = orderTableIds;
    }

    public List<Long> getOrderTableIds() {
        return orderTableIds;
    }
}
