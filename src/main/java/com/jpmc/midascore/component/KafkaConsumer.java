package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KafkaConsumer {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate; // 1. Add this field

    public KafkaConsumer(UserRepository userRepository, 
                         TransactionRepository transactionRepository, 
                         RestTemplate restTemplate) { // 2. Inject it here
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void listen(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender != null && recipient != null && sender.getBalance() >= transaction.getAmount()) {

            // 3. Call the Incentive API
            // Note: Spring handles the serialization of the Transaction object automatically
            Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive", 
                transaction, 
                Incentive.class
            );
            float incentiveAmount = (incentive != null) ? incentive.getAmount() : 0f;

            // 4. Update balances
            // Sender: Minus transaction amount only
            // Recipient: Plus transaction amount AND incentive
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

            // 5. Save the records
            userRepository.save(sender);
            userRepository.save(recipient);

            TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount());
            record.setIncentive(incentiveAmount); // Save the incentive amount in your new field
            transactionRepository.save(record);
        }

        // 6. Check for Wilbur
        UserRecord wilbur = userRepository.findByName("wilbur");
        if (wilbur != null) {
            System.out.println("USER WILBUR BALANCE: " + wilbur.getBalance());
        }
    }
}