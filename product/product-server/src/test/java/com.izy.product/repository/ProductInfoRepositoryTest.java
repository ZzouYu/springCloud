package com.izy.product.repository;

import com.izy.product.dataObject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productinfo = productInfoRepository.findByProductStatus(0);
        Assert.assertTrue(productinfo.size()>0);
    }

    @Test
    public void findByProductIdIn() {
        List<ProductInfo> productinfo = productInfoRepository.findByProductIdIn(Arrays.asList("164103465734242707"));
        Assert.assertTrue(productinfo.size()>0);
    }
}
