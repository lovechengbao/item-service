package com.heima.item.canal;

import com.github.benmanes.caffeine.cache.Cache;
import com.heima.item.config.RedisHandler;
import com.heima.item.pojo.Item;
import com.heima.item.pojo.ItemStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@Component
@CanalTable("tb_item")
public class ItemHandler implements EntryHandler<Item> {
    @Autowired
    private RedisHandler redisHandler;
    @Autowired
    private Cache<Long,Item> itemCache;
    @Override
    public void insert(Item item) {
        redisHandler.saveItem("item:id:" + item.getId(),item);
    }

    @Override
    public void update(Item before, Item after) {
        redisHandler.saveItem("item:id:" + after.getId(),after);
    }

    @Override
    public void delete(Item item) {
        redisHandler.deleteItemById("item:id:" + item.getId());
    }
}
