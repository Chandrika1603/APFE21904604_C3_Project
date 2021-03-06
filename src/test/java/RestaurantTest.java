import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        Restaurant spiedRestaurant=Mockito.spy(restaurant);
        LocalTime nowTime = spiedRestaurant.closingTime.minusHours(3);
        when(spiedRestaurant.getCurrentTime()).thenReturn(nowTime);

        assertThat(spiedRestaurant.getCurrentTime(),lessThan(restaurant.closingTime));
        assertThat(spiedRestaurant.getCurrentTime(),greaterThan(restaurant.openingTime));

        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spiedRestaurant=Mockito.spy(restaurant);
        LocalTime nowTime = spiedRestaurant.openingTime.minusHours(3);
        when(spiedRestaurant.getCurrentTime()).thenReturn(nowTime);

        assertThat(spiedRestaurant.getCurrentTime(),lessThan(restaurant.closingTime));
        assertThat(spiedRestaurant.getCurrentTime(),lessThan(restaurant.openingTime));

        assertFalse(spiedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void calculateOrderCost_called_with_selected_items_from_the_menu_should_return_the_total_order_cost(){

        //Arrange
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Dal soup",179);
        restaurant.addToMenu("Vegetable curry", 369);

        List<String> selectedItemNames= new ArrayList<String>();
        selectedItemNames.add("Vegetable lasagne");
        selectedItemNames.add("Dal soup");
        int expectedCost = 269+179;
        int totalCost = restaurant.calculateOrderCost(selectedItemNames);

        //Assert
        assertEquals(expectedCost,totalCost);
    }

    @Test
    public void calculateOrderCost_called_with_0_items_from_the_menu_should_return_0_as_the_order_cost(){

        //Arrange
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Dal soup",179);
        restaurant.addToMenu("Vegetable curry", 369);

        List<String> selectedItemNames= new ArrayList<String>();
        int expectedCost = 0;
        int totalCost = restaurant.calculateOrderCost(selectedItemNames);

        //Assert
        assertEquals(expectedCost,totalCost);
    }

    @BeforeEach
    private void initializeRestaurant() {
        System.out.println("initializeRestaurant");
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}