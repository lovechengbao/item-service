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
@CanalTable("tb_item_stock")
public class ItemStockHandler implements EntryHandler<ItemStock> {
    @Autowired
    private RedisHandler redisHandler;
    @Autowired
    private Cache<Long,ItemStock> stockCache;

    @Override
    public void insert(ItemStock itemStock) {
        redisHandler.saveItem("item:stock:id:" + itemStock.getId(),itemStock);
    }

    @Override
    public void update(ItemStock before, ItemStock after) {
        redisHandler.saveItem("item:stock:id:" + after.getId(),after);
    }

    @Override
    public void delete(ItemStock itemStock) {
        redisHandler.deleteItemById("item:id:" + itemStock.getId());
    }
}
