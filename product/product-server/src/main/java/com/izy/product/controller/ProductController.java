package com.izy.product.controller;

import com.izy.product.common.DecreaseStockInput;
import com.izy.product.common.ProductInfoOutput;
import com.izy.product.dataObject.ProductCategory;
import com.izy.product.dataObject.ProductInfo;
import com.izy.product.enums.ProductStatusEnum;
import com.izy.product.service.CategoryService;
import com.izy.product.service.ProductService;
import com.izy.product.utils.ResultUtil;
import com.izy.product.vo.CategoryVo;
import com.izy.product.vo.ProductInfoVo;
import com.izy.product.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zouyu
 * @description
 * @date 2019/12/11
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    /**
     * 1. 查询所有在架的商品
     * 2. 获取类目type列表
     * 3. 查询类目
     * 4. 构造数据
     */
    @GetMapping("/list")
    public ResultVo<CategoryVo> getList(){

        //1. 查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpList(ProductStatusEnum.UP.getCode());
        //2. 获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
       // * 3. 查询类目
        List<ProductCategory> categoryType= categoryService.findByCategoryTypeIn(categoryTypeList);
        //4. 构造数据
        List<CategoryVo>  categoryVoList = new ArrayList<CategoryVo>();
        for(ProductCategory productCategory:categoryType){
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setCategoryName(productCategory.getCategoryName());
            categoryVo.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVo> productInfoVoList =new ArrayList<ProductInfoVo>();
            for(ProductInfo productInfo:productInfoList){
                if(productCategory.getCategoryId().equals(productInfo.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            categoryVo.setProductInfoVoList(productInfoVoList);
            categoryVoList.add(categoryVo);
        }
      return  ResultUtil.success(categoryVoList);
    }

    /**
     * 根据商品ID查出商品信息列表
     *  获取商品列表(给订单服务用的)
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ProductInfoOutput> list = productService.findList(productIdList);
        return list;
    }

    /**
     * 扣减库存
     * @param decreaseStockInputList
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productService.decreaseStock(decreaseStockInputList);
    }
}
