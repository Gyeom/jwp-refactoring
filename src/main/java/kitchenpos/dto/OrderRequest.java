package kitchenpos.dto;

import kitchenpos.domain.OrderLineItem;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    private final Long id;
    private final Long orderTableId;
    private final String orderStatus;
    private final LocalDateTime orderedTime;
    private final List<OrderLineItem> orderLineItems;

    private OrderRequest(Long id, Long orderTableId, String orderStatus, LocalDateTime orderedTime,
                         List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
    }

    public static OrderRequest of(Long id, Long orderTableId, String orderStatus, LocalDateTime orderedTime,
                                  List<OrderLineItem> orderLineItems) {
        return new OrderRequest(id, orderTableId, orderStatus, orderedTime, orderLineItems);
    }

    public Long getId() {
        return id;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}
