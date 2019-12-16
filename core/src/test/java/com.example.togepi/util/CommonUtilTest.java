package com.example.togepi.util;

import com.example.togepi.entity.Item;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CommonUtilTest {

    @Test
    public void givenFoodAndBeverageItem_whenCalculatingTax_thenReturnCorrectResult() {
        final String name = "Test item";
        final String categoryId = "1";
        final BigDecimal price = BigDecimal.valueOf(500);
        final Item item = new Item(name, categoryId, price);
        final BigDecimal taxResult = BigDecimal.valueOf(50).setScale(2, BigDecimal.ROUND_HALF_DOWN);

        assertEquals(CommonUtil.calculateTax(item), taxResult);
    }

    @Test
    public void givenTobaccoItem_whenCalculatingTax_thenReturnCorrectResult() {
        final String name = "Test item 2";
        final String categoryId = "2";
        final BigDecimal price = BigDecimal.valueOf(500);
        final Item item = new Item(name, categoryId, price);
        final BigDecimal taxResult = BigDecimal.valueOf(20).setScale(2, BigDecimal.ROUND_HALF_DOWN);

        assertEquals(CommonUtil.calculateTax(item), taxResult);
    }

    @Test
    public void givenEntertainmentItemWithPriceLessThan100_whenCalculatingTax_thenReturnCorrectResult() {
        final String name = "Test item 3";
        final String categoryId = "3";
        final BigDecimal price = BigDecimal.valueOf(99);
        final Item item = new Item(name, categoryId, price);
        final BigDecimal taxResult = BigDecimal.valueOf(0);

        assertEquals(CommonUtil.calculateTax(item), taxResult);
    }

    @Test
    public void givenEntertainmentItemWithPriceGreaterThan100_whenCalculatingTax_thenReturnCorrectResult() {
        final String name = "Test item 4";
        final String categoryId = "3";
        final BigDecimal price = BigDecimal.valueOf(101);
        final Item item = new Item(name, categoryId, price);
        final BigDecimal taxResult = BigDecimal.valueOf(0.01).setScale(2, BigDecimal.ROUND_HALF_DOWN);

        assertEquals(CommonUtil.calculateTax(item), taxResult);
    }
}
