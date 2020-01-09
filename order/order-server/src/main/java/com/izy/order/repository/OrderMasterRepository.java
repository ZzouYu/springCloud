package com.izy.order.repository;

import com.izy.order.dataObject.OrderDetail;
import com.izy.order.dataObject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
}
