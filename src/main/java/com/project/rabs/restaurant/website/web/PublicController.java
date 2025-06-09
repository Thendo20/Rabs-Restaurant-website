package com.project.rabs.restaurant.website.web;

import com.project.rabs.restaurant.website.entity.Menu;
import com.project.rabs.restaurant.website.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Public controller", description = "Publicly available apis")
@RestController
@RequestMapping("/api/v1")
public class PublicController {
    private final MenuService menuService;

    public PublicController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/items")
    @Operation
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All menu items returned successfully")
    })
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menuItems = menuService.findAll();
        return ResponseEntity.ok().body(menuItems);
    }

    @GetMapping("/item/{id}")
    @Operation(summary = "find menu item using ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "menu item returned successfully")
    })
    public ResponseEntity<Menu> getMenuItemById(@PathVariable("id") long id) {
        Menu menu = menuService.findById(id);
        return ResponseEntity.ok().body(menu);
    }

    @GetMapping("/item/{name}")
    @Operation(summary = "find menu item using name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "menu item returned successfully")
    })
    public ResponseEntity<Menu> getMenuItemByName(@PathVariable("name") String name) {
        Menu menu = menuService.findByName(name);
        return ResponseEntity.ok().body(menu);
    }
}
