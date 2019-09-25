package com.xlauch.core.config.redis;

import cn.hutool.core.date.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述    : redis操作类，用于缓存 <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : RedisManager <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/8 14:54  <br/>
 * @version 0.1
 */
@Configuration
public class RedisManager {

    private static final Logger log = Logger.getLogger(RedisManager.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成id专用
     */
    public static final String ID = "id";

    /********************************** 类型：键 *********************************/
    /**
     * 类型：键
     * 删除给定的一个 key
     * @param key
     * @return
     */
    public void del(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * 类型：键
     * 批量删除键值
     * @param prestr
     * @return
     */
    public void batchDel(final String prestr){
        Set<String> set = keys(prestr +"*");
        redisTemplate.delete(set);
    }

    /**
     * 类型：键
     * 查找所有符合给定模式 pattern 的 key
     * KEYS * 匹配数据库中所有 key
     * @param pattern
     * @return
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 类型：键
     * 检查给定 key 是否存在
     * @param key
     * @return
     */
    public Boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) {
                return redisConnection.exists(key.getBytes());
            }
        });
    }


    /********************************** 类型：字符串 *********************************/

    /**
     * 类型：字符串
     * 将字符串值 value 关联到 key
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型
     * @param key
     * @param value
     */
    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
     * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
     *
     * @param key the key
     * @param appendValue the append value
     */
    public void append(final String key, final String appendValue) {
        redisTemplate.opsForValue().append(key, appendValue);
    }

    /**
     *  类型：字符串
     *  将字符串值 value 关联到 key 并设置失效时间
     * @param key
     * @param value
     * @param expireTime
     */
    public void setWithExp(final String key, final String value, final Date expireTime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expireAt(key, expireTime);
    }

    /**
     * 类型：字符串
     * 返回 key 所关联的字符串值
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 类型：字符串
     * 创建数据id，所有额都通过此方法创建
     *
     * @return
     */
    public long createID() {
        return redisTemplate.opsForValue().increment(ID, 1);
    }

    /**
     * 类型：字符串
     * 创建相应模块的自增长id
     * @author
     * Jun 23, 2014 11:46:00 AM
     */
    public long createModuleID(final String module){
        Long value = redisTemplate.opsForValue().increment(module, 1);
        redisTemplate.expireAt(module, DateUtil.offsetMinute(new Date(), 1));
        return value;
    }

    /**
     * 类型：字符串
     * 用于统计当天的短信数
     * key smscnt:当前日期:手机号 如：smscnt:20160115:12345678901
     * unixTime 当天23:59:59的unix时间戳
     * @author
     * Jun 23, 2014 11:46:00 AM
     */
    public long incrWithExpire(final String key, final Date date){
        Long value = redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expireAt(key, date);
        return value;
    }

    /**
     * 类型：字符串
     * +value的和
     * @param key
     * @param value
     * @return
     */
    public long incrBy(final String key, final long value) {
        Long value1 = redisTemplate.opsForValue().increment(key, value);
        return value1;
    }

    /********************************** 类型：hash *********************************/
    /**
     * 类型：hash
     * 将哈希表 key 中的域 field 的值设为 value
     * @param key
     * @param field
     * @param value
     */
    public void hset(final String key, final String field, final String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 类型：hash
     * 返回哈希表 key 中给定域 field 的值
     * @param key
     * @param field
     * @return
     */
    public String hget(final String key, final String field) {
        if (redisTemplate.opsForHash().get(key, field) != null) {
            return redisTemplate.opsForHash().get(key, field)+"";
        }
        return null ;
    }

    /**
     * 类型：hash
     * 返回哈希表 key 中，所有的域和值
     * @param key
     * @return
     */
    public Map<Object, Object> hgetAll(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 类型：hash
     * 查看哈希表 key 中，给定域 field 是否存在
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(final String key, final String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 类型：hash
     * 返回哈希表 key 中域的数量
     * @param key
     * @return
     */
    public Long hlen(final String key){
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 类型：hash
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
     * @param key
     * @param members
     * @return
     */
    public Long hdel(final String key, final String... members) {
        return redisTemplate.opsForHash().delete(key, members);
    }

    /**
     * 类型：hash
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * @param key
     * @param map
     */
    public void hmset(final String key, final Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 类型：hash
     * 为哈希表 key 中的域 field 的值加上增量 increment 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key the key
     * @param field the field
     * @param value the value
     */
    public Long hincrby(final String key, final String field, final long value) {
        return redisTemplate.opsForHash().increment(key, field, value);
    }

    /********************************** 类型：set *********************************/
    /**
     * 类型：set
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素
     * @param key
     * @return
     */
    public String srandmember(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection redisConnection) {
                return new String(redisConnection.sRandMember(key.getBytes()));
            }
        });
    }

    /**
     * 类型：set
     * 返回集合 key 中的所有成员
     * @param key
     * @return
     */
    public Set<byte[]> smembers(final String key) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

            @Override
            public Set<byte[]> doInRedis(RedisConnection redisConnection) {
                return redisConnection.sMembers(key.getBytes());
            }
        });
    }

    /**
     * 类型：set
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
     * @param key
     * @param members
     * @return
     */
    public Long sadd(final String key, final String... members) {
        return redisTemplate.opsForSet().add(key, members);
    }

    /**
     * 类型：set
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
     * @param key
     * @param members
     * @return
     */
    public Long srem(final String key, final String... members) {
        return redisTemplate.opsForSet().remove(key, members);
    }

    /**
     * 类型：set
     * 判断 member 元素是否集合 key 的成员
     * @param key
     * @param value
     * @return
     */
    public Boolean sismember(final String key, final String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 类型：set
     * 返回两个集合的全部成员，该集合是所有给定集合的交集。
     * @param key1
     * @param key2
     * @return
     */
    public Set<String> sinter(final String key1, final String key2){
        return redisTemplate.opsForSet().intersect(key1, key2);
    }

    /********************************** 类型：list *********************************/
    /**
     * 类型：list
     * 返回列表 key 中指定区间内的元素，区间以偏移量 startIndex 和 endIndex 指定
     * @param key
     * @param startIndex
     * @param endIndex
     * @return
     */
    public List<String> lrange(final String key, final long startIndex, final long endIndex) {
        return redisTemplate.opsForList().range(key, startIndex, endIndex);
    }

    /**
     * 类型：list
     * 返回列表 key所有元素
     * @param key
     * @return
     */
    public List<String> lrange(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 类型：list
     * 从头部删除一个元素,
     * @param key
     * @return
     */
    public String lpop(final String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 类型：list
     * 将列表 key 下标为 index 的元素的值设置为 value
     * @param key
     * @param index
     * @param value
     * @return
     */
    public Boolean lset(final String key, final int index, final String value) {
        redisTemplate.opsForList().set(key, index, value);
        return true;
    }

    /**
     * 类型：list
     * 移除并返回列表 key 的尾元素
     * @param key the key
     * @return the string
     */
    public String rpop(final String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 类型：list
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值
     * count = 0 : 移除表中所有与 value 相等的值
     * @param key the key
     * @param count the count
     * @param value the value
     * @return the long
     */
    public long lrem(final String key, final long count, final String value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 类型：list
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * @param key
     * @param members
     * @return
     */
    public Long rpush(final String key, final String... members) {
        return redisTemplate.opsForList().rightPushAll(key, members);
    }

    /**
     * 类型：list
     * 返回列表 key 的长度
     * @param key
     * @return
     */
    public Long llen(final String key) {
        return redisTemplate.opsForList().size(key);
    }


    /**
     * 类型 ：list
     * 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key the key
     * @param values the values
     * @return the size after push
     */
    public Long lpush(final String key, final String... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /********************************** 类型：server *********************************/
    /**
     * 类型：server
     * 返回当前数据库的 key 的数量。
     * @return
     */
    public long dbsize() {
        return redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection redisConnection) {
                return redisConnection.dbSize();
            }
        });
    }


    /**
     * 配置参数keyName
     * @param projectName 项目名称
     * @param paramCode 参数code
     * @param tagId 公司/企业标识id
     * @param type 权限类型 //1平台级，2企业级，3系统级
     * @return
     */
    public String redisPcfgKey(String projectName,String paramCode,Integer tagId,Integer type){

        String ntype = "";
        switch (type==null?0:type){
            case 1 : ntype = "platform";break;
            case 2 : ntype = "customer";break;
            case 3 : ntype = "system";break;
            default:ntype = "system";break;
        }
        return projectName+":paramCfg:"+ntype+":tagId-"+tagId+":"+paramCode;
    }

}
