package com.example.productmanagementsystemrepo.Scheduler;

import com.example.productmanagementsystemrepo.Model.PriceUpdateRequests;
import com.example.productmanagementsystemrepo.Service.Kafka.PriceUpdateRequestsProducer;
import com.example.productmanagementsystemrepo.Service.ProductManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ProductManagementScheduler {
    @Autowired
    private final ProductManagementService productManagementService;
    @Autowired
    private final PriceUpdateRequestsProducer producer;


//    private static final Logger logger  = LoggerFactory.getLogger(ProductManagementScheduler.class);
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime twentyFourHoursBack = now.minusHours(24);

    public ProductManagementScheduler(ProductManagementService productManagementService, PriceUpdateRequestsProducer producer) {
        this.productManagementService = productManagementService;
        this.producer = producer;
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void LatestPriceUpdate() {
        log.info("cron started");
        List<PriceUpdateRequests> list = productManagementService.getAllPriceUpdateRequests(twentyFourHoursBack, now);
        //logger.info("list: {}",list);
        if(!list.isEmpty()) {
            try {
                producer.sendPriceUpdateRequests(list);
            }catch (Exception e) {
//                logger.error("error occurred {}", e.getMessage());
                log.error("error occurred {}", e.getMessage());
            }
        }else {
            log.info("There are no price update requests in the last 24 hours");
        }
    }
}
