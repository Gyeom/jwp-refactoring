package kitchenpos.menugroup.dto;

import kitchenpos.menugroup.domain.MenuGroupEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MenuGroupResponse {
  private final Long id;
  private final String name;

  public MenuGroupResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static MenuGroupResponse from(MenuGroupEntity menuGroupEntity) {
    return new MenuGroupResponse(menuGroupEntity.getId(), menuGroupEntity.getName());
  }

  public static List<MenuGroupResponse> ofList(List<MenuGroupEntity> menuGroupEntities) {
    return menuGroupEntities.stream()
          .map(MenuGroupResponse::from)
          .collect(Collectors.toList());
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MenuGroupResponse that = (MenuGroupResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
