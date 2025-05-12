package com.AIBaraka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepo, FileInfoRepository fileRepo, UserService userService) {
        return args -> {
            // Create a sample admin and user
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword("admin123"); // The password will be encoded
            admin.setRole(User.Role.ADMIN);
            userRepo.save(userService.createUser(admin));

            User user = new User();
            user.setEmail("user@example.com");
            user.setPassword("user123"); // The password will be encoded
            user.setRole(User.Role.USER);
            userRepo.save(userService.createUser(user));

            // Insert dummy files as before
            for (int i = 1; i <= 20; i++) {
                FileInfo file = new FileInfo();
                file.setUser(i % 2 == 0 ? admin : user);
                file.setFileName("document" + i + ".pdf");
                file.setFileSize(1024L * i);
                file.setFileType("pdf");
                file.setStatus(i % 3 == 0 ? FileInfo.Status.APPROVED : FileInfo.Status.PENDING);
                file.setComments("Dummy file " + i);
                file.setFilePath("/uploads/document" + i + ".pdf");
                fileRepo.save(file);
            }
        };
    }
}
