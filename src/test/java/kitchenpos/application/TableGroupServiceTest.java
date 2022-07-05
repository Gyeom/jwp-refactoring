package kitchenpos.application;

import static kitchenpos.utils.generator.OrderTableFixtureGenerator.비어있는_주문_테이블_생성;
import static kitchenpos.utils.generator.OrderTableFixtureGenerator.비어있지_않은_주문_테이블_생성;
import static kitchenpos.utils.generator.TableGroupFixtureGenerator.테이블_그룹_생성;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import kitchenpos.application.table.TableGroupService;
import kitchenpos.domain.order.OrderRepository;
import kitchenpos.domain.table.OrderTable;
import kitchenpos.domain.table.OrderTableRepository;
import kitchenpos.domain.table.TableGroup;
import kitchenpos.domain.table.TableGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service:TableGroup")
class TableGroupServiceTest {

    @Mock
    private OrderTableRepository orderTableRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TableGroupRepository tableGroupRepository;

    @InjectMocks
    private TableGroupService tableGroupService;

    private OrderTable firstOrderTable, secondOrderTable;
    private List<OrderTable> orderTables;

    @BeforeEach
    void setUp() {
        firstOrderTable = 비어있는_주문_테이블_생성();
        secondOrderTable = 비어있는_주문_테이블_생성();
        orderTables = Arrays.asList(firstOrderTable, secondOrderTable);
    }

    @Test
    @DisplayName("테이블 그룹을 생성한다.")
    public void creatTableGroup() {
        given(orderTableRepository.findAllByIdIn(anyList())).willReturn(orderTables);
        given(tableGroupRepository.save(any(TableGroup.class))).will(AdditionalAnswers.returnsFirstArg());

        // When
        tableGroupService.create(테이블_그룹_생성(firstOrderTable, secondOrderTable));

        // Then
        verify(orderTableRepository).findAllByIdIn(anyList());
        verify(tableGroupRepository).save(any(TableGroup.class));
    }

    @ParameterizedTest(name = "case[{index}] : {0} => {1}")
    @MethodSource
    @DisplayName("그룹핑할 주문 테이블이 2개 미만인 경우 예외 발생 검증")
    public void throwException_WhenOrderTableSizeIsLessThanMinimumGroupingTargetSize(
        final List<OrderTable> givenOrderTables,
        final String givenDescription
    ) {
        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> tableGroupService.create(테이블_그룹_생성(firstOrderTable, secondOrderTable)));
    }

    private static Stream<Arguments> throwException_WhenOrderTableSizeIsLessThanMinimumGroupingTargetSize() {
        return Stream.of(
            Arguments.of(Collections.emptyList(), "테이블 그룹이 0개인 경우"),
            Arguments.of(Collections.singletonList(비어있는_주문_테이블_생성()), "테이블 그룹이 1개인 경우")
        );
    }

    @Test
    @DisplayName("존재하지 않는 주문 테이블이 포함된 경우 예외 발생 검증")
    public void throwException_WhenOrderTableIsNotExist() {
        // Given
        given(orderTableRepository.findAllByIdIn(anyList())).willReturn(Collections.emptyList());

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> tableGroupService.create(테이블_그룹_생성(firstOrderTable, secondOrderTable)));

        verify(orderTableRepository).findAllByIdIn(anyList());
    }

    @Test
    @DisplayName("비어있는 주문 테이블이 포함된 경우 예외 발생 검증")
    public void throwException_WhenContainsIsNotEmptyOrderTable() {
        // Given
        List<OrderTable> givenContainsNotEmptyOrderTables = Arrays
            .asList(비어있지_않은_주문_테이블_생성(), 비어있는_주문_테이블_생성());
        given(orderTableRepository.findAllByIdIn(anyList())).willReturn(givenContainsNotEmptyOrderTables);

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> tableGroupService.create(테이블_그룹_생성(firstOrderTable, secondOrderTable)));

        verify(orderTableRepository).findAllByIdIn(anyList());
    }

    @Test
    @DisplayName("이미 그룹핑 된 주문 테이블이 포함된 경우 예외 발생 검증")
    public void throwException_ContainsAlreadyHasTableGroupOrderTable() {
        // Given
        OrderTable givenAlreadyHasTableGroupOrderTable = 비어있는_주문_테이블_생성();
        givenAlreadyHasTableGroupOrderTable.allocateTableGroup(new TableGroup());

        List<OrderTable> givenContainsAlreadyHasTableGroupOrderTables = Arrays
            .asList(givenAlreadyHasTableGroupOrderTable, 비어있는_주문_테이블_생성());
        given(orderTableRepository.findAllByIdIn(anyList())).willReturn(givenContainsAlreadyHasTableGroupOrderTables);

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> tableGroupService.create(테이블_그룹_생성(firstOrderTable, secondOrderTable)));

        verify(orderTableRepository).findAllByIdIn(anyList());
    }

    @Test
    @DisplayName("테이블 그룹을 해제한다.")
    public void ungroupTable() {
        // Given
        TableGroup tableGroup = new TableGroup(orderTables);
        given(tableGroupRepository.findById(any())).willReturn(Optional.of(tableGroup));

        // When
        tableGroupService.ungroup(tableGroup.getId());

        // Then
        verify(tableGroupRepository).findById(any());
        verify(orderRepository).existsByOrderTableIdInAndOrderStatusIn(anyList(), anyList());
    }

    @Test
    @DisplayName("조리중이거나 식사중인 주문 테이블이 포함된 경우 예외 발생 검증")
    public void throwException_WhenOrderTablesOrderHasMealStatusOrCookingStatus() {
        // Given
        TableGroup tableGroup = new TableGroup(orderTables);
        given(tableGroupRepository.findById(any())).willReturn(Optional.of(tableGroup));
        given(orderRepository.existsByOrderTableIdInAndOrderStatusIn(anyList(), anyList())).willReturn(true);

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> tableGroupService.ungroup(any()));

        verify(tableGroupRepository).findById(any());
        verify(orderRepository).existsByOrderTableIdInAndOrderStatusIn(anyList(), anyList());
    }
}
