package com.autobon.platform.controller.technician.order;

import com.autobon.order.entity.Construction;
import com.autobon.order.entity.Order;
import com.autobon.order.service.ConstructionService;
import com.autobon.order.service.OrderService;
import com.autobon.shared.JsonMessage;
import com.autobon.shared.VerifyCode;
import com.autobon.technician.entity.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Created by dave on 16/3/1.
 */
@RestController
@RequestMapping("/api/mobile/technician/construct")
public class ConstructionController {
    @Autowired OrderService orderService;
    @Autowired ConstructionService constructionService;

    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public JsonMessage uploadPhoto(HttpServletRequest request,
            @RequestParam("file")     MultipartFile file) throws IOException {
        if (file.isEmpty()) return new JsonMessage(false, "NO_UPLOAD_FILE", "没有上传文件");

        String path = "/uploads/order";
        File dir = new File(request.getServletContext().getRealPath(path));
        if (!dir.exists()) dir.mkdirs();
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase();
        String filename = "c-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))
                + "-" + VerifyCode.generateVerifyCode(8) + extension;
        file.transferTo(new File(dir.getAbsoluteFile() + File.separator + filename));

        return new JsonMessage(true, "", "", path + "/" + filename);
    }

    @RequestMapping(value = "/beforePhoto", method = RequestMethod.POST)
    public JsonMessage setBeforePhoto(HttpServletRequest request,
            @RequestParam("orderId") int orderId,
            @RequestParam("urls") String urls) {
        if (!Pattern.matches("^([^,\\s]+)(,[^,\\s]+)*$", urls)) {
            return new JsonMessage(false, "PHOTO_PATTERN_MISMATCH", "图片地址格式错误, 请查阅urls参数说明");
        } else if (urls.split(",").length > 3) {
            return new JsonMessage(false, "PHOTO_LIMIT_EXCCED", "图片数量超出限制, 最多3张");
        }

        Technician tech = (Technician) request.getAttribute("user");
        Construction cons = constructionService.getByTechIdAndOrderId(tech.getId(), orderId);
        if (cons == null) {
            return new JsonMessage(false, "NO_CONSTRUCTION", "系统没有你的施工单");
        } else if (cons.getSigninTime() == null) {
            return new JsonMessage(false, "CONSTRUCTION_NOT_SIGNIN", "签到前不可上传照片");
        } else if (cons.getEndTime() != null) {
            return new JsonMessage(false, "CONSTRUCTION_ENDED", "你已完成施工,不可再次上传照片");
        }

        cons.setBeforePhotos(urls);
        constructionService.save(cons);
        return new JsonMessage(true);
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public JsonMessage finish(HttpServletRequest request,
            @RequestParam("orderId")   int orderId,
            @RequestParam("carSeat")   int carSeat,
            @RequestParam("workItems") String workItems) {
        Technician tech = (Technician) request.getAttribute("user");
        Order order = orderService.get(orderId);
        if (order == null || (tech.getId() != order.getMainTechId() && tech.getId() != order.getSecondTechId())) {
            return new JsonMessage(false, "ILLEGAL_OPERATION", "你没有这个订单");
        } else if (order.getStatus() != Order.Status.IN_PROGRESS) {
            return new JsonMessage(false, "ILLEGAL_OPERATION", "非施工中订单, 不允许上传照片");
        }

        return null;
    }
}
