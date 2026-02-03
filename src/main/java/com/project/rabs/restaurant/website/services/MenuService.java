package com.project.rabs.restaurant.website.services;

import com.project.rabs.restaurant.website.entity.Menu;
import com.project.rabs.restaurant.website.exceptions.DuplicateMenuItemException;
import com.project.rabs.restaurant.website.exceptions.MenuItemNotFoundException;
import com.project.rabs.restaurant.website.exceptions.MenuServiceException;
import com.project.rabs.restaurant.website.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> findAll() {
        List<Menu> list = new ArrayList<>();
        menuRepository.findAll().forEach(list::add);

        if (list.isEmpty()) {
            throw new MenuItemNotFoundException("There are no menu items in the database");
        }
        return list;
    }

    public Menu findById(Long id) {
        Optional<Menu> foundMenuItem = menuRepository.findByItemId(id);
        return foundMenuItem.orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found"));
    }

    public Menu findByName(String name) {
        Optional<Menu> foundMenuItem = menuRepository.findByItemName(name);
        return foundMenuItem.orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found"));
    }

    public void deleteMenuItem(String name) {
        if (menuRepository.existsByItemName(name)) {
            menuRepository.deleteByItemName(name);
        } else {
            throw new MenuItemNotFoundException("Menu Item not found to delete");
        }

    }

    private void validateStockCount(Menu menuItem, Integer quantity) {
        if (menuItem.getStockCount() - quantity < 0) {
            throw new MenuServiceException("Stock count cannot be less than 0");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        }

    }

    public void decreaseStock(String itemName, Integer quantity) {
        Optional<Menu> foundMenuItem = menuRepository.findByItemName(itemName);

        if (foundMenuItem.isPresent()) {
            validateStockCount(foundMenuItem.get(), quantity);
            Menu menuItem = foundMenuItem.get();
            menuItem.setStockCount(menuItem.getStockCount() - quantity);
            menuRepository.save(menuItem);
        } else {
            throw new MenuItemNotFoundException("Menu Item not found");
        }
    }

    private void validateMenuItem(Menu menuItem) {
        if (menuItem.getItemName() == null || menuItem.getItemName().isEmpty()) {
            throw new IllegalArgumentException("Menu item name cannot be empty or null");
        }

        if (menuItem.getPrice() < 0) {
            throw new IllegalArgumentException("Menu item price cannot be less than 0");
        }

        if (menuItem.getStockCount() < 0) {
            throw new IllegalArgumentException("Menu item stock count cannot be less than 0");
        }
    }

    public Menu addMenuItem(Menu newMenuItem) {
        validateMenuItem(newMenuItem);

        if (!menuRepository.existsByItemName(newMenuItem.getItemName())) {
            return menuRepository.save(newMenuItem);
        } else {
            throw new DuplicateMenuItemException("Menu Item already exists");
        }

    }

    public Optional<Menu> updateMenuItem(Menu newMenuItem) {
        if (newMenuItem == null) {
            throw new IllegalArgumentException("Menu Item cannot be null");
        }

        validateMenuItem(newMenuItem);

        Optional<Menu> foundMenuItem = Optional.empty();
        if (newMenuItem.getItemId() != null) {
            foundMenuItem = menuRepository.findByItemId(newMenuItem.getItemId());
        }

        if (foundMenuItem.isPresent()) {
            Menu menuItem = foundMenuItem.get();
            menuItem.setItemName(newMenuItem.getItemName());
            menuItem.setPrice(newMenuItem.getPrice());
            menuItem.setStockCount(newMenuItem.getStockCount());

            return Optional.of(menuRepository.save(menuItem));
        }
        return Optional.empty();
    }



}
