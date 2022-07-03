package kitchenpos.dto.menu;

import kitchenpos.domain.menu.MenuProduct;

public class MenuProductResponse {

    private Long seq;
    private Long productId;
    private Long menuId;
    private long quantity;

    protected MenuProductResponse() {
    }

    private MenuProductResponse(Long seq, Long productId, Long menuId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static MenuProductResponse from(MenuProduct menuProduct) {
        return new MenuProductResponse(
            menuProduct.getSeq(),
            menuProduct.getProduct().getId(),
            menuProduct.getMenu().getId(),
            menuProduct.getQuantity()
        );
    }

    public Long getSeq() {
        return seq;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
