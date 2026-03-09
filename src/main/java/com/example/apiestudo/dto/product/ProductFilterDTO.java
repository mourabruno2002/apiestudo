package com.example.apiestudo.dto.product;

import com.example.apiestudo.model.Category;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class ProductFilterDTO {

    private String name;

    private String sku;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal priceMin;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal priceMax;

    private Boolean active;

    private String categoryName;

    //GETTERS
    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public Boolean getActive() {
        return active;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
