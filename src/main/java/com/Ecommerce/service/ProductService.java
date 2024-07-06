package com.Ecommerce.service;

import com.Ecommerce.dto.ProductDto;

import java.util.List;

public interface ProductService {
    public ProductDto addProduct(ProductDto dto);
    public void deleteProduct(long productId);
    public ProductDto updateProduct(long productId,ProductDto productDto);
    public List<ProductDto> ListOfProducts(int pageNo,int pageSize,String sortBy,String sortDir);
    public ProductDto getById(long productId);
}