package kitchenpos.table.application;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.table.domain.Empty;
import kitchenpos.table.domain.NumberOfGuests;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static kitchenpos.common.Messages.ORDER_TABLE_STATUS_CANNOT_UPDATE;
import static kitchenpos.table.fixture.TableFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @InjectMocks
    private TableService tableService;

    @Mock
    private OrderTableRepository orderTableRepository;

    @Mock
    private OrderRepository orderRepository;

    private OrderTable 신규_주문_테이블;
    private ChangeEmptyRequest 테이블_상태_변경_요청;
    private ChangeNumberOfGuestsRequest 테이블_인원수_다섯명으로_변경_요청;

    @BeforeEach
    void setUp() {
        신규_주문_테이블 = OrderTable.of(1L, null, NumberOfGuests.of(4), Empty.of(true));

        테이블_상태_변경_요청 = ChangeEmptyRequest.of(false);
        테이블_인원수_다섯명으로_변경_요청 = ChangeNumberOfGuestsRequest.of(5);
    }

    @Test
    @DisplayName("주문 테이블 등록시 정상적으로 비어있는 테이블을 등록한다면 등록된다")
    void create() {
        // given
        OrderTableRequest 주문_테이블_등록_요청 = OrderTableRequest.of(4, true);

        when(orderTableRepository.save(any())).thenReturn(신규_주문_테이블);

        OrderTableResponse 테이블_등록_결과 = tableService.create(주문_테이블_등록_요청);

        Assertions.assertThat(테이블_등록_결과).isEqualTo(OrderTableResponse.of(신규_주문_테이블));
    }

    @Test
    @DisplayName("주문 테이블 조회를 한다면 정상적으로 조회 된다")
    void list() {
        when(orderTableRepository.findAll()).thenReturn(Arrays.asList(비어있는_주문_테이블_그룹_없음, 비어있는_주문_테이블_그룹_있음));

        List<OrderTableResponse> 테이블_조회_결과 = tableService.list();

        assertAll(
                () -> Assertions.assertThat(테이블_조회_결과).hasSize(2),
                () -> Assertions.assertThat(테이블_조회_결과).containsExactly(
                        OrderTableResponse.of(비어있는_주문_테이블_그룹_없음),
                        OrderTableResponse.of(비어있는_주문_테이블_그룹_있음)
                )
        );
    }

    @Test
    @DisplayName("테이블을 비어있지 않은 상태로 변경한다면 변경된다")
    void changeEmpty() {
        // when
        when(orderTableRepository.findById(any())).thenReturn(Optional.of(비어있는_주문_테이블_그룹_없음));
        when(orderRepository.existsByOrderTableAndOrderStatusIn(any(), any())).thenReturn(false);
        when(orderTableRepository.save(비어있는_주문_테이블_그룹_없음)).thenReturn(비어있는_주문_테이블_그룹_없음);

        ChangeEmptyResponse 테이블_상태_변경_결과 = tableService.changeEmpty(비어있는_주문_테이블_그룹_없음.getId(), 테이블_상태_변경_요청);

        // then
        Assertions.assertThat(테이블_상태_변경_결과.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("테이블을 변경시 테이블 정보가 조회되지 않은 경우 상태 변경에 실패한다")
    void changeEmpty2() {
        // when
        when(orderTableRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> tableService.changeEmpty(9999L, 테이블_상태_변경_요청));
    }

    @Test
    @DisplayName("주문 테이블 비어있는 정보로 변경시 주문 상태가 요리와 식사 상태인 경우 실패 테스트")
    void changeEmpty3() {
        when(orderTableRepository.findById(any())).thenReturn(Optional.of(비어있는_주문_테이블_그룹_없음));
        when(orderRepository.existsByOrderTableAndOrderStatusIn(any(), any())).thenReturn(true);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tableService.changeEmpty(비어있는_주문_테이블_그룹_없음.getId(), 테이블_상태_변경_요청))
                .withMessage(ORDER_TABLE_STATUS_CANNOT_UPDATE)
        ;
    }

    @Test
    @DisplayName("주문 테이블에 인원수를 여섯명에서 다섯명으로 변경하는 경우 변경 된다")
    void changeNumberOfGuests() {
        when(orderTableRepository.findById(주문_테이블_여섯이_있음.getId())).thenReturn(Optional.of(주문_테이블_여섯이_있음));
        when(orderTableRepository.save(주문_테이블_여섯이_있음)).thenReturn(주문_테이블_여섯이_있음);

        ChangeNumberOfGuestsResponse 주문_테이블_등록_결과 = tableService.changeNumberOfGuests(
                주문_테이블_여섯이_있음.getId(),
                테이블_인원수_다섯명으로_변경_요청
        );

        Assertions.assertThat(주문_테이블_등록_결과).isEqualTo(ChangeNumberOfGuestsResponse.of(주문_테이블_여섯이_있음));
    }

    @Test
    @DisplayName("주문 테이블에 인원수를 여섯명에서 -1명으로 변경하는 경우 실패 된다")
    void changeNumberOfGuests2() {
        ChangeNumberOfGuestsRequest 인원수_음수_변경_요청 = ChangeNumberOfGuestsRequest.of(-1);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tableService.changeNumberOfGuests(비어있는_주문_테이블_그룹_없음.getId(), 인원수_음수_변경_요청));
    }

    @Test
    @DisplayName("주문 테이블 조회시 정보가 조회되지 않은 경우 실패 테스트")
    void changeNumberOfGuests3() {
        when(orderTableRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> tableService.changeNumberOfGuests(비어있는_주문_테이블_그룹_없음.getId(), 테이블_인원수_다섯명으로_변경_요청));
    }
}