package com.moyeo.dao;

import com.moyeo.dto.CartDTO;
import java.util.List;
import java.util.Map;

public interface CartDAO {
    void addCart(CartDTO cartDTO);
    List<CartDTO> cartList(String userinfoId);
    void updateCart(CartDTO cartDTO);
    int sumTotal(String userinfoId);
    void deleteCart(int cartIdx);
    List<Map<String, Object>> getAllCartItemsWithPackages(String userinfoId);
    
    /*결제*/
    CartDTO selectCartByIdx (int cartIdx);
}