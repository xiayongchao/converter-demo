import entity.User;
import entity.UserInfo;
import org.jc.framework.converter.core.Converters;
import org.jc.framework.converter.support.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xiayc
 * @date 2019/3/13
 */
public class Main {
    public static void main(String[] args) {
        UserInfo user = new UserInfo();
        user.setId(null);
        user.setName("fdsafas");
        User userInfo = Converters.convert(user, User.class);
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
        userInfoList.add(user);
//        List<User> userList = Converters.convert(userInfoList, User.class);
        List<User> userList = Converters.convert(userInfoList, List.class, User.class);
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

        }
    }

    public void a() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.iterator();
        new LinkedBlockingQueue().iterator();

    }
}
