package com.haiwen.platform.common.utils;


/**
 * Twitter_Snowflake
 * <pre>
 * SnowFlake的结构如下(每部分用-分开):
 *
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 *
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
 *
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)得到的值），
 * 这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序SnowflakeIdWorker类的idepoch属性）。
 *
 * 41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号
 * 加起来刚好64位，为一个Long型。
 *
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 *
 * from https://github.com/twitter/snowflake/blob/scala_28/src/main/scala/com/twitter/service/snowflake/IdWorker.scala
 * </pre>
 */
public class SnowflakeIdWorker {
    /** 机器id所占的位数 */
    private static final long workerIdBits = 5L;
    /** 数据标识id所占的位数 */
    private static final long datacenterIdBits = 5L;
    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /** 支持的最大数据标识id，结果是31 */
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /** 序列在id中占的位数 */
    private static final long sequenceBits = 12L;
    /** 机器ID向左移12位 */
    private static final long workerIdShift = sequenceBits;
    /** 数据标识id向左移17位(12+5) */
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    /** 时间截向左移22位(5+5+12) */
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 开始时间截 (2018-05-01)一旦使用不可改变
     * <br/>
     * 生成方式:
     * <br/>
     * try { System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-01").getTime()); } catch (ParseException e) {}
     */
    private final long idepoch = 1525104000000L;
    /** 工作机器ID(0~31) */
    private final long workerId;
    /** 数据中心ID(0~31) */
    private final long datacenterId;
    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;
    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    private static int initWorkerId = 0;

    public SnowflakeIdWorker() {
        this(initWorkerId, maxDatacenterId);
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("workerId is illegal: " + workerId);
        }
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("datacenterId is illegal: " + workerId);
        }
        if (idepoch >= System.currentTimeMillis()) {
            throw new IllegalArgumentException("idepoch is illegal: " + idepoch);
        }
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public long getId() {
        long id = nextId();
        return id;
    }

    public String getStringId() {
        return Long.toString(nextId());
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - idepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
        return id;
    }

    /**
     * 获取id的时间戳(millis秒)
     *
     * @param id 通过{@link SnowflakeIdWorker#nextId()}生成的id值
     * @return id的时间戳(millis秒)
     */
    public long getIdTimestamp(long id) {
        return idepoch + (id >> timestampLeftShift);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SnowflakeIdWorker{");
        sb.append("workerId=").append(workerId);
        sb.append(", datacenterId=").append(datacenterId);
        sb.append(", idepoch=").append(idepoch);
        sb.append(", lastTimestamp=").append(lastTimestamp);
        sb.append(", sequence=").append(sequence);
        sb.append('}');
        return sb.toString();
    }
}
