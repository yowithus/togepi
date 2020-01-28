package com.example.togepi.util;

import com.example.togepi.enums.Category;
import com.example.togepi.entity.Item;

import java.math.BigDecimal;

public class CommonUtil {

    public static BigDecimal calculateTax(Item item) {
        final BigDecimal price = item.getPrice();
        final String categoryId = item.getCategoryId();
        final Category category = Category.findById(categoryId);

        switch (category) {
            case FOOD_BEVERAGE: {
                final BigDecimal percentage = BigDecimal.valueOf(0.1);
                return price.multiply(percentage).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            }
            case TOBACCO: {
                final BigDecimal percentage = BigDecimal.valueOf(0.02);
                final BigDecimal additionalFee = BigDecimal.valueOf(10);
                return price.multiply(percentage).add(additionalFee).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            }
            case ENTERTAINMENT: {
                final BigDecimal percentage = BigDecimal.valueOf(0.01);
                final BigDecimal threshold = BigDecimal.valueOf(100);
                return price.compareTo(threshold) < 0 ?
                        BigDecimal.ZERO :
                        price.subtract(threshold).multiply(percentage).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            }
            default: {
                return BigDecimal.ZERO;
            }
        }
    }
}
