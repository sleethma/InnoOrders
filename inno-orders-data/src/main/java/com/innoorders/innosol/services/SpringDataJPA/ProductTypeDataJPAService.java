package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.ProductType;
import com.innoorders.innosol.repos.ProductTypeRepo;
import com.innoorders.innosol.services.ProductTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class ProductTypeDataJPAService implements ProductTypeService {

    private final ProductTypeRepo productTypeRepo;

    public ProductTypeDataJPAService(ProductTypeRepo productTypeRepo) {
        this.productTypeRepo = productTypeRepo;
    }

    @Override
    public Set<ProductType> findAll() {
        Set<ProductType> productTypes = new HashSet<>();
        productTypeRepo.findAll().forEach(productTypes:: add);
        return productTypes;
    }

    @Override
    public ProductType findById(Long id) {
        return productTypeRepo.findById(id).orElse(null);
    }

    @Override
    public ProductType save(ProductType object) {
        return productTypeRepo.save(object);
    }

    @Override
    public void delete(ProductType object) {
    productTypeRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    productTypeRepo.deleteById(id);
    }
}
