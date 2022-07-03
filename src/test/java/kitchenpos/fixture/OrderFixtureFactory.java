package kitchenpos.fixture;

import java.util.List;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.dto.OrderLineItemDto;
import kitchenpos.order.dto.OrderRequest;

public class OrderFixtureFactory {
    private OrderFixtureFactory() {
    }

    public static OrderRequest createOrder(Long orderTableId, List<OrderLineItemDto> orderLineItemDtos) {
        return new OrderRequest(orderTableId, orderLineItemDtos);
    }

    public static OrderRequest createParamForUpdateStatus(OrderStatus status) {
        return new OrderRequest(status.name());
    }
}