package com.example.productmanagementsystemrepo.Scheduler;

import com.example.productmanagementsystemrepo.Requests.AdminPriceRequests.PriceRequest;
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


    public ProductManagementScheduler(ProductManagementService productManagementService, PriceUpdateRequestsProducer producer) {
        this.productManagementService = productManagementService;
        this.producer = producer;
    }

    @Scheduled(cron = "0 */59 * * * *")
    public void LatestPriceUpdate() {
        log.info("cron started");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursBack = now.minusHours(24);
        List<PriceRequest> list;
        int batchSize = 5;
        int offset = 0;
        do {
            list = productManagementService.getAllPriceUpdateRequests(twentyFourHoursBack, now, batchSize, offset);
            offset+=batchSize;
            log.info("list size is: {}", list.size());
            if (!list.isEmpty()) {
                producer.sendPriceUpdateRequests(list);
            } else {
                log.info("There are no price updates in last 24 hrs");
                break;
            }
        } while (true);
    }
}
