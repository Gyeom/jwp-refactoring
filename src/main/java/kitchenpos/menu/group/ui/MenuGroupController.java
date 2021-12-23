package kitchenpos.menu.group.ui;

import kitchenpos.menu.group.application.MenuGroupService;
import kitchenpos.menu.group.domain.MenuGroup;
import kitchenpos.menu.group.dto.MenuGroupRequest;
import kitchenpos.menu.group.dto.MenuGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/menu-groups")
public class MenuGroupController {
    private final MenuGroupService menuGroupService;

    public MenuGroupController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroupResponse> create(@RequestBody final MenuGroupRequest menuGroupRequest) {
        final MenuGroupResponse created = menuGroupService.create(menuGroupRequest);
        final URI uri = URI.create("/api/menu-groups/" + created.getId());
        return ResponseEntity.created(uri)
                .body(created)
                ;
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> list() {
        return ResponseEntity.ok()
                .body(menuGroupService.list())
                ;
    }
}
