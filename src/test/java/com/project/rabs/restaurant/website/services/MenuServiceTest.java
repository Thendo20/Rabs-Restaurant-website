package com.project.rabs.restaurant.website.services;

import com.project.rabs.restaurant.website.entity.Menu;
import com.project.rabs.restaurant.website.exception.MenuServiceException;
import com.project.rabs.restaurant.website.repository.MenuRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {
    private Menu defaultMenuItem = new Menu();
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;
    private AutoCloseable autoCloseable;

    private Menu createSampleMenuItem(Long id, String name, Double price, int stockCount) {
        Menu menuItem = new Menu();
        menuItem.setItemId(id);
        menuItem.setItemName(name);
        menuItem.setPrice(price);
        menuItem.setStockCount(stockCount);

        return menuItem;
    }

    @BeforeEach
    void init() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        menuService = new MenuService(menuRepository);
        defaultMenuItem = createSampleMenuItem(1L, "Pap and Gravy", 50.00, 10 );
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void returnAllMenuItems() {
        Menu meniItem2 = createSampleMenuItem(2L, "Pap and Chicken", 60.00, 5);
        Menu menuItem3 = createSampleMenuItem(3L, "Pap and Boerewors", 80.00, 20);

        List<Menu> menuItems = List.of(defaultMenuItem, meniItem2, menuItem3);
        when(menuRepository.findAll()).thenReturn(menuItems);

        List<Menu> menuItemList = menuService.findAll();
        assertThat(menuItemList.size()).isEqualTo(3);
        assertThat(menuItemList.get(0).getItemName()).isEqualTo("Pap and Gravy");
        assertThat(menuItemList.get(1).getItemName()).isEqualTo("Pap and Chicken");
        assertThat(menuItemList.get(2).getItemName()).isEqualTo("Pap and Boerewors");

    }

    @Test
    void findItemByID() {
        when(menuRepository.findByItemId(defaultMenuItem.getItemId())).thenReturn(Optional.of(defaultMenuItem));
        Menu foundMenuItem = menuService.findById(defaultMenuItem.getItemId());
        assertThat(foundMenuItem.getItemName()).isEqualTo(defaultMenuItem.getItemName());

        when(menuRepository.findByItemId(defaultMenuItem.getItemId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.findById(defaultMenuItem.getItemId()))
                .isInstanceOf(MenuServiceException.class)
                .hasMessage("Menu Item not found");
    }

    @Test
    void findItemByName() {
        when(menuRepository.findByItemName("Pap and Gravy")).thenReturn(Optional.of(defaultMenuItem));
        Menu foundMenuItem = menuService.findByName(defaultMenuItem.getItemName());
        assertThat(foundMenuItem.getItemName()).isEqualTo(defaultMenuItem.getItemName());

        when(menuRepository.findByItemName("Pap")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.findByName("Pap"))
                .isInstanceOf(MenuServiceException.class)
                .hasMessage("Menu Item not found");
    }

    @Test
    void addMenuItem() {
        when(menuRepository.existsByItemName(defaultMenuItem.getItemName())).thenReturn(false);
        menuService.addMenuItem(defaultMenuItem);
        verify(menuRepository, times(1)).save(defaultMenuItem);

        when(menuRepository.existsByItemName(defaultMenuItem.getItemName())).thenReturn(true);
        assertThatThrownBy(() -> menuService.addMenuItem(defaultMenuItem))
                .isInstanceOf(MenuServiceException.class)
                .hasMessage("Menu Item already exists");
    }

    @Test
    void deleteMenuItem() {
        when(menuRepository.existsByItemName(defaultMenuItem.getItemName())).thenReturn(true);
        menuService.deleteMenuItem(defaultMenuItem.getItemName());
        verify(menuRepository, times(1)).deleteByItemName(defaultMenuItem.getItemName());

        when(menuRepository.existsByItemName(defaultMenuItem.getItemName())).thenReturn(false);
        assertThatThrownBy(() -> menuService.deleteMenuItem(defaultMenuItem.getItemName()))
                .isInstanceOf(MenuServiceException.class)
                .hasMessage("Menu Item not found to delete");
    }

    @Test
    void decreaseStock() {
        when(menuRepository.findByItemName("Pap and Gravy")).thenReturn(Optional.of(defaultMenuItem));
        menuService.decreaseStock(defaultMenuItem.getItemName(), 5);

        verify(menuRepository).findByItemName("Pap and Gravy");
        verify(menuRepository).save(defaultMenuItem);

        assertThatThrownBy(() -> menuService.decreaseStock(defaultMenuItem.getItemName(), 11))
                .isInstanceOf(MenuServiceException.class)
                        .hasMessage("Stock count cannot be less than 0");

        assertThatThrownBy(() -> menuService.decreaseStock(defaultMenuItem.getItemName(), -5))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("Quantity cannot be less than 0");

        when(menuRepository.findByItemName("Pap and Gravy")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.decreaseStock(defaultMenuItem.getItemName(), -5))
                .isInstanceOf(MenuServiceException.class)
                .hasMessage("Menu Item not found");
    }

    @Test
    void shouldUpdateMenuItemAndReturn() {
        Menu updateMenuItem = createSampleMenuItem(1L, "Pap and Gravy", 30.00, 5);

        when(menuRepository.findByItemId(1L)).thenReturn(Optional.of(defaultMenuItem));
        when(menuRepository.save(any(Menu.class))).thenReturn(defaultMenuItem);

        Optional<Menu> updatedMenuItem = menuService.updateMenuItem(updateMenuItem);

        assertThat(updatedMenuItem.isPresent()).isTrue();
        verify(menuRepository).findByItemId(1L);
        verify(menuRepository).save(defaultMenuItem);
    }

}
