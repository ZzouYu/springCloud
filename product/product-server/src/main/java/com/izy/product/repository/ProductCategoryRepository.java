package com.izy.product.repository;

import com.izy.product.dataObject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    /**
     * 根据类型查找商品类目
     * @param categoryType
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
