package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class VerifyWaldorfBalance {
    static final Logger logger = LoggerFactory.getLogger(VerifyWaldorfBalance.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void verify_waldorf_balance() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(3000);

        UserRecord waldorf = userRepository.findByName("waldorf");
        
        System.out.println("\n");
        System.out.println("============================================================");
        System.out.println("============================================================");
        System.out.println("   WALDORF'S BALANCE AFTER ALL TRANSACTIONS: " + waldorf.getBalance());
        System.out.println("============================================================");
        System.out.println("============================================================");
        System.out.println("\n");
    }
}
