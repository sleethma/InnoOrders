package com.innoorders.innosol.formatters;

import com.innoorders.innosol.models.ProductType;
import com.innoorders.innosol.services.ProductTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class ProductTypeFormatter implements Formatter<ProductType> {

    private final ProductTypeService productTypeService;

    public ProductTypeFormatter(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @Override
    public String print(ProductType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public ProductType parse(String text, Locale locale) throws ParseException {
        Collection<ProductType> findProductTypes = productTypeService.findAll();
        for (ProductType type : findProductTypes) {
            if (type.getName().equals(text)) {
                return type;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }

}
