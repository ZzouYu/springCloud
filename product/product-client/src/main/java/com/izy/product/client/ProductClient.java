package com.izy.product.client;

import com.izy.product.common.ProductInfoOutput;
import com.izy.product.common.DecreaseStockInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="product",fallback = ProductClient.ProductClientFallback.class )
public interface ProductClient {
    @GetMapping("/msg")
    //方法名称可以与服务端不同
     String productMsg();
    @PostMapping("/product/listForOrder")
    //方法名称可以与服务端不同
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);
    //调用扣库存服务
    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);

    @Component
    static class ProductClientFallback implements ProductClient{

        @Override
        public String productMsg() {
            return null;
        }

        @Override
        public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {

        }
    }
}
