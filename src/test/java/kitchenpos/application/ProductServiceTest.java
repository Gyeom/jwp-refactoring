package kitchenpos.application;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kitchenpos.domain.Price;
import kitchenpos.domain.Product;
import kitchenpos.domain.ProductRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product 뿌링클치킨;
    private Product 치킨무;
    private Product 코카콜라;

    @BeforeEach
    void setUp() {
        뿌링클치킨 = Product.of("뿌링클치킨", Price.of(15_000));
        치킨무 = Product.of("치킨무", Price.of(1_000));
        코카콜라 = Product.of("코카콜라", Price.of(3_000));
    }

    @DisplayName("상품이 저장된다.")
    @Test
    void create_product() {
        // given
        when(productRepository.save(this.뿌링클치킨)).thenReturn(this.뿌링클치킨);

        // when
        Product createdProduct = productService.create(this.뿌링클치킨);

        // then
        Assertions.assertThat(createdProduct).isEqualTo(this.뿌링클치킨);
    }

    @DisplayName("상품 가격이 없으면 예외가 발생한다.")
    @Test
    void exception_craeteProduct_nullPrice() {
        // given
        this.뿌링클치킨.changePrice(null);

        // when
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> productService.create(this.뿌링클치킨));
    }

    @DisplayName("상품 가격이 0보다 작으면 예외가 발생한다.")
    @ValueSource(ints = {-1, -10})
    @ParameterizedTest(name="[{index}] 상품가격은 [{0}]")
    void exception_craeteProduct_underZeroPrice(int price) {
        // given
        this.뿌링클치킨.changePrice(Price.of(price));

        // when
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> productService.create(this.뿌링클치킨));
    }

    @DisplayName("상품이 조회된다.")
    @Test
    void search_product() {
        // given
        when(productRepository.findAll()).thenReturn(List.of(this.뿌링클치킨, this.치킨무, this.코카콜라));

        // when
        List<Product> searchedProducts = productService.list();

        // then
        Assertions.assertThat(searchedProducts).isEqualTo(List.of(this.뿌링클치킨, this.치킨무, this.코카콜라));
    }
}
