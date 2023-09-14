package com.example.productmanagementsystemrepo.Service.Kafka;

import com.example.productmanagementsystemrepo.Constants.Constants;
import com.example.productmanagementsystemrepo.Requests.AdminPriceRequests.PriceRequest;
import com.example.productmanagementsystemrepo.Service.PriceUpdateService;
import com.example.productmanagementsystemrepo.Service.Redis.RedisService;
import com.example.productmanagementsystemrepo.db.PriceDb;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PriceUpdateRequestsConsumer {
//    private final static Logger logger = LoggerFactory.getLogger(PriceUpdateRequestsConsumer.class);
    @Autowired
   private RedisService redisService;
    @Autowired
    private PriceUpdateService priceUpdateService;
    @Autowired
    private PriceDb priceDb;


    //    @KafkaListener(topics = "price-update-requests-topic", groupId = "price-update-requests-group", topicPartitions = @TopicPartition(topic = "price-update-requests-topic", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")))
    @KafkaListener(topics = Constants.Kafka.TOPICS, groupId = Constants.Kafka.GROUP_ID)
    public void consumePriceUpdateRequests(String message) {
        try {
            //here, consumer not able to understand the localdatetime property by default, so have added dependency in pom.xml and added that module here using the below command
            ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
            List<PriceRequest>batchList = objectMapper.readValue(message, new TypeReference<List<PriceRequest>>() {});
            priceDb.savePriceRequest(batchList);
            log.info("received {} price update requests",batchList.size());

            for(PriceRequest priceRequest : batchList) {
                String key = priceRequest.getId().toString();
                if(redisService.getKey(key) != null) {
                    log.info("{} is already consumed", priceRequest);
                }else {
                    redisService.setKeyValue(key, "consumed");
                        String productListAsString= priceRequest.getProductIdList().substring(1, priceRequest.getProductIdList().length()-1).trim();
                        String[] productList = productListAsString.split(",");
                        List<String>productIdList = new ArrayList<>();
                        for(String productId : productList) {
                            productIdList.add(productId.trim());
                        }
                        Double delta = priceRequest.getDelta();
                        for(String productId: productIdList) {
                            priceUpdateService.priceUpdateReq(productId, delta);
                        }
                    }
                }
        }catch (Exception e) {
            log.error("error from consumer {} while reading the data from topic",e.getMessage());
        }
    }
}
