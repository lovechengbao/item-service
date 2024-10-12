package com.heima.item.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.heima.item.pojo.Item;
import com.heima.item.pojo.ItemStock;
import com.heima.item.service.IItemService;
import com.heima.item.service.IItemStockService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RedisHandler implements InitializingBean {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IItemService itemService;
    @Autowired
    private IItemStockService itemStockService;
    @Override
    public void afterPropertiesSet() throws Exception {
        //查询商品和库存 存入redis
        List<Item> itemList = itemService.list();
        Map<String, String> itemMap = itemList.stream().collect(Collectors.toMap(item -> "item:id:" + item.getId().toString(), JSONUtil::toJsonStr));
        stringRedisTemplate.opsForValue().multiSet(itemMap);

        List<ItemStock> itemStockList = itemStockService.list();
        Map<String, String> itemStockMap =  itemStockList.stream().collect(Collectors.toMap(item -> "item:stock:id:" + item.getId().toString(), JSONUtil::toJsonStr));
        stringRedisTemplate.opsForValue().multiSet(itemStockMap);
    }

}
