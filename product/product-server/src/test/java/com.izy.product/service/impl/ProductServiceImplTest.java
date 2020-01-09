package com.izy.product.service.impl;

import com.izy.product.ProductApplicationTests;
import com.izy.product.common.DecreaseStockInput;
import com.izy.product.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest  {
    @Autowired
    ProductService productService;
    @Test
    public void decreaseStock() {
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput("157875196366160022",2);
        productService.decreaseStock(Arrays.asList(decreaseStockInput));
    }
}
