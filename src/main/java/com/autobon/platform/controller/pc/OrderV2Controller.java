package com.autobon.platform.controller.pc;

import com.autobon.cooperators.entity.CoopAccount;
import com.autobon.cooperators.entity.Cooperator;
import com.autobon.order.entity.Construction;
import com.autobon.order.entity.Order;
import com.autobon.order.entity.WorkDetail;
import com.autobon.order.service.ConstructionService;
import com.autobon.order.service.OrderService;
import com.autobon.order.service.WorkDetailService;
import com.autobon.order.vo.*;
import com.autobon.shared.JsonMessage;
import com.autobon.shared.JsonPage;
import com.autobon.shared.JsonResult;
import com.autobon.technician.entity.Technician;
import com.autobon.technician.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by wh on 2016/11/15.
 */
@RestController
@RequestMapping("/api/web/admin/order/v2")
public class OrderV2Controller {
    @Autowired
    OrderService orderService;
    @Autowired
    WorkDetailService workDetailService;
    @Autowired
    ConstructionService constructionService;
    @Autowired
    TechnicianService technicianService;
    @RequestMapping(method = RequestMethod.GET)
    public JsonMessage search(
            @RequestParam(value = "orderNum", required = false) String orderNum,
            @RequestParam(value = "orderCreator", required = false) String orderCreator,
            @RequestParam(value = "orderType", required = false) Integer orderType,
            @RequestParam(value = "orderStatus", required = false) Order.Status orderStatus,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        String creatorName = null;
        String contactPhone = null;
        List<Integer> types = null;
        Integer statusCode = orderStatus != null ? orderStatus.getStatusCode() : null;

        if (!"orderTime".equals(sort) && !"id".equals(sort)) return new JsonMessage(false, "ILLEGAL_SORT_PARAM" , "sort参数只能为id或orderTime");

        if (orderCreator != null) {
            if (Pattern.matches("\\d{11}", orderCreator)) {
                contactPhone = orderCreator;
            } else {
                creatorName = orderCreator;
            }
        }

        if (orderType != null) {
            types = Arrays.asList(orderType);
        }

        return new JsonMessage(true, "", "",
                new JsonPage<>(orderService.find(orderNum, creatorName, contactPhone,
                        types, statusCode, sort, Sort.Direction.DESC, page, pageSize)));
    }

    @RequestMapping(value = "/{orderId:[\\d]+}", method = RequestMethod.PUT)
    public JsonMessage update(
            @PathVariable("orderId") int orderId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "status", required = false) int status,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "remark", required = false)String remark,
            @RequestParam(value = "positionLon", required = false)String positionLon,
            @RequestParam(value = "positionLat", required = false)String positionLat,
            @RequestBody List<WorkDetailShow> workDetailShowList) {
        JsonMessage msg = new JsonMessage(true);
        ArrayList<String> messages = new ArrayList<>();
        Order order = orderService.getbyOrderId(orderId);

        if(order == null){
            msg.setError("INVALID_ID");
            messages.add("没有此订单");
        }

        order.setType(type == null ? order.getType():type);
        order.setContactPhone(phone == null ? order.getContactPhone():phone);
        order.setRemark(remark == null ? order.getRemark():remark);
        order.setPositionLon(positionLon == null ? order.getPositionLon():positionLon);
        order.setPositionLat(positionLat == null ? order.getPositionLat():positionLat);
        orderService.save(order);
        for(WorkDetailShow workDetailShow : workDetailShowList){
            WorkDetail workDetail = workDetailService.getByOderIdAndTechId(workDetailShow.getOrderId(),workDetailShow.getTechId());
            workDetail.setProject1(workDetailShow.getProject1() == null? workDetail.getProject1(): workDetailShow.getProject1());
            workDetail.setPosition1(workDetailShow.getPosition1() == null? workDetail.getPosition1(): workDetailShow.getPosition1());
            workDetail.setProject2(workDetailShow.getProject2() == null? workDetail.getProject2(): workDetailShow.getProject2());
            workDetail.setPosition2(workDetailShow.getPosition2() == null? workDetail.getPosition2(): workDetailShow.getPosition2());
            workDetailService.save(workDetail);
        }


        msg.setData(order);
     return msg;
    }
}
