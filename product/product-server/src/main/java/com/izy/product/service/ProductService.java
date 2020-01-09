package com.izy.product.service;

import com.izy.product.common.DecreaseStockInput;
import com.izy.product.common.ProductInfoOutput;
import com.izy.product.dataObject.ProductInfo;

import java.util.List;

public interface ProductService {
    List<ProductInfo> findUpList(Integer productStatus);

    List<ProductInfoOutput> findList(List<String> productIds);

    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
