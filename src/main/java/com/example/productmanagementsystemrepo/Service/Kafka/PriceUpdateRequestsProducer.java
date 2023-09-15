package com.example.productmanagementsystemrepo.Service.Kafka;

import com.example.productmanagementsystemrepo.Requests.AdminPriceRequests.PriceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class PriceUpdateRequestsProducer {
    private final KafkaTemplate<String, String>kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.consumer.topics}")
    private String  kafkaTopic;


//    private final static Logger logger = LoggerFactory.getLogger(PriceUpdateRequestsProducer.class);

    public PriceUpdateRequestsProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendPriceUpdateRequests(List<PriceRequest> priceRequests) {
        try {
            //logger.info(objectMapper.writeValueAsString(priceUpdateRequests));
            kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(priceRequests));
        }catch(Exception e) {
            log.error("error from producer: {} ,while sending message to topic",e.getMessage());
        }
    }
}
