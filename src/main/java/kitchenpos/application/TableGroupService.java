package kitchenpos.application;

import kitchenpos.domain.Order;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import kitchenpos.dto.OrderTableRequest;
import kitchenpos.dto.TableGroupRequest;
import kitchenpos.dto.TableGroupResponse;
import kitchenpos.repository.OrderRepository;
import kitchenpos.repository.OrderTableRepository;
import kitchenpos.repository.TableGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TableGroupService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(final OrderRepository orderRepository,
                             final OrderTableRepository orderTableRepository,
                             final TableGroupRepository tableGroupRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Transactional
    public TableGroupResponse create(final TableGroupRequest request) {
        List<OrderTable> orderTables = findOrderTables(request.getOrderTables());

        TableGroup tableGroup = TableGroup.of(orderTables, orderTables);

        return TableGroupResponse.from(tableGroupRepository.save(tableGroup));
    }

    private List<OrderTable> findOrderTables(final List<OrderTableRequest> orderTables) {
        List<Long> orderTableIds = mapToOrderTableIds(orderTables);

        validateOrderTableIds(orderTableIds);

        return orderTableRepository.findAllByIdIn(orderTableIds);
    }

    private void validateOrderTableIds(final List<Long> orderTableIds) {
        List<Order> orders =
                orderRepository.findAllByOrderStatusInAndOrderTableIdIn(OrderStatus.onGoingOrderStatus(), orderTableIds);

        if (!orders.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private List<Long> mapToOrderTableIds(final List<OrderTableRequest> orderTables) {
        return orderTables.stream()
                .map(OrderTableRequest::getId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        final List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(tableGroupId);

        final List<Long> orderTableIds = orderTables.stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());

        if (orderRepository.existsByOrderTableIdInAndOrderStatusIn(
                orderTableIds, Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name()))) {
            throw new IllegalArgumentException("해당 테이블에는 아직 진행중인 주문이 존재합니다.");
        }

        for (final OrderTable orderTable : orderTables) {
            orderTable.changeTableGroup(null);
            orderTableRepository.save(orderTable);
        }
    }
}
