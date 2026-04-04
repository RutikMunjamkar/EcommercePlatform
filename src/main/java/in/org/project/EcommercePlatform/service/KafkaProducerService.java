package in.org.project.EcommercePlatform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Value("${spring.topics.order-topic}")
    String orderTopic;

    private static final Logger logger= LoggerFactory.getLogger(KafkaConsumerService.class);

    public void produceOrderMessage(String message){
        kafkaTemplate.send(orderTopic,message);
        logger.info("Message sent");
    }
}
