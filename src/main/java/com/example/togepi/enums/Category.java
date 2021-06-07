package com.example.togepi.enums;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    FOOD_BEVERAGE("1", "Food & Beverage"),
    TOBACCO("2", "Tobacco"),
    ENTERTAINMENT("3", "Entertainment");

    private String id;
    private String name;

    Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private static final Map<String, Category> CATEGORY_MAP;

    static {
        CATEGORY_MAP = new HashMap<>();
        for (Category category : Category.values()) {
            CATEGORY_MAP.put(category.getId(), category);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Category findById(final String id) {
        return CATEGORY_MAP.getOrDefault(id, Category.FOOD_BEVERAGE);
    }
}
