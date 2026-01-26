package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

    private final UserRepository userRepository;

    public TransactionListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    @Transactional
    public void listen(Transaction transaction) {
        logger.debug("Received transaction: {}", transaction);

        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender != null && recipient != null) {
            if (sender.getBalance() >= transaction.getAmount()) {
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                recipient.setBalance(recipient.getBalance() + transaction.getAmount());
                userRepository.save(sender);
                userRepository.save(recipient);
            }
        }
    }
}
