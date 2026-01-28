// package com.jpmc.midascore.controller;

// import com.jpmc.midascore.entity.UserRecord;
// import com.jpmc.midascore.foundation.Balance;
// import com.jpmc.midascore.repository.UserRepository;
// import java.util.Optional;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class BalanceController {
//     private final UserRepository userRepository;

//     public BalanceController(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     // @GetMapping("/balance")
//     // public Balance getBalance(@RequestParam Long userId) {
//     //     // Task 5 requires looking up by userId
//     //     UserRecord user = userRepository.findById(userId.longValue());

//     //     if (user != null) {
//     //         // Return balance. Since your Balance class now has multiple 
//     //         // constructors, this will work.
//     //         return new Balance(user.getBalance());
//     //     }
//     //     // Requirement: Default to 0 if user does not exist
//     //     return new Balance(0); 
//     // }

//     @GetMapping("/balance")
//     public Balance getBalance(@RequestParam Long userId) {
//         // This calls the internal JPA method that looks for the @Id field in UserRecord
//         Optional<UserRecord> userOpt = userRepository.findById(userId); 

//         if (userOpt.isPresent()) {
//             return new Balance(userOpt.get().getBalance());
//         }
//         return new Balance(0);
//     }
// }

package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {
    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        Optional<UserRecord> userOpt = userRepository.findById(userId); 
        if (userOpt.isPresent()) {
            UserRecord user = userOpt.get();
            return new Balance(user.getName(), user.getBalance());
        }
        return new Balance(null, 0);
    }
}