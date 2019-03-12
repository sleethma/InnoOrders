package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.ProductType;
import com.innoorders.innosol.services.ProductTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class ProductTypeMapService extends AbstractMapService<ProductType, Long> implements ProductTypeService {
    @Override
    public Set<ProductType> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
    super.deleteById(id);
    }

    @Override
    public void delete(ProductType object) {
    super.deleteByObject(object);
    }

    @Override
    public ProductType save(ProductType object) {
        return super.save(object);
    }

    @Override
    public ProductType findById(Long id) {
        return super.findById(id);
    }
}
