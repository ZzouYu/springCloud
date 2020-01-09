package com.izy.product.repository;

import com.izy.product.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    /**
     * 根据商品状态查询出所有的商品
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     *根据productIds查询出所有的商品
     * @param productIds
     * @return
     */
    List<ProductInfo> findByProductIdIn(List<String> productIds);
}
