package com.xlauch.utils.util.db;


import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 类描述 :
 * 参考示例地址： http://www.importnew.com/16436.html
 * strem : https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/index.html
 * java通过Stream对list集合分组: https://www.cnblogs.com/mr-wuxiansheng/p/7911626.html
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018/1/10
 */
public class TestJDK8 {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    class Man {

        String name;

        String sex;

        int age;

        @Override
        public String toString() {
            return "Man{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


    private List<Man> manList = new ArrayList<>();

    @Before
    public void init() {
        manList.add(new Man("张三", "男", 3));
        manList.add(new Man("张三1", null, 3));
        manList.add(new Man("李四", "女", 4));
        manList.add(new Man("王五", "男", 5));
        manList.add(new Man("赵六", "女", 6));
        manList.add(new Man("哈哈", "女", 9));
    }

    /**
     * 使用lambda表达式对列表进行迭代
     */
    @Test
    public void testLambda() {
        manList.forEach(man -> System.out.println(man));
        System.out.println("------------------------------");
        manList.forEach(System.out::println);
    }


    /**
     * 使用lambda表达式和函数式接口Predicate
     */
    @Test
    public void testLambda2() {
        manList.stream().filter(man -> man.getAge() > 5).forEach(System.out::println);
        System.out.println("------------------------------");
        filter(manList, man -> man.getAge() > 5);
        System.out.println("------------------------------");

        /**
         * 如何在lambda表达式中加入Predicate
         */
        Predicate<Man> predicate = man -> man.getAge() > 5;
        manList.stream().filter(predicate).forEach(System.out::println);
    }

    public void filter(List<Man> manList, Predicate<Man> predicate) {
        manList.stream().filter(man -> predicate.test(man)).forEach(System.out::println);
    }

    /**
     * 过滤排序组装
     */
    @Test
    public void testLambda3() {
        manList = manList.stream()
                .filter(man -> man.getAge() > 5)
                .sorted(Comparator.comparing(Man::getAge).reversed())
                .collect(Collectors.toList());
        manList.forEach(man -> System.out.println(man));
    }

    @Test
    public void testFilter2() {
        List<Man> findList = manList.stream().filter(man -> man.getSex().equalsIgnoreCase("女")).collect(Collectors.toList());

        if (findList != null && findList.size() > 0) {
            Man man = findList.get(0);
            System.out.println(man);
        }
    }

    @Test
    public void testFilter3() {

    }

    /**
     * Java 8中使用lambda表达式的Map和Reduce示例
     */
    @Test
    public void testMapReduce() {
        List<Integer> idList = Arrays.asList(1, 2, 3, 4);
        Integer total = idList.stream().map(id -> id + 2).reduce((sum, id) -> sum += id).get();
        System.out.println("total : " + total);

        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.", "Canada");
        String G7S = G7.stream().map(s -> s.toUpperCase()).collect(Collectors.joining(","));
        System.out.println("" + G7S);
    }


    @Test
    public void testMapReduce2() {
        String names = manList.stream().map(Man::getName).collect(Collectors.joining(","));
        System.out.println("" + names);

    }


    /**
     * java通过Stream对list集合分组
     */
    @Test
    public void testStreamGroup() {
        Map<String, List<Man>> map = manList.stream().collect(Collectors.groupingBy(Man::getName));
        map.forEach((key, mans) -> System.out.println(mans));
    }

    /**
     * java通过Stream对list集合分组
     */
    @Test
    public void testStreamPartition() {
        Map<Boolean, List<Man>> map = manList.stream().collect(Collectors.partitioningBy(man -> man.getAge() > 5));
        map.forEach((key, mans) -> System.out.println(mans));
    }

    /**
     * 计算集合元素的最大值、最小值、总和以及平均值
     */
    @Test
    public void testAvgMinMax() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntSummaryStatistics statistics = nums.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("avg : " + statistics.getAverage());
        System.out.println("count : " + statistics.getCount());
        System.out.println("max : " + statistics.getMax());
        System.out.println("min : " + statistics.getMin());
        System.out.println("sum : " + statistics.getSum());
    }

    @Test
    public void testAvgMinMax2() {
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println("concat : " + concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println("minValue : " + minValue);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(10, Integer::sum);
        System.out.println("sumValue : " + sumValue);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
        System.out.println("concat : " + concat);
    }

    @Test
    public void testMan() {
        Man man = new Man("sdfs", "男", 2);
        getMan(man);
        System.out.println(man);
    }


    private void getMan(Man man) {
        man.setAge(222);
    }

    @Test
    public void getNote() {
        String comment = "表单类型[formType](1:文本，2:日期框)";
        Pattern pattern = Pattern.compile("(.*)");
        Matcher matcher = pattern.matcher(comment);
        matcher.replaceAll("").trim();
        while (matcher.find()) {
            System.out.println(matcher.group(1));

        }
    }

    @Test
    public void testReplace() {
        String str = "validType:['length[0,20]','DATATIME']";
        int width = 50;
        int pos = str.indexOf("length[0,");

        String before = str.substring(0, pos + 9);
        String after = str.substring(pos + 9);
        System.out.println("before : " + before);
        System.out.println("after : " + after);


        pos = after.indexOf("]");
        after = after.substring(pos);

        System.out.println("before : " + before);
        System.out.println("after : " + after);
        System.out.println(before + width + after);
    }

    @Test
    public void testList() {
        System.out.println("befroe-------------------------------");
        manList.forEach(System.out::println);

        String str = JSON.toJSONString(manList);
        System.out.println("" + str);
        List<Man> manList1 = JSON.parseArray(str, Man.class);

        manList1.forEach(System.out::println);

        System.out.println("after-------------------------------");
        manList1 = manList1.stream().map(man -> man.setAge(13)).collect(Collectors.toList());
        manList.forEach(System.out::println);
        manList1.forEach(System.out::println);
    }

    static class Person {
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person() {
        }

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Test
    public void testList2() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "name1"));
        personList.add(new Person(2, "name2"));

        String str = JSON.toJSONString(personList);

        List<Person> personList2 = new ArrayList<>();
        personList2 = JSON.parseArray(str, Person.class);
    }

    @Test
    public void testList3() {
        String ids = "1,2,3,5" ;
        List list = Arrays.asList(ids.split(","));
        list.forEach(System.out::println);
        System.out.println(list.stream().map(s -> "?").collect(Collectors.joining(",")));

        List<Man> manList1 = new ArrayList<>() ;
        Man man = new Man("fff", "男" ,20) ;
        manList1.add(man);
        //man = new Man("aaa", "女" ,22) ;  深复制，另一个对象
        //man.setName("aaa").setAge(33).setSex("nv");  // 同一个对象
        Man man2 = new Man();
        BeanUtils.copyProperties(man , man2);
        man2.setSex("ss");
        manList1.add(man2);
        manList1.forEach(System.out::println);
    }


    @Test
    public void testReduce(){
//        Stream<Integer> stream = Stream.of(1,2,3,4) ;
//        int res1 = stream.reduce((a, b) -> a + b).get();
//        System.out.println("" + res1);
//        int res2 = stream.reduce(0 ,(a, b) -> a + b);
//        System.out.println("" + res2);

        System.out.println("" + manList.stream().map(Man::getAge).reduce(10 , (a,b) -> a+ b));
    }


    @Test
    public void testReduceMap(){
        String res  = manList.stream().map(man -> {
            return man.getSex();
            //return ObjectUtils.defaultIfNull(man.getSex() , "");
        }).filter(s -> s != null).distinct().collect(Collectors.joining(","));

        System.out.println("" +  res);
    }



}
