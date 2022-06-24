package kitchenpos.helper;

import kitchenpos.menu.domain.Menu;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.dto.OrderLineItemRequest;

public class OrderLineItemFixtures {

    public static final OrderLineItemRequest 주문_항목_요청1 = 주문_항목_요청_만들기(1L, 1);
    public static final OrderLineItemRequest 주문_항목_요청2 = 주문_항목_요청_만들기(2L, 2);
    public static final OrderLineItemRequest 없는_주문_항목_요청 = 주문_항목_요청_만들기(9999999L, 2);

    public static OrderLineItemRequest 주문_항목_요청_만들기(Long orderId, Long menuId, Integer quantity){
        return new OrderLineItemRequest(orderId, menuId, quantity);
    }

    public static OrderLineItemRequest 주문_항목_요청_만들기(Long menuId, Integer quantity){
        return new OrderLineItemRequest(null, menuId, quantity);
    }

    public static OrderLineItem 주문_항목_만들기(Long seq, Order order, Menu menu, Integer quantity){
        return new OrderLineItem(seq, order, menu, quantity);
    }
    public static OrderLineItem 주문_항목_만들기(Order order, Menu menu, Integer quantity){
        return 주문_항목_만들기(null, order, menu, quantity);
    }
    public static OrderLineItem 주문_항목_만들기(Menu menu, Integer quantity){
        return 주문_항목_만들기(null, null, menu, quantity);
    }
}
