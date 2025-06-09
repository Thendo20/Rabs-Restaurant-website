package com.project.rabs.restaurant.website.web;

import com.project.rabs.restaurant.website.entity.Menu;
import com.project.rabs.restaurant.website.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Tag(name = "Admin Controller", description = "Controller for admin operations")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/admin/menu")
public class AdminController {
    private final MenuService menuService;

    public AdminController(MenuService menuService) {
        this.menuService = menuService;
    }

    // Menu Management
    @PostMapping
    @Operation(summary = "Create menu Item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menu item created successfully")
    })
    public ResponseEntity<Menu> createMenuItem(@RequestBody Menu menu) {
        Menu menuItemCreated = menuService.addMenuItem(menu);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{name}")
                .buildAndExpand(menuItemCreated.getItemName())
                .toUri();
        return ResponseEntity.created(location).body(menuItemCreated);
    }

    @PutMapping("/{name}")
    @Operation(summary = "Update existing menu item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu item updated successfully")
    })
    public ResponseEntity<Menu> updateMenuItem(@RequestBody Menu menu) {
        Optional<Menu> updatedMenuItem = menuService.updateMenuItem(menu);
        return updatedMenuItem
                .map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> {
                    Menu menuItemCreated = menuService.addMenuItem(menu);
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("{name}")
                            .buildAndExpand(menuItemCreated)
                            .toUri();
                    return ResponseEntity.created(location).body(menuItemCreated);
                });
    }

    @DeleteMapping("/delete/{name}")
    @Operation(summary = "Delete menu item by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu item deleted successfully")
    })
    public ResponseEntity<Void> deleteMenuItem(@PathVariable String name) {
        menuService.deleteMenuItem(name);
        return ResponseEntity.noContent().build();
    }

    //Order Management

}
