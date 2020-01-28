package com.example.togepi.service.impl;

import com.example.togepi.entity.Item;
import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.dao.ItemDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class DefaultLockingService {

    @Autowired
    private ItemDao itemDao;

    public Item updateItem(Long itemId, Item reqItem) {
        final BigDecimal price = reqItem.getPrice();
        final long threadId = Thread.currentThread().getId();

        log.info("before acquiring read lock {}", threadId);

        return itemDao.findById(itemId)
                .map(item -> {
                    log.info("after acquiring read lock {}", threadId);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    item.setPrice(price);

                    final Item resItem = itemDao.save(item);

                    log.info("after write commit {}", threadId);

                    return resItem;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
