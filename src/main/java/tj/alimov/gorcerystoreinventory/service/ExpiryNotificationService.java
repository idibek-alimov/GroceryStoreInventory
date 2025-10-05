package tj.alimov.gorcerystoreinventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tj.alimov.gorcerystoreinventory.repository.StockBatchRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ExpiryNotificationService {
    private final StockBatchRepository stockBatchRepository;

    @Scheduled(cron = "0 0 9 * * ?")
    public void checkExpiries(){
        Instant now = Instant.now();
        Instant threshold = now.plus(7, ChronoUnit.DAYS);
        stockBatchRepository.findAll().stream()
                .filter(batch -> batch.getExpiryDate().isBefore(threshold))
                .forEach(batch -> {
                    System.out.println("Product " + batch.getProduct().getName() +
                            " expiring on " + batch.getExpiryDate());
                });
    }
}
