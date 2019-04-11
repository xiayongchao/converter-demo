package org.jc.framework.converter.test;

import org.jc.framework.converter.core.Converter;
import org.jc.framework.converter.core.Converters;
import org.jc.framework.converter.entity.User;
import org.jc.framework.converter.entity.UserInfo;

import java.util.*;

public class Test {
    @org.junit.Test
    public void test1() {
        User user = new User();
//        user.setId(1);
//        user.setName("下一次");
        UserInfo userInfo = Converters.setDeepCopy(true).convert(user, UserInfo.class);


        System.out.println(userInfo.getId());
        System.out.println(userInfo.getName());
        System.out.println("=====================");
    }

    @org.junit.Test
    public void test3() {

    }

    @org.junit.Test
    public void test4() {
        UserInfo user = new UserInfo();
        user.setId(null);
        user.setName("fdsafas");
        User userInfo = Converters.setDeepCopy(true).convert(user, User.class);
        System.out.println("-----------------------------------------");
        if (userInfo != null) {
            System.out.println(userInfo.getId());
            System.out.println(userInfo.getName());
        } else {
            System.out.println("is null");
        }
        System.out.println("-----------------------------------------");
        System.out.println("=========================================");

        Converters.addConverter(new Converter<List<Integer>, List<Integer>>() {
            /**
             * 进行转换
             *
             * @param source
             * @return
             */
            @Override
            public List<Integer> convert(List<Integer> source) {
                return source;
            }
        }).addConverter(new Converter<Integer, Long>() {
            /**
             * 进行转换
             *
             * @param source
             * @return
             */
            @Override
            public Long convert(Integer source) {
                return source == null ? null : source.longValue();
            }
        });

        List<UserInfo> userInfoList = new ArrayList<>();
        user.setAskIdList(Arrays.asList(1, 2, 3));
        user.setCommentIdList(Arrays.asList(4, 5, 6));
        user.setLogMap(new HashMap<String, String>() {
            {
                put("a", "fdas");
                put("b", "43fre");
            }
        });
        userInfoList.add(user);
        List<User> userList = (List<User>) Converters.setDeepCopy(true).convert(userInfoList, ArrayList.class, User.class);
        for (User user1 : userList) {
            System.out.println(user1.getId());
            System.out.println(user1.getName());
            if (user1.getAskIdList() != null) {
                for (Integer integer : user1.getAskIdList()) {
                    System.out.println("> " + integer);
                }
            }
            if (user1.getCommentIdList() != null) {
                for (Long l : user1.getCommentIdList()) {
                    System.out.println(">> " + l);
                }
            }
            if (user1.getLogMap() != null) {
                for (Map.Entry<String, String> entry : user1.getLogMap().entrySet()) {
                    System.out.println(entry.getKey() + "==" + entry.getValue());
                }
            }
        }
    }
}
